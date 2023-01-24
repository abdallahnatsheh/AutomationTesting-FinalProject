package Pages.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static Tools.Utils.IsElementPresent;

public class LoginPage {
    WebDriver driver;
    WebElement email;
    WebElement password;
    WebElement loginBtn;
    final String emailId = "InputEmail";
    final  String passwordId = "InputPassword";
    final String loginBtnId = "login-btn";
    final String emailErrorId = "email-error";
    final String passwordErrorId = "password-error";
    final String errorMessage = "div[role = 'alert']";
    public LoginPage(WebDriver driver)  {
        this.driver = driver;
        this.email = driver.findElement(By.id(emailId));
        this.password = driver.findElement(By.id(passwordId));
        this.loginBtn = driver.findElement(By.id(loginBtnId));
    }

    public void writeEmail(String email){
        try{
            this.email.sendKeys(email);
        }catch (Exception e){
            System.out.println("error in writing email");
            e.printStackTrace();
        }
    }
    public void writePassword(String password){
        try{
            this.password.sendKeys(password);
        }catch (Exception e){
            System.out.println("error in writing password");
            e.printStackTrace();

        }
    }

    public boolean clickLoginBtn() throws InterruptedException {
        try {
            loginBtn.click();
            if (!IsElementPresent(By.id(emailErrorId), driver) && !IsElementPresent(By.id(passwordErrorId), driver)) {
                Thread.sleep(1500);
                if (!IsElementPresent(By.cssSelector(errorMessage), driver))
                    return true;
            }
            return false;
        }catch (Exception e){
            System.out.println("error in clicking login button");
            e.printStackTrace();
            return false;
        }
    }

}
