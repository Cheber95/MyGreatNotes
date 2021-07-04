package com.example.mygreatnotes.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;

import static com.example.mygreatnotes.view.MainActivity.ARG_NOTE;

public class MainRouterImplementation implements MainRouter{

    private FragmentManager fragmentManager;
    private boolean isLandscape;
    private NotePresenterFragment notePresenterFragment;

    public MainRouterImplementation(FragmentManager fragmentManager, boolean isLandscape, NotePresenterFragment notePresenterFragment) {
        this.fragmentManager = fragmentManager;
        this.isLandscape = isLandscape;
        this.notePresenterFragment = notePresenterFragment;
    }

    @Override
    public void showList() {
        if (isLandscape) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,NoteListFragment.newInstance(notePresenterFragment))
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,NoteListFragment.newInstance(notePresenterFragment))
                    .commit();
        }
    }

    @Override
    public void showSettings() {
        if (isLandscape) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,SettingsFragment.newInstance())
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,SettingsFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void showAuth() {
        if (isLandscape) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,AuthorizationFragment.newInstance())
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container,AuthorizationFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onNoteClicked(NoteUnit noteUnit) {
        if (isLandscape) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container_details, NoteFullFragment.newInstance(noteUnit), NoteFullFragment.TAG)
                    .commit();
            } else {
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(ARG_NOTE)
                    .replace(R.id.container, NoteFullFragment.newInstance(noteUnit))
                    .commit();
            }
    }

    @Override
    public void onNoteLongClick(@NonNull NoteUnit noteUnit, int noteIndex) {

    }

    @Override
    public void onNoteEditSelected(NoteUnit noteUnit) {
        if (isLandscape) {
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(NoteEditFragment.TAG)
                    .replace(R.id.container_details, NoteEditFragment.newInstance(noteUnit),NoteEditFragment.TAG)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(NoteEditFragment.TAG)
                    .replace(R.id.container, NoteEditFragment.newInstance(noteUnit),NoteEditFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onNoteDeleteSelected(NoteUnit noteUnit) {
        comeBack();
        notePresenterFragment.deleteNote(noteUnit);
    }

    @Override
    public void onNoteEdited(NoteUnit noteUnit, String newNoteName, String newNoteText) {
        notePresenterFragment.editNote(noteUnit,newNoteName,newNoteText);
        comeBack();
    }

    @Override
    public void comeBack() {
        fragmentManager.popBackStack();
    }

}
