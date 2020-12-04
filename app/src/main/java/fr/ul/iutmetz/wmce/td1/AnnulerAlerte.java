package fr.ul.iutmetz.wmce.td1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AnnulerAlerte extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        Activity activite = getActivity();

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(activite, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activite);
        }

        builder.setMessage(R.string.confirm_phrase)
                .setTitle(R.string.confirm_titre)
                .setIcon(android.R.drawable.ic_dialog_alert);
        DialogInterface.OnClickListener ecouteur = (DialogInterface.OnClickListener) activite;
        builder.setPositiveButton(R.string.confirm_oui, ecouteur);
        builder.setNegativeButton(R.string.confirm_non, ecouteur);

        return builder.create();
    }
}
