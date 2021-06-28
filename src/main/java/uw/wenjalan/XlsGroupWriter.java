package uw.wenjalan;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// Writes an .xls file containing group information.
//
// author: Alan Wen
// date: 28 Jun 2021
public class XlsGroupWriter {

    // writes a list of Groups to a file
    public static void writeGroups(List<Group> groups, File to) throws IOException {
        // create a new workbook and sheet within it to store the data
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Groups");

        // the headers for the information
        String[] headers = {"Team Number", "Members", "Email", "Time Zone", "Requested Teammates"};

        // write the headers to the sheet
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i);
            headerRow.getCell(i).setCellValue(headers[i]);
        }

        // write the group information to the sheet
        int curRow = 1;
        for (int i = 0; i < groups.size(); i++) {
            Group g = groups.get(i);
            for (Student s : g.getMembers()) {
                // create a row
                Row r = sheet.createRow(curRow);

                // get some information to put in that row
                String[] info = {String.valueOf(i + 1), s.getName(), s.getUwEmail(), String.valueOf(s.getTimezone()), Arrays.toString(s.getPreferredTeammates())};

                // put it in the row
                for (int j = 0; j < info.length; j++) {
                    r.createCell(j);
                    r.getCell(j).setCellValue(info[j]);
                }

                // increment the row and continue
                curRow++;
            }
            // add an empty row between groups
            curRow++;
        }

        // write the sheet to the file
        FileOutputStream fos = new FileOutputStream(to);
        workbook.write(fos);
        workbook.close();
    }

}
