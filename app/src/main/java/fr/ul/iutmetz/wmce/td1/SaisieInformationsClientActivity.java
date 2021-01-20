package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class SaisieInformationsClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_informations_client);
    }

    public void onClickValider(View v){
//        Importer le new client !
//
//        String t = this.titreNouveauPull.getText().toString();
//        String d = this.descriptionNouveauPull.getText().toString();
//        String p = this.prixNouveauPull.getText().toString();
//
//        Produit pull = new Produit(-1 ,t, d, p, "imgdefaut", idCategorie);
//
//        Client client = new Client();
//        Intent intent = new Intent();
//        intent.putExtra("client", client);
//
//        this.setResult(0, intent);
        this.finish();
    }
}