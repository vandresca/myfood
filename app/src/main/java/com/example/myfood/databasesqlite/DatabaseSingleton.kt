package com.example.myfood.databasesqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.myfood.databasesqlite.entity.*


@Database(
    entities = [
        Currency::class, CurrencyTranslation::class, Language::class, Screen::class,
        ScreenWord::class, Word::class, WordTranslation::class, QuantityUnit::class,
        StorePlace::class, ConfigProfile::class], version = 13, exportSchema = true
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
    }
}