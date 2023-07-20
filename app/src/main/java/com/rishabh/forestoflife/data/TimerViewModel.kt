package com.rishabh.forestoflife.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.rishabh.forestoflife.composables.timer


var target = 0L

class TimerViewModel : ViewModel() {
    private val timerLiveData = MutableLiveData<Long>()
    private val stopped = MutableLiveData<Boolean>()

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

    fun setStopped(isStopped : Boolean){
        stopped.postValue(isStopped)
    }

    fun getStopped() : LiveData<Boolean>{
        return stopped
    }

    fun getElapsedTimeNormal() : Long {
        if (timerLiveData.value != null){
            return timerLiveData.value!!
        }

        return 0L
    }

    companion object {
        private lateinit var instance: TimerViewModel

        fun getInstance(context: Context): TimerViewModel {
            if (!::instance.isInitialized) {
                instance = ViewModelProvider(
                    ViewModelStore(),
                    ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
                )[TimerViewModel::class.java]
            }
            return instance
        }
    }
}
