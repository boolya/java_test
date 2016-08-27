package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDataTests extends TestBase {

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
              withAddress("Address_Test").withPhoneHome("1 (234) 567").withPhoneWork("98-76-54").
              withEmail("test1@test.com").withEmail3("test3@test.com").withGroup("test1"));
    }
  }


  @Test
  public void testContactPhonesAddressEmails() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }


  @Test
  public void testContactViewPageData() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromViewPage = app.contact().infoFromViewPage(contact);
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contactInfoFromViewPage.getContactInfo(), equalTo(mergeContactInfo(contactInfoFromEditForm)));
  }

  private String mergeContactInfo(ContactData contact) {
    String result = "";
    if (contact.getFirstName().trim().length() != 0) {
      result += contact.getFirstName();
    }
    if (contact.getLastName().trim().length() != 0) {
      result += " " + contact.getLastName() + "\n";
    }
    if (contact.getAddress().trim().length() != 0) {
      result += contact.getAddress() + "\n\n";
    }
    if (contact.getPhoneHome().trim().length() != 0) {
      result += "H: " + contact.getPhoneHome() + "\n";
    }
    if (contact.getPhoneMobile().trim().length() != 0) {
      result += "M: " + contact.getPhoneMobile() + "\n";
    }
    if (contact.getPhoneWork().trim().length() != 0) {
      result += "W: " + contact.getPhoneWork() + "\n\n";
    }
    if (contact.getEmail().trim().length() != 0) {
      result += contact.getEmail() + " (www." + contact.getEmail().substring(contact.getEmail().indexOf('@') + 1) + ")" + "\n";
    }
    if (contact.getEmail2().trim().length() != 0) {
      result += contact.getEmail2() + " (www." + contact.getEmail2().substring(contact.getEmail2().indexOf('@') + 1) + ")" + "\n";
    }
    if (contact.getEmail3().trim().length() != 0) {
      result += contact.getEmail3() + " (www." + contact.getEmail3().substring(contact.getEmail3().indexOf('@') + 1) + ")" + "\n";
    }
    //Пока считаем, что все контакты создаются в группе "test1"
    if (result.trim().length() != 0) {
      result += "\n\nMember of: test1";
    }
    return result;
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getPhoneHome(), contact.getPhoneMobile(), contact.getPhoneWork()).
            stream().filter((s) -> !s.equals("")).
            map(ContactDataTests::cleaned).
            collect(Collectors.joining("\n"));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).
            stream().filter((s) -> !s.equals("")).
            collect(Collectors.joining("\n"));
  }

}
