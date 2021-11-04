package com.orbitalsonic.roomdatabasemvvmkoin.viewmodel

import androidx.lifecycle.*
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allUserList: LiveData<List<UserEntity>> = repository.allUserList.asLiveData()

    fun insertUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.insertUser(userEntity)
    }

    fun deleteUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.deleteUser(userEntity)
    }

    fun updateUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.updateUser(userEntity)
    }

    fun deleteAllUser() = viewModelScope.launch {
        repository.deleteAllUserList()
    }

}