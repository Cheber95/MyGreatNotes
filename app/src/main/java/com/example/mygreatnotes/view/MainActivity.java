package com.example.mygreatnotes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteClicked,
        PublisherHolder {

    public static String ARG_NOTE = "ARG_NOTE";
    public final Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}