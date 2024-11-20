package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;

public enum Category {
    Electronics,
    Accessories,
    Containers,
    Essentials,
    Clothes,
    Stationaries,
    Books,
    Sports,
    Others;


    // Returns the category enum as a string while removing the underscores and capitalizing the first letter of each word
    public String getString() {
        return this.toString();
    }

    // Returns the icon resource id of the category
    public int getIcon() {
        switch (this) {
            case Electronics:
                return R.drawable.ic_electronics;
            case Accessories:
                return R.drawable.ic_accessories;
            case Containers:
                return R.drawable.ic_containers;
            case Essentials:
                return R.drawable.ic_essentials;
            case Clothes:
                return R.drawable.ic_clothes;
            case Stationaries:
                return R.drawable.ic_stationeries;
            case Books:
                return R.drawable.ic_books;
            case Sports:
                return R.drawable.ic_sports_equipment;
            case Others:
                return R.drawable.ic_other_items;
            default:
                return 0;
        }
    }
}
