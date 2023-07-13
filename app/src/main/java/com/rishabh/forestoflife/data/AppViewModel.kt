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
        val dueTaskDao : DueTaskDao = database.dueTaskDao()

        val newItem = Inventory(
            water = 10,
            fertilizer = 5,
            trees = 20
        )

        val excercise = Task(
            taskHeading = "Excercise",
            isDaily = true,
            isWeekly = false,
            water = 5,
            fertilizer = 1,
            due = Calendar.getInstance().time,
            important = true
        )

        val makeRoutine = Task(
            taskHeading = "Make Routine",
            isDaily = false,
            isWeekly = false,
            water = 5,
            fertilizer = 1,
            due = Calendar.getInstance().time,
            important = true
        )

        val readAbout = DueTask(
            taskHeading = "Read About in Profile",
            important = true,
            isWeekly = false,
            isDaily = false,
            due = Calendar.getInstance().time
        )

        viewModelScope.launch {
            inventoryDao.insertItem(newItem)
            taskDao.insertTask(excercise)
            taskDao.insertTask(makeRoutine)
            dueTaskDao.insertTask(readAbout)
        }
    }

    // TODO : Call is not updating list
    fun workerCall(){
        viewModelScope.launch {
            database.taskDao().checkDueAndUpdate()
        }
    }

    // Tasks Functions

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

    fun getTaskList() : LiveData<List<Task>> {
        return database.taskDao().getAllTasks()
    }

    // Due Task Functions

    fun getDueTaskList() : LiveData<List<DueTask>> {
        return database.dueTaskDao().getAllTasks()
    }

    fun dueTaskCompleted(taskId : Long) {
        viewModelScope.launch {
            database.dueTaskDao().completeTask(taskId = taskId)
        }
    }

    fun deleteDueTask(taskId: Long){
        viewModelScope.launch {
            database.dueTaskDao().deleteTask(taskId)
        }
    }

    fun markAndUnMarkImportantDueTask(taskId: Long){
        viewModelScope.launch {
            database.dueTaskDao().markUnmarkImportance(taskId)
        }
    }


    // Inventory Functions

    fun getInventoryItems(): LiveData<List<Inventory>> {
        return database.inventoryDao().getAllItems()
    }

    fun updateInventory(waterToAdd: Int, fertilizerToAdd: Int, treesToAdd: Int){
        viewModelScope.launch {
            database.inventoryDao().addToInventory(waterToAdd, fertilizerToAdd, treesToAdd)
        }
    }
}