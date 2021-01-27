package fr.ul.iutmetz.wmce.td1.DAO;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

    public class MagasinDAO {
        public static void findAll(Fragment fragment){

            RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
            String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/magasins/findAllMagasins.php";

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    (com.android.volley.Response.Listener<JSONObject>) fragment,
                    (com.android.volley.Response.ErrorListener) fragment);

            // Add the request to the RequestQueue
            queue.add(jsonRequest);
        }

        public static void findOneMagasinById(Fragment fragment, int idMagasin){

            RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
            String url = "https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/magasins/findOneMagasinById.php?id_magasin=" + idMagasin;

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    (com.android.volley.Response.Listener<JSONObject>) fragment,
                    (com.android.volley.Response.ErrorListener) fragment);

            // Add the request to the RequestQueue
            queue.add(jsonRequest);
        }
    }

