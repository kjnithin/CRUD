package com.example.nithin.crud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
//    This is to initialise the values for textview, buttons as well as Edit text
    EditText firstName, lastName,markview, idview;
    Button addBtn, deleteBtn,updateBtn,ViewBtn,detailsBtn;
    TextView tv;
    List<StudentsModel> list = new ArrayList<StudentsModel>();
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//       This is to initialise the particular values for textview, buttons as well as Edit text
//        and to add the on clickListener for the buttons
        db = new DatabaseHandler(getApplicationContext());
        firstName = (EditText) findViewById(R.id.firstNameView);
        lastName = (EditText) findViewById(R.id.lastNameView);
        markview=(EditText) findViewById(R.id.marksView);
        idview = (EditText) findViewById(R.id.deleteView);
        addBtn = (Button) findViewById(R.id.addBtn);
        deleteBtn= (Button) findViewById(R.id.deleteBtn);
        updateBtn= (Button) findViewById(R.id.updateBtn);
        detailsBtn= (Button) findViewById(R.id.searchBtn);;
        ViewBtn=(Button)findViewById(R.id.viewBtn);
        tv = (TextView) findViewById(R.id.viewDetails);
        addBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        ViewBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        detailsBtn.setOnClickListener(this);

//        This is to show the data in the textview on the app loads
        show();

    }
//  This is to show the data in the textview
    public void show(){
        list = db.getAllStudentsList();
        print(list);
    }

//    This is to clear the data from the edit text fild after inserting and updating the data
    public void clear(){
        firstName.setText("");
        lastName.setText("");
        markview.setText("");
        idview.setText("");
    }

//    This method is to iterate through the list and display the values in the text view
    private void print(List<StudentsModel> list) {
        // TODO Auto-generated method stub
        String value = "";
        for(StudentsModel sm : list){
            value = value+"id: "+sm.id+"\n"+"First Name: "+sm.firstName+"\n"+"Last Name: "+sm.lastName+"\n"+"Marks: "+sm.marks+"\n"+"\n";
        }
        tv.setText(value);
    }

//   This is the onClick event where all the CRUD operations will be performed.
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

//        This is to delete the particular records based on the id provided and display the remaning values in the values
        if(v == findViewById(R.id.deleteBtn)){
            tv.setText("");
            String student_id = idview.getText().toString();
            db.deleteStudentDetails(Integer.parseInt(student_id));
            list = db.getAllStudentsList();
            Toast.makeText(getApplicationContext(),"Details Deleted", Toast.LENGTH_LONG).show();
            print(list);
            clear();
        }
//        This is to add the all the records and display it in the textview
        else if(v == findViewById(R.id.addBtn)){
            tv.setText("");
            StudentsModel student = new StudentsModel();
            student.firstName = firstName.getText().toString();
            student.lastName = lastName.getText().toString();
            student.marks=Integer.parseInt(markview.getText().toString());
            db.addStudentDetail(student);
            list = db.getAllStudentsList();
            print(list);
            Toast.makeText(getApplicationContext(),"Details Added", Toast.LENGTH_LONG).show();
            clear();
        }
//        This is to view all the records in the dialog box
        else if(v==findViewById(R.id.viewBtn)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Personal Details");
            String value = "";
            for(StudentsModel sm : list){
                value = value+"id: "+sm.id+"\n"+"First Name: "+sm.firstName+"\n"+"Last Name: "+sm.lastName+"\n"+"Marks: "+sm.marks+"\n"+"\n";
            }
            alertDialogBuilder.setMessage(value);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            clear();

        }
//        This is to update the particular details based on the id provided
        else if(v==findViewById(R.id.updateBtn)){
           db.updateStudentDetails(idview.getText().toString(), firstName.getText().toString(),
                   lastName.getText().toString(), markview.getText().toString());
            Toast.makeText(getApplicationContext(),"Details updated", Toast.LENGTH_LONG).show();
            show();
            clear();

        }
//        This is to display the particular data on the edit text based on the id selected,
//        so that it can used of updating the data
        else if(v==findViewById(R.id.searchBtn)){
            StudentsModel students;
            String student_id = idview.getText().toString();
            students=db.getStudent(Integer.parseInt(student_id));
            String f=students.firstName;
            String l=students.lastName;
            int m=students.marks;
                firstName.setText(f);
                lastName.setText(l);
                markview.setText(m);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
