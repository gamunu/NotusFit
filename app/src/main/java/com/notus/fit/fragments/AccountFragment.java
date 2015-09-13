package com.notus.fit.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataType;
import com.notus.fit.R;
import com.notus.fit.activities.AccountActivity;
import com.notus.fit.models.api_models.User;
import com.notus.fit.utils.FitnessUtils;
import com.notus.fit.utils.PrefManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AccountFragment extends FitnessFragment implements View.OnClickListener {

    protected ParseObject currentParseObject;
    @Bind(R.id.average_steps)
    SwitchCompat mAverageSteps;
    @Bind(R.id.btn_edit_account)
    FloatingActionButton mBtnEditAccount;
    @Bind(R.id.changePassword)
    Button mChangePassword;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.fit_logo)
    ImageView mFitLogo;
    @Bind(R.id.fitbit_logo)
    ImageView mFitbitLogo;
    String[] genderArray;
    @Bind(R.id.gender_spinner)
    Spinner mGenderSpinner;
    @Bind(R.id.height_ft)
    EditText mHeightFt;
    @Bind(R.id.height_in)
    EditText mHeightIn;
    @Bind(R.id.height_units_1)
    TextView mHeightUnits1;
    @Bind(R.id.height_units_2)
    TextView mHeightUnits2;
    @Bind(R.id.misfit_logo)
    ImageView mMisfitLogo;
    @Bind(R.id.moves_logo)
    ImageView mMovesLogo;
    @Bind(R.id.name)
    EditText mMameLastName;
    @Bind(R.id.profile_image)
    ImageView mProfileImage;
    @Bind(R.id.unit_spinner)
    Spinner mUnitSpinner;
    @Bind(R.id.up_logo)
    ImageView mUpLogo;
    @Bind(R.id.weight)
    EditText weight;
    @Bind(R.id.weight_units)
    TextView mWeightUnits;
    private boolean mIsEditMode;
    private User user;
    private String mUserID;

    public AccountFragment() {
        mIsEditMode = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        mBtnEditAccount.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        genderArray = getResources().getStringArray(R.array.gender_array);
        mGenderSpinner.setEnabled(false);
        mUnitSpinner.setEnabled(false);
        mUserID = PrefManager.with(getActivity()).getString(User.OBJECT_ID, null);
        if (mUserID != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(User.CLASS);
            query.fromLocalDatastore();
            query.getInBackground(mUserID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object != null) {
                        user = User.build(object);
                        updateUserFields();
                        return;
                    }
                    new MaterialDialog.Builder(getActivity()).title("Error fetching user data.")
                            .content("There was an error retrieving user data. Please try again in a few minutes.")
                            .positiveText("Dismiss")
                            .positiveColor(ContextCompat.getColor(getContext(), R.color.accent_color)).build().show();

                }
            });
            mAverageSteps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        PrefManager.with(getActivity()).save("should_average", true);
                    } else {
                        PrefManager.with(getActivity()).save("should_average", false);
                    }
                }
            });
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_account:
                boolean z;
                weight.setEnabled(!mIsEditMode);
                Spinner spinner = mUnitSpinner;
                z = !mIsEditMode;
                spinner.setEnabled(z);
                spinner = mGenderSpinner;
                z = !mIsEditMode;
                spinner.setEnabled(z);
                EditText editText = mHeightIn;
                z = !mIsEditMode;
                editText.setEnabled(z);
                editText = mHeightFt;
                z = !mIsEditMode;
                editText.setEnabled(z);
                if (mIsEditMode) {
                    mBtnEditAccount.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_edit));
                    mIsEditMode = false;
                    user.setObjectId(mUserID);
                    user.setWeight(weight.getText().toString());
                    user.setHeight(mHeightFt.getText().toString() + " " + mHeightIn.getText().toString());
                    user.setGender(genderArray[mGenderSpinner.getSelectedItemPosition()]);
                    user.setUnits(mUnitSpinner.getSelectedItem().toString());
                    Log.d(TAG, user.toString());
                    if (mHasWearDevice) {
                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            long endTime = cal.getTimeInMillis();
                            long startTime = cal.getTimeInMillis();
                            final float weightKG = FitnessUtils.convertWeight(Float.parseFloat(user.getWeight()), user.getUnits());
                            Fitness.HistoryApi.insertData(((AccountActivity) getActivity()).getGoogleFitClient(), FitnessUtils.createDataForRequest(getActivity(), DataType.TYPE_WEIGHT, 0, weightKG, startTime, endTime, TimeUnit.SECONDS))
                                    .setResultCallback(new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            if (status.isSuccess()) {
                                                Log.d(FitnessFragment.TAG, "Successfully inserted weight data in KG: " + weightKG);
                                            } else {
                                                Log.d(FitnessFragment.TAG, "Failed to insert weight data.");
                                            }
                                        }
                                    });
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, ex.getMessage(), ex);
                        }
                    }
                    final ParseObject object = User.build(user);
                    if (object != null) {
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (getActivity() != null) {
                                    Toast.makeText(getActivity(), "User successfully updated.", Toast.LENGTH_LONG).show();
                                }
                                object.pinInBackground();
                                PrefManager.with(getActivity()).save(User.UNITS, object.getString(User.UNITS) != null ? object.getString(User.UNITS) : getString(R.string.pref_units_imperial));
                            }
                        });
                        return;
                    }
                    return;
                }
                mBtnEditAccount.setImageResource(R.drawable.ic_done);
                mBtnEditAccount.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done));
                mIsEditMode = true;
            default:
        }
    }

    public void updateUserFields() {
        if (PrefManager.with(getActivity()).getBoolean("should_average", true)) {
            mAverageSteps.setChecked(true);
        }
        if (!mHasWearDevice) {
            mFitLogo.setVisibility(View.GONE);
        }
        if (!mHasFitbit) {
            mFitbitLogo.setVisibility(View.GONE);
        }
        if (!mHasJawbone) {
            mUpLogo.setVisibility(View.GONE);
        }
        if (!mHasMisfit) {
            mMisfitLogo.setVisibility(View.GONE);
        }
        if (!mHasMoves) {
            mMovesLogo.setVisibility(View.GONE);
        }
        user.setHasFitbit(mHasFitbit);
        user.setHasGoogleFit(mHasWearDevice);
        user.setHasJawbone(mHasJawbone);
        user.setHasMisfit(mHasJawbone);
        user.setHasMoves(mHasMoves);
        Picasso.with(getActivity()).load(user.getAvatarUrl()).fit().centerCrop().into(mProfileImage);
        mMameLastName.setText(user.getFirstName() + " " + user.getLastName());
        mEmail.setText(user.getEmail());
        if (user.getGender() != null) {
            if (user.getGender().equals("Male")) {
                mGenderSpinner.setSelection(1);
            } else if (user.getGender().equals("Female")) {
                mGenderSpinner.setSelection(0);
            } else if (user.getGender().equals("Other")) {
                mGenderSpinner.setSelection(2);
            } else {
                mGenderSpinner.setSelection(3);
            }
        }
        if (user.getUnits() != null) {
            if (user.getUnits().equals(FitnessUtils.UNIT_IMPERIAL)) {
                mUnitSpinner.setSelection(0);
                mWeightUnits.setText("lbs");
                mHeightUnits1.setText("ft");
                mHeightUnits2.setText("in");
            } else {
                mUnitSpinner.setSelection(1);
                mWeightUnits.setText("Kg");
                mHeightUnits1.setText("m");
                mHeightUnits2.setText("cm");
            }
        }
        if (user.getWeight() != null) {
            weight.setText(user.getWeight());
        }
        if (user.getHeight() != null) {
            String[] height = user.getHeight().split(" ");
            if (height.length != 0) {
                mHeightFt.setText(height[0]);
            }
            if (height.length > 1) {
                mHeightIn.setText(height[1]);
            }
        }
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setGender((String) mGenderSpinner.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setUnits((String) mUnitSpinner.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public int getLayoutResource() {
        return R.layout.account_fragment;
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
