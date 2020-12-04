package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Produit;
import utils.Utils;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, ActiviteEnAttenteImage {

    private ArrayList<Produit> modele = new ArrayList<>();
    ;
    private int noPullCourant;
    private boolean agrandie;
    private int idCategorie;
    private double totalPanier;
    private int idBoutonRadio;
    private boolean isError;
    private String errorCourante;
    private ArrayList listeImagesProduits;


    private Utils utils = new Utils();

    // Differents elements qui composent la page
    private ImageView image_pull;
    private TextView titre;
    private TextView description;
    private TextView prix;
    private ImageButton panier;
    private Button bPrecedent;
    private Button bSuivant;
    private ImageView image_pull_grande;
    private Spinner scolor;
    private Spinner staille;
    private TextView euro;
    private ImageButton imageAdd;
    private TextView texteAdd;
    private TextView texteCancel;
    private ImageButton imageCancel;
    private TextView errorSpinner;

    private static final int MAIN_SAISIE_NOUVEAU_PULL = 2;
    public static final int RETOUR = 0;
    public static final int ANNULER = -1;

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("noPull", this.noPullCourant);
        outState.putSerializable("listePull", this.modele);
        outState.putBoolean("agrandie", this.agrandie);
        outState.putBoolean("is_error", this.isError);
        outState.putDouble("total_panier", utils.arrondir(this.totalPanier));
        outState.putString("error_courante", this.errorCourante);
        outState.putInt("id_bouton_radio", this.idBoutonRadio);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState!=null){
            this.noPullCourant = savedInstanceState.getInt("noPull");
            this.modele = (ArrayList<Produit>) savedInstanceState.getSerializable("listePull");
            this.agrandie = savedInstanceState.getBoolean("agrandie");
            this.isError = savedInstanceState.getBoolean("is_error");
            this.totalPanier = utils.arrondir(savedInstanceState.getDouble("total_panier"));
            this.errorCourante = savedInstanceState.getString("error_courante");
            this.idBoutonRadio = savedInstanceState.getInt("id_bouton_radio");

        }else {

            //Création des 5 pulls dans la liste
            Produit p0 = new Produit(0, "A Noël c\'est moi qui tient les rennes", "Un pull de rennes qui ravira les petits et les grands. Tricoté par d\'authentiques grand-meres Australiennes, en laine 100% coton naturel issue d\'une filière agriculture durable certifiée ISO-2560.", "19.56", "renne");
            Produit p1 = new Produit(0, "A Noël c\'est moi qui tient les chiens", "Un pull de chien qui ravira les petits et les grands. Tricoté par d\'authentiques grand-meres Australiennes, en laine 100% coton naturel issue d\'une filière agriculture durable certifiée ISO-2560.", "25", "chien");
            Produit p2 = new Produit(0, "A Noël c\'est nous qui tennons les rennes", "Un pull doublement plus moche qui ravira les petits et les grands. Tricoté par d\'authentiques grand-meres Australiennes, en laine 100% coton naturel issue d\'une filière agriculture durable certifiée ISO-2560.", "30","double_pull");
            Produit p3 = new Produit(0, "A Noël c\'est moi qui tient les lamas", "Un pull de lama qui ravira les petits et les grands. Tricoté par d\'authentiques grand-meres Australiennes, en laine 100% coton naturel issue d\'une filière agriculture durable certifiée ISO-2560.", "2", "lama");
            Produit p4 = new Produit(0, "A Noël c\'est moi qui tient les sapins", "Un pull de sapin qui ravira les petits et les grands. Tricoté par d\'authentiques grand-meres Australiennes, en laine 100% coton naturel issue d\'une filière agriculture durable certifiée ISO-2560.", "10.3", "sapin");

            Produit b0 = new Produit(1, "Bonnet en laine", "Ceci est un magnifique bonnet en laine qui tient extrememnt chaud !", "19.5", "bonnet_renne");
            Produit b1 = new Produit(1, "Bonnet en laine plus cher", "Ce bonnet est exactement le même que celui vu précédemment mais en plus cher ! Merci d'acheter celui la !", "25", "bonnet_renne");

            Produit c0 = new Produit(2, "Dabsquette", "Voici une casquette très laide et qui ne tient absolument pas chaud mais il y a un père noël dessus.", "19.5", "casquette_dab");
            Produit c1 = new Produit(2, "Joli renne", "Grâce à cette casquette vous pourrez vous faire passer pour un magnifique renne et tout le monde n'y verra que du feu !", "25", "casquette_renne");


            modele.add(p0);
            modele.add(p1);
            modele.add(b0);
            modele.add(c1);
            modele.add(p2);
            modele.add(p3);
            modele.add(c0);
            modele.add(p4);
            modele.add(b1);

            this.totalPanier = utils.arrondir(0.00);

            this.noPullCourant = 0;

            this.agrandie = false;

            this.isError = false;

            this.idBoutonRadio = -1;

            this.errorCourante = "Erreur";

            // Recuperation id categorie
            if (this.getIntent().getIntExtra("id_categ", -1)!=-1){
                this.idCategorie = this.getIntent().getIntExtra("id_categ", -1);
                ArrayList<Produit> temp = new ArrayList<>();

                for (int i = 0 ; i < this.modele.size() ; i++) {
                    if (this.modele.get(i).getIdCategorie() == idCategorie){
                        temp.add(this.modele.get(i));
                    }
                }
                this.modele = temp;
            }

            if (this.getIntent().getIntExtra("id_bouton_radio", -1)!=-1){
                this.idBoutonRadio = this.getIntent().getIntExtra("id_bouton_radio", -1);
            }
        }
        this.listeImagesProduits = new ArrayList<>();
        for (int i = 0 ; i < this.modele.size() ; i++){
            this.listeImagesProduits.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.modele.get(i).getVisuel() + ".jpg", String.valueOf(i));
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        this.image_pull = (ImageView) this.findViewById(R.id.image_pull);
        this.titre = (TextView) this.findViewById(R.id.titre);
        this.description = (TextView) this.findViewById(R.id.desc);
        this.prix = (TextView) this.findViewById(R.id.prix_pull);
        this.bPrecedent = (Button) this.findViewById(R.id.bPrecedent);
        this.bSuivant = (Button) this.findViewById(R.id.bSuivant);
        this.image_pull_grande = (ImageView) this.findViewById(R.id.image_pull_grande);
        this.scolor = (Spinner) this.findViewById(R.id.color_spinner);
        this.staille = (Spinner) this.findViewById(R.id.taille_spinner);
        this.euro = (TextView) this.findViewById(R.id.euro_pull);
        this.panier = (ImageButton) this.findViewById(R.id.image_panier);
        this.imageAdd = (ImageButton) this.findViewById(R.id.image_add);
        this.texteAdd = (TextView) this.findViewById(R.id.texte_add);
        this.texteCancel = (TextView) this.findViewById(R.id.texte_cancel);
        this.imageCancel = (ImageButton) this.findViewById(R.id.image_cancel);
        this.errorSpinner = (TextView) this.findViewById(R.id.error_spinner);

        // Changements
        changement();
        verifbPrecedent();
        verifbSuivant();

        if (this.agrandie){
            this.visibilite(View.INVISIBLE);

            this.image_pull_grande.setVisibility(View.VISIBLE);
        }

        if (this.idBoutonRadio==0){
            System.out.println("------ BOUTON RADIO 0 (VENTE) ------");
            System.out.println(idBoutonRadio);
            this.visibilityCatalogue(View.VISIBLE);
        } else {
            System.out.println("------ BOUTON RADIO 1 (CATALOGUE) ------");
            System.out.println(idBoutonRadio);
            this.visibilityCatalogue(View.INVISIBLE);
        }

        if (this.isError && !this.agrandie){
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void visibilityCatalogue(int visibility){
        this.panier.setVisibility(visibility);
        this.texteCancel.setVisibility(visibility);
        this.imageCancel.setVisibility(visibility);
    }

    /**
     * clic sur le bouton pull suivant
     * @param v la vue cliquée
     */
    public void onClickSuivant(View v){
        if (this.noPullCourant + 1 < modele.size()) {
            this.noPullCourant += 1;
            this.changement();
        }
        this.isError = false;
        this.errorSpinner.setVisibility(View.INVISIBLE);
        verifbSuivant();
    }

    /**
     * clic sur le bouton pull precedent
     * @param v la vue cliquée
     */
    public void onClickPrecedent(View v){
        if (this.noPullCourant - 1 >= 0) {
            this.noPullCourant -= 1;
            this.changement();
        }
        this.isError = false;
        this.errorSpinner.setVisibility(View.INVISIBLE);
        verifbPrecedent();
    }

    public void changement(){
        // Changement img
        if (this.listeImagesProduits.get(noPullCourant) != null){
            this.image_pull.setImageBitmap((Bitmap) this.listeImagesProduits.get(noPullCourant));
            this.image_pull_grande.setImageBitmap((Bitmap) this.listeImagesProduits.get(noPullCourant));
            System.out.println("--------- liste IMG Produits ---------");
            System.out.println(this.listeImagesProduits);
        } else {
            int id = this.getResources().getIdentifier(
                    this.modele.get(noPullCourant).getVisuel(),
                    "drawable",
                    this.getPackageName());
            this.image_pull.setImageResource(id);
            this.image_pull_grande.setImageResource(id);
        }

        // Changement titre
        this.titre.setText(this.modele.get(noPullCourant).getTitre());
        // Changement desc
        this.description.setText(this.modele.get(noPullCourant).getDescription());
        // Changement prix
        this.prix.setText(this.modele.get(noPullCourant).getPrix());
        // Changement error
        this.errorSpinner.setText(this.errorCourante);
    }

    public void verifbPrecedent(){
        this.bPrecedent.setEnabled(!(this.noPullCourant == 0));
        if (noPullCourant != modele.size()-1){
            this.bSuivant.setEnabled(true);
        }
    }

    public void verifbSuivant(){
        this.bSuivant.setEnabled(!(this.noPullCourant == modele.size()-1));
        if (noPullCourant != 0){
            this.bPrecedent.setEnabled(true);
        }
    }

    public void onClickPanier(View v){
        if (!(this.staille.getSelectedItem().toString().equals("Choix de la taille"))){
            if (!(this.scolor.getSelectedItem().toString().equals("Choix du coloris"))) {
                this.errorSpinner.setVisibility(View.INVISIBLE);
                this.isError = false;
                Toast.makeText(this,
                        String.format(getString(R.string.ajout_panier), this.noPullCourant + 1),
                        Toast.LENGTH_LONG).show();
                double prix = Double.parseDouble(this.modele.get(noPullCourant).getPrix());
                this.totalPanier += utils.arrondir(prix);
            }else {
                this.isError = true;
                this.errorCourante = "Vous devez selectionner un coloris !";
                this.errorSpinner.setText(this.errorCourante);
                this.errorSpinner.setVisibility(View.VISIBLE);
            }
        } else {
            this.isError = true;
            this.errorCourante = "Vous devez selectionner une taille !";
            this.errorSpinner.setText(this.errorCourante);
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void onClickZoom(View v){
        this.agrandie = true;
        this.visibilite(View.INVISIBLE);
        this.image_pull_grande.setVisibility(View.VISIBLE);
        this.errorSpinner.setVisibility(View.INVISIBLE);
    }


    public void onClickDezoom(View v){
        this.agrandie = false;
        this.visibilite(View.VISIBLE);
        this.image_pull_grande.setVisibility(View.INVISIBLE);
        if (this.isError){
            this.errorSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void visibilite(int visibility){
        if (idBoutonRadio==0){
            this.panier.setVisibility(visibility);
            this.texteCancel.setVisibility(visibility);
            this.imageCancel.setVisibility(visibility);
        }
        this.image_pull.setVisibility(visibility);
        this.titre.setVisibility(visibility);
        this.description.setVisibility(visibility);
        this.prix.setVisibility(visibility);
        this.bSuivant.setVisibility(visibility);
        this.bPrecedent.setVisibility(visibility);
        this.scolor.setVisibility(visibility);
        this.staille.setVisibility(visibility);
        this.euro.setVisibility(visibility);
        this.texteAdd.setVisibility(visibility);
        this.imageAdd.setVisibility(visibility);
    }

//    Intent est la classe que vous aurez besoin pour faire le lien entre deux activités,
//    les paramètres sont  l'actitivé actuelle et l'activité que vous souhaitez appeler,
//    une fois que le objet est crée, il faut juste appeler au méthode "startActivity"
    public void onClickAddPull(View v){
        Intent intent = new Intent(MainActivity.this, SaisieNouveauPullActivity.class);
        intent.putExtra("id_categ", this.idCategorie);
        startActivityForResult(intent, MAIN_SAISIE_NOUVEAU_PULL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            if (requestCode == MAIN_SAISIE_NOUVEAU_PULL){
                Bundle extras = data.getExtras();
                Produit p = (Produit) extras.get("nouveau_pull");
                this.modele.add(p);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id ==android.R.id.home){
            Intent intent = new Intent();
            //intent.putExtra("total_panier", Math.round(this.totalPanier*100.0)/100.0);
            intent.putExtra("total_panier", utils.arrondir(this.totalPanier));

            this.setResult(RETOUR, intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickRetour(View v){
        Intent intent = new Intent();
        //intent.putExtra("total_panier", Math.round(this.totalPanier*100.0)/100.0);
        intent.putExtra("total_panier", utils.arrondir(this.totalPanier));

        this.setResult(RETOUR, intent);
        this.finish();
    }

    @Override
    public void onBackPressed(){
        this.onClickRetour(null);
    }

    public void onClickAnnuler(View v){
        AnnulerAlerte alerte = new AnnulerAlerte();
        alerte.show(getSupportFragmentManager(), "suppression");
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            this.setResult(ANNULER);
            this.finish();
        }
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesProduits.set(idx, img);
            if (idx==this.noPullCourant) {
                changement();
            }
        }
    }
}