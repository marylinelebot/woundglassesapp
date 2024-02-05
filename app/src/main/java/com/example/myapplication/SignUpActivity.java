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
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
        Context context = this;
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please complete all the fields")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Action when OK button is clicked
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    // Create a user in the database
                    User userId = new User(surname, name, 0, 0, email, password, 2, null);
                    databaseHelper.insertUser(userId);


                    // Verify is the account is created in the database
                    if (databaseHelper.checkUser(email, password)) {
                        // Account created
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Account created")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();


                    } else {
                        // Account failed to create
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Account failed to create")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Action when OK button is clicked
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            }
        });
    }
}