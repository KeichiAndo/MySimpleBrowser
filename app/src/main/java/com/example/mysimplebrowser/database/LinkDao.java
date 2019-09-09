package com.example.mysimplebrowser.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LinkDao {
    @Query("SELECT * FROM link ORDER BY linkName")
    LiveData<List<LinkEntry>> loadAllTasks();

    @Insert
    void insertTask(LinkEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(LinkEntry taskEntry);

    @Delete
    void deleteTask(LinkEntry taskEntry);

    @Query("SELECT * FROM link WHERE id = :id")
    LiveData<LinkEntry> loadLinkById(int id);
}
