package fr.ul.iutmetz.wmce.td1.manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import fr.ul.iutmetz.wmce.td1.CategoriesActivity;
import fr.ul.iutmetz.wmce.td1.ConnexionActivity;

public class SessionManager {
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String EMAIL = "EMAIL";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("AppKey", 0);
        this.editor = sharedPreferences.edit();
    }

    public void createSession (String email){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void checkIsLogin(){
        if (!this.isLoggin()){
            Intent intent = new Intent(context, ConnexionActivity.class);
            context.startActivity(intent);
            ((CategoriesActivity)context).finish();
        }
    }

    public String getEmailUser(){
        return sharedPreferences.getString(EMAIL, "");
    }

    public void closeSession(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, ConnexionActivity.class);
        context.startActivity(intent);
        ((CategoriesActivity)context).finish();
    }
}
