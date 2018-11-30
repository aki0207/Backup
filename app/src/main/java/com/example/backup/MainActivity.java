package com.example.backup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    Cursor c;
    SQLiteDatabase db;
    SharedPreferences pref;
    boolean flg = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestOpenHelper helper = new TestOpenHelper(this);
        db = helper.getReadableDatabase();
        pref = getSharedPreferences("InsertFlg", Context.MODE_PRIVATE);
        flg = pref.getBoolean("FlG", false);

        try {

            //初回だけカラム追加を行なう
            if (!flg) {

                db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','交遊費', 10000);");
                db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','生活費', 20000);");
                db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','飲食費', 30000);");
                db.execSQL("insert into amount_used(date,category,price) values ('2018-1101','買い物費', 0);");

                //フラグをtrueにし、以後このif文内の処理は行なわない
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("InsertFlg", true);
                editor.apply();

            }
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);




           /* c = db.query("amount_used", new String[]{"category", "price"},
                    null, null, null, null, null);*/

           //交遊費
           String sql = "select * from amount_used where category = ?";
            c = db.rawQuery(sql,new String[]{"交遊費"});
            boolean isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView)findViewById(R.id.excursion_fee);
                tv.setText(String.format("%d", c.getInt(2)));
                isEof = c.moveToNext();
            }

            //生活費
            c = db.rawQuery(sql,new String[]{"生活費"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView)findViewById(R.id.cost_of_living);
                tv.setText(String.format("%d", c.getInt(2)));
                isEof = c.moveToNext();
            }


        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }

        }

    }

}
