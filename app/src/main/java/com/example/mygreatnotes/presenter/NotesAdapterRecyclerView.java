package com.example.mygreatnotes.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygreatnotes.R;
import com.example.mygreatnotes.model.NoteUnit;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapterRecyclerView extends RecyclerView.Adapter<NotesAdapterRecyclerView.NoteViewHolder> {

    private ArrayList<NoteUnit> data = new ArrayList<>();

    public interface OnNoteClickListener {
        void onNoteClickListener(@NonNull NoteUnit noteUnit);
    }

    private OnNoteClickListener listener;

    public void setListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public OnNoteClickListener getListener() {
        return listener;
    }

    public void setData(List<NoteUnit> inputData) {
        data.clear();
        data.addAll(inputData);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapterRecyclerView.NoteViewHolder holder, int position) {

        NoteUnit noteUnit = data.get(position);

        holder.noteTitle.setText(noteUnit.getNoteName());
        holder.noteText.setText(noteUnit.getNoteText());
        holder.noteDate.setText(noteUnit.getNoteDateToString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteText;
        TextView noteDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteText = itemView.findViewById(R.id.note_text);
            noteDate = itemView.findViewById(R.id.note_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onNoteClickListener(data.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

}
