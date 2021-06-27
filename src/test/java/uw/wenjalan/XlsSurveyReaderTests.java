package uw.wenjalan;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class XlsSurveyReaderTests {

    @Test
    public void testReadFile() throws IOException {
        String filepath = "data/info200su2021.xls";
        String configFilePath = "data/info200su2021.cfg";
        File f = new File(filepath);
        File cfg = new File(configFilePath);
        List<Student> students = XlsSurveyReader.readFile(f, CfgReader.readConfig(cfg));
        for (Student s : students) {
            System.out.println(s);
        }
    }

    @Test
    public void testExtractEmail() {
        String test1 = "student1@uw.edu";
        String test2 = "somebody once told me the world was gonna roll me";
        String test3 = "somestudent3, student3@uw.edu";
        String test4 = "somestudent4, student4@uw.edu\nsomestudent5, student5@uw.edu";
        System.out.println(Arrays.toString(XlsSurveyReader.extractUwEmails(test1)));
        System.out.println(Arrays.toString(XlsSurveyReader.extractUwEmails(test2)));
        System.out.println(Arrays.toString(XlsSurveyReader.extractUwEmails(test3)));
        System.out.println(Arrays.toString(XlsSurveyReader.extractUwEmails(test4)));
    }

}
