package com.example.mysimplebrowser;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mysimplebrowser.database.AppDatabase;
import com.example.mysimplebrowser.database.LinkEntry;

public class DetailViewModel extends ViewModel {

    private LiveData<LinkEntry> link;

    public DetailViewModel(AppDatabase database, int linkId) {
        link = database.linkDao().loadLinkById(linkId);
    }

    public LiveData<LinkEntry> getLink() {
        return link;
    }
}
