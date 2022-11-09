import Pages.CustomerPage.*;
import Tools.OpenBrowsers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static Tools.Utils.*;
import static org.testng.Assert.*;

public class CustomeBaseTest {
    WebDriver driver;
    HeaderPage headerPage;
    double finalPrice;
    static final  String inputFilePath = "CsvFiles/Input/input.csv";
    final String webSite = "https://samirest-grill-alpha.web.app/";
    final String browser = "chrome";
    @DataProvider(name = "Data")
    public static Object[][] getData() throws Exception{
        return parseCsvFile(inputFilePath);
    }
    @BeforeSuite
    public void  initTest(){
        finalPrice = 0;
        driver = OpenBrowsers.openBrowser(browser);
        driver.get(webSite);
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

        //close the pop-up modal of the meal
        menuItemModalPage.exitMealModal();
    }
    @Test(testName = "test meals in cart " , dependsOnMethods = "testAddMealsToCart",dataProvider = "Data")
    public void testMealsInCart(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException {
        headerPage.cartPageBtnClick();
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

        //close the pop-up modal of the meal
        cartItemModalPage.exitMealModal();
    }

    @Test(testName = "test meals existence and price in checkout " , dependsOnMethods = "testMealsInCart",dataProvider = "Data")
    public void testMealsInCheckOut(String mealName,String quantity,String qRemoveBeforeAddToCart,String firstRemoveResult , String qBeforeRemoveFromCart,String finalQuantity,String addedMealToCartResult,String removeMealFromCartResult,String type,String typeResult,String addons,String addonsResult) throws InterruptedException {
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

        //close the pop-up checkout dialog
        checkOutPage.closeCheckOutDialog();
    }

    @Test(testName = "final price test" , dependsOnMethods = "testMealsInCheckOut")
    public void testFinalPrice() throws InterruptedException {
        //init the checkout dialog page
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);

        //test if the final price of all the requested meals is equal to what written in the checkout page
        assertEquals(finalPrice,checkOutPage.getFinalPriceE());
    }
}

