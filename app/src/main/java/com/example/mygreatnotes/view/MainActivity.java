package com.example.mygreatnotes.view;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked,
        PublisherHolder {

    public static String ARG_NOTE = "ARG_NOTE";
    public final Publisher publisher = new Publisher();
    private final NotePresenterFragment notePresenterFragment= new NotePresenterFragment(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        NoteListFragment noteListFragment = NoteListFragment.newInstance(notePresenterFragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,noteListFragment)
                .commit();

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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,noteListFragment)
                            .commit();
                }
                if (item.getItemId() == R.id.option_select_user) {
                    Toast.makeText(MainActivity.this,"select user", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.option_settings) {
                    Toast.makeText(MainActivity.this,"settings", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    @Override
    public void onNoteClicked(NoteUnit noteUnit) {

        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_details, NoteFullFragment.newInstance(noteUnit))
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(ARG_NOTE)
                    .replace(R.id.container, NoteFullFragment.newInstance(noteUnit))
                    .commit();
        }
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        // TODO
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        // TODO
    }
}