package ru.job4j.repository.ticket;


import ru.job4j.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findBySessionRowPlace(int sessionId, int rowNumber, int placeNumber);

    boolean deleteBySessionRowPlace(int sessionId, int rowNumber, int placeNumber);

    Collection<Ticket> findAll();

    boolean deleteById(int id);

    Optional<Ticket> findById(int id);
}
