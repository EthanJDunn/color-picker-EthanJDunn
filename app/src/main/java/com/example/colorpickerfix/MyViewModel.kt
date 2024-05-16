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
                if (value.isNotEmpty()) {
                    act.redseekBar.progress = value.toInt()
                }
            }
        }
        viewModelScope.launch {
            prefs.redSwitch.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.redSwitch.isChecked = value.toBoolean()
                    act.redseekBar.isEnabled = value.toBoolean()
                    act.redseekBar.visibility = if (value.toBoolean()) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        viewModelScope.launch {
            prefs.redText.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.redText.setText(value)
                }
            }
        }
        viewModelScope.launch {
            prefs.blueSeekBarProgress.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.blueseekBar.progress = value.toInt()
                }
            }
        }
        viewModelScope.launch {
            prefs.blueSwitch.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.blueSwitch.isChecked = value.toBoolean()
                    act.blueseekBar.isEnabled = value.toBoolean()
                    act.blueseekBar.visibility = if (value.toBoolean()) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        viewModelScope.launch {
            prefs.blueText.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.blueText.setText(value)
                }
            }
        }
        viewModelScope.launch {
            prefs.greenSeekBarProgress.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.greenseekBar.progress = value.toInt()
                }
            }
        }
        viewModelScope.launch {
            prefs.greenSwitch.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.greenSwitch.isChecked = value.toBoolean()
                    act.greenseekBar.isEnabled = value.toBoolean()
                    act.greenseekBar.visibility = if (value.toBoolean()) View.VISIBLE else View.INVISIBLE
                }
            }
        }
        viewModelScope.launch {
            prefs.greenText.collectLatest { value ->
                if (value.isNotEmpty()) {
                    act.greenText.setText(value)
                }
            }
        }
    }
}