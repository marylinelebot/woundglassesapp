package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;

public class GroupActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonCreate;
    private CheckBox checkBox;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private int group_type;
    private Button back;
    private TextView appBarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        sessionManager = new SessionManager(this);

        databaseHelper = new DatabaseHelper(this);


        editTextName = findViewById(R.id.editTextName);
        checkBox = findViewById(R.id.myCheckbox);
        buttonCreate = findViewById(R.id.buttonCreate);
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        appBarText.setText("Create a group");

        //Click listener fir the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this, MainActivity.class));
            }
        });

        Context context = this;
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please enter a name")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    // check if the group already exists
                    if(databaseHelper.doesGroupExist(name)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("This group already exists")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {
                        // Create a group in the database
                        Group group = new Group(name, email, group_type);
                        databaseHelper.insertGroup(group);

                        // Verify is the group is created in the database
                        if (databaseHelper.doesGroupExist(name)) {
                            // Group created
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Group created")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity(new Intent(GroupActivity.this, MainActivity.class));
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {
                            // Group failed to create
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Group failed to create")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity(new Intent(GroupActivity.this, MainActivity.class));
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }

                }

            }
        });
    }
}