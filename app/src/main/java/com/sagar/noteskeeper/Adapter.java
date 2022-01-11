package com.sagar.noteskeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model> notesList;

    public Adapter(Context context, ArrayList<Model> notes) {
        this.context = context;
        this.notesList = notes;
    }

    public void filter(ArrayList<Model> searchedNotes) {
        notesList = searchedNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Model model = notesList.get(position);

        holder.title.setText(model.getTitle());
        holder.subTitle.setText(model.getSubTitle());
        holder.date.setText(model.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UpdateNotesActivity.class);

                intent.putExtra("id", model.getId());
                intent.putExtra("title", model.getTitle());
                intent.putExtra("subTitle", model.getSubTitle());
                intent.putExtra("desc", model.getDescription());

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure you want to delete this note");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper databaseHelper = new DatabaseHelper(context);
                            if (databaseHelper.deleteNote(model.getId()) > 0) {
                                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                notesList.remove(model);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                return true;
            }
        });

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Delete");
//                builder.setMessage("Are you sure you want to delete this note");
//
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
//                        if (databaseHelper.deleteNote(model.getId()) > 0) {
//                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                            notesList.remove(model);
//                            notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, subTitle, date;
       // ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_id);
           // delete = itemView.findViewById(R.id.delete_id);
            subTitle = itemView.findViewById(R.id.subTitle_id);
            date = itemView.findViewById(R.id.date_id);
        }
    }

}
