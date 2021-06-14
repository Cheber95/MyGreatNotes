package com.example.mygreatnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class NoteUnit implements Parcelable {
    private final int noteKey;
    private final String noteName;
    private final String noteText;
    private final Calendar noteDate = Calendar.getInstance();

    public void setNoteNewName(String noteNewName) {
        this.noteNewName = noteNewName;
        noteNewDate.setTimeInMillis(System.currentTimeMillis());
    }

    public void setNoteNewText(String noteNewText) {
        this.noteNewText = noteNewText;
        noteNewDate.setTimeInMillis(System.currentTimeMillis());
    }

    public void setNoteNewDate(Calendar noteNewDate) {
        this.noteNewDate = noteNewDate;
    }

    private String noteNewName = "";
    private String noteNewText = "";
    private Calendar noteNewDate;

    public NoteUnit(int noteKey, String noteName, String noteText) {
        this.noteKey = noteKey;
        this.noteName = noteName;
        this.noteText = noteText;
        noteDate.setTimeInMillis(System.currentTimeMillis());

        this.noteNewName = this.noteName;
        this.noteNewText = this.noteText;
        this.noteNewDate = this.noteDate;
    }

    protected NoteUnit(Parcel in) {
        noteKey = in.readInt();
        noteName = in.readString();
        noteText = in.readString();
        noteDate.setTimeInMillis(in.readLong());
        this.noteNewName = noteName;
        this.noteNewText = noteText;
        this.noteNewDate = noteDate;
    }

    public static final Creator<NoteUnit> CREATOR = new Creator<NoteUnit>() {
        @Override
        public NoteUnit createFromParcel(Parcel in) {
            return new NoteUnit(in);
        }

        @Override
        public NoteUnit[] newArray(int size) {
            return new NoteUnit[size];
        }
    };

    public void getInfo() {
        System.out.println("Имя заметки: " + this.noteName);
        System.out.println("Текст заметки: " + this.noteText);
        System.out.println("Дата создания: " + this.noteDate.toString());
    }

    public int getNoteKey() {
        return noteKey;
    }

    public String getNoteName() {
        return noteNewName;
    }

    public String getNoteText() {
        return noteNewText;
    }

    public Calendar getNoteDate() {
        return noteNewDate;
    }

    public String getNoteDateToString() {
        String res;
        int year = noteNewDate.get(Calendar.YEAR);
        int month = noteNewDate.get(Calendar.MONTH) + 1;
        int day = noteNewDate.get(Calendar.DAY_OF_MONTH);
        int hour = noteNewDate.get(Calendar.HOUR_OF_DAY);
        int min = noteNewDate.get(Calendar.MINUTE);
        String monthString = "";
        String hourString = "";
        String minString = "";

        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = String.valueOf(month);
        }

        if (hour < 10) {
            hourString = "0" + hour;
        } else {
            hourString = String.valueOf(hour);
        }

        if (min < 10) {
            minString = "0" + min;
        } else {
            minString = String.valueOf(min);
        }

        res = day + "." + monthString + "." + year + " " + hourString + ":" + minString;
        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(noteKey);
        dest.writeString(noteNewName);
        dest.writeString(noteNewText);
        dest.writeLong(noteNewDate.getTimeInMillis());
    }
}
