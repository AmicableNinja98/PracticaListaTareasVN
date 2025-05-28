package com.example.practicalistatareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practicalistatareas.domain.Task
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun getTaskDao() : TaskDao

    companion object{
        @Volatile
        private var INSTANCE : TaskDatabase? = null

        fun getDatabase(context: Context) : TaskDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database.db"
                ).addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Executors.newSingleThreadExecutor().execute {
                            INSTANCE?.let {
                                database -> prepopulateDatabase(database)
                            }
                        }
                    }
                })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun prepopulateDatabase(database: TaskDatabase){
            val taskDao = database.getTaskDao()
            val taskList = listOf(
                Task(
                    id = 1,
                    title = "Comprar víveres",
                    description = "Ir al supermercado y comprar leche, pan y huevos.",
                    dueDate = 1717228800000, // Ejemplo: 1 de junio de 2024
                    priority = 2,
                    isCompleted = false,
                    createdAt = 1716835200000 // Ejemplo: 27 de mayo de 2024
                ),
                Task(
                    id = 2,
                    title = "Estudiar para el examen de matemáticas",
                    description = "Revisar los temas de álgebra y geometría.",
                    dueDate = 1717488000000, // 4 de junio de 2024
                    priority = 2,
                    isCompleted = false,
                    createdAt = 1716835200000
                ),
                Task(
                    id = 3,
                    title = "Enviar informe mensual",
                    description = "Preparar y enviar el informe al jefe antes del viernes.",
                    dueDate = 1717650800000, // 6 de junio de 2024
                    priority = 0,
                    isCompleted = false,
                    createdAt = 1716835200000
                ),
                Task(
                    id = 4,
                    title = "Llamar al médico",
                    description = "Agendar cita de revisión anual.",
                    dueDate = null,
                    priority = 1,
                    isCompleted = true,
                    createdAt = 1716748800000 // 26 de mayo de 2024
                ),
                Task(
                    id = 5,
                    title = "Actualizar CV",
                    description = "Agregar último proyecto y corregir formato.",
                    dueDate = null,
                    priority = 1,
                    isCompleted = false,
                    createdAt = 1716835200000
                )
            )

            runBlocking {
                taskList.forEach {
                    task -> taskDao.insertTask(task)
                }
            }
        }
    }

}