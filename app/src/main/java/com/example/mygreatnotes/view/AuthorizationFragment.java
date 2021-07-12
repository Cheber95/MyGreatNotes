package com.example.mygreatnotes.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mygreatnotes.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKAuthResult;
import com.vk.api.sdk.auth.VKScope;
import com.vk.api.sdk.utils.VKUtils;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class AuthorizationFragment extends Fragment {
    public static final String TAG = "TAG_AUTH";
    public static final int AUTH_REQUEST_CODE = 1;
    public static final String AUTH_RES = "AUTH_RES";

    private GoogleSignInClient googleSignInClient;

    public static AuthorizationFragment newInstance() {
        AuthorizationFragment fragment = new AuthorizationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(),options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.sign_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,AUTH_REQUEST_CODE);
            }
        });
        MaterialButton signVK = view.findViewById(R.id.sign_btn_VK);
        signVK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                VK.login(requireActivity());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == AUTH_REQUEST_CODE) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult();
                getParentFragmentManager().setFragmentResult(AUTH_RES,new Bundle());
            } catch (Exception e) {
                Toast.makeText(requireContext(),"печаль", Toast.LENGTH_LONG).show();
            }
        }
    }
}