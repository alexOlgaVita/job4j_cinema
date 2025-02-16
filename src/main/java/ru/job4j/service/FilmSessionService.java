package ru.job4j.service;

import ru.job4j.dto.FilmSessionDto;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {

    Collection<FilmSessionDto> findAll();

    Optional<FilmSessionDto> findById(int id);
}
