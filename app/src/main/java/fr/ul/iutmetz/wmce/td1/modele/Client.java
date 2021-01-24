package fr.ul.iutmetz.wmce.td1.modele;

import java.io.Serializable;

public class Client  implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String identifiant;
    private String mdp;
    private String adrNumero;
    private String adrVoie;
    private String adrCP;
    private String adrVille;
    private String adrPays;

    public Client(int id, String nom, String prenom, String identifiant, String mdp, String adrNumero, String adrVoie, String adrCP, String adrVille, String adrPays) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.mdp = mdp;
        this.adrNumero = adrNumero;
        this.adrVoie = adrVoie;
        this.adrCP = adrCP;
        this.adrVille = adrVille;
        this.adrPays = adrPays;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }


    public String getIdentifiant() {
        return identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public String getAdrNumero() {
        return adrNumero;
    }

    public String getAdrVoie() {
        return adrVoie;
    }

    public String getAdrCP() {
        return adrCP;
    }

    public String getAdrVille() {
        return adrVille;
    }

    public String getAdrPays() {
        return adrPays;
    }
}
