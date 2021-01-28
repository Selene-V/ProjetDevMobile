package fr.ul.iutmetz.wmce.td1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.Magasin;

public class MagasinsAdapter extends ArrayAdapter<Magasin> {

    private ArrayList<Magasin> listeMagasins;

    public MagasinsAdapter(Context context, ArrayList<Magasin> liste)
    {
        super(context, 0, liste);
        this.listeMagasins = liste;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_magasins, parent, false);
        }

        TextView tv1 = convertView.findViewById(R.id.nom_magasin);
        tv1.setText(this.listeMagasins.get(position).getNom());

        return convertView;
    }
}
