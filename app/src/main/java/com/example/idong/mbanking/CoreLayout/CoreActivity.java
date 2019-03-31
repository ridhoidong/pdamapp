package com.example.idong.mbanking.CoreLayout;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.idong.mbanking.CoreLayout.Home.FragmentHome;
import com.example.idong.mbanking.R;
import com.example.idong.mbanking.dialog.CustomDialog;
import com.example.idong.mbanking.util.AppUtil;
import com.example.idong.mbanking.util.BackStackFragment;

public class CoreActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;
    boolean homePressed = true, doubleBackToExitPressedOnce = false;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new FragmentHome());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (android.support.v4.app.Fragment frg : getSupportFragmentManager().getFragments()) {
            frg.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {{

            if(doubleBackToExitPressedOnce){
                finish();
                return;
            }
            doubleBackToExitPressedOnce = true;
            AppUtil.showToastShort(this, "Tekan sekali lagi untuk keluar aplikasi");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment  = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                AppUtil.showToastShort(CoreActivity.this, "home");
                fragment = new FragmentHome();
                break;
            case R.id.navigation_dashboard:
                AppUtil.showToastShort(CoreActivity.this, "dashboard");
                fragment = new FragmentHome();
                break;
            case R.id.navigation_user:
                AppUtil.showToastShort(CoreActivity.this, "user");
                fragment = new FragmentHome();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return false;
    }
}
