package com.example.mygreatnotes.model;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {
    public List<NoteUnit> getNotes() {
        ArrayList<NoteUnit> result = new ArrayList<>();
        int noteCount = 5;
        for (int i = 1; i <= noteCount; i++) {
            result.add(new NoteUnit("Заметка № " + i, "текст заметки № " + i));
        }

        return result;
    }
}
