package com.chesystemsdev.entities

import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask
import java.util.UUID

/** Represents a condition that must be met for a session to be valid */
sealed class Condition {
    abstract fun isMet(): Boolean

    data class TimeCondition(
        val expirationTime: Long
    ) : Condition() {
        override fun isMet() = System.currentTimeMillis() < expirationTime
    }
}

/** Represents an active session with conditions and metadata */
data class Session(
    val id: String,
    val startTime: Long,
    val conditions: List<Condition>,
    val metadata: Map<String, Any> = emptyMap() // Changed to Any to support more flexible metadata
) {
    /** Checks if all session conditions are currently met */
    fun isValid(): Boolean {
        return conditions.all { it.isMet() }
    }
}

/** Helper functions for creating sessions */
object SessionHelper {
    /** Creates a new session with specified conditions */
    fun create(
        conditions: List<Condition>,
        metadata: Map<String, Any> = emptyMap()
    ): Session {
        return Session(
            id = UUID.randomUUID().toString(),
            startTime = System.currentTimeMillis(),
            conditions = conditions,
            metadata = metadata
        )
    }

    /** Helper to create a time-limited session */
    fun createTimeLimit(
        durationMs: Long,
        metadata: Map<String, Any> = emptyMap()
    ): Session {
        val timeCondition = Condition.TimeCondition(
            System.currentTimeMillis() + durationMs
        )
        return create(listOf(timeCondition), metadata)
    }
}



// -------------------------------------------------------------------------



/** ViewModel for observing session conditions */
class SessionViewModel(private val session: Session): ViewModel() {
    private var isActive = false
    private var observers = mutableListOf<(Boolean) -> Unit>()
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
    fun addObserver(observer: (Boolean) -> Unit) {
        observers.add(observer)
    }

    /** Remove observer */
    fun removeObserver(observer: (Boolean) -> Unit) {
        observers.remove(observer)
    }
}
