package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Person;
import ru.kata.spring.boot_security.demo.services.PersonServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PersonServiceImpl personService;

    @Autowired
    public AdminController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String getAllUsers(Model model, Principal principal){
        model.addAttribute("persons", personService.findAll());
        Person person = personService.findByName(principal.getName());
        model.addAttribute("person", person);
        return "/allUsers";
    }
    @GetMapping("/{id}")
    public String read(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personService.getUser(id));
        return "show";
    }
    @GetMapping("/new")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "/registration";
    }
    @PostMapping()
    public String performRegistration(@ModelAttribute("person") Person person) {
        personService.save(person);
        return "redirect:/admin";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", personService.getUser(id));
        return "updateUser";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") long id) {
        personService.save(person);
        return "redirect:/admin";
    }
    // Удаление пользователя
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        personService.delete(personService.getUser(id).getId());
        return "redirect:/admin";
    }
}
