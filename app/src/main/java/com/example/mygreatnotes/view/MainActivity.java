package com.example.mygreatnotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentResultListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;
import com.google.android.material.navigation.NavigationView;
import com.vk.api.sdk.VK;
import com.vk.api.sdk.VKMethodCall;
import com.vk.api.sdk.auth.VKAccessToken;
import com.vk.api.sdk.auth.VKAuthCallback;
import com.vk.api.sdk.auth.VKAuthResult;
import com.vk.api.sdk.utils.VKUtils;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements PublisherHolder, MainRouterHolder {

    private static final String NOTE_EDIT = "NOTE_EDIT";
    public static String ARG_NOTE = "ARG_NOTE";
    public static String KEY_PRESENTER = "KEY_PRESENTER";
    public final Publisher publisher = new Publisher();
    private NotePresenterFragment notePresenterFragment;
    private MainRouterImplementation mainRouterImplementation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (savedInstanceState == null) {
            notePresenterFragment = new NotePresenterFragment(this);

        } else {
            notePresenterFragment = savedInstanceState.getParcelable(KEY_PRESENTER);
        }
        mainRouterImplementation = new MainRouterImplementation(getSupportFragmentManager(), isLandscape, notePresenterFragment);
        mainRouterImplementation.showAuth();

        getSupportFragmentManager().setFragmentResultListener(AuthorizationFragment.AUTH_RES, this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        mainRouterImplementation.showList();
                    }
                });

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name_full
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);

                if (item.getItemId() == R.id.option_select_list) {
                    mainRouterImplementation.showList();
                }
                if (item.getItemId() == R.id.option_select_user) {
                    mainRouterImplementation.showAuth();
                }
                if (item.getItemId() == R.id.option_settings) {
                    mainRouterImplementation.showSettings();
                }
                return true;
            }
        });
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        instanceState.putParcelable(KEY_PRESENTER, notePresenterFragment);
        super.onSaveInstanceState(instanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        notePresenterFragment = instanceState.getParcelable(KEY_PRESENTER);
        super.onRestoreInstanceState(instanceState);
    }

    @Override
    public MainRouterImplementation getMainRouter() {
        return mainRouterImplementation;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        VKAuthCallback callback = new VKAuthCallback() {
            @Override
            public void onLogin(@NotNull VKAccessToken vkAccessToken) {
                Bundle bundle = new Bundle();
                Toast.makeText(getApplicationContext(), "Залогинено", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().setFragmentResult(AuthorizationFragment.AUTH_RES, bundle);
            }

            @Override
            public void onLoginFailed(int i) {
                Toast.makeText(getApplicationContext(), "печаль", Toast.LENGTH_LONG).show();
            }
        };

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}