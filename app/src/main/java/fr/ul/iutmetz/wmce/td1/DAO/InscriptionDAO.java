package fr.ul.iutmetz.wmce.td1.DAO;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.modele.Client;

public class InscriptionDAO {

    public static void insert(Fragment fragment, Client c){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/clients/insertNewClient.php?nom=" + c.getNom() +
        "&prenom=" + c.getPrenom() + "&identifiant_client=" + c.getIdentifiant() + "&mdp=" + c.getMdp() + "&adrNum=" + c.getAdrNumero() +
                "&adrVoie=" + c.getAdrVoie() + "&adrCP=" + c.getAdrCP() + "&adrVille=" + c.getAdrVille() + "&adrP=" + c.getAdrPays();

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
