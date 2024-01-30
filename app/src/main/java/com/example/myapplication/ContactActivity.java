package com.example.myapplication;

import static java.sql.DriverManager.println;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView searchResultsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> searchResults;
    protected DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        SessionManager sessionManager = new SessionManager(this);

        // Initialize UI components
        searchEditText = findViewById(R.id.searchEditText);
        searchResultsListView = findViewById(R.id.searchResultsListView);

        // Initialize database
        dbHelper = new DatabaseHelper(this);

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

        // Set up list item click listener
        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedUser = searchResults.get(position);
                String user1 = sessionManager.getUserEmail();
                dbHelper.addContact(user1, selectedUser);
                startActivity(new Intent(ContactActivity.this, MainActivity.class));
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
