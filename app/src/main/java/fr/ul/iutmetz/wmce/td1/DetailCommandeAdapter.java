package fr.ul.iutmetz.wmce.td1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.LigneCommandeDetaillee;

public class DetailCommandeAdapter extends ArrayAdapter<LigneCommandeDetaillee> {

    private ArrayList<LigneCommandeDetaillee> listeDetailCommande;
    private ArrayList<Bitmap> listeImagesCommande;

    public DetailCommandeAdapter(Context context, ArrayList<LigneCommandeDetaillee> liste, ArrayList<Bitmap> listeImages)
    {
        super(context, 0, liste);
        this.listeDetailCommande = liste;
        this.listeImagesCommande = listeImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_detail_commande, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.img_produit);
        img.setImageBitmap(this.listeImagesCommande.get(position));

        TextView tv1 = convertView.findViewById(R.id.taille_commande);
        tv1.setText(this.listeDetailCommande.get(position).getTaille());

        TextView tv2 = convertView.findViewById(R.id.quantite_commande);
        tv2.setText(Integer.toString(this.listeDetailCommande.get(position).getQuantite()));

        TextView tv3 = convertView.findViewById(R.id.tarif_commande);
        tv3.setText(this.listeDetailCommande.get(position).getTarifLigne() + " €");

        return convertView;
    }
}
