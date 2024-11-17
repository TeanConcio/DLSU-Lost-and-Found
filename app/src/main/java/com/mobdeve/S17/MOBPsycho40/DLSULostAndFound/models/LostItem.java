package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public Date getDateLostAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            return sdf.parse(this.dateLost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
