package com.notus.fit.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.internal.NativeProtocol;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.notus.fit.BuildConfig;
import com.notus.fit.R;
import com.notus.fit.activities.BaseActivity;
import com.notus.fit.activities.LinkDevicesActivity;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.services.CreateUserService;
import com.notus.fit.utils.NetworkUtils;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpFragment extends DefaultFragment implements View.OnClickListener {
    private static final String LOG_TAG = SignUpFragment.class.getSimpleName();
    @Bind(R.id.facebook_signup)
    LoginButton facebookSignUp;
    @Bind(R.id.facebook_logo)
    ImageView fbLogo;
    @Bind(R.id.fithub_signup)
    Button fithubSignUp;
    @Bind(R.id.gplus_logo)
    ImageView gPlusLogo;
    @Bind(R.id.gplus_signup)
    Button gplusSignUp;
    GraphUser graphUser;
    @Bind(R.id.sign_in)
    Button signIn;
    User user;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind((Object) this, view);
        this.fithubSignUp.setOnClickListener(this);
        this.facebookSignUp.setOnClickListener(this);
        this.gplusSignUp.setOnClickListener(this);
        this.signIn.setOnClickListener(this);
        this.fbLogo.setOnClickListener(this);
        this.gPlusLogo.setOnClickListener(this);
        List permissions = new ArrayList();
        permissions.add(PreferenceUtils.EMAIL);
        permissions.add("user_friends");
        this.facebookSignUp.setBackgroundResource(R.drawable.facebook_btn_selector);
        this.facebookSignUp.setPadding(0, 0, 0, 0);
        this.facebookSignUp.setTypeface(Typeface.DEFAULT_BOLD);
        this.facebookSignUp.setTextSize(2, 14.0f);
        this.facebookSignUp.setText(getString(R.string.facebook_signup));
        this.facebookSignUp.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_forward), null, null, null);
        this.facebookSignUp.setReadPermissions(permissions);
        this.facebookSignUp.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser graphUser) {
                if (graphUser != null) {
                    SignUpFragment.this.graphUser = graphUser;
                    String profilePicture = "https://graph.facebook.com/v2.2/" + SignUpFragment.this.graphUser.getId() + "/picture?height=800&type=square&width=800";
                    SignUpFragment.this.user = new User();
                    SignUpFragment.this.user.setAvatarUrl(profilePicture);
                    SignUpFragment.this.user.setEmail(SignUpFragment.this.graphUser.asMap().get(PreferenceUtils.EMAIL).toString());
                    SignUpFragment.this.user.setUsername(SignUpFragment.this.graphUser.asMap().get(PreferenceUtils.EMAIL).toString());
                    SignUpFragment.this.user.setFirstName(SignUpFragment.this.graphUser.getFirstName());
                    SignUpFragment.this.user.setLastName(SignUpFragment.this.graphUser.getLastName());
                    Log.d(SignUpFragment.LOG_TAG, "Email: " + SignUpFragment.this.user.getEmail());
                    new SignIn().execute(new Void[0]);
                    return;
                }
                Log.i(SignUpFragment.LOG_TAG, "Facebook User Failed!");
            }
        });
    }

    public void executeLogin(User user) {
        this.user = user;
        new SignIn().execute(new Void[0]);
    }

    public void clickFB() {
        this.facebookSignUp.performClick();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public int getLayoutResource() {
        return R.layout.fragment_start_sign_up;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gplus_logo:
            case R.id.gplus_signup:
                ((BaseActivity) getActivity()).signInWithGplus();
            case R.id.facebook_logo:
                clickFB();
            default:
        }
    }

    class SignIn extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog pd;

        SignIn() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(SignUpFragment.this.getActivity());
            this.pd.setIndeterminate(true);
            this.pd.setCancelable(false);
            this.pd.setTitle("Signing in...");
            this.pd.show();
        }

        protected Boolean doInBackground(Void... params) {
            ParseObject userObject;
            String userCountry = NetworkUtils.getUserCountry(SignUpFragment.this.getActivity());
            try {
                userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.USERNAME, SignUpFragment.this.user.getUsername()).getFirst();
                if (userObject != null) {
                    Log.d(SignUpFragment.LOG_TAG, "Signing in...");
                    if (userObject.getString(User.COUNTRY) == null) {
                        userObject.put(User.COUNTRY, userCountry);
                    }
                    if (userObject.getString(PreferenceUtils.EMAIL) == null) {
                        userObject.put(PreferenceUtils.EMAIL, userObject.getString(User.USERNAME));
                    }
                    userObject.pinInBackground();
                    NetworkUtils.setPreferences(userObject, SignUpFragment.this.getActivity());
                } else {
                    userObject = new ParseObject(User.CLASS);
                    userObject.put(User.USERNAME, SignUpFragment.this.user.getUsername());
                    userObject.put(PreferenceUtils.EMAIL, SignUpFragment.this.user.getEmail());
                    userObject.put(PreferenceUtils.AVATAR_URL, SignUpFragment.this.user.getAvatarUrl());
                    userObject.put(User.FIRST_NAME, SignUpFragment.this.user.getFirstName());
                    userObject.put(User.LAST_NAME, SignUpFragment.this.user.getLastName());
                    userObject.put(User.COUNTRY, userCountry);
                    userObject.save();
                    NetworkUtils.setPreferences(userObject, SignUpFragment.this.getActivity());
                }
                return Boolean.valueOf(true);
            } catch (ParseException ex) {
                if (ex.getMessage() != null) {
                    Log.d(SignUpFragment.LOG_TAG, ex.getMessage());
                }
                try {
                    userObject = new ParseObject(User.CLASS);
                    userObject.put(User.USERNAME, SignUpFragment.this.user.getUsername());
                    userObject.put(PreferenceUtils.EMAIL, SignUpFragment.this.user.getEmail());
                    userObject.put(PreferenceUtils.AVATAR_URL, SignUpFragment.this.user.getAvatarUrl());
                    userObject.put(User.FIRST_NAME, SignUpFragment.this.user.getFirstName());
                    userObject.put(User.LAST_NAME, SignUpFragment.this.user.getLastName());
                    String str = User.COUNTRY;
                    if (userCountry == null) {
                        userCountry = BuildConfig.FLAVOR;
                    }
                    userObject.put(str, userCountry);
                    userObject.save();
                    NetworkUtils.setPreferences(userObject, SignUpFragment.this.getActivity());
                } catch (ParseException e) {
                    if (e.getMessage() != null) {
                        Log.d(SignUpFragment.LOG_TAG, "Failed creating user: " + e.getMessage());
                    }
                }
                return Boolean.valueOf(false);
            } catch (Exception e2) {
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (SignUpFragment.this.getActivity() != null) {
                final Intent i = new Intent(SignUpFragment.this.getActivity(), LinkDevicesActivity.class);
                i.putExtra(NativeProtocol.METHOD_ARGS_LINK, true);
                if (!aBoolean.booleanValue() || PrefManager.with(SignUpFragment.this.getActivity()).getString(PreferenceUtils.AVATAR_URL, null) == null) {
                    SignUpFragment.this.getActivity().startService(new Intent(SignUpFragment.this.getActivity(), CreateUserService.class));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SignIn.this.pd.dismiss();
                            i.addFlags(335544320);
                            SignUpFragment.this.startActivity(i);
                            SignUpFragment.this.getActivity().finish();
                        }
                    }, 300);
                } else if (this.pd.isShowing()) {
                    this.pd.dismiss();
                    i.addFlags(335544320);
                    SignUpFragment.this.startActivity(i);
                    SignUpFragment.this.getActivity().finish();
                }
            }
        }
    }
}
