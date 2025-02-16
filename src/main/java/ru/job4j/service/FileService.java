package ru.job4j.service;

import ru.job4j.model.File;

import java.util.Collection;

public interface FileService {

    Collection<File> findAll();
}
