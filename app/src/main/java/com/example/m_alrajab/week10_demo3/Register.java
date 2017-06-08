package com.example.m_alrajab.week10_demo3;

import android.content.ContentValues;
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

import static com.example.m_alrajab.week10_demo3.model.LoginContract.LoginEntry.COL_PASSWORD;
import static com.example.m_alrajab.week10_demo3.model.LoginContract.LoginEntry.COL_USERNAME;
import static com.example.m_alrajab.week10_demo3.model.LoginContract.TABLE_NAME;

public class Register extends AppCompatActivity {

    EditText username, password, confirm_password;
    Button register;

    SharedPreferences sharedPref;
    LoginDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.btn_Reg);

        username = (EditText) findViewById(R.id.Reg_UserName);
        password = (EditText) findViewById(R.id.Reg_pass1);
        confirm_password = (EditText) findViewById(R.id.Reg_pass2);

        register.setOnClickListener(new MyRegister());

        mDbHelper = new LoginDbHelper(this);
    }


    //class for registration button that checks if email is @montclair.edu and password is 6 characters or more
    class MyRegister implements View.OnClickListener {
        @Override
        public void onClick(View v) {//as the button is clicked
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            String[] Projection = {
                    LoginContract.LoginEntry._ID,
                    COL_USERNAME, //returns username
            };

            String selection = COL_USERNAME + " = ?"; //where username
            String[] selectionArgs = {username.getText().toString()};

            Cursor cursor = db.query(TABLE_NAME, Projection, selection, selectionArgs, null, null, null);

            if (cursor.getCount() > 0) {//checks if username exists in db, by if any rows already exist in db
                username.setError("This username already exists"); //give error if any rows exist
                username.getError();
            }

            if (password.getText().toString().length() < 6) {//check if password field less than 6 characters
                password.setError("Your password must be at least 6 characters long");
                password.getError(); //if true then give an error
            }

            if (username.getText().toString().trim().length() == 0) {//checks if username is blank
                username.setError("The username must not contain any whitespace!");
                username.getError(); //if true then give an error
            }

            if (!confirm_password.getText().toString().equals(password.getText().toString())) {//checks if confirm password is equal to
                confirm_password.setError("Your passwords do not match");
                confirm_password.getError(); //if true then give an error
            }

            //if(!password.getText().toString().matches(".*\\w.*")){//checks if password uses charcters
            if (!password.getText().toString().matches(".*[a-zA-z0-9]*[a-zA-z][0-9].*")) {//checks if password uses charcters
                password.setError("Password must begin with a letter and have at least one number");
                password.getError();
            } else if (confirm_password.getText().toString().equals(password.getText().toString()) &&
                    cursor.getCount() < 1 &&
                    password.getText().toString().length() >= 6 &&
                    password.getText().toString().matches(".*[a-zA-z0-9]*[a-zA-z][0-9].*") &&
                    username.getText().toString().trim().length() > 0) {//goes to landing page, if no previous if statements were violated
                Intent intent = new Intent(Register.this, Parent.class);

                ContentValues values = new ContentValues();
                values.put(COL_USERNAME, username.getText().toString());
                values.put(COL_PASSWORD, password.getText().toString());

                long newRowID = db.insert(TABLE_NAME, null, values);

                sharedPref = getSharedPreferences("user", Context.MODE_APPEND);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", username.getText().toString());

                editor.apply();
                startActivity(intent);
            }
            cursor.close();
        }
    }
}
