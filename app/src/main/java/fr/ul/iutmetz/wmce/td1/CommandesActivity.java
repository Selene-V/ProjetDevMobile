package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.sql.Date;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Commande;

public class CommandesActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener{

    private ArrayList<Commande> listeCommandes;
    SessionManager sessionManager;

    private ListView lvCommandes;
    private CommandesAdapter adapteur;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_commandes);
        this.listeCommandes = new ArrayList<>();
        sessionManager = new SessionManager(this);
//        sessionManager.checkIsLogin();

        CommandeDAO comDAO = new CommandeDAO();
//        comDAO.findAllCommandsByClient(this, this.sessionManager.getIdUser());
        comDAO.findAllCommandsByClient(this, 1);

        this.adapteur = new CommandesAdapter(
                this,
                this.listeCommandes
        );

    }

    protected void onStart() {
        super.onStart();

        this.lvCommandes = this.findViewById(R.id.commandes_liste);

        this.lvCommandes.setAdapter(adapteur);
        this.lvCommandes.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CommandesActivity.this, MainActivity.class);
        intent.putExtra("id_commande", this.listeCommandes.get(position).getId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this, R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");
            System.out.println("-------------- DATA --------------");
            System.out.println(data);
            for (int i = 0 ; i < response.length() ; i++){
                JSONObject commande = data.getJSONObject(i);

                int idCommande = commande.getInt("id_commande");

                String date = commande.getString("date_commande");
                int idClient = commande.getInt("id_client");

                Commande com = new Commande(idCommande, Date.valueOf(date), idClient);
                this.listeCommandes.add(com);
            }
            this.adapteur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}