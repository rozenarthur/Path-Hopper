package com.example.m_alrajab.week10_demo3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.m_alrajab.week10_demo3.model.LoginContract;
import com.example.m_alrajab.week10_demo3.model.LoginDbHelper;
import com.facebook.stetho.Stetho;

import static com.example.m_alrajab.week10_demo3.model.LoginContract.LoginEntry.COL_PASSWORD;
import static com.example.m_alrajab.week10_demo3.model.LoginContract.LoginEntry.COL_USERNAME;
import static com.example.m_alrajab.week10_demo3.model.LoginContract.TABLE_NAME;

public class Login extends AppCompatActivity {

    private EditText username, password;
    private Button register_btn, forgot_password_btn, login_btn;
    LoginDbHelper mDbHelper;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //creates references to the registration and forgotten password buttons
        register_btn = (Button) findViewById(R.id.btn_register_MA);
        login_btn = (Button) findViewById(R.id.btn_login_ma);

        Stetho.initializeWithDefaults(this);
        username = (EditText) findViewById(R.id.editText_name_MA1);
        password = (EditText) findViewById(R.id.editText_name_MA2);

        mDbHelper = new LoginDbHelper(this);

        //when the registration button is clicked, go to registration page
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent); //go to registration page
            }
        });

        //when the login button is clicked
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                String[] Projection = {
                        LoginContract.LoginEntry._ID,
                        COL_USERNAME,
                        COL_PASSWORD, //query returns the username & password
                };

                String selection = COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?"; //where username and password match
                String[] selectionArgs = {username.getText().toString(),//the entered username for COL_USERNAME
                        password.getText().toString()};//the entered password for COL_PASSWORD
                Cursor cursor = db.query(TABLE_NAME, Projection, selection, selectionArgs, null, null, null);

                if (cursor.getCount() > 0) {//if a record of the entered username and password match is found
                    Intent intent = new Intent(Login.this, Parent.class);
                    sharedPref = getSharedPreferences("user", Context.MODE_APPEND); //create shared preference when user logged in
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", username.getText().toString());
                    editor.apply();
                    startActivity(intent);
                }

                else {//if username and password do not match/ no query result found, give error
                    username.setError("Your Username or password is incorrect!");
                    password.setError("Your Username or password is incorrect!");
                }
                cursor.close();
            }
        });
    }
}
