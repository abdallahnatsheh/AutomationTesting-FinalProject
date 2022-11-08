import Pages.CustomerPage.*;
import Tools.OpenBrowsers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


public class temptest {
    @Test
    public void testBrowser() throws InterruptedException {
        WebDriver driver = OpenBrowsers.openBrowser("chrome");
        driver.get("https://samirest-grill-alpha.web.app/");
        driver.manage().window().maximize();
        Thread.sleep(5000);
        HeaderPage headerPage = new HeaderPage(driver);
        headerPage.loginPageBtnClick();
        Thread.sleep(2000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.writeEmail("abdnatsheh33@gmail.com");
        Thread.sleep(2000);
        loginPage.writePassword("Password321");
        Thread.sleep(2000);
        boolean resultlogin = loginPage.clickLoginBtn();
        assertEquals(true,resultlogin);
        Thread.sleep(2000);
        headerPage.mainMenuPageBtnClick();
        MainMenuPage mainMenuPage = new MainMenuPage(driver);
        int result = mainMenuPage.getMenuItemIndex("سيب سيب");
        assertNotEquals(-1,result);
        mainMenuPage.clickOnMenuItem(result);
        MenuItemModalPage menuItemModalPage =new MenuItemModalPage(driver);
        String name = menuItemModalPage.getMealName();
        assertEquals("سيب سيب",name);

        boolean res = menuItemModalPage.clickOnChosenMealType("بيليبل");
        assertEquals(true,res);

        boolean res1= menuItemModalPage.clickOnChosenAddons("تجربةتجربة,لالبالبالب");
        assertEquals(true,res1);

        int numbeforeadd = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(0,numbeforeadd);

        menuItemModalPage.addMeals("5");
        boolean remove_meals = menuItemModalPage.removeMeals("2");
        assertEquals(true,remove_meals);

        int numberafterremove = menuItemModalPage.getNumMealsBeforeAdd();
        assertEquals(3,numberafterremove);

        boolean addmealstocart = menuItemModalPage.addMealsToCart();
        assertEquals(true,addmealstocart);

        int numincart = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(3,numincart);

        boolean removefromcart = menuItemModalPage.removeMealsFromCart();
        assertEquals(true,removefromcart);

        numincart = menuItemModalPage.GetNumOfMealInCart();
        assertEquals(2,numincart);
        menuItemModalPage.exitMealModal();

        headerPage.cartPageBtnClick();
        CartPage cartPage = new CartPage(driver);
        int result2 = cartPage.getMenuItemIndex("سيب سيب");
        cartPage.clickOnMenuItem(result2);

        CartItemModalPage cartItemModalPage = new CartItemModalPage(driver);
        String name2 = cartItemModalPage.getMealName();
        assertEquals("سيب سيب",name2);

        int numincart2 = cartItemModalPage.GetNumOfMealInCart();
        assertEquals(2,numincart2);
        cartItemModalPage.exitMealModal();

        cartPage.clickCheckOutBtn();
        CheckOutPage checkOutPage = new CheckOutPage(driver);
        CheckOutOrderItem checkthisitem = checkOutPage.getCheckOutOrderItem("سيب سيب");
        boolean checkresult = checkthisitem.checkOrderExist("سيب سيب","2","بيليبل","تجربةتجربة,لالبالبالب");
        assertEquals(true,checkresult);
        double finalprice = 0.0;
        finalprice += checkthisitem.getPrice();
        assertEquals(finalprice,checkOutPage.getFinalPriceE());



        boolean typeresult = checkOutPage.chooseOrderType("حجز");
        assertEquals(true,typeresult);

        checkOutPage.writeNotes("تجربة");

        checkOutPage.closeCheckOutDialog();
        //checkOutPage.clickPayCash();






//        driver.manage().window().maximize();
//        Thread.sleep(5000);
//        HeaderPage headerPage = new HeaderPage(driver);
//        headerPage.loginPageBtnClick();
//        Thread.sleep(2000);
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.writeEmail("");
//        Thread.sleep(2000);
//        loginPage.writePassword("");
//        Thread.sleep(2000);
//        boolean result = loginPage.clickLoginBtn();
//        assertEquals(true,result);
//        // if(exist){
        //   WebElement errorText = driver.findElement(By.id(errorid));
        // String errorCheck =  errorText.getCssValue("color");
        // String hex = Color.fromString(errorCheck).asHex();
        //assertEquals("#d32f2f",hex);
        //}
        Thread.sleep(5000);
       driver.quit();
    }
}
