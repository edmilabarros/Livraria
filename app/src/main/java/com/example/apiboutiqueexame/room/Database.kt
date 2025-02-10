package com.example.apiboutiqueexame.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Criando usuário padrão ao iniciar o banco
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.userDao()?.insertUser(
                                    User(
                                        name = "Admin",
                                        email = "admin@email.com",
                                        password = "123456"
                                    )
                                )
                            }
                        }
                    })
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}
