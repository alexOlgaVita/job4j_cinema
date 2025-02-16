package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.dto.FilmSessionDto;
import ru.job4j.repository.FilmRepository;
import ru.job4j.repository.FilmSessionRepository;
import ru.job4j.repository.HallRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, FilmRepository filmRepository, HallRepository hallRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        return filmSessionRepository.findAll().stream().map(filmSession ->
                        new FilmSessionDto(filmSession.getId(),
                                filmSession.getFilmId(),
                                filmRepository.findAll().stream().filter(film -> film.getId() == filmSession.getFilmId())
                                        .findFirst().get().getName(),
                                filmSession.getHallsId(),
                                hallRepository.findAll().stream().filter(hall -> hall.getId() == filmSession.getHallsId())
                                        .findFirst().get().getName(),
                                filmSession.getStartTime(),
                                filmSession.getEndTime(),
                                filmSession.getPrice()))
                .toList();
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        return filmSessionRepository.findById(id).stream().map(filmSession ->
                        new FilmSessionDto(filmSession.getId(), filmSession.getFilmId(),
                                filmRepository.findAll().stream().filter(film -> film.getId() == filmSession.getFilmId())
                                        .findFirst().get().getName(),
                                filmSession.getHallsId(),
                                hallRepository.findAll().stream().filter(hall -> hall.getId() == filmSession.getHallsId())
                                        .findFirst().get().getName(),
                                filmSession.getStartTime(),
                                filmSession.getEndTime(),
                                filmSession.getPrice()))
                .findFirst();
    }
}
