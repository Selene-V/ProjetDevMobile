package fr.ul.iutmetz.wmce.td1;

import android.graphics.Bitmap;
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

import java.sql.Date;
import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.DAO.FavorisDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.Favoris;
import fr.ul.iutmetz.wmce.td1.modele.Produit;

public class FavorisFragment extends Fragment
    implements AdapterView.OnItemClickListener, ActiviteEnAttenteImage,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private ArrayList<Favoris> listeFavoris;
    SessionManager sessionManager;

    private ListView lvFavoris;
    private ArrayList listeImagesFavoris;
    private FavorisAdapter adapteur;

private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        this.root = inflater.inflate(R.layout.fragment_favoris, container, false);

        this.listeFavoris = new ArrayList<>();
        sessionManager = new SessionManager(this.getContext());

        FavorisDAO favDAO = new FavorisDAO();
        favDAO.findAllFavorisByClient(this, this.sessionManager.getIdUser());

        this.listeImagesFavoris = new ArrayList<>();
        for (int i = 0 ; i < this.listeFavoris.size() ; i++){
            this.listeImagesFavoris.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.listeFavoris.get(i).getProduit().getVisuel(), String.valueOf(i));
        }

        this.adapteur = new FavorisAdapter(
                this.getContext(),
                this.listeFavoris,
                this.listeImagesFavoris
        );

        return this.root;

    }

    public void onStart() {
        super.onStart();

        this.lvFavoris = this.root.findViewById(R.id.commandes_liste);

        this.lvFavoris.setAdapter(adapteur);
        this.lvFavoris.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_produit", this.listeFavoris.get(position).getProduit().getId());
        bundle.putInt("id_categorie", this.listeFavoris.get(position).getProduit().getIdCategorie());

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
                JSONObject pFavoris = data.getJSONObject(i);

                int idClient = pFavoris.getInt("id_client");
                int idCat = pFavoris.getInt("id_categorie");
                int idProduit = pFavoris.getInt("id_produit");
                String title = pFavoris.getString("titre");
                String desc = pFavoris.getString("description");
                String tarif = String.valueOf(pFavoris.getDouble("tarif"));
                String visuel = pFavoris.getString("visuel");

                Produit prod = new Produit(idProduit, title, desc, tarif, visuel, idCat);
                Favoris fav = new Favoris(idClient, prod);
                this.listeFavoris.add(fav);

                this.listeImagesFavoris.add(null);
                ImageFromURL chargement = new ImageFromURL(this);
                chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                        this.listeFavoris.get(i).getProduit().getVisuel(), String.valueOf(i));
            }
            this.adapteur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesFavoris.set(idx, img);
            this.adapteur.notifyDataSetChanged();
        }
    }
}