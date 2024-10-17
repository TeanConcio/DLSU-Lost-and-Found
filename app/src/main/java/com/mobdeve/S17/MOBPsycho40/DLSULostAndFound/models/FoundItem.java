package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDate;

public class FoundItem extends Item {

    private LocalDate dateFound;

    public FoundItem(String name,
                     Category category,
                     String description,
                     String campus,
                     String location,
                     Integer image,
                     LocalDate dateFound) {
        super(name,
                ItemStatus.FOUND,
                category,
                description,
                campus,
                location,
                image);

        this.dateFound = dateFound;
    }



    /*
        Getters and Setters
    */

    public LocalDate getDateFound() { return this.dateFound; }
    public void setDateFound(LocalDate dateFound) { this.dateFound = dateFound; }
}
