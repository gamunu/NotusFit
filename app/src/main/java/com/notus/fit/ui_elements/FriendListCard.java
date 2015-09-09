package com.notus.fit.ui_elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.notus.fit.R;
import com.notus.fit.models.api_models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;

public class FriendListCard extends Card {

    ImageView fitLogo;
    ImageView fitbitLogo;
    ImageView misfitLogo;
    TextView nameLastName;
    ImageView profileImage;
    ImageView upLogo;
    TextView userSteps;
    private User user;

    public FriendListCard(Context context, User user1) {
        super(context, R.layout.friend_list_card);
        user = user1;
    }

    public void setupInnerViewElements(ViewGroup viewgroup, View view) {
        super.setupInnerViewElements(viewgroup, view);
        ButterKnife.bind(this, view);
        if (user != null) {
            Picasso.with(getContext()).load(user.getAvatarUrl()).fit().into(profileImage);
            if (!user.hasGoogleFit()) {
                fitLogo.setVisibility(View.GONE);
            }
            if (!user.hasFitbit()) {
                fitbitLogo.setVisibility(View.GONE);
            }
            if (!user.hasJawbone()) {
                upLogo.setVisibility(View.GONE);
            }
            if (!user.hasMisfit()) {
                misfitLogo.setVisibility(View.GONE);
            }
            userSteps.setText((new StringBuilder()).append(user.getStepsAverage()).append(" steps.").toString());
            nameLastName.setText((new StringBuilder()).append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        }
    }
}
