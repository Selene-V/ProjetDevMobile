package fr.ul.iutmetz.wmce.td1.modele;

public class Categorie extends Rayon {

    private int id;

    public Categorie(int id, String titre, String visuel) {
        super(titre, visuel);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
