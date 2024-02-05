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
import com.example.myapplication.ui.database.GroupDAO;
import com.example.myapplication.ui.database.classes.Group;

import java.util.List;

// A fragment representing a list of Items.
public class GroupsFragment extends Fragment implements GroupAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private GroupDAO groupDAO;
    private SessionManager session;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SessionManager sessionManager = new SessionManager(this.getActivity());

        if (sessionManager.isLoggedIn()) {
            String userEmail = sessionManager.getUserEmail();

            groupDAO = new GroupDAO(getActivity());
            groupDAO.open();
            List<Group> groups = groupDAO.getGroups(userEmail);
            groupDAO.close();

            groupAdapter = new GroupAdapter(groups, this); // Pass the click listener
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