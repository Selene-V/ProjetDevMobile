package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Client;

public class MonCompteFragment extends Fragment
    implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    SessionManager sessionManager;
    private Client clientCourant;

    private TextView monIdentifiant;
    private TextView monNom;
    private TextView monPrenom;
    private TextView monAdresse;

    private TextView numCom;
    private TextView dateCom;

    private Button modif;
    private Button commande;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.root = inflater.inflate(R.layout.fragment_mon_compte, container, false);

        if (savedInstanceState!=null){

        } else {
            sessionManager = new SessionManager(this.getContext());
            sessionManager.checkIsLogin(getView());
            clientCourant=null;
        }

        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.monIdentifiant = this.root.findViewById(R.id.mon_identifiant);
        this.monNom = this.root.findViewById(R.id.mon_nom);
        this.monPrenom = this.root.findViewById(R.id.mon_prenom);
        this.monAdresse = this.root.findViewById(R.id.mon_adresse);

        this.numCom = this.root.findViewById(R.id.id_commande);
        this.dateCom = this.root.findViewById(R.id.date);

        this.modif = this.root.findViewById(R.id.btn_modifier_infos);
        this.modif.setOnClickListener(this::onClickModifier);
        this.commande = this.root.findViewById(R.id.btn_voir_commande);
        this.commande.setOnClickListener(this::onClickVoirCommande);

        int idClient = this.sessionManager.getIdUser();
        // Recherche des infos personnelles du user connecté
        UserDAO userDAO = new UserDAO();
        userDAO.findOneById(this, idClient);

        // Recherche de la dernière commande du user connecté
        CommandeDAO comDAO = new CommandeDAO();
        comDAO.findLastCommandByClient(this, idClient);
    }

    public void onClickVoirCommande(View v){

        Bundle bundle = new Bundle();
        bundle.putInt("id_commande", Integer.valueOf((String) this.numCom.getText()));

        Navigation.findNavController(v).navigate(R.id.action_toDetailCommandeFragment,bundle);

        //System.out.println("--------- NUM COMMANDE -------------");
        //startActivityForResult(intent, 0);
    }

    public void onClickModifier(View v){
        Bundle bundle = new Bundle();
        bundle.putString("action", "modification");
        bundle.putSerializable("client", this.clientCourant);
        Navigation.findNavController(v).navigate(R.id.action_mon_compte_to_modification,bundle);
    }

    public void majVueInfos(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("data");
        String requete = response.getString("requete");
        switch (requete) {
            case "rechercheID":
                this.monIdentifiant.setText(data.getString("identifiant"));
                this.monNom.setText(data.getString("nom"));
                this.monPrenom.setText(data.getString("prenom"));
                String adresse = data.getString("adr_numero") + ", " + data.getString("adr_voie") + "\n"
                        + data.getString("adr_code_postal") + " " + data.getString("adr_ville") + "\n"
                        + data.getString("adr_pays");
                this.monAdresse.setText(adresse);
                this.clientCourant = new Client(sessionManager.getIdUser(), data.getString("nom"), data.getString("prenom"), data.getString("identifiant"), "", data.getString("adr_numero"), data.getString("adr_voie"), data.getString("adr_code_postal"), data.getString("adr_ville"), data.getString("adr_pays"));
                break;
            case "rechercheCommande":
                System.out.println("------------------ DATA ---------------");
                System.out.println(data);
                this.numCom.setText(data.getString("id_commande"));
                String[] dateSplit = data.getString("date_commande").split("-");
                String date = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
                this.dateCom.setText(date);
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            majVueInfos(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}