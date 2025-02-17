package ru.job4j.service.file;

import org.springframework.stereotype.Service;
import ru.job4j.model.File;
import ru.job4j.repository.file.FileRepository;

import java.util.Collection;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    @Override
    public Collection<File> findAll() {
        return fileRepository.findAll();
    }
}
