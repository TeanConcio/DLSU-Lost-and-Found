package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.models;

public enum ItemStatus {
    Lost,
    Found,
    Claimed;

    public String getString() {
        switch (this) {
            case Lost:
                return "Lost";
            case Found:
                return "Found";
            case Claimed:
                return "Claimed";
            default:
                return "";
        }
    }
}
