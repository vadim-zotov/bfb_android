package com.sphereinc.chairlift.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.DepartmentModelAdapter;
import com.sphereinc.chairlift.views.models.DepartmentModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectorListLayout extends LinearLayout {

    private Context context;

    private OnLoadChildsListener listener;

//    public enum SELECTOR_TYPE {MY_PROFILE, USER}


    @Bind(R.id.recycler_view)
    RecyclerView _recyclerView;

    private List<DepartmentModel> departmentModels;

    public SelectorListLayout(Context context, List<DepartmentModel> departmentModels,
                              OnLoadChildsListener listener) {
        super(context);
        this.context = context;
        this.departmentModels = departmentModels;
        this.listener = listener;
        build();
    }

    private void build() {
        View v = View.inflate(context, R.layout.selector_list_layout,
                null);
        addView(v);
        ButterKnife.bind(this);

        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        _recyclerView.swapAdapter(new DepartmentModelAdapter(departmentModels,
                new DepartmentModelAdapter.OnRowClickListener() {
                    @Override
                    public void onItemClick(DepartmentModel item) {
                        listener.onItemClick(item);
                    }
                }), false);
    }


    public interface OnLoadChildsListener {
        void onItemClick(DepartmentModel departmentModel);
    }


}
