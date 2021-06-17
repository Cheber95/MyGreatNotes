package com.example.mygreatnotes.presenter;

import com.example.mygreatnotes.model.NoteUnit;

public interface Observer {
    void updNote(NoteUnit noteUnit);
}
