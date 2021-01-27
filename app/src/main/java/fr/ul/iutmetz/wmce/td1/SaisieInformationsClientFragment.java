package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import fr.ul.iutmetz.wmce.td1.DAO.InscriptionDAO;
import fr.ul.iutmetz.wmce.td1.DAO.ModificationUserDAO;
import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Client;
import utils.Utils;


public class SaisieInformationsClientFragment extends Fragment
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    SessionManager sessionManager;

    private Utils utils = new Utils();

    private String action;

    private Client client;

    private EditText nom;
    private EditText prenom;
    private EditText identifiant;
    private EditText adrNum;
    private EditText adrCP;
    private EditText adrVoie;
    private EditText adrVille;
    private EditText adrPays;
    private EditText mdp;

    private TextView nomHelp;
    private TextView prenomHelp;
    private TextView identifiantHelp;
    private TextView adrNumHelp;
    private TextView adrCPHelp;
    private TextView adrVoieHelp;
    private TextView adrVilleHelp;
    private TextView adrPaysHelp;
    private TextView mdpHelp;

    private Button valider;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_saisie_informations_client, container, false);


        if (savedInstanceState != null) {

        } else {
            sessionManager = new SessionManager(this.getContext());
            // Action permet de savoir si l'on effectue une inscription ou une modification
            // d'un client
            this.action = this.getArguments().getString("action");

            if (this.action.equals("modification")){
                this.client = (Client)this.getArguments().get("client");
                System.out.println(this.client.getIdentifiant());
            }else {
                this.client = null;
            }
        }

        // Recuperation de action
        if (this.getArguments().getString("action", "default")!="default"){
            this.action = this.getArguments().getString("action", "default");
        }

        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.nom = this.root.findViewById(R.id.nom);
        this.prenom = this.root.findViewById(R.id.prenom);
        this.identifiant = this.root.findViewById(R.id.identifiant);
        this.adrNum = this.root.findViewById(R.id.adresse_numero);
        this.adrCP = this.root.findViewById(R.id.adresse_code_postal);
        this.adrVoie = this.root.findViewById(R.id.adresse_voie);
        this.adrVille = this.root.findViewById(R.id.adresse_ville);
        this.adrPays = this.root.findViewById(R.id.adresse_pays);
        this.mdp = this.root.findViewById(R.id.mot_de_passe);

        this.nomHelp = this.root.findViewById(R.id.nom_help);
        this.prenomHelp = this.root.findViewById(R.id.prenom_help);
        this.identifiantHelp = this.root.findViewById(R.id.identifiant_help);
        this.adrNumHelp = this.root.findViewById(R.id.adresse_numero_help);
        this.adrCPHelp = this.root.findViewById(R.id.adresse_code_postal_help);
        this.adrVoieHelp = this.root.findViewById(R.id.adresse_voie_help);
        this.adrVilleHelp = this.root.findViewById(R.id.adresse_ville_help);
        this.adrPaysHelp = this.root.findViewById(R.id.adresse_pays_help);
        this.mdpHelp = this.root.findViewById(R.id.mot_de_passe_help);

        this.valider = this.root.findViewById(R.id.bouton_valider_modifications);
        this.valider.setOnClickListener(this::onClickValider);

        if (this.client != null){
            majVueSaisie();
        }
    }

    public void majVueSaisie(){
        this.nom.setText(this.client.getNom());
        this.prenom.setText(this.client.getPrenom());
        this.identifiant.setText(this.client.getIdentifiant());
        this.adrNum.setText(this.client.getAdrNumero());
        this.adrCP.setText(this.client.getAdrCP());
        this.adrVoie.setText(this.client.getAdrVoie());
        this.adrVille.setText(this.client.getAdrVille());
        this.adrPays.setText(this.client.getAdrPays());
    }

    public void onClickValider(View v) {
        if (validationChamps()) {
            String n = utils.toUpperCaseFirst(this.nom.getText().toString().toLowerCase());
            String p = utils.toUpperCaseFirst(this.prenom.getText().toString().toLowerCase());
            String id_c = this.identifiant.getText().toString().toLowerCase();
            // A CRYPTER
            String motdp = this.mdp.getText().toString();
            String adrN = this.adrNum.getText().toString();
            String adrVoie = utils.toUpperCaseFirst(this.adrVoie.getText().toString().toLowerCase());
            String adrCP = this.adrCP.getText().toString();
            String adrVille = this.adrVille.getText().toString().toUpperCase();
            String adrP = this.adrPays.getText().toString().toUpperCase();
            System.out.println("ON EST APRES ASSIGNATION !");
            System.out.println("ACTION : " + this.action);
            System.out.println("ACTION equals : " + this.action.equals("inscription"));

            if (this.action.equals("inscription")) {
                System.out.println("ON EST DANS UNE INSCRIPTION !");
                this.client = new Client(-1, n, p, id_c, motdp, adrN, adrVoie, adrCP, adrVille, adrP);

                this.identifiantExist(this.client.getIdentifiant());
            }
            // Sinon c'est une modification
            else {
                // Recuperation id Client Connecté :
                System.out.println("ON EST DANS UNE MODIFICATION !");

                int idUser = sessionManager.getIdUser();
                System.out.println("id récupéré dans la session : " + idUser);
                System.out.println("La session est elle connectée : " + sessionManager.isLoggin());
                this.client = new Client(idUser, n, p, id_c, motdp, adrN, adrVoie, adrCP, adrVille, adrP);

                this.identifiantExist(this.client.getIdentifiant());
            }
        }
    }

    public boolean validationChamps() {
        clearErrors();
        boolean validNom = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.nom, this.nomHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validPrenom = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.prenom, this.prenomHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrVoie = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrVoie, this.adrVoieHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrVille = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrVille, this.adrVilleHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validAdrPays = isValid("([ \\u00c0-\\u01ffa-zA-Z'\\-])+(?<!('|\\s|-))", this.adrPays, this.adrPaysHelp, "Caractères acceptés : a-z A-Z , ' - espace");
        boolean validIdentifiant = isValid(".+@.+\\.[a-zA-Z]{2,}", this.identifiant, this.identifiantHelp, "Veuillez entrez une adresse email valide de la forme exemple@exemple.fr");
        boolean validMdp = isValid("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&.]{8,}", this.mdp, this.mdpHelp, "Minimum de huit caractères, au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial (@$!%*?&)");
        boolean validAdrCP = isValid("((0[1-9])|([1-8][0-9])|(9[0-8]))[0-9]{3}", this.adrCP, this.adrCPHelp, "Ne peut commencer par 00 ou 99 et doit comporter 5 chiffres.");
        boolean validAdrNum = isValid("(([1-9]))[0-9]{0,2}", this.adrNum, this.adrNumHelp, "Ne peut commencer par 0 et doit comporter entre 1 et 3 chiffres.");

        return (validNom && validPrenom && validAdrVoie && validAdrVille && validAdrPays && validIdentifiant && validMdp && validAdrCP && validAdrNum);
    }

    public boolean isValid(String regex, EditText nomChamp, TextView nomChampHelp, String messageErreur) {
        boolean valid = true;
        if (TextUtils.isEmpty(nomChamp.getText().toString())) {
            nomChampHelp.setText("Champ obligatoire !");
            nomChampHelp.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            System.out.println("regex : " + Pattern.matches(regex, nomChamp.getText().toString()));
            if (!Pattern.matches(regex, nomChamp.getText().toString())) {
                nomChampHelp.setText(messageErreur);
                nomChampHelp.setVisibility(View.VISIBLE);
                valid = false;
            }
        }
        System.out.println(nomChamp.getText().toString() + " : " + valid);
        return valid;
    }

    public void clearErrors() {
        this.nomHelp.setVisibility(View.INVISIBLE);
        this.prenomHelp.setVisibility(View.INVISIBLE);
        this.adrVoieHelp.setVisibility(View.INVISIBLE);
        this.adrVilleHelp.setVisibility(View.INVISIBLE);
        this.adrPaysHelp.setVisibility(View.INVISIBLE);
        this.identifiantHelp.setVisibility(View.INVISIBLE);
        this.mdpHelp.setVisibility(View.INVISIBLE);
        this.adrCPHelp.setVisibility(View.INVISIBLE);
        this.adrNumHelp.setVisibility(View.INVISIBLE);
    }

    public void identifiantExist(String identifiant) {
        UserDAO userDAO = new UserDAO();
        userDAO.findOneByIdentifiant(this, identifiant);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String requete = response.getString("requete");

            System.out.println("requete");
            System.out.println(requete);

            switch (requete) {
                case "recherche":
                    if (this.action.equals("inscription")) {
                        System.out.println("-------------------- INSCRIPTION ---------------------");
                        if (!response.getBoolean("res")) {
                            System.out.println("IL EXISTE PAS !");
                            Intent intent = new Intent();

                            // ADD CLIENT BDD
                            InscriptionDAO inscDAO = new InscriptionDAO();
                            inscDAO.insert(this, client);

                            this.getActivity().setResult(0, intent);
                            this.getActivity().finish();
                        } else {
                            System.out.println("IL EXISTE !");
                            this.identifiantHelp.setText("Cette adresse email est déjà utilisée !");
                            this.identifiantHelp.setVisibility(View.VISIBLE);
                        }
                    } else {
                        System.out.println("-------------------- MODIFICATION ---------------------");
                        if (((response.getBoolean("res")) && (response.getJSONObject("data").getInt("id_client") == sessionManager.getIdUser()))
                        || (!response.getBoolean("res"))) {
                            System.out.println("IL EXISTE MAIS CEST LUI OU IL EXISTE PAS!");
                            Intent intent = new Intent();

                            // UPDATE CLIENT BDD
                            ModificationUserDAO modifDAO = new ModificationUserDAO();
                            modifDAO.update(this, client);

                            this.getActivity().setResult(0, intent);
                            this.getActivity().finish();
                        } else {
                            System.out.println("IL EXISTE ET CEST PAS LUI !");
                            this.identifiantHelp.setText("Cette adresse email est déjà utilisée !");
                            this.identifiantHelp.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case "insert":
                    break;
                case "update":
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}