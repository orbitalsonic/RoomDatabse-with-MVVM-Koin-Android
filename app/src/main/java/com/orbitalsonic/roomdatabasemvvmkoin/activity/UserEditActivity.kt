package com.orbitalsonic.roomdatabasemvvmkoin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.orbitalsonic.roomdatabasemvvmkoin.R
import com.orbitalsonic.roomdatabasemvvmkoin.databinding.ActivityUserEditBinding
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.utils.BaseObject.USER_ADD_REQUEST_CODE
import com.orbitalsonic.roomdatabasemvvmkoin.utils.BaseObject.USER_EDIT_REQUEST_CODE
import com.orbitalsonic.roomdatabasemvvmkoin.utils.ObjectConverter

class UserEditActivity : BaseActivity() {

    private lateinit var binding: ActivityUserEditBinding
    private var requestCode = -1
    private lateinit var userEntity: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_edit)


        requestCode = intent.getIntExtra("UserRequestCode", 0)
        if (requestCode == USER_EDIT_REQUEST_CODE) {
            title = "Edit User"
            userEntity = ObjectConverter.fromStringToObject(intent.getStringExtra("UserData")!!)
            addInitialEditData()
        } else {
            title = "Add User"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.user_done -> {
                binding.apply {
                    if (etUserName.text.toString().isNotEmpty() &&
                        etCityName.text.toString().isNotEmpty() &&
                        etMobileNumber.text.toString().isNotEmpty()
                    ) {
                        userSave()
                    } else {
                        showMessage("Fields are empty!")
                    }
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun userSave() {

        if (requestCode == USER_ADD_REQUEST_CODE) {
            binding.apply {
                userEntity = UserEntity(
                    userName = etUserName.text.toString(),
                    cityName = etCityName.text.toString(),
                    mobileNumber = etMobileNumber.text.toString()
                )
            }
        } else {
            binding.apply {
                userEntity.userName = etUserName.text.toString()
                userEntity.cityName = etCityName.text.toString()
                userEntity.mobileNumber = etMobileNumber.text.toString()

            }

        }

        val replyIntent = Intent()
        replyIntent.putExtra(
            "UserData",
            ObjectConverter.fromObjectToString(userEntity)
        )
        setResult(Activity.RESULT_OK, replyIntent)
        finish()

    }

    private fun addInitialEditData() {
        binding.apply {
            etUserName.setText(userEntity.userName)
            etCityName.setText(userEntity.cityName)
            etMobileNumber.setText(userEntity.mobileNumber)

        }
    }

}