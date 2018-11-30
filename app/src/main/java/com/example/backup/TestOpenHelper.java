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
        super(context, null, null, DB_VERSION);
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

        //アプリ起動後一回のみテーブルを作成する


        // table row insert
        /*db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','交遊費', 10000);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','生活費', 20000);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','飲食費', 30000);");
        db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','買い物費', 0);");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // データベースの変更が生じた場合は、ここに処理を記述する。
    }
}