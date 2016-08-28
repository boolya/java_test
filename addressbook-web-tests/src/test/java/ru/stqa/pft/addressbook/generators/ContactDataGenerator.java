package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);

    List<ContactData> contacts = generateContacts(count);
    save(contacts, file);
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
              contact.getFirstName(), contact.getLastName(), contact.getAddress(),
              contact.getPhoneHome(), contact.getPhoneMobile(), contact.getPhoneWork(),
              contact.getEmail(), contact.getEmail2(), contact.getEmail3()));
    }
    writer.close();
  }

  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstName(String.format("FirstName %s", i))
              .withLastName(String.format("LastName %s", i))
              .withAddress(String.format("Address %s", i))
              .withPhoneHome(String.format("123-123-%s", i))
              .withPhoneMobile(String.format("111-222-333-%s", i))
              .withPhoneWork(String.format("987-987-%s", i))
              .withEmail(String.format("testemail1_%s@test.com", i))
              .withEmail2(String.format("testemail2_%s@test.com", i))
              .withEmail3(String.format("testemail3_%s@test.com", i))
      );
    }
    return contacts;
  }

}


