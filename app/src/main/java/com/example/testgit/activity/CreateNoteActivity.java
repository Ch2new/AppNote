package com.example.testgit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.testgit.R;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteTitleSubtitle, inputNote;
    private TextView textDateTime;

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

        textDateTime = findViewById(R.id.textDateTime);

        //set Date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy Hh:Mm a",Locale.getDefault());
        textDateTime.setText(sdf.format(new Date()));


    }

    private void saveNote (){
        if(inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note Title can't be empty!", Toast.LENGTH_SHORT).show();
            return ;
        }else if(inputNoteTitleSubtitle.getText().toString().trim().isEmpty() && inputNote.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note can't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

    }



}