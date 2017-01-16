package hh;

import com.codeborne.selenide.WebDriverRunner;
import com.galenframework.testng.GalenTestNgTestBase;
import hh.logger.WebDriverLogger;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;


/**
 * Created by KKornilov on 15.01.2017.
 */
public class Base extends GalenTestNgTestBase {

   public static EventFiringWebDriver driver;

    @Override
    public WebDriver createDriver(Object[] args) {
        FirefoxDriverManager.getInstance().setup();
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setJavascriptEnabled(true);
        driver =
                new EventFiringWebDriver(new FirefoxDriver(cap));
        driver.register(new WebDriverLogger());
        WebDriverRunner.setWebDriver(driver);
        WebDriverRunner.getWebDriver().manage().window().maximize();

        return  driver ;
    }
}
