package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.dto.TicketDto;
import ru.job4j.model.Ticket;
import ru.job4j.repository.FilmRepository;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;
import ru.job4j.repository.TicketRepository;

import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository, FilmSessionRepository filmSessionRepository,
                               FilmRepository filmRepository, HallRepository hallRepository) {
        this.ticketRepository = sql2oTicketRepository;
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<TicketDto> findBySessionRowPlace(int sessionId, int rowNumber, int placeNumber) {
        /* return ticketRepository.findBySessionRowPlace(sessionId, rowNumber, placeNumber); */
        return ticketRepository.findBySessionRowPlace(sessionId, rowNumber, placeNumber).stream().map(ticket ->
                new TicketDto(ticket.getId(),
                        filmRepository.findAll().stream().filter(film ->
                                film.getId() == filmSessionRepository.findAll().stream().filter(filmSession -> filmSession.getId() == ticket.getSessionId())
                                        .findFirst().get().getFilmId()
                        ).findFirst().get().getName(),
                        hallRepository.findAll().stream().filter(hall ->
                                hall.getId() == filmSessionRepository.findAll().stream().filter(filmSession -> filmSession.getId() == ticket.getSessionId())
                                        .findFirst().get().getHallsId()
                        ).findFirst().get().getName(),
                        filmSessionRepository.findAll().stream().filter(filmSession -> filmSession.getId() == ticket.getSessionId())
                                .findFirst().get().getStartTime(),
                        filmSessionRepository.findAll().stream().filter(filmSession -> filmSession.getId() == ticket.getSessionId())
                                .findFirst().get().getEndTime(),
                        ticket.getRowNumber(),
                        ticket.getPlaceNumber(),
                        filmSessionRepository.findAll().stream().filter(filmSession -> filmSession.getId() == ticket.getSessionId())
                                .findFirst().get().getPrice()
                )
        ).findFirst();
    }

    @Override
    public boolean deleteBySessionRowPlace(int sessionId, int rowNumber, int placeNumber) {
        return ticketRepository.deleteBySessionRowPlace(sessionId, rowNumber, placeNumber);
    }
}