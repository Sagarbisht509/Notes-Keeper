package com.sagar.noteskeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addNotes;
    SearchView searchView;

    private RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> notesList;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNotes = findViewById(R.id.addNotes_id);
        searchView = findViewById(R.id.searchView_id);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
            }
        });

        notesList = new ArrayList<>();
        adapter = new Adapter(MainActivity.this, notesList);

        recyclerView = findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllNotes();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                notesList.add(new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();

        for (Model m : notesList) {
            if (m.getTitle().toLowerCase().contains(text)) {
                filteredList.add(m);
            }

            if (!filteredList.isEmpty()) {
                adapter.filter(filteredList);
            }
        }

    }

//    private DeleteAllNotes()
//    {
//        DatabaseHelper databaseHelper1 = new DatabaseHelper(this);
//        databaseHelper1.deleteAllNotes();
//        recreate();
//    }

}