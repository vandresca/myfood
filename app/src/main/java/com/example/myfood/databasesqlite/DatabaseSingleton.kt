package com.example.myfood.databasesqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.myfood.databasesqlite.entity.*


@Database(
    entities = [
        Currency::class, CurrencyTranslation::class, Language::class, Screen::class,
        ScreenWord::class, Word::class, WordTranslation::class], version = 2, exportSchema = true
)
abstract class RoomSingleton : androidx.room.RoomDatabase() {
    abstract fun sqliteDao(): SQLiteDao

    companion object {
        fun getInstance(context: Context): RoomSingleton {
            return Room.databaseBuilder(
                context,
                RoomSingleton::class.java,
                "sample.db"
            )
                .fallbackToDestructiveMigration()
                .createFromAsset("myfood.db").build()
        }
    }
}