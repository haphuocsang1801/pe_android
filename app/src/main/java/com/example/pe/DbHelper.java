package com.example.pe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context,"PE",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table Employee(ID text primary key, FULLNAME text, AGE integer)";
        db.execSQL(sql);

        sql = "Insert Into Employee Values ('NV-113','Beatrix',34)";
        db.execSQL(sql);
        sql = "Insert Into Employee Values ('GD-234','Gwyneth',45)";
        db.execSQL(sql);
        sql = "Insert Into Employee Values ('KT-143','Emmanuel',23)";
        db.execSQL(sql);
        sql = "Insert Into Employee Values ('NS-435','Kerenza',25)";
        db.execSQL(sql);
        sql = "Insert Into Employee Values ('NV-124','Ermintrude',27)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if exists Employee");
    }
}
