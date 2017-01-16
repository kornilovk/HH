package hh.Steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by KKornilov on 15.01.2017.
 */
public class SearchPage {

    SearchPageElements searchpage = new SearchPageElements();

    @Step("Проверка наличия компании")
    public SearchPageElements CheckCompanyName(String SearchCompanyName) {
        searchpage.SearchCompanyName()
                .should(Condition.visible)
                .should(Condition.appear)
                .should(Condition.text(SearchCompanyName));
        return page(SearchPageElements.class);
    }

    @Step("Проверка наличия компании")
    public EmployerPage ToCompany() {
        searchpage.SearchCompanyName()
                .should(Condition.visible)
                .should(Condition.appear)
                .click();
        return page(EmployerPage.class);
    }
}

    class SearchPageElements {


        public SelenideElement SearchCompanyName() {
            return $(By.cssSelector("div.l-nopaddings > table > tbody > tr > td > div > a"));
        }

    }

