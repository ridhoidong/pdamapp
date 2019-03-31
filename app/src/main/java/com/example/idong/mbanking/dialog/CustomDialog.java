package com.example.idong.mbanking.dialog;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Idong on 30/03/2019.
 */

public class CustomDialog {
    private static AlertDialog alertDialog = null;
    private Context mContext;

    public CustomDialog(Context context) {
        this.mContext = context;
    }

    public static void DialogQuitApp(final Context context) {
        try {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin akan keluar dari aplikasi ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alertdialog, int which) {
                        ((Activity) context).finish();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface alertdialog, int which) {
                        alertdialog.cancel();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
