package Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Table {
    WebDriver driver;
    WebElement table;
    public Table(WebDriver driver, WebElement table) {
        this.driver = driver;
        this.table = table;
    }

    public int getColumnsNumber() {
        List<WebElement> headers = this.getHeadersElements();
        for(int i=0;i<headers.size();i++) {
            System.out.println(headers.get(i).getText());
        }
        int headrs_num = headers.size();
        return headrs_num;
    }

    public List<WebElement> getHeadersElements(){
        List<WebElement> headers = table.findElements(By.xpath("//thead/tr/th"));
        return headers;
    }
    public WebElement getHeadersElementsByIndex(int index){
        List<WebElement> headers = table.findElements(By.xpath("//thead/tr/th"));
        return headers.get(index);
    }

    public List<WebElement> getRowsElements(){
        List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));
        return rows;
    }
    public WebElement getRowsElementsByIndex(int index){
        List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));
        return rows.get(index);
    }

    public int getRowNumber() {
        List<WebElement> rows = this.getRowsElements();
        int row_num = rows.size();
        return row_num;
    }

    public String getCellValue(int rowIndex, String columnHeader) {
        List<WebElement> headers = this.getHeadersElements();
        List<WebElement> rows = this.getRowsElements();
        for(int i=0;i<headers.size();i++) {
            if(headers.get(i).getText().equalsIgnoreCase(columnHeader)) {
                return rows.get(rowIndex).findElements(By.tagName("td")).get(i).getText();
            }
        }
        return "";
    }

}
