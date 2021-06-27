package uw.wenjalan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// This application is designed to form groups for the INFO 200 class at
// the University of Washington based on an excel spreadsheet generated
// from a survey.
//
// author: Alan Wen
// date: 23 Jun 2021
public class Main {

    // main program entry point
    // args[0]: the location of the .xls file to generate groups from
    public static void main(String[] args) throws IOException {
        // check if they specified args
        if (args.length < 2) {
            // complain
            System.err.println("Please specify both a .xls file and a .cfg file");
            System.exit(1);
        }

	    // get the Files from args
        File file = new File(args[0]);
        File cfgFile = new File(args[1]);

        // load configuration file
        CfgReader.Config config = CfgReader.readConfig(cfgFile);
        System.out.println("Using config file " + args[1]);

        // load student data from files
        List<Student> students = XlsSurveyReader.readFile(file, config);
        System.out.println("Found " + students.size() + " students in " + args[0]);

        // create groups from the files
        List<Group> groups = generateGroups(students, config);

        // print them out for now
        System.out.println(groups);
    }

    // Generates groups of students with a given configuration.
    //
    // The process for group creation is based on an "compatibility" rating, where lower numbers are more compatible.
    // The process will prioritize the factors in the following order:
    //  1. Preferred Teammates
    //  2. Time Zone Difference
    //  3. Preferred Role Diversity
    //
    // While creating groups, the following conditions must be satisfied:
    //  1. There must be at least two groups that can be combined and not exceed the maximum limit defined in config
    //  2. There must be at least two groups that can be combined and not exceed the maximum time difference in config
    //  3. Once there are no more valid combinations that can be made, processing stops
    public static List<Group> generateGroups(List<Student> students, CfgReader.Config config) {
        // a map of UW Net IDs to Student objects
        Map<String, Student> idsToStudents = new HashMap<>();
        students.forEach((s) -> idsToStudents.put(s.getUwEmail().toLowerCase(), s));

        // step 0: assign each student to a group by themselves in a map
        // key: UW Net ID
        // val: Group
        Map<Student, Group> studentToGroups = new HashMap<>();
        for (Student s : students) {
            Group g = new Group();
            g.addStudent(s);
            studentToGroups.put(s, g);
        }

        // step 1: create initial groups based on preferred teammates
        for (Student s : studentToGroups.keySet()) {
            // find each preferred teammate and add them to this group
            for (String teammateId : s.getPreferredTeammates()) {
                Student teammate = idsToStudents.get(teammateId.toLowerCase());
                Group otherGroup = studentToGroups.get(teammate);
                Group thisGroup = studentToGroups.get(s);

                // if they aren't in the same group already...
                if (!thisGroup.equals(otherGroup)) {
                    // ... combine this group with the other group
                    thisGroup.combineWith(otherGroup);

                    // and set the teammate's group to this group
                    studentToGroups.put(teammate, thisGroup);
                }
            }
        }

        // pause
        Set<Group> groups = new HashSet<>(studentToGroups.values());
        for (Group g : groups) {
            System.out.println(g + "\n\n");
        }

        // return null
        return null;
    }
}
