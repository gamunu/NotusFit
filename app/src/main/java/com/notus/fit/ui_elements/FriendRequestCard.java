package com.notus.fit.ui_elements;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.notus.fit.R;
import com.notus.fit.fragments.UserListFragment;
import com.notus.fit.models.api_models.User;
import com.notus.fit.network.services.UpdateFriendRequestService;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import it.gmariotti.cardslib.library.internal.Card;

public class FriendRequestCard extends Card
        implements android.view.View.OnClickListener {

    ImageButton acceptRequest;
    ImageButton cancelRequest;
    UserListFragment fragment;
    TextView name;
    CircleImageView userAvatar;
    private long _id;
    private User user;

    public FriendRequestCard(Context context, User user1, long l) {
        super(context, R.layout.card_friend_request);
        user = user1;
        _id = l;
    }

    public void onClick(View view) {
        boolean flag = false;
        Intent intent = new Intent(getContext(), UpdateFriendRequestService.class);
        intent.putExtra("id", _id);

        //TODO: frien
        /*switch(view.getId()) {
            intent.putExtra("status", "friend");
            flag = true;

            intent.putExtra("status", "declined");
            flag = true;
        }*/

        if (flag) {
            getContext().startService(intent);
        }
    }

    public void setFragment(UserListFragment userlistfragment) {
        fragment = userlistfragment;
    }

    public void setupInnerViewElements(ViewGroup viewgroup, View view) {
        super.setupInnerViewElements(viewgroup, view);
        ButterKnife.bind(this, view);
        acceptRequest.setOnClickListener(this);
        cancelRequest.setOnClickListener(this);
        if (user != null) {
            Picasso.with(getContext()).load(user.getAvatarUrl()).fit().into(userAvatar);
            name.setText((new StringBuilder()).append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        }
    }
}
