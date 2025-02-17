package ru.job4j.service.ticket;


import ru.job4j.dto.TicketDto;
import ru.job4j.model.Ticket;

import java.util.Optional;

public interface TicketService {

    Optional<Ticket> save(Ticket ticket);

    Optional<TicketDto> findBySessionRowPlace(int sessionId, int rowNumber, int placeNumber);

    boolean deleteBySessionRowPlace(int sessionId, int rowNumber, int placeNumber);
}