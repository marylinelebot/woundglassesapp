package com.example.myapplication.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.ui.database.classes.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myapp2.db";
    private static final int DATABASE_VERSION = 5;

    public static final String TABLE_USER = "user";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_SURNAME = "surname";
    public static final String COL_GROUPID = "group_id";
    public static final String COL_ROLEID = "role_id";
    public static final String COL_EMAIL = "email";
    public static final String COL_PWD = "pwd";
    public static final String COL_STATUSID = "status_id";
    public static final String COL_SERIALNUMBER = "serial_number";

    public static final String TABLE_GROUPS = "groups";
    public static final String COL_TYPEID = "type_id";

    public static final String TABLE_ROLEUSER = "role_user";
    public static final String COL_ROLETITLE = "role_title";

    public static final String TABLE_STATUSUSER = "status_user";
    public static final String COL_STATUSTITLE = "status_title";

    public static final String TABLE_DOCUMENTS = "documents";
    public static final String COL_DOCUMENTSTITLE = "title";
    public static final String COL_DOCUMENTSPATH = "path";
    public static final String COL_DOCUMENTSINFORMATIONS = "informations";


    public static final String TABLE_GROUPTYPE = "group_type";
    public static final String COL_GROUPTYPE = "group_type";


    public static final String TABLE_CONTACTS = "contacts";
    public static final String COL_USER1 = "user1";
    public static final String COL_USER2 = "user2";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tables creation
        try {

            //Table user
            db.execSQL("CREATE TABLE " + TABLE_USER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NAME + " TEXT, " +
                    COL_SURNAME + " TEXT, " +
                    COL_GROUPID + " INTEGER," +
                    COL_ROLEID +" INTEGER,"+
                    COL_EMAIL +" TEXT," +
                    COL_PWD + " TEXT, " +
                    COL_STATUSID + " INTEGER, " +
                    COL_SERIALNUMBER + " TEXT," +
                    "FOREIGN KEY (" + COL_GROUPID + ") REFERENCES groups(" + COL_ID + ")," +
                    "FOREIGN KEY (" + COL_ROLEID + ") REFERENCES role_user(" + COL_ID + ")," +
                    "FOREIGN KEY (" + COL_STATUSID + ") REFERENCES status_user(" + COL_ID + ")" +
                    ");");

            //Table groups
            db.execSQL("CREATE TABLE " + TABLE_GROUPS + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NAME + " TEXT, " +
                    COL_TYPEID + " INTEGER, " +
                    "FOREIGN KEY (" + COL_TYPEID + ") REFERENCES group_type(" + COL_ID + ")" +
                    ");");

            //Table role_user
            db.execSQL("CREATE TABLE " + TABLE_ROLEUSER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ROLETITLE + " TEXT " +
                    ");");

            //Table status_user
            db.execSQL("CREATE TABLE " + TABLE_STATUSUSER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_STATUSTITLE + " TEXT " +
                    ");");

            // Table documents
            db.execSQL("CREATE TABLE " + TABLE_DOCUMENTS + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_DOCUMENTSTITLE + " TEXT, " +
                    COL_DOCUMENTSPATH + " TEXT, " +
                    COL_DOCUMENTSINFORMATIONS + " TEXT " +
                    ");");

            //Table group_type
            db.execSQL("CREATE TABLE " + TABLE_GROUPTYPE + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_GROUPTYPE +  " TEXT " +
                    ");");

            //Table contacts
            db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COL_USER1 + " INTEGER, " +
                    COL_USER2 + " INTEGER, " +
                    "UNIQUE (" + COL_USER1 + "," + COL_USER2 + ")," +
                    "FOREIGN KEY (" + COL_USER1 + ") REFERENCES " + TABLE_USER + "(" + COL_ID + ")," +
                    "FOREIGN KEY (" + COL_USER2 + ") REFERENCES " + TABLE_USER + "(" + COL_ID + ")" +
                    ");");


            Log.e("DatabaseHelper", "Success to create TABLES");

        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error to create TABLES", e);
        }
    }


    // Methods to manipulate objects ( insertUser, insertGroup, etc.)

    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_SURNAME, user.getSurname());
        values.put(COL_GROUPID, user.getGroupId());
        values.put(COL_ROLEID, user.getRoleId());
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PWD, user.getPwd());
        values.put(COL_STATUSID, user.getStatusId());
        values.put(COL_SERIALNUMBER, user.getSerialNumber());

        long newRowId = db.insert(TABLE_USER, null, values);
        Log.d("DatabaseHelper", "New line with ID: " + newRowId);
        return newRowId;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Update database if needed
    }


    //check if user is in the database, used while login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=? AND " + COL_PWD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }
}