package ru.job4j.repository.film;


import ru.job4j.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Collection<Film> findAll();

    Optional<Film> findById(int id);

    Optional<Film> save(Film film);

    boolean deleteById(int id);
}
