package com.example.colorpickerfix

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "QuizViewModel"

class MyViewModel : ViewModel() {

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