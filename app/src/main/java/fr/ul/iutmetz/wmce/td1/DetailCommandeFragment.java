package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.LigneCommandeDetaillee;
import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class DetailCommandeFragment extends Fragment
        implements ActiviteEnAttenteImage,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private int idCommandeCourante;
    private ArrayList<LigneCommandeDetaillee> listeDetailCommande;
    private ArrayList listeImagesCommande;

    SessionManager sessionManager;

    private ListView lvDetailCommande;
    private DetailCommandeAdapter adapteur;

    private View root;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_affichage_detail_commande, container, false);

        sessionManager = new SessionManager(this.getContext());

        // Recuperation id categorie
        if (this.getArguments().getInt("id_commande", -1)!=-1){
            this.idCommandeCourante = this.getArguments().getInt("id_commande", -1);
        }

        this.listeDetailCommande = new ArrayList<>();
//        sessionManager.checkIsLogin();

        CommandeDAO comDAO = new CommandeDAO();
        comDAO.findDetailOneCommand(this, idCommandeCourante);

        this.listeImagesCommande = new ArrayList<>();
        for (int i = 0 ; i < this.listeDetailCommande.size() ; i++){
            this.listeImagesCommande.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.listeDetailCommande.get(i).getProduit().getVisuel(), String.valueOf(i));
        }
        this.adapteur = new DetailCommandeAdapter(
                this.getContext(),
                this.listeDetailCommande,
                this.listeImagesCommande
        );

        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.lvDetailCommande = this.root.findViewById(R.id.commande_detaillee_liste);

        this.lvDetailCommande.setAdapter(adapteur);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");
            System.out.println("-------------- DATA DETAILS--------------");
            System.out.println(data);
            for (int i = 0 ; i < response.length() ; i++){
                JSONObject commande = data.getJSONObject(i);

                int idCommande = commande.getInt("id_commande");
                int idProd = commande.getInt("id_produit");

                String titre = commande.getString("titre");
                String desc = commande.getString("description");
                String prix = commande.getString("tarif");
                String visuel = commande.getString("visuel");
                int idCat = commande.getInt("id_categorie");

                Produit produit = new Produit(idProd, titre, desc, prix, visuel, idCat);

                int quantite = commande.getInt("quantite");
                String taille = commande.getString("libelle");
                String tarif = commande.getString("tarif");

                LigneCommandeDetaillee lCom = new LigneCommandeDetaillee(i, idCommande, produit, quantite, taille, tarif);
                this.listeDetailCommande.add(lCom);

                this.listeImagesCommande.add(null);
                ImageFromURL chargement = new ImageFromURL(this);
                chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                        this.listeDetailCommande.get(i).getProduit().getVisuel(), String.valueOf(i));
            }
            this.adapteur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(){
        this.getActivity().setResult(-1);
        this.getActivity().finish();
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesCommande.set(idx, img);
            this.adapteur.notifyDataSetChanged();
        }
    }
}