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
    final String cartPageBtnXpath = "//*[@id=\"responsive-navbar-nav\"]/div[3]/div/i";

    public HeaderPage (WebDriver driver){
        this.driver = driver;
        this.mainMenuPageBtn = driver.findElement(By.xpath(mainMenuPageBtnXpath));
        this.loginPageBtn = driver.findElement(By.xpath(loginPageBtnXpath));
        this.cartPageBtn = driver.findElement(By.xpath(cartPageBtnXpath));
        this.specialOrderPageBtn = driver.findElement(By.xpath(specialOrderPageBtnXpath));
        this.mainPageBtn = driver.findElement(By.xpath(mainPageBtnXpath));
    }
    public void loginPageBtnClick(){
        this.loginPageBtn.click();
    }

    public void mainPageBtnClick(){
        mainPageBtn.click();
    }

    public void mainMenuPageBtnClick(){
        mainMenuPageBtn.click();
    }

    public void cartPageBtnClick(){
        cartPageBtn.click();
    }

    public void specialOrderPageBtnClick(){
        specialOrderPageBtn.click();
    }


}
