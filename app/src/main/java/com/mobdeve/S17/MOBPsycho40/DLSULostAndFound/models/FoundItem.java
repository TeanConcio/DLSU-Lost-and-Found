package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDateTime;

public class FoundItem extends Item {

    private LocalDateTime dateFound;

    public FoundItem(String name,
                     String category,
                     String description,
                     String campus,
                     String location,
                     Integer image,
                     LocalDateTime dateFound) {
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

    public LocalDateTime getDateFound() { return this.dateFound; }
    public void setDateFound(LocalDateTime dateFound) { this.dateFound = dateFound; }
}
