package com.chesystemsdev.session

/** Business transaction interface for requests, orders, etc. */
interface Transaction {
    /** Unique ID */
    val id: String
    
    /** Source reference */
    val sourceRef: String
    
    /** Current status */
    val status: Status

    /** Transaction states */
    enum class Status {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED,
        CANCELLED
    }
}