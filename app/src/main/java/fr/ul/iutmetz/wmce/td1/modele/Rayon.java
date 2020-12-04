package fr.ul.iutmetz.wmce.td1.modele;

import java.io.Serializable;

public class Rayon implements Serializable {
     private String titre;
        private String visuel;

        public Rayon(String titre, String visuel) {
            this.titre = titre;
            this.visuel = visuel;
        }

        public String getTitre() {
            return titre;
        }

        public String getVisuel() {
            return visuel;
        }

}
