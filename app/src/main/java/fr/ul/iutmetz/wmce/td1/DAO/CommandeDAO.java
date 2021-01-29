package fr.ul.iutmetz.wmce.td1.DAO;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.Panier;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.modele.Taille;
import utils.Triplet;

public class CommandeDAO {

    public static void findLastCommandByClient(Fragment fragment, int idClient){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/findLastCommandByClient.php?id_client="+idClient;

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void findAllCommandsByClient(Fragment fragment, int idClient){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/findAllCommandsByClient.php?id_client="+idClient;

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void findDetailOneCommand(Fragment fragment, int idCommande){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/findDetailOneCommand.php?id_commande="+idCommande;

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void insertCommande(Fragment fragment, Commande commande){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/insertCommande.php?id_commande="+commande.getId()+"&id_client="+commande.getIdClient()+"&date="+commande.getDateCommande();

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void insertLigneCommande(Fragment fragment, ArrayList<Triplet<Produit, Taille, Integer>> contenuPanier, Commande commande){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        for (int i = 0 ; i < contenuPanier.size() ; i++){
            String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/insertLigneCommande.php?id_commande="+commande.getId()+"&id_produit="+contenuPanier.get(i).getProduit().getId()+"&id_taille="+contenuPanier.get(i).getTaille().getId()+"&quantite="+contenuPanier.get(i).getQuantite()+"&tarif="+contenuPanier.get(i).getProduit().getPrix();

            // Request a string response from the provided URL
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    (com.android.volley.Response.Listener<JSONObject>) fragment,
                    (com.android.volley.Response.ErrorListener) fragment);

            // Add the request to the RequestQueue
            queue.add(jsonRequest);
        }


    }
}
