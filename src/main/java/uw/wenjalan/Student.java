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
    private final double timezone;
    private final String[] preferredTeammates;
    private final String[] roles;

    // constructor
    // name: name of the student, formatted as "LASTNAME, FIRSTNAME"
    // timezone: the timezone of this student, in hours ahead of Seattle (PST)
    // roles: the preferred roles of this student
    // gender: the gender of this student
    public Student(String name, String studentEmail, double timezone, String[] preferredTeammates, String[] roles) {
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

    public double getTimezone() {
        return timezone;
    }

    public String[] getPreferredTeammates() {
        return preferredTeammates;
    }

    public String[] getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return name + " (" +
                "uwEmail='" + uwEmail + '\'' +
                ", timezone=" + timezone +
                ", preferredTeammates=" + Arrays.toString(preferredTeammates) +
                ", roles=" + Arrays.toString(roles) +
                "})";
    }
}
