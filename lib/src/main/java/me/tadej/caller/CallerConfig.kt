package me.tadej.caller

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.READ_PHONE_STATE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Process

class CallerConfig {
  private val interceptors = ArrayList<CallerInterceptor>()

  /**
   * Adds a new [CallerInterceptor].
   */
  fun add(interceptor: CallerInterceptor): CallerConfig {
    interceptors.add(interceptor)
    return this
  }

  /**
   * Starts the [Caller] instance with [CallerInterceptor]s added in this [CallerConfig].
   * If [Caller] is already running, an exception will be thrown.
   *
   * @throws IllegalStateException if [Caller] is already running
   *                               or no [CallerInterceptor] were added.
   */
  fun start(context: Context) {
    if (CallerService.isRunning) {
      throw IllegalStateException("already running");
    }
    if (interceptors.isEmpty()) {
      throw IllegalStateException("at least one interceptor required")
    }
    checkPermission(context, CALL_PHONE, READ_PHONE_STATE)
    CallerService.start(context, interceptors)
  }

  private fun checkPermission(context: Context, vararg permissions: String) {
    for (permission in permissions) {
      if (context.checkPermission(permission, Process.myPid(),
          Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
        throw SecurityException("$permission required")
      }
    }
  }
}
