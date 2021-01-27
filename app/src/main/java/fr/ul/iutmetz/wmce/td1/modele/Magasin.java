package fr.ul.iutmetz.wmce.td1.modele;

    public class Magasin {

        private int id;
        private String nom;
        private String latitude;
        private String longitude;

        public Magasin(int id, String nom, String latitude, String longitude) {
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

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }
