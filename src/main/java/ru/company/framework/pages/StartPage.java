package ru.company.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StartPage extends BasePage {
    @FindBy(xpath = "//div[@class='service']")
    List<WebElement> services;

    /**
     * Функция наведения мыши на менюшку и открытия ее страницы
     *
     * @param titleName - наименование меню
     * @return DepositPage - т.е. переходим на след. страницу
     */
    @Step("Переход в меню {titleName}")
    public DepositPage selectMenu(String titleName) {
        WebElement title;
        for (WebElement menuItem : services) {
            title = menuItem.findElement(By.xpath(".//div[@class='service__title-text']"));
            if (title.getText().equalsIgnoreCase(titleName)){
                action.moveToElement(title).click().build().perform();
                return app.getDepositPage();
            }
        }
        Assertions.fail("Меню: \"" + titleName + "\" не найден!");
        return app.getDepositPage();
    }
}
