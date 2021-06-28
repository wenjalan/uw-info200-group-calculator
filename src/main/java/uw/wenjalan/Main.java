package uw.wenjalan;

import java.io.File;
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
            System.err.println("Usage: java -jar uw-info-200-group-calculator.jar <survey responses>.xls <config file>.cfg");
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

        // print them out
        int groupedStudents = 0;
        for (int i = 1; i < groups.size() + 1; i++) {
            Group g = groups.get(i - 1);
            System.out.println("Group #" + i + " ============\n" + g + "\n\n");
            groupedStudents += g.size();
        }
        System.out.println("Done! Created " + groups.size() + " groups out of " + groupedStudents + " students");
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
        // a final list of groups to send back
        List<Group> finalGroups = new LinkedList<>();

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

                // if the requested teammate IS the student themselves, continue
                if (s.equals(teammate)) {
                    continue;
                }

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

        // add each group to a PriorityQueue based on their size
        Set<Group> groups = new HashSet<>(studentToGroups.values());
        // pq is set to arrange from largest (front) to smallest (back) group
        PriorityQueue<Group> pq = new PriorityQueue<>(
                Collections.reverseOrder(
                        Comparator.comparingInt(Group::size)
                )
        );
        pq.addAll(groups);

        // while there are groups remaining in the pq, combine groups
        while (!pq.isEmpty()) {
            // get the next group
            Group g = pq.remove();

            // if this group meets or exceeds the size limit, mark them complete and continue
            if (g.size() >= config.MAX_GROUP_SIZE) {
                finalGroups.add(g);
                continue;
            }

            // find all groups which we can combine with without going over the max size
            List<Group> canCombine = new LinkedList<>();
            for (Group other : pq) {
                if (g.size() + other.size() <= config.MAX_GROUP_SIZE) {
                    canCombine.add(other);
                }
            }

            // find all groups whose difference in timezones is the minimum
            long minDifference = Integer.MAX_VALUE;
            List<Group> leastTimeDifference = new LinkedList<>();
            for (Group other : canCombine) {
                long timeDifference = Math.abs(g.getMeanTimeZone() - other.getMeanTimeZone());
                if (timeDifference == minDifference) {
                    leastTimeDifference.add(other);
                }
                else if (timeDifference < minDifference) {
                    leastTimeDifference.clear();
                    leastTimeDifference.add(other);
                    minDifference = timeDifference;
                }
            }

            // if the min time difference was greater than the allowed threshold, automatically mark this group complete
            if (minDifference > config.MAX_TIME_DIFFERENCE) {
                finalGroups.add(g);
                continue;
            }

            // of the leastTimeDifference groups, find all groups whose role compatibility is the greatest, lower scores are better
            int bestCompatibility = Integer.MAX_VALUE;
            List<Group> mostCompatible = new LinkedList<>();
            for (Group other : leastTimeDifference) {
                int compatibility = g.getRoleCompatibility(other);
                if (compatibility == bestCompatibility) {
                    mostCompatible.add(other);
                }
                else if (compatibility < bestCompatibility) {
                    mostCompatible.clear();
                    mostCompatible.add(other);
                    bestCompatibility = compatibility;
                }
            }

            // from the more compatible groups, choose the first one I guess
            Group other = mostCompatible.remove(0);

            // remove the other group from the pq and combine the two groups
            pq.remove(other);
            g.combineWith(other);

            // add the group back to the pq for further combinations
            pq.add(g);
        }

        // return the finalized groups
        return finalGroups;
    }
}
