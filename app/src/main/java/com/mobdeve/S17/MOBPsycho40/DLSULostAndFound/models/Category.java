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
    Sports_Equipment,
    Other_Items;


    // Returns the category enum as a string while removing the underscores and capitalizing the first letter of each word
    public String getString() {
        String categoryString = this.toString().toLowerCase();
        String[] words = categoryString.split("_");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append("_");
        }

        // Remove the trailing underscore and return the result
        return capitalized.substring(0, capitalized.length() - 1);
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
            case Sports_Equipment:
                return R.drawable.ic_sports_equipment;
            case Other_Items:
                return R.drawable.ic_other_items;
            default:
                return 0;
        }
    }
}
