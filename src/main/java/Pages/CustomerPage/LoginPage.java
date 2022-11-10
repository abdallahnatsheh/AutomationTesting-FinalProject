package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.IsElementPresent;

public class LoginPage extends HeaderPage {
    WebElement email;
    WebElement password;
    WebElement loginBtn;
    final String emailId = "input-email";
    final  String passwordId = "input-password";
    final String loginBtnId = "login-button";
    final String emailErrorId = "invalid-email";
    final String passwordErrorId = "invalid-password";
    final String errorMessage = "div[role = 'alert']";
    public LoginPage(WebDriver driver)  {
        super(driver);
        this.email = driver.findElement(By.id(emailId));
        this.password = driver.findElement(By.id(passwordId));
        this.loginBtn = driver.findElement(By.id(loginBtnId));
    }

    public void writeEmail(String email){
        this.email.sendKeys(email);
    }
    public void writePassword(String password){
        this.password.sendKeys(password);
    }

  public boolean clickLoginBtn() throws InterruptedException {
      loginBtn.click();
      if(!IsElementPresent(By.id(emailErrorId),driver) && !IsElementPresent(By.id(passwordErrorId),driver) ) {
          Thread.sleep(500);
          if(!IsElementPresent(By.cssSelector(errorMessage),driver))
            return true;
      }
      return false;
  }


}
