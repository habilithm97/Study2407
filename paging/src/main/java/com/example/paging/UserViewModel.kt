package com.example.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    fun insertUser(user: List<User>) {
        // viewModelScope : ViewModel 생명주기 코루틴 스코프
        // -> ViewModel 파괴 시 스코프 내 실행 중인 모든 코루틴 자동 취소
        viewModelScope.launch { // 비동기적 데이터 삽입 (UI 스레드 차단 x, 백그라운드 작업 처리)
            repository.insertUser(user)
        }
    }
    // Flow를 이용해 사용자 데이터를 비동기적으로 불러오고
    // ViewModel 생명주기 동안 캐싱하여 효율적인 데이터 관리 가능
    // -> UI가 데이터를 여러 번 요청해도 동일한 데이터를 사용하게 되어 성능이 향상
    val user: Flow<PagingData<User>> = repository.getUser().cachedIn(viewModelScope)
}