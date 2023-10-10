package com.example.testgit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.testgit.R;
import com.example.testgit.database.NotesDatabase;
import com.example.testgit.entities.Note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteTitleSubtitle, inputNote;
    private TextView textDateTime;

    private View viewSubtitleIndicator;
    private String selectedNoteColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteTitleSubtitle = findViewById(R.id.inputNoteTitleSubtitle);
        inputNote = findViewById(R.id.inputNote);

        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);

        textDateTime = findViewById(R.id.textDateTime);

        //set Date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy hh:mm a",Locale.getDefault());
        textDateTime.setText(sdf.format(new Date()));

        ImageView imageSave = findViewById(R.id.imageSave);

        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        selectedNoteColor = "#333333";

        initMiscellaneous();
        setSubtitleIndicatorColor();
    }

    private void saveNote (){
        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note Title can't be empty!", Toast.LENGTH_SHORT).show();
            return ;
        }else if(inputNoteTitleSubtitle.getText().toString().trim().isEmpty() && inputNote.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteTitleSubtitle.getText().toString());
        note.setNote_text(inputNote.getText().toString());
        note.setDateTime(textDateTime.getText().toString());

        class SaveNoteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).notedao().insertnote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new SaveNoteTask().execute();
    }

    private void initMiscellaneous(){
        final LinearLayout layoutMiscellaneous = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscellaneous);
        layoutMiscellaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imageColor1 = layoutMiscellaneous.findViewById(R.id.imgColor1);
        final ImageView imageColor2 = layoutMiscellaneous.findViewById(R.id.imgColor2);
        final ImageView imageColor3 = layoutMiscellaneous.findViewById(R.id.imgColor3);
        final ImageView imageColor4 = layoutMiscellaneous.findViewById(R.id.imgColor4);
        final ImageView imageColor5 = layoutMiscellaneous.findViewById(R.id.imgColor5);
    }

    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }



}