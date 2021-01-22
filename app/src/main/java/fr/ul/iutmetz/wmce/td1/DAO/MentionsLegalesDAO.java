package fr.ul.iutmetz.wmce.td1.DAO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MentionsLegalesDAO {

    public static void findAll(Context activite){

        RequestQueue queue = Volley.newRequestQueue(activite);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/mentions_legales/findAll.php";

        // Request a string response from the provided URL
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activite,
                (com.android.volley.Response.ErrorListener) activite);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
