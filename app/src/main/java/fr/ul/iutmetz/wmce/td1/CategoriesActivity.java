package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import utils.Utils;

public class CategoriesActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener, ActiviteEnAttenteImage {

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private ArrayList<Categorie> listeCategories = new ArrayList<>();
    private double totalPanier;
    private Utils utils = new Utils();

    private ArrayList listeImagesCategories;
    private CategoriesAdapter adaptateur;

    private ListView lvCategories;
    private TextView prixTotal;
    private RadioButton vente;

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("listeCategorie", this.listeCategories);
        outState.putDouble("total_panier", utils.arrondir(this.totalPanier));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_categories);

        if (savedInstanceState!=null){
            this.listeCategories = (ArrayList<Categorie>) savedInstanceState.getSerializable("listeCategorie");
            this.totalPanier = utils.arrondir(savedInstanceState.getDouble("total_panier"));
        } else {
            Categorie c0 = new Categorie(0, "Pulls", "renne");
            Categorie c1 = new Categorie(1, "Bonnets", "bonnet_renne");
            Categorie c2 = new Categorie(2, "Casquettes", "casquette_dab");

            listeCategories.add(c0);
            listeCategories.add(c1);
            listeCategories.add(c2);

            this.totalPanier = utils.arrondir(0.00);


        }
        this.listeImagesCategories = new ArrayList<>();
        for (int i = 0 ; i < this.listeCategories.size() ; i++){
            this.listeImagesCategories.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.listeCategories.get(i).getVisuel() + ".jpg", String.valueOf(this.listeCategories.get(i).getId()));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.lvCategories = this.findViewById(R.id.ca_liste);
        this.prixTotal = (TextView) this.findViewById(R.id.total_panier_nombre);
        this.vente = (RadioButton) this.findViewById(R.id.vente);


        this.adaptateur = new CategoriesAdapter(
                this,
                this.listeCategories,
                this.listeImagesCategories
        );

        this.lvCategories.setAdapter(adaptateur);
        this.lvCategories.setOnItemClickListener(this);
        this.prixTotal.setText(" " + utils.arrondir(this.totalPanier));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
        intent.putExtra("id_categ", this.listeCategories.get(position).getId());
        if (this.vente.isChecked()){
            intent.putExtra("id_bouton_radio", MAIN_VENTE);
            startActivityForResult(intent, MAIN_VENTE);
        } else {
            intent.putExtra("id_bouton_radio", MAIN_CATALOGUE);
            startActivityForResult(intent, MAIN_CATALOGUE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            if (requestCode == MAIN_VENTE){
                Bundle extras = data.getExtras();
                this.totalPanier += utils.arrondir((double) extras.get("total_panier"));
                this.prixTotal.setText(" " + utils.arrondir(this.totalPanier));
            } else if (requestCode == MAIN_CATALOGUE){
                // on ne fait rien si on revient du mode catalogue
            }
        } // on ne fait rien en cas d'annulation
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesCategories.set(idx, img);
            this.adaptateur.notifyDataSetChanged();
        }
    }

}