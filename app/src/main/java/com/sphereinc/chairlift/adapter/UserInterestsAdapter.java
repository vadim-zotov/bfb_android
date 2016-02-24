package com.sphereinc.chairlift.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.api.entity.Interest;

import java.util.List;


public class UserInterestsAdapter extends RecyclerView.Adapter<UserInterestsAdapter.ViewHolder> {

    private List<Interest> interests;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_interest_name);
        }
    }

    public UserInterestsAdapter(List<Interest> interests) {
        this.interests = interests;
    }

    @Override
    public UserInterestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_interest_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(interests.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return interests.size();
    }
}