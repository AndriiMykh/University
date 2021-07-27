package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students/")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping(value = "/registration")
    public String home(Model model){
        model.addAttribute("student", StudentDto.builder()
                .withAddress(AddressDto.builder().build())
                .withGroup(GroupDto.builder().build())
                .build());
        return "studentRegistration";
    }

    @GetMapping(value = "/login")
    public String login(Model model){
        return "studentLogin";
    }

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("students", service.findAll());
        return "studentsList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<StudentDto> pageable = service.findAll(page);
        model.addAttribute("students", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "studentsList";
    }

    @GetMapping("/delete/{id}")
    public String showAll(@PathVariable Long id) {
        service.deleteStudentById(id);
        return "redirect:/students/all";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateStudentForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("student", service.findById(id));
            return "updateStudent";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/{id}")
    public String showById(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("student", service.findById(id));
            return "studentProfile";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/login")
    public String authenticateStudent( @RequestParam(value = "login") String login,
                                       @RequestParam(value = "password") String password,
                                       Model model){
        String message;
        try {
            service.authenticateStudent(login, password);
            return "redirect:/";
        }catch (ServiceException e){
            message = e.getMessage();
            model.addAttribute("message", message);
            return "studentLogin";
        }
    }

    @PostMapping("/register")
    public String saveStudent(@ModelAttribute("student") StudentDto student){
        service.registerStudent(student);
        return "redirect:/";
    }

    @PostMapping(value = "/update/{id}")
    public String updateStudent(@ModelAttribute("student") StudentDto student) {
        service.updateStudent(student);
        return "redirect:/students/all";
    }

}
