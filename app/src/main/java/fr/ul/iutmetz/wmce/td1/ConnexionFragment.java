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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import fr.ul.iutmetz.wmce.td1.DAO.UserDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;

public class ConnexionFragment extends Fragment
        implements com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private EditText identifiant;
    private EditText motDePasse;

    private Button connexion;

    private TextView inscription;

    SessionManager sessionManager;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_connexion, container, false);


        if (savedInstanceState!=null){

        } else {
        }

        sessionManager = new SessionManager(this.getContext());


        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.identifiant = this.root.findViewById(R.id.identifiant);
        this.connexion = this.root.findViewById(R.id.connexion);
        this.connexion.setOnClickListener(this::onClickConnexion);
        this.motDePasse = this.root.findViewById(R.id.mot_de_passe);
        this.inscription = this.root.findViewById(R.id.inscription);
        this.inscription.setOnClickListener(this::onClickInscription);
    }

    public void onClickConnexion(View v){
        UserDAO conDAO = new UserDAO();
        conDAO.findOneByIdentifiant(this, identifiant.getText().toString());
    }


    public void onClickInscription(View v){
        Bundle bundle = new Bundle();
        bundle.putString("action", "inscription");
        Navigation.findNavController(v).navigate(R.id.action_to_SaisieInformationsClientFragment,bundle);
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
                    ((ActiviteConnexion) this.getActivity()).connexion(data.getInt("id_client"));

                    Bundle bundle = new Bundle();

                    Navigation.findNavController(this.root).navigate(R.id.action_to_Boutique, bundle);

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