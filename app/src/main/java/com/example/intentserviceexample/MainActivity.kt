package com.example.intentserviceexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.intentserviceexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val input = binding.editTextInput.text.toString()

            val serviceIntent = Intent(this, ExampleIntentService::class.java)
            serviceIntent.putExtra("inputExtra", input)

            ContextCompat.startForegroundService(this, serviceIntent)
        }
    }
}