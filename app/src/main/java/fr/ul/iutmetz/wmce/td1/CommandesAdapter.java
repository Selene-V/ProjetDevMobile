package fr.ul.iutmetz.wmce.td1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Commande;

public class CommandesAdapter extends ArrayAdapter<Commande> {

    private ArrayList<Commande> listeCommandes;

    public CommandesAdapter(Context context, ArrayList<Commande> liste)
    {
        super(context, 0, liste);
        this.listeCommandes = liste;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_commandes, parent, false);
        }
        TextView tv1 = convertView.findViewById(R.id.numero_commande);
        System.out.println("TV1");
        System.out.println(tv1);
        tv1.setText(this.listeCommandes.get(position).getId());

        TextView tv2 = convertView.findViewById(R.id.date_commande);
        tv2.setText((CharSequence) this.listeCommandes.get(position).getDateCommande());

        return convertView;
    }
}
