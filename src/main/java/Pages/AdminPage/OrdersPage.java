package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrdersPage extends BasePage {

    WebElement tableOrderByDate;
    final String tableOrderByDateXpath = "//*[@id=\"content\"]/div[2]/table/thead/tr/th[2]";

    public OrdersPage(WebDriver driver) {
        super(driver);
    }
    public void orderTableByDate(){
        this.tableOrderByDate = driver.findElement(By.id(tableOrderByDateXpath));
        this.tableOrderByDate.click();
        this.tableOrderByDate.click();
    }
}
