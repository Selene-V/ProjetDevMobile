package fr.ul.iutmetz.wmce.td1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.DAO.FavorisDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.Panier;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.modele.Taille;
import utils.Triplet;

public class PanierFragment extends Fragment implements ActiviteEnAttenteImage, com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {
    private PanierAdapter adaptateur;
    private Panier panier;
    private ArrayList<Bitmap> listeProduitsImages;
    private View root;
    private ListView lvPanierListe;
    private float prixTotal;
    private Button valider;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {

        this.root = inflater.inflate(R.layout.fragment_panier, container, false);

        this.panier = ((ActiviteEcommerce) this.getActivity()).getPanier();

        this.sessionManager = new SessionManager(this.getContext());

        this.listeProduitsImages = new ArrayList<>();
        for (int i = 0 ; i < this.panier.getBasketContent().size() ; i++){
            this.listeProduitsImages.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.panier.getBasketContent().get(i).getProduit().getVisuel(), String.valueOf(i));
        }

        this.prixTotal = this.panier.getTotalPanier();

        TextView tvTotal = this.root.findViewById(R.id.totalAmount);
        tvTotal.setText(Float.toString(this.prixTotal));

        this.adaptateur = new PanierAdapter(
                this.getContext(),
                this.panier.getBasketContent(),
                this.listeProduitsImages
        );

        return this.root;
    }

    @Override
    public void onStart(){
        super.onStart();

        this.valider = this.root.findViewById(R.id.bouttonValiderPanier);
        this.lvPanierListe = this.root.findViewById(R.id.panier_liste);
        this.lvPanierListe.setAdapter(adaptateur);

        this.valider.setOnClickListener(this::onClickValiderCommande);
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeProduitsImages.set(idx, img);
            this.adaptateur.notifyDataSetChanged();
        }
    }

    public void onClickValiderCommande(View view)  {

        String pattern = "YYYY-DD-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        Commande c = new Commande(-1,date ,sessionManager.getIdUser());

        CommandeDAO cDAO = new CommandeDAO();
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
            switch (requete){
                case "insertCommande" :
                    CommandeDAO cDAOCommande = new CommandeDAO();
                    cDAOCommande.findLastCommandByClient(this,sessionManager.getIdUser());
                case "rechercheCommande":
                    JSONObject data = response.getJSONObject("data");
                    int idCommande = data.getInt("id_commande");
                    CommandeDAO cDAOLigne = new CommandeDAO();
                    cDAOLigne.insertLigneCommande(this,panier.getBasketContent(), idCommande);
                    break;
                case "insertLigneCommande" :
                    this.panier = new Panier(new ArrayList<Triplet<Produit, Taille, Integer>>());
                    ((ActiviteEcommerce) this.getActivity()).setPanier(this.panier);
                    Navigation.findNavController(this.root).navigate(R.id.action_to_Boutique,new Bundle());
                    break;
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
    }
}