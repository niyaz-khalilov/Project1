package ru.khalilov.project1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.khalilov.project1.dao.BookDAO;
import ru.khalilov.project1.dao.PersonDAO;
import ru.khalilov.project1.models.Book;
import ru.khalilov.project1.models.Person;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String booksPage(Model model) {
        List<Book> books = bookDAO.showAllBooks();
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person personIn) {
        model.addAttribute("book", bookDAO.showBook(bookId));
        Person person = bookDAO.showOwner(bookId);
        if (person == null) {
            model.addAttribute("people", personDAO.showAllPerson());
        } else {
            model.addAttribute("owner", person);
        }
        return "books/show";
    }

    @GetMapping("/new")
    public String bookAddPage (@ModelAttribute("book") Book book) {
        return "books/new";
    }

  @PostMapping()
    public String addBook (@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
              return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook (@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }


    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/edit";
    }


    @PatchMapping("/{id}")
    public String editBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        book.setBookId(id);
        bookDAO.update(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignBookToPeople(@ModelAttribute Person person, @PathVariable("id") int bookId) {
        bookDAO.assignBook(person.getPersonId(), bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String releaseBookFromPeople(@PathVariable("id") int bookId) {
        bookDAO.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }
}
