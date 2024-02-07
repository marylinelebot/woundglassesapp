package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonSignup, back;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private TextView appBarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        Context context = this;

        //Login fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignup = findViewById(R.id.buttonSignup);
        appBarText = findViewById(R.id.name_activity);
        back = findViewById(R.id.button_back);

        appBarText.setText("Login");

        //Click listener for the "Back" button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                User user = databaseHelper.getUserbyEmail(email);

                // check if the user is in the database
                if (user != null) {
                    if(password.equals(user.getPwd())) {
                        // Success of the connection
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Sucess of the connection")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        sessionManager.setLoggedIn(true);
                                        sessionManager.setUserEmail(email);

                                        // Redirect to MainActivity
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {
                        // Connection failed
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Incorrect password")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } else {

                    // Connection failed
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Incorrect email")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
