package com.example.fragmentapp.models;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;
    private boolean isStudent;

    public User(String name, int age, boolean isStudent) {
        this.name = name;
        this.age = age;
        this.isStudent = isStudent;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isStudent() { return isStudent; }
    public void setStudent(boolean student) { isStudent = student; }
}