package com.example.stepbystep

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var stepCountTextView: TextView
    private lateinit var resetButton: Button

    // Dodanie symulowanego sensora krokomierza
    private val simulatedStepCounterSensor = SimulatedStepCounterSensor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepCountTextView = findViewById(R.id.StepByStep)
        resetButton = findViewById(R.id.resetButton)

        resetButton.setOnClickListener {
            simulatedStepCounterSensor.resetStepCount()
            updateStepCount()
        }
    }

    override fun onResume() {
        super.onResume()

        // Symulacja odczytu z sensora co sekundę
        val handler = Handler(Looper.getMainLooper())
        val runnableCode = object : Runnable {
            override fun run() {
                simulatedStepCounterSensor.onSensorChanged()
                updateStepCount()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnableCode)
    }

    private fun updateStepCount() {
        stepCountTextView.text = simulatedStepCounterSensor.stepCount.toString()
    }
}

// Symulowany sensor krokomierza
class SimulatedStepCounterSensor {

    private val random = Random()
    var stepCount = 0

    fun onSensorChanged() {
        // Symulacja dodawania kroków w sposób losowy
        stepCount += random.nextInt(10)
    }

    fun resetStepCount() {
        stepCount = 0
    }
}
