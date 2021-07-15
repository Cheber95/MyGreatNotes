package com.example.mygreatnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class NoteUnit implements Parcelable {
    public static final String NOTE_KEY = "NOTE_KEY";
    private String noteKey;
    private String noteName;
    private String noteText;
    private Calendar noteDate = Calendar.getInstance();

    public void setNoteNewName(String noteNewName) {
        this.noteName = noteNewName;
        noteDate.setTimeInMillis(System.currentTimeMillis());
    }

    public void setNoteNewText(String noteNewText) {
        this.noteText = noteNewText;
        noteDate.setTimeInMillis(System.currentTimeMillis());
    }

    public void setNoteNewDate(Calendar noteNewDate) {
        this.noteDate = noteNewDate;
    }


    public NoteUnit(String noteKey, String noteName, String noteText) {
        this.noteKey = noteKey;
        this.noteName = noteName;
        this.noteText = noteText;
        noteDate.setTimeInMillis(System.currentTimeMillis());
    }

    protected NoteUnit(Parcel in) {
        noteKey = in.readString();
        noteName = in.readString();
        noteText = in.readString();
        noteDate.setTimeInMillis(in.readLong());
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

    public String getNoteKey() {
        return noteKey;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteText() {
        return noteText;
    }

    public Calendar getNoteDate() {
        return noteDate;
    }

    public String getNoteDateToString() {
        return noteDate.getTime().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteKey);
        dest.writeString(noteName);
        dest.writeString(noteText);
        dest.writeLong(noteDate.getTimeInMillis());
    }
}
