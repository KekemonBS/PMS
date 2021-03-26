package ua.kpi.comsys.iv8106.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();mText.setValue("Just fiddled with this field");

    }

    public LiveData<String> getText() {
        return mText;
    }
}