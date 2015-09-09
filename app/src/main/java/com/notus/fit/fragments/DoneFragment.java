package com.notus.fit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.notus.fit.MainActivity;
import com.notus.fit.R;
import com.notus.fit.utils.PrefManager;
import com.notus.fit.utils.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoneFragment extends DefaultFragment {
    @Bind(R.id.finish_setup)
    Button finishSetup;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind((Object) this, view);
        this.finishSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager.with(DoneFragment.this.getActivity()).save(PreferenceUtils.FIRST_TIME, false);
                Intent i = new Intent(DoneFragment.this.getActivity(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DoneFragment.this.startActivity(i);
            }
        });
    }

    public int getLayoutResource() {
        return R.layout.fragment_done;
    }
}
