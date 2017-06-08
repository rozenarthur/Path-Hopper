package com.example.m_alrajab.week10_demo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.m_alrajab.week10_demo3.model.LeaderBoardJson;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;
    DatabaseReference DbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        tv1 = (TextView)findViewById(R.id.leaders);
        tv2 = (TextView)findViewById(R.id.leaders2);
        tv3 = (TextView)findViewById(R.id.leaders3);
        tv4 = (TextView)findViewById(R.id.place);


        DbRef = FirebaseDatabase.getInstance().getReference("leaders");

        DbRef.orderByChild("timesGameBeaten").limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //gets all of the children at this level
                String kids_title = "Child Name\n";
                String score_title= "Score (# of times Beaten)\n";
                String parent_title = "Parent Username\n";
                String rank = "\n";

                ArrayList<String> list1 = new ArrayList<String>();
                ArrayList<String> list2 = new ArrayList<String>();
                ArrayList<String> list3 = new ArrayList<String>();
                int i = 1;

                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    LeaderBoardJson person = child.getValue(LeaderBoardJson.class);

                    String place = person.getChildName() + "\n";
                    String place2 = person.getTimesGameBeaten() + "\n";
                    String place3 = person.getParentName() + "\n";
                    rank += i + ". \n";

                    list1.add(place);
                    list2.add(place2);
                    list3.add(place3);


                    tv4.setText(rank);

                    i++;
                }

                Collections.reverse(list1);
                Collections.reverse(list2);
                Collections.reverse(list3);

                String kids = TextUtils.join("", list1); //transfers arraylist of all kids into string
                String scores = TextUtils.join("", list2);//transfers arraylist of all scores into string
                String parents = TextUtils.join("", list3); //transfers arraylist of all parents into string

                tv1.setText(kids_title + kids);
                tv2.setText(score_title + scores);
                tv3.setText(parent_title + parents);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
