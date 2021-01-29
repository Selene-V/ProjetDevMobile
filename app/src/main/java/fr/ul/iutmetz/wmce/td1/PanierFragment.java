package fr.ul.iutmetz.wmce.td1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import fr.ul.iutmetz.wmce.td1.DAO.CommandeDAO;
import fr.ul.iutmetz.wmce.td1.manager.SessionManager;
import fr.ul.iutmetz.wmce.td1.modele.Commande;
import fr.ul.iutmetz.wmce.td1.modele.Panier;

public class PanierFragment extends Fragment implements ActiviteEnAttenteImage {
    private PanierAdapter adaptateur;
    private Panier panier;
    private ArrayList<Bitmap> listeProduitsImages;
    private View root;
    private ListView lvPanierListe;
    private float prixTotal;
    private Button valider;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.root = inflater.inflate(R.layout.fragment_panier, container, false);

        this.panier = ((ActiviteEcommerce) this.getActivity()).getPanier();

        this.sessionManager = new SessionManager(this.getContext());

        this.listeProduitsImages = new ArrayList<>();
        for (int i = 0 ; i < this.panier.getBasketContent().size() ; i++){
            this.listeProduitsImages.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~viola11u/WS_PM/" +
                    this.panier.getBasketContent().get(i).getProduit().getVisuel(), String.valueOf(i));
        }

        for (int i = 0 ; i < this.panier.getBasketSize() ; i++ ){
            Log.e("Calcul", "In");
            this.prixTotal = this.prixTotal + Float.parseFloat(this.panier.getBasketContent().get(i).getProduit().getPrix())  * this.panier.getBasketContent().get(i).getQuantite();
        }
        TextView tvTotal = this.root.findViewById(R.id.totalAmount);
        tvTotal.setText(Float.toString(this.prixTotal));

        this.adaptateur = new PanierAdapter(
                this.getContext(),
                this.panier.getBasketContent(),
                this.listeProduitsImages
        );

        return this.root;
    }

    @Override
    public void onStart(){
        super.onStart();

        this.valider = this.root.findViewById(R.id.bouttonValiderPanier);
        this.lvPanierListe = this.root.findViewById(R.id.panier_liste);
        this.lvPanierListe.setAdapter(adaptateur);
        //this.lvPanierListe.setOnItemClickListener(this);

        this.valider.setOnClickListener(this::onClickValiderCommande);
    }

    @Override
    public void receptionnerImage(Object[] resultats) {
        if (resultats[0] != null){
            int idx = Integer.parseInt(resultats[1].toString());
            Bitmap img = (Bitmap) resultats[0];
            this.listeProduitsImages.set(idx, img);
            this.adaptateur.notifyDataSetChanged();
        }
    }

    public void onClickValiderCommande(View view)  {

        String pattern = "yyyy-dd-mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println(date);

        Commande c = new Commande(-1,date ,sessionManager.getIdUser());

    }
}