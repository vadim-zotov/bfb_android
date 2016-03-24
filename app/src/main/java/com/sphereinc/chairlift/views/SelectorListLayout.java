package com.sphereinc.chairlift.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.DirectoryModelAdapter;
import com.sphereinc.chairlift.adapter.UserSearchAdapter;
import com.sphereinc.chairlift.views.models.DepartmentModel;
import com.sphereinc.chairlift.views.models.ParentModel;
import com.sphereinc.chairlift.views.models.TreeModel;
import com.sphereinc.chairlift.views.models.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectorListLayout extends LinearLayout {

    private Context context;

    private OnLoadChildsListener listener;
    private UserSearchAdapter.OnUserClickListener onUserClickListener;

//    public enum SELECTOR_TYPE {MY_PROFILE, USER}


    @Bind(R.id.recycler_view)
    RecyclerView _recyclerView;

    private List<TreeModel> treeModels;

    public SelectorListLayout(Context context, List<TreeModel> treeModels,
                              OnLoadChildsListener listener, UserSearchAdapter.OnUserClickListener onUserClickListener) {
        super(context);
        this.context = context;
        this.treeModels = treeModels;
        this.listener = listener;
        this.onUserClickListener = onUserClickListener;
        build();
    }

    private void build() {
        View v = View.inflate(context, R.layout.selector_list_layout,
                null);
        addView(v);
        ButterKnife.bind(this);

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        _recyclerView.swapAdapter(new DirectoryModelAdapter(context, treeModels,
                new DirectoryModelAdapter.OnDepartmentRowClickListener() {
                    @Override
                    public void onItemClick(DepartmentModel item) {
                        listener.onItemClick(item);
                    }
                },
                new DirectoryModelAdapter.OnParentRowClickListener() {
                    @Override
                    public void onItemClick(ParentModel item) {
                        listener.onItemClick(item);
                    }
                },
                new DirectoryModelAdapter.OnUserRowClickListener() {
                    @Override
                    public void onItemClick(UserModel userModel) {
                        onUserClickListener.onItemClick(userModel.getId());
                    }
                }), false);
    }


    public interface OnLoadChildsListener {
        void onItemClick(TreeModel treeModel);
    }



}
