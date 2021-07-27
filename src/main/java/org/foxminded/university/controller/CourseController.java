package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.CourseDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/courses/")
@AllArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("courses", service.findAll());
        return "coursesList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<CourseDto> pageable = service.findAll(page);
        model.addAttribute("courses", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "coursesList";
    }

    @GetMapping(value = "/{id}")
    public String showGroupByID(@PathVariable Long id, Model model) {
        model.addAttribute("courses", service.findById(id));
        return "coursesList";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateAddressForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("course", service.findById(id));
            return "updateCourse";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/update/{id}")
    public String updateAddress(@ModelAttribute("course") CourseDto course) {
        service.updateCourse(course);
        return "redirect:/courses/all";
    }
}
