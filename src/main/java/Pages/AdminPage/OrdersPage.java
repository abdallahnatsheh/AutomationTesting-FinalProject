package Pages.AdminPage;

import Tools.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OrdersPage extends BasePage {
    WebElement ordersTableEL;
    Table ordersTable;
    final String ordersTableXpath = "//*[@id=\"content\"]/div[2]/table";


    public OrdersPage(WebDriver driver) {
        super(driver);
        this.ordersTableEL = driver.findElement(By.xpath(ordersTableXpath));
        this.ordersTable = new Table(driver,ordersTableEL);

    }
    public void orderTableByDate(){
        WebElement orderDate = this.ordersTable.getHeadersElementsByIndex(1);
        orderDate.click();
        orderDate.click();
    }
    public String getFirstRowFinalPrice(){
       return this.ordersTable.getCellValue(0,"₪ السعر كاملا");
    }
    public void clickOnFirstResult(){
        ordersTable.getRowsElementsByIndex(0).click();
    }

}
