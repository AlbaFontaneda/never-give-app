package com.rigobertosl.nevergiveapp.events.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rigobertosl.nevergiveapp.R;
import com.rigobertosl.nevergiveapp.objects.Profile;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    HashMap<String, Profile> names;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameOfMember;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOfMember = (TextView)itemView.findViewById(R.id.name_member);
        }
    }

    /********************************** Constructor of Adapter ************************************/
    public MemberAdapter(HashMap<String, Profile> names){
        this.names = names;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_member, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Profile profile = (new ArrayList<Profile>(names.values())).get(position);
        holder.nameOfMember.setText(profile.getName());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
