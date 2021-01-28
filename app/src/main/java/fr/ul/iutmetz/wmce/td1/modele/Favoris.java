package fr.ul.iutmetz.wmce.td1.modele;

public class Favoris {

    private int idClient;
    private int idProduit;

    public Favoris(int idClient, int idProduit) {
        this.idClient = idClient;
        this.idProduit = idProduit;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduit() {
        return idProduit;
    }
}
