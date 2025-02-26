package ru.job4j.repository.filmsession;


import ru.job4j.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionRepository {

    Collection<FilmSession> findAll();

    Optional<FilmSession> findById(int id);

    Optional<FilmSession> save(FilmSession filmSession);
}
