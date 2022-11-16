import Pages.AdminPage.LoginPage;
import Tools.OpenBrowsers;
import Tools.TakeScreenShot;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static Tools.Utils.*;
import static Tools.Utils.timeToWait;
import static org.testng.Assert.assertTrue;

public class AdminBaseTest {

    WebDriver driver;
    TakeScreenShot screenShot;
    double finalPrice;
    static final  String inputFilePath = "CsvFiles/Input/input.csv";
    final String webSite = "https://admin-samirest-grill-alpha.web.app/";
    final String browser = "chrome";
    final String loginCredentials = "Creds/admin.creds";
    final String loginForm= "//*[@id=\"root\"]//*/form";
    final String screenShotsPath = "ScreenShots/loginTestResult.png";
    final String saveScreenPath = "ScreenShots/loginTestResult.png";
    List<String> credentials;
    @DataProvider(name = "Data")
    public static Object[][] getData() throws Exception{
        return parseCsvFile(inputFilePath);
    }
    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG(String path) throws IOException {
        //attach image to allure
        File file = new File(path);
        BufferedImage bufferedImage = ImageIO.read(file);

        byte[] image = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", bos);
            image = bos.toByteArray();
        } catch (Exception e) {System.out.println(e); }
        return image;
    }


    @BeforeSuite
    public void  initTest() throws IOException, InterruptedException {
        finalPrice = 0;
        credentials = readCredentials(loginCredentials);
        driver = OpenBrowsers.openBrowser(browser);
        driver.get(webSite);
        driver.manage().window().maximize();
        screenShot = new TakeScreenShot(driver);
        timeToWait(4);
        waitForPageLoad(driver,loginForm);

    }
    @Severity(SeverityLevel.BLOCKER)
    @Description("login account test")
    @Story("Test login as an admin from credential file")
    @Test()
    public void loginTest() throws InterruptedException, IOException {
        //init login page and enter credentials
        LoginPage loginPage = new LoginPage(driver);
        loginPage.writeEmail(credentials.get(0));
        timeToWait(2);
        loginPage.writePassword(credentials.get(1));
        timeToWait(2);

        //test if login is successful
        boolean clickLoginBtnResult = loginPage.clickLoginBtn();
        assertTrue(clickLoginBtnResult);
        timeToWait(5);
        screenShot.takeScreenShot(screenShotsPath);
        saveScreenshotPNG(saveScreenPath);
    }
}
