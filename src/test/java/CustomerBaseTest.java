import Pages.CustomerPage.*;
import Tools.OpenBrowsers;
import Tools.TakeScreenShot;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
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
import static org.testng.Assert.*;

public class CustomerBaseTest {
    WebDriver driver;
    BasePage basePage;
    TakeScreenShot screenShot;
    double finalPrice;
    static final  String inputFilePath = "CsvFiles/Input/input.csv";
    static final  String outputFilePath = "CsvFiles/Output/output.csv";
    final String webSite = "https://samirest-grill-alpha.web.app/";
    final String browser = "chrome";
    final String loginCredentials = "Creds/customer.creds";
    final String headerXpath = "//*[@id=\"responsive-navbar-nav\"] ";
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
    @Attachment(value = "TestResultReport", type = "text/csv", fileExtension = ".csv")
    public static String attachCSV(final String csv) {
        return csv;
    }


    //get the initial requirements then start the browser
    @BeforeSuite
    public void  initTest() throws IOException {
        finalPrice = 0;
        credentials = readCredentials(loginCredentials);
        driver = OpenBrowsers.openBrowser(browser);
        driver.get(webSite);
        driver.manage().window().maximize();
        screenShot = new TakeScreenShot(driver);
        waitForPageLoad(driver,headerXpath);

    }
    @Severity(SeverityLevel.BLOCKER)
    @Description("login account test")
    @Story("Test login as customer from credential file")
    @Test()
    public void loginTest() throws InterruptedException, IOException {
        //init main base page
        basePage = new BasePage(driver);
        //go to login page
        basePage.loginPageBtnClick();
        timeToWait(2);

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
        screenShot.takeScreenShot("ScreenShots/loginTestResult.png");
        saveScreenshotPNG("ScreenShots/loginTestResult.png");
    }
    @Severity(SeverityLevel.CRITICAL)
    @Description("add a meal to the cart")
    @Story("do several tests to check that every functionality of ordering meals is work as intended")
    @Test(dependsOnMethods = "loginTest",dataProvider = "Data")
    public void testAddMealsToCart(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException, IOException {
        basePage.mainMenuPageBtnClick();
        MainMenuPage mainMenuPage = new MainMenuPage(driver);

        //get index of the meal in main menu
        int result = mainMenuPage.getMenuItemIndex(mealName);
        assertNotEquals(-1,result);

        //click on the meal and check if it's the chosen meal
        mainMenuPage.clickOnMenuItem(result);
        MenuItemModalPage menuItemModalPage =new MenuItemModalPage(driver);
        String name = menuItemModalPage.getMealName();
        assertEquals(mealName,name);

        //choose meal type and test if its exist
        boolean clickOnChosenMealTypeResult = menuItemModalPage.clickOnChosenMealType(type);
        assertEquals(Boolean.parseBoolean(typeResult),clickOnChosenMealTypeResult);

        //choose all addons and test if they are existed
        boolean clickOnChosenAddonsResult= menuItemModalPage.clickOnChosenAddons(addons);
        assertEquals(Boolean.parseBoolean(addonsResult),clickOnChosenAddonsResult);

        //check that the number of the meal before being added to cart is 0 means you didn't add meal to preview
        int getNumMealsBeforeAddResult = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(0,getNumMealsBeforeAddResult);

        //add meals according to requested quantity
        //test removing meals (not from cart just from the preview)
        menuItemModalPage.addMeals(quantity);
        boolean remove_meals = menuItemModalPage.removeMeals(qRemoveBeforeAddToCart);
        assertEquals(Boolean.parseBoolean(firstRemoveResult),remove_meals);

        //test that the number of meals in preview is the requested one before add to cart
        int getNumMealsBeforeAddResult2 = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(Integer.parseInt(qBeforeRemoveFromCart),getNumMealsBeforeAddResult2);

        //add the order to cart
        boolean addMealsToCartResult = menuItemModalPage.addMealsToCart();
        assertEquals(Boolean.parseBoolean(addedMealToCartResult),addMealsToCartResult);

        //check the number of meals in cart is the same as requested in the preview
        int GetNumOfMealInCartResult = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(qBeforeRemoveFromCart),GetNumOfMealInCartResult);

        //test remove one of the chosen meal from cart
        boolean removeMealsFromCartResult = menuItemModalPage.removeMealsFromCart();
        assertEquals(Boolean.parseBoolean(removeMealFromCartResult),removeMealsFromCartResult);

        //check if the removal works and it removed one meal
        GetNumOfMealInCartResult = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(finalQuantity),GetNumOfMealInCartResult);

        screenShot.takeScreenShot("ScreenShots/AddMealToCartResult.png");
        saveScreenshotPNG("ScreenShots/AddMealToCartResult.png");

        //close the pop-up modal of the meal
        menuItemModalPage.exitMealModal();
    }
    @Severity(SeverityLevel.NORMAL)
    @Description("test meals in cart")
    @Story("check that all the added meals are in cart and shown properly")
    @Test(dependsOnMethods = "testAddMealsToCart",dataProvider = "Data")
    public void testMealsInCart(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException, IOException {
        basePage.cartPageBtnClick();
        CartPage cartPage = new CartPage(driver);

        //check if meal exist in cart from the cart page (not the preview like the previous test)
        //and click on it to open its pop-up modal
        int getMenuItemIndexResult = cartPage.getMenuItemIndex(mealName);
        cartPage.clickOnMenuItem(getMenuItemIndexResult);

        //test if the clicked on meal is the requested one
        CartItemModalPage cartItemModalPage = new CartItemModalPage(driver);
        String getMealNameResult = cartItemModalPage.getMealName();
        assertEquals(mealName,getMealNameResult);

        //get number of meals of the requested one is in the cart
        int GetNumOfMealInCartResult = cartItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(finalQuantity),GetNumOfMealInCartResult);

        screenShot.takeScreenShot("ScreenShots/MealsInCartResult.png");
        saveScreenshotPNG("ScreenShots/MealsInCartResult.png");
        //close the pop-up modal of the meal
        cartItemModalPage.exitMealModal();

    }

    @Severity(SeverityLevel.MINOR)
    @Description("test meals existence and price in checkout ")
    @Story("every meal added in cart should be existed in the checkout dialog")
    @Test(dependsOnMethods = "testMealsInCart",dataProvider = "Data")
    public void testMealsInCheckOut(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException, IOException {
        //init the checkout dialog page
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);

        //test if the requested meal in the checkout dialog
        CheckOutOrderItem checkthisitem = checkOutPage.getCheckOutOrderItem(mealName);
        boolean checkOrderExistResult = checkthisitem.checkOrderExist(mealName,finalQuantity,type,addons);
        assertTrue(checkOrderExistResult);

        //add its price to be checked later for the final price test
        finalPrice += checkthisitem.getPrice();

        screenShot.takeScreenShot("ScreenShots/MealsInCheckOutResult.png");
        saveScreenshotPNG("ScreenShots/MealsInCheckOutResult.png");
        //close the pop-up checkout dialog
        checkOutPage.closeCheckOutDialog();
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("final price test")
    @Story("final step in the test is to checkout testing reservation orders with simple note")
    @Test(dependsOnMethods = "testMealsInCheckOut")
    public void testFinalPrice() throws InterruptedException, IOException {
        //init the checkout dialog page
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);

        //test if the final price of all the requested meals is equal to what written in the checkout page
        assertEquals(finalPrice,checkOutPage.getFinalPriceE());

        //make checkout
        boolean chooseOrderTypeResult = checkOutPage.chooseOrderType("حجز");
        assertTrue(chooseOrderTypeResult);
        checkOutPage.writeNotes("تجربة");
        timeToWait(2);

        screenShot.takeScreenShot("ScreenShots/FinalPriceResult.png");
        saveScreenshotPNG("ScreenShots/FinalPriceResult.png");

        boolean clickPayCashResult = checkOutPage.clickPayCash();
        assertTrue(clickPayCashResult);
    }

    @Test(dependsOnMethods = "testFinalPrice")
    @Description("saving final result in csv and attach it to allure report")
    public void writeFinalResultToFile() throws InterruptedException {
        boolean writeResult = writeCsvFileResult((int) finalPrice,credentials.get(0),outputFilePath);
        assertTrue(writeResult);
        timeToWait(1);
        attachCSV(outputFilePath);
    }
    @AfterSuite
    public void afterSuite(){
        driver.close();
    }
}

