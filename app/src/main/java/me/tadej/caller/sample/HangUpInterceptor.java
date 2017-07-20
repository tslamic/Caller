package me.tadej.caller.sample;

import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.telephony.ITelephony;
import me.tadej.caller.CallerInterceptor;
import org.jetbrains.annotations.NotNull;

final class HangUpInterceptor implements CallerInterceptor {
  private static final String TAG = HangUpInterceptor.class.getSimpleName();

  public static final Creator<HangUpInterceptor> CREATOR = new Creator<HangUpInterceptor>() {
    @Override public HangUpInterceptor createFromParcel(Parcel source) {
      return new HangUpInterceptor();
    }

    @Override public HangUpInterceptor[] newArray(int size) {
      return new HangUpInterceptor[size];
    }
  };

  @Override public void onIdle(@NotNull ITelephony telephony) {
    Log.d(TAG, "onIdle");
  }

  @Override public void onRinging(@NotNull ITelephony telephony, @NotNull String incomingNumber) {
    Log.d(TAG, "onRinging, incomingNumber=" + incomingNumber);
    try {
      telephony.endCall();
    } catch (RemoteException e) {
      Log.e(TAG, "could not end call due to remote exception", e);
    }
  }

  @Override public void onOffHook(@NotNull ITelephony telephony) {
    Log.d(TAG, "onOffHook");
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {}
}
