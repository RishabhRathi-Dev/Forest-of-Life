package com.rishabh.forestoflife.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase

    init {
        database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    fun initialSetup() {
        val inventoryDao: InventoryDao = database.inventoryDao()

        val newItem = Inventory(
            water = 10,
            fertilizer = 5,
            trees = 20
        )

        viewModelScope.launch {
            inventoryDao.insertItem(newItem)
        }
    }

    fun getInventoryItems(): LiveData<List<Inventory>> {
        return database.inventoryDao().getAllItems()
    }

    fun updateInventory(waterToAdd: Int, fertilizerToAdd: Int, treesToAdd: Int){
        viewModelScope.launch {
            database.inventoryDao().addToInventory(waterToAdd, fertilizerToAdd, treesToAdd)
        }
    }
}