package com.chesystemsdev.entities.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chesystemsdev.entities.Session
import java.util.Timer
import java.util.TimerTask

/** Factory for Session instances */
class SessionFacto(
    private val session: Session
) : ViewModelProvider.NewInstanceFactory() {
    /** Creates new SessionViewMo instance */
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SessionMo(session) as T
}

/** ViewModel for observing Session's Conditions */
class SessionMo(
    private val session: Session
): ViewModel() {
    private var isActive = false
    private var observers = mutableListOf<SessionObserver>()
    private var checkTask: Timer? = null

    /** Start monitoring session conditions */
    fun startObserving(checkIntervalMs: Long = 1000) {
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

    /** Stop monitoring session conditions */
    fun stopObserving() {
        isActive = false
        checkTask?.cancel()
        checkTask = null
    }

    /** Add observer for session validity changes */
    fun addObserver(observer: SessionObserver) {
        observers.add(observer)
    }

    /** Remove observer */
    fun removeObserver(observer: SessionObserver) {
        observers.remove(observer)
    }
}

private typealias SessionObserver = (isValid: Boolean) -> Unit