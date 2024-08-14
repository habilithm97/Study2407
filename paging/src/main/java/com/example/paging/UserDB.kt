package com.example.paging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null

        fun getInstance(context: Context): UserDB {
            // INSTANCE가 null일 때만 생성
            return INSTANCE ?: synchronized(this) { // 동기화(한 번에 하나의 스레드만 접근 가능)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    "user_db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}