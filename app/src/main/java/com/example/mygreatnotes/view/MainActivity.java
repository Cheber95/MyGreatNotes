package com.example.mygreatnotes.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;
import com.google.android.material.navigation.NavigationView;

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
        mainRouterImplementation.showList();

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
}