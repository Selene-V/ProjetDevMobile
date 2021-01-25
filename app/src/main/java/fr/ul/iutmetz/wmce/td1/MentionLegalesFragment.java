package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.MentionsLegalesDAO;

public class MentionLegalesFragment extends Fragment
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

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        this.root = inflater.inflate(R.layout.fragment_mention_legales, container, false);

        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.RCS = this.root.findViewById(R.id.RCS);
        this.coordonnees = this.root.findViewById(R.id.coordonnees);
        this.coord = this.root.findViewById(R.id.coord);
        this.conditionsGenerales = this.root.findViewById(R.id.conditions_generales);
        this.CGU = this.root.findViewById(R.id.CGU);
        this.servicesFournis = this.root.findViewById(R.id.services_fournis);
        this.sfDesc = this.root.findViewById(R.id.sf_desc);
        this.proprieteIntellectuelle = this.root.findViewById(R.id.propriete_intellectuelle);
        this.proprieteIntellectuelleDesc = this.root.findViewById(R.id.propriete_intellectuelle_desc);
        this.CNIL = this.root.findViewById(R.id.CNIL);
        this.CNILDesc = this.root.findViewById(R.id.CNIL_desc);
        this.litiges = this.root.findViewById(R.id.litiges);
        this.litigesDesc = this.root.findViewById(R.id.litiges_desc);
        this.donneesPerso = this.root.findViewById(R.id.donnees_perso);
        this.donneesPersoDesc = this.root.findViewById(R.id.donnees_perso_desc);

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
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
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