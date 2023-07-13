package com.rishabh.forestoflife.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

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
        val taskDao : TaskDao = database.taskDao()

        val newItem = Inventory(
            water = 10,
            fertilizer = 5,
            trees = 20
        )

        val newTask = Task(
            taskHeading = "Excercise",
            isDaily = true,
            isWeekly = false,
            water = 1,
            fertilizer = 1,
            due = Calendar.getInstance().time,
            important = true
        )

        viewModelScope.launch {
            inventoryDao.insertItem(newItem)
            taskDao.insertTask(newTask)
        }
    }

    fun getDueTaskList() : LiveData<List<DueTask>> {
        return database.dueTaskDao().getAllTasks()
    }
    fun getTaskList() : LiveData<List<Task>> {
        return database.taskDao().getAllTasks()
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