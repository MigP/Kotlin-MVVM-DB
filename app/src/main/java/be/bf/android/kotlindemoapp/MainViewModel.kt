package be.bf.android.kotlindemoapp

import android.app.Application
import androidx.lifecycle.*
import be.bf.android.kotlindemoapp.dal.dao.UserDao
import be.bf.android.kotlindemoapp.dal.entities.User
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private var _count: MutableLiveData<Int> = MutableLiveData(0)
    val count: LiveData<Int>
        get() = _count

    private val _users = mutableListOf<User>()
    val users: MutableLiveData<List<User>> = MutableLiveData(_users)

    init {
        viewModelScope.launch {
            val context = application.applicationContext
            UserDao(context).findAll().collect {
                it.onEach { user -> println(user) }
                users.value = it
                _count.value = it.size
            }
        }
    }

    fun addUser(user: User) {
        UserDao(getApplication<Application>().applicationContext).insert(user)
        _users.add(user)
        _count.value = _users.size
        users.value = _users
    }
}