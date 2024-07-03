// NoteDatabase.java
package com.example.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    private static NoteDatabase instance;

    private NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new NoteDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " + NotesContract.NotesEntry.TABLE_NAME + " ("
                + NotesContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NotesContract.NotesEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + NotesContract.NotesEntry.COLUMN_CONTENT + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_NOTES_TABLE = "DROP TABLE IF EXISTS " + NotesContract.NotesEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_NOTES_TABLE);
        onCreate(db);
    }

    public void insertNote(String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.insert("notes", null, values);
        db.releaseReference(); // release the database reference
    }

    public void updateNote(int id, String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        db.update("notes", values, "_id =?", new String[]{String.valueOf(id)});
        db.releaseReference(); // release the database reference
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "_id =?", new String[]{String.valueOf(id)});
        db.releaseReference(); // release the database reference
    }
}