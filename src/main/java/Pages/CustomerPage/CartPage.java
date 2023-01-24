package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.timeToWait;

public class CartPage extends MainMenuPage {
    WebElement checkOutBtn;
    final String checkOutBtnId = "checkout-btn";
    public CartPage(WebDriver driver) {
        super(driver);
        this.checkOutBtn = driver.findElement(By.id(checkOutBtnId));
    }
    public void clickCheckOutBtn() throws InterruptedException {
        try{
            checkOutBtn.click();
            timeToWait(0.5);
        }catch (Exception e){
            System.out.println("error in Click checkout button");
            e.printStackTrace();
        }

    }


}
