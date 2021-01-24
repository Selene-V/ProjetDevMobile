package fr.ul.iutmetz.wmce.td1.modele;

import java.io.Serializable;
import java.sql.Date;

public class Commande implements Serializable {
    private int id;
    private Date dateCommande;
    private int idClient;

    public Commande(int id, Date dateCommande, int idClient) {
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

    public void setDateCommande(String dateCommande) {
        this.dateCommande = Date.valueOf(dateCommande);
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
