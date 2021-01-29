package fr.ul.iutmetz.wmce.td1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.modele.Taille;
import utils.Triplet;

public class PanierAdapter extends ArrayAdapter<Triplet<Produit, Taille, Integer>> {

    private ArrayList<Triplet<Produit, Taille, Integer>> panierContent;
    private ArrayList<Bitmap> listeImagesProduits;
    private float prixTotal;

    public PanierAdapter(Context context, ArrayList<Triplet<Produit, Taille, Integer>> panierContent, ArrayList<Bitmap> listeImages) {
        super(context,0,panierContent);
        this.panierContent = panierContent;
        this.listeImagesProduits = listeImages;
        this.prixTotal = prixTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_panier, parent, false);
        }
        TextView tvTitre = convertView.findViewById(R.id.Titre_produit);
        tvTitre.setText(this.panierContent.get(position).getProduit().getTitre());

        TextView tvTaille = convertView.findViewById(R.id.taille_panier);
        tvTaille.setText(this.panierContent.get(position).getTaille().getLabel());

        TextView tvQuantite = convertView.findViewById(R.id.quantite_panier);
        tvQuantite.setText(this.panierContent.get(position).getQuantite().toString());

        TextView tvPrix = convertView.findViewById(R.id.tarif_panier);
        float prixTotalProduit = Float.parseFloat(this.panierContent.get(position).getProduit().getPrix()) * this.panierContent.get(position).getQuantite();
        tvPrix.setText(String.valueOf(prixTotalProduit));

        ImageView img = convertView.findViewById(R.id.img_produit_panier);
        if (this.listeImagesProduits.get(position) != null) {
            img.setImageBitmap(this.listeImagesProduits.get(position));
        } else {
            int id = getContext().getResources().getIdentifier(
                    this.panierContent.get(position).getProduit().getVisuel(),
                    "drawable",
                    getContext().getPackageName()
            );
            img.setImageResource(id);
        }

        return convertView;
    }
    //item liste panier + fragment panier
}