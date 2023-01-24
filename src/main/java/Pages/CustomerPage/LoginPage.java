package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.IsElementPresent;

public class LoginPage extends BasePage {
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
        try{
            this.email.sendKeys(email);
        }catch (Exception e){
            System.out.println("error in writing email ");
            e.printStackTrace();
        }
    }
    public void writePassword(String password){
        try{
            this.password.sendKeys(password);
        }catch (Exception e){
            System.out.println("error in writing password ");
            e.printStackTrace();
        }
    }

  public boolean clickLoginBtn() throws InterruptedException {
      try{
          loginBtn.click();
          if(!IsElementPresent(By.id(emailErrorId),driver) && !IsElementPresent(By.id(passwordErrorId),driver) ) {
              Thread.sleep(500);
              if(!IsElementPresent(By.cssSelector(errorMessage),driver))
                  return true;
          }
          return false;
      }
      catch (Exception e){
          System.out.println("error in clicking login button ");
          e.printStackTrace();
          return false;
      }
  }


}
