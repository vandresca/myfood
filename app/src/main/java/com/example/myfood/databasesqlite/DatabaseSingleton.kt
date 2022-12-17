package com.example.myfood.databasesqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.myfood.databasesqlite.entity.*


@Database(
    entities = [
        Currency::class, CurrencyTranslation::class, Language::class, Screen::class,
        ScreenWord::class, Word::class, WordTranslation::class, QuantityUnit::class,
        StorePlace::class, ConfigProfile::class], version = 20, exportSchema = true
)
abstract class RoomSingleton : androidx.room.RoomDatabase() {


    abstract fun sqliteDao(): SQLiteDao

    companion object {

        private const val SQLITE_DATABASE = "myfood.db"
        private const val SQLITE_DATABASE_POST = "sample.db"


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

        // Get reference of the LanguageDatabase and assign it null value
        @Volatile
        private var instance: RoomSingleton? = null
        private val LOCK = Any()

        // create an operator fun which has context as a parameter
        // assign value to the instance variable
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        // create a buildDatabase function assign the required values
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomSingleton::class.java,
            SQLITE_DATABASE
        ).fallbackToDestructiveMigration().build()
    }
}