package fr.ul.iutmetz.wmce.td1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import fr.ul.iutmetz.wmce.td1.modele.Taille;

public class SpinnerAdapter extends ArrayAdapter<Taille> {
    private ArrayList<Taille> listeTailles;

    public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<Taille> liste) {
        super(context, textViewResourceId, liste);
        this.listeTailles = liste;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(listeTailles.get(position).getLabel());

        return label;
    }

    @Override
    public Taille getItem(int position){
        return listeTailles.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(listeTailles.get(position).getLabel());

        return label;
    }
}
