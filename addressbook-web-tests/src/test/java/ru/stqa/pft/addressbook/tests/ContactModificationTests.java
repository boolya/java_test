package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      if (app.db().groups().size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
      app.goTo().homePage();
      Groups groups = app.db().groups();
      File photo = new File("src/test/resources/image.jpg");
      ContactData newContact = new ContactData().
              withFirstName("First_name_Test").withLastName("Last_name_Test").
              withAddress("Address_Test").withPhoneHome("1 (234) 567").withPhoneMobile("+7987654321").withPhoneWork("98-76-54").
              withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withPhoto(photo)
              .inGroup(groups.iterator().next());
      app.contact().create(newContact);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/image.jpg");
    ContactData contact = new ContactData().
            withId(modifiedContact.getId()).withFirstName("First_name_Test").withLastName("Last_name_Test").
            withAddress("Address_TestMod").withPhoneHome("12-345-67").withPhoneMobile("+7-987654321").withPhoneWork("11(222)333").
            withEmail("test1@test.com").withEmail2("test2@test.com").withEmail3("test3@test.com").withPhoto(new File("src/test/resources/image.jpg"))
            .inGroup(groups.iterator().next());
    app.goTo().homePage();
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }

}
