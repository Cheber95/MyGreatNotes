package com.example.mygreatnotes.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;
import com.example.mygreatnotes.presenter.NotePresenterFragment;
import com.example.mygreatnotes.presenter.Observer;
import com.example.mygreatnotes.presenter.Publisher;
import com.example.mygreatnotes.presenter.PublisherHolder;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class NoteFullFragment extends Fragment implements Observer {

    public static final String KEY_NOTE = "ARG_NOTE";
    public static final String TAG = "TAG_FULL";

    public static NoteFullFragment newInstance(NoteUnit noteUnit) {
        NoteFullFragment fragment = new NoteFullFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, noteUnit);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Publisher publisher;
    private MainRouterImplementation mainRouter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.subscribe(this);
        }

        if (context instanceof MainRouterHolder) {
            mainRouter = ((MainRouterHolder) context).getMainRouter();
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.unSubscribe(this);
        }
        publisher = null;
        mainRouter = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_full, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_appbar_full,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NoteUnit noteUnit = getArguments().getParcelable(KEY_NOTE);
        if (item.getItemId() == R.id.menu_edit) {
            mainRouter.onNoteEditSelected(noteUnit);
            return true;
        }
        if (item.getItemId() == R.id.menu_del) {
            mainRouter.onNoteDeleteSelected(noteUnit);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noteHeader = view.findViewById(R.id.header_det_note);
        TextView noteText = view.findViewById(R.id.text_note);
        TextView noteDate = view.findViewById(R.id.date_note);

        NoteUnit noteUnit = getArguments().getParcelable(KEY_NOTE);

        noteHeader.setText(noteUnit.getNoteName());
        noteText.setText(noteUnit.getNoteText());
        noteDate.setText(noteUnit.getNoteDateToString());
        Calendar noteCalendar = noteUnit.getNoteDate();

        MaterialButton btnSetDate = view.findViewById(R.id.button_date);
        MaterialButton btnSetTime = view.findViewById(R.id.button_time);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), (view1, setYear, setMonth, setDay) -> {
            noteCalendar.set(setYear,setMonth,setDay);
            noteUnit.setNoteNewDate(noteCalendar);
            noteDate.setText(noteUnit.getNoteDateToString());
        }, noteCalendar.get(Calendar.YEAR), noteCalendar.get(Calendar.MONTH),noteCalendar.get(Calendar.DAY_OF_MONTH));

        TimePickerDialog timePicker = new TimePickerDialog(getContext(), (view12, setHourOfDay, setMinute) -> {
            noteCalendar.set(Calendar.HOUR_OF_DAY,setHourOfDay);
            noteCalendar.set(Calendar.MINUTE,setMinute);
            noteUnit.setNoteNewDate(noteCalendar);
            noteDate.setText(noteUnit.getNoteDateToString());
        }, noteCalendar.get(Calendar.HOUR_OF_DAY), noteCalendar.get(Calendar.MINUTE),true);

        btnSetDate.setOnClickListener(v -> datePicker.show());

        btnSetTime.setOnClickListener(v -> timePicker.show());

    }

    @Override
    public void updNote(NoteUnit noteUnit) {
        Toast.makeText(requireContext(),(noteUnit.getNoteName()+" pressed"),Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE,noteUnit);
        setArguments(bundle);
    }
}