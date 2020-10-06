package io.github.ccjhr.kotlinutils

import java.lang.System.nanoTime
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Not 100% accurate, because the creation of the Thread takes some time.
 * Usage:
 * ```
 * val result = withTimeout(duration = 6000, unit = MILLISECONDS) {
 *   sleep(5999)
 *   "The result is here"
 * }
 * ```
 */
inline fun <reified T> withTimeout(duration: Long, unit: TimeUnit = SECONDS, action: ()->T): T {
    val duration = when(unit) {
        NANOSECONDS -> duration
        MICROSECONDS -> duration * 1000
        MILLISECONDS -> duration * 1_000_000
        SECONDS -> duration * 1_000_000_000
        MINUTES -> duration * 1_000_000_000 * 60
        HOURS -> duration * 1_000_000_000 * 3600
        DAYS -> duration * 1_000_000_000 * 3600 * 24
    }

    val isDone = AtomicBoolean(false)
    val now = nanoTime()
    val max = now + duration

    Thread {
        while (nanoTime() <= max && !isDone.get()) { }

        if (!isDone.get())
            throw InterruptedException("Timeout")
    }.start()

    val result =  action.invoke()
    isDone.set(true)

    return result
}
