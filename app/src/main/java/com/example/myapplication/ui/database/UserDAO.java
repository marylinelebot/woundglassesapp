package com.example.myapplication.ui.database;

/// UserDAO.java

import static com.example.myapplication.ui.database.DatabaseHelper.COL_EMAIL;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_USER1;
import static com.example.myapplication.ui.database.DatabaseHelper.COL_USER2;
import static com.example.myapplication.ui.database.DatabaseHelper.TABLE_CONTACTS;
import com.example.myapplication.ui.database.classes.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() {
        dbHelper.close();
    }



    // Find the contacts of a user in the database
    public List<User> getContacts(String userEmail) {
        List<User> contacts = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve from the contacts table
        String[] projection = {
                COL_USER1,
                COL_USER2
        };

        // Define the selection criteria to filter contacts for the specified user
        String selection = COL_USER1 + " = ? OR " + COL_USER2 + " = ?";
        String[] selectionArgs = { userEmail, userEmail };

        // Query the contacts table
        Cursor cursor = db.query(
                TABLE_CONTACTS,
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
                    String user1 = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER1));
                    String user2 = cursor.getString(cursor.getColumnIndexOrThrow(COL_USER2));
                    // Assuming you have a method to fetch user details based on email
                    if (!user1.equals(userEmail)) {
                        User contact = dbHelper.getUserbyEmail(user1);
                        if (contact != null) {
                            contacts.add(contact);
                        }
                    }
                    if (!user2.equals(userEmail)) {
                        User contact = dbHelper.getUserbyEmail(user2);
                        if (contact != null) {
                            contacts.add(contact);
                        }
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return contacts;
    }

}