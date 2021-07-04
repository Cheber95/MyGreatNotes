package com.example.mygreatnotes.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NoteEditFragment extends Fragment {

    public static final String TAG = "TAG_EDIT";
    private static final String EDIT_KEY = "EDIT_KEY";
    private NoteUnit noteUnit;
    private MainRouterImplementation mainRouter;

    public static NoteEditFragment newInstance(NoteUnit noteUnit) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(EDIT_KEY, noteUnit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainRouterHolder) {
            mainRouter = ((MainRouterHolder) context).getMainRouter();
        }
    }

    @Override
    public void onDetach() {
        mainRouter = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteUnit = getArguments().getParcelable(EDIT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText noteName = view.findViewById(R.id.header_edit_note);
        TextInputEditText noteText = view.findViewById(R.id.text_edit_note);
        MaterialButton buttonSave = view.findViewById(R.id.button_save);

        noteName.setText(noteUnit.getNoteName());
        noteText.setText(noteUnit.getNoteText());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainRouter.onNoteEdited(
                        noteUnit,
                        noteName.getText().toString(),
                        noteText.getText().toString());
            }
        });
    }
}