package com.example.backup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    Cursor c;
    SQLiteDatabase db;
    TestOpenHelper helper;
    //テスト用日付け
    int year = 2018;
    int month = 11;
    int day = 3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (helper == null) {
            helper = new TestOpenHelper(this);

        }
        if (db == null) {
            db = helper.getReadableDatabase();
        }


        try {


            //総額
            String sql = "select sum(price) from amount_used";
            c = db.rawQuery(sql, null);
            boolean isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.the_amount);
                tv.setText(String.format("%d", c.getInt(0)));
                isEof = c.moveToNext();
            }


            //交遊費
            sql = "select price from amount_used where category = ? and date = ?";
            c = db.rawQuery(sql, new String[]{"交遊費","2018-1103"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.excursion_fee);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }

            //生活費
            sql = "select price from amount_used where category = ?";
            c = db.rawQuery(sql, new String[]{"生活費"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.cost_of_living);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }


            //飲食費
            c = db.rawQuery(sql, new String[]{"飲食費"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.food_cost);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }

            //買い物費
            c = db.rawQuery(sql, new String[]{"買い物費"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.shopping_expenses);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }

            //その他1
            c = db.rawQuery(sql, new String[]{"その他1"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.other_one);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }

            //その他2
            c = db.rawQuery(sql, new String[]{"その他2"});
            isEof = c.moveToFirst();
            while (isEof) {
                TextView tv = (TextView) findViewById(R.id.other_two);
                tv.setText(String.format("%d", c.getInt(0)) + "円");
                isEof = c.moveToNext();
            }


            Button go_edit_button = (Button) findViewById(R.id.go_edit_page);
            go_edit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("YEAR",year);
                    intent.putExtra("MONTH",month);
                    intent.putExtra("DAY",day);
                    startActivity(intent);

                }
            });


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
