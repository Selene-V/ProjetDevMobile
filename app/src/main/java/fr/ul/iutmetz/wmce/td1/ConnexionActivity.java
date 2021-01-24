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

import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;

public class ConnexionActivity extends AppCompatActivity
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private EditText identifiant;
    private EditText motDePasse;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        if (savedInstanceState!=null){

        } else {
            sessionManager = new SessionManager(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.identifiant = (EditText) this.findViewById(R.id.identifiant);
        this.motDePasse = (EditText) this.findViewById(R.id.mot_de_passe);


    }

    public void onClickConnexion(View v){
        UserDAO conDAO = new UserDAO();
        conDAO.findOneByIdentifiant(this, identifiant.getText().toString());
    }


    public void onClickInscription(View v){
        Intent intent = new Intent(ConnexionActivity.this, SaisieInformationsClientActivity.class);
        intent.putExtra("action", "inscription");
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
            if (!response.getBoolean("res")) {
                Toast.makeText(this, R.string.con_erreur_bdd, Toast.LENGTH_LONG).show();
            } else {
                JSONObject data = response.getJSONObject("data");

                System.out.println(data.getString("mot_de_passe"));
                System.out.println(motDePasse.getText().toString());

                if (motDePasse.getText().toString().equals(data.getString("mot_de_passe"))){
                    System.out.println(data.getInt("id_client"));

                    Toast.makeText(getApplicationContext(), "Vous allez être redirigé...",Toast.LENGTH_LONG).show();
                    sessionManager.createSession(data.getInt("id_client"));

//                    Intent intent = new Intent(ConnexionActivity.this, CategoriesActivity.class);
                    Intent intent = new Intent(ConnexionActivity.this, MonCompteActivity.class);
                    startActivityForResult(intent, 0);
                }else {
                    //wrong password
                    Toast.makeText(getApplicationContext(), "Identifiants incorrectes !", Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}