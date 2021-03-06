package uw.wenjalan;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Reads an .xls file (excel 1997-2003) containing the student responses to
// the class survey. Implementation is based on data gathered during the
// INFO 200 A Summer 2021 class.
//
// author: Alan Wen
// date: 25 Jun 2021
public class XlsSurveyReader {

    // returns a list of Students from a given file
    public static List<Student> readFile(File file, CfgReader.Config cfg) throws IOException {
        // if the file does not end with .xls, complain
        if (!file.getName().endsWith(".xls")) {
            System.err.println("File does not end with .xls (Excel 1997-2003).");
            if (file.getName().endsWith(".xlsx")) {
                System.err.println("Please resave the file as an Excel 1997-2003 file.");
            }
        }

        // create a new Workbook from the File
        Workbook workbook = new HSSFWorkbook(new FileInputStream(file));

        // get the first sheet (there should only be one)
        Sheet sheet = workbook.getSheetAt(0);

        // for each student in the sheet, create a new Student object
        // rows and cols are offset by 1 since excel is 1-based but in here it's 0-based
        List<Student> students = new ArrayList<>();
        for (int i = cfg.DATA_START_ROW - 1; i <= sheet.getLastRowNum(); i++) {
            // retrieve this row
            Row row = sheet.getRow(i);

            // get the student's name
            String studentName = row.getCell(cfg.LAST_NAME_COL - 1).toString() + ", " +
                    row.getCell(cfg.FIRST_NAME_COL - 1).toString();

            // get the student's email
            String studentEmail = row.getCell(cfg.UW_EMAIL_COL - 1).toString();

            // get their timezone offset
            // if ENABLE_TIME_ZONE is 0, set everyone's timezone to 0.0 (Seattle PST)
            double timeZoneUTFOffset;
            if (cfg.ENABLE_TIME_ZONE == 1) {
                timeZoneUTFOffset = Double.parseDouble(row.getCell(cfg.TIME_ZONE_COL - 1).toString());
            }
            else {
                timeZoneUTFOffset = 0.0;
            }

            // get their preferred role
            String[] preferredRoles = {};
            if (row.getCell(cfg.PREFERRED_ROLES_COL - 1) != null) {
                preferredRoles = row.getCell(cfg.PREFERRED_ROLES_COL - 1).toString().split(", ");
            }

            // get their preferred teammates, if they have them
            String[] preferredTeammates;
            if (row.getCell(cfg.HAS_PREFERRED_TEAMMATES_COL - 1).toString().equalsIgnoreCase("Yes") &&
                row.getCell(cfg.PREFERRED_TEAMMATES_COL - 1) != null) {
                // grab all valid UW NetIDs from this string here
                preferredTeammates = extractUwEmails(row.getCell(cfg.PREFERRED_TEAMMATES_COL - 1).toString());
            } else {
                preferredTeammates = new String[0];
            }

            // create a Student
            Student student = new Student(
                    studentName,
                    studentEmail,
                    timeZoneUTFOffset,
                    preferredTeammates,
                    preferredRoles);

            // add it to our list
            students.add(student);
        }

        // return students
        return students;
    }

    // extracts an array of UW emails from a given String
    // returns: an array of UW emails, delimited by either a space " " or a comma "," or a newline "\n"
    public static String[] extractUwEmails(String str) {
        List<String> emails = new ArrayList<>();

        // remove all commas and replace with spaces
        str = str.replaceAll("[,???]", " ");

        String[] tokens = str.split("\\s+");
        for (String token : tokens) {
            if (token.endsWith("@uw.edu")) {
                emails.add(token);
            }
        }
        return emails.toArray(new String[]{});
    }

}
