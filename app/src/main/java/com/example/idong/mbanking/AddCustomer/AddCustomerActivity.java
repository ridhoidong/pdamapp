package com.example.idong.mbanking.AddCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.idong.mbanking.R;
import com.example.idong.mbanking.util.AppUtil;

public class AddCustomerActivity extends AppCompatActivity {
    private String selectedMenu, customerId;
    private EditText et_customerid, et_customername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);
        Intent it = getIntent();
        selectedMenu = it.getStringExtra("selectedMenu");
        customerId = it.getStringExtra("customerId");
        Log.d("EA", customerId);
        et_customerid = (EditText) findViewById(R.id.et_customerid);
        et_customername = (EditText) findViewById(R.id.et_customername);
        AppUtil.toolbarRegular(this, selectedMenu);
        disableEditText(et_customerid);
    }

    private void disableEditText(EditText editText) {
        et_customerid.setText(customerId);
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }
}
