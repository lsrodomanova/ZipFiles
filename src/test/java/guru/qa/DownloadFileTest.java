package guru.qa;

import com.codeborne.pdftest.assertj.Assertions;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DownloadFileTest {

    @Test
    void downloadFile() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadFile = Selenide.$("#raw-url").download();
        try(InputStream is = new FileInputStream(downloadFile)) {
            Assertions.assertThat(new String (is.readAllBytes(), StandardCharsets.UTF_8)).contains("This repository is the home of the next generation of JUnit");
        }
    }
}
