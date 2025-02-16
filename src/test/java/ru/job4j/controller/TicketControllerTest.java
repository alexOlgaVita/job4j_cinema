package ru.job4j.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dto.FilmDto;
import ru.job4j.dto.FilmSessionDto;
import ru.job4j.model.Hall;
import ru.job4j.model.Ticket;
import ru.job4j.model.User;
import ru.job4j.service.*;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerTest {

    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private FilmService filmService;
    private HallService hallService;
    private UserService userService;
    private TicketController ticketController;
    private MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void initServices() {
        mockMvc = MockMvcBuilders.standaloneSetup(mockMvc).build();
        session = new MockHttpSession();

        ticketService = mock(TicketService.class);
        filmSessionService = mock(FilmSessionService.class);
        filmService = mock(FilmService.class);
        hallService = mock(HallService.class);
        userService = mock(UserService.class);
        ticketController = new TicketController(ticketService, filmService, filmSessionService,
                hallService, userService);
    }

    @Test
    public void whenRequestTicketCreationPageThenGetPage() {
        var filmSessionDto = new FilmSessionDto(1, 1, "Силиконовая долина", 1, "Зал № 1",
                Timestamp.valueOf("2025-08-07 06:00:01"), Timestamp.valueOf("2025-08-07 09:00:01"), 120);
        var filmDto = new FilmDto(1, "Силиконовая долина", "Сериал про стартаперов",
                2025, 18, 120, "Комедия", "cilicon/cilicon.jpg");
        var hall = new Hall(1, "Зал №1", 10, 15, "VIP-зал");

        when(filmSessionService.findById(filmSessionDto.getId())).thenReturn(Optional.of(filmSessionDto));
        when(filmService.findById(filmSessionDto.getFilmId())).thenReturn(Optional.of(filmDto));
        when(hallService.findById(filmSessionDto.getFilmId())).thenReturn(Optional.of(hall));

        var model = new ConcurrentModel();
        var view = ticketController.getCreationPage(model, filmSessionDto.getId(), session);
        var actualFilmSession = model.getAttribute("filmSession");
        var actualFilm = model.getAttribute("film");
        var actualHallFilm = model.getAttribute("hall");

        assertThat(view).isEqualTo("tickets/create");
        assertThat(actualFilmSession).isEqualTo(filmSessionDto);
        assertThat(actualFilm).isEqualTo(filmDto);
        assertThat(actualHallFilm).isEqualTo(hall);
    }

    @Test
    public void whenPostTicketWithUniqRowPlaceThenSaveDataAndRedirectToSuccessPage() throws Exception {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        var filmSessionDto = new FilmSessionDto(1, 1, "Силиконовая долина", 1, "Зал № 1",
                Timestamp.valueOf("2025-08-07 06:00:01"), Timestamp.valueOf("2025-08-07 09:00:01"), 120);
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);

        when(filmSessionService.findById(ticket.getSessionId())).thenReturn(Optional.of(filmSessionDto));
        when(userService.findById(ticket.getUserId())).thenReturn(Optional.of(user));
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenPostTicketWithNotUniqRowPlaceThenNotSaveDataAndRedirectToFailPage() throws Exception {
        var ticket = new Ticket(1, 1, 10, 15, 1);
        var filmSessionDto = new FilmSessionDto(1, 1, "Силиконовая долина", 1, "Зал № 1",
                Timestamp.valueOf("2025-08-07 06:00:01"), Timestamp.valueOf("2025-08-07 09:00:01"), 120);
        var user = new User(1, "olga@milo.ru", "olga", "olgaPass");
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);

        when(filmSessionService.findById(ticket.getSessionId())).thenReturn(Optional.of(filmSessionDto));
        when(userService.findById(ticket.getUserId())).thenReturn(Optional.of(user));
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);
        var actualTicket = ticketArgumentCaptor.getValue();

        assertThat(view).isEqualTo("tickets/fail");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenPostTicketWithoutAuthorizationThenNotSaveDataAndRedirectToRegistrationPage() throws Exception {
        var ticket = new Ticket(1, 1, 10, 15, -1);
        var filmSessionDto = new FilmSessionDto(1, 1, "Силиконовая долина", 1, "Зал № 1",
                Timestamp.valueOf("2025-08-07 06:00:01"), Timestamp.valueOf("2025-08-07 09:00:01"), 120);
        var user = new User(-1, "olga@milo.ru", "olga", "olgaPass");
        var ticketArgumentCaptor = ArgumentCaptor.forClass(Ticket.class);

        when(filmSessionService.findById(ticket.getSessionId())).thenReturn(Optional.of(filmSessionDto));
        when(userService.findById(ticket.getUserId())).thenReturn(Optional.of(user));
        when(ticketService.save(ticketArgumentCaptor.capture())).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var view = ticketController.create(ticket, model);

        assertThat(view).isEqualTo("users/register");
    }
}