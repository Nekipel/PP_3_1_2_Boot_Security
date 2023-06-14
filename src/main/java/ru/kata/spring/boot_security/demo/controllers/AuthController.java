package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class AuthController {

    private final PersonServiceImpl personService;

    @Autowired
    public AuthController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("person", personService.findByName(username));
        return "/user";
    }
}
