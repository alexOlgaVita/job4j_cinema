package ru.job4j.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.service.filmsession.FilmSessionService;

@ThreadSafe
@Controller
@RequestMapping("/filmSessions")
public class FilmSessionsController {

    private final FilmSessionService filmSessionService;

    public FilmSessionsController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "filmSessions/list";
    }
}
