package com.example.myapplication.ui.database;

import static java.sql.DriverManager.println;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.ui.database.classes.Group;
import com.example.myapplication.ui.database.classes.User;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

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


    public static final String TABLE_GROUPUSER = "group_user";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tables creation
        try {

            //Table user
            db.execSQL("CREATE TABLE " + TABLE_USER + "(" +
                    COL_NAME + " TEXT, " +
                    COL_SURNAME + " TEXT, " +
                    COL_GROUPID + " INTEGER," +
                    COL_ROLEID +" INTEGER,"+
                    COL_EMAIL +" TEXT PRIMARY KEY," +
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
                    COL_EMAIL + " TEXT, " +
                    COL_TYPEID + " INTEGER, " +
                    "FOREIGN KEY (" + COL_EMAIL + ") REFERENCES user(" + COL_EMAIL + ")," +
                    "FOREIGN KEY (" + COL_TYPEID + ") REFERENCES group_type(" + COL_ID + ")" +
                    ");");

            //Table role_user
            db.execSQL("CREATE TABLE " + TABLE_ROLEUSER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ROLETITLE + " TEXT " +
                    ");");
            // Inserting values into role_user table
            db.execSQL("INSERT INTO " + TABLE_ROLEUSER + " (" + COL_ROLETITLE + ") VALUES ('User')");
            db.execSQL("INSERT INTO " + TABLE_ROLEUSER + " (" + COL_ROLETITLE + ") VALUES ('Administrator')");


            //Table status_user
            db.execSQL("CREATE TABLE " + TABLE_STATUSUSER + "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_STATUSTITLE + " TEXT " +
                    ");");
            // Inserting values into status_user table
            db.execSQL("INSERT INTO " + TABLE_STATUSUSER + " (" + COL_STATUSTITLE + ") VALUES ('Active')");
            db.execSQL("INSERT INTO " + TABLE_STATUSUSER + " (" + COL_STATUSTITLE + ") VALUES ('Non-Active')");
            db.execSQL("INSERT INTO " + TABLE_STATUSUSER + " (" + COL_STATUSTITLE + ") VALUES ('Suspended')");

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
            // Inserting values into group_type table
            db.execSQL("INSERT INTO " + TABLE_GROUPTYPE + " (" + COL_GROUPTYPE + ") VALUES ('Individual group')");
            db.execSQL("INSERT INTO " + TABLE_GROUPTYPE + " (" + COL_GROUPTYPE + ") VALUES ('Non-individual group')");

            //Table contacts
            db.execSQL("CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COL_USER1 + " TEXT, " +
                    COL_USER2 + " TEXT, " +
                    "UNIQUE (" + COL_USER1 + "," + COL_USER2 + ")," +
                    "FOREIGN KEY (" + COL_USER1 + ") REFERENCES " + TABLE_USER + "(" + COL_EMAIL + ")," +
                    "FOREIGN KEY (" + COL_USER2 + ") REFERENCES " + TABLE_USER + "(" + COL_EMAIL + ")" +
                    ");");

            //Table group_user
            db.execSQL("CREATE TABLE " + TABLE_GROUPUSER + "(" +
                    COL_EMAIL + " TEXT, " +
                    COL_GROUPID + " INTEGER, " +
                    "UNIQUE (" + COL_EMAIL + "," + COL_GROUPID + ")," +
                    "FOREIGN KEY (" + COL_EMAIL + ") REFERENCES " + TABLE_USER + "(" + COL_EMAIL + ")," +
                    "FOREIGN KEY (" + COL_GROUPID + ") REFERENCES " + TABLE_GROUPS + "(" + COL_ID + ")" +
                    ");");


            Log.e("DatabaseHelper", "Success to create TABLES");

        } catch (SQLException e) {
            Log.e("DatabaseHelper", "Error to create TABLES", e);
        }
    }


    // Methods to manipulate objects ( insertUser, insertGroup, etc.)

    // Insert a user in the database
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

    // Insert a group in the database
    public long insertGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, group.getName());
        values.put(COL_EMAIL, group.getEmail());
        values.put(COL_TYPEID, group.getTypeId());

        long newRowId = db.insert(TABLE_GROUPS, null, values);
        Log.d("DatabaseHelper", "New line with ID: " + newRowId);
        return newRowId;
    }

    // Add a contact
    public long addContact(String user1, String user2) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        if (!Objects.equals(user1, user2)){
            values.put(COL_USER1, user1);
            values.put(COL_USER2, user2);

            long newRowId = db.insert(TABLE_CONTACTS, null, values);
            Log.d("DatabaseHelper", "New contact with ID: " + newRowId);
            return newRowId;
        }else {
            Log.d("DatabaseHelper", "Error in adding contact");
            return 0;
        }
    }


    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Update database if needed
    }


    //check if user is in the database, used while login
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_EMAIL};
        String selection = COL_EMAIL + "=? AND " + COL_PWD + "=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    // Find a user in the database with his email
    @SuppressLint("Range")
    public User getUserbyEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[]{COL_NAME, COL_SURNAME, COL_GROUPID, COL_ROLEID, COL_EMAIL, COL_PWD, COL_STATUSID, COL_SERIALNUMBER},
                COL_EMAIL + "=?", new String[]{email}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        assert cursor != null;
        user.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        user.setSurname(cursor.getString(cursor.getColumnIndex(COL_SURNAME)));
        user.setGroupId(cursor.getInt(cursor.getColumnIndex(COL_GROUPID)));
        user.setRoleId(cursor.getInt(cursor.getColumnIndex(COL_ROLEID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        user.setPwd(cursor.getString(cursor.getColumnIndex(COL_PWD)));
        user.setStatusId(cursor.getInt(cursor.getColumnIndex(COL_STATUSID)));
        user.setSerialNumber(cursor.getString(cursor.getColumnIndex(COL_SERIALNUMBER)));

        cursor.close();
        return user;
    }

    // Search all user in the database with their email
    // Used in the ContactActivity while writing
    public ArrayList<String> searchUsers(String query) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Perform a database query to search for users
        ArrayList<String> searchResults = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + COL_EMAIL + " FROM " + TABLE_USER +
                " WHERE " + COL_EMAIL + " LIKE '%" + query + "%'", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String user = cursor.getString(0);
                searchResults.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return searchResults;
    }

    // Verify if the contact already exist or not
    public boolean contactExists(String user1, String user2) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE (" + COL_USER1 + " = ? AND " + COL_USER2 + " = ?) OR (" + COL_USER2 + " = ? AND " + COL_USER1 + " = ?)";
        Cursor cursor = null;
        boolean exists = false;
        try {
            cursor = db.rawQuery(query, new String[]{user1, user2, user1, user2});
            if (cursor != null && cursor.getCount() > 0) {
                exists = cursor.moveToFirst();
            }
        } catch (Exception e) {
            Log.e("TAG", "Error checking if contact exists: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return exists;
    }

    // Method to modify user data in the database
    public void modifyUserData(String name, String surname, String pwd, String serialnumber, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_USER + " SET " + COL_NAME + " = ?, "+ COL_SURNAME + " = ?, " + COL_PWD + " = ?, "+ COL_SERIALNUMBER + " = ? WHERE " + COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name, surname, pwd, serialnumber, email});
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    // Find a group in the database with his name
    @SuppressLint("Range")
    public Group getGroupbyName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[]{COL_NAME, COL_EMAIL, COL_TYPEID},
                COL_NAME + "=?", new String[]{name}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Group group = new Group();
        group.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        group.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        group.setTypeId(cursor.getInt(cursor.getColumnIndex(COL_TYPEID)));

        cursor.close();
        return group;
    }

    // Find a group in the database with his name
    @SuppressLint("Range")
    public boolean doesGroupExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[]{COL_NAME},
                COL_NAME + "=?", new String[]{name}, null, null, null, null);

        boolean groupExists = cursor != null && cursor.getCount() > 0;

        if (cursor != null)
            cursor.close();

        return groupExists;
    }
}

