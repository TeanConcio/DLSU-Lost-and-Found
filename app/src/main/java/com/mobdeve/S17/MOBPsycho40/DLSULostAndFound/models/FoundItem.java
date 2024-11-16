package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDate;

public class FoundItem extends Item {

    private String dateFound;

    public FoundItem(String name,
                     Category category,
                     String description,
                     String campus,
                     String location,
                     Integer image,
                     String dateFound) {
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

    public String getDateFound() { return this.dateFound; }
    public void setDateFound(String dateFound) { this.dateFound = dateFound; }
}
