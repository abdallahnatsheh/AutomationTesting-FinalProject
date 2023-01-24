package Pages.AdminPage;

import Tools.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        try{
            WebElement orderDate = this.ordersTable.getHeadersElementsByIndex(1);
            orderDate.click();
            orderDate.click();
        }catch (Exception e){
            System.out.println("error in ordering table by date");
            e.printStackTrace();
        }

    }
    public String getFirstRowFinalPrice(){
        try{
            return this.ordersTable.getCellValue(0,"₪ السعر كاملا");
        }catch (Exception e){
            System.out.println("error in getting first row final price");
            e.printStackTrace();
            return null;
        }
    }
    public void clickOnFirstResult(){
        try {
            ordersTable.getRowsElementsByIndex(0).click();
        }catch (Exception e){
            System.out.println("error in clicking on first row");
            e.printStackTrace();
        }
    }

}
