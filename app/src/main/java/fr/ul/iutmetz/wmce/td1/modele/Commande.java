package fr.ul.iutmetz.wmce.td1.modele;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Commande implements Serializable {
    private int id;
    private String dateCommande;
    private int idClient;

    public Commande(int id, String dateCommande, int idClient) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCommande() {
        return dateCommande.toString();
    }

    public void setDateCommande(String dateCommande) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");
        String date = "";
        this.dateCommande = date;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
