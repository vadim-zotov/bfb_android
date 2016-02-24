package com.sphereinc.chairlift.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.api.entity.RequiredSkill;

import java.util.List;


public class UserJobRelatedAdapter extends RecyclerView.Adapter<UserJobRelatedAdapter.ViewHolder> {

    private List<RequiredSkill> requiredSkills;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_skill_name);
        }
    }

    public UserJobRelatedAdapter(List<RequiredSkill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    @Override
    public UserJobRelatedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_job_related_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(requiredSkills.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return requiredSkills.size();
    }
}