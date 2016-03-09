package com.sphereinc.chairlift;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sphereinc.chairlift.common.ImageHandler;
import com.sphereinc.chairlift.common.Keys;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.fragments.DepartmantUserTeamFragment;
import com.sphereinc.chairlift.fragments.DirectoryFragment;
import com.sphereinc.chairlift.fragments.FeedbackFragment;
import com.sphereinc.chairlift.fragments.UserFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private List<BackPressedCallback> backPressedCallbacks = new ArrayList<>();

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
                        switchToUserFragment();
                        return true;

                    case R.id.directory:
//                        switchFragment(new DirectoryFragment());
                        DepartmantUserTeamFragment fragment = new DepartmantUserTeamFragment();
                        backPressedCallbacks.add(fragment);
                        switchFragment(fragment);
                        return true;

                    case R.id.feedback:
                        switchFeedbackFragment();
                        return true;

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
        navigationView.getMenu().getItem(0).setChecked(true);
        switchToUserFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void switchToUserFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.USER_FRAGMENT_TYPE, Keys.USER_FRAGMENT_TYPE_MY_PROFILE);
        Fragment fragment = new UserFragment();
        fragment.setArguments(bundle);
        switchFragment(fragment);
    }

    private void switchFeedbackFragment() {
        switchFragment(new FeedbackFragment());
    }



}
