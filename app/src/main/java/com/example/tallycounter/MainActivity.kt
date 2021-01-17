package com.example.tallycounter

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect.*
import android.os.Vibrator
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

// TODO add information page

class MainActivity : AppCompatActivity() {

    var tallyVal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the textView to 0
        textView_tally.text = tallyVal.toString()
        target_value.isEnabled = target_switch.isChecked

        target_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            target_value.isEnabled = isChecked
        }

        button_reset.setOnClickListener {
            if (tallyVal > 0) {
                vibratePhoneShort()
                tallyVal = 0
                textView_tally.text = tallyVal.toString()
            }
        }

        button_add.setOnClickListener {
            vibratePhoneShort()
            tallyVal++
            textView_tally.text = tallyVal.toString()

            val target = target_value.text.toString()

            if (target_value.isEnabled) {
                if (target.isEmpty()) {
                    showShortToast("The target can't be blank!")
                    tallyVal--
                    textView_tally.text = tallyVal.toString()
                } else if (tallyVal >= target.toInt()) {
                    vibratePhoneLong()
                    showShortToast("You reached your target of $target!")
                    target_switch.isChecked = false
                }
            }

//            if (target.isEmpty() && target_value.isEnabled) {
//            } else {
//
//                if (target.isNotEmpty() && target_value.isEnabled) {
//                }
//            }
        }

        button_subtract.setOnClickListener {
            if (tallyVal > 0) {
                vibratePhoneShort()
                tallyVal--
                textView_tally.text = tallyVal.toString()
            }
        }

    }

    private fun vibratePhoneShort() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(createOneShot(10, DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(10)
            }
        }
    }

    private fun vibratePhoneLong() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(createOneShot(1000, DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(500)
            }
        }
    }

    private fun showShortToast(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        )
            .show()
    }
}
