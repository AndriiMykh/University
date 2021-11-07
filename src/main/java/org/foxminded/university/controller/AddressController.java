package org.foxminded.university.controller;

import lombok.AllArgsConstructor;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/addresses/")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("addresses", service.findAll());
        return "addressesList";
    }

    @GetMapping(value = "/pageable")
    public String showAllPageable(Model model, @RequestParam("pageNumber") int pageNumber,
                                  @RequestParam("itemsPerPage") int itemsPerPage) {
        Page page = new Page( pageNumber, itemsPerPage);
        Pageable<AddressDto> pageable = service.findAll(page);
        model.addAttribute("addresses", pageable.getItems());
        model.addAttribute("page", page.getPageNumber()+1);
        return "addressesList";
    }

    @GetMapping(value = "/{id}")
    public String showAddressByID(@PathVariable Long id, Model model) {
        model.addAttribute("addresses", service.findById(id));
        return "addressesList";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateAddressForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("address", service.findById(id));
            return "updateAddress";
        } catch (ServiceException e) {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/update/{id}")
    public String updateAddress(@ModelAttribute("address") AddressDto address) {
        service.updateAddress(address);
        return "redirect:/addresses/all";
    }
}
