package fr.ul.iutmetz.wmce.td1.DAO;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.modele.Favoris;

public class SuppressionFavoriDAO {

    public static void delete(Fragment fragment, Favoris f){

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/favoris/deleteFavori.php?id_client=" + f.getIdClient() + "&id_produit=" + f.getIdProduit();

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (com.android.volley.Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
