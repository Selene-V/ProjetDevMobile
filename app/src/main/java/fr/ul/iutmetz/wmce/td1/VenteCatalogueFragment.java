package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.AjoutFavoriDAO;
import fr.ul.iutmetz.wmce.td1.DAO.FavorisDAO;
import fr.ul.iutmetz.wmce.td1.DAO.ProduitDAO;
import fr.ul.iutmetz.wmce.td1.DAO.SuppressionFavoriDAO;
import fr.ul.iutmetz.wmce.td1.DAO.TailleDAO;
import fr.ul.iutmetz.wmce.td1.modele.Panier;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Favoris;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.ActiviteEcommerce;

import fr.ul.iutmetz.wmce.td1.modele.Taille;
import utils.Triplet;
import utils.Utils;


public class VenteCatalogueFragment extends Fragment
        implements ActiviteEnAttenteImage,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private ArrayList<Produit> modele;
    private int noPullCourant;
    private boolean agrandie;
    private int idCategorie;

    private Panier panier;

    private boolean isError;
    private String errorCourante;
    private ArrayList<Bitmap> listeImagesProduits;
    private ArrayMap<Integer, Boolean> listeFavorisProduit;


    private Utils utils = new Utils();

    // Differents elements qui composent la page
    private ImageView image_pull;
    private TextView titre;
    private TextView description;
    private TextView prix;
    private ImageButton panierButton;
    private Button bPrecedent;
    private Button bSuivant;
    private ImageView image_pull_grande;
    private Spinner staille;
    private TextView euro;
    private TextView errorSpinner;
    private ImageButton favoris;


    SessionManager sessionManager;

    private static final int MAIN_SAISIE_NOUVEAU_PULL = 2;

    private View root;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("noPull", this.noPullCourant);
        outState.putSerializable("listePull", this.modele);
        outState.putBoolean("agrandie", this.agrandie);
        outState.putBoolean("is_error", this.isError);
        outState.putSerializable("panier", this.panier);
        outState.putString("error_courante", this.errorCourante);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_vente_catalogue, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState!=null){
            this.noPullCourant = savedInstanceState.getInt("noPull");
            this.modele = (ArrayList<Produit>) savedInstanceState.getSerializable("listePull");
            this.agrandie = savedInstanceState.getBoolean("agrandie");
            this.isError = savedInstanceState.getBoolean("is_error");
            this.panier = (Panier) savedInstanceState.getSerializable("panier");
            this.errorCourante = savedInstanceState.getString("error_courante");

        }else {
            this.sessionManager = new SessionManager(this.getContext());
            this.modele = new ArrayList<>();

            // Recuperation id categorie
            if (this.getArguments().getInt("id_categ", -1)!=-1){
                this.idCategorie = this.getArguments().getInt("id_categ", -1);
            }

            ProduitDAO prodDAO = new ProduitDAO();
            prodDAO.findAllByCategorie(this, this.idCategorie);

            this.panier = ((ActiviteEcommerce) this.getActivity()).getPanier();

            this.noPullCourant = 0;

            this.agrandie = false;

            this.isError = false;

            this.errorCourante = "Erreur";
        }


        this.listeFavorisProduit = new ArrayMap<>();

        this.listeImagesProduits = new ArrayList<>();
        for (int i = 0 ; i < this.modele.size() ; i++){
            this.listeImagesProduits.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.modele.get(i).getVisuel(), String.valueOf(i));
        }

        return this.root;
    }

    @Override
    public void onStart(){
        super.onStart();

        this.image_pull = this.root.findViewById(R.id.image_pull);
        this.titre = this.root.findViewById(R.id.titre);
        this.description = this.root.findViewById(R.id.desc);
        this.prix = this.root.findViewById(R.id.prix_pull);
        this.bPrecedent = this.root.findViewById(R.id.bPrecedent);
        this.bSuivant = this.root.findViewById(R.id.bSuivant);
        this.image_pull_grande = this.root.findViewById(R.id.image_pull_grande);
        this.staille = this.root.findViewById(R.id.taille_spinner);
        this.euro = this.root.findViewById(R.id.euro_pull);
        this.panierButton = this.root.findViewById(R.id.image_panier);
        this.errorSpinner = this.root.findViewById(R.id.error_spinner);
        this.favoris = this.root.findViewById(R.id.image_favori);

        this.image_pull.setOnClickListener(this::onClickZoom);
        this.image_pull_grande.setOnClickListener(this::onClickDezoom);
        this.bPrecedent.setOnClickListener(this::onClickPrecedent);
        this.bSuivant.setOnClickListener(this::onClickSuivant);
        this.panierButton.setOnClickListener(this::onClickPanier);
        this.favoris.setOnClickListener(this::onClickFavoris);
        this.panierButton.setOnClickListener(this::onClickPanier);

        if (this.modele.size()>0){
            // Changements
            changement();
            verifbPrecedent();
            verifbSuivant();
        }

        if (this.agrandie){
            this.visibilite(View.INVISIBLE);

            this.image_pull_grande.setVisibility(View.VISIBLE);
        }

        if (!sessionManager.isLoggin()){
            this.favoris.setVisibility(View.INVISIBLE);
        }

        if (this.isError && !this.agrandie){
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void onClickFavoris(View v){
        if (sessionManager.isLoggin()){
            int idClient = sessionManager.getIdUser();
            Favoris f = new Favoris(idClient, this.modele.get(noPullCourant).getId());
            if (this.listeFavorisProduit.containsKey(this.modele.get(noPullCourant).getId())){
                // SUPPRESSION
                SuppressionFavoriDAO delFavDAO = new SuppressionFavoriDAO();
                delFavDAO.delete(this, f);
                Toast.makeText(this.getContext(), R.string.delete_favori, Toast.LENGTH_LONG).show();
            } else {
                // AJOUT
                AjoutFavoriDAO newFavDAO = new AjoutFavoriDAO();
                newFavDAO.insert(this, f);
                Toast.makeText(this.getContext(), R.string.insert_favori, Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * clic sur le bouton pull suivant
     * @param v la vue cliquée
     */
    public void onClickSuivant(View v){
        if (this.noPullCourant + 1 < modele.size()) {
            this.noPullCourant += 1;
            this.changement();
        }
        this.isError = false;
        this.errorSpinner.setVisibility(View.INVISIBLE);
        verifbSuivant();
    }

    /**
     * clic sur le bouton pull precedent
     * @param v la vue cliquée
     */
    public void onClickPrecedent(View v){
        if (this.noPullCourant - 1 >= 0) {
            this.noPullCourant -= 1;
            this.changement();
        }
        this.isError = false;
        this.errorSpinner.setVisibility(View.INVISIBLE);
        verifbPrecedent();
    }

    public void changementSpinnerTaille(ArrayList listSpinner){

        ArrayAdapter adaptateur = new ArrayAdapter(this.getContext(),
                android.R.layout.simple_spinner_item, listSpinner
        );
        adaptateur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staille.setAdapter(adaptateur);
    }

    public void changement(){
        // Changement img
        if (this.listeImagesProduits.get(noPullCourant) != null){
            this.image_pull.setImageBitmap((Bitmap)this.listeImagesProduits.get(noPullCourant));
            this.image_pull_grande.setImageBitmap((Bitmap) this.listeImagesProduits.get(noPullCourant));
        } else {
            int id = this.getResources().getIdentifier(
                    this.modele.get(noPullCourant).getVisuel(),
                    "drawable",
                    this.getActivity().getPackageName());
            this.image_pull.setImageResource(id);
            this.image_pull_grande.setImageResource(id);
        }

        // Changement titre
        this.titre.setText(this.modele.get(noPullCourant).getTitre());
        // Changement desc
        this.description.setText(this.modele.get(noPullCourant).getDescription());
        // Changement prix
        this.prix.setText(this.modele.get(noPullCourant).getPrix());
        // Changement error
        this.errorSpinner.setText(this.errorCourante);

        this.changementVueFavoris();

        TailleDAO tailleDAO = new TailleDAO();
        tailleDAO.peuplerSpinnerTaille(this);
    }

    public void changementVueFavoris(){
        if(this.listeFavorisProduit.size() >0) {
            System.out.println(listeFavorisProduit);
            System.out.println("nopulCourant : " + noPullCourant);
            System.out.println("idPull : " + this.modele.get(noPullCourant).getId());
            if (this.listeFavorisProduit.containsKey(this.modele.get(noPullCourant).getId())) {
                this.favoris.setImageResource(R.drawable.ic_favoris);
            } else {
                this.favoris.setImageResource(R.drawable.ic_pas_favoris);
            }
        }
    }

    public void verifbPrecedent(){
        this.bPrecedent.setEnabled(!(this.noPullCourant == 0));
        if (noPullCourant != modele.size()-1){
            this.bSuivant.setEnabled(true);
        }
    }

    public void verifbSuivant(){
        this.bSuivant.setEnabled(!(this.noPullCourant == modele.size()-1));
        if (noPullCourant != 0){
            this.bPrecedent.setEnabled(true);
        }
    }

    public void onClickPanier(View v){
        if (!(this.staille.getSelectedItem().toString().equals("Choix de la taille"))){

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.quantity_alert,null);
        final EditText text = view.findViewById(R.id.EditQuantity);

        builder.setTitle(R.string.quantity_titre);
        builder.setView(view);

        builder.setPositiveButton(R.string.valider, ((dialog, which) -> {
            final String input = text.getText().toString();
            int quantity =1;

            if(!input.matches("")){
                quantity = Integer.parseInt(input);
            }


            this.errorSpinner.setVisibility(View.INVISIBLE);
            this.isError = false;

            if(this.panier.articleInBasket(this.modele.get(noPullCourant).getId()) != -1){
                int index = this.panier.articleInBasket(this.modele.get(noPullCourant).getId());
                this.panier.updateArticleQuantity(this.panier.getBasketContent().get(index).getProduit().getId(), quantity);                System.out.println(this.panier.getBasketContent().get(0).getQuantite());

            }
            else{
                this.panier.basketContent.add(new Triplet<Produit, String, Integer>(this.modele.get(noPullCourant), this.staille.getSelectedItem().toString(), quantity ));
                System.out.println(this.panier.getBasketContent().get(0).getQuantite());
            }

            ((ActiviteEcommerce) this.getActivity()).setPanier(this.panier);
            //Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_gestion_panier);
            Toast.makeText(this.getContext(),
                    String.format(getString(R.string.ajout_panier), this.noPullCourant + 1),
                    Toast.LENGTH_LONG).show();

        }));
        builder.setNegativeButton(R.string.annuler,null);
        builder.show();
        }
        else {
            this.isError = true;
            this.errorCourante = "Vous devez selectionner une taille !";
            this.errorSpinner.setText(this.errorCourante);
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void onClickZoom(View v){
        this.agrandie = true;
        this.visibilite(View.INVISIBLE);
        this.image_pull_grande.setVisibility(View.VISIBLE);
        this.errorSpinner.setVisibility(View.INVISIBLE);
    }


    public void onClickDezoom(View v){
        this.agrandie = false;
        this.visibilite(View.VISIBLE);
        this.image_pull_grande.setVisibility(View.INVISIBLE);
        if (this.isError){
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void visibilite(int visibility){
        this.image_pull.setVisibility(visibility);
        this.titre.setVisibility(visibility);
        this.description.setVisibility(visibility);
        this.prix.setVisibility(visibility);
        this.bSuivant.setVisibility(visibility);
        this.bPrecedent.setVisibility(visibility);
        this.staille.setVisibility(visibility);
        this.euro.setVisibility(visibility);
        this.panierButton.setVisibility(visibility);
        this.panierButton.setVisibility(visibility);
        if (sessionManager.isLoggin()){
            this.favoris.setVisibility(visibility);
        } else {
            this.favoris.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            if (requestCode == MAIN_SAISIE_NOUVEAU_PULL){
                Bundle extras = data.getExtras();
                Produit p = (Produit) extras.get("nouveau_pull");
                this.modele.add(p);
            }
        }
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesProduits.set(idx, img);
            if (idx==this.noPullCourant) {
                changement();
            }
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String requete = response.getString("requete");
            int cmp = 0;
            switch (requete){
                case "produits" :
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0 ; i < data.length() ; i++) {
                        JSONObject o = response.getJSONArray("data").getJSONObject(i);

                        int idProduit = o.getInt("id_produit");
                        int idCat = o.getInt("id_categorie");
                        String title = o.getString("titre");
                        String desc = o.getString("description");
                        String tarif = String.valueOf(o.getDouble("tarif"));
                        String visuel = o.getString("visuel");

                        if (sessionManager.isLoggin()){
                            int idClient = sessionManager.getIdUser();
                            FavorisDAO favDAO = new FavorisDAO();
                            favDAO.findOneByIds(this, idClient, idProduit);
                        }

                        Produit prod = new Produit(idProduit, title, desc, tarif, visuel, idCat);
                        this.modele.add(prod);

                        this.listeImagesProduits.add(null);
                        ImageFromURL chargement = new ImageFromURL(this);
                        chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                                this.modele.get(i).getVisuel(), String.valueOf(i));
                    }
//                    // Changements
//                    changement();
//                    verifbPrecedent();
//                    verifbSuivant();
                    break;
                case "taillesProduits" :
                    JSONArray taille = response.getJSONArray("data");
                    ArrayList<Taille> listSpinner = new ArrayList<>();
                    listSpinner.add(new Taille(0, "Choix de la taille"));
                    for (int i = 0 ; i < taille.length() ; i++){
                        JSONObject o = taille.getJSONObject(i);
                        int idProduit = o.getInt("id_produit");
                        if (this.modele.get(noPullCourant).getId() == idProduit) {
                            String libelle = o.getString("libelle");
                            listSpinner.add(libelle);
                        }
                    }
                    changementSpinnerTaille(listSpinner);
                    break;
                case "favorisProduit" :
                    boolean isFavori = response.getBoolean("res");
                    if (isFavori){
                        JSONObject fav = response.getJSONObject("data");
                        System.out.println("isFavori de " + this.listeFavorisProduit.size()+1);
                        System.out.println(isFavori);
                        System.out.println("id produit");
                        System.out.println(fav.getInt("id_produit"));
                        this.listeFavorisProduit.put(fav.getInt("id_produit"), isFavori);
                    } else {
                        System.out.println("isFavori de " + this.listeFavorisProduit.size()+1);
                        System.out.println(isFavori);
                        this.listeFavorisProduit.put(-1, isFavori);
                    }
                    // Changements
                    changement();
                    verifbPrecedent();
                    verifbSuivant();
                    break;
                case "insertFavori" :
                    int idProduitInsert = response.getInt("id_produit");
                    this.listeFavorisProduit.put(idProduitInsert, true);

                    changementVueFavoris();
                    break;
                case "deleteFavori" :
                    int idProduitDelete = response.getInt("id_produit");
                    this.listeFavorisProduit.remove(idProduitDelete);

                    changementVueFavoris();
                    break;
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}