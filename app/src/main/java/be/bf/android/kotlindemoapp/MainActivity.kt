package be.bf.android.kotlindemoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import be.bf.android.kotlindemoapp.dal.dao.UserDao
import be.bf.android.kotlindemoapp.dal.entities.User
import be.bf.android.kotlindemoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.count.observe(this) {
            binding.tvEntries.text = "${binding.tvEntries.text.substring(0, binding.tvEntries.text.indexOf(":") + 1)} ${it}"
        }

        var username: String = "Miguel Pinto"
        var password: String = "mig@gmail.com"

        binding.inc.setOnClickListener {
            viewModel.addUser(User(username, password))
        }
    }

    private fun toCounterActivity(v: View) {
        val intent = Intent(this, CountActivity::class.java)
        startActivity(intent)
    }
}