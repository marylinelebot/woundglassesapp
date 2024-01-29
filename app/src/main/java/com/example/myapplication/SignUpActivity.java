package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword;
    private Button buttonSignUp;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sessionManager = new SessionManager(this);

        databaseHelper = new DatabaseHelper(this);


        editTextSurname = findViewById(R.id.editTextSurname);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Create a user
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values
                String surname = editTextSurname.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Verify if the fields aren't empty
                if (surname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a user in the database
                    User userId = new User(surname, name, 0, 0, email, password, 2, null);
                    databaseHelper.insertUser(userId);


                    // Verify is the account is created in the database
                    if (databaseHelper.checkUser(email, password)) {
                        // Account created
                        Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        sessionManager.setLoggedIn(true);
                    } else {
                        // Account failed to create
                        Toast.makeText(SignUpActivity.this, "Account failed to create", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}