package guru.qa;

import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class ZipFileTesttt {
    ClassLoader cl = getClass().getClassLoader();
    String pdfName = "tarify.pdf";
    String xlsxName = "dates.xlsx";
    String csvName = "dates.csv";

    @Test
    void parseZipCSVTest() throws Exception {
        ZipFile zf = new ZipFile("C:/ZipFiles/src/test/resources/ziptest.zip");
        ZipEntry entry;
        InputStream is = cl.getResourceAsStream("ziptest.zip");
        assert is != null;
        ZipInputStream zis = new ZipInputStream(is);
        while ((entry = zis.getNextEntry()) != null) {
            try (InputStream stream = zf.getInputStream(entry)) {
                CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                List<String[]> content = reader.readAll();
                org.assertj.core.api.Assertions.assertThat(entry.getName().contains(csvName));
            }
        }
    }


        @Test
        public void parseZipPDFTest() throws Exception {
        ZipFile zf = new ZipFile("C:/ZipFiles/src/test/resources/ziptest.zip");
        ZipEntry entry;
        InputStream is = cl.getResourceAsStream("ziptest.zip");
        assert is != null;
        ZipInputStream zis = new ZipInputStream(is);
        while ((entry = zis.getNextEntry()) != null) {
            try (InputStream stream = zf.getInputStream(entry)) {
                PDF pdf = new PDF(stream);
                org.assertj.core.api.Assertions.assertThat(entry.getName().contains(pdfName));
            }
        }
    }
}



