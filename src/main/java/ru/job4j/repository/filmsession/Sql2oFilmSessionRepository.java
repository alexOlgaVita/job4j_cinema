package ru.job4j.repository.filmsession;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions");
            return query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);

        }
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id");
            query.addParameter("id", id);
            var filmSessions = query.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSessions);
        }
    }

    @Override
    public Optional<FilmSession> save(FilmSession filmSession) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO film_sessions(film_id, halls_id, start_time, end_time, price)
                    VALUES (:film_id, :halls_id, :start_time, :end_time, :price)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("film_id", filmSession.getFilmId())
                    .addParameter("halls_id", filmSession.getHallsId())
                    .addParameter("start_time", filmSession.getStartTime())
                    .addParameter("end_time", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            filmSession.setId(generatedId);
            return Optional.of(filmSession);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
