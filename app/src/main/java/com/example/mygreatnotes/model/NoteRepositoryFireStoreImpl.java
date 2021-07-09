package com.example.mygreatnotes.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NoteRepositoryFireStoreImpl implements NoteRepository {

    private static final String NOTES = "notes";
    private static final String DATE = "date";
    private static final String NAME = "name";
    private static final String TEXT = "text";

    private ArrayList<NoteUnit> noteRepository = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<NoteUnit>> callback) {

        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc: task.getResult()) {
                                String noteID = doc.getId();
                                String noteName = doc.getString(NAME);
                                String noteText = doc.getString(TEXT);

                                NoteUnit parcelNote = new NoteUnit(noteID, noteName, noteText);
                                Calendar noteDate = Calendar.getInstance();
                                noteDate.setTime(((Timestamp) doc.get(DATE)).toDate());
                                parcelNote.setNoteNewDate(noteDate);
                                noteRepository.add(parcelNote);
                            }
                            callback.onSuccess(noteRepository);
                        }
                    }
                });
    }

    @Override
    public void addNote(Callback<NoteUnit> callback) {
        String noteName = "";
        String noteText = "";
        Date noteDate = new Date();
        noteDate.setTime(System.currentTimeMillis());

        HashMap<String, Object> noteToSend = new HashMap<>();
        noteToSend.put(NAME,noteName);
        noteToSend.put(TEXT,noteText);
        noteToSend.put(DATE, noteDate);
        firebaseFirestore.collection(NOTES)
                .add(noteToSend)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            NoteUnit noteUnit = new NoteUnit(
                                    task.getResult().getId(),
                                    noteName,
                                    noteText
                            );
                            Calendar bufferDate = Calendar.getInstance();
                            bufferDate.setTime(noteDate);
                            noteUnit.setNoteNewDate(bufferDate);
                            callback.onSuccess(noteUnit);
                        }
                    }
                });
    }

    @Override
    public void deleteNote(Callback<Void> callback, NoteUnit noteUnit) {

    }

    @Override
    public void editNote(Callback<NoteUnit> callback, NoteUnit editableNote, String newNoteName, String newNoteText) {
        HashMap<String, Object> noteToUpdate = new HashMap<>();
        if (newNoteName != null) {
            editableNote.setNoteNewName(newNoteName);
        }
        if (newNoteText != null) {
            editableNote.setNoteNewText(newNoteText);
        }
        Date noteDate = new Date();
        noteDate.setTime(System.currentTimeMillis());

        Calendar bufferDate = Calendar.getInstance();
        bufferDate.setTime(noteDate);
        editableNote.setNoteNewDate(bufferDate);

        noteToUpdate.put(NAME,editableNote.getNoteName());
        noteToUpdate.put(TEXT,editableNote.getNoteText());
        noteToUpdate.put(DATE,noteDate);
        firebaseFirestore.collection(NOTES)
                .document(editableNote.getNoteKey())
                .update(noteToUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        callback.onSuccess(editableNote);
                    }
                });
    }
}
