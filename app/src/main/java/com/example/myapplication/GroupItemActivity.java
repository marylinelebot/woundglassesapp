package com.example.myapplication;


import static android.app.PendingIntent.getActivity;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;
import com.example.myapplication.ui.database.classes.GroupUser;
import com.example.myapplication.ui.database.classes.User;
import com.example.myapplication.ui.users.UserAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GroupItemActivity extends AppCompatActivity {
    private SessionManager session;
    private Button back, addUser, showMembers, updateGroup, saveButton;
    private TextView appBarText, name, surname;
    private Group group;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView2;
    private UserAdapter userAdapter;
    private PopupWindow popupWindow;
    private EditText searchEditText;
    private ListView searchResultsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> searchResults;
    private CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        Context context = this;
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        addUser = findViewById(R.id.buttonAdd);
        showMembers = findViewById(R.id.buttonShow);
        updateGroup = findViewById(R.id.buttonUpdate);

        session = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        appBarText.setText("Group details");

        //Click listener for the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupItemActivity.this, MainActivity.class));
            }
        });


        // Get group data
        group = new Group();
        group = databaseHelper.getGroupbyName(session.getGroupName());
        String group_type = databaseHelper.getGroupTypeById(group.getTypeId());

        // Display group data in TextViews
        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        TextView groupTypeTextView = findViewById(R.id.groupTypeTextView);

        groupNameTextView.setText(group.getName());
        groupTypeTextView.setText(group_type);

        //Click listener for the "Add a Member" button
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inflate the layout for the popup
                View popupView = LayoutInflater.from(context).inflate(R.layout.add_member_popup, null);

                // Initialize UI components from the popup layout
                searchEditText = popupView.findViewById(R.id.searchEditText);
                searchResultsListView = popupView.findViewById(R.id.searchResultsListView);

                if (session.isLoggedIn()) {
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
                        boolean exists = databaseHelper.contactExists(selectedUser, group.getName());

                        // Check if the contact already exists
                        if (exists) {
                            Snackbar.make(view, "This contact already exists", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            GroupUser groupUser = new GroupUser(selectedUser, group.getName());
                            databaseHelper.insertGroupUser(groupUser);;
                        }
                    }
                });

                // Create the popup window
                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // Show the popup window
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            }

            // search the users in the database while writing
            private void searchUser(String query) {
                // Update ListView with search results
                searchResults = databaseHelper.searchContacts(query, session.getUserEmail());
                // Update ListView with search results
                adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, searchResults);
                searchResultsListView.setAdapter(adapter);
            }
        });

        //Click listener for the "Show Members" button
        showMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inflate the layout for the popup
                View popupView = LayoutInflater.from(context).inflate(R.layout.show_members_popup, null);

                // Initialize UI components from the popup layout
                name = popupView.findViewById(R.id.nameTextView);
                surname = popupView.findViewById(R.id.surnameTextView);

                recyclerView2 = popupView.findViewById(R.id.recyclerView);
                recyclerView2.setLayoutManager(new LinearLayoutManager(context));

                if (session.isLoggedIn()){

                    List<User> users = databaseHelper.getGroupMembers(group.getName());

                    userAdapter = new UserAdapter(users);
                    recyclerView2.setAdapter(userAdapter);

                    // Create the popup window
                    popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // Show the popup window
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                }

            }

        });


        //Click listener for the "Update Group" button
        updateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Inflate the layout for the popup
                View popupView = LayoutInflater.from(context).inflate(R.layout.update_group_popup, null);

                // Initialize UI components from the popup layout
                name = popupView.findViewById(R.id.nameEditText);
                checkBox = popupView.findViewById(R.id.myCheckbox);
                saveButton = popupView.findViewById(R.id.saveButton);

                if (session.isLoggedIn()){
                    Group groups = databaseHelper.getGroupbyName(group.getName());
                    if (groups != null) {
                        // Display user details in the EditText fields
                        name.setText(groups.getName());
                        if(groups.getTypeId() == 1){
                            checkBox.setChecked(true);
                        }else{
                            checkBox.setChecked(false);
                        }
                    }

                    // Set OnClickListener for the save button
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Get the new user data
                            int group_type;

                            // Check if the checkbox is checked
                            if (checkBox.isChecked()) {
                                group_type = 1;
                            } else {
                                group_type = 2;
                            }

                            // Modify the user data
                            databaseHelper.modifyGroupData(group_type, group.getName());
                            startActivity(new Intent(GroupItemActivity.this, GroupItemActivity.class));
                        }
                    });

                    // Create the popup window
                    popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    // Show the popup window
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                }

            }

        });
    }
}
