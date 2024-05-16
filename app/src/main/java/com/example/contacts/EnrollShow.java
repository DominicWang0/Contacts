package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EnrollShow extends AppCompatActivity implements View.OnClickListener {
    EditText et_account, et_passcode;
    RadioButton rb_gander;
    RadioGroup rg_gander;
    Button btn_backToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_show);
        init();
    }

    void init() {
        et_account = findViewById(R.id.show_et_account);
        et_passcode = findViewById(R.id.show_et_passcode);
        btn_backToLogin = findViewById(R.id.show_btn_backToLogin);
        btn_backToLogin.setOnClickListener(this);
        rg_gander = findViewById(R.id.show_rg_gander);
        getData();
    }

    void getData() {
        Intent getData = getIntent();
        et_account.setText(getData.getStringExtra("account"));
        et_passcode.setText(getData.getStringExtra("passcode"));
        Log.i("ShowData", String.valueOf(getData.getIntExtra("gander", -1)));
        rg_gander.check(getData.getIntExtra("gander", -1));
    }

    @Override
    public void onClick(View view) {
        Intent backToLogin = new Intent(EnrollShow.this, Login.class);
        startActivity(backToLogin);
    }
}