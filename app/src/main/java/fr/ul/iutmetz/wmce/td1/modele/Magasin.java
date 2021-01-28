package fr.ul.iutmetz.wmce.td1.modele;

    public class Magasin {

        private int id;
        private String nom;
        private double latitude;
        private double longitude;

        public Magasin(int id, String nom, double latitude, double longitude) {
            this.id = id;
            this.nom = nom;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public int getId() {
            return id;
        }

        public String getNom() {
            return nom;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
