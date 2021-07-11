package com.example.swenson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvLogcat = findViewById<AppCompatTextView>(R.id.tvLogcat)
        tvLogcat.text = (SwensonTasks.loadMainQuestions())

        val btn = findViewById<AppCompatButton>(R.id.btnConvert)
        btn.setOnClickListener {
            try {
                val intent = Intent()
                intent.setClassName(this, "com.example.converterlib.ui.ConverterActivity")
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("", e.localizedMessage)
            }
        }
    }
}