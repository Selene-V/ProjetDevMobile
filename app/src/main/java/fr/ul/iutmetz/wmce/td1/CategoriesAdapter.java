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

import fr.ul.iutmetz.wmce.td1.modele.Categorie;

public class CategoriesAdapter extends ArrayAdapter<Categorie> {
    private ArrayList<Categorie> listeCategories;
    private ArrayList<Bitmap> listeImagesCategories;

    public CategoriesAdapter(Context context, ArrayList<Categorie> liste, ArrayList<Bitmap> listeImages) {
        super(context, 0, liste);
        this.listeCategories = liste;
        this.listeImagesCategories = listeImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_categories, parent, false);
        }
            TextView tv = convertView.findViewById(R.id.ilc_titre);
            tv.setText(this.listeCategories.get(position).getTitre());

            ImageView img = convertView.findViewById(R.id.ilc_visuel);
            if (this.listeImagesCategories.get(position) != null) {
                img.setImageBitmap(this.listeImagesCategories.get(position));
            } else {
                int id = getContext().getResources().getIdentifier(
                        this.listeCategories.get(position).getVisuel(),
                        "drawable",
                        getContext().getPackageName()
                );
                img.setImageResource(id);
            }

        return convertView;
    }
}

