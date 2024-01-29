package com.example.myapplication;

import static android.view.View.OnClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        //CoordinatorLayout stackedButtonsContainer = findViewById(R.id.stackedButtonsContainer);
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab3 = findViewById(R.id.fab3);
        buttonLogIn = findViewById(R.id.button_login);
        buttonLogOut = findViewById(R.id.button_logout);


        //check if  user is connected
        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            Log.d("Session", "User connected");
            buttonLogIn.setVisibility(View.INVISIBLE);
            buttonLogOut.setVisibility(View.VISIBLE);
        } else {
            Log.d("Session", "User not connected");
            buttonLogIn.setVisibility(View.VISIBLE);
            buttonLogOut.setVisibility(View.INVISIBLE);
        }

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

        //Add contact button
        binding.appBarMain.fab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        //Add group button
        binding.appBarMain.fab2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Group", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        //Video call button
        binding.appBarMain.fab3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Call", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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
                sessionManager.setLoggedIn(false);
                //Redirect the user to the main activity
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });


        //Menu
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Menu navMenu = navigationView.getMenu();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_users, R.id.nav_groups)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    // Functions for the rotating menu
    private void rotateImage() {
        float rotationDegree = isRotated ? 0f : 45f;
        fab.animate().rotation(rotationDegree).start();
        isRotated = !isRotated;
    }

    private void moveButtonsUp() {
        moveButton(fab1, 0f, -200f);
        moveButton(fab2, 0f, -400f);
        moveButton(fab3, 0f, -600f);
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
