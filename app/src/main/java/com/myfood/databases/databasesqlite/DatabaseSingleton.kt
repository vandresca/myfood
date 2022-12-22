package com.myfood.databases.databasesqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.myfood.databases.databasesqlite.entity.*

// Definimos las entidades de cada una de las tablas que forman la base de datos
// También definimos el número de versión. Cada vez que sobreescribamos el fichero de
// base de datos con cambios se debera actualizar y emplear el método
// .fallbackToDestructiveMigration()
@Database(
    entities = [
        Currency::class, CurrencyTranslation::class, Language::class, Screen::class,
        ScreenWord::class, Word::class, WordTranslation::class, QuantityUnit::class,
        StorePlace::class, ConfigProfile::class], version = 22, exportSchema = true
)
abstract class RoomSingleton : androidx.room.RoomDatabase() {

    // Función abstracta que obtiene los metodos de la interfaz SQLiteDao
    abstract fun sqliteDao(): SQLiteDao

    companion object {

        // Se crean dos constantes una para el nombre de la base de datos
        // que se encuentra en el dispositivo y otro para la que se creara
        // en tiempo de ejecución.
        private const val SQLITE_DATABASE = "myfood.db"
        private const val SQLITE_DATABASE_POST = "sample.db"

        // Se crea y obtiene una instancia de la base de datos SQLite
        // Este método se ejecutara cuando no sean las pruebas unitarias.
        fun getInstance(context: Context): RoomSingleton {
            return Room.databaseBuilder(
                context,
                RoomSingleton::class.java,
                SQLITE_DATABASE_POST
            )
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .createFromAsset(SQLITE_DATABASE).build()
        }

        // Se obtiene la referencia de RoomSingleton y se le asigna un valor
        // nulo
        @Volatile
        private var instance: RoomSingleton? = null

        // Se crea una funcion operador cuyo valor de contexto como parametro
        // se asigna  a la variable instancia.
        // Este método se invocara para las pruebas unitarias
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private val LOCK = Any()

        // Se crea una función buildDatabase para asignar los valores requeridos
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomSingleton::class.java,
            SQLITE_DATABASE
        ).fallbackToDestructiveMigration().build()
    }
}