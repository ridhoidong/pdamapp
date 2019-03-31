package com.example.idong.mbanking.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idong.mbanking.R;

/**
 * Created by Idong
 */

public class AppUtil {

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toolbarRegular(final Context context, String title) {

        Toolbar toolbar = (Toolbar) ((AppCompatActivity) context).findViewById(R.id.tb_regular);
        ImageView btnBack = (ImageView) ((AppCompatActivity) context).findViewById(R.id.btn_back);
        TextView tvPageTitle = (TextView) ((AppCompatActivity) context).findViewById(R.id.tv_page_title);
        tvPageTitle.setText(title);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) context).onBackPressed();
            }
        });
    }

    public static String getInitials(String label) {
        String initials="";
        String[] parts = label.split(" ");
        char initial;
        for (int i=0; i<parts.length; i++){
            initial=parts[i].charAt(0);
            initials+=initial;
        }
        return(initials.toUpperCase());
    }
}