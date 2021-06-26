package uw.wenjalan;

import java.util.Arrays;

// Represents a Student in a Group
//
// author: Alan Wen
// date: 23 Jun 2021
public class Student {

    // fields
    private final String name;
    private final String uwEmail;
    private final int timezone;
    private final String[] preferredTeammates;
    private final String[] roles;

    // constructor
    // name: name of the student, formatted as "LASTNAME, FIRSTNAME"
    // timezone: the timezone of this student, as an integer offset from UTC
    // roles: the preferred roles of this student
    // gender: the gender of this student
    public Student(String name, String studentEmail, int timezone, String[] preferredTeammates, String[] roles) {
        this.name = name;
        this.uwEmail = studentEmail;
        this.timezone = timezone;
        this.preferredTeammates = preferredTeammates;
        this.roles = roles;
    }

    // getters //
    public String getName() {
        return name;
    }

    public String getUwEmail() {
        return uwEmail;
    }

    public int getTimezone() {
        return timezone;
    }

    public String[] getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", uwEmail='" + uwEmail + '\'' +
                ", timezone=" + timezone +
                ", preferredTeammates=" + Arrays.toString(preferredTeammates) +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
