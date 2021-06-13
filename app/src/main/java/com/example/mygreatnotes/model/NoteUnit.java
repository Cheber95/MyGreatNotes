package com.example.mygreatnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteUnit implements Parcelable {
    private final String noteName;
    private final String noteText;
    private final Date noteDateOfCreate = new Date();

    public NoteUnit(String noteName, String noteText) {
        this.noteName = noteName;
        this.noteText = noteText;
        noteDateOfCreate.setTime(System.currentTimeMillis());
    }

    protected NoteUnit(Parcel in) {
        noteName = in.readString();
        noteText = in.readString();
        noteDateOfCreate.setTime(in.readLong());
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
        System.out.println("Дата создания: " + this.noteDateOfCreate.toString());
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteText() {
        return noteText;
    }

    public Date getNoteDateOfCreate() {
        return noteDateOfCreate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteName);
        dest.writeString(noteText);
        dest.writeLong(noteDateOfCreate.getTime());
    }
}
