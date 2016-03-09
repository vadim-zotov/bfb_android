package com.sphereinc.chairlift.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.common.fontawesome.TextAwesome;
import com.sphereinc.chairlift.views.models.DepartmentModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DepartmentModelAdapter extends RecyclerView.Adapter<DepartmentModelAdapter.ViewHolder> {

    private List<DepartmentModel> departmentModels;

    private OnRowClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        public TextView _tvName;

        @Bind(R.id.tv_has_childs)
        public TextAwesome _tvHasChilds;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final DepartmentModel item, final OnRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public DepartmentModelAdapter(List<DepartmentModel> departmentModels, OnRowClickListener listener) {
        this.departmentModels = departmentModels;
        this.listener = listener;
    }

    @Override
    public DepartmentModelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_row, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DepartmentModel departmentModel = departmentModels.get(position);
        holder._tvName.setText(departmentModel.getDepartment().getName());
        if (departmentModel.getChildDepartment().isEmpty()) {
            holder._tvHasChilds.setVisibility(View.GONE);
        } else {
            holder._tvHasChilds.setVisibility(View.VISIBLE);
        }
        holder.bind(departmentModels.get(position), listener);
    }

    public interface OnRowClickListener {
        void onItemClick(DepartmentModel departmentModel);
    }

    @Override
    public int getItemCount() {
        return departmentModels.size();
    }
}