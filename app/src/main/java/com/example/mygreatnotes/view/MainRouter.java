package com.example.mygreatnotes.view;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;

public interface MainRouter {

    void showList();

    void showSettings();

    void showAuth();

    void onNoteClicked(NoteUnit noteUnit);

    void onNoteLongClick(@NonNull NoteUnit noteUnit, int noteIndex);

    void onNoteEditSelected(NoteUnit noteUnit);

    void onNoteDeleteSelected(NoteUnit noteUnit);

    void onNoteEdited(NoteUnit noteUnit, String newNoteName, String newNoteText);

    void comeBack();
}
