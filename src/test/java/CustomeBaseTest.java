import Pages.CustomerPage.*;
import Tools.OpenBrowsers;
import Tools.ReadCsvFile;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static Tools.Utils.timeToWait;
import static Tools.Utils.waitForPageLoad;
import static org.testng.Assert.*;

public class CustomeBaseTest {
    WebDriver driver;
    HeaderPage headerPage;
    double finalprice;
    @DataProvider(name = "Data")
    public static Object[][] getData() throws Exception{
        List<String[]> lines = ReadCsvFile.readAllLines("CsvFiles/Input/input.csv");
        lines.remove(0);
        Object[][] data = new Object[lines.size()][lines.get(0).length];
        int index = 0;
        for(String[] line : lines) {
            data[index] = line;
            index++;
        }
        return data;
    }
    @BeforeSuite
    public void  makeBrowser(){
        finalprice = 0;
        driver = OpenBrowsers.openBrowser("chrome");
        driver.get("https://samirest-grill-alpha.web.app/");
        driver.manage().window().maximize();
        waitForPageLoad(driver);

    }
    @Test(testName = "login-test")
    public void loginTest() throws InterruptedException {
        headerPage = new HeaderPage(driver);
        headerPage.loginPageBtnClick();
        Thread.sleep(2000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.writeEmail("abdnatsheh33@gmail.com");
        Thread.sleep(2000);
        loginPage.writePassword("Password321");
        Thread.sleep(2000);
        boolean resultlogin = loginPage.clickLoginBtn();
        assertTrue(resultlogin);
    }
    @Test(testName = "add a meal to the cart" , dependsOnMethods = "loginTest",dataProvider = "Data")
    public void testAddMealsToCart(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException {
        timeToWait(5);
        headerPage.mainMenuPageBtnClick();
        MainMenuPage mainMenuPage = new MainMenuPage(driver);
        int result = mainMenuPage.getMenuItemIndex(mealName);
        assertNotEquals(-1,result);
        mainMenuPage.clickOnMenuItem(result);
        MenuItemModalPage menuItemModalPage =new MenuItemModalPage(driver);
        String name = menuItemModalPage.getMealName();
        assertEquals(mealName,name);

        boolean res = menuItemModalPage.clickOnChosenMealType(type);
        assertEquals(Boolean.parseBoolean(typeResult),res);

        boolean res1= menuItemModalPage.clickOnChosenAddons(addons);
        assertEquals(Boolean.parseBoolean(addonsResult),res1);

        int numbeforeadd = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(0,numbeforeadd);

        menuItemModalPage.addMeals(quantity);
        boolean remove_meals = menuItemModalPage.removeMeals(qRemoveBeforeAddToCart);
        assertEquals(Boolean.parseBoolean(firstRemoveResult),remove_meals);

        int numberafterremove = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(Integer.parseInt(qBeforeRemoveFromCart),numberafterremove);

        boolean addmealstocart = menuItemModalPage.addMealsToCart();
        assertEquals(Boolean.parseBoolean(addedMealToCartResult),addmealstocart);

        int numincart = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(qBeforeRemoveFromCart),numincart);

        boolean removefromcart = menuItemModalPage.removeMealsFromCart();
        assertEquals(Boolean.parseBoolean(removeMealFromCartResult),removefromcart);

        numincart = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(finalQuantity),numincart);
        menuItemModalPage.exitMealModal();
    }
    @Test(testName = "test meals in cart " , dependsOnMethods = "testAddMealsToCart",dataProvider = "Data")
    public void testMealsInCart(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException {
        headerPage.cartPageBtnClick();
        CartPage cartPage = new CartPage(driver);
        int result2 = cartPage.getMenuItemIndex(mealName);
        cartPage.clickOnMenuItem(result2);

        CartItemModalPage cartItemModalPage = new CartItemModalPage(driver);
        String name2 = cartItemModalPage.getMealName();
        assertEquals(mealName,name2);

        int numincart2 = cartItemModalPage.GetNumOfMealInCart();
        assertEquals(Integer.parseInt(finalQuantity),numincart2);
        cartItemModalPage.exitMealModal();
    }

    @Test(testName = "test meals existence and price in checkout " , dependsOnMethods = "testMealsInCart",dataProvider = "Data")
    public void testMealsInCheckOut(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        CheckOutOrderItem checkthisitem = checkOutPage.getCheckOutOrderItem(mealName);
        boolean checkresult = checkthisitem.checkOrderExist(mealName,finalQuantity,type,addons);
        assertTrue(checkresult);
        finalprice += checkthisitem.getPrice();
        checkOutPage.closeCheckOutDialog();
    }

    @Test(testName = "final price test" , dependsOnMethods = "testMealsInCheckOut")
    public void testFinalPrice() throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        assertEquals(finalprice,checkOutPage.getFinalPriceE());
    }
}

