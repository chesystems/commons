package com.chesystemsdev.entities.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chesystemsdev.entities.Session
import java.util.Timer
import java.util.TimerTask

private typealias MultiSessionObserver = (sessionId: String, isValid: Boolean) -> Unit

/** Factory for multiple Session monitoring */
class MultiSessionFacto(
    private val session: Session
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MultiSessionMo(session) as T
}

/** ViewModel for observing multiple Sessions' Conditions */
class MultiSessionMo(
    initialSession: Session
) : BaseSessionMo() {
    private val sessions = mutableMapOf<String, Session>()
    private val sessionStates = mutableMapOf<String, Boolean>()
    private var observers = mutableListOf<MultiSessionObserver>()

    init {
        addSession(initialSession)
    }

    fun addSession(session: Session) {
        sessions[session.id] = session
        sessionStates[session.id] = true
    }

    fun removeSession(sessionId: String) {
        sessions.remove(sessionId)
        sessionStates.remove(sessionId)
    }

    override fun startObserving(checkIntervalMs: Long) {
        if (checkTask != null) return

        checkTask = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    sessions.forEach { (sessionId, session) ->
                        val isValid = session.isValid()
                        if (isValid != sessionStates[sessionId]) {
                            sessionStates[sessionId] = isValid
                            observers.forEach { it(sessionId, isValid) }
                        }

                        if (!isValid) {
                            removeSession(sessionId)
                        }
                    }

                    if (sessions.isEmpty()) {
                        stopObserving()
                    }
                }
            }, 0, checkIntervalMs)
        }
    }

    fun addObserver(observer: MultiSessionObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: MultiSessionObserver) {
        observers.remove(observer)
    }
}