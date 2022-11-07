package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderPage {
    WebDriver driver;
    WebElement mainPageBtn;
    WebElement mainMenuPageBtn;
    WebElement loginPageBtn;
    WebElement cartPageBtn;
    WebElement specialOrderPageBtn;
    final String mainPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[1]/a[1]";
    final String mainMenuPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[1]/a[2]";
    final String loginPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[2]/a";
    final String specialOrderPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[1]/button";
    final String cartPageBtnXpath = "//*[@id=\"cart-btn\"]";

    public HeaderPage (WebDriver driver){
        this.driver = driver;
    }
    public void loginPageBtnClick(){
        this.loginPageBtn = driver.findElement(By.xpath(loginPageBtnXpath));
        this.loginPageBtn.click();
    }

    public void mainPageBtnClick(){
        this.mainPageBtn = driver.findElement(By.xpath(mainPageBtnXpath));
        mainPageBtn.click();
    }

    public void mainMenuPageBtnClick(){
        this.mainMenuPageBtn = driver.findElement(By.xpath(mainMenuPageBtnXpath));
        mainMenuPageBtn.click();
    }

    public void cartPageBtnClick(){
        this.cartPageBtn = driver.findElement(By.xpath(cartPageBtnXpath));
        cartPageBtn.click();
    }

    public void specialOrderPageBtnClick(){
        this.specialOrderPageBtn = driver.findElement(By.xpath(specialOrderPageBtnXpath));
        specialOrderPageBtn.click();
    }


}
