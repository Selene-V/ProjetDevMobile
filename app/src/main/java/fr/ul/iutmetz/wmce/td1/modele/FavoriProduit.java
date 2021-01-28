package fr.ul.iutmetz.wmce.td1.modele;

public class FavoriProduit {

    private int idClient;
    private Produit produit;

    public FavoriProduit(int idClient, Produit produit) {
        this.idClient = idClient;
        this.produit = produit;
    }

    public int getIdClient() {
        return idClient;
    }

    public Produit getProduit() {
        return produit;
    }
}
