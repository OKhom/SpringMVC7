package ua.kiev.prog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.services.ContactService;

import javax.annotation.PostConstruct;

@Component
public class TestDataBean {
    @Autowired
    private ContactService contactService;

    @PostConstruct
    public void fillData() {
        Group group = new Group("Test");
        contactService.addGroup(group);

        Contact contact;
        for (int i = 0; i < 25; i++) {
            contact = new Contact(null, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
            contactService.addContact(contact);
        }
        for (int i = 0; i < 7; i++) {
            contact = new Contact(group, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            contactService.addContact(contact);
        }

        for (int i = 0; i < 4; i++) {
            group = new Group("Group" + (i + 1));
            contactService.addGroup(group);
        }

        for (int i = 7; i < 10; i++) {
            contact = new Contact(group, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            contactService.addContact(contact);
        }

        for (int i = 0; i < 4; i++) {
            group = new Group("TestGroup" + (i + 5));
            contactService.addGroup(group);
        }

        for (int i = 10; i < 14; i++) {
            contact = new Contact(group, "Other" + i, "OtherSurname" + i, "7654321" + i, "user" + i + "@other.com");
            contactService.addContact(contact);
        }
    }
}
