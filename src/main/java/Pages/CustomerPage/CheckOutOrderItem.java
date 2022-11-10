package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class CheckOutOrderItem extends  CheckOutPage {
    WebElement order;
    WebElement quantity;
    WebElement name;
    WebElement price;
    WebElement addons;
    WebElement type;

    public CheckOutOrderItem (WebDriver driver , String ItemId){
        super(driver);
        this.order = driver.findElement(By.id(ItemId));
        this.quantity = order.findElement(By.xpath("div[1]/div[1]"));
        this.name =  order.findElement(By.xpath("div[1]/div[2]"));
        this.price =  order.findElement(By.xpath("div[1]/div[3]"));
        this.addons =  order.findElement(By.xpath("div[2]"));
        this.type =  order.findElement(By.xpath("div[3]"));
    }


    public int getQuantity() {
        return Integer.parseInt(quantity.getText());
    }

    public String getName() {
        return name.getText();
    }

    public double getPrice() {
        return  Double.parseDouble(price.getText());
    }
    public List<String> getAddons(){
        return Arrays.asList(addons.getText().split("\\s*,\\s*"));
    }

    public String getType() {
        return type.getText();
    }

    public boolean compareName(String name){
        return name.equals(getName());
    }
    public boolean compareQuantity(String quantity){
        int quantityInt = Integer.parseInt(quantity);
        int orderQuantity = getQuantity();
        return quantityInt == orderQuantity;
    }
    public boolean compareType(String type){
        return type.equals(getType());
    }
    public boolean compareAddons(String addons){
        List<String> addonsList = Arrays.asList(addons.split("\\s*,\\s*"));
        return addonsList.equals(getAddons());
    }

    public boolean checkOrderExist(String name,String quantity,String type,String addons){
        return compareName(name) && compareQuantity(quantity)  && compareType(type)
                && compareAddons(addons);
    }

}
