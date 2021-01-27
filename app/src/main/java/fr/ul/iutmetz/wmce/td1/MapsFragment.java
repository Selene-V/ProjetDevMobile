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
    private ArrayList<Magasin> listeMagasins;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_maps, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.listeMagasins = new ArrayList<Magasin>();
        MagasinDAO magDAO = new MagasinDAO();
        magDAO.findAll(this);

        return this.root;
    }

    public void onResponse(JSONObject response){
        try {
            for (int i = 0 ; i < response.length() ; i++){

                int idMag = response.getInt("id_magasin");
                String name = response.getString("name_magasin");
                String latitude = response.getString("latitude");
                String longitude = response.getString("longitude");

                Magasin mag = new Magasin(idMag, name, latitude, longitude);
                this.listeMagasins.add(mag);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
        LatLng magasin = new LatLng(Double.parseDouble(this.listeMagasins.get(this.listeMagasins.size()-1).getLatitude()), Double.parseDouble(this.listeMagasins.get(this.listeMagasins.size()-1).getLongitude()));
        mMap.addMarker(new MarkerOptions().position(magasin).title("marker"));


        /*for (int i = 0 ; i < this.listeMagasins.size() ; i++){

            String message = this.listeMagasins.get(1).getLatitude();
            mMap.addMarker(new MarkerOptions().position(magasin).title("marker"+ i));
        }*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0, 0)));

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
//        Toast.makeText(this.getContext(), R.string.ca_erreur_bdd, Toast.LENGTH_LONG).show();
    }
}