package ru.job4j.repository;


import ru.job4j.model.File;

import java.util.Collection;

public interface FileRepository {

    Collection<File> findAll();
}
