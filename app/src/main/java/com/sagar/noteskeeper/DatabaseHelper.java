package com.sagar.noteskeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DatabaseName = "MyDB";

    public static final String tableName = "Notes";
    public static final String c_id = "id";
    public static final String c_title = "title";
    public static final String c_subTitle = "subTitle";
    public static final String c_desc = "description";
    public static final String c_date = "date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseName, null, 1);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE " + tableName + " (" +
                c_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                c_title + " TEXT, " +
                c_subTitle + " TEXT, " +
                c_desc + " TEXT, " +
                c_date + " TEXT);";

        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String q = "DROP TABLE IF EXISTS " + tableName;

        db.execSQL(q);

        onCreate(db);
    }

    public Cursor getAllNotes() {
        String q = "SELECT * FROM Notes";
        SQLiteDatabase database =  this.getReadableDatabase();

        Cursor cursor = null;
        if(database != null)
        {
            cursor = database.rawQuery(q, null);
        }

        return cursor;

    }

    public void addNotes(String title, String subTitle, String desc, String date) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(c_title, title);
        contentValues.put(c_subTitle, subTitle);
        contentValues.put(c_desc, desc);
        contentValues.put(c_date, date);

        long result = database.insert(tableName, null, contentValues);

        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteAllNotes() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String q = "DELETE FROM " + tableName;
        sqLiteDatabase.execSQL(q);
    }

    public int deleteNote(String id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        return  database.delete(tableName,"id=?", new String[]{id});
    }

    public void updateNotes(String id, String title, String subTitle, String desc, String d) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(c_title, title);
        contentValues.put(c_subTitle, subTitle);
        contentValues.put(c_desc, desc);
        contentValues.put(c_date, d);

        long result = database.update(tableName, contentValues, "id=?", new String[]{id} );

        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}
