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
import com.sphereinc.chairlift.views.models.ParentModel;
import com.sphereinc.chairlift.views.models.UserModel;
import com.squareup.picasso.Callback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context context;
    private OnUserClickListener userRowClickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
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
            ButterKnife.bind(this, v);
        }

        public void bind(final int userId, final OnUserClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(userId);
                }
            });
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public UserAdapter(List<User> users, OnUserClickListener userRowClickListener) {
        this.users = users;
        this.userRowClickListener = userRowClickListener;
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
        holder.bind(user.getId(), userRowClickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserClickListener {
        void onItemClick(int userId);
    }

}