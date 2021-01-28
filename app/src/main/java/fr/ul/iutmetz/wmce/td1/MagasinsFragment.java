package fr.ul.iutmetz.wmce.td1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.MagasinDAO;
import fr.ul.iutmetz.wmce.td1.modele.Magasin;

public class MagasinsFragment extends Fragment
    implements AdapterView.OnItemClickListener,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private ArrayList<Magasin> listeMagasins;

    private ListView lvMagasins;
    private MagasinsAdapter adapteur;

private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.root = inflater.inflate(R.layout.fragment_magasins, container, false);

        this.listeMagasins = new ArrayList<>();

        MagasinDAO magasinDAO = new MagasinDAO();
        magasinDAO.findAll(this);

        this.adapteur = new MagasinsAdapter(
                this.getContext(),
                this.listeMagasins
        );

        return this.root;

    }

    public void onStart() {
        super.onStart();

        this.lvMagasins = this.root.findViewById(R.id.magasins_liste);

        this.lvMagasins.setAdapter(adapteur);
        this.lvMagasins.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_magasin", this.listeMagasins.get(position).getId());

        Navigation.findNavController(view).navigate(R.id.action_to_MapsFragment,bundle);
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
            for (int i = 0 ; i < data.length() ; i++){
                JSONObject magasin = data.getJSONObject(i);
                int idMag = magasin.getInt("id_magasin");
                String name = magasin.getString("nom");
                double latitude = magasin.getDouble("latitude");
                double longitude = magasin.getDouble("longitude");

                Magasin mag = new Magasin(idMag, name, latitude, longitude);
                this.listeMagasins.add(mag);
                System.out.println(listeMagasins.get(i).getNom());
            }
            this.adapteur.notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}