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
        try{
        return this.orderNameEL.getText();
        }
        catch (Exception e){
            System.out.println("error getting order name");
            e.printStackTrace();
            return null;
        }
    }
    public String getOrderType(){
    try{
        return this.orderTypeEL.getText();
    }catch (Exception e){
        System.out.println("error getting order type");
        e.printStackTrace();
        return null;
        }
    }
    public List<String> getOrderAddons() {
        try {
            return Arrays.asList(order.findElement(By.id("order-addons")).getText().split("\\s*,\\s*"));
        } catch (Exception e) {
            System.out.println("error getting order addons");
            e.printStackTrace();
            return null;
        }
    }
    public int getOrderQuantity(){
        try{
            return Integer.parseInt(this.orderQuantityEL.getText());
        }catch (Exception e){
            System.out.println("error getting order quantity");
            e.printStackTrace();
            return -1;
        }
    }
    public boolean compareAddons(String addons){
        try{
            List<String> addonsList = Arrays.asList(addons.split("\\s*,\\s*"));
            return addonsList.equals(getOrderAddons());
        }catch (Exception e){
            System.out.println("error in comparing addons");
            e.printStackTrace();
            return false;
        }

    }

}
