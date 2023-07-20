package com.rishabh.forestoflife.data

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rishabh.forestoflife.composables.timer


var target = 0L

class TimerViewModel : ViewModel() {
    private val timerLiveData = MutableLiveData<Long>()

    fun setElapsedTime(elapsedTime: Long) {
        timerLiveData.postValue(elapsedTime)
    }

    fun getTimerLiveData(): LiveData<Long> {
        return timerLiveData
    }

    fun setTimerTarget(timeInMillies: Long){
        target = timeInMillies
    }

    fun getTimerTarget() : Long {
        return target
    }

    fun getElapsedTimeNormal() : Long {
        if (timerLiveData.value != null){
            return timerLiveData.value!!
        }

        return 0L
    }
}
