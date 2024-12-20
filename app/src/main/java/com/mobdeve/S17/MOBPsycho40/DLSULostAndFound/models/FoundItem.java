package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FoundItem extends Item {

    private String dateFound;

    // Default constructor required for Firebase
    public FoundItem() {
        super();
    }

    public FoundItem(String id,
                     String name,
                     ItemStatus status,
                     Category category,
                     String description,
                     String campus,
                     String location,
                     String image,
                     String dateFound) {
        super(id,
                name,
                status,
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
    public String getDateFound() {
        return this.dateFound;
    }

    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    public Date parseDateFoundAsDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        try {
            return sdf.parse(this.dateFound);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
