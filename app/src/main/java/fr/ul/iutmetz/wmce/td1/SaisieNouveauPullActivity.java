package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class SaisieNouveauPullActivity extends AppCompatActivity {
    private int idCategorie;

    private EditText titreNouveauPull;
    private EditText descriptionNouveauPull;
    private EditText prixNouveauPull;


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_nouveau_pull);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState!=null){

        }else {
            this.idCategorie = -1;

            if (this.getIntent().getIntExtra("id_categ", -1)!=-1){
                this.idCategorie = this.getIntent().getIntExtra("id_categ", -1);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.titreNouveauPull = (EditText) this.findViewById(R.id.titre_nouveau_pull);
        this.descriptionNouveauPull = (EditText) this.findViewById(R.id.description_nouveau_pull);
        this.prixNouveauPull = (EditText) this.findViewById(R.id.prix_nouveau_pull);
    }

    public void onClickValider(View v){
        String t = this.titreNouveauPull.getText().toString();
        String d = this.descriptionNouveauPull.getText().toString();
        String p = this.prixNouveauPull.getText().toString();

        Produit pull = new Produit(idCategorie ,t, d, p, "imgdefaut");

        Intent intent = new Intent();
        intent.putExtra("nouveau_pull", pull);

        this.setResult(0, intent);
        this.finish();
    }

    @Override
    public void onBackPressed(){
        this.setResult(-1);
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.setResult(-1);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}