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

import fr.ul.iutmetz.wmce.td1.DAO.InscriptionDAO;
import fr.ul.iutmetz.wmce.td1.modele.Client;


public class SaisieInformationsClientActivity extends AppCompatActivity
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private String action;

    private EditText nom;
    private EditText prenom;
    private EditText identifiant;
    private EditText adr_num;
    private EditText adr_cp;
    private EditText adr_voie;
    private EditText adr_ville;
    private EditText adr_pays;
    private EditText mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_informations_client);

        if (savedInstanceState!=null){

        } else {
            // Action permet de savoir si l'on effectue une inscription ou une modification
            // d'un client
            this.action = this.getIntent().getStringExtra("action");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.nom = (EditText) this.findViewById(R.id.nom);
        this.prenom = (EditText) this.findViewById(R.id.prenom);
        this.identifiant = (EditText) this.findViewById(R.id.identifiant);
        this.adr_num = (EditText) this.findViewById(R.id.adresse_numero);
        this.adr_cp = (EditText) this.findViewById(R.id.adresse_code_postal);
        this.adr_voie = (EditText) this.findViewById(R.id.adresse_voie);
        this.adr_ville = (EditText) this.findViewById(R.id.adresse_ville);
        this.adr_pays = (EditText) this.findViewById(R.id.adresse_pays);
        this.mdp = (EditText) this.findViewById(R.id.mot_de_passe);

    }

    public void onClickValider(View v){
        if (this.action.equals("inscription")){
            String n = this.nom.getText().toString();
            String p = this.prenom.getText().toString();
            String id_c = this.identifiant.getText().toString();
            String motdp = this.mdp.getText().toString();
            String adrN = this.adr_num.getText().toString();
            String adrVoie = this.adr_voie.getText().toString();
            String adrCP = this.adr_cp.getText().toString();
            String adrVille = this.adr_ville.getText().toString();
            String adrP = this.adr_pays.getText().toString();

            Client client = new Client(-1, n, p, id_c, motdp, adrN, adrVoie, adrCP, adrVille, adrP);

            Intent intent = new Intent();

            // ADD CLIENT BDD
            InscriptionDAO inscDAO = new InscriptionDAO();
            inscDAO.insert(this, client);

            this.setResult(0, intent);
            this.finish();
        }
        // Sinon c'est une modification
        else {
            //TODO
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}