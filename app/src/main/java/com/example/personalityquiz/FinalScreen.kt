package com.example.personalityquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_final_screen.*

class FinalScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_screen)
        val adjective = intent.getStringExtra(MainActivity.EXTRA_ADJECTIVE)
        textView_finalScreen_score.text = "You are$adjective"
    }
}