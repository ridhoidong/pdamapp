package com.example.idong.mbanking.WriteTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.idong.mbanking.BuildConfig;
import com.example.idong.mbanking.R;
import com.example.idong.mbanking.util.AppUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class WriteTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    private String selectedMenu;
    private TextView tv_profilpicture, tv_name, tv_customerid, tv_phonenumber, tv_address;
    private EditText et_meterinput;
    private Spinner sp_metercondition;
    private ImageView iv_galleryfoto, iv_camerafoto;
    private RoundedImageView iv_photopreview;
    private LinearLayout ll_photopreview;
    private Button btn_submitwritetransaction;
    private String name, customerId, phoneNumber, address, meterInput, meterCondition;
    private final int PICK_IMAGE = 1;
    private final int TAKE_PICTURE = 0;
    private Bitmap bitmap;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writetransaction);
        Intent it = getIntent();
        selectedMenu = it.getStringExtra("selectedMenu");
        AppUtil.toolbarRegular(this, selectedMenu);
        tv_profilpicture = (TextView) findViewById(R.id.tv_profilpicture);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_customerid = (TextView) findViewById(R.id.tv_customerid);
        tv_phonenumber = (TextView) findViewById(R.id.tv_phonenumber);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_meterinput = (EditText) findViewById(R.id.et_meterinput);
        sp_metercondition = (Spinner) findViewById(R.id.sp_metercondition);
        iv_galleryfoto = (ImageView) findViewById(R.id.iv_galleryfoto);
        iv_camerafoto = (ImageView) findViewById(R.id.iv_camerafoto);
        ll_photopreview = (LinearLayout) findViewById(R.id.ll_photopreview);
        iv_photopreview = (RoundedImageView) findViewById(R.id.iv_photopreview);
        btn_submitwritetransaction = (Button) findViewById(R.id.btn_submitwritetransaction);

        main();
    }

    public void main(){
        name = tv_name.getText().toString().trim();
        tv_profilpicture.setText(AppUtil.getInitials(name));
        iv_galleryfoto.setOnClickListener(this);
        iv_camerafoto.setOnClickListener(this);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            iv_camerafoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_galleryfoto:
                openGalery();
                break;
            case R.id.iv_camerafoto:
                openCamera();
                break;
        }
    }

    public void openGalery(){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(Intent.createChooser(it, "Select File"), PICK_IMAGE);
    }

    private void openCamera() {
        checkCameraPermission();
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = FileProvider.getUriForFile(WriteTransactionActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(getImage.getPath(), "fotometer.png"));
        }
        return outputFileUri;
    }


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public void checkCameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        } else {
            Uri outputFileUri = getCaptureImageOutputUri();
            Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(captureIntent, TAKE_PICTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_CAMERA_REQUEST_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                AppUtil.showToastLong(this, "Camera Permission Granted");
                Uri outputFileUri = getCaptureImageOutputUri();
                Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(captureIntent, TAKE_PICTURE);
            }
            else {
                AppUtil.showToastLong(this, "Camera Permission Denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (getPickImageResultUri(data) != null) {
                uri = getPickImageResultUri(data);
                Log.d("URI" , String.valueOf(uri));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        bitmap = getResizedBitmap(bitmap, 500);
                        bitmap = rotateImageIfRequired(bitmap, uri);
                    } catch (Exception e) {
                        Log.d("WKWKWK", e.getMessage());
                        e.printStackTrace();
                    }
                ll_photopreview.setVisibility(View.VISIBLE);
                iv_photopreview.setImageBitmap(bitmap);
            }
        }

    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage){
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(selectedImage.getPath());
        } catch (IOException e) {
            Log.d("YYY", e.getMessage());
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Log.d("ORIENTATION", "EA");
        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
