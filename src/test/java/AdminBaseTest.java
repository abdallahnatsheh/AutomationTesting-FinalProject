import Pages.AdminPage.BasePage;
import Pages.AdminPage.LoginPage;
import Pages.AdminPage.OrdersDetailsPage;
import Pages.AdminPage.OrdersPage;
import Tools.OpenBrowsers;
import Tools.TakeScreenShot;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static Tools.Utils.*;
import static Tools.Utils.timeToWait;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AdminBaseTest {

    WebDriver driver;
    TakeScreenShot screenShot;
    double finalPrice;
    static final  String inputFilePath = "CsvFiles/Input/input.csv";
    static final  String customerOutputFilePath = "CsvFiles/Output/output.csv";

    final String webSite = "https://admin-samirest-grill-alpha.web.app/";
    final String browser = "chrome";
    final String loginCredentials = "Creds/admin.creds";
    final String loginForm= "//*[@id=\"root\"]//*/form";
    final String AdminLoginTestResultSH = "ScreenShots/AdminLoginTestResult.png";
    final String AdminFirstOrderPageSH = "ScreenShots/AdminFirstOrderPage.png";
    List<String> credentials;
    List<String[]> customerTestResult;

    double customerFinalPrice;
    String customerEmail;

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
    public void  initTest() throws Exception {
        credentials = readCredentials(loginCredentials);
        customerTestResult = readCsvFileResult(customerOutputFilePath);
        customerFinalPrice = Double.parseDouble(customerTestResult.get(0)[1]);
        customerEmail = customerTestResult.get(0)[0];
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
        screenShot.takeScreenShot(AdminLoginTestResultSH);
        saveScreenshotPNG(AdminLoginTestResultSH);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("find latest order and test its final price")
    @Story("find the first order and test if its final price same as the customer bought")
    @Test(dependsOnMethods = "loginTest")
    public void openOrdersPage() throws InterruptedException, IOException {
        BasePage basePage = new BasePage(driver);
        basePage.ordersPageClick();
        timeToWait(2);
        OrdersPage ordersPage = new OrdersPage(driver);
        ordersPage.orderTableByDate();
        finalPrice = Double.parseDouble(ordersPage.getFirstRowFinalPrice());
        assertEquals(customerFinalPrice,finalPrice);
        ordersPage.clickOnFirstResult();
        timeToWait(2);
        screenShot.takeScreenShot(AdminFirstOrderPageSH);
        saveScreenshotPNG(AdminFirstOrderPageSH);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("check if the customer's order exist in order list")
    @Story("admin should get the customer's order and here we check if its exist")
    @Test(dependsOnMethods = "openOrdersPage",dataProvider = "Data")
    public void testSentOrder(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) {
        OrdersDetailsPage ordersDetailsPage = new OrdersDetailsPage(driver);
        String cEmail = ordersDetailsPage.getCustomerEmail();
        assertEquals(cEmail,customerEmail);
        ordersDetailsPage.parseOrderList();
        boolean checkResult = ordersDetailsPage.isOrderExist(mealName,Integer.parseInt(finalQuantity),type,addons);
        assertTrue(checkResult);
    }
    @AfterSuite
    public void afterSuite(){
        driver.close();
    }

}
