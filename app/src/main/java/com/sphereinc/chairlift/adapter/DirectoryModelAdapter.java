package com.sphereinc.chairlift.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.common.fontawesome.TextAwesome;
import com.sphereinc.chairlift.views.models.DepartmentModel;
import com.sphereinc.chairlift.views.models.ParentModel;
import com.sphereinc.chairlift.views.models.TreeModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DirectoryModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TreeModel> treeModels;

    private OnDepartmentRowClickListener departmentRowClickListener;
    private OnParentRowClickListener parentRowClickListener;

    private static final int TYPE_PARENT = 1;
    private static final int TYPE_DEPARTMENT = 2;

    public static class ChildViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        public TextView _tvName;

        @Bind(R.id.tv_child_count)
        public TextView _tvChildsCount;

        @Bind(R.id.tv_has_childs)
        public TextAwesome _tvHasChilds;

        public ChildViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final DepartmentModel item, final OnDepartmentRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public static class ParentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        public TextView _tvName;

        @Bind(R.id.tv_child_count)
        public TextView _tvChildsCount;

        @Bind(R.id.tv_has_childs)
        public TextAwesome _tvHasChilds;

        public ParentViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final ParentModel item, final OnParentRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public DirectoryModelAdapter(List<TreeModel> treeModels, OnDepartmentRowClickListener departmentRowClickListener,
                                 OnParentRowClickListener parentRowClickListener) {
        this.treeModels = treeModels;
        this.departmentRowClickListener = departmentRowClickListener;
        this.parentRowClickListener = parentRowClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        TreeModel treeModel = treeModels.get(position);
        if (treeModel instanceof ParentModel) {
            return TYPE_PARENT;
        } else {
            return TYPE_DEPARTMENT;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case TYPE_PARENT:
                View v1 = inflater.inflate(R.layout.directory_parent_row, viewGroup, false);
                viewHolder = new ParentViewHolder(v1);
                break;
            case TYPE_DEPARTMENT:
                View v2 = inflater.inflate(R.layout.department_row, viewGroup, false);
                viewHolder = new ChildViewHolder(v2);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case TYPE_PARENT:
                ParentViewHolder parentViewHolder = (ParentViewHolder) viewHolder;

                ParentModel parentModel = (ParentModel) treeModels.get(position);

                parentViewHolder._tvName.setText(parentModel.getTitle());
                if (parentModel.getChilds().isEmpty()) {
                    parentViewHolder._tvHasChilds.setVisibility(View.GONE);
                } else {
                    parentViewHolder._tvHasChilds.setVisibility(View.VISIBLE);
                    parentViewHolder._tvChildsCount.setText("(" + parentModel.getChilds().size() + "sub-departments)");
                }
                parentViewHolder.bind((ParentModel) treeModels.get(position), parentRowClickListener);

                break;
            case TYPE_DEPARTMENT:
                ChildViewHolder childViewHolder = (ChildViewHolder) viewHolder;

                DepartmentModel departmentModel = (DepartmentModel) treeModels.get(position);

                childViewHolder._tvName.setText(departmentModel.getDepartment().getName());
                if (departmentModel.getChilds().isEmpty()) {
                    childViewHolder._tvHasChilds.setVisibility(View.GONE);
                    if(departmentModel.getDepartment().getUsersCount() > 0) {
                        childViewHolder._tvChildsCount.setText("(" + departmentModel.getDepartment().getUsersCount() + " users)");
                    }
                } else {
                    childViewHolder._tvHasChilds.setVisibility(View.VISIBLE);
                    childViewHolder._tvChildsCount.setText("(" + departmentModel.getChilds().size() + " sub-departments)");
                }
                childViewHolder.bind((DepartmentModel) treeModels.get(position), departmentRowClickListener);

                break;
            default:
                break;
        }
    }


    public interface OnDepartmentRowClickListener {
        void onItemClick(DepartmentModel departmentModel);
    }

    public interface OnParentRowClickListener {
        void onItemClick(ParentModel parentModel);
    }

    @Override
    public int getItemCount() {
        return treeModels.size();
    }


}