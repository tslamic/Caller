package me.tadej.caller

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.android.internal.telephony.ITelephony

class CallerService : Service() {
  companion object {
    @JvmStatic internal var isRunning = false // https://stackoverflow.com/a/608600
    @JvmStatic internal val EXTRA_INTERCEPTORS = "me.tadej.caller.EXTRA_INTERCEPTORS"

    @JvmStatic fun start(context: Context, interceptors: List<CallerInterceptor>) {
      val intent = Intent(context, CallerService::class.java)
      intent.putExtra(EXTRA_INTERCEPTORS, ArrayList(interceptors))
      context.startService(intent)
    }

    @JvmStatic fun stop(context: Context) {
      val intent = Intent(context, CallerService::class.java)
      context.stopService(intent)
    }
  }

  private lateinit var manager: TelephonyManager
  private lateinit var listener: PhoneStateListener

  override fun onCreate() {
    super.onCreate()
    manager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val interceptors = intent?.getParcelableArrayListExtra<CallerInterceptor>(
        EXTRA_INTERCEPTORS) ?: throw IllegalStateException("no interceptors")
    listener = CallerStateListener(getTelephonyInstance(), interceptors)
    manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE)
    isRunning = true
    return Service.START_REDELIVER_INTENT
  }

  override fun stopService(name: Intent?): Boolean {
    manager.listen(listener, PhoneStateListener.LISTEN_NONE)
    isRunning = false
    return super.stopService(name)
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  private fun getTelephonyInstance(): ITelephony {
    val method = TelephonyManager::class.java.getDeclaredMethod("getITelephony")
    method.isAccessible = true
    return method.invoke(manager) as ITelephony
  }
}
