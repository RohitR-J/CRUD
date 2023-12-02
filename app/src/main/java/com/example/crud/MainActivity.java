package com.example.crud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name = findViewById(R.id.name);
        EditText loc = findViewById(R.id.loc);
        EditText desig = findViewById(R.id.desig);
        Button btn = findViewById(R.id.btn);
        Button btn2 = findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_str = name.getText().toString().trim();
                String loc_str = loc.getText().toString().trim();
                String desig_str = desig.getText().toString().trim();

                if(!name_str.isEmpty() && !loc_str.isEmpty() && !desig_str.isEmpty()){
                    SQLiteDatabase db = openOrCreateDatabase("user_data.db",MODE_PRIVATE, null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS user_table (Name TEXT, Location TEXT, Designation TEXT);");
                    ContentValues values = new ContentValues();
                    values.put("Name",name_str);
                    values.put("Location",loc_str);
                    values.put("Designation",desig_str);
                    db.insert("user_table",null,values);
                    Toast.makeText(MainActivity.this,"Submitted Successfully",Toast.LENGTH_SHORT).show();
                    db.close();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = openOrCreateDatabase("user_data.db",MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("SELECT * FROM user_table",null);
                String result = "";
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("Name"));
                    @SuppressLint("Range") String loc = cursor.getString(cursor.getColumnIndex("Location"));
                    @SuppressLint("Range") String desig = cursor.getString(cursor.getColumnIndex("Designation"));
                    result += "Name : "+name+"\n"+"Location : "+loc+"\n"+"Designation : "+desig+"\n\n";
                }
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("res",result);
                startActivity(intent);
            }
        });
    }
}




