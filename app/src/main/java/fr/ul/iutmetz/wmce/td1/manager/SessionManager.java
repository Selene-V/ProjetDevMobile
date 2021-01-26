package fr.ul.iutmetz.wmce.td1.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.Navigation;

import fr.ul.iutmetz.wmce.td1.R;

public class SessionManager {
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String ID = "ID";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("AppKey", 0);
        this.editor = sharedPreferences.edit();
    }

    public void createSession (int id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(ID, id);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void checkIsLogin(View view){
        if (!this.isLoggin()){
            Bundle bundle = new Bundle();
            Navigation.findNavController(view).navigate(R.id.action_to_ConnexionFragment,bundle);
        }
    }

    public int getIdUser(){
        return sharedPreferences.getInt(ID, -1);
    }

    public void closeSession(View view){
        editor.clear();
        editor.commit();
        Bundle bundle = new Bundle();
        Navigation.findNavController(view).navigate(R.id.action_to_ConnexionFragment,bundle);
    }
}
