package com.example.mygreatnotes.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mygreatnotes.model.Callback;
import com.example.mygreatnotes.model.NoteRepositoryFireStoreImpl;
import com.example.mygreatnotes.model.NoteRepositoryImpl;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.view.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotePresenterFragment implements Parcelable {

    private static final String KEY_REPO = "KEY_REPO";
    private MainActivity mainActivity;
    private List<NoteUnit> noteRepository;
    private NoteRepositoryFireStoreImpl noteRepo = new NoteRepositoryFireStoreImpl();;
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
        noteRepository = new ArrayList<>();
        notesAdapterRecyclerView = new NotesAdapterRecyclerView();

        noteRepo.getNotes(new Callback<List<NoteUnit>>() {
            @Override
            public void onSuccess(List<NoteUnit> result) {
                noteRepository.addAll(result);
                notesAdapterRecyclerView.setData(noteRepository);
                notesAdapterRecyclerView.notifyDataSetChanged();
            }
        });
    }

    protected NotePresenterFragment(Parcel in) {
         in.readList(this.noteRepository,NoteUnit.class.getClassLoader());
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

    public NoteUnit getNote(int i) {
        return noteRepository.get(i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(noteRepository);
    }

    public void editNote(NoteUnit noteUnit, String noteNewName, String noteNewText) {
        int position = noteRepository.indexOf(noteUnit);
        noteRepo.editNote(new Callback<NoteUnit>() {
            @Override
            public void onSuccess(NoteUnit result) {
                noteRepository.set(position,result);
                mainActivity.getMainRouter().onNoteEditSelected(result);
                notesAdapterRecyclerView.setData(noteRepository);
                notesAdapterRecyclerView.notifyItemChanged(position);
            }
        }, noteUnit, noteNewName,noteNewText);
    }

    public void deleteNote(NoteUnit noteUnit) {
        noteRepo.deleteNote(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                int position = noteRepository.indexOf(noteUnit);
                noteRepository.remove(noteUnit);
                notesAdapterRecyclerView.setData(noteRepository);
                notesAdapterRecyclerView.notifyItemRemoved(position);
            }
        }, noteUnit);
    }

    public void addNote() {
        noteRepo.addNote(new Callback<NoteUnit>() {
            @Override
            public void onSuccess(NoteUnit result) {
                noteRepository.add(result);
                mainActivity.getMainRouter().onNoteEditSelected(result);
                notesAdapterRecyclerView.setData(noteRepository);
                notesAdapterRecyclerView.notifyItemInserted(notesAdapterRecyclerView.getItemCount());
            }
        });
    }

    public void sortNotes() {
        Comparator<NoteUnit> comparator = Comparator.comparing(NoteUnit::getNoteName);
        Collections.sort(noteRepository, comparator);
        notesAdapterRecyclerView.setData(noteRepository);
        notesAdapterRecyclerView.notifyDataSetChanged();
    }
}
