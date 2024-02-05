package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;

public class GroupItemActivity extends AppCompatActivity {
    private SessionManager session;
    private Button back;
    private TextView appBarText;
    private Group group;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        databaseHelper = new DatabaseHelper(this);
        appBarText.setText("Group details");

        //Click listener fir the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupItemActivity.this, MainActivity.class));
            }
        });

        session = new SessionManager(this);
        // Get group data
        group = new Group();
        group = databaseHelper.getGroupbyName(session.getGroupName());
        String grouptype = databaseHelper.getGroupTypeById(group.getTypeId());

        // Display group data in TextViews
        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        TextView groupTypeTextView = findViewById(R.id.groupTypeTextView);

        groupNameTextView.setText(group.getName());
        groupTypeTextView.setText(grouptype);
    }
}
