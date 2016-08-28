package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/image.jpg"); //всем контактам одно фото пока
    list.add(new Object[] {new ContactData().withFirstName("First_name_Test1").withLastName("Last_name_Test1").
            withAddress("Address_Test1").withPhoneHome("123-123-1").withPhoneMobile("111-222-333-1").withPhoneWork("987-987-1").
            withEmail("testemail1_1@test.com").withEmail2("testemail2_1@test.com").withEmail3("testemail3_1@test.com")
            .withGroup("test1").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstName("First_name_Test2").withLastName("Last_name_Test2").
            withAddress("Address_Test2").withPhoneHome("123-123-2").withPhoneMobile("111-222-333-2").withPhoneWork("987-987-2").
            withEmail("testemail1_2@test.com").withEmail2("testemail2_2@test.com").withEmail3("testemail3_2@test.com")
            .withGroup("test1").withPhoto(photo)});
    list.add(new Object[] {new ContactData().withFirstName("First_name_Test3").withLastName("Last_name_Test3").
            withAddress("Address_Test3").withPhoneHome("123-123-3").withPhoneMobile("111-222-333-3").withPhoneWork("987-987-3").
            withEmail("testemail1_3@test.com").withEmail2("testemail2_3@test.com").withEmail3("testemail3_3@test.com")
            .withGroup("test1").withPhoto(photo)});
    return list.iterator();
  }

  @BeforeMethod
  public void ensurePreconditions() {
    // Перед созданием нового контакта проверяем, есть ли хоть одна группа. Если нет - создаём.
    // На данном этапе считаем, что все группы создаются и модифицируются с названием "test1".
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test (enabled = false)
  public void testBadContactCreation() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData().
            withFirstName("First_name_Test'").withLastName("Last_name_Test").
            withAddress("Address_Test").withPhoneHome("1 (234) 567").withPhoneMobile("+7987654321").withPhoneWork("98-76-54").
            withEmail("test1@test.com").withEmail2("test2@test.com").withGroup("test1");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

  @Test (enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath()); //C:\JavaTest\java_test\addressbook-web-tests\.
    File photo = new File("src/test/resources/image.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

}
