package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.mantis.model.UserData;

public class UserHelper extends HelperBase {

  public UserHelper(ApplicationManager app) {
    super(app);
  }

  public void loginAsAdmin() {
    type(By.name("username"), app.getProperty("web.adminLogin"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value='Войти']"));
  }

  private void selectUserById(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id))).click();
  }

  public void reset() {
    click(By.cssSelector("input[value='Сбросить пароль']"));
  }

  public void resetPasswordByAdmin(UserData user) {
    selectUserById(user.getId());
    reset();
  }

  public void changePassword(String changePasswordLink, UserData user, String newPassword) {
    wd.get(changePasswordLink);
    type(By.name("password"), newPassword);
    type(By.name("password_confirm"), newPassword);
    click(By.cssSelector("input[value='Изменить учетную запись']"));
  }

  public void newUserCreate(UserData newUser) {
    click(By.cssSelector("input[value='Создать учетную запись']"));
    fillUserForm(newUser);
  }

  public void fillUserForm(UserData newUser) {
    type(By.name("username"), newUser.getUsername());
    type(By.name("email"), newUser.getEmail());
    new Select(wd.findElement(By.name("access_level"))).selectByVisibleText("участник");
    click(By.cssSelector("input[value='Создать']"));
  }

  public void logout() {
    wd.findElement(By.cssSelector("a[href='/mantisbt-1.3.0/logout_page.php']")).click();
  }
}

