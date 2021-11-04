package com.orbitalsonic.roomdatabasemvvmkoin.repository

import androidx.annotation.WorkerThread
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.interfaces.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    /***
     * user_table methods
     */

    val allUserList: Flow<List<UserEntity>> = userDao.getAllUserList()


    @WorkerThread
    suspend fun insertUser(userEntity: UserEntity) {
        userDao.insertUser(userEntity)
    }


    @WorkerThread
    suspend fun deleteUser(userEntity: UserEntity) {
        userDao.deleteUser(userEntity)
    }


    @WorkerThread
    suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(userEntity)
    }


    @WorkerThread
    suspend fun deleteAllUserList() {
        userDao.deleteAllUserList()
    }

}