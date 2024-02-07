package com.example.myapplication.ui.groups;

// GroupAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.database.classes.Group;

import java.util.List;

// Make the list of groups clickable so you can be redirected to the group page
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Group> groups;
    private OnItemClickListener listener;


    // Interface for click listener
    public interface OnItemClickListener {
        void onItemClick(Group group);
    }

    public GroupAdapter(List<Group> groups, OnItemClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);

            // Set click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(groups.get(position));
                    }
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_groups, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.nameTextView.setText(group.getName());
    }


    @Override
    public int getItemCount() {
        return groups.size();
    }
}
