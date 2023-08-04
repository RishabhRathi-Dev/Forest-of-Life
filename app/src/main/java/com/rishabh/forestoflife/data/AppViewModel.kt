package com.rishabh.forestoflife.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
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
        val taskDao : TaskDao = database.taskDao()
        val dueTaskDao : DueTaskDao = database.dueTaskDao()
        val pointsDao : PointsDao = database.pointsDao()
        val focusTime : FocusTimeDao = database.focusTimeDao()

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val dateWithoutTime = sdf.parse(sdf.format(Date()))
        val starterPoints = Points(0, 0, sdf.parse(sdf.format(Calendar.getInstance().time)))
        val starterTime = FocusTime(0, 0, sdf.parse(sdf.format(Calendar.getInstance().time)))

        val excercise = Task(
            taskHeading = "Exercise",
            isDaily = true,
            isWeekly = false,
            points = 20,
            due = sdf.parse(sdf.format(Calendar.getInstance().time)),
            important = true
        )

        val makeRoutine = Task(
            taskHeading = "Read Help From Profile",
            isDaily = false,
            isWeekly = false,
            points = 20,
            due = sdf.parse(sdf.format(Calendar.getInstance().time)),
            important = true
        )


        viewModelScope.launch {
            focusTime.insert(starterTime)
            pointsDao.insert(starterPoints)
            taskDao.insertTask(excercise)
            taskDao.insertTask(makeRoutine)

        }
    }

    // TODO : Call is not updating list
    fun workerCall(){
        viewModelScope.launch {
            database.taskDao().checkDueAndUpdate()
            database.pointsDao().checkForDeduction()
            database.focusTimeDao().checkDailyReset()
        }
    }

    // Tasks Functions

    fun taskCompleted(taskId : Long, points: Int) {
        viewModelScope.launch {
            database.pointsDao().addPoints(points = points)
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

    fun markAndUnMarkImportant(taskId: Long, points : Int){
        viewModelScope.launch {
            database.taskDao().markUnmarkImportance(taskId)
            database.taskDao().updateRewards(taskId, points)
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

    fun getPoints(): LiveData<Int> {
        return database.pointsDao().getPoints()
    }

    fun getStaticPoints() : Int {
        return database.pointsDao().getPointsStatic()
    }

    fun deductPoints(points: Int) {
        viewModelScope.launch {
            database.pointsDao().addPoints(points = -points)
        }
    }

    fun getTime(): LiveData<Long> {
        return database.focusTimeDao().getTime()
    }

    fun getStaticTime() : Long {
        return database.focusTimeDao().getTimeStatic()
    }

    fun addTime(time: Long) {
        viewModelScope.launch {
            database.focusTimeDao().addTime(time)
        }
    }

    companion object {
        private lateinit var instance: AppViewModel
        fun getInstance(context: Context): AppViewModel {
            if (!::instance.isInitialized) {
                instance = ViewModelProvider(
                    ViewModelStore(),
                    ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                )[AppViewModel::class.java]
            }
            return instance
        }
    }

}