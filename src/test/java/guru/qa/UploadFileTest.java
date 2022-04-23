package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;

public class UploadFileTest {
    @Test
    void uploadTest() throws Exception {
        Selenide.open("https://the-internet.herokuapp.com/upload");
        Selenide.$("input[type='file']").uploadFromClasspath("ziptest.zip");
        Selenide.$("#file-submit").click();
        Selenide.$("div.example").shouldHave(Condition.text("File Uploaded!"));
        Selenide.$("#uploaded-files").shouldHave(Condition.text("ziptest.zip"));
    }
}
