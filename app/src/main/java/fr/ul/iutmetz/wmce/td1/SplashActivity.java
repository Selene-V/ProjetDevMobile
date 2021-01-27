package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.AdapterView;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.CategorieDAO;
import fr.ul.iutmetz.wmce.td1.DAO.MagasinDAO;
import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import fr.ul.iutmetz.wmce.td1.modele.Magasin;

public class SplashActivity extends AppCompatActivity{

    private Categorie[] listeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final int SPLASH_TIME_OUT = 3000;
        getSupportActionBar().hide();

       /* this.listeCategories = new ArrayList<>();
        new Handler().postDelayed(
                () -> CategorieDAO.findAll(this),
                SPLASH_TIME_OUT
        );*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2*1000);
    }

   /* @Override
    public void onResponse(JSONArray response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }*/
}
// 1 User OK
// jeandupont@gmail.com
// Azerty1?