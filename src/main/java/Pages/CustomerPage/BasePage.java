package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    WebDriver driver;
    WebElement mainMenuPageBtn;
    WebElement loginPageBtn;
    WebElement cartPageBtn;
    final String mainMenuPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[1]/a[2]";
    final String loginPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[2]/a";
    final String cartPageBtnXpath = "//*[@id=\"cart-btn\"]";

    public BasePage (WebDriver driver){
        this.driver = driver;
    }
    public void loginPageBtnClick(){
        try{
            this.loginPageBtn = driver.findElement(By.xpath(loginPageBtnXpath));
            this.loginPageBtn.click();
        }catch (Exception e){
            System.out.println("error in clicking login page button");
            e.printStackTrace();
        }

    }


    public void mainMenuPageBtnClick(){
        try{
            this.mainMenuPageBtn = driver.findElement(By.xpath(mainMenuPageBtnXpath));
            mainMenuPageBtn.click();
        }catch (Exception e){
            System.out.println("error in clicking main menu page button");
            e.printStackTrace();
        }

    }

    public void cartPageBtnClick(){
        try{
            this.cartPageBtn = driver.findElement(By.xpath(cartPageBtnXpath));
            cartPageBtn.click();
        }catch (Exception e){
            System.out.println("error in clicking login page button");
            e.printStackTrace();
        }

    }


}
