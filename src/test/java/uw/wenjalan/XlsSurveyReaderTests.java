package uw.wenjalan;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class XlsSurveyReaderTests {

    @Test
    public void testReadFile() throws IOException {
        String filepath = "data/info200su2021.xls";
        String configFilePath = "data/info200su2021.cfg";
        File f = new File(filepath);
        File cfg = new File(configFilePath);
        XlsSurveyReader.readFile(f, cfg);
    }

}
