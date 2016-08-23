package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    // Сначала проверяем, есть ли хоть один контакт, который можно было бы удалить.
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      // Если ни одного контакта нет - создаём,
      // предварительно проверив, есть ли хоть одна группа (создаём и новую группу, если нет ни одной).
      // На данном этапе считаем, что все группы создаются и модифицируются с названием "test1".
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
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
  public void testContactModification() {
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstName("First_name_Test").withLastName("Last_name_Test").
            withAddress("Address_TestMod").withPhoneHome("1234567").withPhoneMobile("0987654321").
            withEmail1("test1@test.com").withEmail2("test2@test.com").withGroup(null);
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);

  }

}
