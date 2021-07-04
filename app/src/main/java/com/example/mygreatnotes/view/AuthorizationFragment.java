package com.example.mygreatnotes.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mygreatnotes.R;

public class AuthorizationFragment extends Fragment {
    private static final String TAG = "TAG_AUTH";

    public static AuthorizationFragment newInstance() {
        AuthorizationFragment fragment = new AuthorizationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }
}
