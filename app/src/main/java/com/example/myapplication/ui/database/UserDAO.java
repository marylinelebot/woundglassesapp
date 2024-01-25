package com.example.myapplication.ui.database;

/// UserDAO.java

import static com.example.myapplication.ui.database.DatabaseHelper.COL_EMAIL;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_PWD;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_USER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ui.database.classes.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    //Get all users
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    //Get one user, used in getUsers()
    @SuppressLint("Range")
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(Math.toIntExact(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_ID))));
        user.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
        user.setSurname(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SURNAME)));
        user.setGroupId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_GROUPID)));
        user.setRoleId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ROLEID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        user.setPwd(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PWD)));
        user.setStatusId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_STATUSID)));
        user.setSerialNumber(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_SERIALNUMBER)));
        return user;
    }
}