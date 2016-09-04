package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
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

  public void resetPassword(UserData user) {
    selectUserById(user.getId());
    reset();
  }

  public int count() {
    return wd.findElements(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=']"))).size();
  }
}
