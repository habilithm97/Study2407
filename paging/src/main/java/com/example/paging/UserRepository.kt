package com.example.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: List<User>) {
        userDao.insert(user)
    }

    // UI에서 데이터의 상태를 관찰하고 변화가 생길 때마다 자동으로 업데이트
    fun getUser(): Flow<PagingData<User>> {
        return Pager( // Pager 객체 생성
            // Pager의 동작 방식 정의
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { userDao.getUser() } // 데이터 제공
        ).flow // Pager 객체가 페이징된 데이터를 Flow 형태로 반환(비동기적 제공)
        // -> UI에서 이를 수집(collect)하고 표시할 수 있게 함
        /**
         * Flow 사용 -> UI에서 데이터를 연속적으로 받아옴 -> 스크롤을 할 때마다
          새로운 데이터를 불러오고 UI 업데이트
          (페이징된 데이터는 전체 데이터를 한 번에 불러오는 것이 아니라, 필요할 때마다
           페이지 단위로 데이터를 요청하고 이를 UI에 반영하는 방식)
         */
    }
}