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
        try{
            return Integer.parseInt(quantity.getText());
        }catch (Exception e){
            System.out.println("error in getting quantity");
            e.printStackTrace();
            return -1;
        }
    }

    public String getName() {
        try{
            return name.getText();
        }catch (Exception e){
            System.out.println("error in getting name");
            e.printStackTrace();
            return null;
        }

    }

    public double getPrice() {
        try{
            return  Double.parseDouble(price.getText());
        }catch (Exception e){
            System.out.println("error in getting price");
            e.printStackTrace();
            return -1.0;
        }

    }
    public List<String> getAddons(){
        try{
            return Arrays.asList(addons.getText().split("\\s*,\\s*"));
        }catch (Exception e){
            System.out.println("error in getting addons");
            e.printStackTrace();
            return null;
        }

    }

    public String getType() {
        try{
            return type.getText();
        }catch (Exception e){
            System.out.println("error in getting type of meal");
            e.printStackTrace();
            return null;
        }
    }

    public boolean compareName(String name){
        try{
            return name.equals(getName());
        }catch (Exception e){
            System.out.println("error in comparing names");
            e.printStackTrace();
            return false;
        }

    }
    public boolean compareQuantity(String quantity){
        int quantityInt = Integer.parseInt(quantity);
        int orderQuantity = getQuantity();
        try{
            return quantityInt == orderQuantity;
        }catch (Exception e){
            System.out.println("error in comparing quantity");
            e.printStackTrace();
            return false;
        }

    }
    public boolean compareType(String type){
        try{
            return type.equals(getType());
        }catch (Exception e){
            System.out.println("error in comparing types");
            e.printStackTrace();
            return false;
        }
    }
    public boolean compareAddons(String addons){
        List<String> addonsList = Arrays.asList(addons.split("\\s*,\\s*"));
        try{
            return addonsList.equals(getAddons());
        }catch (Exception e){
            System.out.println("error in getting quantity");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkOrderExist(String name,String quantity,String type,String addons){
        try{
            return compareName(name) && compareQuantity(quantity)  && compareType(type)
                    && compareAddons(addons);
        }catch (Exception e){
            System.out.println("error in checking order if exist");
            e.printStackTrace();
            return false;
        }
    }

}
