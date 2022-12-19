import 'dart:async';

import 'package:flutter/services.dart';
import 'dart:io' show Platform;

/// Custom Exception for the plugin,
/// thrown whenever the plugin is used on platforms other than Android
class NotificationException implements Exception {
  String _cause;

  NotificationException(this._cause);

  @override
  String toString() {
    return _cause;
  }
}

class NotificationEvent {
  String? packageMessage;
  String? packageExtraBigText;
  String? packageName;
  String? title;
  String? source;
  String? appId;
  String? notifyKey;
  DateTime timeStamp;

  NotificationEvent(
      {this.packageName,
      this.title,
      this.packageMessage,
      this.packageExtraBigText,
        this.source,
        this.appId,
        this.notifyKey,

        required this.timeStamp});

  factory NotificationEvent.fromMap(Map<dynamic, dynamic> map) {
    DateTime time = DateTime.now();
    String? name = map['packageName'];
    String? message = map['message'];
    String? extraBigText = map['extraBigText'];
    String? title = map['title'];
    String source = map['source'];
    String appId = map['appId'];
    String notifyKey = map['notifyKey'];

    NotificationEvent event = NotificationEvent(
        packageName: name,
        title: title,
        packageMessage: message,
        packageExtraBigText: extraBigText,
        source:source,
        appId:appId,
        notifyKey:notifyKey,
        timeStamp: time);

    return event;
  }

  @override
  String toString() {
    return "Notification Event \n"
        "Package Name: $packageName \n "
        "Title: $title \n"
        "Message: $packageMessage \n"
        "ExtraBigText: $packageExtraBigText \n"
        "Source: $source \n"
        "AppId: $appId \n"
        "NotifyKey: $notifyKey \n"
        "ExtraBigText: $packageExtraBigText \n"
        "Timestamp: $timeStamp \n";
  }
}

NotificationEvent _notificationEvent(dynamic data) {
  return new NotificationEvent.fromMap(data);
}

class Notifications {
  static const EventChannel _notificationEventChannel =
      EventChannel('notifications');

  Stream<NotificationEvent>? _notificationStream;

  Stream<NotificationEvent>? get notificationStream {
    if (Platform.isAndroid) {
      if (_notificationStream == null) {
        _notificationStream = _notificationEventChannel
            .receiveBroadcastStream()
            .map((event) => _notificationEvent(event));
      }
      return _notificationStream;
    }
    throw NotificationException(
        'Notification API exclusively available on Android!');
  }
}
