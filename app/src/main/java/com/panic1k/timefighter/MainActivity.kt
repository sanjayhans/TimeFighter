package com.panic1k.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
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
    internal val TAG = MainActivity::class.java.simpleName
    internal var timeLeftOnTimer: Long = 60000

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "On Create Called. Score is: $score")

        tapMeButton = findViewById<Button>(R.id.tap_me_button)
        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        resetgame()

        tapMeButton.setOnClickListener {
            incrementScore()

        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()
        Log.d(TAG, "onSaveInstanceState: Saving Score : $score & Time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called.")
    }
    private fun resetgame() {
        score = 0
        gameScoreTextView.text = getString(R.string.your_score, score.toString())
        val initialTiemLeft = initialCountdown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTiemLeft.toString())

        countDownTimer = object : CountDownTimer(initialCountdown, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
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
