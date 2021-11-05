package com.example.habitsmasher.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.habitsmasher.R;
import com.example.habitsmasher.User;

public class ProfileFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // create the sample user
        User user = new User("TestUser", "123");

        // get the UI elements
        TextView username = view.findViewById(R.id.username);
        TextView followers = view.findViewById(R.id.number_followers);
        TextView following = view.findViewById(R.id.number_following);

        // set the UI elements
        username.setText("@" + user.getUsername());
        followers.setText(String.valueOf(user.getFollowerCount()));
        following.setText(String.valueOf(user.getFollowingCount()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}