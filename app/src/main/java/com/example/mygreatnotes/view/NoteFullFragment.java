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
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

    public static NoteFullFragment newInstance(NoteUnit noteUnit) {
        NoteFullFragment fragment = new NoteFullFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE, noteUnit);
        fragment.setArguments(bundle);
        return fragment;
    }

    private NotePresenterFragment notePresenterFragment;

    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.subscribe(this);
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.unSubscribe(this);
        }
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notePresenterFragment = new NotePresenterFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_full, container, false);
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

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int setYear, int setMonth, int setDay) {
                noteCalendar.set(setYear,setMonth,setDay);
                noteUnit.setNoteNewDate(noteCalendar);
                noteDate.setText(noteUnit.getNoteDateToString());
            }
        }, noteCalendar.get(Calendar.YEAR), noteCalendar.get(Calendar.MONTH),noteCalendar.get(Calendar.DAY_OF_MONTH));

        TimePickerDialog timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int setHourOfDay, int setMinute) {
                noteCalendar.set(Calendar.HOUR_OF_DAY,setHourOfDay);
                noteCalendar.set(Calendar.MINUTE,setMinute);
                noteUnit.setNoteNewDate(noteCalendar);
                noteDate.setText(noteUnit.getNoteDateToString());
            }
        }, noteCalendar.get(Calendar.HOUR_OF_DAY), noteCalendar.get(Calendar.MINUTE),true);

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });

    }

    @Override
    public void updNote(NoteUnit noteUnit) {
        Toast.makeText(requireContext(),(noteUnit.getNoteName()+" pressed"),Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_NOTE,noteUnit);
        setArguments(bundle);

        //View view = getLayoutInflater().inflate(R.layout.fragment_note_full, )

        //View view = LayoutInflater.from(requireContext()).in;
    }
}