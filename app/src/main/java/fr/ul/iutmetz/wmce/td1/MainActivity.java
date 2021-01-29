package fr.ul.iutmetz.wmce.td1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fr.ul.iutmetz.wmce.td1.manager.SessionManager;

import java.util.ArrayList;

import fr.ul.iutmetz.wmce.td1.modele.Panier;
import fr.ul.iutmetz.wmce.td1.modele.Produit;
import fr.ul.iutmetz.wmce.td1.modele.Taille;
import utils.Triplet;

public class MainActivity extends AppCompatActivity implements ActiviteEcommerce, ActiviteConnexion {

    private AppBarConfiguration mAppBarConfiguration;
    private fr.ul.iutmetz.wmce.td1.modele.Panier Panier;

    SessionManager sessionManager;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_boutique, R.id.nav_nous_trouver, R.id.nav_mentions_legales)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        this.Panier = new Panier(new ArrayList<Triplet<Produit, Taille, Integer>>());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.changeMenu(menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDestination destinationCourante = navController.getCurrentDestination();

        int idMenu = item.getItemId();
        System.out.println("---- item ----");
        System.out.println(idMenu);
        // A CHANGER QUAND PANIER OK
        if (idMenu == R.id.nav_panier){
//            if (destinationCourante != null && destinationCourante.getId() != R.id.nav_panier){
//                navController.navigate((R.id.nav_panier));
//            }
            if (destinationCourante != null && destinationCourante.getId() != R.id.nav_mon_compte) {
                if (sessionManager.isLoggin()){
                    navController.navigate((R.id.nav_mon_compte));
                } else {
                    if (destinationCourante != null && destinationCourante.getId() != R.id.nav_connexion) {
                        navController.navigate((R.id.nav_connexion));
                    }
                }
            }
            return true;
        } else if (idMenu == R.id.nav_deconnexion) {
            if (destinationCourante != null && destinationCourante.getId() != R.id.nav_deconnexion) {
                deconnexion();
                navController.popBackStack(R.id.nav_boutique, true);
                navController.navigate((R.id.nav_boutique));
            }
            return true;
        } else if (idMenu == R.id.nav_mon_compte) {
            if (destinationCourante != null && destinationCourante.getId() != R.id.nav_mon_compte) {
                if (sessionManager.isLoggin()){
                    navController.navigate((R.id.nav_mon_compte));
                } else {
                    if (destinationCourante != null && destinationCourante.getId() != R.id.nav_connexion) {
                        navController.navigate((R.id.nav_connexion));
                    }
                }
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void changeMenu(Menu menu){
        if (this.sessionManager.isLoggin()){
            menu.getItem(0).setIcon(R.drawable.ic_nav_mon_compte);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setVisible(true);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_nav_connexion);
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setVisible(false);
        }
    }

    @Override
    public void connexion(int id) {
        this.sessionManager.createSession(id);
        this.changeMenu(this.menu);
    }

    @Override
    public void deconnexion() {
        sessionManager.closeSession();
        this.changeMenu(this.menu);
    }


    @Override
    public Panier getPanier() {
        return this.Panier;
    }

    @Override
    public void setPanier(Panier panier) {
        this.Panier = panier;
    }
}