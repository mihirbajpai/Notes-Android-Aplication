package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title, desc;
    Button cancel ,save;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Note");
        setContentView(R.layout.activity_update);

        title=findViewById(R.id.editTextTitleUpdate);
        desc=findViewById(R.id.editTextDescUpdate);
        cancel=findViewById(R.id.buttonCancelUpdate);
        save=findViewById(R.id.buttonSaveUpdate);

        getData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UpdateActivity.this, "Nothing Saved", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateNote();
            }
        });
    }

    private void updateNote(){

        String titleLast=title.getText().toString();
        String descLast=desc.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("titleLast", titleLast);
        intent.putExtra("descLast", descLast);
        if(noteId!=-1){
            intent.putExtra("noteId", noteId);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    public void getData(){
        Intent i=getIntent();
        noteId=i.getIntExtra("id", -1);
        String noteTitle=i.getStringExtra("title");
        String noteDesc=i.getStringExtra("desc");

        title.setText(noteTitle);
        desc.setText(noteDesc);
    }
}