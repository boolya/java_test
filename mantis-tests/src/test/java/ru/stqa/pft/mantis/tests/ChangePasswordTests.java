package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase  {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangePassword() {
    app.goTo().loginPage();
    app.login().loginAsAdmin();
    app.goTo().manageMainPage();
    app.goTo().manageUserPage();

    Users before = app.db().users();
    UserData modifiedUser = before.iterator().next();
    while(modifiedUser.getUsername() == "administrator") {
      modifiedUser = before.iterator().next();
    }

    UserData user = new UserData().withId(modifiedUser.getId());
    app.user().resetPassword(user);


    //assertThat(app.user().count(),equalTo(before.size()));
    //Users after = app.db().users();
    //assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));

  }

   @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

















}
