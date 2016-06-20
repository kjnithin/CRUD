package com.example.nithin.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NITHIN on 2016-06-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

//    Current version of the database
    private static final int DATABASE_VERSION = 1;

//    This is the name of the database
    protected static final String DATABASE_NAME = "Studentdb";

//    This is to initialise the values
    private static final String KEY_ID = "id";
    private static final String KEY_firstname = "firstName";
    private static final String KEY_lastname = "lastName";
    private static final String KEY_marks = "marks";
    private static final String  TABLE_student="students";

    public static String TAG = "tag";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String sql= "CREATE TABLE " +TABLE_student+
            "( " + KEY_ID + " INTEGER PRIMARY KEY," + KEY_firstname + " TEXT,"
            + KEY_lastname + " TEXT," +KEY_marks+" INTEGER);";


    //    This is to create the students table with id, first name, last name and marks as the fields
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

//    This method is called when any modifications in database are done like
//    version is updated or database schema is changed

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS records";
        db.execSQL(sql);

        onCreate(db);
    }

//    This method is to insert the data into the database
    public long addStudentDetail(StudentsModel student) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_firstname, student.firstName);
        values.put(KEY_lastname, student.lastName);
        values.put(KEY_marks,student.marks);

        // insert row in students table

        long insert = db.insert(TABLE_student, null, values);

        return insert;
    }

//    This method will update the data and insert it into the database
    public boolean updateStudentDetails(String id,String firstName,String lastName,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_ID,id);
        values.put(KEY_firstname, firstName);
        values.put(KEY_lastname, lastName);
        values.put(KEY_marks, marks);

        // update row in students table base on students id value
         db.update(TABLE_student, values, KEY_ID + " = ?",
                new String[] {id} );
        return true;
    }

//    This method is to delete the details of the particular student based on the selection
    public void deleteStudentDetails(long id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_student, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

//    This is to get the details of particular student
    public StudentsModel getStudent(long id) {
    SQLiteDatabase db = this.getReadableDatabase();
    String selectQuery = "SELECT  * FROM " + TABLE_student + " WHERE "
            + KEY_ID + " = " + id;
    Log.d(TAG, selectQuery);

    Cursor c = db.rawQuery(selectQuery, null);

    if (c != null)
        c.moveToFirst();

    StudentsModel students = new StudentsModel();
    students.id = c.getInt(c.getColumnIndex(KEY_ID));
    students.firstName = c.getString(c.getColumnIndex(KEY_firstname));
    students.lastName = c.getString(c.getColumnIndex(KEY_lastname));
    students.marks = c.getInt(c.getColumnIndex(KEY_marks));
        return students;
    }

    public List<StudentsModel> getAllStudentsList() {
        List<StudentsModel> studentsArrayList = new ArrayList<StudentsModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_student;
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                StudentsModel students = new StudentsModel();
                students.id = c.getInt(c.getColumnIndex(KEY_ID));
                students.firstName = c.getString(c.getColumnIndex(KEY_firstname));
                students.lastName = c.getString(c.getColumnIndex(KEY_lastname));
                students.marks=c.getInt(c.getColumnIndex(KEY_marks));

                // adding to Students list
                studentsArrayList.add(students);
            } while (c.moveToNext());
        }

        return studentsArrayList;
    }
}





