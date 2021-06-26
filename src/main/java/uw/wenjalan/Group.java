package uw.wenjalan;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// Represents a Group of Students
//
// author: Alan Wen
// date: 23 Jun 2021
public class Group {

    // the members of this Group
    private final LinkedList<Student> members;

    // constructor: empty group
    public Group() {
        members = new LinkedList<>();
    }

    // combines this group with another group
    // post: all members of other added to this group
    public void combineWith(Group other) {
        this.members.addAll(other.getMembers());
    }

    // adds a Student to this group
    public void addStudent(Student student) {
        this.members.add(student);
    }

    // removes a Student from this group
    // returns: whether the operation succeeded
    public boolean removeStudent(Student student) {
        return this.members.remove(student);
    }

    // calculates the average timezone of this group
    // the average timezone is defined as the mean of the timezones of all
    // the members in the group
    public double getMeanTimeZone() {
        double timeZoneSum = 0.0;
        for (Student s : members) {
            timeZoneSum += s.getTimezone();
        }
        double timeZoneMean = timeZoneSum / members.size();
        return timeZoneMean;
    }

    // returns a list of roles this group has
    public List<String> getRoles() {
        List<String> roles = new LinkedList<>();
        for (Student s : members) {
            roles.addAll(Arrays.asList(s.getRoles()));
        }
        return roles;
    }

    // getters //
    public List<Student> getMembers() {
        return members;
    }

    // toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Members (").append(members.size()).append(")\n");
        for (Student s : members) {
            sb.append(s.getName()).append("\n");
        }
        sb.append("Mean Time Zone: ").append(getMeanTimeZone()).append("\n");
        sb.append("Group Roles: ").append(getRoles()).append("\n");
        return sb.toString();
    }
}
