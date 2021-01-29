package fr.ul.iutmetz.wmce.td1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.ul.iutmetz.wmce.td1.manager.SessionManager;

public class BoutiqueFragment extends Fragment {

    SessionManager sessionManager;
    private BottomNavigationView bnv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_boutique, container, false);

        this.bnv = root.findViewById(R.id.bnv_boutique);
        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_boutique);
        NavigationUI.setupWithNavController(bnv, navHostFragment.getNavController());

        sessionManager = new SessionManager(this.getActivity());

        navHostFragment.getNavController().addOnDestinationChangedListener(this::onNavigationChanged);

        return root;
    }

    private void onNavigationChanged(NavController navController, NavDestination navDestination, Bundle bundle){
        for (int i = 0 ; i < this.bnv.getMenu().size() ; i++){
            this.bnv.getMenu().getItem(i).setEnabled(true);
        }

        MenuItem fav = bnv.getMenu().findItem(R.id.boutique_favoris);
        if(sessionManager.isLoggin()){
            fav.setEnabled(true);
        } else {
            fav.setEnabled(false);
        }

        MenuItem menuItem = this.bnv.getMenu().findItem(navDestination.getId());

        if (menuItem != null){
            menuItem.setEnabled(false);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
//        NavDestination destinationCourante = navController.getCurrentDestination();
//
//        int idMenu = item.getItemId();
//        System.out.println("---- item ----");
//        System.out.println(idMenu);
//        if (idMenu == R.id.boutique_favoris){
//            if (destinationCourante != null && destinationCourante.getId() != R.id.boutique_favoris) {
//                if (sessionManager.isLoggin()){
//                    navController.navigate((R.id.boutique_favoris));
//                } else {
//                    navController.navigate((R.id.boutique_categories));
//                }
//            }
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
//    }
}