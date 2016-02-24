package com.sphereinc.chairlift.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sphereinc.chairlift.MainActivity;
import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdditionalSkillsAdapter;
import com.sphereinc.chairlift.adapter.UserInterestsAdapter;
import com.sphereinc.chairlift.adapter.UserJobRelatedAdapter;
import com.sphereinc.chairlift.api.entity.AdditionalSkill;
import com.sphereinc.chairlift.api.entity.Interest;
import com.sphereinc.chairlift.api.entity.RequiredSkill;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.Keys;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.utils.DateUtils;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.decorator.DividerItemDecorator;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private UserFacade userFacade = new UserFacadeImpl();

    @Bind(R.id.profile_image)
    CircleImageView _ivProfile;

    @Bind(R.id.tv_username)
    TextView _tvUserName;

    @Bind(R.id.tv_role)
    TextView _tvRole;

    @Bind(R.id.tv_department_name)
    TextView _tvDepartmentName;


    @Bind(R.id.tv_location)
    TextView _tvLocation;

    @Bind(R.id.tv_about)
    TextView _tvAbout;


    @Bind(R.id.tv_home_phone)
    TextView _tvHomePhone;

    @Bind(R.id.rlyt_home_phone)
    RelativeLayout _rlytHomePhone;

    @Bind(R.id.tv_home_email)
    TextView _tvHomeEmail;

    @Bind(R.id.rlyt_home_email)
    RelativeLayout _rlytHomeEmail;

    @Bind(R.id.tv_employee_since)
    TextView _tvEmployeeSince;

    @Bind(R.id.tv_emeregency_name)
    TextView _tvEmeregencyName;

    @Bind(R.id.tv_emeregency_contact)
    TextView _tvEmeregencyContact;

    @Bind(R.id.tv_emeregency_role)
    TextView _tvEmeregencyRole;

    @Bind(R.id.rlyt_emeregency)
    RelativeLayout _rlytEmeregencyRole;

    @Bind(R.id.tv_additional_medical_info)
    TextView _tvAdditionalMedicalInfo;

    @Bind(R.id.rlyt_additional_medical_info)
    RelativeLayout _rlytAdditionalMedicalInfo;


    @Bind(R.id.tv_birthday)
    TextView _tvBirthday;

    @Bind(R.id.rlyt_birthday)
    RelativeLayout _rlytBirthday;

    @Bind(R.id.tv_college)
    TextView _tvCollege;

    @Bind(R.id.rlyt_college)
    RelativeLayout _rlytCollege;

    private static User user;

    public enum UserFragmentType {MY_PROFILE, USER}

    private UserFragmentType userFragmentType;

    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Keys.USER_FRAGMENT_TYPE_MY_PROFILE.equals(getArguments().getString(Keys.USER_FRAGMENT_TYPE))) {
            userFragmentType = UserFragmentType.MY_PROFILE;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_my_profile));
        } else if (Keys.USER_FRAGMENT_TYPE_USER.equals(getArguments().getString(Keys.USER_FRAGMENT_TYPE))) {
            userFragmentType = UserFragmentType.USER;
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_user));
            userId = getArguments().getInt(Keys.USER_ID);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_fragment, container, false);

        ButterKnife.bind(this, v);
        _ivProfile.bringToFront();
        return v;
    }

    @OnClick({R.id.btn_call})
    public void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + user.getBusinessPhone()));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
    }

    @OnClick({R.id.btn_send_email})
    public void sendEmail() {
        startActivity(Intent.createChooser(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", user.getEmail(), null)), "Send email..."));
    }

    @OnClick({R.id.btn_linkedin})
    public void openLinkedIn() {
        if (user.getLinkedinURL() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getLinkedinURL())));
        }
    }

    @OnClick({R.id.btn_twitter})
    public void openTwitter() {
        if (user.getTwiterURL() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getTwiterURL())));
        }
    }

    @OnClick({R.id.btn_facebook})
    public void openFacebook() {
        if (user.getFacebookURL() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getFacebookURL())));
        }
    }

    @OnClick({R.id.btn_job_related_skills})
    public void openJobRelatedSkills() {
        ((MainActivity) getActivity()).switchFragment(new UserJobRelatedDialog());
    }

    @OnClick({R.id.btn_additional_skills})
    public void openAdditionalSkills() {
        ((MainActivity) getActivity()).switchFragment(new UserAdditionalSkillsFragment());
    }

    @OnClick({R.id.btn_interests})
    public void openInterests() {
        ((MainActivity) getActivity()).switchFragment(new UserInterestsFragment());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        loadData();
    }

    public static class UserJobRelatedDialog extends Fragment {
        @Bind(R.id.recycler_view)
        RecyclerView _recyclerView;

        static UserJobRelatedDialog newInstance() {
            UserJobRelatedDialog f = new UserJobRelatedDialog();
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.user_job_related_fragment, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_job_related_skills));

            ButterKnife.bind(this, v);

            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            _recyclerView.setHasFixedSize(true);
            _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            _recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

            //
            RequiredSkill requiredSkill = new RequiredSkill();
            requiredSkill.setName("Job related additionalSkill 1");
            UserFragment.user.getRequiredSkills().add(requiredSkill);
            requiredSkill = new RequiredSkill();
            requiredSkill.setName("Job related additionalSkill 2");
            UserFragment.user.getRequiredSkills().add(requiredSkill);
            //

            _recyclerView.setAdapter(new UserJobRelatedAdapter(UserFragment.user.getRequiredSkills()));
        }

    }

    public static class UserInterestsFragment extends Fragment {
        @Bind(R.id.recycler_view)
        RecyclerView _recyclerView;

        static UserInterestsFragment newInstance() {
            UserInterestsFragment f = new UserInterestsFragment();
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.user_interests_fragment, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_interests));
            ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            _recyclerView.setHasFixedSize(true);

            _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            _recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

            //
            Interest interest = new Interest();
            interest.setName("interest 1");
            UserFragment.user.getInterests().add(interest);
            interest = new Interest();
            interest.setName("interest 2");
            UserFragment.user.getInterests().add(interest);
            //

            _recyclerView.setAdapter(new UserInterestsAdapter(UserFragment.user.getInterests()));
        }

    }

    public static class UserAdditionalSkillsFragment extends Fragment {
        @Bind(R.id.recycler_view)
        RecyclerView _recyclerView;

        static UserAdditionalSkillsFragment newInstance() {
            UserAdditionalSkillsFragment f = new UserAdditionalSkillsFragment();
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.user_additional_skills_fragment, container, false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_additional_skills));
            ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            _recyclerView.setHasFixedSize(true);

            _recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            _recyclerView.addItemDecoration(new DividerItemDecorator(getActivity()));

            //
            AdditionalSkill additionalSkill = new AdditionalSkill();
            additionalSkill.setName("additional skill 1");
            UserFragment.user.getAdditionalSkills().add(additionalSkill);
            additionalSkill = new AdditionalSkill();
            additionalSkill.setName("additional skill 2");
            UserFragment.user.getAdditionalSkills().add(additionalSkill);
            //

            _recyclerView.setAdapter(new UserAdditionalSkillsAdapter(UserFragment.user.getAdditionalSkills()));
        }

    }

    private void loadData() {
        DialogUtils.showDialog(getString(R.string.loading), getActivity());
        if (UserFragmentType.MY_PROFILE.equals(userFragmentType)) {
            userFacade.getMe(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response) {
                    drawData(response.body());

                    //TODO move to get login details
                    storeUserData(response.body());
                    DialogUtils.hideProgressDialogs();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    DialogUtils.hideProgressDialogs();
                }
            });
        } else if (UserFragmentType.USER.equals(userFragmentType)) {
            userFacade.getUser(userId, new Callback<User>() {
                @Override
                public void onResponse(Response<User> response) {
                    drawData(response.body());
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

    private void storeUserData(User user) {
        Preferences.getInstance().setUserDepartmentId(user.getDepartment().getId());
        Preferences.getInstance().setUserName(user.getUserName());
        Preferences.getInstance().setUserMail(user.getEmail());
        Preferences.getInstance().setUserAvatarUrl(user.getAvatar().getIcon().getUrl());
        Preferences.getInstance().setUserRole(user.getJobRole().getTitle());

        ((MainActivity) getActivity()).setHeaderData();
    }

    private void drawData(User user) {
        this.user = user;

        Picasso.with(getContext())
                .load(user.getAvatar().getDashboard().getUrl())
                .into(_ivProfile);

        _tvUserName.setText(user.getUserName());
        _tvRole.setText(user.getJobRole().getTitle());
        _tvDepartmentName.setText(user.getDepartment().getName());
        _tvAbout.setText(user.getAbout());


        if (user.getLocation() != null) {
            _tvLocation.setText(user.getLocation().getCity());
        }


        if (user.getCellPhone() != null) {
            _tvHomePhone.setText(user.getCellPhone());
        } else {
            _rlytHomePhone.setVisibility(View.GONE);
        }


        if (user.getPersonalEmail() != null) {
            _tvHomeEmail.setText(user.getPersonalEmail());
        } else {
            _rlytHomeEmail.setVisibility(View.GONE);
        }


        if (user.getEmploymentStartDate() != null) {
            _tvEmployeeSince.setText(getString(R.string.my_profile_employee_since) + " " + DateUtils.getDateString(user.getEmploymentStartDate()));
        }


        if (user.getEmergencyFullName() != null || user.getEmergencyPhone() != null || user.getEmergencyRole() != null) {
            _tvEmeregencyName.setText(user.getEmergencyFullName());
            _tvEmeregencyContact.setText(user.getEmergencyPhone());
            _tvEmeregencyRole.setText(user.getEmergencyRole());
        } else {
            _rlytEmeregencyRole.setVisibility(View.GONE);
        }


        if (user.getBirthdayDate() != null) {
            _tvBirthday.setText(DateUtils.getDateString(user.getBirthdayDate()));
        } else {
            _rlytBirthday.setVisibility(View.GONE);
        }


        if (user.getCollege() != null) {
            _tvCollege.setText(user.getCollege());
        } else {
            _rlytCollege.setVisibility(View.GONE);
        }

//         _tvAdditionalMedicalInfo
        _rlytAdditionalMedicalInfo.setVisibility(View.GONE);

    }
}
