import Pages.CustomerPage.LoginPage;
import Tools.OpenBrowsers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class temptest {
    @Test
    public void testBrowser() throws InterruptedException {
        WebDriver driver = OpenBrowsers.openBrowser("firefox");
        driver.get("https://samirest-grill-alpha.web.app/login");
        driver.manage().window().maximize();
        Thread.sleep(5000);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.writeEmail("");
        Thread.sleep(2000);
        loginPage.writePassword("");
        Thread.sleep(2000);
        boolean result = loginPage.clickLoginBtn();
        assertEquals(true,result);
        // if(exist){
        //   WebElement errorText = driver.findElement(By.id(errorid));
        // String errorCheck =  errorText.getCssValue("color");
        // String hex = Color.fromString(errorCheck).asHex();
        //assertEquals("#d32f2f",hex);
        //}
        Thread.sleep(5000);
       // driver.quit();
    }
}
