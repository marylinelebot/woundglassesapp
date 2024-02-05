package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;
public class EditUserActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText, serialnumberEditText;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    private User user;
    private Button back;
    private TextView appBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Initialize UI components
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        serialnumberEditText = findViewById(R.id.serialnumberEditText);
        saveButton = findViewById(R.id.saveButton);
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        appBarText.setText("Edit User");

        //Click listener fir the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
            }
        });

        dbHelper = new DatabaseHelper(this);
        SessionManager sessionManager = new SessionManager(this);

        // Check if a user is connected
        if (sessionManager.isLoggedIn()) {
            // Get user details
            user = dbHelper.getUserbyEmail(sessionManager.getUserEmail());
            if (user != null) {
                // Display user details in the EditText fields
                nameEditText.setText(user.getName());
                surnameEditText.setText(user.getSurname());
                emailEditText.setText(user.getEmail());
                passwordEditText.setText(user.getPwd());
                serialnumberEditText.setText(user.getSerialNumber());
            } else {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                finish(); // Finish activity if user not found
            }
        } else {
            Toast.makeText(this, "No logged in user", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set OnClickListener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new user data
                String surname = surnameEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                String mail = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String serialnumber = serialnumberEditText.getText().toString().trim();

                // Modify the user data
                dbHelper.modifyUserData(name, surname, password, serialnumber, mail);

                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
            }
        });
    }

}
