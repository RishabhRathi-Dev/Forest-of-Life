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

        val excercise = Task(
            taskHeading = "Excercise",
            isDaily = true,
            isWeekly = false,
            water = 1,
            fertilizer = 1,
            due = Calendar.getInstance().time,
            important = true
        )

        val makeRoutine = Task(
            taskHeading = "Make Routine",
            isDaily = false,
            isWeekly = false,
            water = 1,
            fertilizer = 1,
            due = Calendar.getInstance().time,
            important = true
        )

        viewModelScope.launch {
            inventoryDao.insertItem(newItem)
            taskDao.insertTask(excercise)
            taskDao.insertTask(makeRoutine)
        }
    }

    fun taskCompleted(taskId : Long, waterToAdd: Int, fertilizerToAdd: Int) {
        viewModelScope.launch {
            database.inventoryDao().addToInventory(waterToAdd = waterToAdd, fertilizerToAdd = fertilizerToAdd, treesToAdd = 0)
            database.taskDao().completeTask(taskId = taskId)
        }
    }

    fun deleteTask(taskId: Long){
        viewModelScope.launch {
            database.taskDao().deleteTask(taskId)
        }
    }

    fun markAndUnMarkImportant(taskId: Long){
        viewModelScope.launch {
            database.taskDao().markUnmarkImportance(taskId)
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