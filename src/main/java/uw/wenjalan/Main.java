package uw.wenjalan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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

        // load student data from files
        List<Student> students = XlsSurveyReader.readFile(file, config);

        // create groups from the files
        List<Group> groups = generateGroups(students, config);

        // print them out for now
        System.out.println(groups);
    }

    // generates groups of students with a given configuration
    public static List<Group> generateGroups(List<Student> students, CfgReader.Config config) {
        System.out.println(students);
        System.out.println(config);
        return Collections.EMPTY_LIST;
    }
}
