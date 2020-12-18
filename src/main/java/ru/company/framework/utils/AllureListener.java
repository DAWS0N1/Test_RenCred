package ru.company.framework.utils;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.company.framework.managers.DriverManager;


public class AllureListener implements TestWatcher {

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] addScreenshot(){
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }


    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        addScreenshot();
    }
}
