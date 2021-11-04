package com.orbitalsonic.roomdatabasemvvmkoin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.interfaces.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_data_databases"
                ).addCallback(UserDatabaseCallback(scope, context))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class UserDatabaseCallback(
        private val scope: CoroutineScope, context: Context
    ) : RoomDatabase.Callback() {

        private val mContext = context

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val userDao = database.userDao()

                    // Delete all content here.
                    userDao.deleteAllUserList()

                    // Add some data in Room if you want.
                    for (index in 0..3)
                    userDao.insertUser(
                        UserEntity(
                            userName = "User $index",
                            cityName = "City $index",
                            mobileNumber = "09204538$index"
                        )
                    )


                }
            }
        }
    }

}