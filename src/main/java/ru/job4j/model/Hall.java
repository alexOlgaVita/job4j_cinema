package ru.job4j.model;

import java.util.Map;
import java.util.Objects;

public class Hall {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "row_count", "rowCount",
            "place_count", "placeCount",
            "description", "description"
    );

    private int id;
    private String name;
    private int rowCount;
    private int placeCount;
    private String description;

    public Hall(int id, String name, int rowCount, int placeCount, String description) {
        this.id = id;
        this.name = name;
        this.rowCount = rowCount;
        this.placeCount = placeCount;
        this.description = description;
    }

    public Hall() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return id == hall.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
