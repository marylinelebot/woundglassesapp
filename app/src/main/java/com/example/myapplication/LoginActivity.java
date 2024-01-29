package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonSignup;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        //Login fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // check if the user is in the database
                if (databaseHelper.checkUser(email, password)) {
                    // Success of the connection
                    Toast.makeText(LoginActivity.this, "Success of the connection", Toast.LENGTH_SHORT).show();
                    sessionManager.setLoggedIn(true);
                    sessionManager.setUserEmail(email);

                    // Redirect to MainActivity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {

                    // Connection failed
                    Toast.makeText(LoginActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Go to SignUpActivity to create an account
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }
}
