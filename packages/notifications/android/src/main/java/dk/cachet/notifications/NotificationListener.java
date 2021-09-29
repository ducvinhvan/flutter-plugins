package dk.cachet.notifications;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.annotation.RequiresApi;
import android.util.Log;
/**
 * Notification listening service. Intercepts notifications if permission is
 * given to do so.
 */
@SuppressLint("OverrideAbstract")
@RequiresApi(api = VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {

  public static String NOTIFICATION_INTENT = "notification_event";
  public static String NOTIFICATION_PACKAGE_NAME = "notification_package_name";
  public static String NOTIFICATION_MESSAGE = "notification_message";
  public static String NOTIFICATION_EXTRA_BIG_TEXT = "notification_extra_big_text";
  public static String NOTIFICATION_TITLE = "notification_title";

  @RequiresApi(api = VERSION_CODES.KITKAT)
  @Override
  public void onNotificationPosted(StatusBarNotification notification) {
    // Retrieve package name to set as title.
    String packageName = notification.getPackageName();
    // Retrieve extra object from notification to extract payload.
    Bundle extras = notification.getNotification().extras;

    // Pass data from one activity to another.
    Intent intent = new Intent(NOTIFICATION_INTENT);
    intent.putExtra(NOTIFICATION_PACKAGE_NAME, packageName);

    if (extras != null) {
      // if (extras != null) {
      //   for (String key : extras.keySet()) {
      //     Log.e("TAG", key + " : " + (extras.get(key) != null ? extras.get(key) : "NULL"));
      //   }
      // }
      CharSequence title = extras.getCharSequence(Notification.EXTRA_TITLE);
      CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
      CharSequence bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT);
      
      intent.putExtra(NOTIFICATION_TITLE, title != null ? title.toString() : "");
      intent.putExtra(NOTIFICATION_MESSAGE, text != null ? text.toString(): "");
      intent.putExtra(NOTIFICATION_EXTRA_BIG_TEXT, bigText != null ? bigText.toString() : "");
    }
    sendBroadcast(intent);
  }
}
