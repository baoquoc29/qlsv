package com.example.qlsv;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final String TABLE_NAME = "Student";
    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_CLASS = "class";
    private static final String COLUMN_NAME = "student_name";
    private static final String COLUMN_MAJOR = "major";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_STUDENT_ID + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_CLASS + " TEXT, " +
            COLUMN_MAJOR + " TEXT);";



    public SqliteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertData(String id, String name, String classst, String major) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_CLASS, classst);
        contentValues.put(COLUMN_MAJOR, major);

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long newRowId = -1;

        try {
            newRowId = sqLiteDatabase.insertOrThrow(TABLE_NAME, null, contentValues);
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.close();
        }

        return newRowId;
    }

    public List<Students> display_view(){
        List<Students> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] list_columns = {
                COLUMN_STUDENT_ID,COLUMN_NAME,COLUMN_CLASS,COLUMN_MAJOR
        };
        Cursor cursor = db.query(TABLE_NAME,list_columns,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String classst = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS));
            String major = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAJOR));

            list.add(new Students(id,name,classst,major));
        }
        cursor.close();
        db.close();
        return list;
    }
    public void deleteColumns(String id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_STUDENT_ID + " = '" + id + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void updateColumn(String id, String name, String classst, String major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_CLASS, classst);
        contentValues.put(COLUMN_MAJOR, major);
        String whereClause = COLUMN_STUDENT_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        int rowsAffected = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
    }
}