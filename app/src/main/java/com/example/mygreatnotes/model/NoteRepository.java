package com.example.mygreatnotes.model;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository  {

    public List<NoteUnit> getNotes() {
        ArrayList<NoteUnit> noteRepo = new ArrayList<>();
        int noteCount = 5;
        for (int i = 1; i <= noteCount; i++) {
            noteRepo.add(new NoteUnit(i,"Заметка № " + i, "текст заметки № " + i));
        }
        return noteRepo;
    }
}
