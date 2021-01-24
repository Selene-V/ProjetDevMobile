package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import fr.ul.iutmetz.wmce.td1.DAO.MentionsLegalesDAO;

public class MentionLegalesActivity extends AppCompatActivity
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private TextView RCS;
    private TextView coordonnees;
    private TextView coord;
    private TextView conditionsGenerales;
    private TextView CGU;
    private TextView servicesFournis;
    private TextView sfDesc;
    private TextView proprieteIntellectuelle;
    private TextView proprieteIntellectuelleDesc;
    private TextView CNIL;
    private TextView CNILDesc;
    private TextView litiges;
    private TextView litigesDesc;
    private TextView donneesPerso;
    private TextView donneesPersoDesc;

    private TextView[] lChampsARemplir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mention_legales);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.RCS = this.findViewById(R.id.RCS);
        this.coordonnees = this.findViewById(R.id.coordonnees);
        this.coord = this.findViewById(R.id.coord);
        this.conditionsGenerales = this.findViewById(R.id.conditions_generales);
        this.CGU = this.findViewById(R.id.CGU);
        this.servicesFournis = this.findViewById(R.id.services_fournis);
        this.sfDesc = this.findViewById(R.id.sf_desc);
        this.proprieteIntellectuelle = this.findViewById(R.id.propriete_intellectuelle);
        this.proprieteIntellectuelleDesc = this.findViewById(R.id.propriete_intellectuelle_desc);
        this.CNIL = this.findViewById(R.id.CNIL);
        this.CNILDesc = this.findViewById(R.id.CNIL_desc);
        this.litiges = this.findViewById(R.id.litiges);
        this.litigesDesc = this.findViewById(R.id.litiges_desc);
        this.donneesPerso = this.findViewById(R.id.donnees_perso);
        this.donneesPersoDesc = this.findViewById(R.id.donnees_perso_desc);

        this.lChampsARemplir = new TextView[]{
                this.RCS,
                this.coordonnees,
                this.coord,
                this.conditionsGenerales,
                this.CGU,
                this.servicesFournis,
                this.sfDesc,
                this.proprieteIntellectuelle,
                this.proprieteIntellectuelleDesc,
                this.CNIL,
                this.CNILDesc,
                this.litiges,
                this.litigesDesc,
                this.donneesPerso,
                this.donneesPersoDesc
        };

        MentionsLegalesDAO mlDAO = new MentionsLegalesDAO();
        mlDAO.findAll(this);

    }

    public void majVueMentionLegales(JSONObject response) throws JSONException {
        JSONArray data = response.getJSONArray("data");
        int iTabMaj = 0;
        for (int idb = 0 ; idb < data.length() ; idb++){
            JSONObject o = data.getJSONObject(idb);
            String mention = o.getString("mentions").replaceAll("<br>", "\n");
            String[] mentionSplit = mention.split("::");
            if (mentionSplit.length>1){
                for (int j = 0 ; j < mentionSplit.length ; j++){
                    lChampsARemplir[iTabMaj].setText(mentionSplit[j]);
                    iTabMaj++;
                }
            } else {
                lChampsARemplir[iTabMaj].setText(mention);
                iTabMaj++;
            }
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
            this.majVueMentionLegales(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}