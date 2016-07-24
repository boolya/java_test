package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.gotoHomePage();
    app.selectContact();
    app.modifySelectedContact();
    app.deleteSelectedContact();
    app.returnToHomePage();
  }

}
