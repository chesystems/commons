package com.chesystemsdev.entities

import java.util.UUID

/** Represents a user profile with basic information */
data class UserProfile(
    val id: String,
    val username: String,
    val metadata: Map<String, Any> = emptyMap()
) {
    companion object {
        /** Creates a new user profile */
        fun create(
            username: String,
            metadata: Map<String, Any> = emptyMap()
        ): UserProfile {
            return UserProfile(
                id = UUID.randomUUID().toString(),
                username = username,
                metadata = metadata
            )
        }
    }

    /** Creates a session for this user */
    fun createSession(
        durationMs: Long = 3600000, // 1 hour default
        additionalMetadata: Map<String, Any> = emptyMap()
    ): Session {
        val sessionMetadata = mapOf(
            "userId" to id,
            "username" to username
        ) + additionalMetadata
        
        return SessionHelper.createTimeLimit(
            durationMs = durationMs,
            metadata = sessionMetadata
        )
    }
}


