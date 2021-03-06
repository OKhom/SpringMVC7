package ua.kiev.prog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.services.ContactService;

@Controller
public class GroupController {
    static final int DEFAULT_GROUP_ID = -1;
    private static final int ITEMS_PER_PAGE = 6;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @RequestMapping("/groups_list")
    public String groupsList(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        long totalCountGroup = contactService.countGroup();
        int start = page * ITEMS_PER_PAGE;
        long pageCount = (totalCountGroup / ITEMS_PER_PAGE) +
                ((totalCountGroup % ITEMS_PER_PAGE > 0) ? 1 : 0);

        model.addAttribute("groups", contactService.listGroups(start, ITEMS_PER_PAGE));
        model.addAttribute("pages", pageCount);
        return "groups_list";
    }

    @RequestMapping(value="/group/add",
            method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name) {
        contactService.addGroup(new Group(name));
        return "redirect:/";
    }

    @RequestMapping("/group/{id}")
    public String listGroup(@PathVariable(value = "id") long groupId,
                            Model model) {
        Group group = (groupId != DEFAULT_GROUP_ID) ?
                contactService.findGroup(groupId) : null;

        model.addAttribute("groups", contactService.listGroups());
        model.addAttribute("contacts", contactService.listContacts(group));

        return "index";
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(
            @RequestParam(value = "toDelete[]", required = false)
                    long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            contactService.deleteGroup(toDelete);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/group/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", contactService.searchGroups(pattern));
        return "groups_list";
    }
}
