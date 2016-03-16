package com.sphereinc.chairlift.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.common.ImageHandler;
import com.squareup.picasso.Callback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSearchAdapter extends ArrayAdapter<User> implements Filterable {

    Context context;
    private OnUserClickListener userClickListener;

    private ItemFilter mFilter = new ItemFilter();

    private List<User> users;

    public UserSearchAdapter(Context context, int resourceId,
                             List<User> users, OnUserClickListener userClickListener) {
        super(context, resourceId, users);
        this.users = users;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    private class ViewHolder {

        public CircleImageView _ivProfile;

        public TextView _tvProfile;

        public TextView _tvUserName;

        public TextView _tvRole;

        public TextView _tvLocation;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final User user = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.user_row, null);
            holder = new ViewHolder();

            holder._ivProfile = (CircleImageView) convertView
                    .findViewById(R.id.profile_image);
            holder._tvProfile = (TextView) convertView
                    .findViewById(R.id.tv_profile);
            holder._tvUserName = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder._tvRole = (TextView) convertView
                    .findViewById(R.id.tv_role);
            holder._tvLocation = (TextView) convertView
                    .findViewById(R.id.tv_location);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickListener.onItemClick(user.getId());
            }
        });

        return convertView;
    }


    public interface OnUserClickListener {
        void onItemClick(int userId);
    }


    private class ItemFilter extends Filter {
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.values = users;
            results.count = users.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users = (List<User>) results.values;
            notifyDataSetChanged();
        }

    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
