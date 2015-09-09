package com.notus.fit.ui_elements;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notus.fit.R;
import com.notus.fit.models.api_models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import it.gmariotti.cardslib.library.internal.Card;

public class PendingUserCard extends Card {

    TextView name;
    User user;
    CircleImageView userAvatar;

    public PendingUserCard(Context context, User user1) {
        super(context, R.layout.card_pending);
        user = user1;
    }

    public void setupInnerViewElements(ViewGroup viewgroup, View view) {
        super.setupInnerViewElements(viewgroup, view);
        ButterKnife.bind(this, view);
        if (user != null) {
            Picasso.with(getContext()).load(user.getAvatarUrl()).fit().into(userAvatar);
            name.setText((new StringBuilder()).append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        }
    }
}
