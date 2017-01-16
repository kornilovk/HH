package hh;

import static com.codeborne.selenide.Selenide.open;
import static java.util.Arrays.asList;

import hh.Steps.EmployerPage;
import hh.Steps.SearchPage;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;
import hh.Steps.HomePage;

/**
 * Created by KKornilov on 13.01.2017.
 */

@Title("backend auth")
@Description("features")
public class TestHh extends Base {

    @Features("My Feature")
    @Stories({"Story1", "Story2"})
    @Test
    public void CheckCompanyExist() throws Exception {
        HomePage mainPage = open("https://spb.hh.ru", HomePage.class);
        checkLayout("specs/HomePage.gspec", asList("desktop"));

        SearchPage search = mainPage.CompanySearch("новые облачные");
        search.CheckCompanyName("Новые Облачные Технологии");
        checkLayout("specs/SearchPage.gspec", asList("desktop"));
    }

    @Features("My Feature")
    @Stories({"Story1", "Story2"})
   @Test(dependsOnMethods = { "CheckCompanyExist" })
    public void CheckVacansyCount() throws Exception {
/*       SearchPage search= open("https://spb.hh.ru/employers_list?query=новые облачные&areaId=113", SearchPage.class);
       search.CheckCompanyName("Новые Облачные Технологии");
       EmployerPage employer = search.ToCompany();
       employer.CheckCount(0);*/

       EmployerPage employer= open("https://spb.hh.ru/employer/213397", EmployerPage.class);
       employer.CheckCompanyName("Новые Облачные Технологии ");
       employer.CheckCount(0);
        checkLayout("specs/EmployerPage.gspec", asList("section"));
    }



    @Features("My Feature")
    @Stories({"Story1", "Story2"})
    @Test(dependsOnMethods = { "CheckVacansyCount" })
    public void CheckQaVacansy() throws Exception {
        EmployerPage employer= open("https://spb.hh.ru/employer/213397", EmployerPage.class);
        employer.CheckVacansy("Qa");
        checkLayout("specs/EmployerPage.gspec", asList("Content"));

    }

    @AfterTest
    public void  close_driver() throws Exception {
        driver.quit();
    }


}
