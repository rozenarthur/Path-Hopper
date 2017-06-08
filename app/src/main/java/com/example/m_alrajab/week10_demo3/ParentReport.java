package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.Map;

public class ParentReport extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;
    SharedPreferences sharedPref;
    int i = 0;
    int tot_Stage1 = 0;
    int tot_Game = 0;
    RelativeLayout layout;
    int width = 0;
    int height = 0;


    static int n =50;
    static int k =50;
    static int games =50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_report);

        tv1 = (TextView) findViewById(R.id.parent_account);
        tv2 = (TextView) findViewById(R.id.kids_numbers);
        tv3 = (TextView) findViewById(R.id.avg_stage1);
        tv4 = (TextView) findViewById(R.id.avg_game);

        layout = (RelativeLayout)findViewById(R.id.parent_layout);

        Stetho.initializeWithDefaults(this);

        sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
        final String username = sharedPref.getString("username", "");
        final String kidsName = Parent.player;

        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(layout.getMeasuredHeight()> 0){
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    height = layout.getMeasuredHeight();

                    RelativeLayout.LayoutParams mp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                    mp.setMargins(0,(int)(height *.25), 0, 0);
                    tv2.setLayoutParams(mp);

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                    lp.setMargins(0,(int)(height *.47), 0, 0);
                    tv3.setLayoutParams(lp);

                    RelativeLayout.LayoutParams kp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                    kp.setMargins(0,(int)(height *.66), 0, 0);
                    tv4.setLayoutParams(kp);
                }
            }
        });

        tv1.setText(username + "'s Parent Report");

        n = 50;
        k = 50;
        games = 50;

        Map<String, ?> keys = sharedPref.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet()) { //check all sharedprefences
            if (entry.getKey().contains(username + "/kids/")) {
                i++; //count the number of kids accounts
            }

            if(entry.getKey().contains(username + "/kid/") && entry.getKey().contains("/StageOneBeaten"))
            {//gets the totalof Stage1Beaten for every child from parent account

                String val = entry.getValue().toString();
                tot_Stage1 += Integer.parseInt(val);
            }//end if

            if(entry.getKey().contains(username + "/kid/") && entry.getKey().contains("/GameBeaten"))
            {//gets the totalof Stage1Beaten for every child from parent account

                String val = entry.getValue().toString();
                tot_Game += Integer.parseInt(val);
            }//end if
        }//end for

        tv2.setText("Number of Kids Accounts: " + i);
        tv3.setText("Total times Stage 1 Beaten(All kids Accounts): " + tot_Stage1);
        tv4.setText("Total times Game Beaten(All kids Accounts): " + tot_Game);

        n = n * i;
        k = k * tot_Stage1;
        games = games * tot_Game;

    }
}
