package com.chesystemsdev.entities.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chesystemsdev.entities.Session
import java.util.Timer
import java.util.TimerTask

private typealias SingleSessionObserver = (isValid: Boolean) -> Unit

/** Factory for single Session monitoring */
class SingleSessionFacto(
    private val session: Session
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SingleSessionMo(session) as T
}

/** ViewModel for observing single Session's Conditions */
class SingleSessionMo(
    private val session: Session
) : BaseSessionMo() {
    private var isActive = false
    private var observers = mutableListOf<SingleSessionObserver>()

    override fun startObserving(checkIntervalMs: Long) {
        if (isActive) return
        isActive = true

        checkTask = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val isValid = session.isValid()
                    observers.forEach { it(isValid) }

                    if (!isValid) {
                        stopObserving()
                    }
                }
            }, 0, checkIntervalMs)
        }
    }

    override fun stopObserving() {
        isActive = false
        super.stopObserving()
    }

    fun addObserver(observer: SingleSessionObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: SingleSessionObserver) {
        observers.remove(observer)
    }
}