package com.rishabh.forestoflife.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val dateWithoutTime = sdf.parse(sdf.format(Date()))

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
            due = sdf.parse(sdf.format(Calendar.getInstance().time)),
            important = true
        )

        val makeRoutine = Task(
            taskHeading = "Make Routine",
            isDaily = false,
            isWeekly = false,
            water = 5,
            fertilizer = 1,
            due = sdf.parse(sdf.format(Calendar.getInstance().time)),
            important = true
        )

        val readAbout = DueTask(
            taskHeading = "Read About in Profile",
            important = true,
            isWeekly = false,
            isDaily = false,
            due = sdf.parse(sdf.format(Calendar.getInstance().time))
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

    fun createTask(task: Task){
        viewModelScope.launch {
            database.taskDao().insertTask(task)
        }
    }

    fun markAndUnMarkImportant(taskId: Long, water : Int, fertilizer : Int){
        viewModelScope.launch {
            database.taskDao().markUnmarkImportance(taskId)
            database.taskDao().updateRewards(taskId, water, fertilizer)
        }
    }

    fun getTaskList() : LiveData<List<Task>> {
        return database.taskDao().getAllTasks()
    }

    fun getTaskListAsList() : List<Task> {
        return database.taskDao().getAllTasksAsList()
    }

    fun getImportantTaskList() : LiveData<List<Task>> {
        return database.taskDao().getImportantTasks()
    }

    fun getTodayTaskList() : LiveData<List<Task>> {
        var yesterday = Calendar.getInstance()
        val today = Calendar.getInstance().time;
        yesterday.time = Calendar.getInstance().time
        yesterday.add(Calendar.DATE, -1)

        return database.taskDao().getTodayTasks(yesterday.time, today)
    }

    // Due Task Functions

    fun getDueTaskList() : LiveData<List<DueTask>> {
        return database.dueTaskDao().getAllTasks()
    }

    fun getImportantDueTaskList() : LiveData<List<DueTask>> {
        return database.dueTaskDao().getImportantTasks()
    }

    fun getDueTaskListAsList() : List<DueTask> {
        return database.dueTaskDao().getAllTasksAsList()
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
            delay(100)
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


    // Other
    fun onTurnOnNotificationsClicked(granted: Boolean?) {

    }
}