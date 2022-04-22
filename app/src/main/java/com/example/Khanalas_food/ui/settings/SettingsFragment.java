package com.example.Khanalas_food.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Khanalas_food.R;
import com.example.Khanalas_food.SharedPrefManager;
import com.example.Khanalas_food.User;

public class SettingsFragment extends Fragment {
    TextView textViewId, textViewLogin, textViewEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        textViewId = (TextView) view.findViewById(R.id.textViewId);
        textViewLogin = (TextView) view.findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getting the current user
        User user = SharedPrefManager.getInstance(getContext()).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewLogin.setText(user.getLogin());
        textViewEmail.setText(user.getEmail());
    }
}