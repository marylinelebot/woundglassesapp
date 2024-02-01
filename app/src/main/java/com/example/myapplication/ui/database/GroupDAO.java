package com.example.myapplication.ui.database;

/// UserDAO.java



import static com.example.myapplication.ui.database.DatabaseHelper.COL_NAME;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_USER1;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_USER2;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_CONTACTS;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_EMAIL;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_GROUPS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;
import com.example.myapplication.ui.database.classes.User;

import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public GroupDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() {
        dbHelper.close();
    }


    //Get all groups
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

    // Find the contacts of a user in the database
    public List<Group> getGroups(String userEmail) {
        List<Group> groups = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve from the contacts table
        String[] projection = {
                COL_NAME
        };

        // Define the selection criteria to filter contacts for the specified user
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = { userEmail};

        // Query the contacts table
        Cursor cursor = db.query(
                TABLE_GROUPS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Iterate through the cursor to retrieve contact emails
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String groupname = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));

                    // Assuming you have a method to fetch user details based on email
                    Group group = dbHelper.getGroupbyName(groupname);
                    groups.add(group);
                }
            } finally {
                cursor.close();
            }
        }

        return groups;
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