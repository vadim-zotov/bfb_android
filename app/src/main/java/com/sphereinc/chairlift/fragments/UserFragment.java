package com.sphereinc.chairlift.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sphereinc.chairlift.MainActivity;
import com.sphereinc.chairlift.R;
import com.sphereinc.chairlift.adapter.UserAdditionalSkillsAdapter;
import com.sphereinc.chairlift.adapter.UserInterestsAdapter;
import com.sphereinc.chairlift.adapter.UserJobRelatedAdapter;
import com.sphereinc.chairlift.api.entity.User;
import com.sphereinc.chairlift.api.facade.UserFacade;
import com.sphereinc.chairlift.api.facadeimpl.UserFacadeImpl;
import com.sphereinc.chairlift.common.ImageHandler;
import com.sphereinc.chairlift.common.Keys;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.fontawesome.TextAwesome;
import com.sphereinc.chairlift.common.utils.DateUtils;
import com.sphereinc.chairlift.common.utils.DialogUtils;
import com.sphereinc.chairlift.common.utils.ErrorHandler;
import com.sphereinc.chairlift.decorator.DividerItemDecorator;

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

    @Bind(R.id.tv_profile)
    public TextView _tvProfile;

    @Bind(R.id.flyt_profile)
    public FrameLayout _flytProfile;

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

    @Bind(R.id.lyt_about)
    LinearLayout _lytAbout;

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

    @Bind(R.id.card_skills_parent)
    CardView _cardSkillsParent;

    @Bind(R.id.rlyt_college)
    RelativeLayout _rlytCollege;

    @Bind(R.id.rlyt_job_related_skills)
    RelativeLayout _rlytJobRelated;

    @Bind(R.id.rlyt_additional_skills)
    RelativeLayout _rlytAdditionalSkills;

    @Bind(R.id.rlyt_interests)
    RelativeLayout _rlytInterests;

    @Bind(R.id.btn_call)
    TextAwesome _btnCall;

    @Bind(R.id.btn_send_email)
    TextAwesome _btnSendEmail;

    @Bind(R.id.btn_linkedin)
    TextAwesome _btnLinkedin;

    @Bind(R.id.btn_twitter)
    TextAwesome _btnTwitter;

    @Bind(R.id.btn_facebook)
    TextAwesome _btnFacebook;

    @Bind(R.id.btn_github)
    TextAwesome _btnGitHub;

    @Bind(R.id.card_additional_info)
    CardView _cardAdditionalInfo;

    private static User user;

    public enum UserFragmentType {MY_PROFILE, USER}

    private UserFragmentType userFragmentType;

    private int userId;

    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Keys.USER_FRAGMENT_TYPE_MY_PROFILE.equals(getArguments().getString(Keys.USER_FRAGMENT_TYPE))) {
            userFragmentType = UserFragmentType.MY_PROFILE;
        } else if (Keys.USER_FRAGMENT_TYPE_USER.equals(getArguments().getString(Keys.USER_FRAGMENT_TYPE))) {
            userFragmentType = UserFragmentType.USER;
            userId = getArguments().getInt(Keys.USER_ID);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_fragment, container, false);
        activity = (MainActivity) getActivity();

        if (UserFragmentType.MY_PROFILE.equals(userFragmentType)) {
            activity.getSupportActionBar().setTitle(getString(R.string.title_my_profile));
            activity.setActiveMenuItem(0);
        } else if (UserFragmentType.USER.equals(userFragmentType)) {
            activity.getSupportActionBar().setTitle(getString(R.string.title_user));
            activity.setActiveMenuItem(null);
        }

        ButterKnife.bind(this, v);
        _flytProfile.bringToFront();
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

    @OnClick({R.id.btn_github})
    public void openGithub() {
        if (user.getGithubURL() != null) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getGithubURL())));
        }
    }

    @OnClick({R.id.rlyt_job_related_skills})
    public void openJobRelatedSkills() {
        ((MainActivity) getActivity()).switchFragment(new UserJobRelatedDialog());
    }

    @OnClick({R.id.rlyt_additional_skills})
    public void openAdditionalSkills() {
        ((MainActivity) getActivity()).switchFragment(new UserAdditionalSkillsFragment());
    }

    @OnClick({R.id.rlyt_interests})
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
                    ErrorHandler.checkConnectionError(getContext(), t);
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
                    ErrorHandler.checkConnectionError(getContext(), t);
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

        if (user.getAvatar() != null && user.getAvatar().getDashboard().getUrl() != null) {
            ImageHandler.getSharedInstance(getActivity()).load(user.getAvatar().getDashboard().getUrl())
                    .into(_ivProfile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            _ivProfile.setVisibility(View.VISIBLE);
                            _tvProfile.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            _ivProfile.setVisibility(View.GONE);
                            _tvProfile.setVisibility(View.VISIBLE);
                        }
                    });


        } else {
            _ivProfile.setVisibility(View.GONE);
            _tvProfile.setVisibility(View.VISIBLE);
        }

        _tvUserName.setText(user.getUserName());
        _tvRole.setText(user.getJobRole().getTitle());
        _tvDepartmentName.setText(user.getDepartment().getName());


        if (user.getAbout() != null) {
            _tvAbout.setText(user.getAbout());
        } else {
            _lytAbout.setVisibility(View.GONE);
        }

        _tvProfile.setText(user.getInitials());

        if (user.getLocation() != null) {
            _tvLocation.setText(user.getLocation().getCity());
        }

        if (user.getEmploymentStartDate() != null) {
            _tvEmployeeSince.setText(getString(R.string.my_profile_employee_since) + " " + DateUtils.getDateString(user.getEmploymentStartDate()));
        }

        if (user.getBusinessPhone() != null) {
            _btnCall.setVisibility(View.VISIBLE);
        } else {
            _btnCall.setVisibility(View.GONE);
        }

        if (user.getEmail() != null) {
            _btnSendEmail.setVisibility(View.VISIBLE);
        } else {
            _btnSendEmail.setVisibility(View.GONE);
        }

        if (user.getLinkedinURL() != null) {
            _btnLinkedin.setVisibility(View.VISIBLE);
        } else {
            _btnLinkedin.setVisibility(View.GONE);
        }

        if (user.getTwiterURL() != null) {
            _btnTwitter.setVisibility(View.VISIBLE);
        } else {
            _btnTwitter.setVisibility(View.GONE);
        }

        if (user.getFacebookURL() != null) {
            _btnFacebook.setVisibility(View.VISIBLE);
        } else {
            _btnFacebook.setVisibility(View.GONE);
        }

        if (user.getGithubURL() != null) {
            _btnGitHub.setVisibility(View.VISIBLE);
        } else {
            _btnGitHub.setVisibility(View.GONE);
        }

        boolean showAdditionalInfo = false;

        if (user.getCellPhone() != null) {
            showAdditionalInfo = true;
            _tvHomePhone.setText(user.getCellPhone());
        } else {
            _rlytHomePhone.setVisibility(View.GONE);
        }


        if (user.getPersonalEmail() != null) {
            showAdditionalInfo = true;
            _tvHomeEmail.setText(user.getPersonalEmail());
        } else {
            _rlytHomeEmail.setVisibility(View.GONE);
        }

        if (user.getEmergencyFullName() != null || user.getEmergencyPhone() != null || user.getEmergencyRole() != null) {
            showAdditionalInfo = true;
            _tvEmeregencyName.setText(user.getEmergencyFullName());
            _tvEmeregencyContact.setText(user.getEmergencyPhone());
            _tvEmeregencyRole.setText(user.getEmergencyRole());
        } else {
            _rlytEmeregencyRole.setVisibility(View.GONE);
        }


        if (user.getBirthdayDate() != null) {
            showAdditionalInfo = true;
            _tvBirthday.setText(DateUtils.getDateString(user.getBirthdayDate()));
        } else {
            _rlytBirthday.setVisibility(View.GONE);
        }


        if (user.getCollege() != null) {
            showAdditionalInfo = true;
            _tvCollege.setText(user.getCollege());
        } else {
            _rlytCollege.setVisibility(View.GONE);
        }

        if (user.getDietaryRestrictions() != null) {
            showAdditionalInfo = true;
            _tvAdditionalMedicalInfo.setText(user.getDietaryRestrictions());
        } else {
            _rlytAdditionalMedicalInfo.setVisibility(View.GONE);
        }

        if (showAdditionalInfo) {
            _cardAdditionalInfo.setVisibility(View.VISIBLE);
        } else {
            _cardAdditionalInfo.setVisibility(View.GONE);
        }


        if (user.getRequiredSkills().isEmpty() && user.getAdditionalSkills().isEmpty() &&
                user.getInterests().isEmpty()) {
            _cardSkillsParent.setVisibility(View.GONE);
        } else {
            if (user.getRequiredSkills().isEmpty()) {
                _rlytJobRelated.setVisibility(View.GONE);
            } else {
                _rlytJobRelated.setVisibility(View.VISIBLE);
            }

            if (user.getAdditionalSkills().isEmpty()) {
                _rlytAdditionalSkills.setVisibility(View.GONE);
            } else {
                _rlytAdditionalSkills.setVisibility(View.VISIBLE);
            }

            if (user.getInterests().isEmpty()) {
                _rlytInterests.setVisibility(View.GONE);
            } else {
                _rlytInterests.setVisibility(View.VISIBLE);
            }
        }

    }
}
