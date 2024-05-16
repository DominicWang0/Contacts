package com.example.contacts;

import static com.example.contacts.dataProcessing.dataValid.isEmailValid;
import static com.example.contacts.dataProcessing.dataValid.isEmpty;
import static com.example.contacts.dataProcessing.dataValid.isPhoneValid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.dataProcessing.MyHelper;
import com.example.contacts.dataProcessing.OneContact;

public class Homepage extends AppCompatActivity implements View.OnClickListener {
    TextView tv_name;
    EditText et_phone, et_email, et_address, et_signature;
    CheckBox cb_hobby1, cb_hobby2, cb_hobby3;
    Button btn_modify, btn_delete, btn_back;
    ImageView iv_headshot;
    OneContact user;
    AlertDialog ad_phoneUnvalid, ad_emailUnvalid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (!init()) {
            Log.i("Homepage", "初始化出错 init()");
        }else Log.i("Homepage", "初始化成功 init()");

    }

    boolean init() {
        tv_name = findViewById(R.id.hp_tv_name);
        et_phone = findViewById(R.id.hp_et_phone);
        et_email = findViewById(R.id.hp_et_email);
        et_address = findViewById(R.id.hp_et_address);
        et_signature = findViewById(R.id.hp_et_signature);
        cb_hobby1 = findViewById(R.id.hp_cb_singing);
        cb_hobby2 = findViewById(R.id.hp_cb_dancing);
        cb_hobby3 = findViewById(R.id.hp_cb_sport);
        btn_modify = findViewById(R.id.hp_btn_modify);
        btn_delete = findViewById(R.id.hp_btn_delete);
        btn_back = findViewById(R.id.hp_btn_back);
        iv_headshot = findViewById(R.id.hp_iv_headshot);

        btn_modify.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        if (!getData()) {
            Log.i("Homepage", "获取数据出错    getData()");
        }
        if (!setData()) {
            Log.i("Homepage", "安装数据出错    setData()");
        }
        if (!dialogBuilder()) {
            Log.i("Homepage", "构建弹窗出错    dialogBuilder()");
        }

        return true;
    }

    @SuppressLint("Range")
    boolean getData() {
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();

        String keyWord;
        Intent getData = getIntent();
        keyWord = getData.getStringExtra("name");
        Log.i("Homepage","用户名为："+keyWord);
        Cursor userData = db.query("Contacts", null, "name=?", new String[]{keyWord}, null, null, null);
        userData.moveToFirst();

        Boolean hobby1, hobby2, hobby3;
        hobby1 = userData.getInt(userData.getColumnIndex("hobby1_singing")) == 1;
        hobby2 = userData.getInt(userData.getColumnIndex("hobby2_dancing")) == 1;
        hobby3 = userData.getInt(userData.getColumnIndex("hobby3_sport")) == 1;

        user = new OneContact(userData.getString(userData.getColumnIndex("name")), userData.getString(userData.getColumnIndex("gander")), userData.getString(userData.getColumnIndex("phone")), userData.getString(userData.getColumnIndex("email")), userData.getString(userData.getColumnIndex("address")), userData.getString(userData.getColumnIndex("signature")), hobby1, hobby2, hobby3);

        return true;
    }

    @SuppressLint("ResourceAsColor")
    boolean setData() {

        //设置头像
        if (user.getGander().equals("男")) {
            iv_headshot.setImageResource(R.drawable.ic_headshot_gentle);
//            tv_name.setTextColor(R.color.gentle);
        } else if (user.getGander().equals("女")) {
            iv_headshot.setImageResource(R.drawable.ic_headshot_lady);
//            tv_name.setTextColor(R.color.lady);
        } else {
            iv_headshot.setImageResource(R.drawable.ic_headshot);
            Toast.makeText(this, "性别为空", Toast.LENGTH_SHORT).show();
        }

        //设置名字
        tv_name.setText(user.getName());

        //设置其他
        et_phone.setText(user.getPhone());
        et_email.setText(user.getEmail());
        et_address.setText(user.getAddress());
        et_signature.setText(user.getSignature());

        cb_hobby1.setChecked(user.isHobby1_singing());
        cb_hobby2.setChecked(user.isHobby2_dancing());
        cb_hobby3.setChecked(user.isHobby3_sport());

        return true;
    }

    boolean dialogBuilder() {
        ad_phoneUnvalid = new AlertDialog.Builder(this).setTitle("电话号不合法").setIcon(R.drawable.ic_contacts_ios).setMessage("请正确输入电话号。").setNegativeButton("关闭", null).create();
        ad_emailUnvalid = new AlertDialog.Builder(this).setTitle("邮箱不合法").setIcon(R.drawable.ic_contacts_ios).setMessage("请正确输入邮箱，邮箱要求：\n" + "1.用户名部分：由a～z的英文字母（不区分大小写）开头；可由英文字母、0～9的数字、点、减号或下划线组成；长度为3～18个字符；不能以点、减号结尾\n" + "2.服务器名部分：只能有一个点，点和\"@\"之间不能为空；可由英文字母、0～9的数字、点、减号或下划线组成；不能以点、减号或下划线结尾），多级服务器名是不受支持的。").setNegativeButton("关闭", null).create();
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent backToContactList = new Intent(this, ContactList.class);
        if (view.getId() == R.id.hp_btn_modify) {
            //取值部分
            String name, phone, email, address, signature, gander;
            boolean hobby1, hobby2, hobby3;
            name = user.getName();
            phone = et_phone.getText().toString().trim();
            email = et_email.getText().toString().trim();
            address = et_address.getText().toString().trim();
            signature = et_signature.getText().toString().trim();
            hobby1 = cb_hobby1.isChecked();
            hobby2 = cb_hobby2.isChecked();
            hobby3 = cb_hobby3.isChecked();
            gander = user.getGander();

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
            }

            //验证部分
            if (!isPhoneValid(phone)) {
                ad_phoneUnvalid.show();
                return;
            } else if (!isEmailValid(email)) {
                ad_emailUnvalid.show();
                return;
            }

            //修改部分
            OneContact user = new OneContact(name, gander, phone, email, address, signature, hobby1, hobby2, hobby3);
            if (!update(user)) {
                Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(backToContactList);
            }

        } else if (view.getId() == R.id.hp_btn_delete) {

            if (!delete(user.getName())) {
                Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
            } else startActivity(backToContactList);

        } else if (view.getId() == R.id.hp_btn_back) {

            startActivity(backToContactList);

        }

    }

    //更新
    private boolean update(OneContact data) {
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", data.getName());
        values.put("gander", data.getGander());
        values.put("phone", data.getPhone());
        values.put("email", data.getEmail());
        values.put("address", data.getAddress());
        values.put("signature", data.getSignature());
        values.put("hobby1_singing", data.isHobby1_singing());
        values.put("hobby2_dancing", data.isHobby2_dancing());
        values.put("hobby3_sport", data.isHobby3_sport());

        int code = db.update("Contacts", values, "name=?", new String[]{data.getName()});
        db.close();
        return code != -1;
    }

    //删除
    private boolean delete(String name) {
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        int i = db.delete("Contacts", "name=?", new String[]{name});
        db.close();
        return i != 0;
    }


}