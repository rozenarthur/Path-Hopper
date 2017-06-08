package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parent extends AppCompatActivity {

    TextView tv1;
    Button create_kids, report, play_game, log, leaderboard;
    Spinner choose_player;
    public static String player;

 SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        tv1 = (TextView)findViewById(R.id.title_login);

        create_kids = (Button)findViewById(R.id.create);
        report = (Button)findViewById(R.id.Report);
        play_game = (Button)findViewById(R.id.play_game);
        log = (Button)findViewById(R.id.log);
        leaderboard = (Button)findViewById(R.id.leaderboard);

        choose_player = (Spinner)findViewById(R.id.choose_player);

        sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
        final String username = sharedPref.getString("username", "");

        tv1.setText("Logged in as: " + username);

        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        String choose = "Choose a player to play Game as:";
        list.add(choose);

        Map<String,?> keys = sharedPref.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){ //check all sharedprefences
            if (entry.getKey().contains(username + "/kids/")){ //adds every kids account from logged in user to spinner
                list.add(entry.getValue().toString());
            }
        }

        adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_player.setAdapter(adapter);

        int position = adapter.getPosition(choose);
        choose_player.setSelection(position); //sets the choose player string as default

        create_kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parent.this, CreateKids.class);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parent.this, LeaderBoard.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Parent.this, ParentReport.class);
                startActivity(intent);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player = choose_player.getSelectedItem().toString();
                if(!player.contains("Choose a player to play Game as:")){ //goes to a player's log file
                    Intent intent = new Intent(Parent.this, PlayerLog.class);
                    startActivity(intent);
                }

                else{//tells user to select a player's log to view
                    Toast.makeText(getBaseContext(), "Select a player to view their log file" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

        play_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player = choose_player.getSelectedItem().toString();

                if(!player.contains("Choose a player to play Game as:")){ //checks if kids account exists
                    Intent intent = new Intent(Parent.this, Start_Screen.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(getBaseContext(), "You need to select a Kids Account!" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
