package com.example.backup;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestOpenHelper extends SQLiteOpenHelper {
    final static private int DB_VERSION = 1;
    SharedPreferences pref;
    boolean flg = false;

    public TestOpenHelper(Context context) {
        super(context, "Bank.db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // table create
        db.execSQL(
                "create table amount_used("+
                        "   date text not null," +
                        "   category text not null,"+
                        "   price text"+
                        ");"
        );




        // table row insert
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','交遊費', 0);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','生活費', 0);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','飲食費', 0);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','買い物費', 0);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','その他1', 0);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','その他2', 0);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // データベースの変更が生じた場合は、ここに処理を記述する。
    }
}