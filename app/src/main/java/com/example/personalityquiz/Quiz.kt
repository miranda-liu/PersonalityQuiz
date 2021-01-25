package com.example.personalityquiz

import androidx.annotation.IntegerRes
import java.security.KeyStore

// include in the constructor a parameter for the list of questions
// include variables for the score

//alt + control + l formats everything for you
class Quiz(var questions: List<Question>) {
    /**
     * @return: whether or not another question exists in the list of questions
     */
    var questionsAsked  = 0
    var score = 0

    // if the number of questions asked is greater than or equal to total questions, return false
    fun hasNextQuestion(): Boolean {
        return questionsAsked < questions.size-1
    }

    /**
     * Precondition: another question exists in the list of questions to return
     */
    // only call this if you know you have another question
    fun getNextQuestion(): Question {
        questionsAsked++
        return questions.elementAt(questionsAsked - 1)
    }

    fun checkAnswer(answerSelected:String){
        // compare the parameter that the user selected to the correct answer of the current question
        score += questions[questionsAsked - 1].answers[answerSelected] ?: 0
                // ?: gives the default value of ___ if the thing you're trying to find is null
    }
}
