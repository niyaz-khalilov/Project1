package ru.khalilov.project1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping("/library")
    public String mainPage () {
        return "/main/page";
    }


}
