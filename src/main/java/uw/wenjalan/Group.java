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

    // returns whether a given Student is in this group
    // uwId: their UW NetID, in @uw.edu format
    public boolean containsStudent(String uwId) {
        for (Student s : members) {
            if (s.getUwEmail().equalsIgnoreCase(uwId)) {
                return true;
            }
        }
        return false;
    }

    // calculates the average timezone of this group, rounded to the nearest integer
    // the average timezone is defined as the mean of the timezones of all
    // the members in the group
    public long getMeanTimeZone() {
        double timeZoneSum = 0.0;
        for (Student s : members) {
            timeZoneSum += s.getTimezone();
        }
        double timeZoneMean = timeZoneSum / members.size();
        return Math.round(timeZoneMean);
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
        sb.append("Members (").append(members.size()).append("):\n");
        for (Student s : members) {
            sb.append("\t").append(s.getName()).append(" (").append(s.getUwEmail()).append("), TZO: ").append(s.getTimezone()).append("\n");
        }
        sb.append("Mean Time Zone: ").append(getMeanTimeZone()).append("\n");
        sb.append("Group Roles: ").append(getRoles());
        return sb.toString();
    }

    // returns the number of members in this group
    public int size() {
        return members.size();
    }

    // returns the number of role collisions (members with the same roles) with another group
    public int getRoleCompatibility(Group other) {
        return 0;
    }
}
