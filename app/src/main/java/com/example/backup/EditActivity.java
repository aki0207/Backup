package com.example.backup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TestOpenHelper helper;
    Cursor c;
    int i_year = 0;
    int i_month = 0;
    int i_day = 0;

    String month = "";
    String day = "";
    String current_day = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        i_year = getIntent().getIntExtra("YEAR", 0);
        i_month = getIntent().getIntExtra("MONTH", 0);
        i_day = getIntent().getIntExtra("DAY", 0);

        if (i_month < 10) {
            month = String.format("%02d", i_month);
        } else {
            month = String.valueOf(i_month);
        }

        if (i_day < 10) {
            day = String.format("%02d", i_day);
        } else {
            day = String.valueOf(i_day);
        }

        current_day = String.valueOf(i_year) + "-" + month + day;


        helper = new TestOpenHelper(this);


        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        Button edit_button = (Button) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (helper == null) {

                    helper = new TestOpenHelper(getApplicationContext());

                }


                if (db == null) {

                    db = helper.getWritableDatabase();

                }

                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                // 選択されているカテゴリを取得
                String selected_category = (String) spinner.getSelectedItem();
                //金額
                EditText price_form = (EditText) findViewById(R.id.price_form);
                String entered_price = price_form.getText().toString();


                //更新する前に検索する
                String sql = "select price from amount_used where date = ? and category = ?";
                String[] array = {current_day,selected_category};
                c = db.rawQuery(sql, array);


                if (!(c.isBeforeFirst())) {


                    boolean isEof = c.moveToFirst();
                    while (isEof) {
                        TextView tv = (TextView) findViewById(R.id.the_amount);
                        tv.setText(String.format("%d", c.getInt(0)));
                        isEof = c.moveToNext();
                    }

                    ContentValues values = new ContentValues();
                    values.put("category", selected_category);
                    values.put("price", entered_price);

                    db.beginTransaction();
                    db.update("amount_used", values, "category = '" + selected_category + "'", null);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    Context context = getApplicationContext();
                    Toast.makeText(context, "更新が完了しました", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
