package ru.company.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepositPage  extends BasePage {
    @FindBy(xpath = "//h1")
    WebElement title;

    @FindBy(xpath = "//div[@class='calculator__currency-row']")
    WebElement currencyBlock;

    @FindBy(xpath = "//div[@class='calculator__slide-section' and contains(@data-property, 'amount')]//input")
    WebElement inputSumValue;

    @FindBy(xpath = "//div[@id='period-styler']")
    WebElement selectTerm;

    @FindBy(xpath = "//div[@class='calculator__slide-section' and contains(@data-property, 'replenish')]//input")
    WebElement inputEzhemesech;

    @FindBy(xpath = "//div[@class='calculator__check-row']//label")
    List<WebElement> checkboxesList;

    @FindBy(xpath = "//tr[@class='calculator__dep-result-table-row']")
    List<WebElement> results;

    @FindBy(xpath = "//div[@class='calculator__dep-result-value']/span[contains(@class, 'result')]")
    WebElement valItog;

    static String termVal;

    /**
     * Метод проверки страницы
     *
     * @param titleName - имя веб элемента заголовка
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Проверяем страницу'")
    public DepositPage pageCheck(String titleName) {
        Assertions.assertEquals(titleName, title.getText(), "Открытая страница не соответствует запрошенной");
        return this;
    }

    /**
     * Метод выбора валюты
     *
     * @param cur - имя выбираемой валюты
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Выбираем валюту '{cur}'")
    public DepositPage selectCurrency(String cur) {
        WebElement name = currencyBlock.findElement(By.xpath("./div[@class='calculator__currency-label']"));
        List <WebElement> currencies = currencyBlock.findElements(By.xpath(".//label"));
        if (name.getText().equalsIgnoreCase("Валюта")) {
            switch (cur) {
                case "rub":
                    for (WebElement currency: currencies){
                        if (currency.getAttribute("innerText").trim().equalsIgnoreCase("Рубли")) {
                            action.moveToElement(currency).click().build().perform();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    break;
                case "usd":
                    for (WebElement currency: currencies){
                        if (currency.getAttribute("innerText").trim().equalsIgnoreCase("Доллары США")) {
                            action.moveToElement(currency).click().build().perform();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    break;

                default:
                    Assertions.fail("Валюта не найдена");
            }
        }
        return this;
    }

    /**
     * Метод ввода значений
     *
     * @param nameField - имя веб элемента, поля
     * @param value     - значение поля
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Вставляем в поле '{nameField}' значение '{value}'")
    public DepositPage inputDeposit(String nameField, String value) {
        switch (nameField) {
            case "Сумма вклада":
                fillInput(inputSumValue, value);
                break;
            case "На срок":
                scrollElementInCenter(selectTerm, 200);
                selectTerm.click();
                WebElement dropMenu = selectTerm.findElement(By.xpath(".//div[@class='jq-selectbox__dropdown']"));
                elementToBeVisible(dropMenu);
                List<WebElement> selectingMenu = dropMenu.findElements(By.xpath(".//li"));
                for (WebElement li : selectingMenu){
                    if (parseTextTerm(li.getText()).equalsIgnoreCase(value)){
                        li.click();
                        termVal = li.getText();
                        break;
                    }
                }
                break;
            case "Ежемесячное пополнение":
                fillInput(inputEzhemesech, value);
                break;

            default:
                Assertions.fail("Поле с наименованием '" + nameField + "' отсутствует на странице!");
        }
        return this;
    }

    /**
     * Метод проверки чекбоксов
     *
     * @param checkboxName - имя веб элемента, чекбокса
     * @return Deposit - т.е. остаемся на этой странице
     */
    @Step("Проверяем чекбокс '{checkboxName}'")
    public DepositPage selectCheckbox(String checkboxName){
        for (WebElement checkbox : checkboxesList) {
            if (checkbox.getAttribute("innerText").trim().equalsIgnoreCase(checkboxName)){
                scrollElementInCenter(checkbox, 400);
                elementToBeVisible(checkbox);
                checkbox.findElement(By.xpath("./span[@class='calculator__check-block-input']/div")).click();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this;
            }
        }
        Assertions.fail("Элемент не найден");
        return this;
    }

    /**
     * Метод проверки полей
     *
     * @param fieldName - имя веб элемента, поля
     * @param value - значение поля
     * @return DepositPage - т.е. остаемся на этой странице
     */
    @Step("Проверяем поле '{fieldName}'  на значение '{value}'")
    public DepositPage checkingField(String fieldName, String value) {
        switch (fieldName){
            case "Начислено %:":
                for (WebElement result : results){
                    WebElement title = result.findElement(By.xpath("./td[contains(@class, 'title')]"));
                    if (title.getText().equalsIgnoreCase(fieldName)){
                        WebElement res = result.findElement(By.xpath(".//b"));
                        Assertions.assertEquals(value, parseText(res.getText()), "Полученное значение не совпадает с запрашиваемым");
                        break;
                    }
                }
                break;
            case "Пополнение за":
                for (WebElement result : results){
                    WebElement title = result.findElement(By.xpath("./td[contains(@class, 'title')]"));
                    if (title.getText().equalsIgnoreCase(fieldName + " " + termVal + ":")){
                        WebElement res = result.findElement(By.xpath("./td[contains(@class, 'val')]/span[@class='js-calc-replenish']"));
                        Assertions.assertEquals(value, parseText(res.getText()), "Полученное значение не совпадает с запрашиваемым");
                        break;
                    }
                }
                break;
            case "К снятию":
                Assertions.assertEquals(value, parseText(valItog.getText()), "Полученное значение не совпадает с запрашиваемым");
                break;
            default:
                Assertions.fail("Элемент " + fieldName + " не найден на странице");
        }
        return this;
    }
}
