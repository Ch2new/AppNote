package com.example.testgit.listener;

import com.example.testgit.entities.Note;

public interface NotesListener {
     void onNoteClicked(Note note, int position);
}
