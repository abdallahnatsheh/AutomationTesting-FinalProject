package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class OrderDetail extends OrdersDetailsPage {
    WebElement orderNameEL;
    WebElement orderTypeEL;
    WebElement orderQuantityEL;
    WebElement order;
    final String orderNameId = "order-name";
    final String orderTypeId = "order-type";
    final String orderQuantityId = "order-quantity";


    public OrderDetail(WebDriver driver,WebElement order){
        super(driver);
        this.order = order;
        this.orderNameEL = order.findElement(By.id(orderNameId));
        this.orderTypeEL = order.findElement(By.id(orderTypeId));
        this.orderQuantityEL = order.findElement(By.id(orderQuantityId));
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
    public boolean compareAddons(String addons){
        List<String> addonsList = Arrays.asList(addons.split("\\s*,\\s*"));
        return addonsList.equals(getOrderAddons());
    }

}
