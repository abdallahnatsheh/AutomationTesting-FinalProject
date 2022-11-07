package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static Tools.Utils.IsElementPresent;
import static Tools.Utils.timeToWait;

public class MenuItemModalPage {
    WebDriver driver;
    WebElement closeModal;
    WebElement mealName;
    WebElement addMealToCartBtnNotEmpty;
    WebElement addMealToCartBtnEmpty;
    WebElement addMeal;
    WebElement removeMealFromCart;
    WebElement removeMeal;

    WebElement numOfMealInCart;

    WebElement numMealsToAdd;

    final String closeModalClassName ="btn-close";
    final String mealNameId = "meal-name";
    final String addMealToCartBtnNotEmptyId = "add-meal-not-empty";
    final String addMealToCartBtnEmptyId = "add-meal-empty";

    final String addMealId = "meal-plus";
    final String removeMealId = "meal-minus";
    final String removeMealFromCartId= "remove-meal";
    final String numMealsToAddId = "num-meals";
    final String numOfMealInCartXpath = "//*[@id=\"in-cart\"]/span";

    public MenuItemModalPage(WebDriver driver){
        this.driver = driver;
        this.closeModal = driver.findElement(By.className(closeModalClassName));
        this.mealName = driver.findElement(By.id(mealNameId));
        this.addMealToCartBtnEmpty = driver.findElement(By.id(addMealToCartBtnEmptyId));
        this.addMeal = driver.findElement(By.id(addMealId));
        this.removeMeal = driver.findElement(By.id(removeMealId));
        this.numMealsToAdd = driver.findElement(By.id(numMealsToAddId));
    }

    public String getMealName(){
        return  this.mealName.getText();
    }

    public boolean clickOnChosenMealType(String typeName){
        WebElement types;
        boolean result = false;
        if(IsElementPresent(By.id(typeName),driver)) {
            types = driver.findElement(By.id(typeName));
            types.click();
            result = true;
        }
        return result;
    }

    public boolean clickOnChosenAddons(String addons) throws InterruptedException {
        String[] addonsList = addons.split("\\s*,\\s*");
        WebElement addonE;
        boolean result = false;
        for (String addon:addonsList) {
            if(IsElementPresent(By.id(addon),driver)) {
                addonE = driver.findElement(By.id(addon));
                addonE.click();
                result = true;
                timeToWait(0.5);
            }
            else {
                result = false;
                break;
            }
        }
        return result;
    }

    public int getNumMealsBeforeAdd (){
        return Integer.parseInt(this.numMealsToAdd.getText());
    }
    //add meals to order before add to cart
    public void addMeals (String NumOfMeals) throws InterruptedException {
        int NumOfMealsInt = Integer.parseInt(NumOfMeals);
        for (int i=0 ; i <NumOfMealsInt;i++){
            addMeal.click();
            timeToWait(0.5);
        }
    }
    //remove meal before add to cart
    public boolean removeMeals (String NumOfMeals) throws InterruptedException {
        int NumOfMealsBeforeRem =getNumMealsBeforeAdd();
        int NumOfMealsInt = Integer.parseInt(NumOfMeals);
        if(NumOfMealsBeforeRem > NumOfMealsInt){
            for (int i=0 ; i <NumOfMealsInt;i++){
                if(removeMeal.isEnabled()) {
                    removeMeal.click();
                    timeToWait(0.5);
                }

            }
        }else {
            return false;
        }
        return true;
    }
    //add meal to cart
    public boolean addMealsToCart () throws InterruptedException {
        if(IsElementPresent(By.id(addMealToCartBtnEmptyId),driver) && !IsElementPresent(By.id(addMealToCartBtnNotEmptyId),driver)) {
           return false;
        }
        else {
            addMealToCartBtnNotEmpty = driver.findElement(By.id(addMealToCartBtnNotEmptyId));
            addMealToCartBtnNotEmpty.click();
            timeToWait(0.5);
            return true;
        }
    }
    //
    public int GetNumOfMealInCart (){
        if(IsElementPresent(By.xpath(numOfMealInCartXpath),driver)) {
            numOfMealInCart = driver.findElement(By.xpath(numOfMealInCartXpath));
            return Integer.parseInt(numOfMealInCart.getText());
        }
        else
            return 0;
    }

    // remove one meal from cart if existed
    public boolean removeMealsFromCart() throws InterruptedException {
        if(IsElementPresent(By.id(removeMealFromCartId),driver) && GetNumOfMealInCart() >0) {
            removeMealFromCart = driver.findElement(By.id(removeMealFromCartId));
            removeMealFromCart.click();
            timeToWait(0.5);
            return true;
            }
        else
            return false;
    }

    public void exitMealModal() throws InterruptedException {
        timeToWait(0.5);
        closeModal.click();
    }


}
