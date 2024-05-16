package com.example.rgbcolorpickerdialog

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Button
import android.widget.TextView
import android.widget.SeekBar
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.example.rgbcolorpickerdialog.databinding.RgbLayoutDialogBinding
import android.view.View
import android.widget.EditText
import androidx.core.graphics.toColor

class MainActivity : AppCompatActivity() {

    private val rgbLayoutDialogBinding: RgbLayoutDialogBinding by lazy {
        RgbLayoutDialogBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val colorTxt = findViewById<TextView>(R.id.colorTxt)
        val pickColorBtn = findViewById<Button>(R.id.pickColorBtn)
//        inputText = (EditText) findViewById(R.id.colorValueTxt)
//        val colorSwtch = findViewById<Switch>(R.id.colorSwitch)


        val rgbDialog = Dialog(this).apply {
            setContentView(rgbLayoutDialogBinding.root)
            window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
        }

        setOnSeekbar(
            "R",
            rgbLayoutDialogBinding.redLayout.typeTxt,
            rgbLayoutDialogBinding.redLayout.seekBar,
            rgbLayoutDialogBinding.redLayout.colorValueTxt,

            )
        setOnSeekbar(
            "G",
            rgbLayoutDialogBinding.greenLayout.typeTxt,
            rgbLayoutDialogBinding.greenLayout.seekBar,
            rgbLayoutDialogBinding.greenLayout.colorValueTxt,

            )
        setOnSeekbar(
            "B",
            rgbLayoutDialogBinding.blueLayout.typeTxt,
            rgbLayoutDialogBinding.blueLayout.seekBar,
            rgbLayoutDialogBinding.blueLayout.colorValueTxt,

            )
        rgbLayoutDialogBinding.cancelBtn.setOnClickListener {
            rgbDialog.dismiss()
        }
        rgbLayoutDialogBinding.pickBtn.setOnClickListener {
            colorTxt.text = setRGBColor()
            rgbDialog.dismiss()
        }

        rgbLayoutDialogBinding.resetBtn.setOnClickListener {
            rgbLayoutDialogBinding.blueLayout.seekBar.setProgress(0);
            rgbLayoutDialogBinding.redLayout.seekBar.setProgress(0);
            rgbLayoutDialogBinding.greenLayout.seekBar.setProgress(0);
            colorTxt.text = setRGBColor()


        }
        setSwitchListener(
            rgbLayoutDialogBinding.redLayout.colorSwitch,
            rgbLayoutDialogBinding.redLayout.seekBar,
            rgbLayoutDialogBinding.redLayout.colorValueTxt,
        )
        setSwitchListener(
            rgbLayoutDialogBinding.blueLayout.colorSwitch,
            rgbLayoutDialogBinding.blueLayout.seekBar,
            rgbLayoutDialogBinding.blueLayout.colorValueTxt,
        )
        setSwitchListener(
            rgbLayoutDialogBinding.greenLayout.colorSwitch,
            rgbLayoutDialogBinding.greenLayout.seekBar,
            rgbLayoutDialogBinding.greenLayout.colorValueTxt,
        )



        pickColorBtn.setOnClickListener {
            rgbDialog.show()
        }
    }


    private fun setOnSeekbar(
        type: String,
        typeTxt: TextView,
        seekBar: SeekBar,
        colorTxt: TextView
    ) {
        typeTxt.text = type
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                colorTxt.text = seekBar.progress.toString()
                setRGBColor()

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        colorTxt.text = seekBar.progress.toString()

    }

    private fun setRGBColor(): String {
        val hex = String.format(
            "#%02x%02x%02x",
            rgbLayoutDialogBinding.redLayout.seekBar.progress,
            rgbLayoutDialogBinding.greenLayout.seekBar.progress,
            rgbLayoutDialogBinding.blueLayout.seekBar.progress,
        )
        rgbLayoutDialogBinding.colorView.setBackgroundColor(Color.parseColor(hex))
        return hex

    }


    private fun setSwitchListener(switch: Switch, seekBar: SeekBar, textView: TextView) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                seekBar.isEnabled = true
                seekBar.visibility = View.VISIBLE
                textView.isEnabled = true
                textView.alpha = 1f

            } else {
                seekBar.progress = 0
                seekBar.visibility = View.INVISIBLE
                textView.isEnabled = false
                textView.alpha = 0.5f
            }
        }
    }

}