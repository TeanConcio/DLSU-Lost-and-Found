package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDateTime;
import java.util.Date;

public class LostItem extends Item {

    private LocalDateTime dateLost;

    public LostItem(String name,
                    String category,
                    String description,
                    String campus,
                    String location,
                    Integer image,
                    LocalDateTime dateLost) {
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

    public LocalDateTime getDateLost() {
        return this.dateLost;
    }
    public void setDateLost(LocalDateTime dateLost) {
        this.dateLost = dateLost;
    }
}
