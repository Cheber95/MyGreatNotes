package com.example.mygreatnotes.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteRepository;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;

public class NoteListFragment extends Fragment {

    public interface OnNoteClicked {
        void onNoteClicked(NoteUnit noteUnit);
    }

    public static final String KEY_LIST = "KEY_LIST";
    private OnNoteClicked onNoteClicked;
    private Publisher publisher;

    public static NoteListFragment newInstance(NotePresenterFragment notePresenterFragment) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_LIST, notePresenterFragment);
        fragment.setArguments(bundle);
        return fragment;
    }

    private NotePresenterFragment notePresenterFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }

        if (context instanceof PublisherHolder) {
            publisher = ( (PublisherHolder) context).getPublisher();
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_appbar_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Toast.makeText(requireContext(),"Selected option «Create new»", Toast.LENGTH_LONG).show();
            return true;
        }

        if (item.getItemId() == R.id.menu_sort) {
            Toast.makeText(requireContext(),"Selected option «Sort by name", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.notePresenterFragment = getArguments().getParcelable(KEY_LIST);
        LinearLayout notesListContainer = view.findViewById(R.id.note_list_container);

        for (int i = 0; i < notePresenterFragment.getNotesCount(); i++) {
            NoteUnit noteUnit = notePresenterFragment.getNote(i);
            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesListContainer, false);
            TextView noteHeader = itemView.findViewById(R.id.item_note_textview);
            noteHeader.setText(noteUnit.getNoteName());

            noteHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNoteClicked != null ) {
                        onNoteClicked.onNoteClicked(noteUnit);
                    }

                    if (publisher != null ) {
                        publisher.notify(noteUnit);
                    }
                }
            });

            notesListContainer.addView(noteHeader);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
