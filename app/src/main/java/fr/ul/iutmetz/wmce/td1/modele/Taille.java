package fr.ul.iutmetz.wmce.td1.modele;

public class Taille {
    private int id;
    private String label;


    public Taille(int id, String label){
        this.id = id;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
