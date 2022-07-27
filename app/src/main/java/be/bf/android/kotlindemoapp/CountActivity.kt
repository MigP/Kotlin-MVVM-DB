package be.bf.android.kotlindemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import be.bf.android.kotlindemoapp.databinding.ActivityCountBinding

class CountActivity : AppCompatActivity() {
    private val countViewModel: MainViewModel by viewModels()
    private lateinit var v: ActivityCountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        v = ActivityCountBinding.inflate(layoutInflater)
        setContentView(v.root)

        countViewModel.count.observe(this) { v.counter.text = it.toString() }
//        v.inc.setOnClickListener { countViewModel.inc() }
    }
}