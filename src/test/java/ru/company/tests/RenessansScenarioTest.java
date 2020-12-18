package ru.company.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.company.base.BaseTests;


public class RenessansScenarioTest extends BaseTests {

    @ParameterizedTest(name = "Ренессанс Тест [{index}]")
    @CsvSource({"rub,300000,6,50000,Ежемесячная капитализация,9132.17,250000,559132.17",
    "usd,500000,12,20000,Ежемесячная капитализация,920.60,220000,720920.60"})
    void renessansScenario(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8){
        app.getStartPage()
                .selectMenu("Вклады")
                .pageCheck("Вклады")
                //"rub" or "usd"
                .selectCurrency(s1)
                .inputDeposit("Сумма вклада", s2)
                .inputDeposit("На срок", s3)
                .inputDeposit("Ежемесячное пополнение", s4)
                .selectCheckbox(s5)
                .checkingField("Начислено %:", s6)
                .checkingField("Пополнение за", s7)
                .checkingField("К снятию", s8);

    }
}
