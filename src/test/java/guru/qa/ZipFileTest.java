package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;



public class ZipFileTest {
    ClassLoader cl = getClass().getClassLoader();
    String pdfName = "tarify.pdf";
    String xlsxName = "dates.xlsx";
    String csvName = "dates.csv";

    @Test
    void parseZipPDFTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/ziptest.zip"));
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("ziptest.zip"));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                try (InputStream inputStream = zf.getInputStream(entry)) {
                    if (entry.getName().equals(pdfName)) {
                        PDF pdf = new PDF(inputStream);
                        org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo(pdfName);
                        org.assertj.core.api.Assertions.assertThat(pdf.numberOfPages).isEqualTo(5);
                    }
                    if (entry.getName().equals(xlsxName)) {
                        XLS xls = new XLS(inputStream);
                        String stringCellValue = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                        org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo(xlsxName);
                        org.assertj.core.api.Assertions.assertThat(stringCellValue).contains("DR1");
                    }
                    if (entry.getName().equals(csvName)) {
                        CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        List<String[]> content = reader.readAll();
                        org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo(csvName);
                        org.assertj.core.api.Assertions.assertThat(content).containsAnyOf(
                                new String[] {"New", "Year"}
                        );
                    }
            }
        }
    }
}
