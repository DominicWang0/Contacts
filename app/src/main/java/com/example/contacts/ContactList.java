package com.example.contacts;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contacts.dataProcessing.MyHelper;
import com.example.contacts.dataProcessing.OneContact;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity implements View.OnClickListener {
    ArrayList<OneContact> list = new ArrayList<>();


    Button btn_add, btn_search, btn_sync;
    EditText et_search;
    RecyclerView rv_list;

    AlertDialog deleteDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        init();
    }

    void init() {
        et_search = findViewById(R.id.main_et_search);
        btn_add = findViewById(R.id.main_btn_add);
        btn_search = findViewById(R.id.main_btn_search);
        btn_sync = findViewById(R.id.main_btn_sync);


        rv_list = findViewById(R.id.main_rv_result);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        btn_sync.setOnClickListener(this);

        showData();
    }

    private void showData() {
        MyHelper myHelper = new MyHelper(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor allData = db.query("Contacts", new String[]{"name", "phone"}, null, null, null, null, null);
        if (allData.getCount() == 0) {
            Toast.makeText(this, "暂无数据，请添加", Toast.LENGTH_SHORT).show();
        } else {
            list.clear();
            while (allData.moveToNext()) {
                @SuppressLint("Range") OneContact user = new OneContact(allData.getString(allData.getColumnIndex("name")), allData.getString(allData.getColumnIndex("phone")));
                list.add(user);
            }
            HomeAdapter myAdapter;
            myAdapter = new HomeAdapter();
            rv_list.setAdapter(myAdapter);

        }
    }

    private int search() {
        String searchText = et_search.getText().toString().trim();
        int i;
        for (i = 0; i < list.size(); i++) {
            if (searchText.equals(list.get(i).getName()) || searchText.equals(list.get(i).getPhone()))
                return i;
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.main_btn_add) {
            //添加
            Intent toEmptyHomepage = new Intent(ContactList.this, EmptyHomepage.class);
            startActivity(toEmptyHomepage);
        } else if (view.getId() == R.id.main_btn_search) {
            //查找
            int searchResault = search();
            if (searchResault == -1) {
                et_search.setText("");
                Toast.makeText(this, "查无此人", Toast.LENGTH_SHORT).show();
            } else {
                Intent toHomepage = new Intent(ContactList.this, Homepage.class);
                toHomepage.putExtra("name", list.get(searchResault).getName());
                startActivity(toHomepage);
            }
        } else if (view.getId() == R.id.main_btn_sync) {
            //刷新
            showData();
        }
    }

    ///////////////////////////Recycle  View///////////////////////////////////
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {


        @NonNull
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(ContactList.this).inflate(R.layout.contact_view, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toHomepage = new Intent(ContactList.this, Homepage.class);
                    toHomepage.putExtra("name", ((TextView) v.findViewById(R.id.tv_name)).getText().toString().trim());
                    startActivity(toHomepage);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
            holder.name.setText(list.get(position).getName().trim());
            holder.phone.setText(list.get(position).getPhone().trim());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, phone;

            public MyViewHolder(View view) {
                super(view);
                name = view.findViewById(R.id.tv_name);
                phone = view.findViewById(R.id.tv_phone);
            }
        }
    }
}