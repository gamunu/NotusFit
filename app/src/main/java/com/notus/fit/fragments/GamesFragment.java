package com.notus.fit.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.notus.fit.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GamesFragment extends DefaultFragment {
    @Bind(R.id.achievements_btn)
    Button achievementsBtn;
    @Bind(R.id.leaderboardds_btn)
    Button leaderboardsBtn;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind((Object) this, view);
    }

    @OnClick(R.id.leaderboardds_btn)
    public void showLeaderboard() {
    }

    @OnClick(R.id.achievements_btn)
    public void showAchievements() {
    }

    public int getLayoutResource() {
        return R.layout.fragment_games;
    }
}
