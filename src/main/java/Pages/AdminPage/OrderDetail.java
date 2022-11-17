package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class OrderDetail {
    WebDriver driver;
    WebElement orderNameEL;
    WebElement orderTypeEL;
    WebElement orderQuantityEL;
    WebElement orderPriceEL;
    WebElement order;

    public OrderDetail(WebDriver driver,WebElement order){
        this.driver = driver;
        this.order = order;
        this.orderNameEL = order.findElement(By.id("order-name"));
        this.orderTypeEL = order.findElement(By.id("order-type"));
        this.orderQuantityEL = order.findElement(By.id("order-quantity"));
        this.orderPriceEL = order.findElement(By.id("order-price"));
    }

    public String getOrderName(){
        return this.orderNameEL.getText();
    }
    public String getOrderType(){
      return this.orderTypeEL.getText();
    }
    public List<String> getOrderAddons(){
        return Arrays.asList(order.findElement(By.id("order-addons")).getText().split("\\s*,\\s*"));
    }
    public int getOrderQuantity(){
        return Integer.parseInt(this.orderQuantityEL.getText());
    }
    public double getOrderPrice() {
        return Double.parseDouble(this.orderPriceEL.getText());
    }
    public boolean compareAddons(String addons){
        List<String> addonsList = Arrays.asList(addons.split("\\s*,\\s*"));
        return addonsList.equals(getOrderAddons());
    }

}
