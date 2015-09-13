package com.notus.fit.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.notus.fit.R;
import com.notus.fit.fragments.ChallengeDetailFragment;
import com.notus.fit.utils.ColorUtils;
import com.notus.fit.utils.CustomUtils;


public class ChallengeDetailActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackNav();
        if (getIntent().getExtras() != null) {
            try {
                ColorDrawable pColor = new ColorDrawable(getIntent().getExtras().getInt("pColor"));
                if (Build.VERSION.SDK_INT >= 16) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.addFlags(Integer.MIN_VALUE);
                        window.clearFlags(67108864);
                        window.setStatusBarColor(ColorUtils.darkenColor(getIntent().getExtras().getInt("pColor")));
                    }
                    this.mToolbar.setBackground(pColor);
                } else {
                    this.mToolbar.setBackground(pColor);
                }
                ChallengeDetailFragment cdF = new ChallengeDetailFragment();
                cdF.setArguments(getIntent().getExtras());
                CustomUtils.addFragmentToContainer(cdF, R.id.container, this);
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }
        }
    }
}
