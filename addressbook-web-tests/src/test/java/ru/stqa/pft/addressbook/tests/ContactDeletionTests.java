package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    // Сначала проверяем, есть ли хоть один контакт, который можно было бы удалить.
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      // Если ни одного контакта нет - создаём,
      // предварительно проверив, есть ли хоть одна группа (создаём и новую группу, если нет ни одной).
      // На данном этапе считаем, что все группы создаются и модифицируются с названием "test1".
      app.goTo().groupPage();
      if (app.group().list().size() == 0) {
        app.group().create(new GroupData().withName("test1"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().
              withFirstName("First_name_Test").withLastName("Last_name_Test").
              withAddress("Address_Test").withPhoneHome("1234567").withPhoneMobile("0987654321").
              withEmail1("test1@test.com").withEmail2("test2@test.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactDeletion() {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(index);
    Assert.assertEquals(before, after);
  }

}
