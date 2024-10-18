package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

import com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.R;

public enum Category {
    ELECTRONICS,
    ACCESSORIES,
    CONTAINERS,
    ESSENTIALS,
    CLOTHES,
    STATIONERIES,
    BOOKS,
    SPORTS_EQUIPMENT,
    OTHER_ITEMS;



    // Returns the category enum as a string while removing the underscores and capitalizing the first letter of each word
    public String getString() {
        String categoryString = this.toString().toLowerCase().replace("_", " ");
        String[] words = categoryString.split(" ");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append(" ");
        }

        return capitalized.toString().trim();
    }

    // Returns the icon resource id of the category
    public int getIcon() {
        switch (this) {
            case ELECTRONICS:
                return R.drawable.ic_electronics;
            case ACCESSORIES:
                return R.drawable.ic_accessories;
            case CONTAINERS:
                return R.drawable.ic_containers;
            case ESSENTIALS:
                return R.drawable.ic_essentials;
            case CLOTHES:
                return R.drawable.ic_clothes;
            case STATIONERIES:
                return R.drawable.ic_stationeries;
            case BOOKS:
                return R.drawable.ic_books;
            case SPORTS_EQUIPMENT:
                return R.drawable.ic_sports_equipment;
            case OTHER_ITEMS:
                return R.drawable.ic_other_items;
            default:
                return 0;
        }
    }
}
