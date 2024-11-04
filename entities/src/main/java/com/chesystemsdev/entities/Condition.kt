package com.chesystemsdev.entities

/** Represents a condition that must be met for a session to be valid */
sealed class Condition {
    abstract fun isMet(): Boolean

    data class TimeCondition(
        val expirationTime: Long
    ) : Condition() {
        override fun isMet() = System.currentTimeMillis() < expirationTime
    }
}