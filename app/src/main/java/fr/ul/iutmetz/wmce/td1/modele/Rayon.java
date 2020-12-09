package fr.ul.iutmetz.wmce.td1.modele;

import java.io.Serializable;

public class Rayon implements Serializable {
    private int id;
    private String titre;
    private String visuel;

    public Rayon(int id, String titre, String visuel) {
        this.id = id;
        this.titre = titre;
        this.visuel = visuel;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getVisuel() {
        return visuel;
    }

}
