package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.util.Date;

public class FoundItem extends Item {

    private Date dateFound;

    public FoundItem(String name,
                     String category,
                     String description,
                     String campus,
                     String location,
                     Integer image,
                     Date dateFound) {
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

    public Date getDateFound() {
        return this.dateFound;
    }
    public void setDateFound(Date dateFound) {
        this.dateFound = dateFound;
    }
}
