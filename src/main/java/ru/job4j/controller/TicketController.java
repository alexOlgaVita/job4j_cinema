package ru.job4j.controller;

import jakarta.servlet.http.HttpSession;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Ticket;
import ru.job4j.model.User;
import ru.job4j.service.film.FilmService;
import ru.job4j.service.filmsession.FilmSessionService;
import ru.job4j.service.hall.HallService;
import ru.job4j.service.ticket.TicketService;
import ru.job4j.service.user.UserService;

@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FilmService filmService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;
    private final UserService userService;

    public TicketController(TicketService ticketService, FilmService filmService, FilmSessionService filmSessionService,
                            HallService hallService, UserService userService) {
        this.ticketService = ticketService;
        this.filmService = filmService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
        this.userService = userService;
    }

    @GetMapping("/{filmSessionId}")
    public String getCreationPage(Model model, @PathVariable int filmSessionId, HttpSession session) {
        var filmSessionOptional = filmSessionService.findById(filmSessionId);
        var filmOptional = filmService.findById(filmSessionOptional.get().getFilmId());
        var hallOptional = hallService.findById(filmSessionOptional.get().getHallsId());
        if (filmSessionOptional.isEmpty()) {
            model.addAttribute("message", "Сессия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("filmSession", filmSessionOptional.get());
        model.addAttribute("film", filmOptional.get());
        model.addAttribute("hall", hallOptional.get());
        var user = (User) session.getAttribute("user");
        var userId = (user != null) ? user.getId() : -1;
        model.addAttribute("userId", userId);
        return "tickets/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Ticket ticket, Model model) {
        if (ticket.getUserId() == -1) {
            return "users/login";
        }
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            model.addAttribute("message",
                    "Не удалось приобрести билет на заданное место. Вероятно, оно уже занято. "
                            + "Перейдите на страницу бронирования билетов и попробуйте снова.");
            return "tickets/fail";
        }
        var filmSessionOptional = filmSessionService.findById(ticket.getSessionId());
        model.addAttribute("filmSession", filmSessionOptional.get());
        model.addAttribute("ticket", ticket);
        var userOptional = userService.findById(ticket.getUserId());
        model.addAttribute("userName", userOptional.get().getName());
        return "tickets/success";
    }
}
