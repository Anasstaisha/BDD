package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
  void prepareForTest() {
    open("http://localhost:9999");
    Configuration.holdBrowserOpen = true;
    var loginPage = new LoginPage();
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

    @Test
    void shouldTransferMoneyToFirstCard() {
      var dashboardPage = new DashboardPage();
      int amountValue = 10_000;
      var expAmountOfMoneyOnFirstCard = dashboardPage.getFirstCardBalance() + amountValue;
      var expAmountOfMoneyOnSecondCard = dashboardPage.getSecondCardBalance() - amountValue;
      var depositingFunds = dashboardPage.firstCardDepositing();
      depositingFunds.replenishment(amountValue, DataHelper.getSecondCardInformation());
      var actAmountOfMoneyOnFirstCard = dashboardPage.getFirstCardBalance();
      var actAmountOfMoneyOnSecondCard = dashboardPage.getSecondCardBalance();
      assertEquals(expAmountOfMoneyOnFirstCard, actAmountOfMoneyOnFirstCard);
      assertEquals(expAmountOfMoneyOnSecondCard, actAmountOfMoneyOnSecondCard);

    }

  @Test
  void shouldTransferMoneyToSecondCard() {
    var dashboardPage = new DashboardPage();
    int amountValue = 10_000;
    var expAmountOfMoneyOnFirstCard = dashboardPage.getFirstCardBalance() - amountValue;
    var expAmountOfMoneyOnSecondCard = dashboardPage.getSecondCardBalance() + amountValue;
    var depositingFunds = dashboardPage.secondCardDepositing();
    depositingFunds.replenishment(amountValue, DataHelper.getFirstCardInformation());
    var actAmountOfMoneyOnFirstCard = dashboardPage.getFirstCardBalance();
    var actAmountOfMoneyOnSecondCard = dashboardPage.getSecondCardBalance();
    assertEquals(expAmountOfMoneyOnFirstCard, actAmountOfMoneyOnFirstCard);
    assertEquals(expAmountOfMoneyOnSecondCard, actAmountOfMoneyOnSecondCard);
  }

 /* @Test
  void shouldTransferMoneyBetweenOwnCardsV3() {
    var loginPage = open("http://localhost:9999", LoginPageV3.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
    val DashboardPage = new DashboardPage();
    int value = 1000;

  }*/
}

