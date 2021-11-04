package com.orbitalsonic.roomdatabasemvvmkoin.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "userName") var userName: String,
    @ColumnInfo(name = "cityName") var cityName: String,
    @ColumnInfo(name = "mobileNUmber") var mobileNumber:String
)
