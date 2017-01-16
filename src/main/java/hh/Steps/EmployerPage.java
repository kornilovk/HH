package hh.Steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created by KKornilov on 15.01.2017.
 */
public class EmployerPage {
    EmployerPageElements employerpage = new EmployerPageElements();

    @Step("Проверка количества вакансий в текущем регионе")
    public EmployerPageElements CheckCount(Integer count) {

        Integer employercount = Integer.valueOf(employerpage.CountInMainRegion().getText());
        assert(employercount >= count) : "Количество вакансий в текущем регионе меньше ожидаемого";
        return page(EmployerPageElements.class);
    }

    @Step("В текущем регионе есть вакансия \"QA Automation Engineer\"")
    public EmployerPageElements CheckVacansy(String VacansyName) {
        Integer count = 0;
        employerpage.IT().click();
        employerpage.Vacansys().shouldBe(CollectionCondition.sizeGreaterThan(0));
        Iterator element = employerpage.Vacansys().iterator();

        while (element.hasNext()) {
            SelenideElement elem = (SelenideElement) element.next();
            String name = elem.getText();
            boolean match = StringUtils.containsIgnoreCase(name, VacansyName);
            if (match){
                count = count + 1;
            }
        }
        assert(count > 0) : "В текущем регионе отсутвуют ожидаемые вакансии";
        return page(EmployerPageElements.class);
    }

    @Step("Проверка названия компании")
    public EmployerPageElements CheckCompanyName(String CompanyName) {

        String name = employerpage.CompanyName().getText();
        assert(name.equals(CompanyName)) : "Название компании " + name + "не соотвествует ожидаемому " + CompanyName;
        return page(EmployerPageElements.class);
    }
}

class EmployerPageElements {


    public SelenideElement CompanyName() {
        return $(By.cssSelector("div > div.bloko-gap.bloko-gap_bottom > div > h1"));
    }

    public SelenideElement CountInMainRegion() {
        return $(By.cssSelector("div.b-employerpage-vacancies.g-expand > h4 > span.b-employerpage-vacancies-hint"));
    }

    public SelenideElement IT() {
        return $(By.cssSelector("div > div:nth-child(1) > div.b-emppage-vacancies-group-title > a"));
    }

    public ElementsCollection Vacansys() {
        return $$(By.cssSelector("div.b-vacancy-list-name > a"));
    }

}

