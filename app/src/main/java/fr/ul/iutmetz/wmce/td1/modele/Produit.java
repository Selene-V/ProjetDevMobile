package fr.ul.iutmetz.wmce.td1.modele;

public class Produit extends Rayon {

    private String description;
    private String prix;
    private int idCategorie;

    public Produit(int idCategorie, String titre, String description, String prix, String visuel) {
        super(titre, visuel);
        this.description = description;
        this.prix = prix;
        this.idCategorie = idCategorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getDescription() {
        return description;
    }

    public String getPrix() {
        return prix;
    }

}
