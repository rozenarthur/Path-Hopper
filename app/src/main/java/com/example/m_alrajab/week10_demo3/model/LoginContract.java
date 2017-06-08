package com.example.m_alrajab.week10_demo3.model;

import android.provider.BaseColumns;

/**
 * Created by Arthur on 3/24/2017.
 */

public class LoginContract {
    private LoginContract() {}

    public static class LoginEntry implements BaseColumns {
        //columns for each user for what values they have
        public static final String COL_USERNAME = "username";
        public static final String COL_NAME = "name";
        public static final String COL_PASSWORD = "password";
        public static final String COL_EMAIL = "email";
    }

        public static final String TABLE_NAME = "login_table";
        //the type of each columns
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        LoginEntry._ID + " INTEGER PRIMARY KEY," +
                        LoginEntry.COL_USERNAME + " TEXT," +
                        LoginEntry.COL_NAME + " TEXT," +
                        LoginEntry.COL_PASSWORD + " TEXT," +
                        LoginEntry.COL_EMAIL + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
}
