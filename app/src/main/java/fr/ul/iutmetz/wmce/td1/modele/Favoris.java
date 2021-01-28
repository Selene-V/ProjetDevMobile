package fr.ul.iutmetz.wmce.td1.modele;

public class Favoris {

    private int idClient;
    private Produit produit;

    public Favoris(int idClient, Produit Produit) {
        this.idClient = idClient;
        this.produit = Produit;
    }

    public int getIdClient() {
        return idClient;
    }

    public Produit getProduit() {
        return produit;
    }
}
