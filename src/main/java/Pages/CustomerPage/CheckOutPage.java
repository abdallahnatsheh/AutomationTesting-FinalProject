package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.IsElementPresent;
import static Tools.Utils.timeToWait;

public class CheckOutPage {
    WebDriver driver;
    WebElement finalPriceE;
    WebElement orderTypelRadio;
    WebElement orderNotes;
    WebElement payCash;
    WebElement closeCheckOut;

    public CheckOutPage (WebDriver driver){
        this.driver = driver;
        this.finalPriceE = driver.findElement(By.id("final-price"));
        this.orderNotes = driver.findElement(By.id("order-notes"));
        this.payCash =driver.findElement(By.id("pay-cash"));
        this.closeCheckOut =driver.findElement(By.id("close-order-dialog"));

    }

    public double getFinalPriceE() {
        return Double.parseDouble(finalPriceE.getText());
    }
    public CheckOutOrderItem  getCheckOutOrderItem(String orderItemId){
        return new CheckOutOrderItem(driver,orderItemId);
    }
    public boolean chooseOrderType(String ordeType){
        if(IsElementPresent(By.id(ordeType),driver)){
            orderTypelRadio = driver.findElement(By.id(ordeType));
            orderTypelRadio.click();
            return true;
        }else
            return false;
    }

    public void writeNotes(String notes){
        this.orderNotes.sendKeys(notes);
    }

    public boolean clickPayCash () throws InterruptedException {
        if(this.payCash.isEnabled()){
            payCash.click();
            timeToWait(2);
            return true;
        }
        else
            return false;
    }
    public void closeCheckOutDialog(){
        closeCheckOut.click();
    }
}
