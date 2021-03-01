package ua.kiev.prog.services;

import ua.kiev.prog.model.Contact;
import ua.kiev.prog.model.Group;

import java.util.List;

public interface ContactService {
    void addContact(Contact contact);
    void addGroup(Group group);
    void deleteContact(long[] ids);
    void deleteGroup(long[] ids);
    List<Group> listGroups();
    List<Group> listGroups(int start, int count);
    List<Contact> listContacts(Group group, int start, int count);
    List<Contact> listContacts(Group group);
    long count();
    long count(Group group);
    Group findGroup(long id);
    List<Contact> searchContacts(String pattern);
    List<Group> searchGroups(String pattern);
    long countGroup();
}
