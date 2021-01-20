package fr.ul.iutmetz.wmce.td1;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.EditText;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;


public class SignUp extends AppCompatActivity {

    private String regNom;
    private String regPrenom;
    private String regIdentifiants;
    private String regMdp;
    private String regAdNum;
    private String regAdVoie;
    private String regAdCP;
    private String regAdVille;
    private String regAdPays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_form);
    }

    public void registerUser(View view) {
        String nom = ((EditText) findViewById(R.id.regNom)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.regPrenom)).getText().toString();
        String identifiant = ((EditText) findViewById(R.id.regIdentifiants)).getText().toString();
        String mot_de_passe = ((EditText) findViewById(R.id.regMdp)).getText().toString();
        String adr_numero = ((EditText) findViewById(R.id.regAdNum)).getText().toString();
        String adr_voie = ((EditText) findViewById(R.id.regAdVoie)).getText().toString();
        String adr_code_postal = ((EditText) findViewById(R.id.regAdCP)).getText().toString();
        String adr_ville = ((EditText) findViewById(R.id.regAdVille)).getText().toString();
        String adr_pays = ((EditText) findViewById(R.id.regAdPays)).getText().toString();

        //if(tous les validateurs OK){
            //ajout dans la bdd
        //}


    }

    private Boolean validateNom() {
        String val = ((EditText) findViewById(R.id.regNom)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regNom)).setError("Ce champ ne peut pas être vide ");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regNom)).setError(null);
            return true;
        }
    }

    private Boolean validatePrenom() {
        String val = ((EditText) findViewById(R.id.regPrenom)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regPrenom)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regPrenom)).setError(null);
            return true;
        }
    }
    private Boolean validateIdentifiants() {
        String val = ((EditText) findViewById(R.id.regIdentifiants)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regIdentifiants)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regIdentifiants)).setError(null);
            return true;
        }
    }

    private Boolean validateMdp() {
        String val = ((EditText) findViewById(R.id.regMdp)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regMdp)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regMdp)).setError(null);
            return true;
        }
    }

    private Boolean validateAdNum() {
        String val = ((EditText) findViewById(R.id.regAdNum)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regAdNum)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regAdNum)).setError(null);
            return true;
        }
    }

    private Boolean validateAdVoie() {
        String val = ((EditText) findViewById(R.id.regAdVoie)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regAdVoie)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regAdVoie)).setError(null);
            return true;
        }
    }

    private Boolean validateAdCP() {
        String val = ((EditText) findViewById(R.id.regAdCP)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regAdCP)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regAdCP)).setError(null);
            return true;
        }
    }

    private Boolean validateAdVille() {
        String val = ((EditText) findViewById(R.id.regAdVille)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regAdVille)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regAdVille)).setError(null);
            return true;
        }
    }

    private Boolean validatePays() {
        String val = ((EditText) findViewById(R.id.regAdPays)).getText().toString();

        if (val.isEmpty()) {
            ((EditText) findViewById(R.id.regAdPays)).setError("");
            return false;
        }
        else {
            ((EditText) findViewById(R.id.regAdPays)).setError(null);
            return true;
        }
    }
}
