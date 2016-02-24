package com.sphereinc.chairlift.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.api.entity.AdditionalSkill;

import java.util.List;


public class UserAdditionalSkillsAdapter extends RecyclerView.Adapter<UserAdditionalSkillsAdapter.ViewHolder> {

    private List<AdditionalSkill> skills;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_additional_skill_name);
        }
    }

    public UserAdditionalSkillsAdapter(List<AdditionalSkill> skills) {
        this.skills = skills;
    }

    @Override
    public UserAdditionalSkillsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_additional_skill_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(skills.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return skills.size();
    }
}