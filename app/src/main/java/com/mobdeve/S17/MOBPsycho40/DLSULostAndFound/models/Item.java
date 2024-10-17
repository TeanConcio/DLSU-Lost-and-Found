package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.Date;

public abstract class Item {

    private String name;
    private ItemStatus status;
    private Category category;
    private String description;
    private String campus;
    private String location;
    private Integer image;

    private LocalDate dateClaimed;

    public Item(String name,
                ItemStatus status,
                Category category,
                String description,
                String campus,
                String location,
                Integer image) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.description = description;
        this.campus = campus;
        this.location = location;
        this.image = image;
    }



    /*
        Getters and Setters
    */

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ItemStatus getStatus() {
        return this.status;
    }
    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public Category getCategory() {
        return this.category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCampus() {
        return this.campus;
    }
    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getImage() {
        return this.image;
    }
    public void setImage(Integer image) {
        this.image = image;
    }

    public LocalDate getDateClaimed() {
        return this.dateClaimed;
    }
    public void setDateClaimed(LocalDate dateClaimed) {
        this.dateClaimed = dateClaimed;
    }
}
