package fr.ul.iutmetz.wmce.td1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class ImageFromURL extends AsyncTask<String, Void, Object[]> {
    private ActiviteEnAttenteImage activite;

    @Override
    protected Object[] doInBackground(String... urlEtIndice) {
        String urlImage = urlEtIndice[0];
        Bitmap image = null;

        try {
            InputStream in = new URL(urlImage).openStream();
            image = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e){
            Log.e("Pas d'image", "image générique à utiliser à la place !");
        }
        return new Object[] {image, urlEtIndice[1]};
    }

    @Override
    protected void onPostExecute(Object[] result){
        this.activite.receptionnerImage(result);
    }

    public ImageFromURL(ActiviteEnAttenteImage activite){
        super();
        this.activite = activite;
    }
}
