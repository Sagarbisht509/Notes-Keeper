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

public class UpdateNotesActivity extends AppCompatActivity {

    private EditText title, subTitle, desc;
    private FloatingActionButton update;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        ImageView back = findViewById(R.id.back_id);
        title = findViewById(R.id.updateTitle_id);
        subTitle = findViewById(R.id.updateSubTitle_id);
        desc = findViewById(R.id.updateDesc_id);
        update = findViewById(R.id.update_id);

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
//        actionBar.setTitle("Update Notes");

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        title.setText(intent.getStringExtra("title"));
        subTitle.setText(intent.getStringExtra("subTitle"));
        desc.setText(intent.getStringExtra("desc"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils(title.getText().toString().trim(), subTitle.getText().toString().trim(), desc.getText().toString().trim()))
                {
                    DateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy");
                    Date date = new Date();
                    String d = dateFormat.format(date);

                    DatabaseHelper databaseHelper = new DatabaseHelper(UpdateNotesActivity.this);
                    databaseHelper.updateNotes(id, title.getText().toString().trim(), subTitle.getText().toString().trim(), desc.getText().toString().trim(), d );

                    startActivity(new Intent(UpdateNotesActivity.this, MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(UpdateNotesActivity.this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public Boolean TextUtils(String x, String y, String z)
    {
        return !x.isEmpty() || !y.isEmpty() || !z.isEmpty();
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }
}