package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Client;

public class ConnexionFragment extends Fragment
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private EditText identifiant;
    private EditText motDePasse;

    SessionManager sessionManager;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.activity_connexion, container, false);


        if (savedInstanceState!=null){

        } else {
            sessionManager = new SessionManager(this.getContext());
        }

        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.identifiant = this.root.findViewById(R.id.identifiant);
        this.motDePasse = this.root.findViewById(R.id.mot_de_passe);
    }

    public void onClickConnexion(View v){
        UserDAO conDAO = new UserDAO();
        conDAO.findOneByIdentifiant(this, identifiant.getText().toString());
    }


    public void onClickInscription(View v){
        Bundle bundle = new Bundle();
        bundle.putString("action", "inscription");
        Navigation.findNavController(v).navigate(R.id.action_toSaisieInformationsClientFragment,bundle);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.con_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            //correct id
            // Verification mdp :
            if (!response.getBoolean("res")) {
                Toast.makeText(this.getContext(), R.string.con_erreur_bdd, Toast.LENGTH_LONG).show();
            } else {
                JSONObject data = response.getJSONObject("data");

                System.out.println(data.getString("mot_de_passe"));
                System.out.println(motDePasse.getText().toString());

                if (motDePasse.getText().toString().equals(data.getString("mot_de_passe"))) {
                    System.out.println(data.getInt("id_client"));

                    Toast.makeText(this.getContext(), "Vous allez être redirigé...", Toast.LENGTH_LONG).show();
                    sessionManager.createSession(data.getInt("id_client"));

                    Bundle bundle = new Bundle();

                    Navigation.findNavController(this.root).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment2, bundle);

//                    Intent intent = new Intent(ConnexionActivity.this, CategoriesActivity.class);
//                    startActivityForResult(intent, 0);
                } else {
                    //wrong password
                    Toast.makeText(this.getContext(), "Identifiants incorrectes !", Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}