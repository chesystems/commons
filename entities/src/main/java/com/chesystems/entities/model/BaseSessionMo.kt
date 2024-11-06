package com.chesystems.entities.model

import androidx.lifecycle.ViewModel
import java.util.Timer

/** Base ViewModel for session monitoring */
abstract class BaseSessionMo : ViewModel() {
    protected var checkTask: Timer? = null

    /** Start monitoring session conditions */
    abstract fun startObserving(checkIntervalMs: Long = 1000)

    /** Stop monitoring session conditions */
    open fun stopObserving() {
        checkTask?.cancel()
        checkTask = null
    }
}