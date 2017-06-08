package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

public class PlayerLog extends AppCompatActivity {

    TextView tv;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_log);

        tv = (TextView)findViewById(R.id.his);
        Stetho.initializeWithDefaults(this);

        sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
        final String username = sharedPref.getString("username", "");
        final String kidsName = Parent.player;
        final String dateOfCreation = sharedPref.getString(username + "/kid/" + kidsName + "/DateOfCreation", "");
        final String Stage1Beaten = sharedPref.getString(username + "/kid/" + kidsName + "/StageOneBeaten", "");
        final String GameBeaten = sharedPref.getString(username + "/kid/" + kidsName + "/GameBeaten", "");
        final String lastPlayed = sharedPref.getString(username + "/kid/" + kidsName + "/LastPlayed", "");

        tv.setText(
             kidsName + "'s Log File" + "\n" +
                "Account created on: " + dateOfCreation +"\n" +
                "Number of times Stage 1 Beaten: " + Stage1Beaten + "\n" +
                     "Number of times Game Beaten: " + GameBeaten + "\n"+
                "Last played on: " + lastPlayed +"\n"
        );//end setText statement
    }
}
