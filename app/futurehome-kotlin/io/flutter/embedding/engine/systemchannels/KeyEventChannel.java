package io.flutter.embedding.engine.systemchannels;

import android.view.KeyEvent;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.JSONMessageCodec;
import java.util.HashMap;
import java.util.Map;

public class KeyEventChannel {
   private static final String TAG = "KeyEventChannel";
   public final BasicMessageChannel<Object> channel;

   public KeyEventChannel(BinaryMessenger var1) {
      this.channel = new BasicMessageChannel<>(var1, "flutter/keyevent", JSONMessageCodec.INSTANCE);
   }

   private static BasicMessageChannel.Reply<Object> createReplyHandler(KeyEventChannel.EventResponseHandler var0) {
      return new KeyEventChannel$$ExternalSyntheticLambda0(var0);
   }

   private Map<String, Object> encodeKeyEvent(KeyEventChannel.FlutterKeyEvent var1, boolean var2) {
      HashMap var4 = new HashMap();
      String var3;
      if (var2) {
         var3 = "keyup";
      } else {
         var3 = "keydown";
      }

      var4.put("type", var3);
      var4.put("keymap", "android");
      var4.put("flags", var1.event.getFlags());
      var4.put("plainCodePoint", var1.event.getUnicodeChar(0));
      var4.put("codePoint", var1.event.getUnicodeChar());
      var4.put("keyCode", var1.event.getKeyCode());
      var4.put("scanCode", var1.event.getScanCode());
      var4.put("metaState", var1.event.getMetaState());
      if (var1.complexCharacter != null) {
         var4.put("character", var1.complexCharacter.toString());
      }

      var4.put("source", var1.event.getSource());
      var4.put("deviceId", var1.event.getDeviceId());
      var4.put("repeatCount", var1.event.getRepeatCount());
      return var4;
   }

   public void sendFlutterKeyEvent(KeyEventChannel.FlutterKeyEvent var1, boolean var2, KeyEventChannel.EventResponseHandler var3) {
      this.channel.send(this.encodeKeyEvent(var1, var2), createReplyHandler(var3));
   }

   public interface EventResponseHandler {
      void onFrameworkResponse(boolean var1);
   }

   public static class FlutterKeyEvent {
      public final Character complexCharacter;
      public final KeyEvent event;

      public FlutterKeyEvent(KeyEvent var1) {
         this(var1, null);
      }

      public FlutterKeyEvent(KeyEvent var1, Character var2) {
         this.event = var1;
         this.complexCharacter = var2;
      }
   }
}
