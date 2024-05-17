package com.example.colorpickerfix

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelStore
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat


class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    private lateinit var myViewModel: MyViewModel
//    private val myViewModel: MyViewModel by lazy {
//        ViewModelProvider(this).get(MyViewModel::class.java)
//
//
//    }

    lateinit var redseekBar: SeekBar
    lateinit var blueseekBar: SeekBar
    lateinit var greenseekBar: SeekBar
    lateinit var redSwitch: SwitchCompat
    lateinit var greenSwitch: SwitchCompat
    lateinit var blueSwitch: SwitchCompat
    lateinit var saveBtn: Button
    lateinit var resetBtn: Button
    lateinit var redText: EditText
    lateinit var blueText: EditText
    lateinit var greenText: EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colorpickerlayout)

        MyDataStoreRepository.initialize(this)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        connectSeekBars()
        setActions()
        connectViews()
        loadData()
//        Log.d("MainActivity", "BeforeViewModelSave")
//        viewmodelSave()
//        Log.d("MainActivity", "AfterViewModelSave")
//        viewmodelLoad()


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.d("MainActivity", "configchange")

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            setContentView(R.layout.colorpickerlayout)

            Log.d("MainActivity", "MyViewModelPortrait")
            connectViews()
            connectSeekBars()
            setActions()
            viewmodelLoad()

        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {

            setContentView(R.layout.landscape_colorpickerlayout)

            Log.d("MainActivity", "MyViewModelLandscape")

            connectViews()
            connectSeekBars()
            setActions()
            viewmodelLoad()



        }

    }




    private fun connectSeekBars() {
        redseekBar = findViewById(R.id.colorSeekBar)
        greenseekBar = findViewById(R.id.colorSeekBar2)
        blueseekBar = findViewById(R.id.colorSeekBar3)
        redSwitch = findViewById(R.id.colorSwitch)
        greenSwitch = findViewById(R.id.colorSwitch2)
        blueSwitch = findViewById(R.id.colorSwitch3)
        saveBtn = findViewById(R.id.saveButton)
        resetBtn = findViewById(R.id.resetButton)
        redText = findViewById(R.id.editText)
        greenText = findViewById(R.id.editText2)
        blueText = findViewById(R.id.editText3)



        redseekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(findViewById(R.id.linearLayout), R.id.colorSeekBar))
        greenseekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(findViewById(R.id.linearLayout), R.id.colorSeekBar2))
        blueseekBar.setOnSeekBarChangeListener(createSeekBarChangeListener(findViewById(R.id.linearLayout), R.id.colorSeekBar3))

        redSwitch.setOnCheckedChangeListener { _, isChecked ->
            redseekBar.isEnabled = isChecked
            redseekBar.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
            if (redSwitch.isChecked) {

            }
            else {
                redText.setText("0")
                redseekBar.progress = 0
            }
            viewmodelSave()
        }

        greenSwitch.setOnCheckedChangeListener { _, isChecked ->
            greenseekBar.isEnabled = isChecked
            greenseekBar.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
            if (greenSwitch.isChecked) {

            }
            else {
                greenText.setText("0")
                greenseekBar.progress = 0
            }
            viewmodelSave()
        }

        blueSwitch.setOnCheckedChangeListener { _, isChecked ->
            blueseekBar.isEnabled = isChecked
            blueseekBar.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
            if (blueSwitch.isChecked) {


            }
            else {
                blueText.setText("0")
                blueseekBar.progress = 0
            }
            viewmodelSave()
        }

        redText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val progress = s?.toString()?.toIntOrNull() ?: 0
                if (redSwitch.isChecked) {
                    redseekBar.progress = (progress * 100)/255
                }
                viewmodelSave()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        greenText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val progress = s?.toString()?.toIntOrNull() ?: 0
                if (greenSwitch.isChecked) {

                    greenseekBar.progress = (progress * 100)/255
                }
                viewmodelSave()

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        blueText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val progress = s?.toString()?.toIntOrNull() ?: 0
                if (blueSwitch.isChecked) {
                    blueseekBar.progress = (progress * 100)/255
                }
                viewmodelSave()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
    private fun setActions() {
        saveBtn.setOnClickListener { saveData() }
        resetBtn.setOnClickListener { resetData() }
    }
    private fun loadData() {
        myViewModel.loadInputs(this)
    }

    private fun resetData() {
        redseekBar.progress = 0
        greenseekBar.progress = 0
        blueseekBar.progress = 0
        redSwitch.isChecked = false
        blueSwitch.isChecked = false
        greenSwitch.isChecked = false
        redText.setText("0")
        blueText.setText("0")
        greenText.setText("0")

        saveData()

        myViewModel.sendCurrentValues(redseekBar.progress, greenseekBar.progress, blueseekBar.progress, redSwitch.isChecked, greenSwitch.isChecked, blueSwitch.isChecked)


    }

//    private fun loadSavedState() {
//        val savedRed = loadSavedRedValue()
//        val savedGreen = loadSavedGreenValue()
//        val savedBlue = loadSavedBlueValue()
//        val savedRedSwitch = loadSavedRedSwitchValue()
//        val savedGreenSwitch = loadSavedGreenSwitchValue()
//        val savedBlueSwitch = loadSavedBlueSwitchValue()
//
//    }

    private fun viewmodelSave() {
        myViewModel.setCurrentValues(
            redseekBar.progress,
            greenseekBar.progress,
            blueseekBar.progress,
            redSwitch.isChecked,
            greenSwitch.isChecked,
            blueSwitch.isChecked

        )
        Log.d("MainActivity", "viewmodelsave")
    }

    private fun viewmodelLoad(){
        redseekBar.progress = myViewModel.currentredProgress
        greenseekBar.progress = myViewModel.currentgreenProgress
        blueseekBar.progress = myViewModel.currentblueProgress
        redSwitch.isChecked = myViewModel.currentredEnabled
        Log.d("MainActivity", "redswitchloaded")
        greenSwitch.isChecked = myViewModel.currentgreenEnabled
        Log.d("MainActivity", "greenswitchloaded")
        blueSwitch.isChecked = myViewModel.currentblueEnabled
        Log.d("MainActivity", "blueswitchloaded")
        redseekBar.isEnabled = myViewModel.currentredEnabled
        greenseekBar.isEnabled = myViewModel.currentgreenEnabled
        blueseekBar.isEnabled = myViewModel.currentblueEnabled
        Log.d("MainActivity", "viewmodelLoad")
    }
    private fun saveData() {
        myViewModel.saveInput(redseekBar.progress.toString(), 1)
        myViewModel.saveInput(greenseekBar.progress.toString(), 2)
        myViewModel.saveInput(blueseekBar.progress.toString(), 3)
        myViewModel.saveInput(redSwitch.isChecked.toString(), 4)
        myViewModel.saveInput(greenSwitch.isChecked.toString(), 5)
        myViewModel.saveInput(blueSwitch.isChecked.toString(), 6)
//        myViewModel.saveInput(redText.text.toString(), 7)
//        myViewModel.saveInput(blueText.text.toString(), 8)
//        myViewModel.saveInput(greenText.text.toString(), 9)
    }

    private fun connectViews() {
        myViewModel.viewModelScope.launch {

        }
    }

    private fun createSeekBarChangeListener(view: View, viewId: Int): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update the color of the target view based on the SeekBar progress
                updateViewColor(view, viewId, progress)




            }


            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewmodelSave()
            }

        }
    }


    private fun updateViewColor(view: View, viewId: Int, progress: Int) {
        // Get the color based on the SeekBar progress
        val color = getColorFromProgress(progress)

        // Set the background color of the target view
        view.findViewById<View>(R.id.colorView).setBackgroundColor(color)
    }

    private fun getColorFromProgress(progress: Int): Int {
        val red = (255 * redseekBar.progress) / 100 // Assuming the SeekBar ranges from 0 to 100
        val green = (255 * greenseekBar.progress) /100
        val blue = (255 * blueseekBar.progress) /100
        return android.graphics.Color.rgb(red, green, blue)
    }
}

    //private fun update()