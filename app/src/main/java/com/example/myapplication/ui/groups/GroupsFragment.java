package com.example.myapplication.ui.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.GroupItemActivity;
import com.example.myapplication.R;
import com.example.myapplication.SessionManager;
import com.example.myapplication.ui.database.DatabaseHelper;
import com.example.myapplication.ui.database.classes.Group;

import java.util.List;

// A fragment representing a list of Items.
public class GroupsFragment extends Fragment implements GroupAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private SessionManager session;
    private DatabaseHelper databaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SessionManager sessionManager = new SessionManager(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());

        if (sessionManager.isLoggedIn()) {
            String userEmail = sessionManager.getUserEmail();

            // Get the list of groups
            List<Group> groups = databaseHelper.getGroups(userEmail);

            // Print the list
            groupAdapter = new GroupAdapter(groups, this);
            recyclerView.setAdapter(groupAdapter);
        }
        return view;
    }


    @Override
    public void onItemClick(Group group) {
        // Stock the selected group name
        // Redirect to the group page
        session = new SessionManager(getActivity());
        session.setGroupName(group.getName());
        startActivity(new Intent(getActivity(), GroupItemActivity.class));
    }
}