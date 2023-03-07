package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText title, desc;
    Button cancel ,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_note);

        title=findViewById(R.id.editTextTitle);
        desc=findViewById(R.id.editTextDesc);
        cancel=findViewById(R.id.buttonCancel);
        save=findViewById(R.id.buttonSave);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddNoteActivity.this, "Nothing Saved", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    public void saveNote(){

        String noteTitle=title.getText().toString();
        String noteDesc=desc.getText().toString();

        Intent i=new Intent();

        i.putExtra("noteTitle", noteTitle);
        i.putExtra("noteDesc", noteDesc);
        setResult(RESULT_OK, i);
        finish();

    }
}