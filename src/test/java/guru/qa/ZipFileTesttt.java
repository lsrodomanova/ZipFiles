package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.File;
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
    public void parseZipPDFTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/ziptest.zip"));
        try (ZipInputStream is = new ZipInputStream(cl.getResourceAsStream("ziptest.zip"))) {
            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                if ((entry.getName()).contains(pdfName)) {
                    try (InputStream stream = zf.getInputStream(entry)) {
                        PDF pdf = new PDF(stream);
                        Assertions.assertThat(pdf.numberOfPages).isEqualTo(5);
                    }
                }
            }
        }
    }

    @Test
    void parseZipXlsxTest() throws Exception {
        ZipFile zf = new ZipFile("C:/ZipFiles/src/test/resources/ziptest.zip");
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("ziptest.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ((entry.getName()).contains(xlsxName)) {
                    try (InputStream stream = zf.getInputStream(entry)) {
                        XLS xls = new XLS(stream);
                        String stringCellValue = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                        Assertions.assertThat(entry.getName()).isEqualTo(xlsxName);
                        Assertions.assertThat(stringCellValue).contains("DR1");
                    }
                }
            }
        }
    }

    @Test
    void parseZipCSVTest() throws Exception {
        ZipFile zf = new ZipFile("C:/ZipFiles/src/test/resources/ziptest.zip");
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("ziptest.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ((entry.getName()).contains(csvName)) {
                    try (InputStream stream = zf.getInputStream(entry)) {
                        CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                        List<String[]> content = reader.readAll();
                        Assertions.assertThat(entry.getName()).isEqualTo(csvName);
                        Assertions.assertThat(content).containsAnyOf(
                                new String[]{"New", "Year"}
                        );
                    }
                }
            }
        }
    }
}
