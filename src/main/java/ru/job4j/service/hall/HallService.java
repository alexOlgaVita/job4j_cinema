package ru.job4j.service.hall;

import ru.job4j.model.Hall;

import java.util.Collection;
import java.util.Optional;

public interface HallService {

    Collection<Hall> findAll();

    Optional<Hall> findById(int id);
}