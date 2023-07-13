package com.rishabh.forestoflife.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Database
import androidx.room.Update
import androidx.lifecycle.LiveData
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date
import java.util.Calendar

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
    var important: Boolean
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
            val updatedWater = existingItem.water + waterToAdd
            val updatedFertilizer = existingItem.fertilizer + fertilizerToAdd
            val updatedTrees = existingItem.trees + treesToAdd
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
        getAllTasks().value?.forEach { task ->
            val currentDate = Date()
            if (task.due.before(currentDate)) {
                val toDue = DueTask(taskHeading = task.taskHeading, important = task.important)
                addTaskToDueTable(toDue)
                deleteTask(task.taskId)
            }
        }
    }

    @Query("UPDATE task SET important = (NOT important) WHERE taskId = :taskId")
    suspend fun markUnmarkImportance(taskId: Long)

    @Query("DELETE FROM task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("SELECT * FROM task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Long): Task?

}

@Dao
interface DueTaskDao {
    @Query("SELECT * FROM DueTask")
    fun getAllTasks(): LiveData<List<DueTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: DueTask)
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
