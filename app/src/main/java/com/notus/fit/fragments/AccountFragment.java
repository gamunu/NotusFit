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
    SwitchCompat averageSteps;
    @Bind(R.id.btn_edit_account)
    FloatingActionButton btnEditAccount;
    @Bind(R.id.changePassword)
    Button changePassword;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.fit_logo)
    ImageView fitLogo;
    @Bind(R.id.fitbit_logo)
    ImageView fitbitLogo;
    String[] genderArray;
    @Bind(R.id.gender_spinner)
    Spinner genderSpinner;
    @Bind(R.id.height_ft)
    EditText heightFt;
    @Bind(R.id.height_in)
    EditText heightIn;
    @Bind(R.id.height_units_1)
    TextView heightUnits1;
    @Bind(R.id.height_units_2)
    TextView heightUnits2;
    @Bind(R.id.misfit_logo)
    ImageView misfitLogo;
    @Bind(R.id.moves_logo)
    ImageView movesLogo;
    @Bind(R.id.name)
    EditText nameLastName;
    @Bind(R.id.profile_image)
    ImageView profileImage;
    @Bind(R.id.unit_spinner)
    Spinner unitSpinner;
    @Bind(R.id.up_logo)
    ImageView upLogo;
    @Bind(R.id.weight)
    EditText weight;
    @Bind(R.id.weight_units)
    TextView weightUnits;
    private boolean isEditMode;
    private User user;
    private String userID;

    public AccountFragment() {
        this.isEditMode = false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind((Object) this, view);
        this.btnEditAccount.setOnClickListener(this);
        this.changePassword.setOnClickListener(this);
        this.genderArray = getResources().getStringArray(R.array.gender_array);
        this.genderSpinner.setEnabled(false);
        this.unitSpinner.setEnabled(false);
        this.userID = PrefManager.with(getActivity()).getString(User.OBJECT_ID, null);
        if (this.userID != null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(User.CLASS);
            query.fromLocalDatastore();
            query.getInBackground(this.userID, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object != null) {
                        AccountFragment.this.user = User.build(object);
                        AccountFragment.this.updateUserFields();
                        return;
                    }
                    new MaterialDialog.Builder(AccountFragment.this.getActivity()).title("Error fetching user data.")
                            .content("There was an error retrieving user data. Please try again in a few minutes.")
                            .positiveText("Dismiss")
                            .positiveColor(AccountFragment.this.getResources()
                                    .getColor(R.color.accent_color)).build().show();

                }
            });
            this.averageSteps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        PrefManager.with(AccountFragment.this.getActivity()).save("should_average", true);
                    } else {
                        PrefManager.with(AccountFragment.this.getActivity()).save("should_average", false);
                    }
                }
            });
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_account:
                boolean z;
                this.weight.setEnabled(!this.isEditMode);
                Spinner spinner = this.unitSpinner;
                if (this.isEditMode) {
                    z = false;
                } else {
                    z = true;
                }
                spinner.setEnabled(z);
                spinner = this.genderSpinner;
                if (this.isEditMode) {
                    z = false;
                } else {
                    z = true;
                }
                spinner.setEnabled(z);
                EditText editText = this.heightIn;
                if (this.isEditMode) {
                    z = false;
                } else {
                    z = true;
                }
                editText.setEnabled(z);
                editText = this.heightFt;
                if (this.isEditMode) {
                    z = false;
                } else {
                    z = true;
                }
                editText.setEnabled(z);
                if (this.isEditMode) {
                    this.btnEditAccount.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_edit));
                    this.isEditMode = false;
                    this.user.setObjectId(this.userID);
                    this.user.setWeight(this.weight.getText().toString());
                    this.user.setHeight(this.heightFt.getText().toString() + " " + this.heightIn.getText().toString());
                    this.user.setGender(this.genderArray[this.genderSpinner.getSelectedItemPosition()]);
                    this.user.setUnits(this.unitSpinner.getSelectedItem().toString());
                    Log.d(TAG, this.user.toString());
                    if (this.hasWearDevice) {
                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            long endTime = cal.getTimeInMillis();
                            long startTime = cal.getTimeInMillis();
                            final float weightKG = FitnessUtils.convertWeight(Float.parseFloat(this.user.getWeight()), this.user.getUnits());
                            Fitness.HistoryApi.insertData(((AccountActivity) getActivity()).getGoogleFitClient(), FitnessUtils.createDataForRequest(getActivity(), DataType.TYPE_WEIGHT, 0, Float.valueOf(weightKG), startTime, endTime, TimeUnit.SECONDS))
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
                        } catch (Exception e) {
                        }
                    }
                    final ParseObject object = User.build(this.user);
                    if (object != null) {
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (AccountFragment.this.getActivity() != null) {
                                    Toast.makeText(AccountFragment.this.getActivity(), "User successfully updated.", Toast.LENGTH_LONG).show();
                                }
                                object.pinInBackground();
                                PrefManager.with(AccountFragment.this.getActivity()).save(User.UNITS, object.getString(User.UNITS) != null ? object.getString(User.UNITS) : AccountFragment.this.getString(R.string.pref_units_imperial));
                            }
                        });
                        return;
                    }
                    return;
                }
                this.btnEditAccount.setImageResource(R.drawable.ic_done);
                this.btnEditAccount.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done));
                this.isEditMode = true;
            default:
        }
    }

    public void updateUserFields() {
        if (PrefManager.with(getActivity()).getBoolean("should_average", true)) {
            this.averageSteps.setChecked(true);
        }
        if (!this.hasWearDevice) {
            this.fitLogo.setVisibility(View.GONE);
        }
        if (!this.hasFitbit) {
            this.fitbitLogo.setVisibility(View.GONE);
        }
        if (!this.hasJawbone) {
            this.upLogo.setVisibility(View.GONE);
        }
        if (!this.hasMisfit) {
            this.misfitLogo.setVisibility(View.GONE);
        }
        if (!this.hasMoves) {
            this.movesLogo.setVisibility(View.GONE);
        }
        this.user.setHasFitbit(this.hasFitbit);
        this.user.setHasGoogleFit(this.hasWearDevice);
        this.user.setHasJawbone(this.hasJawbone);
        this.user.setHasMisfit(this.hasJawbone);
        this.user.setHasMoves(this.hasMoves);
        Picasso.with(getActivity()).load(this.user.getAvatarUrl()).fit().centerCrop().into(this.profileImage);
        this.nameLastName.setText(this.user.getFirstName() + " " + this.user.getLastName());
        this.email.setText(this.user.getEmail());
        if (this.user.getGender() != null) {
            if (this.user.getGender().equals("Male")) {
                this.genderSpinner.setSelection(1);
            } else if (this.user.getGender().equals("Female")) {
                this.genderSpinner.setSelection(0);
            } else if (this.user.getGender().equals("Other")) {
                this.genderSpinner.setSelection(2);
            } else {
                this.genderSpinner.setSelection(3);
            }
        }
        if (this.user.getUnits() != null) {
            if (this.user.getUnits().equals(FitnessUtils.UNIT_IMPERIAL)) {
                this.unitSpinner.setSelection(0);
                this.weightUnits.setText("lbs");
                this.heightUnits1.setText("ft");
                this.heightUnits2.setText("in");
            } else {
                this.unitSpinner.setSelection(1);
                this.weightUnits.setText("Kg");
                this.heightUnits1.setText("m");
                this.heightUnits2.setText("cm");
            }
        }
        if (this.user.getWeight() != null) {
            this.weight.setText(this.user.getWeight());
        }
        if (this.user.getHeight() != null) {
            String[] height = this.user.getHeight().split(" ");
            if (height.length != 0) {
                this.heightFt.setText(height[0]);
            }
            if (height.length > 1) {
                this.heightIn.setText(height[1]);
            }
        }
        this.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AccountFragment.this.user.setGender((String) AccountFragment.this.genderSpinner.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AccountFragment.this.user.setUnits((String) AccountFragment.this.unitSpinner.getItemAtPosition(position));
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
