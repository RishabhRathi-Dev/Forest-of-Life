package com.rishabh.forestoflife.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

// Define the Task entity
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0,
    val taskHeading: String,
    var due: Date,
    val points: Int,
    val isDaily: Boolean,
    val isWeekly: Boolean,
    val important: Boolean
)

// Define the DueTask entity
@Entity(tableName = "DueTask")
data class DueTask(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0,
    var taskHeading: String,
    var important: Boolean,
    var due: Date,
    var points : Int,
    val isDaily: Boolean,
    val isWeekly: Boolean,
)

@Entity(tableName = "Points")
data class Points(
    @PrimaryKey
    val id : Int = 0,
    var points: Int,
    var lastModified: Date
)

@Entity(tableName = "FocusTime")
data class FocusTime(
    @PrimaryKey
    val id : Int = 0,
    var time: Long,
    var lastModified: Date
)

@Dao
interface PointsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(points: Points)
    @Query("SELECT points FROM Points WHERE id = 0")
    fun getPoints() : LiveData<Int>

    @Query("SELECT points FROM Points WHERE id = 0")
    fun getPointsStatic() : Int

    @Query("SELECT * FROM Points WHERE id = 0")
    fun getAll() : Points

    @Transaction
    suspend fun addPoints(points: Int){
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        if ((getPointsStatic() + points) in 0..350){
            newPoints(points = getPointsStatic() + points)
        }

        else if ((getPointsStatic() + points) > 350){
            newPoints(points = 350)
        }

        else if ((getPointsStatic() + points) < 0){
            newPoints(points = 0)
        }

        newModified(date = sdf.parse(sdf.format(Calendar.getInstance().time)))
    }

    @Query("UPDATE Points SET points = :points WHERE id = 0")
    fun newPoints(points: Int)

    @Query("UPDATE POINTS SET lastModified = :date WHERE id = 0")
    fun newModified(date: Date)

    @Update
    suspend fun updatePoints(points: Points)
    @Transaction
    suspend fun checkForDeduction(){
        val calendar = Calendar.getInstance()
        calendar.time = Calendar.getInstance().time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val currentDate = calendar.time
        val p = getAll()

        val left = (TimeUnit.DAYS.convert(currentDate.time - p.lastModified.time, TimeUnit.MILLISECONDS)).toInt()

        if (p.lastModified.before(currentDate)){
            addPoints(-120 * left)
        }
    }
}

@Dao
interface FocusTimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(focusTime: FocusTime)
    @Query("SELECT time FROM FocusTime WHERE id = 0")
    fun getTime() : LiveData<Long>

    @Query("SELECT time FROM FocusTime WHERE id = 0")
    fun getTimeStatic() : Long

    @Query("SELECT * FROM FocusTime WHERE id = 0")
    fun getAll() : FocusTime

    @Transaction
    suspend fun addTime(points: Long){
        val sdf = SimpleDateFormat("dd-MM-yyyy")

        if ((getTimeStatic() + points) in 0..45*60*1000){
            newPoints(time = getTimeStatic() + points)
        }

        else if ((getTimeStatic() + points) > 45*60*1000){
            newPoints(time = 45*60*1000)
        }

        else if ((getTimeStatic() + points) < 0){
            newPoints(time = 0)
        }
        newModified(date = sdf.parse(sdf.format(Calendar.getInstance().time)))
    }

    @Query("UPDATE FocusTime SET time = :time WHERE id = 0")
    fun newPoints(time: Long)

    @Query("UPDATE FocusTime SET lastModified = :date WHERE id = 0")
    fun newModified(date: Date)

    @Query("UPDATE FocusTime SET time = 0 WHERE id = 0")
    fun dailyReset()

    @Update
    suspend fun updateFocusTime(focusTime: FocusTime)
    @Transaction
    suspend fun checkDailyReset(){
        val calendar = Calendar.getInstance()
        calendar.time = Calendar.getInstance().time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val currentDate = calendar.time
        val p = getAll()

        if (p.lastModified.before(currentDate)){
            dailyReset()
        }
    }
}


// Create a Data Access Object (DAO) for the Task entity
@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER BY due ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE important = 1")
    fun getImportantTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE due BETWEEN :prevDate AND :date")
    fun getTodayTasks(prevDate : Date, date: Date) : LiveData<List<Task>>

    @Query("SELECT * FROM task ORDER BY due ASC")
    fun getAllTasksAsList() : List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskToDueTable(dueTask: DueTask)

    @Update
    suspend fun updateTask(task: Task)

    @Transaction
    suspend fun completeTask(taskId: Long) {
        val task = getTaskById(taskId)

        task?.let {
            if (task.isDaily) {
                val calendar = Calendar.getInstance()
                calendar.time = task.due
                calendar.add(Calendar.DAY_OF_MONTH, 1) // Add one day
                task.due = calendar.time
                updateTask(task)
            } else if (task.isWeekly) {
                val calendar = Calendar.getInstance()
                calendar.time = task.due
                calendar.add(Calendar.WEEK_OF_YEAR, 1) // Add one week
                task.due = calendar.time
                updateTask(task)
            } else {
                deleteTask(task.taskId)
            }
        }
    }

    @Transaction
    suspend fun checkDueAndUpdate() {

        val calendar = Calendar.getInstance()
        calendar.time = Calendar.getInstance().time
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val currentDate = calendar.time
        val tasks = getAllTasksAsList()
        tasks.forEach { task ->
            Log.d("Date",task.due.toString() + ";" + currentDate.toString())
            if (task.due.before(currentDate)) {
                val toDue = DueTask(
                    taskHeading = task.taskHeading,
                    important = task.important,
                    isWeekly = task.isWeekly,
                    isDaily = task.isDaily,
                    points = task.points,
                    due = task.due
                )

                addTaskToDueTable(toDue)
                deleteTask(task.taskId)
            }
        }
    }

    @Query("UPDATE task SET important = (NOT important) WHERE taskId = :taskId")
    suspend fun markUnmarkImportance(taskId: Long)

    @Query("UPDATE task SET points = :points WHERE taskId = :taskId")
    suspend fun updateRewards(taskId: Long, points : Int)

    @Query("DELETE FROM task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

}

@Dao
interface DueTaskDao {
    @Query("SELECT * FROM DueTask")
    fun getAllTasks(): LiveData<List<DueTask>>

    @Query("SELECT * FROM DueTask WHERE important = 1")
    fun getImportantTasks(): LiveData<List<DueTask>>

    @Query("SELECT * FROM DueTask")
    fun getAllTasksAsList(): List<DueTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: DueTask)

    @Query("SELECT * FROM DueTask WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Long): DueTask?

    @Update
    suspend fun updateTask(task: DueTask)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskToTaskTable(task: Task)

    @Transaction
    suspend fun completeTask(taskId: Long) {
        val task = getTaskById(taskId)
        val c = Calendar.getInstance()
        c.time = Calendar.getInstance().time
        c.add(Calendar.DAY_OF_MONTH, -1)
        val current = c.time
        task?.let {
            if (task.isDaily) {
                val calendar = Calendar.getInstance()
                calendar.time = task.due
                calendar.add(Calendar.DAY_OF_MONTH, 1) // Add one day
                task.due = calendar.time
                if (current.before(task.due)){
                    val backToCurrent = Task(
                        taskHeading = task.taskHeading,
                        important = task.important,
                        isDaily = task.isDaily,
                        isWeekly = task.isWeekly,
                        points = task.points,
                        due = task.due
                    )

                    addTaskToTaskTable(backToCurrent)
                    deleteTask(task.taskId)

                } else {
                    updateTask(task)
                }
            } else if (task.isWeekly) {
                val calendar = Calendar.getInstance()
                calendar.time = task.due
                calendar.add(Calendar.WEEK_OF_YEAR, 7) // Add one week
                task.due = calendar.time
                if (current.before(task.due)){
                    val backToCurrent = Task(
                        taskHeading = task.taskHeading,
                        important = task.important,
                        isDaily = task.isDaily,
                        isWeekly = task.isWeekly,
                        points = task.points,
                        due = task.due
                    )

                    addTaskToTaskTable(backToCurrent)
                    deleteTask(task.taskId)

                } else {
                    updateTask(task)
                }
            } else {
                deleteTask(task.taskId)
            }
        }
    }

    @Query("DELETE FROM DueTask WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("UPDATE DueTask SET important = (NOT important) WHERE taskId = :taskId")
    suspend fun markUnmarkImportance(taskId: Long)
}

// Create the Room database
// Define a custom type converter
class DateTypeConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}

// Update your Room database configuration with the type converter
@Database(entities = [FocusTime::class, Points::class, Task::class, DueTask::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun focusTimeDao() : FocusTimeDao
    abstract fun pointsDao() : PointsDao
    abstract fun taskDao(): TaskDao
    abstract fun dueTaskDao() : DueTaskDao
}
