package com.example.idong.mbanking.CoreLayout.Home;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.idong.mbanking.CoreLayout.Scanner.AScanQR;
import com.example.idong.mbanking.R;
import com.example.idong.mbanking.util.AppUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Map;

/**
 * Created by PID on 3/29/2019.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private View view;
    private CarouselView carouselView;
    private ImageListener imageListener;
    private LinearLayout ll_addcustomer, ll_writetransaction;
    private IntentResult intentResult;
    public static String selectedMenu="";
    private int[] sampleImages = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        carouselView = view.findViewById(R.id.carousel);
        ll_addcustomer = view.findViewById(R.id.ll_addcustomer);
        ll_writetransaction = view.findViewById(R.id.ll_writetransaction);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        ll_addcustomer.setOnClickListener(this);
        ll_writetransaction.setOnClickListener(this);

        return view;
    }

    public void openQRScanner(){
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setPrompt("");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(AScanQR.class).initiateScan();
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        SelectedMenu menu;
        switch (v.getId()){
            case R.id.ll_addcustomer :
                selectedMenu = getString(R.string.menu_addcustomer);
                menu = new SelectedMenu();
                menu.setSelectedmenu(selectedMenu);
                openQRScanner();
                break;
            case R.id.ll_writetransaction:
                selectedMenu = getString(R.string.menu_writetransaction);
                menu = new SelectedMenu();
                menu.setSelectedmenu(selectedMenu);
                openQRScanner();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String qrcontent = "";
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                AppUtil.showToastShort(getContext(), "Gagal membaca QR Code");
            } else {
                qrcontent = intentResult.getContents();
                AppUtil.showToastShort(getContext(), selectedMenu + "###" + qrcontent);
                Log.d("qr", qrcontent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
