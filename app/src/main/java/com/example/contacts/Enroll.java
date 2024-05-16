package com.example.contacts;

import static com.example.contacts.dataProcessing.SPSave.SPSaveUserInfo;
import static com.example.contacts.dataProcessing.dataValid.isEmailValid;
import static com.example.contacts.dataProcessing.dataValid.isPasscodeValid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Enroll extends AppCompatActivity implements View.OnClickListener {

    EditText et_account, et_passcode, et_rePasscode;
    Button btn_enroll;
    TextView tv_backToLogin, tv_accounDescription, tv_passcodeDescription, tv_rePasscodeDescription;
    RadioGroup rg_gander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        init();
    }

    void init() {
        et_account = findViewById(R.id.enroll_et_account);
        et_passcode = findViewById(R.id.enroll_et_passcode);
        et_rePasscode = findViewById(R.id.enroll_et_rePasscode);
        btn_enroll = findViewById(R.id.enroll_btn_enroll);
        tv_backToLogin = findViewById(R.id.enroll_tv_backToLogin);
        rg_gander = findViewById(R.id.enroll_rg_gander);
        tv_accounDescription = findViewById(R.id.enroll_tv_accountDescription);
        tv_passcodeDescription = findViewById(R.id.enroll_tv_passcodeDescription);
        tv_rePasscodeDescription = findViewById(R.id.enroll_tv_rePasscodeDescription);
        btn_enroll.setOnClickListener(this);
        tv_backToLogin.setOnClickListener(this);

        String account;
        Intent getData = getIntent();
        account = getData.getStringExtra("account");
        if (account.equals("")) {

        } else {
            et_account.setText(account);
            et_passcode.setText(getData.getStringExtra("passcode"));
        }


        Log.i("Enroll", "初始化成功");
    }

    @Override
    public void onClick(View view) {
        Intent backToLogin = new Intent(Enroll.this, Login.class);
        Intent toEnrollShow = new Intent(Enroll.this, EnrollShow.class);
        if (view.getId() == R.id.enroll_btn_enroll) {
            //全部合法标识
            boolean isAllCompliant = true;

            //注册
            if (isEmpty(et_account)) {
                Toast.makeText(this, "账号为空", Toast.LENGTH_SHORT).show();
                isAllCompliant = false;
            } else if (isEmpty(et_passcode)) {
                Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
                isAllCompliant = false;
            } else if (isEmpty(rg_gander)) {
                Toast.makeText(this, "性别为空", Toast.LENGTH_SHORT).show();
                isAllCompliant = false;
            }

            //判断账号是否合法
            if (isEmailValid(et_account.getText().toString().trim())) {
                tv_accounDescription.setTextColor(Color.parseColor("#000000"));
            } else {
                tv_accounDescription.setTextColor(Color.parseColor("#FF0000"));
                isAllCompliant = false;
            }

            //判断密码是否合法
            if (isPasscodeValid(et_passcode.getText().toString().trim())) {
                tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            } else {
                tv_passcodeDescription.setTextColor(Color.parseColor("#FF0000"));
                isAllCompliant = false;
            }

            //判断两次密码是否一致
            if (et_passcode.getText().toString().trim().equals(et_rePasscode.getText().toString().trim())) {
                tv_rePasscodeDescription.setVisibility(View.GONE);
            } else {
                tv_rePasscodeDescription.setTextColor(Color.parseColor("#FF0000"));
                tv_rePasscodeDescription.setVisibility(View.VISIBLE);
                isAllCompliant = false;
            }

            if (isAllCompliant) {
                //全部合法
                if (SPSaveUserInfo(this, et_account.getText().toString().trim(), et_passcode.getText().toString().trim())) {
                    Log.i("Enroll", "用户数据成功记录");
                    toEnrollShow.putExtra("account", et_account.getText().toString().trim());
                    toEnrollShow.putExtra("passcode", et_passcode.getText().toString());
                    toEnrollShow.putExtra("gander", rg_gander.getCheckedRadioButtonId());
                    startActivity(toEnrollShow);
                }
            }
            Log.i("Enroll", "注册按钮被按下");
        } else if (view.getId() == R.id.enroll_tv_backToLogin) {
            //返回登录
            startActivity(backToLogin);
            Log.i("Enroll", "返回登录页按钮被按下");
        }
    }

    boolean isEmpty(EditText temp) {
        if (temp.getText().length() == 0 || temp.getText().toString().trim() == "") {
            return true;
        }
        return false;
    }

    boolean isEmpty(RadioGroup temp) {
        if (temp.getCheckedRadioButtonId() != -1)
            return false;
        else
            return true;

    }


}