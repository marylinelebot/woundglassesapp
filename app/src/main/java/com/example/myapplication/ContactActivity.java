package com.example.myapplication;

import static java.sql.DriverManager.println;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView searchResultsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> searchResults;
    protected DatabaseHelper dbHelper;
    private Button back;
    private TextView appBarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        SessionManager sessionManager = new SessionManager(this);

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        appBarText.setText("Add Contacts");

        //Click listener fir the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivity.this, MainActivity.class));
            }
        });

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        if (sessionManager.isLoggedIn()){
            // Set up search functionality
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    println(s.toString());
                    searchUser(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


        // Set up list item click listener
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = searchResults.get(position);
                String connectedUser = sessionManager.getUserEmail();
                boolean exists = dbHelper.contactExists(connectedUser, selectedUser);

                // Check if the contact already exists
                if (exists) {
                    Snackbar.make(view, "This contact already exists", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    // Add the contact if it doesn't already exist
                    dbHelper.addContact(connectedUser, selectedUser);
                    startActivity(new Intent(ContactActivity.this, MainActivity.class));
                }
            }
        });
    }

    // search the users in the database while writing
    private void searchUser(String query) {
        // Update ListView with search results
        searchResults = dbHelper.searchUsers(query);
        // Update ListView with search results
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        searchResultsListView.setAdapter(adapter);
    }


}
