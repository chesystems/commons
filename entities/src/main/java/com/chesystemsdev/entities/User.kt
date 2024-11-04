package com.chesystemsdev.entities

import java.util.UUID

/** Represents a user profile with basic information */
data class User(
    val id: String,
    val username: String,
    val metadata: Map<String, Any> = emptyMap()
)

object UserHelper {
    /** Creates a new user profile */
    fun create(
        username: String,
        metadata: Map<String, Any> = emptyMap()
    ): User {
        return User(
            id = UUID.randomUUID().toString(),
            username = username,
            metadata = metadata
        )
    }

    /** Creates a session for this user */
    fun createSession(
        user: User,
        durationMs: Long = 3600000, // 1 hour default
        additionalMetadata: Map<String, Any> = emptyMap()
    ): Session {
        val sessionMetadata = mapOf(
            "userId" to user.id,
            "username" to user.username
        ) + additionalMetadata
        
        return SessionHelper.createTimeLimit(
            durationMs = durationMs,
            metadata = sessionMetadata
        )
    }
}