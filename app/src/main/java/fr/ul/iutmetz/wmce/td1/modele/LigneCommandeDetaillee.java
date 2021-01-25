package fr.ul.iutmetz.wmce.td1.modele;

public class LigneCommandeDetaillee {
    private int idLigne;
    private int idCommande;
    private Produit produit;
    private int quantite;
    private String taille;
    private String tarifLigne;

    public LigneCommandeDetaillee(int idLigne, int idCommande, Produit produit, int quantite, String taille, String tarifLigne) {
        this.idLigne = idLigne;
        this.idCommande = idCommande;
        this.produit = produit;
        this.quantite = quantite;
        this.taille = taille;
        this.tarifLigne = tarifLigne;
    }

    public int getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getTarifLigne() {
        return tarifLigne;
    }

    public void setTarifLigne(String tarifLigne) {
        this.tarifLigne = tarifLigne;
    }
}
