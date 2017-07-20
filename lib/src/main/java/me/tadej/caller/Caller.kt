package me.tadej.caller

import android.content.Context

object Caller {
  /**
   * Stops the [Caller] instance, if running.
   */
  @JvmStatic fun stop(context: Context) = CallerService.stop(context)

  /**
   * Determines if the [Caller] instance is running.
   * @return `true` if running, `false` otherwise.
   */
  @JvmStatic fun isRunning(): Boolean = CallerService.isRunning

  /**
   * Determines if the [Caller] instance is not running.
   * @return `true` if not running, `false` otherwise.
   */
  @JvmStatic fun isNotRunning() = !isRunning()

  /**
   * Returns a new [CallerConfig] instance.
   * @return a new [CallerConfig] instance.
   */
  @JvmStatic fun newConfig() = CallerConfig()
}
