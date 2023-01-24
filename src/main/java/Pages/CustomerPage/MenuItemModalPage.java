package Pages.CustomerPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static Tools.Utils.IsElementPresent;
import static Tools.Utils.timeToWait;

public class MenuItemModalPage extends MainMenuPage {
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
        super(driver);
        this.closeModal = driver.findElement(By.className(closeModalClassName));
        this.mealName = driver.findElement(By.id(mealNameId));
        this.addMealToCartBtnEmpty = driver.findElement(By.id(addMealToCartBtnEmptyId));
        this.addMeal = driver.findElement(By.id(addMealId));
        this.removeMeal = driver.findElement(By.id(removeMealId));
        this.numMealsToAdd = driver.findElement(By.id(numMealsToAddId));
    }

    public String getMealName(){
        try{
            return  this.mealName.getText();
        }catch (Exception e){
            System.out.println("error getting meal name");
            e.printStackTrace();
            return null;
        }
    }

    public boolean clickOnChosenMealType(String typeName) throws InterruptedException {
        try{
            WebElement types;
            boolean result = false;
            if(IsElementPresent(By.id(typeName),driver)) {
                types = driver.findElement(By.id(typeName));
                types.click();
                timeToWait(1);
                result = true;
            }
            return result;
        }catch (Exception e){
            System.out.println("error clicking on meal type");
            e.printStackTrace();
            return false;
        }

    }

    public boolean clickOnChosenAddons(String addons) throws InterruptedException {
        try{
            String[] addonsList = addons.split("\\s*,\\s*");
            WebElement addonE;
            boolean result = false;
            for (String addon:addonsList) {
                if(IsElementPresent(By.id(addon),driver)) {
                    addonE = driver.findElement(By.id(addon));
                    addonE.click();
                    result = true;
                    timeToWait(1);
                }
                else {
                    result = false;
                    break;
                }
            }
            return result;
        }catch (Exception e){
            System.out.println("error clicking on chosen addons");
            e.printStackTrace();
            return false;
        }

    }

    public int getNumMealsBeforeAdd (){
        try{
            return Integer.parseInt(this.numMealsToAdd.getText());
        }catch (Exception e){
            System.out.println("error in getting number of meals before adding to cart");
            e.printStackTrace();
            return -1;
        }
    }
    //add meals to order before add to cart
    public void addMeals (String NumOfMeals) throws InterruptedException {
        try{
            int NumOfMealsInt = Integer.parseInt(NumOfMeals);
            for (int i=0 ; i <NumOfMealsInt;i++){
                addMeal.click();
                timeToWait(1);
            }
        }catch (Exception e){
            System.out.println("error adding meals to order before adding to cart");
            e.printStackTrace();
        }

    }
    //remove meal before add to cart
    public boolean removeMeals (String NumOfMeals) throws InterruptedException {
        try{
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
        }catch (Exception e){
            System.out.println("error removing meals before adding to cart");
            e.printStackTrace();
            return false;
        }

    }
    //add meal to cart
    public boolean addMealsToCart () throws InterruptedException {
        try{
            if(IsElementPresent(By.id(addMealToCartBtnEmptyId),driver) && !IsElementPresent(By.id(addMealToCartBtnNotEmptyId),driver)) {
                return false;
            }
            else {
                addMealToCartBtnNotEmpty = driver.findElement(By.id(addMealToCartBtnNotEmptyId));
                addMealToCartBtnNotEmpty.click();
                timeToWait(0.5);
                return true;
            }
        }catch (Exception e){
            System.out.println("error adding meals to cart");
            e.printStackTrace();
            return false;
        }

    }
    //
    public int GetNumOfMealInCart (){
        try{
            if(IsElementPresent(By.xpath(numOfMealInCartXpath),driver)) {
                numOfMealInCart = driver.findElement(By.xpath(numOfMealInCartXpath));
                return Integer.parseInt(numOfMealInCart.getText());
            }
            else
                return 0;
        }catch (Exception e){
            System.out.println("error getting number of meals in cart");
            e.printStackTrace();
            return -1;
        }

    }

    // remove one meal from cart if existed
    public boolean removeMealsFromCart() throws InterruptedException {
        try{
            if(IsElementPresent(By.id(removeMealFromCartId),driver) && GetNumOfMealInCart() >0) {
                removeMealFromCart = driver.findElement(By.id(removeMealFromCartId));
                removeMealFromCart.click();
                timeToWait(0.5);
                return true;
            }
            else
                return false;
        }catch (Exception e){
            System.out.println("error in removing meals from cart");
            e.printStackTrace();
            return false;
        }

    }

    public void exitMealModal() throws InterruptedException {
        try{
            timeToWait(0.5);
            closeModal.click();
        }catch (Exception e){
            System.out.println("error in exiting meals modal");
            e.printStackTrace();
        }

    }


}
