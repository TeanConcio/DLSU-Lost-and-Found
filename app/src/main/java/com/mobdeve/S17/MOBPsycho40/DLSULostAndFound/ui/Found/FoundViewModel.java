package com.mobdeve.S17.MOBPsycho40.DLSULostAndFound.ui.Found;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoundViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FoundViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is found fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}