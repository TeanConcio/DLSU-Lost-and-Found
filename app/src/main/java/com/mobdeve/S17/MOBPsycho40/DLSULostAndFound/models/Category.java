package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

public enum Category {
    ELECTRONICS,
    ACCESSORIES,
    CONTAINERS,
    ESSENTIALS,
    CLOTHES,
    STATIONERIES,
    BOOKS,
    SPORTS_EQUIPMENT,
    OTHER;

    // Returns the category enum as a string while removing the underscores and capitalizing the first letter of each word
    public String getCategoryString() {
        String categoryString = this.toString().toLowerCase().replace("_", " ");
        String[] words = categoryString.split(" ");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1)).append(" ");
        }

        return capitalized.toString().trim();
    }

}
