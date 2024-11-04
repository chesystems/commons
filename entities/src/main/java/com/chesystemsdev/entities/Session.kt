package com.chesystemsdev.entities

import java.util.UUID

/** Represents an active session with conditions and metadata */
data class Session(
    override val id: String,
    val startTime: Long,
    val conditions: List<Condition>,
    val metadata: Map<String, Any> = emptyMap(),
    override val sourceRef: String = metadata["userId"]?.toString() ?: "",
    override val status: Transaction.Status = determineStatus()
) : Transaction {
    /** Checks if all session conditions are currently met */
    fun isValid(): Boolean {
        return conditions.all { it.isMet() }
    }

    private companion object {
        private fun determineStatus(): Transaction.Status {
            // Could be expanded based on your needs
            return Transaction.Status.COMPLETED
        }
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