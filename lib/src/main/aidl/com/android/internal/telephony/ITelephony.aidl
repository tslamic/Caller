package com.android.internal.telephony;

interface ITelephony {
  /**
   * End call if there is a call in progress, otherwise does nothing.
   *
   * @return whether it hung up
   */
  boolean endCall();
}
