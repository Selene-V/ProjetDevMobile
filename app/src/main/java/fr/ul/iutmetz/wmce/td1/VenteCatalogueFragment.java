package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import fr.ul.iutmetz.wmce.td1.DAO.ProduitDAO;
import fr.ul.iutmetz.wmce.td1.DAO.TailleDAO;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import utils.Utils;


public class VenteCatalogueFragment extends Fragment
        implements ActiviteEnAttenteImage,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private ArrayList<Produit> modele;
    private int noPullCourant;
    private boolean agrandie;
    private int idCategorie;
    private double totalPanier;
    private boolean isError;
    private String errorCourante;
    private ArrayList listeImagesProduits;


    private Utils utils = new Utils();

    // Differents elements qui composent la page
    private ImageView image_pull;
    private TextView titre;
    private TextView description;
    private TextView prix;
    private ImageButton panier;
    private Button bPrecedent;
    private Button bSuivant;
    private ImageView image_pull_grande;
    private Spinner staille;
    private TextView euro;
    private ImageButton imageAdd;
    private TextView texteAdd;
    private TextView errorSpinner;

    private static final int MAIN_SAISIE_NOUVEAU_PULL = 2;
    public static final int RETOUR = 0;
    public static final int ANNULER = -1;

    private View root;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("noPull", this.noPullCourant);
        outState.putSerializable("listePull", this.modele);
        outState.putBoolean("agrandie", this.agrandie);
        outState.putBoolean("is_error", this.isError);
        outState.putDouble("total_panier", utils.arrondir(this.totalPanier));
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
            this.totalPanier = utils.arrondir(savedInstanceState.getDouble("total_panier"));
            this.errorCourante = savedInstanceState.getString("error_courante");

        }else {

            this.modele = new ArrayList<>();

            ProduitDAO prodDAO = new ProduitDAO();
            prodDAO.findAll(this);



            this.totalPanier = utils.arrondir(0.00);

            this.noPullCourant = 0;

            this.agrandie = false;

            this.isError = false;

            this.errorCourante = "Erreur";

            // Recuperation id categorie
            if (this.getArguments().getInt("id_categ", -1)!=-1){
                this.idCategorie = this.getArguments().getInt("id_categ", -1);
            }
        }

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
        this.panier = this.root.findViewById(R.id.image_panier);
        this.imageAdd = this.root.findViewById(R.id.image_add);
        this.texteAdd = this.root.findViewById(R.id.texte_add);
        this.errorSpinner = this.root.findViewById(R.id.error_spinner);

        this.image_pull.setOnClickListener(this::onClickZoom);
        this.image_pull_grande.setOnClickListener(this::onClickDezoom);
        this.bPrecedent.setOnClickListener(this::onClickPrecedent);
        this.bSuivant.setOnClickListener(this::onClickSuivant);
        this.panier.setOnClickListener(this::onClickPanier);

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

        if (this.isError && !this.agrandie){
            this.errorSpinner.setVisibility(View.VISIBLE);
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
            System.out.println("--------- liste IMG Produits ---------");
            System.out.println(this.listeImagesProduits);
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

        TailleDAO tailleDAO = new TailleDAO();
        tailleDAO.peuplerSpinnerTaille(this);
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
            this.errorSpinner.setVisibility(View.INVISIBLE);
            this.isError = false;
            Toast.makeText(this.getContext(),
                    String.format(getString(R.string.ajout_panier), this.noPullCourant + 1),
                    Toast.LENGTH_LONG).show();
            double prix = Double.parseDouble(this.modele.get(noPullCourant).getPrix());
            this.totalPanier += utils.arrondir(prix);
        } else {
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
        this.texteAdd.setVisibility(visibility);
        this.imageAdd.setVisibility(visibility);
        this.panier.setVisibility(visibility);
    }

    //    Intent est la classe que vous aurez besoin pour faire le lien entre deux activités,
//    les paramètres sont  l'actitivé actuelle et l'activité que vous souhaitez appeler,
//    une fois que le objet est crée, il faut juste appeler au méthode "startActivity"
//    public void onClickAddPull(View v){
//        Intent intent = new Intent(afficheProduit.this, SaisieNouveauPullFragment.class);
//        intent.putExtra("id_categ", this.idCategorie);
//        startActivityForResult(intent, MAIN_SAISIE_NOUVEAU_PULL);
//    }

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
            System.out.println("REQUETE RESPONSE");
            System.out.println(response);
            String requete = response.getString("requete");
            System.out.println("requete");
            System.out.println(requete);
            JSONArray data = response.getJSONArray("data");
            int cmp = 0;
            switch (requete){
                case "produits" :
                    System.out.println("PRODUIT");
                    for (int i = 0 ; i < data.length() ; i++) {
                        JSONObject o = response.getJSONArray("data").getJSONObject(i);

                        int idCat = o.getInt("id_categorie");

                        System.out.println("---------- produit " + i + "------------------");
                        if (idCategorie == idCat) {

                            int idProduit = o.getInt("id_produit");
                            String title = o.getString("titre");
                            String desc = o.getString("description");
                            String tarif = String.valueOf(o.getDouble("tarif"));
                            String visuel = o.getString("visuel");

                            Produit prod = new Produit(idProduit, title, desc, tarif, visuel, idCat);
                            System.out.println("------- Produit : " + prod.getTitre());
                            this.modele.add(prod);

                            this.listeImagesProduits.add(null);
                            ImageFromURL chargement = new ImageFromURL(this);
                            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                                    this.modele.get(cmp).getVisuel(), String.valueOf(cmp));
                            cmp++;
                        }
                    }
                    // Changements
                    changement();
                    verifbPrecedent();
                    verifbSuivant();
                    break;
                case "taillesProduits" :
                    System.out.println("TAILLESPRODUITS");
                    ArrayList<String> listSpinner = new ArrayList<>();
                    listSpinner.add("Choix de la taille");
                    for (int i = 0 ; i < data.length() ; i++){
                        JSONObject o = data.getJSONObject(i);
                        int idProduit = o.getInt("id_produit");
                        if (this.modele.get(noPullCourant).getId() == idProduit) {
                            System.out.println(idProduit);
                            String libelle = o.getString("libelle");
                            System.out.println(libelle);
                            listSpinner.add(libelle);
                        }
                    }
                    changementSpinnerTaille(listSpinner);
                    break;
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}