package co.com.etn.arquitecturamvpbase.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.com.etn.arquitecturamvpbase.R;
import co.com.etn.arquitecturamvpbase.helper.CustomSharedPreferences;
import co.com.etn.arquitecturamvpbase.model.User;

/**
 * Created by Lisandro Gómez on 10/17/17.
 */

public class ProfileFragment extends Fragment {


    private TextView profileName;
    private TextView profileLikes;
    private TextView profileFollowers;
    private TextView profileFollowings;

    private ImageView imgProfile;

    private User user;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        getUserShared(view);

        return view;
    }

    private void initViews(View view) {
        profileName = (TextView) view.findViewById(R.id.txtUsernameProfile);
        profileLikes = (TextView) view.findViewById(R.id.profile_likes);
        profileFollowers = (TextView) view.findViewById(R.id.profile_followers);
        profileFollowings = (TextView) view.findViewById(R.id.profile_following);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);

    }

    public void getUserShared(View view) {
        CustomSharedPreferences preferences = new CustomSharedPreferences(view.getContext());
        user = preferences.getObjectUser(User.class.getName());
        if (user != null) {
            setUserValues(view);
        }
    }

    private void setUserValues(View view) {
        profileName.setText(user.getUsername());
        profileLikes.setText(user.getLikes() + " Likes");
        profileLikes.setText(user.getFollowers() + " Followers");
        profileLikes.setText(user.getFollowings() + " Followings");
        Picasso.with(view.getContext()).load(user.getPhoto()).into(imgProfile);
    }

}
