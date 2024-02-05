package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword;
    private Button buttonSignUp;

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private Button back;
    private TextView appBarText;


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
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        appBarText.setText("Sign up");

        //Click listener fir the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }
        });

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
                    Snackbar.make(v, "Please complete all the fields", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //Toast.makeText(SignUpActivity.this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a user in the database
                    User userId = new User(surname, name, 0, 0, email, password, 2, null);
                    databaseHelper.insertUser(userId);


                    // Verify is the account is created in the database
                    if (databaseHelper.checkUser(email, password)) {
                        // Account created
                        Snackbar.make(v, "Account created", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    } else {
                        // Account failed to create
                        Snackbar.make(v, "Account failed to create", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(SignUpActivity.this, "Account failed to create", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}