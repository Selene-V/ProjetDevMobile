package fr.ul.iutmetz.wmce.td1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class PanierFragment extends Fragment {
    private PanierAdapter adaptateur;
    private ArrayList<Produit> listeProduits;
    private ArrayList<Bitmap> listeProduitsImages;
    private View root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.root = inflater.inflate(R.layout.fragment_affichage_categories, container, false);

        this.adaptateur = new PanierAdapter(
                this.getContext(),
                this.listeProduits,
                this.listeProduitsImages
        );

        return this.root;
    }
}
