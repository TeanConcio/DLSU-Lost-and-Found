package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.util.Date;

public class LostItem extends Item {

    private Date dateLost;

    public LostItem(String name,
                    ItemStatus status,
                    String category,
                    String description,
                    String campus,
                    String location,
                    Integer image,
                    Date dateLost) {
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

    public Date getDateLost() {
        return this.dateLost;
    }
    public void setDateLost(Date dateLost) {
        this.dateLost = dateLost;
    }
}
