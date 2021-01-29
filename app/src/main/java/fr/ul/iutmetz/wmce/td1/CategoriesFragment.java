package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.CategorieDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Categorie;
import utils.Utils;

public class CategoriesFragment extends Fragment
    implements AdapterView.OnItemClickListener, ActiviteEnAttenteImage,
                com.android.volley.Response.Listener<JSONArray>,
                com.android.volley.Response.ErrorListener {

    private ArrayList<Categorie> listeCategories;
    private ImageButton ic_panier;
    private Utils utils = new Utils();

    private ArrayList listeImagesCategories;
    private CategoriesAdapter adaptateur;

    private ListView lvCategories;
    private TextView prixTotal;

    private View root;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("listeCategorie", this.listeCategories);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_categories, container, false);

        if (savedInstanceState!=null){
            this.listeCategories = (ArrayList<Categorie>) savedInstanceState.getSerializable("listeCategorie");
        } else {

            this.listeCategories = new ArrayList<>();

            CategorieDAO catDAO = new CategorieDAO();
            catDAO.findAll(this);

        }

        this.listeImagesCategories = new ArrayList<>();
        for (int i = 0 ; i < this.listeCategories.size() ; i++){
            this.listeImagesCategories.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.listeCategories.get(i).getVisuel(), String.valueOf(i));
        }
        this.adaptateur = new CategoriesAdapter(
                this.getContext(),
                this.listeCategories,
                this.listeImagesCategories
        );
        return this.root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.lvCategories = this.root.findViewById(R.id.ca_liste);
        this.ic_panier = this.root.findViewById(R.id.icon_panier);

        this.lvCategories.setAdapter(adaptateur);
        this.lvCategories.setOnItemClickListener(this);
        this.ic_panier.setOnClickListener(this::onClickGoToBasket);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_categ", this.listeCategories.get(position).getId());

        Navigation.findNavController(view).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment2,bundle);
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesCategories.set(idx, img);
            this.adaptateur.notifyDataSetChanged();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    public void onClickGoToBasket(View view){
        Bundle bundle = new Bundle();
        //bundle.putInt("id_categ", this.listeCategories.get(position).getId());

        Navigation.findNavController(view).navigate(R.id.action_to_PanierFragment,bundle);
    }

    @Override
    public void onResponse(JSONArray response) {

        try {
            for (int i = 0 ; i < response.length() ; i++){
                JSONObject o = response.getJSONObject(i);
                int idCat = o.getInt("id_categorie");
                String title = o.getString("titre");
                String visuel = o.getString("visuel");
                Categorie cat = new Categorie(idCat, title, visuel);
                this.listeCategories.add(cat);
                this.listeImagesCategories.add(null);
                ImageFromURL chargement = new ImageFromURL(this);
                chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                        this.listeCategories.get(i).getVisuel(), String.valueOf(i));
            }
            this.adaptateur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed(){

    }
}