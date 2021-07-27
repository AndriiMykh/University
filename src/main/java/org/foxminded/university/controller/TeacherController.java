package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Teacher;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teachers/")
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("teacher", Teacher.builder().withAddress(Address.builder().build()).build());
        return "teacherRegistration";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "teacherLogin";
    }

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachersList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<TeacherDto> pageable = teacherService.findAll(page);
        model.addAttribute("teachers", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "teachersList";
    }

    @GetMapping("/delete/{id}")
    public String showAll(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers/all";
    }

    @GetMapping(value = "/{id}")
    public String showById(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("teacher", teacherService.findById(id));
            return "teacherProfile";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateTeacherForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("teacher", teacherService.findById(id));
            return "updateTeacher";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/login")
    public String authenticateTeacher(@RequestParam(value = "login") String login,
                                      @RequestParam(value = "password") String password,
                                      Model model) {
        String message;
        try {
            teacherService.authenticateTeacher(login, password);
            return "redirect:/";
        } catch (ServiceException e) {
            message = e.getMessage();
            model.addAttribute("message", message);
            return "teacherLogin";
        }
    }

    @PostMapping("/register")
    public String saveTeacher(@ModelAttribute("teacher") TeacherDto teacher, BindingResult result, Model model) {
        teacherService.registerTeacher(teacher);
        return "redirect:/";
    }

    @PostMapping(value = "/update/{id}")
    public String updateTeacher(@ModelAttribute("teacher") TeacherDto teacher) {
        teacherService.updateTeacher(teacher);
        return "redirect:/teachers/all";
    }
}
