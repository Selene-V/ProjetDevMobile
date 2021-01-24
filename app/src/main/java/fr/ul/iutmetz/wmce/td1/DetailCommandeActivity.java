package fr.ul.iutmetz.wmce.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailCommandeActivity extends AppCompatActivity {

    private int idCommandeCourante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_commande);

        if (savedInstanceState==null){
            this.idCommandeCourante = this.getIntent().getIntExtra("id_commande", -1);
        }
    }

    public void onBackPressed(){
        this.setResult(-1);
        this.finish();
    }
}