package tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

@Owner("akida")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Официальные источники Акрон")
public class AcronParametrizedTests {

    @BeforeEach
    void precondition() {
        step("Перейти на главную страниу Акрона", () -> {
            open("https://www.acron.ru/");
        });
    }

    @AfterEach
    void closeBrowser() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
        closeWebDriver();
    }

    @CsvSource({
//            "0, https://www.acron.ru/contacts", -- не ясно как открыть в новой вкладке
            "1, https://vk.com/acronpjsc",
//            "2, https://www.facebook.com/AcronPJSC",  // в россии не доступен больше
            "3, https://www.youtube.com/channel/UCKxgdzy7CMqws5Z6TR_UuqA",
            "4, https://www.instagram.com/acron_group/", // не самый стабильный тест
            // после второго открытия просит зарегестрироваться (куки и кеш чистится после каждого
            // выполнения теста в @AfterEach в решении задачи подсказки не было
            "5, https://t.me/acron_official"
    })

    @ParameterizedTest(name = "Переход на офицальные источники в соцсетях Acron: \"{1}\"")
    void checkSiteMoveToOfficialPage(int testData, String expectedURL) {
        step("Перейти в раздел информации о компании", () -> {
            $("[class*='burger js-burger']").click();
        });
        step("Открыть официальные источники", () -> {
            $("ul.menu__socials li", testData).click();
        });
        step("Проверить что открылась страница с оффициальным источником", () -> {
            switchTo().window(1);
            webdriver().shouldHave(url(expectedURL));
        });
    }

}