package com.example.blue.listeners;

import com.example.blue.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
