package com.jarkendar.stosik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class DatabaseLackey extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final int VERSION = 1;

    private static final String TABLE_TASKS = "TASKS";

    private static final String FIELD_ROW_ID = "_id";
    private static final String FIELD_TITLE = "TITLE";
    private static final String FIELD_PRIORITY = "PRIORITY";
    private static final String FIELD_END_DATE = "END_DATE";
    private static final String FIELD_ENDED = "ENDED";
    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public DatabaseLackey(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        updateDatabase(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateDatabase(sqLiteDatabase, 0, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateDatabase(sqLiteDatabase, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        if (newVersion > 0){
            //todo create tables
            String createTableTask = "CREATE TABLE " + TABLE_TASKS + "(" +
                    FIELD_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_TITLE + " TEXT NOT NULL, " +
                    FIELD_PRIORITY + " INTEGER NOT NULL DEFAULT 0, " +
                    FIELD_END_DATE + " LONG NOT NULL, " +
                    FIELD_ENDED + " INTEGER DEFAULT " + FALSE + " " +
                    ");";
            sqLiteDatabase.execSQL(createTableTask);
        }
    }

    public void insertTask(SQLiteDatabase sqLiteDatabase, Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, task.getTitle());
        contentValues.put(FIELD_PRIORITY, task.getPriority());
        contentValues.put(FIELD_END_DATE, task.getEndDate().getTime());
        sqLiteDatabase.insert(TABLE_TASKS, null, contentValues);
    }

    public void updateEndTask(SQLiteDatabase sqLiteDatabase, Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_ENDED, TRUE);
        sqLiteDatabase.update(TABLE_TASKS, contentValues, FIELD_TITLE+"=? AND "+FIELD_PRIORITY+"=? AND "+FIELD_END_DATE + "=?", new String[] {task.getTitle(), task.getPriority()+"",task.getEndDate().getTime() + ""});
    }

    public LinkedList<Task> selectAllNotEndedTasks(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query(TABLE_TASKS, new String[] {FIELD_TITLE, FIELD_PRIORITY, FIELD_END_DATE, FIELD_ENDED}, FIELD_ENDED+"="+FALSE, null, null, null, FIELD_END_DATE + " DESC, "+FIELD_PRIORITY+" ASC");
        LinkedList<Task> tasks = new LinkedList<>();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(FIELD_TITLE));
            int priority = cursor.getInt(cursor.getColumnIndex(FIELD_PRIORITY));
            Date endDate = new Date(cursor.getLong(cursor.getColumnIndex(FIELD_END_DATE)));
            tasks.addFirst(new Task(title, priority, endDate));
        }
        cursor.close();
        return tasks;
    }
}
