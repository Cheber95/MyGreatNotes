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

import java.util.List;

public class NotePresenterFragment implements Parcelable {

    private static final String KEY_REPO = "KEY_REPO";
    private MainActivity mainActivity;
    private List<NoteUnit> noteRepository;
    private NotesAdapterRecyclerView notesAdapterRecyclerView;

    public NotePresenterFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        NoteRepository noteRepo = new NoteRepository();
        this.noteRepository = noteRepo.getNotes();
        notesAdapterRecyclerView = new NotesAdapterRecyclerView();
        notesAdapterRecyclerView.setData(noteRepo.getNotes());
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
        dest.writeList(noteRepository);
    }
}
