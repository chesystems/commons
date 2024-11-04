package com.chesystemsdev.entities.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chesystemsdev.entities.Session
import java.util.Timer
import java.util.TimerTask

private typealias SimpleSessionObserver = (isValid: Boolean) -> Unit

/** Factory for single Session monitoring */
class SessionFacto(
    private val session: Session
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SessionMo(session) as T
}

/** ViewModel for observing single Session's Conditions */
class SessionMo(
    private val session: Session
) : BaseSessionMo() {
    private var isActive = false
    private var observers = mutableListOf<SimpleSessionObserver>()

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

    fun addObserver(observer: SimpleSessionObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: SimpleSessionObserver) {
        observers.remove(observer)
    }
}