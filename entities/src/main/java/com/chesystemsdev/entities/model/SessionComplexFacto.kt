package com.chesystemsdev.entities.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chesystemsdev.entities.Session
import java.util.Timer
import java.util.TimerTask

/** Factory for Session instances */
class SessionComplexFacto(
    private val session: Session
) : ViewModelProvider.NewInstanceFactory() {
    /** Creates new SessionViewMo instance */
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SessionMo(session) as T
}

/** ViewModel for observing multiple Sessions' Conditions */
class SessionComplexMo(
    private val initialSession: Session
): ViewModel() {
    private val sessions = mutableMapOf<String, Session>()
    private val sessionStates = mutableMapOf<String, Boolean>()
    private var observers = mutableListOf<ComplexSessionObserver>()
    private var checkTask: Timer? = null

    init {
        addSession(initialSession)
    }

    /** Add a new session to monitor */
    fun addSession(session: Session) {
        sessions[session.id] = session  // Assuming Session has an id property
        sessionStates[session.id] = true
    }

    /** Remove a session from monitoring */
    fun removeSession(sessionId: String) {
        sessions.remove(sessionId)
        sessionStates.remove(sessionId)
    }

    /** Start monitoring all session conditions */
    fun startObserving(checkIntervalMs: Long = 1000) {
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

    /** Stop monitoring session conditions */
    fun stopObserving() {
        checkTask?.cancel()
        checkTask = null
    }

    /** Add observer for session validity changes */
    fun addObserver(observer: ComplexSessionObserver) {
        observers.add(observer)
    }

    /** Remove observer */
    fun removeObserver(observer: ComplexSessionObserver) {
        observers.remove(observer)
    }
}

private typealias ComplexSessionObserver = (sessionId: String, isValid: Boolean) -> Unit