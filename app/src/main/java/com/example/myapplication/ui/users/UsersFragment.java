package com.example.myapplication.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.User;

import java.util.List;

// A fragment representing a list of Items.

public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper;


    //get the contacts of a user from the database to print them on the screen
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SessionManager sessionManager = new SessionManager(this.getActivity());
        databaseHelper = new DatabaseHelper(getActivity());

        if (sessionManager.isLoggedIn()){
            String userEmail = sessionManager.getUserEmail();

            List<User> users = databaseHelper.getContacts(userEmail);

            userAdapter = new UserAdapter(users);
            recyclerView.setAdapter(userAdapter);
        }

        return view;
    }
}