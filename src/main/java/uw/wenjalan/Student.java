package uw.wenjalan;

// Represents a Student in a Group
//
// author: Alan Wen
// date: 23 Jun 2021
public class Student {

    // fields
    private final String name;
    private final int timezone;
    private final String role;
    private final String gender;

    // constructor
    // name: name of the student, formatted as "LASTNAME, FIRSTNAME"
    // timezone: the timezone of this student, as an integer offset from UTC
    // role: the role of this student
    // gender: the gender of this student
    public Student(String name, int timezone, String role, String gender) {
        this.name = name;
        this.timezone = timezone;
        this.role = role;
        this.gender = gender;
    }

    // getters //
    public String getName() {
        return name;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }
}
