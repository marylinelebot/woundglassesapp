package com.example.myapplication;

import static android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Button buttonLogIn, buttonLogOut;
    private boolean isRotated = false;
    private boolean areButtonsUp = false;
    private TextView textViewEmail, textViewName;
    private ImageView edit;
    private DatabaseHelper dbHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        buttonLogIn = findViewById(R.id.button_login);
        buttonLogOut = findViewById(R.id.button_logout);

        // Find the NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Get the header view of the NavigationView
        View headerView = navigationView.getHeaderView(0);
        // Find the TextView within the header view
        textViewEmail = headerView.findViewById(R.id.textViewEmail);
        textViewName = headerView.findViewById(R.id.textViewName);
        edit = headerView.findViewById(R.id.editView);

        dbHelper = new DatabaseHelper(this);

        //check if  user is connected
        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            String userEmail = sessionManager.getUserEmail();
            user = dbHelper.getUserbyEmail(userEmail);
            user.setStatusId(1);

            String userSurname = user.getSurname();
            String userName = user.getName();

            Log.d("Session", "User connected");
            buttonLogIn.setVisibility(View.INVISIBLE);
            buttonLogOut.setVisibility(View.VISIBLE);

            // Changing the data type from String to CharSequence to match the setText() function
            CharSequence charSequenceEmail = userEmail.subSequence(0, userEmail.length());
            CharSequence charSequenceName = userName.subSequence(0, userName.length());
            CharSequence charSequenceSurname = userSurname.subSequence(0, userSurname.length());

            if (charSequenceEmail != null) {
                textViewEmail.setText(charSequenceEmail);
                textViewName.setText(charSequenceSurname + " " + charSequenceName);
            }

            // If edit button clicked, go to EditUserActivity
            edit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, EditUserActivity.class));
                }
            });


            //Add contact button
            binding.appBarMain.fab1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ContactActivity.class));
                }

            });

            //Add group button
            binding.appBarMain.fab2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, GroupActivity.class));
                }

            });

            //Video call button
            binding.appBarMain.fab3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, AugmentedRealityActivity.class));
                }
            });

            //Rotating menu button
            binding.appBarMain.fab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    rotateImage();
                    if (areButtonsUp) {
                        moveButtonsDown();
                    } else {
                        moveButtonsUp();
                    }
                    areButtonsUp = !areButtonsUp;
                }
            });

            //Menu
            DrawerLayout drawer = binding.drawerLayout;

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_users, R.id.nav_groups)
                    .setOpenableLayout(drawer)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        } else {
            Log.d("Session", "User not connected");
            buttonLogIn.setVisibility(View.VISIBLE);
            buttonLogOut.setVisibility(View.INVISIBLE);
        }




        //Login or Logout button action
        binding.appBarMain.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        binding.appBarMain.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setStatusId(2);
                sessionManager.setLoggedIn(false);
                //Redirect the user to the main activity
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });


    }

    // Functions for the rotating menu
    private void rotateImage() {
        float rotationDegree = isRotated ? 0f : 45f;
        fab.animate().rotation(rotationDegree).start();
        isRotated = !isRotated;
    }

    private void moveButtonsUp() {
        moveButton(fab1, 0f, -58f);
        moveButton(fab2, 0f, -116f);
        moveButton(fab3, 0f, -174f);
    }

    private void moveButtonsDown() {
        moveButton(fab1, 0f, 0f);
        moveButton(fab2, 0f, 0f);
        moveButton(fab3, 0f, 0f);

    }

    private void moveButton(View button, float translationX, float translationY) {
        button.animate().translationX(translationX).translationY(translationY).setDuration(500).start();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
