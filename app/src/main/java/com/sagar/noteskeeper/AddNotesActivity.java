package com.sagar.noteskeeper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNotesActivity extends AppCompatActivity {

    private EditText title, subTitle, desc;
    private FloatingActionButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        ImageView back = findViewById(R.id.back_id);
        title = findViewById(R.id.title_id);
        subTitle = findViewById(R.id.subTitle_id);
        desc = findViewById(R.id.desc_id);
        save = findViewById(R.id.save_id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        actionBar.setTitle("Add Notes");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils(title.getText().toString().trim(), subTitle.getText().toString().trim(), desc.getText().toString().trim()))
                {
                    DateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy");
                    Date date = new Date();
                    String d = dateFormat.format(date);

                    DatabaseHelper databaseHelper = new DatabaseHelper(AddNotesActivity.this);
                    databaseHelper.addNotes( title.getText().toString().trim(), subTitle.getText().toString().trim(), desc.getText().toString().trim(), d );

                    startActivity(new Intent(AddNotesActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(AddNotesActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public Boolean TextUtils(String x, String y, String z)
    {
        if(x.isEmpty() && y.isEmpty() && z.isEmpty()) return false;
        else return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }
}