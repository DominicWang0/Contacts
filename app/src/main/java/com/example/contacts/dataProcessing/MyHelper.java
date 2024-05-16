package com.example.contacts.dataProcessing;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(@Nullable Context context) {
        super(context, "contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Contacts (_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),gander VARCHAR(20),phone VARCHAR(20),email varchar(30),address varchar(50),signature varchar(50),hobby1_singing boolean,hobby2_dancing boolean,hobby3_sport boolean)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
