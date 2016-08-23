package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    // Перед созданием нового контакта проверяем, есть ли хоть одна группа. Если нет - создаём.
    // На данном этапе считаем, что все группы создаются и модифицируются с названием "test1".
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testContactCreation() {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData().
            withFirstName("First_name_Test").withLastName("Last_name_Test").
            withAddress("Address_Test").withPhoneHome("1234567").withPhoneMobile("0987654321").
            withEmail1("test1@test.com").withEmail2("test2@test.com").withGroup("test1");
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
