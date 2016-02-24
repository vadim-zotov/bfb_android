package com.sphereinc.chairlift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sphereinc.chairlift.api.entity.LoginDetails;
import com.sphereinc.chairlift.api.facade.LoginFacade;
import com.sphereinc.chairlift.api.facadeimpl.LoginFacadeImpl;
import com.sphereinc.chairlift.common.Preferences;
import com.sphereinc.chairlift.common.utils.DialogUtils;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Preferences preferences = Preferences.getInstance();
    private LoginFacade loginFacade = new LoginFacadeImpl();

    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.email_input_layout)
    TextInputLayout _emailInputLayout;
    @Bind(R.id.pass_input_layout)
    TextInputLayout _passInputLayout;
    @Bind(R.id.sc_remember_me)
    SwitchCompat _scRememberMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _scRememberMe.setChecked(true);

        if (preferences.refreshToken() != null && preferences.rememberLogin()) {
            DialogUtils.showDialog("Authenticating...", LoginActivity.this);
            loginFacade.relogin(new Callback<LoginDetails>() {
                @Override
                public void onResponse(Response<LoginDetails> response) {
                    if (response.code() == 200 && response.body() != null) {
                        onLoginSuccess(response.body());
                    } else {
                        onLoginFailed();
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

        _emailInputLayout.setErrorEnabled(true);
        _passInputLayout.setErrorEnabled(true);

        //
        _emailText.setText("admin@fakemail.com");
        _passwordText.setText("TestAccount123");
        //


        _emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        _passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _passInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        DialogUtils.showDialog("Authenticating...", LoginActivity.this);

        loginFacade.login(_emailText.getText().toString(), _passwordText.getText().toString(), new Callback<LoginDetails>() {
            @Override
            public void onResponse(Response<LoginDetails> response) {
                if (response.code() == 200 && response.body() != null) {
                    onLoginSuccess(response.body());
                } else {
                    onLoginFailed();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(LoginDetails loginDetails) {
        preferences.setRememberLogin(_scRememberMe.isChecked());
        storeLoginDetails(loginDetails);
        _loginButton.setEnabled(true);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    private void storeLoginDetails(LoginDetails loginDetails) {
        preferences.setAccessToken(loginDetails.getAccessToken());
        preferences.setRefreshToken(loginDetails.getRefreshToken());
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailInputLayout.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailInputLayout.setError(null);
        }

        if (password.isEmpty()) {
            _passInputLayout.setError("Password is empty");
            valid = false;
        } else {
            _passInputLayout.setError(null);
        }

        return valid;
    }

}
