package ru.khalilov.project1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khalilov.project1.dao.PersonDAO;
import ru.khalilov.project1.models.Book;
import ru.khalilov.project1.models.Person;
import ru.khalilov.project1.util.PersonUtil;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonUtil personUtil;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonUtil personUtil) {
        this.personDAO = personDAO;
        this.personUtil = personUtil;
    }

    @GetMapping()
    public String peoplePage(Model model) {
        List<Person> people = personDAO.showAllPerson();
        model.addAttribute("people", people);
        return "people/index";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPerson(id));
        List<Book> books = personDAO.getBooks(id);
        if (books == null || books.size()==0) {
            model.addAttribute("notBook", books);
        } else {
            model.addAttribute("listOfBook", books);
        }
        return "people/show";
    }

    @GetMapping("/new")
    public String personAddPage (@ModelAttribute ("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String addPerson (@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personUtil.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson (@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String editPerson(@ModelAttribute @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        personUtil.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        person.setPersonId(id);
        personDAO.update(person);
        return "redirect:/people";
    }
}
