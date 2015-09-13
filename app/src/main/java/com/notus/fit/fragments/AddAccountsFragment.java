package com.notus.fit.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.jawbone.upplatformsdk.api.ApiManager;
import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.activities.BaseActivity;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.fitbit.FitbitClient;
import com.notus.fit.network.jawbone.JawboneAPI;
import com.notus.fit.network.misfit.MisfitClient;
import com.notus.fit.network.misfit.RequestTokenBody;
import com.notus.fit.network.moves.MoveApiClient;
import com.notus.fit.network.moves.MovesAccessToken;
import com.notus.fit.network.moves.MovesUser;
import com.notus.fit.network.scribe.AuthActivity;
import com.notus.fit.network.services.UpdateDeviceToken;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddAccountsFragment extends FitnessFragment implements View.OnClickListener {
    public static Token requestToken;
    public static OAuthService service;
    Token accessToken;
    @Bind(R.id.button_layout)
    LinearLayout buttonLayout;
    @Bind(R.id.connect_fitbit)
    Button connectFitbit;
    @Bind(R.id.connect_fitbit_layout)
    CardView connectFitbitLayout;
    @Bind(R.id.connect_google_fit)
    Button connectGoogleFit;
    @Bind(R.id.connect_google_fit_layout)
    CardView connectGoogleFitLayout;
    @Bind(R.id.connect_misfit)
    Button connectMisfit;
    @Bind(R.id.connect_misfit_layout)
    CardView connectMisfitLayout;
    @Bind(R.id.connect_moves)
    Button connectMoves;
    @Bind(R.id.connect_moves_layout)
    CardView connectMovesLayout;
    @Bind(R.id.connect_up)
    Button connectUp;
    @Bind(R.id.connect_up_layout)
    CardView connectUpLayout;
    @Bind(R.id.disconnect_fitbit)
    Button disconnectFitbit;
    @Bind(R.id.disconnect_google_fit)
    Button disconnectGoogleFit;
    @Bind(R.id.disconnect_misfit)
    Button disconnectMisfit;
    @Bind(R.id.disconnect_moves)
    Button disconnectMoves;
    @Bind(R.id.disconnect_up)
    Button disconnectUp;
    @Bind(R.id.finishButton)
    Button finishButton;
    @Bind(R.id.fitbit_logo)
    ImageView fitbitLogo;
    @Bind(R.id.googlefit_logo)
    ImageView googleFitLogo;
    @Bind(R.id.misfit_logo)
    ImageView misfitLogo;
    @Bind(R.id.moves_logo)
    ImageView movesLogo;
    @Bind(R.id.up_logo)
    ImageView upLogo;
    String userID;
    ParseObject userObject;
    private JawboneAPI jawboneAPI;
    private MisfitClient misfitClient;


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        this.userID = PrefManager.with(getActivity()).getString(User.OBJECT_ID, null);
        if (this.userID != null) {
            Log.d(TAG, "User ID: " + this.userID);
        } else {
            Log.d(TAG, "user id is null");
        }
        this.connectFitbit.setOnClickListener(this);
        this.connectGoogleFit.setOnClickListener(this);
        this.connectUp.setOnClickListener(this);
        this.connectMisfit.setOnClickListener(this);
        this.finishButton.setOnClickListener(this);
        this.disconnectFitbit.setOnClickListener(this);
        this.disconnectGoogleFit.setOnClickListener(this);
        this.disconnectUp.setOnClickListener(this);
        this.disconnectMisfit.setOnClickListener(this);
        this.connectMoves.setOnClickListener(this);
        this.disconnectMoves.setOnClickListener(this);
        if (this.mHasWearDevice) {
            this.mBaseActivity.setConnected(this.connectGoogleFit, this.disconnectGoogleFit);
            this.disconnectGoogleFit.setVisibility(View.VISIBLE);
            this.connectGoogleFit.setEnabled(false);
        }
        if (this.mHasFitbit) {
            this.mBaseActivity.setConnected(this.connectFitbit, this.disconnectFitbit);
            this.disconnectFitbit.setVisibility(View.VISIBLE);
            this.connectFitbit.setEnabled(false);
        }
        if (this.mHasJawbone) {
            this.mBaseActivity.setConnected(this.connectUp, this.disconnectUp);
            this.disconnectUp.setVisibility(View.VISIBLE);
            this.connectUp.setEnabled(false);
        }
        if (this.mHasMisfit) {
            this.mBaseActivity.setConnected(this.connectMisfit, this.disconnectMisfit);
            this.disconnectMisfit.setVisibility(View.VISIBLE);
            this.connectMisfit.setEnabled(false);
        }
        if (this.mHasMoves) {
            this.mBaseActivity.setConnected(this.connectMoves, this.disconnectMoves);
            this.disconnectMoves.setVisibility(View.VISIBLE);
            this.connectMoves.setEnabled(false);
        }
        Picasso.with(getActivity()).load(R.drawable.ic_fitbit_logo_horizontal).placeholder(R.drawable.ic_fitbit_logo_horizontal).into(this.fitbitLogo);
        Picasso.with(getActivity()).load(R.drawable.ic_fit_large).placeholder(R.drawable.ic_fit_large).into(this.googleFitLogo);
        Picasso.with(getActivity()).load(R.drawable.ic_up_logo).placeholder(R.drawable.ic_up_logo).into(this.upLogo);
        Picasso.with(getActivity()).load(R.drawable.ic_logo_misfit_name).placeholder(R.drawable.ic_logo_misfit_name).into(this.misfitLogo);
        Picasso.with(getActivity()).load(R.drawable.ic_moves_logo).placeholder(R.drawable.ic_moves_logo).into(this.movesLogo);
        if (getArguments() != null && !getArguments().getBoolean(NativeProtocol.METHOD_ARGS_LINK)) {
            hideFinishSetup();
        }
    }

    public void hideFinishSetup() {
        this.finishButton.setVisibility(View.GONE);
    }

    public int getLayoutResource() {
        return R.layout.fragment_add_accounts;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_google_fit:
                this.mBaseActivity.buildFitnessClient(this.connectGoogleFit, this.disconnectGoogleFit);
                return;
            case R.id.disconnect_google_fit:
                ((BaseActivity) getActivity()).revokeGoogleFitAccess();
                ((BaseActivity) getActivity()).setDisconnected(this.connectGoogleFit, getString(R.string.connect_google_fit));
                PrefManager.with(getActivity()).save(User.HAS_GOOGLEFIT, false);
                this.disconnectGoogleFit.setVisibility(View.GONE);
                enableButton(this.connectGoogleFit, R.drawable.google_fit_btn_selector, R.string.connect_google_fit);
                return;
            case R.id.connect_up:
                this.jawboneAPI = new JawboneAPI(getActivity(), getActivity().getClass());
                startActivityForResult(this.jawboneAPI.getIntentForWebView(), 12050);
                return;
            case R.id.disconnect_up:
                ((BaseActivity) getActivity()).setDisconnected(this.connectUp, getString(R.string.connect_jawbone));
                PrefManager.with(getActivity()).save(User.HAS_JAWBONE, false);
                this.disconnectUp.setVisibility(View.GONE);
                PrefManager.with(getActivity()).remove(ServerProtocol.DIALOG_PARAM_ACCESS_TOKEN);
                PrefManager.with(getActivity()).remove("refresh_token");
                enableButton(this.connectUp, R.drawable.jawbone_btn_selector, R.string.connect_jawbone);
                return;
            case R.id.connect_fitbit:
                startActivityForResult(new Intent(getActivity(), AuthActivity.class), 101);
                return;
            case R.id.disconnect_fitbit:
                PrefManager.with(getActivity()).remove(FitbitClient.FITBIT_TOKEN);
                PrefManager.with(getActivity()).remove(FitbitClient.FITBIT_TOKEN_SECRET);
                PrefManager.with(getActivity()).remove(FitbitClient.FITBIT_TOKEN_RAW);
                PrefManager.with(getActivity()).remove(FitbitClient.FITBIT_PIN);
                PrefManager.with(getActivity()).save(User.HAS_FITBIT, false);
                ((BaseActivity) getActivity()).setDisconnected(this.connectFitbit, getString(R.string.connect_fitbit));
                this.disconnectFitbit.setVisibility(View.GONE);
                enableButton(this.connectFitbit, R.drawable.fitbit_btn_selector, R.string.connect_fitbit);
                return;
            case R.id.connect_misfit:
                this.misfitClient = new MisfitClient(getActivity());
                startActivityForResult(this.misfitClient.getIntentForWebView(), MisfitClient.MISFIT_AUTORIZE_ACCES_CODE);
                return;
            case R.id.disconnect_misfit:
                ((BaseActivity) getActivity()).setDisconnected(this.connectMisfit, getString(R.string.connect_misfit));
                PrefManager.with(getActivity()).save(User.HAS_MISFIT, false);
                this.disconnectMisfit.setVisibility(View.GONE);
                PrefManager.with(getActivity()).remove(User.HAS_MISFIT);
                PrefManager.with(getActivity()).remove(MisfitClient.MISFIT_TOKEN);
                enableButton(this.connectMisfit, R.drawable.jawbone_btn_selector, R.string.connect_misfit);
                return;
            case R.id.connect_moves:
                try {
                    startActivityForResult(new Intent("android.intent.action.VIEW", MoveApiClient.createAuthUri("moves", "app", "/authorize").build()), MoveApiClient.REQUEST_AUTHORIZE);
                } catch (ActivityNotFoundException ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                    Toast.makeText(getActivity(), "Moves app not installed", Toast.LENGTH_SHORT).show();
                }
                return;
            case R.id.disconnect_moves:
                new MaterialDialog.Builder(getActivity()).title("Disconnect Moves").content("To disconnect moves please go to the Moves app and select \"Connected Apps\". Find FitHub and click on \"Revoke Access\". If you select OK, FitHub will not try to make any request to Moves in your behalf and will assume that you disconnected FitHub within the Moves App.").positiveText("OK")
                        .positiveColor(ContextCompat.getColor(getContext(), R.color.primary_dark))
                        .negativeColor(ContextCompat.getColor(getContext(), R.color.accent_color))
                        .negativeText("Dismiss").callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        ((BaseActivity) getActivity()).setDisconnected(connectMoves, getString(R.string.connect_moves));
                        PrefManager.with(getActivity()).save(User.HAS_MOVES, false);
                        disconnectMoves.setVisibility(View.GONE);
                        PrefManager.with(getActivity()).remove(User.HAS_MOVES);
                        PrefManager.with(getActivity()).remove(User.MOVES_TOKEN);
                        PrefManager.with(getActivity()).remove(User.MOVES_REFRESH_TOKEN);
                        enableButton(connectMoves, R.drawable.moves_btn_selector, R.string.connect_moves);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).build().show();
                return;
            case R.id.finishButton:
                PrefManager.with(getActivity()).save(PreferenceUtils.FIRST_TIME, false);
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
        }
    }

    public void onStop() {
        super.onStop();
        launchService();
    }

    public void launchService() {
        getActivity().startService(new Intent(getActivity(), UpdateDeviceToken.class));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == -1) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    final String pin = extras.getString("PIN");
                    final Verifier verifier = new Verifier(pin);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                accessToken = AddAccountsFragment.service.getAccessToken(AddAccountsFragment.requestToken, verifier);
                                PrefManager.with(getActivity()).save(User.HAS_FITBIT, true);
                                PrefManager.with(getActivity()).save(FitbitClient.FITBIT_TOKEN, accessToken.getToken());
                                PrefManager.with(getActivity()).save(FitbitClient.FITBIT_TOKEN_SECRET, accessToken.getSecret());
                                PrefManager.with(getActivity()).save(FitbitClient.FITBIT_TOKEN_RAW, accessToken.getRawResponse());
                                PrefManager.with(getActivity()).save(FitbitClient.FITBIT_PIN, pin);
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mBaseActivity.setConnected(connectFitbit, disconnectFitbit);
                                    }
                                });
                                userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, userID).getFirst();
                                userObject.put(User.HAS_FITBIT, true);
                                userObject.put(User.FITBIT_TOKEN, (accessToken.getToken() + "," + accessToken.getSecret()) + "," + accessToken.getRawResponse());
                                userObject.save();
                            } catch (Exception ex) {
                                if (ex.getMessage() != null) {
                                    Log.d(FitnessFragment.TAG, "Failed to add fitbit", ex);
                                }
                            }
                        }
                    }).start();
                }
            }
        } else if (requestCode == 12050 && resultCode == -1) {
            try {
                String code = data.getStringExtra("code");
                if (code != null) {
                    PrefManager.with(getActivity()).save(User.HAS_JAWBONE, true);
                    ApiManager.getRequestInterceptor().clearAccessToken();
                    ApiManager.getRestApiInterface().getAccessToken(JawboneAPI.CLIENT_ID, JawboneAPI.CLIENT_SECRET, code, this.jawboneAPI.getAccessTokenRequestListener());
                    this.mBaseActivity.setConnected(this.connectUp, this.disconnectUp);
                }
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
        } else if (requestCode == 18723 && resultCode == -1) {
            try {
                String code = data.getStringExtra(MisfitClient.ACCESS_CODE);
                if (code != null) {
                    MisfitClient.getRestApiInterface().getAccessToken(new RequestTokenBody(code), this.misfitClient.getAccessTokenRequestListener());
                    this.mBaseActivity.setConnected(this.connectMisfit, this.disconnectMisfit);
                    return;
                }
                Log.d(TAG, "Code is null!");
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
        } else {
            if (requestCode == 3998 && resultCode == -1) {
                try {
                    Uri resultUri = data.getData();
                    Log.d(TAG, resultUri.toString());
                    RestAdapter movesRestAdapter = MoveApiClient.getBaseRestAdapter();
                    movesRestAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
                    movesRestAdapter.create(MoveApiClient.MovesConnector.class).authorize("authorization_code", MoveApiClient.extractCodeFromCallbackURL(resultUri.toString()), MoveApiClient.CLIENT_ID, MoveApiClient.CLIENT_SECRET, MoveApiClient.REDIRECT_URI, new Callback<MovesAccessToken>() {

                        @Override
                        public void success(MovesAccessToken movesAccessToken, Response response) {
                            PrefManager.with(getActivity()).save(User.MOVES_TOKEN, movesAccessToken.getAccessToken());
                            PrefManager.with(getActivity()).save(User.MOVES_REFRESH_TOKEN, movesAccessToken.getRefreshToken());
                            PrefManager.with(getActivity()).save(User.HAS_MOVES, true);
                            try {
                                userObject = ParseQuery.getQuery(User.CLASS).whereEqualTo(User.OBJECT_ID, userID).getFirst();
                                userObject.put(User.HAS_MOVES, true);
                                userObject.put(User.MOVES_TOKEN, movesAccessToken.getAccessToken());
                                userObject.put(User.MOVES_REFRESH_TOKEN, movesAccessToken.getRefreshToken());
                                userObject.save();
                            } catch (ParseException ex) {
                                Log.e(LOG_TAG, ex.getMessage(), ex);
                            }
                            MoveApiClient.getBaseRestAdapter(getActivity()).create(MoveApiClient.MovesConnector.class).getUser(new Callback<MovesUser>() {

                                @Override
                                public void success(final MovesUser movesUser, Response response) {
                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            PrefManager.with(getActivity()).save(MoveApiClient.MOVES_FIRST_DATE, movesUser.getProfile().getFirstDate());
                                            mBaseActivity.setConnected(connectMoves, disconnectMoves);
                                        }
                                    });
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            });

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getActivity(), "An error occured. Please try again later.", Toast.LENGTH_LONG).show();
                            Log.d(FitnessFragment.TAG, "Error: " + error.getMessage());
                        }
                    });
                } catch (Exception ex) {
                    Log.e(LOG_TAG, ex.getMessage(), ex);
                }
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void enableButton(Button b, int resId, int stringId) {
        b.setBackgroundResource(resId);
        b.setEnabled(true);
        b.setText(getResources().getString(stringId));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
