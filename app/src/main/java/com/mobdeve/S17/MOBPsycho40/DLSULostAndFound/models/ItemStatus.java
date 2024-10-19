package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

public enum ItemStatus {
    LOST,
    FOUND,
    CLAIMED;

    public String getString() {
        switch (this) {
            case LOST:
                return "Lost";
            case FOUND:
                return "Found";
            case CLAIMED:
                return "Claimed";
            default:
                return "";
        }
    }
}
