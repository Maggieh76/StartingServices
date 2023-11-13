package com.example.startingservices

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    lateinit var timerBinder: TimerService.TimerBinder
    var isConnected = false
    lateinit var time : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time = findViewById<EditText>(R.id.editText)

        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                timerBinder = service as TimerService.TimerBinder
                //timerBinder.setHandler(timerHandler)
                isConnected = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isConnected = false
            }
        }
        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        findViewById<Button>(R.id.button).setOnClickListener {
            var input = time.text.toString().toInt()
            if (isConnected) timerBinder.start(input)
        }
    }
}