package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    // Сначала проверяем, есть ли хоть один контакт, который можно было бы удалить.
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
      app.getContactHelper().createContact(new ContactData("First_name_Test", "Last_name_Test", "Address_Test", "1234567", "0987654321", "test1@test.com", "test2@test.com", "test1"));
    }
    // Если есть хотя бы один контакт для удаления, берём его и удаляем.
    app.getContactHelper().selectContact();
    app.getContactHelper().submitContactDeletionFromList();
    app.getContactHelper().returnToHomePage();
  }

}
