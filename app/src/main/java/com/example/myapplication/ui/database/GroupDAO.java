package com.example.myapplication.ui.database;

/// GroupDAO.java

import static com.example.myapplication.ui.database.DatabaseHelper.COL_EMAIL;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_GROUPID;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_NAME;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_GROUPS;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_GROUPUSER;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        Cursor cursor = database.query(TABLE_GROUPS, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group group = cursorToGroup(cursor);
            groups.add(group);
            cursor.moveToNext();
        }
        cursor.close();
        return groups;
    }


    // Find the groups of a user in the database
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


    //Get one group, used in getGroups()
    @SuppressLint("Range")
    private Group cursorToGroup(Cursor cursor) {
        Group group = new Group();
        group.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME)));
        group.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        group.setTypeId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_TYPEID)));
        return group;
    }

}