package com.example.nithin.crud;

/**
 * Created by NITHIN on 2016-06-16.
 */
public class StudentsModel {
    public int id;
    public String firstName;
    public String lastName;
    public int marks;

    public StudentsModel(int id, String firstName, String lastName, int marks) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.marks=marks;
    }
    public StudentsModel(){

    }
}
