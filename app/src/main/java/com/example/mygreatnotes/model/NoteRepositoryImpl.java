package com.example.mygreatnotes.model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteRepositoryImpl implements NoteRepository{

    ArrayList<NoteUnit> noteRepo;

    public NoteRepositoryImpl() {
        this.noteRepo = new ArrayList<>();
        int noteCount = 5;
        for (int i = 1; i <= noteCount; i++) {
            noteRepo.add(new NoteUnit(i,"Заметка № " + i, "текст заметки № " + i));
        }
        String longtext = "Важнейшее значение в романе имеют философские взгляды писателя. Публицистические главы предваряют и объясняют художественное описание событий. Фатализм Толстого связан с его пониманием стихийности истории как «бессознательной, общей, роевой жизни человечества». Главная мысль романа, по словам самого Толстого, — «мысль народная». Народ, в понимании Толстого — главная движущая сила истории, носитель лучших человеческих качеств. Главные герои проходят путь к народу (Пьер на Бородинском поле; «наш барин» — называли Безухова солдаты). Идеал Толстого воплощён в образе Платона Каратаева. Идеал женский — в образе Наташи Ростовой. Кутузов и Наполеон — нравственные полюсы романа: «Нет величия там, где нет простоты, добра и правды». «Что нужно для счастья? Тихая семейная жизнь… с возможностью делать добро людям» (Л. Н. Толстой). ";
        noteRepo.get(1).setNoteNewText(longtext);
    }


    @Override
    public void getNotes(Callback<List<NoteUnit>> callback) {
        callback.onSuccess(noteRepo);
    }

    public void setData(List<NoteUnit> noteRepo) {
        List<NoteUnit> tmpData = new ArrayList<>();
        tmpData.addAll(noteRepo);
        this.noteRepo.clear();
        this.noteRepo.addAll(tmpData);
    }

    @Override
    public void addNote(Callback<NoteUnit> callback) {
        NoteUnit noteUnit = new NoteUnit(UUID.randomUUID().hashCode(),"","");
        noteRepo.add(noteUnit);
        callback.onSuccess(noteUnit);
    }

    @Override
    public void deleteNote(Callback<Void>callback, NoteUnit noteUnit) {
        noteRepo.remove(noteUnit);
        callback.onSuccess(null);
    }

    @Override
    public void editNote(Callback<NoteUnit> callback, NoteUnit editableNote, String newNoteName, String newNoteText) {
        int indexOfNote = noteRepo.indexOf(editableNote);
        if (newNoteName != null) {
            noteRepo.get(indexOfNote).setNoteNewName(newNoteName);
        }
        if (newNoteText != null) {
            noteRepo.get(indexOfNote).setNoteNewText(newNoteText);
        }
        callback.onSuccess(noteRepo.get(indexOfNote));
    }
}