package utils;

import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.modele.Taille;

public class Triplet<T, U, V> {

        private T produit;
        private U taille;
        private V quantite;

        public Triplet(T produit, U taille, V quantite) {
            this.produit = produit;
            this.taille = taille;
            this.quantite = quantite;
        }

        public T getProduit() { return produit; }
        public U getTaille() { return taille; }
        public V getQuantite() { return quantite; }

        public void setProduit(T p){this.produit = p;}
        public void setTaille(U t){this.taille = t;}
        public void setQuantite(V q){this.quantite = q;}

    }
