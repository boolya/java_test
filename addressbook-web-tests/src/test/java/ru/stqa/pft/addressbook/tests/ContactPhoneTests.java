package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().create(new GroupData().withName("test1"));
      }
      app.goTo().homePage();
      app.contact().create(new ContactData().
              withFirstName("First_name_Test").withLastName("Last_name_Test").
              withAddress("Address_Test").withPhoneHome("1 (234) 567").withPhoneMobile("+7987654321").withPhoneWork("98-76-54").
              withEmail1("test1@test.com").withEmail2("test2@test.com").withGroup("test1"));
    }
  }


  @Test
  public void testContactPhone() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getPhoneHome(), equalTo(cleaned(contactInfoFromEditForm.getPhoneHome())));
    assertThat(contact.getPhoneMobile(), equalTo(cleaned(contactInfoFromEditForm.getPhoneMobile())));
    assertThat(contact.getPhoneWork(), equalTo(cleaned(contactInfoFromEditForm.getPhoneWork())));
  }

  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }


}
