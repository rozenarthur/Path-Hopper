package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Start_Screen extends AppCompatActivity {

    Button play, goback;
    TextView tv1;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__screen);

        play = (Button)findViewById(R.id.play);
        goback = (Button)findViewById(R.id.back);
        tv1 = (TextView)findViewById(R.id.pathfinder);

        tv1.setText("" + Parent.player);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
                final String username = sharedPref.getString("username", "");
                final String kidsname = Parent.player;

                Date dNow = new Date();
                SimpleDateFormat ft = //gets the current date
                        new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a");
                ft.setTimeZone(TimeZone.getTimeZone("EST"));

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(username + "/kid/" + kidsname + "/LastPlayed", ft.format(dNow));
                editor.apply();

                Intent intent = new Intent(Start_Screen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Start_Screen.this, Parent.class);
                startActivity(intent);
            }
        });
    }
}
