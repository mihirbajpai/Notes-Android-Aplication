package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes=new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);


        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currNote=notes.get(position);
        holder.textViewTitle.setText(currNote.getTitle());
        holder.textViewDesc.setText(currNote.getDescription());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position){

        return notes.get(position);

    }

    class NoteHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDesc;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle=itemView.findViewById(R.id.textViewTitle);
            textViewDesc=itemView.findViewById(R.id.textViewDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!= null && position!=RecyclerView.NO_POSITION){

                        listener.onItemClickListener(notes.get(position));

                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;

    }






}
