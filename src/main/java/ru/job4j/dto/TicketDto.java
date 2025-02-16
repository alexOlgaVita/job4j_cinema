package ru.job4j.dto;

import java.sql.Timestamp;

public class TicketDto {

    private int id;
    private String filmName;
    private String hallName;
    private Timestamp startTime;
    private Timestamp endTime;
    private int rowNumber;
    private int placeNumber;

    private int price;

    public TicketDto() {
    }

    public TicketDto(int id, String filmName, String hallName, Timestamp startTime, Timestamp endTime,
                     int rowNumber, int placeNumber, int price) {
        this.id = id;
        this.filmName = filmName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getHallName() {
        return hallName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
