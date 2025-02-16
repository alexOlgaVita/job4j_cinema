package ru.job4j.dto;

import java.sql.Timestamp;

public class FilmSessionDto {

    private int id;
    private int filmId;
    private String filmName;
    private int hallsId;
    private String hallsName;
    private Timestamp startTime;
    private Timestamp endTime;
    private int price;

    public FilmSessionDto() {

    }

    public FilmSessionDto(int id, int filmId, String filmName, int hallsId, String hallsName, Timestamp startTime,
                          Timestamp endTime, int price) {
        this.id = id;
        this.filmId = filmId;
        this.filmName = filmName;
        this.hallsId = hallsId;
        this.hallsName = hallsName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getFilmId() {
        return filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public int getHallsId() {
        return hallsId;
    }

    public String getHallsName() {
        return hallsName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public void sethallsId(int hallsId) {
        this.hallsId = hallsId;
    }

    public void sethallsName(String hallsName) {
        this.hallsName = hallsName;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
