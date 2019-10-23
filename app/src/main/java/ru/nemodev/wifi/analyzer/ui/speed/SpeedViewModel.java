package ru.nemodev.wifi.analyzer.ui.speed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpeedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpeedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}