package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;



public class LostItem extends Item {

    private String dateLost;

    public LostItem(String name,
                    Category category,
                    String description,
                    String campus,
                    String location,
                    Integer image,
                    String dateLost) {
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

    public String getDateLost() {
        return this.dateLost;
    }
    public void setDateLost(String dateLost) {
        this.dateLost = dateLost;
    }
}
