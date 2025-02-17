package ru.job4j.service.file;

import ru.job4j.model.File;

import java.util.Collection;

public interface FileService {

    Collection<File> findAll();
}
