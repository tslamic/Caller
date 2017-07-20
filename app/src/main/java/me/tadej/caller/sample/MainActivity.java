package me.tadej.caller.sample;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import me.tadej.caller.Caller;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends AppCompatActivity {
  private static final int REQUEST_CODE = 123;
  private Button toggle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toggle = (Button) findViewById(R.id.toggle);
    toggle.setText(Caller.isRunning() ? R.string.stop : R.string.start);
  }

  @Override protected void onResume() {
    super.onResume();
    if (!hasPermission(CALL_PHONE) || !hasPermission(READ_PHONE_STATE)) {
      ActivityCompat.requestPermissions(this, new String[] {
          CALL_PHONE, READ_PHONE_STATE
      }, REQUEST_CODE);
    }
  }

  @SuppressLint("MissingPermission") @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CODE) {
      for (int i = 0, length = permissions.length; i < length; i++) {
        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
          throw new AssertionError(permissions[i] + " permission required");
        }
      }
    }
  }

  public void onToggle(View v) {
    if (Caller.isRunning()) {
      Caller.stop(this);
      toggle.setText(R.string.start);
    } else {
      Caller.newConfig().add(new HangUpInterceptor()).start(this);
      toggle.setText(R.string.stop);
    }
  }

  private boolean hasPermission(String permission) {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
  }
}
