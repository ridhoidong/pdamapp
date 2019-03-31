package com.example.idong.mbanking.CoreLayout.Scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.idong.mbanking.AddCustomer.AddCustomerActivity;
import com.example.idong.mbanking.CoreLayout.Home.FragmentHome;
import com.example.idong.mbanking.R;
import com.example.idong.mbanking.WriteTransaction.WriteTransactionActivity;
import com.example.idong.mbanking.util.AppUtil;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by IDONG on 30/03/2019.
 */

public class AScanQR extends AppCompatActivity implements DecoratedBarcodeView.TorchListener, View.OnClickListener{
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton, btn_submit;
    private ImageView flashButton;
    private LinearLayout bottom_sheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tv_inputmanual;
    private EditText et_customerid;
    private String selectedMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        switchFlashlightButton = (Button)findViewById(R.id.switch_flashlight);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_customerid = (EditText) findViewById(R.id.et_customeridbts);
        tv_inputmanual = (TextView) findViewById(R.id.tv_inputmanual);
        flashButton = (ImageView) findViewById(R.id.iv_flash);
        selectedMenu = FragmentHome.selectedMenu;
        barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.qr_scanner_layout);
        barcodeScannerView.setTorchListener(this);
        checkPermissionCamera();

        if (!hasFlash()) {
            flashButton.setVisibility(View.GONE);
        }
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

        AppUtil.toolbarRegular(this, selectedMenu);

        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getString(R.string.turn_on_flashlight).equals(switchFlashlightButton.getText())) {
                        barcodeScannerView.setTorchOn();
                    } else {
                        barcodeScannerView.setTorchOff();
                    }

            }
//                if(flashButton.getResources().getR)
//                {
//                    Log.d("TEST MASUL", "2222");
//                    barcodeScannerView.setTorchOn();
//                }
//                else
//                {
//                    Log.d("TEST MASUL", "3333");
//                    barcodeScannerView.setTorchOff();
//                }
//            }
        });

        btn_submit.setOnClickListener(this);
        tv_inputmanual.setOnClickListener(this);
        bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottom_sheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public void checkPermissionCamera()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_CAMERA_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                AppUtil.showToastLong(this, "Camera Permission Granted");
            }
            else {
                AppUtil.showToastLong(this, "Camera Permission Denied");
                onBackPressed();
            }
        }
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


    public void switchFlashlight(View view) {

        if(flashButton.getResources().equals(R.drawable.ic_flashon))
        {
            barcodeScannerView.setTorchOn();
        }
        else
        {
            barcodeScannerView.setTorchOff();
        }
    }

    public void switchFlashlight2 (View view)
    {
        if (getString(R.string.turn_on_flashlight).equals(switchFlashlightButton.getText())) {
            barcodeScannerView.setTorchOn();
        } else {
            barcodeScannerView.setTorchOff();
        }
    }

    public void goInputManual(Intent intent){
        intent.putExtra("selectedMenu", selectedMenu);
        intent.putExtra("customerId", et_customerid.getText().toString().trim());
        startActivity(intent);
    }


    @Override
    public void onTorchOn() {
        switchFlashlightButton.setText("Turn off Flashlight");
        flashButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_flashoff));
    }

    @Override
    public void onTorchOff() {
        switchFlashlightButton.setText("Turn on Flashlight");
        flashButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_flashon));
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()){
            case R.id.tv_inputmanual:
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            break;
            case R.id.btn_submit:
                if (selectedMenu.equalsIgnoreCase(getString(R.string.menu_addcustomer))){
                    it = new Intent(this, AddCustomerActivity.class);
                }
                else if(selectedMenu.equalsIgnoreCase(getString(R.string.menu_writetransaction))){
                    it = new Intent(this, WriteTransactionActivity.class);
                }
                goInputManual(it);
        }
    }
}