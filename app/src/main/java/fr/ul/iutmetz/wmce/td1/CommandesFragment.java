package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.Navigation;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.sql.Date;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Commande;

public class CommandesFragment extends Fragment
    implements AdapterView.OnItemClickListener,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener, LoaderManager.LoaderCallbacks {

    private ArrayList<Commande> listeCommandes;
    SessionManager sessionManager;

    private ListView lvCommandes;
    private CommandesAdapter adapteur;

private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.root = inflater.inflate(R.layout.activity_affichage_commandes, container, false);

        this.listeCommandes = new ArrayList<>();
        sessionManager = new SessionManager(this.getContext());
//        sessionManager.checkIsLogin();

        CommandeDAO comDAO = new CommandeDAO();
//        comDAO.findAllCommandsByClient(this, this.sessionManager.getIdUser());
        comDAO.findAllCommandsByClient(this, 1);

        this.adapteur = new CommandesAdapter(
                this.getContext(),
                this.listeCommandes
        );

        return this.root;

    }

    public void onStart() {
        super.onStart();

        this.lvCommandes = this.root.findViewById(R.id.commandes_liste);

        this.lvCommandes.setAdapter(adapteur);
        this.lvCommandes.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_commande", this.listeCommandes.get(position).getId());

        Navigation.findNavController(view).navigate(R.id.action_to_DetailCommandeFragment,bundle);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");
            System.out.println("-------------- DATA --------------");
            System.out.println(data);
            for (int i = 0 ; i < response.length() ; i++){
                JSONObject commande = data.getJSONObject(i);

                int idCommande = commande.getInt("id_commande");

                String date = commande.getString("date_commande");
                int idClient = commande.getInt("id_client");

                Commande com = new Commande(idCommande, Date.valueOf(date), idClient);
                this.listeCommandes.add(com);
            }
            this.adapteur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        for(int i = 0; i <= listeCommandes.size(); i++){
            Uri myUri = Uri.parse("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/commandes/findAllCommandsByClient.php?id_client="+this.sessionManager.getIdUser());
            return new CursorLoader(getActivity(), myUri, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        adapteur = new CommandesAdapter(getActivity(), listeCommandes);
        lvCommandes.setAdapter(adapteur);

        Toast toast = Toast.makeText(getActivity(), "data loaded", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
}