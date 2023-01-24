package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    WebDriver driver;
    WebElement ordersPage;
    final String ordersId = "orders";

    public BasePage (WebDriver driver){
     try{
        this.driver = driver;
     }
     catch (Exception e){
         System.out.println("error in base page");
         e.getStackTrace();
     }

    }

    public void ordersPageClick(){
        try{
        this.ordersPage = driver.findElement(By.id(ordersId));
        this.ordersPage.click();
        }catch (Exception e) {
            System.out.println("error in order page click");
            e.printStackTrace();
        }
    }



}
