package ru.job4j.service.genre;

import org.springframework.stereotype.Service;
import ru.job4j.model.Genre;
import ru.job4j.repository.genre.GenreRepository;

import java.util.Collection;

@Service
public class SimpleGenreService implements GenreService {

    private final GenreRepository genreRepository;

    public SimpleGenreService(GenreRepository sql2oGenreRepository) {
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Collection<Genre> findAll() {
        return genreRepository.findAll();
    }
}
