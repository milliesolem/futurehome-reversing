package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.StringCodec;
import java.util.Locale;

public class LifecycleChannel {
   private static final String CHANNEL_NAME = "flutter/lifecycle";
   private static final String TAG = "LifecycleChannel";
   private final BasicMessageChannel<String> channel;
   private LifecycleChannel.AppLifecycleState lastAndroidState = null;
   private LifecycleChannel.AppLifecycleState lastFlutterState = null;
   private boolean lastFocus = true;

   public LifecycleChannel(DartExecutor var1) {
      this(new BasicMessageChannel<>(var1, "flutter/lifecycle", StringCodec.INSTANCE));
   }

   public LifecycleChannel(BasicMessageChannel<String> var1) {
      this.channel = var1;
   }

   private void sendState(LifecycleChannel.AppLifecycleState var1, boolean var2) {
      LifecycleChannel.AppLifecycleState var4 = this.lastAndroidState;
      if (var4 != var1 || var2 != this.lastFocus) {
         if (var1 == null && var4 == null) {
            this.lastFocus = var2;
         } else {
            int var3 = <unrepresentable>.$SwitchMap$io$flutter$embedding$engine$systemchannels$LifecycleChannel$AppLifecycleState[var1.ordinal()];
            if (var3 != 1) {
               if (var3 != 2 && var3 != 3 && var3 != 4 && var3 != 5) {
                  var4 = null;
               } else {
                  var4 = var1;
               }
            } else if (var2) {
               var4 = LifecycleChannel.AppLifecycleState.RESUMED;
            } else {
               var4 = LifecycleChannel.AppLifecycleState.INACTIVE;
            }

            this.lastAndroidState = var1;
            this.lastFocus = var2;
            if (var4 != this.lastFlutterState) {
               StringBuilder var6 = new StringBuilder("AppLifecycleState.");
               var6.append(var4.name().toLowerCase(Locale.ROOT));
               String var7 = var6.toString();
               StringBuilder var5 = new StringBuilder("Sending ");
               var5.append(var7);
               var5.append(" message.");
               Log.v("LifecycleChannel", var5.toString());
               this.channel.send(var7);
               this.lastFlutterState = var4;
            }
         }
      }
   }

   public void aWindowIsFocused() {
      this.sendState(this.lastAndroidState, true);
   }

   public void appIsDetached() {
      this.sendState(LifecycleChannel.AppLifecycleState.DETACHED, this.lastFocus);
   }

   public void appIsInactive() {
      this.sendState(LifecycleChannel.AppLifecycleState.INACTIVE, this.lastFocus);
   }

   public void appIsPaused() {
      this.sendState(LifecycleChannel.AppLifecycleState.PAUSED, this.lastFocus);
   }

   public void appIsResumed() {
      this.sendState(LifecycleChannel.AppLifecycleState.RESUMED, this.lastFocus);
   }

   public void noWindowsAreFocused() {
      this.sendState(this.lastAndroidState, false);
   }

   private static enum AppLifecycleState {
      DETACHED,
      HIDDEN,
      INACTIVE,
      PAUSED,
      RESUMED;
      private static final LifecycleChannel.AppLifecycleState[] $VALUES = $values();
   }
}
