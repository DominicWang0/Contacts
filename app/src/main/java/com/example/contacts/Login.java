package com.example.contacts;

import static com.example.contacts.dataProcessing.SPSave.SPGetUserInfo;
import static com.example.contacts.dataProcessing.dataValid.isEmailValid;
import static com.example.contacts.dataProcessing.dataValid.isEmpty;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.dataProcessing.dataValid;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    TextView tv_enroll, tv_accountDescription, tv_passcodeDescription;
    EditText et_account, et_passcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    void init() {
        btn_login = findViewById(R.id.login_btn_login);
        tv_enroll = findViewById(R.id.login_tv_enroll);
        et_account = findViewById(R.id.login_et_account);
        et_passcode = findViewById(R.id.login_et_passcode);
        tv_accountDescription = findViewById(R.id.login_tv_accountDescription);
        tv_passcodeDescription = findViewById(R.id.login_tv_passcodeDescription);

        btn_login.setOnClickListener(this);
        tv_enroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent toEnroll = new Intent(Login.this, Enroll.class);
        Intent toMainWindow = new Intent(Login.this, ContactList.class);
        if (view.getId() == R.id.login_btn_login) {
            //登录
            //账号格式合法
            if (isAccountValid(et_account.getText().toString().trim())) {

            } else return;

            //密码格式合法
            if (isPasscodeValid(et_passcode.getText().toString().trim())) {

            } else return;

            //验证账号密码匹配
            if (isVerifiedPassed(et_account.getText().toString().trim(), et_passcode.getText().toString().trim())) {
                //启动跳转
                startActivity(toMainWindow);
            }

        } else if (view.getId() == R.id.login_tv_enroll) {
            //注册
            toEnroll.putExtra("account", "");    //未传值的注册页面跳转操作
            startActivity(toEnroll);
        }
    }

    private boolean isPasscodeValid(String passcode) {
        if (passcode.length() == 0 || passcode.equals("")) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            tv_passcodeDescription.setVisibility(View.GONE);
            return false;
        }
        if (dataValid.isPasscodeValid(passcode)) {
            //密码格式  合法
            tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            tv_passcodeDescription.setVisibility(View.GONE);
            return true;
        } else {
            //密码格式  非法
            tv_passcodeDescription.setTextColor(Color.parseColor("#FF0000"));
            tv_passcodeDescription.setVisibility(View.VISIBLE);
            return false;
        }
    }

    boolean isAccountValid(String account) {

        if (isEmpty(account)) {
            //账号为空
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            tv_accountDescription.setTextColor(Color.parseColor("#000000"));
            tv_accountDescription.setVisibility(View.GONE);
            return false;
        }

        if (isEmailValid(account)) {
            //邮箱格式  合法
            tv_accountDescription.setTextColor(Color.parseColor("#000000"));
            tv_accountDescription.setVisibility(View.GONE);
            return true;
        } else {
            //邮箱格式  非法
            tv_accountDescription.setTextColor(Color.parseColor("#FF0000"));
            tv_accountDescription.setVisibility(View.VISIBLE);
            return false;
        }

    }

    private boolean isVerifiedPassed(String account, String passcode) {
        String truePasscode = SPGetUserInfo(this, account);
        if (truePasscode == "not found") {
            //账号不存在
            AlertDialog nonAccount = new AlertDialog.Builder(Login.this).setTitle("账号不存在").setIcon(R.drawable.ic_contacts_ios).setMessage("是否新建账户？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent toEnroll = new Intent(Login.this, Enroll.class);
                    toEnroll.putExtra("account", account);
                    toEnroll.putExtra("passcode",passcode);
                    startActivity(toEnroll);
                }
            }).setNegativeButton("否", null).create();
            nonAccount.show();
            return false;
        }
        if (passcode.equals(truePasscode)) {
            return true;
        } else {
            Toast.makeText(this, "错误的密码", Toast.LENGTH_SHORT).show();
            et_passcode.setText(null);
            return false;
        }
    }

}