package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MonCompteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_compte);


        if (savedInstanceState!=null){

        } else {

        }
    }

    public void onClickModifier(View v){
        Intent intent = new Intent(MonCompteActivity.this, SaisieInformationsClientActivity.class);
        intent.putExtra("action", "modification");
        startActivityForResult(intent, 0);
    }
}