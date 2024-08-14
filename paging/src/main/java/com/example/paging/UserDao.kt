package com.example.paging

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: List<User>) // suspend : 코루틴 내에서 비동기적으로 호출

    @Query("select * from user order by name")
    fun getUser(): PagingSource<Int, User> // PagingSource : 여러 페이지로 나뉘어진 데이터를 효율적으로 로드
}