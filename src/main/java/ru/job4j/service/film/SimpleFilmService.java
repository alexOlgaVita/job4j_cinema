package ru.job4j.service.film;

import org.springframework.stereotype.Service;
import ru.job4j.dto.FilmDto;
import ru.job4j.repository.file.FileRepository;
import ru.job4j.repository.film.FilmRepository;
import ru.job4j.repository.genre.GenreRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final FileRepository fileRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository genreRepository, FileRepository fileRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = genreRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Collection<FilmDto> findAll() {
        return filmRepository.findAll().stream().map(film ->
                        new FilmDto(film.getId(), film.getName(), film.getDescription(),
                                film.getYear(), film.getMinimalAge(), film.getDurationMinutes(),
                                genreRepository.findAll().stream().filter(genre -> genre.getId() == film.getGenreId())
                                        .findFirst().get().getName(),
                                fileRepository.findAll().stream().filter(file -> file.getId() == film.getFileId())
                                        .findFirst().map(e -> e.getPath() + "/" + e.getName()).get()))
                .toList();
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        return filmRepository.findById(id).stream().map(film ->
                        new FilmDto(film.getId(), film.getName(), film.getDescription(),
                                film.getYear(), film.getMinimalAge(), film.getDurationMinutes(),
                                genreRepository.findAll().stream().filter(genre -> genre.getId() == film.getGenreId())
                                        .findFirst().get().getName(),
                                fileRepository.findAll().stream().filter(file -> file.getId() == film.getFileId())
                                        .findFirst().map(e -> e.getPath() + "/" + e.getName()).get()))
                .findFirst();
    }
}
