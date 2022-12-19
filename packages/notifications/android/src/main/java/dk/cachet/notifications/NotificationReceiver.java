package dk.cachet.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import io.flutter.plugin.common.EventChannel.EventSink;
import java.util.HashMap;
import android.util.Log;

/**
 * Receives events from @NotificationListener
 */

public class NotificationReceiver extends BroadcastReceiver {

  private EventSink eventSink;

  public NotificationReceiver(EventSink eventSink) {
    this.eventSink = eventSink;
  }

  @RequiresApi(api = VERSION_CODES.JELLY_BEAN_MR2)
  @Override
  public void onReceive(Context context, Intent intent) {
    /// Unpack intent contents
    String packageName = intent.getStringExtra(NotificationListener.NOTIFICATION_PACKAGE_NAME);
    String title = intent.getStringExtra(NotificationListener.NOTIFICATION_TITLE);
    String message = intent.getStringExtra(NotificationListener.NOTIFICATION_MESSAGE);
    String extraBigText = intent.getStringExtra(NotificationListener.NOTIFICATION_EXTRA_BIG_TEXT);
    String source = intent.getStringExtra(NotificationListener.NOTIFICATION_SOURCE);
    String appId = intent.getStringExtra(NotificationListener.NOTIFICATION_APP_ID);
    String notifyKey = intent.getStringExtra(NotificationListener.NOTIFICATION_KEY);


    /// Send data back via the Event Sink
    HashMap<String, Object> data = new HashMap<>();
    data.put("packageName", packageName);
    data.put("title", title);
    data.put("message", message);
    data.put("extraBigText", extraBigText);
    data.put("source", source);
    data.put("appId", appId);
    data.put("notifyKey", notifyKey);

    // Log.e("TAG", data.toString());

    eventSink.success(data);
  }
}
