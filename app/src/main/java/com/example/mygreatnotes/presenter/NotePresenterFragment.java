package com.example.mygreatnotes.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteRepository;
import com.example.mygreatnotes.model.NoteUnit;

import java.util.List;

public class NotePresenterFragment {

    private Fragment myFragment;
    private NoteRepository noteRepository;

    public NotePresenterFragment(Fragment myFragment) {
        this.myFragment = myFragment;
        this.noteRepository = new NoteRepository();
    }

    public int getNotesCount() {
        return noteRepository.getNotes().size();
    }

    public NoteUnit getNote(int i) {
        return noteRepository.getNotes().get(i);
    }
}
