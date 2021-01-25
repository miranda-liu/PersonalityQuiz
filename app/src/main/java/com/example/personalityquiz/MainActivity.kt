package com.example.personalityquiz

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_final_screen.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    companion object{
        val EXTRA_ADJECTIVE = "adjective"
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonString")

        // parsing the string into our custom objects using Gson
        // use the pairing between collection, list, or array section of
        // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
        val gson = Gson()
        val jsonDataType = object: TypeToken<List<Question>>(){  }.type
        val questions = gson.fromJson<List<Question>>(jsonString, jsonDataType)

        Log.d(TAG, "AFTER: $questions")

        // construct a quiz object
        // get the first question and set the text fields & buttons to match
        // to test, call a few of the functions in the Quiz class and see if they do what you expect them to do.

        var quiz = Quiz(questions)
        var currentQuestion = quiz.getNextQuestion()
        var answers = currentQuestion.answers.keys
        textView_main_question.text = currentQuestion.question
        radioButton_main_questionA.text = answers.elementAt(0)
        radioButton_main_questionB.text = answers.elementAt(1)
        radioButton_main_questionC.text = answers.elementAt(2)
        radioButton_main_questionD.text = answers.elementAt(3)
        radioGroup.clearCheck()

        button_main_next.setOnClickListener(){
            if(quiz.hasNextQuestion()){
                currentQuestion = quiz.getNextQuestion()
                answers = currentQuestion.answers.keys
                textView_main_question.text = currentQuestion.question
                radioButton_main_questionA.text = answers.elementAt(0)
                radioButton_main_questionB.text = answers.elementAt(1)
                radioButton_main_questionC.text = answers.elementAt(2)
                radioButton_main_questionD.text = answers.elementAt(3)
                radioGroup.clearCheck()
            }
            else{
                var adjective = ""
                adjective = if(quiz.score >= 300){
                    " blah"
                } else if(quiz.score >= 200){
                    " blah blah"
                } else{
                    " blah blah blah"
                }

                val finalScoreIntent = Intent(this, FinalScreen::class.java).apply {
                    putExtra(EXTRA_ADJECTIVE, adjective)
                }
                startActivity(finalScoreIntent)

            }
        }

        radioButton_main_questionA.setOnClickListener(){
            quiz.score += 100
        }
        radioButton_main_questionB.setOnClickListener(){
            quiz.score += 75
        }
        radioButton_main_questionC.setOnClickListener(){
            quiz.score += 50
        }
        radioButton_main_questionD.setOnClickListener(){
            quiz.score += 25
        }

        // in the button listeners, when the user clicks on something,
        // it passes the info on to the Quiz class. The Quiz class decides if
        // it's right or wrong and updates its score.
        // We check if there are more questions
        // If there are, we'll ask for another question & set that up
        // otherwise, we're done and go to the final score screen

    }
}

