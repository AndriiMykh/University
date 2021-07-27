package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.ScheduleDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/schedules/")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("schedules", service.findAll());
        return "schedulesList";
    }


    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<ScheduleDto> pageable = service.findAll(page);
        model.addAttribute("schedules", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "schedulesList";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateScheduleForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("schedule", service.findById(id));
            return "updateSchedule";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @GetMapping(value = "/scheduleForToday/student/{id}")
    public String showScheduleForStudentForToday(@PathVariable Long id, Model model) {
        model.addAttribute("schedules", service.getScheduleForStudentForToday(id));
        return "schedulesList";
    }

    @GetMapping(value = "/scheduleForToday/teacher/{id}")
    public String showScheduleForTeacherForToday(@PathVariable Long id, Model model) {
        model.addAttribute("schedules", service.getScheduleForTeacherForToday(id));
        return "schedulesList";
    }

    @GetMapping(value = "/scheduleForMonth/teacher/{id}")
    public String showScheduleForTeacherForMonth(@PathVariable Long id, Model model) {
        model.addAttribute("schedules", service.getScheduleForTeacherForMonth(id));
        return "schedulesList";
    }

    @GetMapping(value = "/scheduleForMonth/student/{id}")
    public String showScheduleForStudentForMonth(@PathVariable Long id, Model model) {
        model.addAttribute("schedules", service.getScheduleForStudentForMonth(id));
        return "schedulesList";
    }

    @PostMapping(value = "/update/{id}")
    public String updateGroup(@ModelAttribute("schedule") ScheduleDto schedule) {
        service.updateSchedule(schedule);
        return "redirect:/schedules/all";
    }
}
