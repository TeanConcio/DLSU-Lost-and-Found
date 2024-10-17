package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDate;

public class LostItem extends Item {

    private LocalDate dateLost;

    public LostItem(String name,
                    Category category,
                    String description,
                    String campus,
                    String location,
                    Integer image,
                    LocalDate dateLost) {
        super(name,
                ItemStatus.LOST,
                category,
                description,
                campus,
                location,
                image);

        this.dateLost = dateLost;
    }



    /*
        Getters and Setters
    */

    public LocalDate getDateLost() {
        return this.dateLost;
    }
    public void setDateLost(LocalDate dateLost) {
        this.dateLost = dateLost;
    }
}
