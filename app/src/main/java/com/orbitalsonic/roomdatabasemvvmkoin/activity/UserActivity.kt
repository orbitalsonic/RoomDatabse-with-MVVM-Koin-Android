package com.orbitalsonic.roomdatabasemvvmkoin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.roomdatabasemvvmkoin.R
import com.orbitalsonic.roomdatabasemvvmkoin.adapters.UserListAdapter
import com.orbitalsonic.roomdatabasemvvmkoin.databinding.ActivityUserBinding
import com.orbitalsonic.roomdatabasemvvmkoin.datamodel.UserEntity
import com.orbitalsonic.roomdatabasemvvmkoin.interfaces.OnUserClickListener
import com.orbitalsonic.roomdatabasemvvmkoin.utils.BaseObject.USER_ADD_REQUEST_CODE
import com.orbitalsonic.roomdatabasemvvmkoin.utils.BaseObject.USER_EDIT_REQUEST_CODE
import com.orbitalsonic.roomdatabasemvvmkoin.utils.ObjectConverter
import com.orbitalsonic.roomdatabasemvvmkoin.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserActivity : BaseActivity() {
    private lateinit var binding: ActivityUserBinding

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var mAdapter: UserListAdapter
    private lateinit var userEntity: UserEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        title = "User Data"


        iniViews()
        createRecyclerView()

        userViewModel.allUserList.observe(this) { userList ->

            userList.let {
                mAdapter.submitList(it)
            }

        }
    }

    private fun iniViews(){

        binding.addUser.setOnClickListener {
            val intent=Intent(this, UserEditActivity::class.java)
            intent.putExtra("UserRequestCode", USER_ADD_REQUEST_CODE)
            startActivityForResult(intent, USER_ADD_REQUEST_CODE)
        }

    }

    private fun createRecyclerView() {
        mAdapter = UserListAdapter()
        binding.userRecyclerview.adapter = mAdapter
        binding.userRecyclerview.layoutManager = LinearLayoutManager(this)

        mAdapter.setOnItemClickListener(object : OnUserClickListener {
            override fun onItemClick(position: Int) {
                userEntity = mAdapter.currentList[position]

                val intent = Intent(this@UserActivity, UserEditActivity::class.java)
                intent.putExtra("UserRequestCode", USER_EDIT_REQUEST_CODE)
                intent.putExtra("UserData", ObjectConverter.fromObjectToString(userEntity))
                startActivityForResult(intent, USER_EDIT_REQUEST_CODE)

            }


        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userEntity = mAdapter.currentList[viewHolder.adapterPosition]
                userViewModel.deleteUser(userEntity)
                showMessage("Item Deleted!")
            }
        }).attachToRecyclerView(binding.userRecyclerview)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if ( resultCode == Activity.RESULT_OK) {
            if (requestCode== USER_ADD_REQUEST_CODE){
                intentData?.getStringExtra("UserData")?.let { reply ->
                    val mUser = ObjectConverter.fromStringToObject(reply)
                    userViewModel.insertUser(mUser)
                }

            }

            if (requestCode== USER_EDIT_REQUEST_CODE){
                intentData?.getStringExtra("UserData")?.let { reply ->
                    val mUser = ObjectConverter.fromStringToObject(reply)
                    userViewModel.updateUser(mUser)
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.user_remove -> {
                clearData()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private  fun clearData(){
        userViewModel.deleteAllUser()
    }
}