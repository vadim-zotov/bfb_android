package com.sphereinc.chairlift.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdapter;
import com.sphereinc.chairlift.api.entity.response.UserSearchResult;
import com.sphereinc.chairlift.api.facade.DepartmentFacade;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.DepartmentFacadeImpl;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.decorator.DividerItemDecorator;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class DirectoryFragment extends Fragment {

    private UserFacade userFacade = new UserFacadeImpl();

    private DepartmentFacade departmentFacade = new DepartmentFacadeImpl();

    @Bind(R.id.recycler_view)
    RecyclerView _recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.directory_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_directory));

        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDepartmentUsers();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        mSearchMenuItem.setVisible(true);


        MenuItemCompat.setOnActionExpandListener(mSearchMenuItem,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem menuItem) {
                        // Return true to allow the action view to expand
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                        loadDepartmentUsers();
                        return true;
                    }
                });

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String name) {
                userFacade.searchByName(name, new Callback<UserSearchResult>() {
                    @Override
                    public void onResponse(Response<UserSearchResult> response) {
                        UserSearchResult result = response.body();
                        if (result != null && result.getUsers() != null) {
                            _recyclerView.swapAdapter(new UserAdapter(result.getUsers()), false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
                return false;
            }

        });
    }

    private void loadDepartmentUsers() {
        DialogUtils.showDialog(getString(R.string.loading), getActivity());
        userFacade.getDepartmentUsers(Preferences.getInstance().userDepartmentId(), new Callback<UserSearchResult>() {
            @Override
            public void onResponse(Response<UserSearchResult> response) {
                UserSearchResult result = response.body();
                if (result != null && result.getUsers() != null) {
                    _recyclerView.swapAdapter(new UserAdapter(result.getUsers()), false);
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
}
