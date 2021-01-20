package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.ConnexionDAO;

public class ConnexionActivity extends AppCompatActivity
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private EditText identifiant;
    private EditText motDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        if (savedInstanceState!=null){

        } else {

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.identifiant = (EditText) this.findViewById(R.id.identifiant);
        this.motDePasse = (EditText) this.findViewById(R.id.mot_de_passe);


    }

    public void onClickConnexion(View v){
        ConnexionDAO conDAO = new ConnexionDAO();
        conDAO.findOneById(this, identifiant.getText().toString());
    }


    public void onClickInscription(View v){
        Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this, R.string.con_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            //correct id
            // Verification mdp :
            System.out.println(response.getString("mot_de_passe"));
            System.out.println(motDePasse.getText().toString());

            if (motDePasse.getText().toString().equals(response.getString("mot_de_passe"))){

                Toast.makeText(getApplicationContext(), "Vous allez être redirigé...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConnexionActivity.this, CategoriesActivity.class);
                startActivityForResult(intent, 0);
            }else {
                //wrong password
                Toast.makeText(getApplicationContext(), "Identifiants incorrectes !", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}