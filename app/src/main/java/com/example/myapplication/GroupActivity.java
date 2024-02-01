package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;
import com.example.myapplication.ui.database.classes.User;
import com.google.android.material.snackbar.Snackbar;

public class GroupActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonCreate;
    private CheckBox checkBox;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private int group_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        sessionManager = new SessionManager(this);

        databaseHelper = new DatabaseHelper(this);


        editTextName = findViewById(R.id.editTextName);
        checkBox = findViewById(R.id.myCheckbox);
        buttonCreate = findViewById(R.id.buttonCreate);
        String email = sessionManager.getUserEmail();
        // Create a group
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values
                String name = editTextName.getText().toString().trim();

                // Check if the checkbox is checked
                if (checkBox.isChecked()) {
                    group_type = 1;
                }

                // Verify if the fields aren't empty
                if (name.isEmpty()) {
                    Snackbar.make(v, "Please enter a name", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    // check if the group already exists
                    if(databaseHelper.doesGroupExist(name)){
                        Snackbar.make(v, "This group already exists", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        // Create a group in the database
                        Group group = new Group(name, email, group_type);
                        databaseHelper.insertGroup(group);

                        // Verify is the group is created in the database
                        if (databaseHelper.doesGroupExist(name)) {
                            // Account created
                            Snackbar.make(v, "Group created", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            startActivity(new Intent(GroupActivity.this, MainActivity.class));
                        } else {
                            // Account failed to create
                            Snackbar.make(v, "Group failed to create", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            startActivity(new Intent(GroupActivity.this, MainActivity.class));
                        }
                    }

                }

            }
        });
    }
}