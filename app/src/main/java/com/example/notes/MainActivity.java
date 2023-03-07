package com.example.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    ActivityResultLauncher<Intent> activityResultLauncherForAddNote;
    ActivityResultLauncher<Intent> activityResultLauncherForUpdateNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To register activity
        registerActivityForAddNote();
        registerActivityForUpdateNote();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update recycler view
                adapter.setNotes(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));

                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Note note) {

                Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("id", note.getId());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("desc", note.getDescription());
                //activityResultLauncher

                activityResultLauncherForUpdateNote.launch(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.new_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.top_menu:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//                startActivity(intent);
//                startActivityForResult(intent, 1);
//                Activity result launcher
                activityResultLauncherForAddNote.launch(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void registerActivityForUpdateNote(){
        activityResultLauncherForUpdateNote= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        int resultCode=result.getResultCode();
                        Intent data=result.getData();

                        if(resultCode==RESULT_OK && data!= null){
                            String title=data.getStringExtra("titleLast");
                            String desc=data.getStringExtra("descLast");
                            int id=data.getIntExtra("noteId", -1);

                            Note note=new Note(title, desc);
                            note.setId(id);
                            noteViewModel.update(note);


                        }


                    }
                });
    }

    public void registerActivityForAddNote() {
        activityResultLauncherForAddNote = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()

            {
                @Override
                public void onActivityResult (ActivityResult result){
                    int resultCode = result.getResultCode();
                    Intent data = result.getData();

                    if (resultCode == RESULT_OK && data != null) {

                        String title = data.getStringExtra("noteTitle");
                        String desc = data.getStringExtra("noteDesc");

                        Note note = new Note(title, desc);
                        noteViewModel.insert(note);

                    }
                }
            });
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!= null){

            String title=data.getStringExtra("noteTitle");
            String desc=data.getStringExtra("noteDesc");

            Note note=new Note(title, desc);
            noteViewModel.insert(note);

        }
    }
}