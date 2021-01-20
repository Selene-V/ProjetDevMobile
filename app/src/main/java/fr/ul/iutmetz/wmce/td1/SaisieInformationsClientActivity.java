package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import fr.ul.iutmetz.wmce.td1.DAO.InscriptionDAO;
import fr.ul.iutmetz.wmce.td1.modele.Client;


public class SaisieInformationsClientActivity extends AppCompatActivity
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private String action;

    private EditText nom;
    private EditText prenom;
    private EditText identifiant;
    private EditText adrNum;
    private EditText adrCP;
    private EditText adrVoie;
    private EditText adrVille;
    private EditText adrPays;
    private EditText mdp;

    private TextView nomHelp;
    private TextView prenomHelp;
    private TextView identifiantHelp;
    private TextView adrNumHelp;
    private TextView adrCPHelp;
    private TextView adrVoieHelp;
    private TextView adrVilleHelp;
    private TextView adrPaysHelp;
    private TextView mdpHelp;

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
        this.adrNum = (EditText) this.findViewById(R.id.adresse_numero);
        this.adrCP = (EditText) this.findViewById(R.id.adresse_code_postal);
        this.adrVoie = (EditText) this.findViewById(R.id.adresse_voie);
        this.adrVille = (EditText) this.findViewById(R.id.adresse_ville);
        this.adrPays = (EditText) this.findViewById(R.id.adresse_pays);
        this.mdp = (EditText) this.findViewById(R.id.mot_de_passe);

        this.nomHelp = (TextView) this.findViewById(R.id.nom_help);
        this.prenomHelp = (TextView) this.findViewById(R.id.prenom_help);
        this.identifiantHelp = (TextView) this.findViewById(R.id.identifiant_help);
        this.adrNumHelp = (TextView) this.findViewById(R.id.adresse_numero_help);
        this.adrCPHelp = (TextView) this.findViewById(R.id.adresse_code_postal_help);
        this.adrVoieHelp = (TextView) this.findViewById(R.id.adresse_voie_help);
        this.adrVilleHelp = (TextView) this.findViewById(R.id.adresse_ville_help);
        this.adrPaysHelp = (TextView) this.findViewById(R.id.adresse_pays_help);
        this.mdpHelp = (TextView) this.findViewById(R.id.mot_de_passe_help);

    }

    public void onClickValider(View v){
        if (validationChamps()){
            if (this.action.equals("inscription")){
                String n = this.nom.getText().toString();
                String p = this.prenom.getText().toString();
                String id_c = this.identifiant.getText().toString();
                String motdp = this.mdp.getText().toString();
                String adrN = this.adrNum.getText().toString();
                String adrVoie = this.adrVoie.getText().toString();
                String adrCP = this.adrCP.getText().toString();
                String adrVille = this.adrVille.getText().toString();
                String adrP = this.adrPays.getText().toString();

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
    }

    public boolean isValid(String regex, EditText nomChamp, TextView nomChampHelp, String messageErreur){
        boolean valid = true;
        if (TextUtils.isEmpty(nomChamp.getText().toString())) {
            nomChampHelp.setText("Champ obligatoire !");
            nomChampHelp.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            System.out.println("regex : " + Pattern.matches(regex, nomChamp.getText().toString()));
            if (!Pattern.matches(regex, nomChamp.getText().toString())){
                nomChampHelp.setText(messageErreur);
                nomChampHelp.setVisibility(View.VISIBLE);
                valid = false;
            }
        }
        System.out.println(nomChamp.getText().toString() + " : " + valid);
        return valid;
    }

    public boolean validationChamps(){
        clearErrors();
        boolean validNom = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.nom, this.nomHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validPrenom = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.prenom, this.prenomHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrVoie = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrVoie, this.adrVoieHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrVille = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrVille, this.adrVilleHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrPays = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrPays, this.adrPaysHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validIdentifiant = isValid(".+@.+\\.[a-zA-Z]{2,}", this.identifiant, this.identifiantHelp, "Veuillez entrez une adresse email valide de la forme exemple@exemple.fr");
        boolean validMdp = isValid("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&.]{8,}", this.mdp, this.mdpHelp, "Minimum de huit caractères, au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial (@$!%*?&)");
        boolean validAdrCP = isValid("((0[1-9])|([1-8][0-9])|(9[0-8]))[0-9]{3}", this.adrCP, this.adrCPHelp, "Ne peut commencer par 00 ou 99 et doit comporter 5 chiffres.");
        boolean validAdrNum = isValid("(([1-9]))[0-9]{0,2}", this.adrNum, this.adrNumHelp, "Ne peut commencer par 0 et doit comporter entre 1 et 3 chiffres.");

        return (validNom && validPrenom && validAdrVoie && validAdrVille && validAdrPays && validIdentifiant && validMdp && validAdrCP && validAdrNum);
    }

    public void clearErrors(){
        this.nomHelp.setVisibility(View.INVISIBLE);
        this.prenomHelp.setVisibility(View.INVISIBLE);
        this.adrVoieHelp.setVisibility(View.INVISIBLE);
        this.adrVilleHelp.setVisibility(View.INVISIBLE);
        this.adrPaysHelp.setVisibility(View.INVISIBLE);
        this.identifiantHelp.setVisibility(View.INVISIBLE);
        this.mdpHelp.setVisibility(View.INVISIBLE);
        this.adrCPHelp.setVisibility(View.INVISIBLE);
        this.adrNumHelp.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}