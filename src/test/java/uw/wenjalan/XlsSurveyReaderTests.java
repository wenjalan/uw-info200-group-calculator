package uw.wenjalan;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XlsSurveyReaderTests {

    @Test
    public void testReadFile() throws IOException {
        String filepath = "data/info200su2021.xls";
        String configFilePath = "data/info200su2021.cfg";
        File f = new File(filepath);
        File cfg = new File(configFilePath);
        List<Student> students = XlsSurveyReader.readFile(f, cfg);
        for (Student s : students) {
            System.out.println(s);
        }
    }

}
