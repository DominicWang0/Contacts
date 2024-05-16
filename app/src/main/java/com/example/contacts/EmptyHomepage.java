package com.example.contacts;

import static com.example.contacts.dataProcessing.dataValid.isEmailValid;
import static com.example.contacts.dataProcessing.dataValid.isEmpty;
import static com.example.contacts.dataProcessing.dataValid.isPhoneValid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.contacts.dataProcessing.MyHelper;
import com.example.contacts.dataProcessing.OneContact;

public class EmptyHomepage extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_phone, et_email, et_address, et_signature;
    RadioGroup rg_gander;
    RadioButton rb_male, rb_female;
    CheckBox cb_sing, cb_dance, cb_sport;
    Button btn_save, btn_cancel;

    AlertDialog ad_phoneUnvalid, ad_emailUnvalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_homepage);

        if (!init()) Log.i("EmptyHomepage", "初始化出错    init()");
        if (!dialogBuilder()) Log.i("EmptyHomepage", "弹窗初始化出错  dialogBuilder()");
    }

    boolean init() {
        et_name = findViewById(R.id.ehp_et_name);
        et_phone = findViewById(R.id.ehp_et_phone);
        et_email = findViewById(R.id.ehp_et_email);
        et_address = findViewById(R.id.ehp_et_address);
        et_signature = findViewById(R.id.ehp_et_signature);
        rg_gander = findViewById(R.id.ehp_rg_gander);
        rb_male = findViewById(R.id.ehp_rb_male);
        rb_female = findViewById(R.id.ehp_rb_female);
        cb_sing = findViewById(R.id.ehp_cb_singing);
        cb_dance = findViewById(R.id.ehp_cb_dancing);
        cb_sport = findViewById(R.id.ehp_cb_sport);
        btn_save = findViewById(R.id.ehp_btn_save);
        btn_cancel = findViewById(R.id.ehp_btn_cancel);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        return true;
    }

    boolean dialogBuilder() {
        ad_phoneUnvalid = new AlertDialog.Builder(EmptyHomepage.this).setTitle("电话号不合法").setIcon(R.drawable.ic_contacts_ios).setMessage("请正确输入电话号。").setNegativeButton("关闭", null).create();
        ad_emailUnvalid = new AlertDialog.Builder(EmptyHomepage.this).setTitle("邮箱不合法").setIcon(R.drawable.ic_contacts_ios).setMessage("请正确输入邮箱，邮箱要求：\n" + "1.用户名部分：由a～z的英文字母（不区分大小写）开头；可由英文字母、0～9的数字、点、减号或下划线组成；长度为3～18个字符；不能以点、减号结尾\n" + "2.服务器名部分：只能有一个点，点和\"@\"之间不能为空；可由英文字母、0～9的数字、点、减号或下划线组成；不能以点、减号或下划线结尾），多级服务器名是不受支持的。").setNegativeButton("关闭", null).create();
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent backToContactList = new Intent(EmptyHomepage.this, ContactList.class);

        if (view.getId() == R.id.ehp_btn_save) {
            //取值部分
            String name, phone, email, address, signature, ganderString;
            int ganderInt;
            boolean hobby1, hobby2, hobby3;
            name = et_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            email = et_email.getText().toString().trim();
            address = et_address.getText().toString().trim();
            signature = et_signature.getText().toString().trim();
            ganderInt = rg_gander.getCheckedRadioButtonId();
            hobby1 = cb_sing.isChecked();
            hobby2 = cb_dance.isChecked();
            hobby3 = cb_sport.isChecked();
            Log.i("EmptyHomepage", String.valueOf(ganderInt));

            //判空部分
            if (isEmpty(name)) {
                Toast.makeText(this, "姓名为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (isEmpty(phone)) {
                Toast.makeText(this, "电话号为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (isEmpty(email)) {
                Toast.makeText(this, "邮箱为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (ganderInt == -1) {
                Toast.makeText(this, "性别为空", Toast.LENGTH_SHORT).show();
                return;
            }

            //验证部分
            if (!isPhoneValid(phone)) {
                ad_phoneUnvalid.show();
                return;
            } else if (!isEmailValid(email)) {
                ad_emailUnvalid.show();
                return;
            }

            //转换部分
            if (ganderInt == R.id.ehp_rb_male) {
                ganderString = "男";
            } else {
                ganderString = "女";
            }

            //储存部分
            OneContact user = new OneContact(name, ganderString, phone, email, address, signature, hobby1, hobby2, hobby3);
            if (insert(user) == -1) {
                Toast.makeText(this, "存储失败", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(backToContactList);

        } else if (view.getId() == R.id.ehp_btn_cancel) {
            startActivity(backToContactList);
        }
    }

    //插入
    public long insert(OneContact data) {
        //获取数据库对象
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();

        //数据源
        ContentValues values = new ContentValues();
        Log.i("EmptyHomepage", "name " + data.getName());
        Log.i("EmptyHomepage", "phone " + data.getPhone());
        Log.i("EmptyHomepage", "mail " + data.getPhone());
        Log.i("EmptyHomepage", "add " + data.getAddress());
        Log.i("EmptyHomepage", "signature " + data.getSignature());
        Log.i("EmptyHomepage", "gander " + data.getGander());
        Log.i("EmptyHomepage", "h1 " + data.isHobby1_singing());


        values.put("name", data.getName());
        values.put("gander", data.getGander());
        values.put("phone", data.getPhone());
        values.put("email", data.getEmail());
        values.put("address", data.getAddress());
        values.put("signature", data.getSignature());
        values.put("hobby1_singing", data.isHobby1_singing());
        values.put("hobby2_dancing", data.isHobby2_dancing());
        values.put("hobby3_sport", data.isHobby3_sport());

        //存入
        long count = db.insert("Contacts", null, values);
        db.close();
        return count;
    }
}