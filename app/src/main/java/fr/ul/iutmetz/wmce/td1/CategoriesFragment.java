package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.navigation.Navigation;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
                com.android.volley.Response.ErrorListener, LoaderManager.LoaderCallbacks<Cursor> {

    SessionManager sessionManager;

    private ArrayList<Categorie> listeCategories;
    private double totalPanier;
    private Utils utils = new Utils();

    private ArrayList listeImagesCategories;
    private CategoriesAdapter adaptateur;

    private ListView lvCategories;
    private TextView prixTotal;
    private RadioButton vente;

    private View root;

    private boolean firstTimeLoaded;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putSerializable("listeCategorie", this.listeCategories);
        outState.putDouble("total_panier", utils.arrondir(this.totalPanier));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.categories_fragment, container, false);

        if(!firstTimeLoaded){
            getLoaderManager().initLoader(1, null, this);
            firstTimeLoaded = true;
        }
        else{
            getLoaderManager().restartLoader(1, null, this);
        }

        if (savedInstanceState!=null){
            this.listeCategories = (ArrayList<Categorie>) savedInstanceState.getSerializable("listeCategorie");
            this.totalPanier = utils.arrondir(savedInstanceState.getDouble("total_panier"));
        } else {
            //sessionManager = new SessionManager(this.getContext());
            //sessionManager.checkIsLogin(getView());

            this.listeCategories = new ArrayList<>();

            CategorieDAO catDAO = new CategorieDAO();
            catDAO.findAll(this);

            this.totalPanier = utils.arrondir(0.00);
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
        this.prixTotal = this.root.findViewById(R.id.total_panier_nombre);

        this.lvCategories.setAdapter(adaptateur);
        this.lvCategories.setOnItemClickListener(this);
        this.prixTotal.setText(" " + utils.arrondir(this.totalPanier));
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

    @Override
    public void onResponse(JSONArray response) {

        try {
            for (int i = 0 ; i < response.length() ; i++){
                JSONObject o = response.getJSONObject(i);
                System.out.println("---------- cat " + i + "------------------");
                int idCat = o.getInt("id_categorie");
                String title = o.getString("titre");
                String visuel = o.getString("visuel");
                Categorie cat = new Categorie(idCat, title, visuel);
                System.out.println("------- Categorie : " + cat.getTitre());
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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri myUri = Uri.parse("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/php/categories/findallCategorie.php");
        if(id==1){
            return new CursorLoader(getActivity(), myUri, null, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(data!=null){
            StringBuilder stringBuilderQueryResult=new StringBuilder("");
            /*while(data.moveToNext()){
                stringBuilderQueryResult.append(data.getString(0) + " " + data.getString(1) + data.getString(2) + "\n");
            }*/
            Toast toast = Toast.makeText(getActivity(), "oui", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getActivity(), "no categories", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}