package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.LessonDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lessons/")
@AllArgsConstructor
public class LessonController {

    private final LessonService service;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("lessons", service.findAll());
        return "lessonsList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<LessonDto> pageable = service.findAll(page);
        model.addAttribute("lessons", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "lessonsList";
    }

    @GetMapping(value = "/{id}")
    public String showAddressByID(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", service.findById(id));
        return "lessonProfile";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateGroupForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("lesson", service.findById(id));
            return "updateLesson";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/update/{id}")
    public String updateGroup(@ModelAttribute("lesson") LessonDto lesson) {
        service.updateLesson(lesson);
        return "redirect:/lessons/all";
    }
}
