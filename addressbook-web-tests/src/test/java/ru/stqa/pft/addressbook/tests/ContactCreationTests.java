package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    File photo = new File("src/test/resources/image.jpg"); //всем контактам одно фото пока
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withAddress(split[2])
              .withPhoneHome(split[3]).withPhoneMobile(split[4]).withPhoneWork(split[5])
              .withEmail(split[6]).withEmail2(split[7]).withEmail3(split[8]).withGroup("test1").withPhoto(photo)});
      line = reader.readLine();
    }
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

  @Test(dataProvider = "validContacts")
  public void testContactCreation(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
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

  @Test(enabled = false)
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath()); //C:\JavaTest\java_test\addressbook-web-tests\.
    File photo = new File("src/test/resources/image.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }

}
