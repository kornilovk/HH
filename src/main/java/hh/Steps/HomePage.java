package hh.Steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import hh.Base;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.IOException;
import java.util.Iterator;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;




/**
 * Created by KKornilov on 13.01.2017.
 */
public class HomePage extends Base {
    MainPageElements mainpage = new MainPageElements();



    @Step("Поиск компании")
    public SearchPage CompanySearch(String CompanyName) throws IOException {

        mainpage.SearchField().shouldBe(Condition.appear).sendKeys(CompanyName);
        Select dropdown = new Select(mainpage.SearchElement());
        dropdown.selectByVisibleText("Компании");
        Iterator element = mainpage.SearchButton().iterator();
        while (element.hasNext()) {
            SelenideElement elem = (SelenideElement) element.next();
            Integer height = elem.getSize().getHeight();
            Integer width = elem.getSize().getWidth();

            if (height > 0 && width > 0){
                elem.click();
            }
        }
        return page(SearchPage.class);
    }
}




    class MainPageElements {

        public SelenideElement SearchField() {
            return $(By.cssSelector("div.bloko-control-group__main > input"));
        }

        public SelenideElement SearchElement() {
            return $(By.cssSelector("div.bloko-control-group__minor.HH-Navi-SearchSelector-SelectWrapper > select"));
        }

        public ElementsCollection SearchButton() {
            return $$(By.cssSelector(".bloko-button.bloko-button_primary.bloko-button_stretched"));
        }

    }

