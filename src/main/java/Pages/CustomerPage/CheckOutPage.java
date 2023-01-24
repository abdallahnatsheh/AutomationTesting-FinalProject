package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.IsElementPresent;
import static Tools.Utils.timeToWait;

public class CheckOutPage extends CartPage {
    WebElement finalPriceE;
    WebElement orderTypeRadio;
    WebElement orderNotes;
    WebElement payCash;
    WebElement closeCheckOut;

    public CheckOutPage (WebDriver driver){
        super(driver);
        this.finalPriceE = driver.findElement(By.id("final-price"));
        this.orderNotes = driver.findElement(By.id("order-notes"));
        this.payCash =driver.findElement(By.id("pay-cash"));
        this.closeCheckOut =driver.findElement(By.id("close-order-dialog"));

    }

    public double getFinalPriceE() {
        try{
            return Double.parseDouble(finalPriceE.getText());
        }catch (Exception e){
            System.out.println("error in getting final price");
            e.printStackTrace();
            return -1.0;
        }
    }
    public CheckOutOrderItem  getCheckOutOrderItem(String orderItemId){
        try{
            return new CheckOutOrderItem(driver,orderItemId);
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
            return null;
        }
    }
    public boolean chooseOrderType(String ordeType){
        try{
            if(IsElementPresent(By.id(ordeType),driver)){
                orderTypeRadio = driver.findElement(By.id(ordeType));
                orderTypeRadio.click();
                return true;
            }else
                return false;
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
            return false;
        }
    }

    public void writeNotes(String notes){
        try{
            this.orderNotes.sendKeys(notes);
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
        }
    }

    public boolean clickPayCash () throws InterruptedException {
        try{
            if(this.payCash.isEnabled()){
                payCash.click();
                timeToWait(2);
                return true;
            }
            else
                return false;
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
            return false;
        }
    }
    public void closeCheckOutDialog(){
        try{
            closeCheckOut.click();
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
        }
    }
}
