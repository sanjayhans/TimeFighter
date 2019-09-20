package com.panic1k.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal lateinit var tapMeButton: Button
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal var score = 0
    internal var gameStarted = false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountdown: Long = 60000
    internal val countDownInterval: Long = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tapMeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        resetgame()

        tapMeButton.setOnClickListener {
            incrementScore()

        }

    }

    private fun resetgame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val initialTiemLeft = initialCountdown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTiemLeft.toString())

        countDownTimer = object : CountDownTimer(initialCountdown, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft.toString())

            }

            override fun onFinish() {

            }

        }
        gameStarted = false


    }

    private fun endGame() {
        Toast.makeText(
            this,
            getString(R.string.game_over_message, score.toString()),
            Toast.LENGTH_SHORT
        )
        resetgame()
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun incrementScore() {

        if (!gameStarted) {
            startGame()
        }
        score = score + 1
        val newScore = getString(R.string.your_score, score.toString())
        gameScoreTextView.text = newScore


    }

}
