package fr.ul.iutmetz.wmce.td1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.DAO.MagasinDAO;
import fr.ul.iutmetz.wmce.td1.modele.Magasin;



public class MapsFragment extends Fragment implements OnMapReadyCallback,
        com.android.volley.Response.Listener<JSONObject>,
        com.android.volley.Response.ErrorListener {

    private GoogleMap mMap;
    private View root;
    private Magasin magasin;
    private int idMagasin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Recuperation id categorie
        if (this.getArguments().getInt("id_categ", -1)!=-1){
            this.idMagasin = this.getArguments().getInt("id_magasin", -1);
        }

        MagasinDAO magDAO = new MagasinDAO();
        magDAO.findOneMagasinById(this, this.idMagasin);

        return this.root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        double latitude = this.magasin.getLatitude();
        double longitude = this.magasin.getLongitude();
        String titre = this.magasin.getNom();
        LatLng positionMagasin = new LatLng(latitude, longitude);
        this.mMap.addMarker(new MarkerOptions().position(positionMagasin).title(titre));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(positionMagasin));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }

    public void onResponse(JSONObject response){
        try {
            JSONObject data = response.getJSONObject("data");
            int id = data.getInt("id_magasin");
            String nom = data.getString("nom");
            double latitude = data.getDouble("latitude");
            double longitude = data.getDouble("longitude");
            this.magasin = new Magasin(id, nom, latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}