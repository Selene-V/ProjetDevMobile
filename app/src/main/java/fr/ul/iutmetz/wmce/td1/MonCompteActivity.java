package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;

public class MonCompteActivity extends AppCompatActivity
    implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    SessionManager sessionManager;

    private TextView monIdentifiant;
    private TextView monNom;
    private TextView monPrenom;
    private TextView monAdresse;

    private TextView numCom;
    private TextView dateCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);


        if (savedInstanceState!=null){

        } else {
            sessionManager = new SessionManager(this);
            sessionManager.checkIsLogin();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.monIdentifiant = this.findViewById(R.id.mon_identifiant);
        this.monNom = this.findViewById(R.id.mon_nom);
        this.monPrenom = this.findViewById(R.id.mon_prenom);
        this.monAdresse = this.findViewById(R.id.mon_adresse);

        this.numCom = this.findViewById(R.id.id_commande);
        this.dateCom = this.findViewById(R.id.date);

        int idClient = this.sessionManager.getIdUser();
        // Recherche des infos personnelles du user connecté
        UserDAO userDAO = new UserDAO();
        userDAO.findOneById(this, idClient);

        // Recherche de la dernière commande du user connecté
        CommandeDAO comDAO = new CommandeDAO();
        comDAO.findLastCommandByClient(this, idClient);
    }

    public void onClickVoirCommande(View v){
        //TODO
    }

    public void onClickModifier(View v){
        Intent intent = new Intent(MonCompteActivity.this, SaisieInformationsClientActivity.class);
        intent.putExtra("action", "modification");
        startActivityForResult(intent, 0);
    }

    public void majVueInfos(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("data");
        String requete = response.getString("requete");
        switch (requete) {
            case "rechercheID":
                this.monIdentifiant.setText(data.getString("identifiant"));
                this.monNom.setText(data.getString("nom"));
                this.monPrenom.setText(data.getString("prenom"));
                String adresse = data.getString("adr_numero") + ", " + data.getString("adr_voie") + "\n"
                        + data.getString("adr_code_postal") + " " + data.getString("adr_ville") + "\n"
                        + data.getString("adr_pays");
                this.monAdresse.setText(adresse);
                break;
            case "rechercheCommande":
                System.out.println("------------------ DATA ---------------");
                System.out.println(data);
                this.numCom.setText(data.getString("id_commande"));
                String[] dateSplit = data.getString("date_commande").split("-");
                String date = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
                this.dateCom.setText(date);
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this, R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            majVueInfos(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}