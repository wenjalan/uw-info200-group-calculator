package uw.wenjalan;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

// Reads an .xls file (excel 1997-2003) containing the student responses to
// the class survey. Implementation is based on data gathered during the
// INFO 200 A Summer 2021 class.
//
// author: Alan Wen
// date: 25 Jun 2021
public class XlsSurveyReader {

    // returns a list of Students from a given file
    public static List<Student> readFile(File file, File config) throws IOException {
        // if the file does not end with .xls, complain
        if (!file.getName().endsWith(".xls")) {
            System.err.println("File does not end with .xls (Excel 1997-2003).");
            if (file.getName().endsWith(".xlsx")) {
                System.err.println("Please resave the file as an Excel 1997-2003 file.");
            }
        }

        // read the associated .cfg file
        CfgReader.Config cfg = CfgReader.readConfig(config);

        // create a new Workbook from the File
        Workbook workbook = new HSSFWorkbook(new FileInputStream(file));

        // get the first sheet (there should only be one)
        Sheet sheet = workbook.getSheetAt(0);

        // print it?
        System.out.println(sheet);

        // return nothing
        return null;
    }

}
