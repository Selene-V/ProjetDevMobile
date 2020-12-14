package fr.ul.iutmetz.wmce.td1.DAO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class CategorieDAO {

    public static void findAll(Context activite){

        RequestQueue queue = Volley.newRequestQueue(activite);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/categories/findallCategorie.php";

        // Request a string response from the provided URL
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activite,
                (com.android.volley.Response.ErrorListener) activite);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
