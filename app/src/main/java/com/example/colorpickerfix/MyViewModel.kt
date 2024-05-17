package com.example.colorpickerfix

import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val CURRENT_RED_KEY = "CURRENT_RED_KEY"
const val CURRENT_GREEN_KEY = "CURRENT_GREEN_KEY"
const val CURRENT_BLUE_KEY = "CURRENT_BLUE_KEY"
const val CURRENT_REDSWITCH_KEY = "CURRENT_REDSWITCH_KEY"
const val CURRENT_GREENSWITCH_KEY = "CURRENT_GREENSWITCH_KEY"
const val CURRENT_BLUESWITCH_KEY = "CURRENT_BLUESWITCH_KEY"

class MyViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {



    private var currentRed: Int
        get() = savedStateHandle[CURRENT_RED_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_RED_KEY, value)

    private var currentGreen: Int
        get() = savedStateHandle[CURRENT_GREEN_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_GREEN_KEY, value)

    private var currentBlue: Int
        get() = savedStateHandle[CURRENT_BLUE_KEY] ?: 0
        set(value) = savedStateHandle.set(CURRENT_BLUE_KEY, value)

    private var currentredSwitch: Boolean
        get() = savedStateHandle[CURRENT_REDSWITCH_KEY] ?: false
        set(value) = savedStateHandle.set(CURRENT_REDSWITCH_KEY, value)

    private var currentgreenSwitch: Boolean
        get() = savedStateHandle[CURRENT_GREENSWITCH_KEY] ?: false
        set(value) = savedStateHandle.set(CURRENT_GREENSWITCH_KEY, value)

    private var currentblueSwitch: Boolean
        get() = savedStateHandle[CURRENT_BLUESWITCH_KEY] ?: false
        set(value) = savedStateHandle.set(CURRENT_BLUESWITCH_KEY, value)


    val currentredProgress: Int
        get() = currentRed

    val currentgreenProgress: Int
        get() = currentGreen

    val currentblueProgress: Int
        get() = currentBlue

    val currentredEnabled: Boolean
        get() = currentredSwitch

    val currentgreenEnabled: Boolean
        get() = currentgreenSwitch

    val currentblueEnabled: Boolean
        get() = currentblueSwitch

    fun setCurrentValues(red: Int, green: Int, blue: Int, redSwitch: Boolean, greenSwitch: Boolean, blueSwitch: Boolean) {
        currentRed = red
        currentGreen = green
        currentBlue = blue
        currentredSwitch = redSwitch
        Log.d("MyViewModel", "redswitchloaded")
        currentgreenSwitch = greenSwitch
        Log.d("MyViewModel", "greenswitchloaded")
        currentblueSwitch = blueSwitch
        Log.d("MyViewModel", "blueswitchloaded")
    }

    fun sendCurrentValues(currentredProgress: Int,currentgreenProgress: Int, currentblueProgress: Int,currentredEnabled: Boolean, currentgreenEnabled: Boolean, currentblueEnabled: Boolean ){

        val red = currentredProgress
        val green = currentgreenProgress
        val blue = currentblueProgress
        val redSwitch = currentredEnabled
        Log.d("MyViewModel", "redswitchCurrentValues")
        val greenSwitch = currentgreenEnabled
        Log.d("MyViewModel", "greenswitchCurrentValues")
        val blueSwitch = currentblueEnabled
        Log.d("MyViewModel", "blueswitchCurrentValues")

        setCurrentValues(red, green, blue, redSwitch, greenSwitch, blueSwitch)

    }




    private val prefs = MyDataStoreRepository.get()
    //    init {
//        Log.d(TAG, "ViewModel instance created")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "ViewModel instance about to be destroyed")
//    }

    fun saveInput(s: String, index: Int) {
        viewModelScope.launch {
            prefs.saveInput(s, index)

        }
    }

    fun loadInputs(act: MainActivity) {
        viewModelScope.launch {
            prefs.redSeekBarProgress.collectLatest { value ->
                    act.redseekBar.progress = value.toInt()
            }
        }
        viewModelScope.launch {
            prefs.redSwitch.collectLatest { value ->
                    act.redSwitch.isChecked = value.toBoolean()
                    act.redseekBar.isEnabled = value.toBoolean()
            }
        }
        viewModelScope.launch {
            prefs.blueSeekBarProgress.collectLatest { value ->
                    act.blueseekBar.progress = value.toInt()
            }
        }
        viewModelScope.launch {
            prefs.blueSwitch.collectLatest { value ->
                    act.blueSwitch.isChecked = value.toBoolean()
                    act.blueseekBar.isEnabled = value.toBoolean()
            }
        }
        viewModelScope.launch {
            prefs.greenSeekBarProgress.collectLatest { value ->
                    act.greenseekBar.progress = value.toInt()

            }
        }
        viewModelScope.launch {
            prefs.greenSwitch.collectLatest { value ->
                    act.greenSwitch.isChecked = value.toBoolean()
                    act.greenseekBar.isEnabled = value.toBoolean()
            }
        }
    }

}