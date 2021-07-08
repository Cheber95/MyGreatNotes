package com.example.mygreatnotes.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface NoteRepository {

    void getNotes(Callback<List<NoteUnit>> callback);

    void addNote(Callback<NoteUnit> callback);

    void deleteNote(Callback<Void> callback, NoteUnit noteUnit);

    void editNote(Callback<NoteUnit> callback, NoteUnit editableNote, @Nullable String newNoteName, @Nullable String newNoteText);

}
