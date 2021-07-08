package com.example.mygreatnotes.model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository  {

    ArrayList<NoteUnit> noteRepo;

    public NoteRepository() {
        this.noteRepo = new ArrayList<>();
        int noteCount = 5;
        for (int i = 1; i <= noteCount; i++) {
            noteRepo.add(new NoteUnit(i,"Заметка № " + i, "текст заметки № " + i));
        }
        String longtext = "Важнейшее значение в романе имеют философские взгляды писателя. Публицистические главы предваряют и объясняют художественное описание событий. Фатализм Толстого связан с его пониманием стихийности истории как «бессознательной, общей, роевой жизни человечества». Главная мысль романа, по словам самого Толстого, — «мысль народная». Народ, в понимании Толстого — главная движущая сила истории, носитель лучших человеческих качеств. Главные герои проходят путь к народу (Пьер на Бородинском поле; «наш барин» — называли Безухова солдаты). Идеал Толстого воплощён в образе Платона Каратаева. Идеал женский — в образе Наташи Ростовой. Кутузов и Наполеон — нравственные полюсы романа: «Нет величия там, где нет простоты, добра и правды». «Что нужно для счастья? Тихая семейная жизнь… с возможностью делать добро людям» (Л. Н. Толстой). ";
        noteRepo.get(1).setNoteNewText(longtext);
    }

    public List<NoteUnit> getNotes() {
        return noteRepo;
    }

    public void setData(List<NoteUnit> noteRepo) {
        List<NoteUnit> tmpData = new ArrayList<>();
        tmpData.addAll(noteRepo);
        this.noteRepo.clear();
        this.noteRepo.addAll(tmpData);
    }

    public void addNote(NoteUnit noteUnit) {
        noteRepo.add(noteUnit);
    }

    public void deleteNote(NoteUnit noteUnit) {
        noteRepo.remove(noteUnit);
    }

    public void editNote(NoteUnit editableNote, @Nullable String newNoteName, @Nullable String newNoteText) {
        int indexOfNote = noteRepo.indexOf(editableNote);
        if (newNoteName != null) {
            noteRepo.get(indexOfNote).setNoteNewName(newNoteName);
        }
        if (newNoteText != null) {
            noteRepo.get(indexOfNote).setNoteNewText(newNoteText);
        }
    }

}
