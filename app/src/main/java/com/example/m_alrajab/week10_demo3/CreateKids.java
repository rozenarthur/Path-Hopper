package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CreateKids extends AppCompatActivity {

    Button createbtn;
    EditText name;
    DatabaseReference DbRef;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kids);

        name = (EditText)findViewById(R.id.create_kids_name);
        createbtn = (Button)findViewById(R.id.create_kids_account);

        Stetho.initializeWithDefaults(this);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
                 final String username = sharedPref.getString("username", "");
                DbRef = FirebaseDatabase.getInstance().getReference("leaders");

                DbRef.orderByChild("childName").equalTo(name.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (name.getText().toString().trim().length() == 0) {//checks if name is blank
                            name.setError("The username must not contain any whitespace!");
                        }

                        if (dataSnapshot.getValue() != null) { //player exists in //db
                            name.setError("This Name is already Taken!");
                        }

                        else { //create kids account

                            Date dNow = new Date();
                            SimpleDateFormat ft = //gets the current date
                                    new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a");
                            ft.setTimeZone(TimeZone.getTimeZone("EST"));

                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(username + "/kids/" + name.getText().toString(), name.getText().toString());
                            editor.putString(username + "/kids" , "exists"); //checks if user has a kids account
                            editor.putString(username + "/kid/" + name.getText().toString() + "/DateOfCreation", ft.format(dNow));
                            editor.putString(username + "/kid/" + name.getText().toString() + "/StageOneBeaten", "0");
                            editor.putString(username + "/kid/" + name.getText().toString() + "/GameBeaten", "0");
                            editor.putString(username + "/kid/" + name.getText().toString() + "/LastPlayed", "Has not Played Yet");
                            editor.apply();

                            Toast.makeText(getBaseContext(), "You have created a kids account: " + name.getText().toString() ,Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(CreateKids.this, Parent.class);
                            startActivity(intent);
                        }
                    }//end onDataChange
                    @Override
                    public void onCancelled(DatabaseError error) {}
                });


            }
        });
    }
}
