package com.notus.fit.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.notus.fit.R;
import com.notus.fit.activities.StartActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeFragment extends FitnessFragment {
    @Bind(R.id.nextButton)
    Button next;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StartActivity) getActivity()).setPagerNumber(1);
            }
        });
    }

    public int getLayoutResource() {
        return R.layout.fragment_hub_explanation;
    }
}
