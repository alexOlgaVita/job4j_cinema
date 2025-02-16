package ru.job4j.model;

import java.util.Map;
import java.util.Objects;

public class Film {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "year", "year",
            "genre_id", "genreId",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationMinutes",
            "file_id", "fileId"
    );

    private int id;
    private String name;
    private String description;
    private int year;
    private int genreId;
    private int minimalAge;
    private int durationMinutes;
    private int fileId;

    public Film() {
    }

    public Film(int id, String name, String description, int year, int genreId, int minimalAge,
                int durationMinutes, int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genreId = genreId;
        this.minimalAge = minimalAge;
        this.durationMinutes = durationMinutes;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public int getGenreId() {
        return genreId;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
