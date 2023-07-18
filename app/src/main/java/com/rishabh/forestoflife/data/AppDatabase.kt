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

// Define the Inventory entity
@Entity(tableName = "inventory")
data class Inventory(
    @PrimaryKey val id: Long = 0,
    var water: Int,
    var fertilizer: Int,
    var trees: Int
)

// Define the Island entity
@Entity(tableName = "island")
data class Island(
    @PrimaryKey val id: Long = 0,
    val typeString: String,
    val lifeString: String,
    val treeVectorString: String,
    val treeDateString: String
)

// Define the Task entity
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long = 0,
    val taskHeading: String,
    var due: Date,
    val water: Int,
    val fertilizer: Int,
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
    val isDaily: Boolean,
    val isWeekly: Boolean,
)

// Create a Data Access Object (DAO) for the Inventory entity
@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory WHERE id=0")
    fun getAllItems(): LiveData<List<Inventory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Inventory)

    @Update
    suspend fun updateItem(item: Inventory)

    @Transaction
    suspend fun addToInventory(waterToAdd: Int, fertilizerToAdd: Int, treesToAdd: Int) {
        val existingItem = getInventoryItem()
        existingItem?.let {

            val updatedWater = if (existingItem.water + waterToAdd <= 20) existingItem.water + waterToAdd else 20
            val updatedFertilizer = if (existingItem.fertilizer + fertilizerToAdd <= 10) existingItem.fertilizer + fertilizerToAdd else 10
            val updatedTrees = if (existingItem.trees + treesToAdd <= 50) existingItem.trees + treesToAdd else 50
            existingItem.water = updatedWater
            existingItem.fertilizer = updatedFertilizer
            existingItem.trees = updatedTrees
            updateItem(existingItem)
        }
    }

    @Query("SELECT * FROM inventory WHERE id=0")
    suspend fun getInventoryItem(): Inventory?
}

// Create a Data Access Object (DAO) for the Island entity
@Dao
interface IslandDao {
    @Query("SELECT * FROM island")
    fun getAllIslands(): LiveData<List<Island>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIsland(island: Island)
}

// Create a Data Access Object (DAO) for the Task entity
@Dao
interface TaskDao {

    @Query("SELECT * FROM task ORDER BY important DESC, due ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task ORDER BY important DESC, due ASC")
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
            if (task.due.before(currentDate)) {
                val toDue = DueTask(
                    taskHeading = task.taskHeading,
                    important = task.important,
                    isWeekly = task.isWeekly,
                    isDaily = task.isDaily,
                    due = task.due
                )

                addTaskToDueTable(toDue)
                deleteTask(task.taskId)
            }
        }
    }

    @Query("UPDATE task SET important = (NOT important) WHERE taskId = :taskId")
    suspend fun markUnmarkImportance(taskId: Long)

    @Query("UPDATE task SET water = :water, fertilizer = :fertilizer WHERE taskId = :taskId")
    suspend fun updateRewards(taskId: Long, water: Int, fertilizer: Int)

    @Query("DELETE FROM task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

}

@Dao
interface DueTaskDao {
    @Query("SELECT * FROM DueTask ORDER BY important DESC")
    fun getAllTasks(): LiveData<List<DueTask>>

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
                        water = 5,
                        fertilizer = 1,
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
                        water = 5,
                        fertilizer = 1,
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
@Database(entities = [Inventory::class, Island::class, Task::class, DueTask::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
    abstract fun islandDao(): IslandDao
    abstract fun taskDao(): TaskDao
    abstract fun dueTaskDao() : DueTaskDao
}
