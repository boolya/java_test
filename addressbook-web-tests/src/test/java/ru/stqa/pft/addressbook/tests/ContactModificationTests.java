package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

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
              withAddress("Address_Test").withPhoneHome("1 (234) 567").withPhoneMobile("+7987654321").withPhoneWork("98-76-54").
              withEmail("test1@test.com").withEmail2("test2@test.com").withGroup("test1"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstName("First_name_Test").withLastName("Last_name_Test").
            withAddress("Address_TestMod").withPhoneHome("12-345-67").withPhoneMobile("+7-987654321").withPhoneWork("11(222)333").
            withEmail("test1@test.com").withEmail2("test2@test.com").withGroup(null);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
