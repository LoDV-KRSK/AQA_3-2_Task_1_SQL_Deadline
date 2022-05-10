package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.getAuthInfo;
import static ru.netology.data.DataHelper.getInvalidAuthInfo;

public class TestUI {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }


    @AfterAll
    static void cleanUp() {
        DataHelper.clearDB();
    }

    @Test
    void shouldLogIn() {
        var validUser = getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(validUser);
        var verifyInfo = DataHelper.getVerificationCodeFor();
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        dashboardPage.getHeading();
    }

    @Test
    void shouldBeBlocked() {
        var invalidUser = getInvalidAuthInfo();
        var loginPage = new LoginPage();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.blockLogin();
    }
}
