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
import fr.ul.iutmetz.wmce.td1.modele.Favoris;

public class FavorisAdapter extends ArrayAdapter<Favoris> {

    private ArrayList<Favoris> listeFavoris;
    private ArrayList<Bitmap> listeImagesFavoris;


    public FavorisAdapter(Context context, ArrayList<Favoris> liste, ArrayList<Bitmap> listeImages)
    {
        super(context, 0, liste);
        this.listeFavoris = liste;
        this.listeImagesFavoris = listeImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_favoris, parent, false);
        }

        ImageView img = convertView.findViewById(R.id.produit_visuel);
        if (this.listeImagesFavoris.get(position) != null) {
            img.setImageBitmap(this.listeImagesFavoris.get(position));
        }else {
            int id = getContext().getResources().getIdentifier(
                    this.listeFavoris.get(position).getProduit().getVisuel(),
                    "drawable",
                    getContext().getPackageName()
            );
            img.setImageResource(id);
        }

        TextView tv1 = convertView.findViewById(R.id.produit_titre);
        tv1.setText(this.listeFavoris.get(position).getProduit().getTitre());

        return convertView;
    }
}
