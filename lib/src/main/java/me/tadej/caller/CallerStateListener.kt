package me.tadej.caller

import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.android.internal.telephony.ITelephony

internal class CallerStateListener(val telephony: ITelephony,
    val interceptors: List<CallerInterceptor>) : PhoneStateListener() {
  override fun onCallStateChanged(state: Int, incomingNumber: String?) {
    when (state) {
      TelephonyManager.CALL_STATE_IDLE ->
        for (listener in interceptors) {
          listener.onIdle(telephony)
        }
      TelephonyManager.CALL_STATE_RINGING ->
        for (listener in interceptors) {
          listener.onRinging(telephony, incomingNumber!!)
        }
      TelephonyManager.CALL_STATE_OFFHOOK ->
        for (listener in interceptors) {
          listener.onOffHook(telephony)
        }
    }
  }
}
