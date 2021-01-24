package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class PanierAdapter extends ArrayAdapter<Produit> {

    private ArrayList<Produit> listeProduits;
    private ArrayList<Bitmap> listeImagesProduits;

    public PanierAdapter(Context context, ArrayList<Produit> liste, ArrayList<Bitmap> listeImages) {
        super(context, 0, liste);
        this.listeProduits = liste;
        this.listeImagesProduits = listeImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_categories, parent, false);
        }
        TextView tv = convertView.findViewById(R.id.ilc_titre);
        tv.setText(this.listeProduits.get(position).getTitre());

        ImageView img = convertView.findViewById(R.id.ilc_visuel);
        if (this.listeImagesProduits.get(position) != null) {
            img.setImageBitmap(this.listeImagesProduits.get(position));
        } else {
            int id = getContext().getResources().getIdentifier(
                    this.listeProduits.get(position).getVisuel(),
                    "drawable",
                    getContext().getPackageName()
            );
            img.setImageResource(id);
        }

        return convertView;
    }
}