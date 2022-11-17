package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class OrdersDetailsPage extends OrdersPage {
    List<WebElement> orderList;
    List<OrderDetail> detailedOrderList;

    public OrdersDetailsPage(WebDriver driver) {
        super(driver);
        this.orderList = driver.findElements(By.xpath("//*[@id=\"order-details\"]/div"));
        this.detailedOrderList = new ArrayList<>();
    }
    public void parseOrderList(){
        for (WebElement order:orderList) {
            detailedOrderList.add(new OrderDetail(driver,order));
        }
    }
    public boolean isOrderExist(String mealName,int finalQuantity,String type,String addons){
        boolean resultFlag = false;
        for (OrderDetail orderD:detailedOrderList) {
            if(orderD.getOrderName().equals(mealName) && orderD.getOrderQuantity() == finalQuantity && orderD.getOrderType().equals(type) && orderD.compareAddons(addons) )
                resultFlag = true;
        }
        return resultFlag;
    }
}
