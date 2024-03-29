package com.example.mysimplebrowser;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.mysimplebrowser.database.AppDatabase;
import com.example.mysimplebrowser.database.LinkEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<LinkEntry>> links;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        links = database.linkDao().loadAllTasks();
    }

    public LiveData<List<LinkEntry>> getTasks() {
        return links;
    }
}