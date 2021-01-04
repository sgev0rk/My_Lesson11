package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.myapp.databinding.SecondActivityBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    companion object {

        private const val monthX: String = "Name"
        private const val dayX = ""

        fun start(context: Context, monthName: String, date: Int) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(monthX, monthName)
            intent.putExtra(dayX, date)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        getData()
    }

    private fun setupBinding() {
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getData() {
        val monthX = intent.getStringExtra(monthX)
        val dayX = intent.getIntExtra(dayX, 0)
        binding.monthDate.text = "Month: $monthX\nDate: $dayX"
    }
}