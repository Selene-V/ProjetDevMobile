package fr.ul.iutmetz.wmce.td1;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Panier;
import fr.ul.iutmetz.wmce.td1.modele.Produit;

public interface ActiviteEcommerce {
    public Panier getPanier();
    public void setPanier(Panier panier);

}
