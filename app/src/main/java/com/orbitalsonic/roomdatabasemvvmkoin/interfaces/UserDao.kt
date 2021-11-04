package com.orbitalsonic.roomdatabasemvvmkoin.interfaces

import androidx.room.*
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /***
     * User DAO
     */

    @Query("SELECT * FROM user_table")
    fun getAllUserList(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUserList()

}