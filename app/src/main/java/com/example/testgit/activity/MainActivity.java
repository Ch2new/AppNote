package com.example.testgit.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.testgit.R;
import com.example.testgit.activity.CreateNoteActivity;
import com.example.testgit.database.NotesDatabase;
import com.example.testgit.entities.Note;

import java.util.List;
import com.example.testgit.adapters.NoteAdapters;
import com.example.testgit.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_AND_NOTE = 1;

    private RecyclerView noteRecyclerView;
    private List<Note> noteList;
    private NoteAdapters notesAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);

        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
//               startActivity(intent);            }
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_AND_NOTE
                );
            }
        });

        noteRecyclerView = findViewById(R.id.noteRecycleView);
        noteRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        notesAdapters = new NoteAdapters(noteList);
        noteRecyclerView.setAdapter(notesAdapters);

        getNote();
    }

    private void getNote(){
        class getNotesTask extends AsyncTask<Void, Void, List<Note>>{
            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).notedao().getAllNotes();
            }
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (noteList.size() == 0){
                    noteList.addAll(notes);
                    notesAdapters.notifyDataSetChanged();
                }else {
                    noteList.add(0, notes.get(0));
                    notesAdapters.notifyItemChanged(0);
                }
                noteRecyclerView.smoothScrollToPosition(0);
            }
        }
        new getNotesTask().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_AND_NOTE && resultCode == RESULT_OK){
            getNote();
        }
    }
}