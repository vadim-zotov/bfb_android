package com.sphereinc.chairlift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sphereinc.chairlift.MainActivity;
import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.DepartmentModelAdapter;
import com.sphereinc.chairlift.adapter.UserAdapter;
import com.sphereinc.chairlift.api.entity.Department;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.DepartmentFacade;
import com.sphereinc.chairlift.api.facadeimpl.DepartmentFacadeImpl;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.converters.DepartmentConverter;
import com.sphereinc.chairlift.views.SelectorListLayout;
import com.sphereinc.chairlift.views.models.DepartmentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmantUserTeamFragment extends Fragment
        implements MainActivity.BackPressedCallback {

    @Bind(R.id.flyt_main)
    FrameLayout _flytMain;


    private boolean canBeDissmissed = true;

    private DepartmentFacade departmentFacade = new DepartmentFacadeImpl();

    private Stack<SelectorListLayout> layouts = new Stack<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dtu_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_directory));
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onBackPressed() {
        if (layouts.size() > 0) {
            _flytMain.removeView(layouts.pop());
            if(layouts.isEmpty()){
                canBeDissmissed = true;
            }
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDepartments();
    }

    private void loadDepartments() {
        DialogUtils.showDialog(getString(R.string.loading), getActivity());
        departmentFacade.getDepartments(new Callback<DepartmentsSearchResult>() {
            @Override
            public void onResponse(Response<DepartmentsSearchResult> response) {
                DepartmentsSearchResult result = response.body();
                if (result != null &&
                        result.getDepartments() != null) {
                    List<DepartmentModel> parentModels = DepartmentConverter.fromDepartmentsToModels(result.getDepartments());
                    addSelectorLayout(parentModels);
                }
                DialogUtils.hideProgressDialogs();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                DialogUtils.hideProgressDialogs();
            }
        });
    }

    private void addSelectorLayout(List<DepartmentModel> departmentModels) {
        canBeDissmissed = false;
        SelectorListLayout selectorListLayout = new SelectorListLayout(getActivity(), departmentModels,
                new SelectorListLayout.OnLoadChildsListener() {
                    @Override
                    public void onItemClick(DepartmentModel departmentModel) {
                        if (departmentModel.getChildDepartment() != null &&
                                !departmentModel.getChildDepartment().isEmpty()) {
                            addSelectorLayout(departmentModel.getChildDepartment());
                        }
                    }
                });
        layouts.add(selectorListLayout);
        _flytMain.addView(selectorListLayout);
    }


    public boolean canBeDissmised(){
        return canBeDissmissed;
    }

}
