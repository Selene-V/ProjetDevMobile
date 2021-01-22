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

        this.lChampsARemplir = new TextView[]{this.RCS, this.coordonnees, this.coord};

        MentionsLegalesDAO mlDAO = new MentionsLegalesDAO();
        mlDAO.findAll(this);

    }

    public void majVueMentionLegales(JSONObject response) throws JSONException {
        JSONArray data = response.getJSONArray("data");
        for (int i = 0 ; i < data.length() ; i++){
            JSONObject o = data.getJSONObject(i);
            String mention = o.getString("mentions").replaceAll("<br>", "\n");
            String[] mentionSplit = mention.split("::");
            System.out.println("MENTION SPLIT : ");
            System.out.println(Arrays.toString(mentionSplit));
            System.out.println("MENTION SPLIT LENGTH: ");
            System.out.println(mentionSplit.length);
            if (mentionSplit.length>1){
                for (int j = 0 ; j < mentionSplit.length ; j++){
                    System.out.println("-------- SPLIT ------");
                    System.out.println(mentionSplit[j]);
                    lChampsARemplir[i].setText(mentionSplit[j]);
                    i++;
                }
            } else {
                lChampsARemplir[i].setText(mention);
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