// MainActivity.java
package com.example.note;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NoteDatabase noteDatabase = NoteDatabase.getInstance(this);
    private ListView noteListView;
    private EditText titleEditText;
    private EditText contentEditText;
    private Note selectedNote;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;
    private ArrayList<Note> notes;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(this);
        noteListView = findViewById(R.id.note_list_view);
        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        addButton = findViewById(R.id.add_button);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, notes);
        noteListView.setAdapter(noteAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNote = notes.get(position);
                titleEditText.setText(selectedNote.getTitle());
                contentEditText.setText(selectedNote.getContent());
            }
        });
    }

    private void addNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        Note note = new Note(notes.size() + 1, title, content);
        noteDatabase.insertNote("New Note", "This is a new note");
        notes.add(note);
        noteAdapter.notifyDataSetChanged();
        titleEditText.setText("");
        contentEditText.setText("");
    }

    private void editNote() {
        if (selectedNote != null) {
            String title = titleEditText.getText().toString();
            String content = contentEditText.getText().toString();
            selectedNote.setTitle(title);
            selectedNote.setContent(content);
            noteDatabase.updateNote(selectedNote.getId(), selectedNote.getTitle(), selectedNote.getContent());
            noteAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Please select a note to edit", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        if (selectedNote != null) {
            noteDatabase.deleteNote(selectedNote.getId());
            notes.remove(selectedNote);
            noteAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Please select a note to delete", Toast.LENGTH_SHORT).show();
        }
    }
}