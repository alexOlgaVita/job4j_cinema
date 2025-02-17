package ru.job4j.repository.film;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }

    @Override
    public Optional<Film> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id");
            query.addParameter("id", id);
            var film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public Optional<Film> save(Film film) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
                    VALUES (:name, :description, :year, :genre_id, :minimal_age, :duration_in_minutes, :file_id)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genre_id", film.getGenreId())
                    .addParameter("minimal_age", film.getMinimalAge())
                    .addParameter("duration_in_minutes", film.getDurationMinutes())
                    .addParameter("file_id", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return Optional.of(film);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        if (findById(id).isEmpty()) {
            return false;
        }
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM films WHERE id = :id");
            query.addParameter("id", id);
            query.executeUpdate();
            return true;
        }
    }
}
