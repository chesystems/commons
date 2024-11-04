package com.chesystemsdev.entities

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