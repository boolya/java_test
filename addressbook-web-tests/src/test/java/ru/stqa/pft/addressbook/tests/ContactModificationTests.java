package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Created by Оля on 24.07.2016.
 */
public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    // Сначала проверяем, есть ли хоть один контакт, который можно было бы изменить.
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      // Если ни одного контакта нет - создаём,
      // предварительно проверив, есть ли хоть одна группа (создаём и новую группу, если нет ни одной).
      // На данном этапе считаем, что все группы создаются и модифицируются с названием "test1".
      app.getNavigationHelper().gotoGroupPage();
      if (!app.getGroupHelper().isThereAGroup()) {
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      app.getNavigationHelper().gotoHomePage();
      app.getContactHelper().createContact(new ContactData("First_name_Test", "Last_name_Test", "Address_Test", "1234567", "0987654321", "test1@test.com", "test2@test.com", "test1"), true);
    }
    // Если есть хотя бы один контакт в списке, берём его для изменения его данных.
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("First_name_TestMod", "Last_name_TestMod", "Address_TestMod", "1234567", "0987654321", "test1@test.com", "test2@test.com", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}
