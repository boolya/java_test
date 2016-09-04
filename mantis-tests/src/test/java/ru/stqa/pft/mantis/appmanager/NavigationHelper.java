package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

  public void loginPage() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
  }

  public void manageMainPage() {
    wd.findElement(By.cssSelector("a[href='/mantisbt-1.3.0/manage_overview_page.php']")).click();
  }

  public void manageUserPage() {
    wd.findElement(By.cssSelector("a[href='/mantisbt-1.3.0/manage_user_page.php']")).click();
  }

}
