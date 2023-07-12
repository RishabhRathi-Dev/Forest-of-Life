package com.rishabh.forestoflife.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase

    init {
        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        ).build()
    }
}