// NoteAdapter.java
package com.example.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private ArrayList<Note> notes;

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        }

        Note note = notes.get(position);
        TextView titleTextView = view.findViewById(R.id.title_text_view);
        TextView contentTextView = view.findViewById(R.id.content_text_view);

        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());

        return view;
    }
}