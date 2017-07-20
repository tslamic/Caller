package me.tadej.caller

import android.os.Parcelable
import com.android.internal.telephony.ITelephony

interface CallerInterceptor : Parcelable {
  /**
   * Invoked when [ITelephony] instance is idle.
   */
  fun onIdle(telephony: ITelephony)

  /**
   * Invoked when a new call arrived and is ringing or waiting.
   * In the latter case, another call is already active.
   */
  fun onRinging(telephony: ITelephony, incomingNumber: String)

  /**
   * Invoked when at least one call exists that is dialing, active, or on hold,
   * and no calls are ringing or waiting.
   */
  fun onOffHook(telephony: ITelephony)
}
