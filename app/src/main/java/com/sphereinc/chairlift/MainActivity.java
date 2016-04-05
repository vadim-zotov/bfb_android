package com.sphereinc.chairlift;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sphereinc.chairlift.adapter.UserSearchAdapter;
import com.sphereinc.chairlift.common.ApplicationContextProvider;
import com.sphereinc.chairlift.common.ImageHandler;
import com.sphereinc.chairlift.common.Keys;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.fragments.DepartmantUserTeamFragment;
import com.sphereinc.chairlift.fragments.FeedbackFragment;
import com.sphereinc.chairlift.fragments.UserFragment;
import com.sphereinc.chairlift.views.customsearchable.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private List<BackPressedCallback> backPressedCallbacks = new ArrayList<>();

//    private MaterialSearchView searchView;

    public interface BackPressedCallback {
        void onBackPressed();

        boolean canBeDissmised();
    }

    @Override
    public void onBackPressed() {
        if (backPressedCallbacks != null && !backPressedCallbacks.isEmpty()) {
            for (BackPressedCallback callback : backPressedCallbacks) {
                if (callback.canBeDissmised()) {
                    getSupportFragmentManager().popBackStack();
                    backPressedCallbacks.remove(callback);
                } else {
                    callback.onBackPressed();
                }
            }
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {


                    case R.id.my_profile:
                        backPressedCallbacks.clear();
                        switchToUserFragment(null);
                        return true;

                    case R.id.directory:
                        backPressedCallbacks.clear();
                        DepartmantUserTeamFragment fragment = new DepartmantUserTeamFragment();
                        fragment.setOnUserClickListener(new UserSearchAdapter.OnUserClickListener() {
                            @Override
                            public void onItemClick(int userId) {
//                                getSearchView().closeSearch();
                                switchToUserFragment(userId);
                            }
                        });

                        backPressedCallbacks.add(fragment);
                        switchFragment(fragment);
                        return true;

//                    case R.id.feedback:
//                        switchFeedbackFragment();
//                        return true;

                    case R.id.logout:
                        logout();
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        setFirstMenuItemSelected();
    }


    public void setActiveMenuItem(Integer menuItemPosition) {
        if (menuItemPosition != null) {
            navigationView.getMenu().getItem(menuItemPosition).setChecked(true);
        } else {
            navigationView.getMenu().getItem(0).setChecked(false);
            navigationView.getMenu().getItem(1).setChecked(false);
            navigationView.getMenu().getItem(2).setChecked(false);
//            navigationView.getMenu().getItem(3).setChecked(false);
        }
    }

    private void logout() {
        Preferences.getInstance().clearStoredData();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }


    public void setHeaderData() {
        View header = navigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.tv_header_username)).setText(Preferences.getInstance().userName());
        ((TextView) header.findViewById(R.id.tv_header_role)).setText(Preferences.getInstance().userRole());
        if (Preferences.getInstance().userAvatarUrl() != null) {
            ImageHandler.getSharedInstance(getApplicationContext()).with(getApplicationContext()).
                    load(Preferences.getInstance().userAvatarUrl()).into((CircleImageView) header.findViewById(R.id.iv_profile_image));
        }
    }

    private void setFirstMenuItemSelected() {
        setActiveMenuItem(0);
        switchToUserFragment(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void switchToUserFragment(Integer userId) {
        Bundle bundle = new Bundle();

        if (userId == null) {
            bundle.putString(Keys.USER_FRAGMENT_TYPE, Keys.USER_FRAGMENT_TYPE_MY_PROFILE);
        } else {
            bundle.putString(Keys.USER_FRAGMENT_TYPE, Keys.USER_FRAGMENT_TYPE_USER);
            bundle.putInt(Keys.USER_ID, userId);
        }
        Fragment fragment = new UserFragment();

        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            int userId = data.getIntExtra("user_id", -1);
            if (userId > 0) {
                switchToUserFragment(userId);
            }
        }
    }


    private void switchFeedbackFragment() {
        switchFragment(new FeedbackFragment());
    }

//    public MaterialSearchView getSearchView() {
//        return searchView;
//    }
}
