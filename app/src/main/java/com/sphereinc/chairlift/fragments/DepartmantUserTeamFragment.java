package com.sphereinc.chairlift.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sphereinc.chairlift.MainActivity;
import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdapter;
import com.sphereinc.chairlift.adapter.UserSearchAdapter;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.entity.response.DepartmentsSearchResult;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.DepartmentFacade;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.DepartmentFacadeImpl;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.common.utils.ErrorHandler;
import com.sphereinc.chairlift.converters.ModelConverter;
import com.sphereinc.chairlift.views.SelectorListLayout;
import com.sphereinc.chairlift.views.models.DepartmentModel;
import com.sphereinc.chairlift.views.models.ParentModel;
import com.sphereinc.chairlift.views.models.TreeModel;
import com.sphereinc.chairlift.views.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmantUserTeamFragment extends Fragment
        implements MainActivity.BackPressedCallback {

    @Bind(R.id.flyt_main)
    FrameLayout _flytMain;

    private UserFacade userFacade = new UserFacadeImpl();

    private boolean canBeDissmissed = true;

    private DepartmentFacade departmentFacade = new DepartmentFacadeImpl();

    private Stack<SelectorListLayout> layouts = new Stack<>();

    private MainActivity activity;

    private UserSearchAdapter.OnUserClickListener onUserClickListener;

    public String nextSearchString;
    public boolean searchInProgress = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dtu_fragment, container, false);
        activity = (MainActivity) getActivity();
        activity.setActiveMenuItem(1);
        activity.getSupportActionBar().setTitle(getString(R.string.title_directory));

        ButterKnife.bind(this, v);

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onBackPressed() {
        if (layouts.size() > 1) {
            _flytMain.removeView(layouts.pop());
            if (layouts.size() == 1) {
                canBeDissmissed = true;
            }
        }
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        mSearchMenuItem.setVisible(true);

//        activity.getSearchView().setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(final String name) {
//                searchUserByName(name);
//                return true;
//            }
//        });
//
//        activity.getSearchView().setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });

    }

    private void searchUserByName(String name) {
        nextSearchString = "";
        searchInProgress = true;
        userFacade.searchByName(name, new Callback<UserSearchResult>() {
            @Override
            public void onResponse(Response<UserSearchResult> response) {
                if (nextSearchString.isEmpty()) {
                    UserSearchResult result = response.body();
                    if (result != null && result.getUsers() != null) {
                        UserSearchAdapter adapter = new UserSearchAdapter(getActivity(), R.layout.user_row, result.getUsers(),
                                onUserClickListener);
//                        activity.getSearchView().setAdapter(adapter);
                    }
                    searchInProgress = false;
                } else {
                    searchUserByName(nextSearchString);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                ErrorHandler.checkConnectionError(getContext(), t);
            }
        });
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

                List<TreeModel> departmentsAndTeams = new ArrayList<>(2);

                if (result != null &&
                        result.getDepartments() != null) {
                    List<TreeModel> departmetParentModels = ModelConverter.fromDepartmentsToModels(result.getDepartments());
                    ParentModel departmentParentModel = new ParentModel();
                    departmentParentModel.setTitle(getString(R.string.department));
                    departmentParentModel.setChilds(departmetParentModels);
//                    create parents for department and teams
                    departmentsAndTeams.add(departmentParentModel);
                }


                addSelectorLayout(departmentsAndTeams, true);

                DialogUtils.hideProgressDialogs();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                DialogUtils.hideProgressDialogs();
            }
        });
    }

    private void addSelectorLayout(List<TreeModel> treeModels, boolean canBeDissmissed) {
        this.canBeDissmissed = canBeDissmissed;
        SelectorListLayout selectorListLayout = new SelectorListLayout(getActivity(), treeModels,
                new SelectorListLayout.OnLoadChildsListener() {
                    @Override
                    public void onItemClick(TreeModel model) {

                        if (model instanceof DepartmentModel) {
                            final DepartmentModel castedDepartmentModel = (DepartmentModel) model;
                            if (!((DepartmentModel) model).isChildsAreLoaded()) {
                                if (castedDepartmentModel.getDepartment().getUsersCount() > 0) {

                                    DialogUtils.showDialog(getString(R.string.loading), getActivity());
                                    userFacade.getDepartmentUsers(castedDepartmentModel.getDepartment().getId(),
                                            new Callback<UserSearchResult>() {
                                                @Override
                                                public void onResponse(Response<UserSearchResult> response) {
                                                    castedDepartmentModel.setChildsAreLoaded(true);
                                                    UserSearchResult result = response.body();
                                                    if (result != null && result.getUsers() != null) {
                                                        castedDepartmentModel.getChilds().addAll(ModelConverter.fromUsersToModels(result.getUsers()));
                                                    }
                                                    addSelectorLayout(castedDepartmentModel.getChilds(), false);
                                                    DialogUtils.hideProgressDialogs();
                                                }

                                                @Override
                                                public void onFailure(Throwable t) {
                                                    t.printStackTrace();
                                                    DialogUtils.hideProgressDialogs();
                                                    ErrorHandler.checkConnectionError(getContext(), t);
                                                }
                                            });


                                }
                            }
                        }

                        if (model.getChilds() != null &&
                                !model.getChilds().isEmpty()) {
                            addSelectorLayout(model.getChilds(), false);
                        }
                    }
                }, onUserClickListener);
        layouts.add(selectorListLayout);
        _flytMain.addView(selectorListLayout);
    }


    public boolean canBeDissmised() {
        return canBeDissmissed;
    }

    public void setOnUserClickListener(UserSearchAdapter.OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }
}


