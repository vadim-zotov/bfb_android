package com.sphereinc.chairlift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.common.ImageHandler;
import com.squareup.picasso.Callback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.profile_image)
        public CircleImageView _ivProfile;

        @Bind(R.id.tv_profile)
        public TextView _tvProfile;

        @Bind(R.id.tv_username)
        public TextView _tvUserName;

        @Bind(R.id.tv_role)
        public TextView _tvRole;

        @Bind(R.id.tv_location)
        public TextView _tvLocation;

        private int userId;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            ButterKnife.bind(this, v);
        }

        @Override
        public void onClick(View view) {
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        User user = users.get(position);
        if (user.getAvatar() != null && user.getAvatar().getIcon().getUrl() != null) {

            ImageHandler.getSharedInstance(context).load(user.getAvatar().getIcon().getUrl()).into(holder._ivProfile, new Callback() {
                @Override
                public void onSuccess() {
                    holder._ivProfile.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    holder._ivProfile.setVisibility(View.GONE);
                }
            });


        } else {
            holder._ivProfile.setVisibility(View.GONE);
        }

        holder._tvUserName.setText(user.getUserName());
        holder._tvRole.setText(user.getJobRole().getTitle());
        holder._tvProfile.setText(user.getInitials());
        if (user.getLocation() != null) {
            holder._tvLocation.setText(user.getLocation().getCity());
        }
        holder.setUserId(user.getId());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}