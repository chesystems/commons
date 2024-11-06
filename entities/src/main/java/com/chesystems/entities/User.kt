package com.chesystems.entities

import java.util.UUID

/** Represents a user profile with basic information */
interface User<ID, M> {
    val id: ID
    val username: String
    val metadata: M
    
    companion object {
        /** Creates a simple user with String ID and Map metadata */
        fun create(
            username: String,
            metadata: Map<String, Any> = emptyMap()
        ): User<String, Map<String, Any>> = SimpleUser(
            id = UUID.randomUUID().toString(),
            username = username,
            metadata = metadata
        )
    }
}

/** Basic implementation of User */
private data class SimpleUser<ID, M>(
    override val id: ID,
    override val username: String,
    override val metadata: M
) : User<ID, M>