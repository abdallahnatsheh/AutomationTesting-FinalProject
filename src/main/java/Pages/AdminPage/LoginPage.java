package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
  WebElement email;
  WebElement password;
  WebElement loginBtn;


  public void loginPage(WebDriver driver){
      this.email = driver.findElement(By.id(""));
      this.password = driver.findElement(By.id(""));
      this.loginBtn = driver.findElement(By.id(""));
  }

  public void pressLoginBtn(){
      loginBtn.click();
  }


}
