package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @BeforeMethod
  public void ensurePreconditions() {
    //Проверить, есть ли пользователи (кроме админа, который есть всегда), и создать, если нет
    if (app.db().users().size() <= 1) {
      long date = System.currentTimeMillis();
      UserData newUser = new UserData().
              withUsername("test" + date).withEmail("test" + date + "@test.com").withPassword("123123");
      app.goTo().loginPage();
      app.login().loginAsAdmin();
      app.goTo().manageMainPage();
      app.goTo().manageUserPage();
      app.user().newUserCreate(newUser);
      app.user().logout();
    }

  }

  @Test
  public void testChangePassword() throws IOException, MessagingException {

    //Получаем список пользователей из базы и выбираем не администратора
    Users before = app.db().users();
    UserData modifiedUser = before.iterator().next();
    while (modifiedUser.getUsername() == "administrator") {
      modifiedUser = before.iterator().next();
    }
    UserData user = new UserData()
            .withId(modifiedUser.getId())
            .withUsername(modifiedUser.getUsername())
            .withEmail(modifiedUser.getEmail())
            .withPassword(modifiedUser.getPassword());
    long now = System.currentTimeMillis();
    String newPassword = String.format(user.getPassword() + "%s", now);

    //Администратор авторизуется, переходит на страницу управления пользователями
    // и сбрасывает пароль для ранее выбранного пользователя
    app.goTo().loginPage();
    app.login().loginAsAdmin();
    app.goTo().manageMainPage();
    app.goTo().manageUserPage();
    app.user().resetPasswordByAdmin(user);

    //Пользователь получает письмо и ссылку в нём для изменения пароля
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String changePasswordLink = findChangePasswordLink(mailMessages, user);

    //Пользователь переходит по ссылке из письма и сохраняет новый пароль
    app.user().changePassword(changePasswordLink, user, newPassword);

    //Проверяем возможность пользователя авторизоваться с новым паролем
    HttpSession session = app.newSession();
    assertTrue(session.login(user.getUsername(), newPassword));
    assertTrue(session.isLoggedInAs(user.getUsername()));

    //Выполняем финальную проверку на сравнение списков пользователей "до" и "после"
    Users after = app.db().users();
    assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));

  }

  private String findChangePasswordLink(List<MailMessage> mailMessages, UserData user) {
    String email = user.getEmail();
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
