package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (:session_id, :row_number, :place_number, :user_id)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> findBySessionRowPlace(int sessionId, int rowNumber, int placeNumber) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE session_id = :sessionId"
                    + " AND row_number = :rowNumber AND place_number = :placeNumber");
            query.addParameter("sessionId", sessionId);
            query.addParameter("rowNumber", rowNumber);
            query.addParameter("placeNumber", placeNumber);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public boolean deleteBySessionRowPlace(int sessionId, int rowNumber, int placeNumber) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE session_id = :sessionId"
                    + " AND row_number = :rowNumber AND place_number = :placeNumber");
            query.addParameter("sessionId", sessionId);
            query.addParameter("rowNumber", rowNumber);
            query.addParameter("placeNumber", placeNumber);
            query.executeUpdate();
            return true;
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        if (findById(id).isEmpty()) {
            return false;
        }
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            query.executeUpdate();
            return true;
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            var candidate = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(candidate);
        }
    }
}
