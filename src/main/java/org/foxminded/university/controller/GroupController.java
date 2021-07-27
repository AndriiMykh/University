package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/groups/")
@AllArgsConstructor
public class GroupController {

    private final GroupService service;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("groups", service.findAll());
        return "groupsList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<GroupDto> pageable = service.findAll(page);
        model.addAttribute("groups", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "groupsList";
    }

    @GetMapping(value = "/{id}")
    public String showGroupByID(@PathVariable Long id, Model model) {
        model.addAttribute("groups", service.findById(id));
        return "groupsList";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateGroupForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("group", service.findById(id));
            return "updateGroup";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/update/{id}")
    public String updateGroup(@ModelAttribute("group") GroupDto group) {
        service.updateGroup(group);
        return "redirect:/groups/all";
    }
}
