package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LostItem extends Item {

    private String dateLost;
    private String userID;

    public LostItem() {
        super(); // Call the no-argument constructor of the parent class
    }
    public LostItem(String id,
                    String name,
                    Category category,
                    String description,
                    String campus,
                    String location,
                    Integer image,
                    String dateLost,
                    String userID) {
        super(id,
                name,
                ItemStatus.Lost,
                category,
                description,
                campus,
                location,
                image);

        this.dateLost = dateLost;
        this.userID = userID;
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
    public Date parseDateLostAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            return sdf.parse(this.dateLost);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getUserID() { // Getter for userID
        return this.userID;
    }
    public void setUserID(String userID) { // Setter for userID
        this.userID = userID;
    }
}
