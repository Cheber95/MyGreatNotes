package com.example.mygreatnotes.presenter;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteRepository;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.view.MainActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotePresenterFragment implements Parcelable {

    private static final String KEY_REPO = "KEY_REPO";
    private MainActivity mainActivity;
    private List<NoteUnit> noteRepository;
    private NoteRepository noteRepo;
    private NotesAdapterRecyclerView notesAdapterRecyclerView;
    private int contextMenuIndex;
    private NoteUnit contextMenuNote;

    public void setContextMenuIndex(int contextMenuIndex) {
        this.contextMenuIndex = contextMenuIndex;
    }

    public void setContextMenuNote(NoteUnit contextMenuNote) {
        this.contextMenuNote = contextMenuNote;
    }

    public int getContextMenuIndex() {
        return contextMenuIndex;
    }

    public NoteUnit getContextMenuNote() {
        return contextMenuNote;
    }

    public NotePresenterFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        noteRepo = new NoteRepository();
        this.noteRepository = noteRepo.getNotes();
        notesAdapterRecyclerView = new NotesAdapterRecyclerView();
        notesAdapterRecyclerView.setData(noteRepo.getNotes());
    }

    protected NotePresenterFragment(Parcel in) {
         in.readList(this.noteRepository,NoteUnit.class.getClassLoader());
         noteRepo.setData(noteRepository);
    }

    public static final Creator<NotePresenterFragment> CREATOR = new Creator<NotePresenterFragment>() {
        @Override
        public NotePresenterFragment createFromParcel(Parcel in) {
            return new NotePresenterFragment(in);
        }

        @Override
        public NotePresenterFragment[] newArray(int size) {
            return new NotePresenterFragment[size];
        }
    };

    public NotesAdapterRecyclerView getNotesAdapterRecyclerView() {
        return notesAdapterRecyclerView;
    }

    public int getNotesCount() {
        return noteRepository.size();
    }

    public NoteUnit getNote(int i) {
        return noteRepository.get(i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(noteRepo.getNotes());
    }

    public void editNote(NoteUnit noteUnit, String noteNewName, String noteNewText) {
        int position = noteRepo.getNotes().indexOf(noteUnit);
        noteRepo.editNote(noteUnit,noteNewName,noteNewText);
        notesAdapterRecyclerView.notifyItemChanged(position);
    }

    public void deleteNote(NoteUnit noteUnit) {
        int position = noteRepo.getNotes().indexOf(noteUnit);
        noteRepo.deleteNote(noteUnit);
        notesAdapterRecyclerView.setData(noteRepository);
        notesAdapterRecyclerView.notifyItemRemoved(position);
    }

    public NoteUnit addNote() {
        int newNoteKey = noteRepository.size(); // плохая манера, подумать потом как создавать ключ
        NoteUnit newNoteUnit = new NoteUnit(newNoteKey, "", "");
        noteRepo.addNote(newNoteUnit);
        notesAdapterRecyclerView.setData(noteRepo.getNotes());
        notesAdapterRecyclerView.notifyItemInserted(notesAdapterRecyclerView.getItemCount());
        return newNoteUnit;
    }

    public void sortNotes() {
        Comparator<NoteUnit> comparator = Comparator.comparing(NoteUnit::getNoteName);
        Collections.sort(noteRepository, comparator);
        notesAdapterRecyclerView.setData(noteRepo.getNotes());
        notesAdapterRecyclerView.notifyDataSetChanged();
    }
}
