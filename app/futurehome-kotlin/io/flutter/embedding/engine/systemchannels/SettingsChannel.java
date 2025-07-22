package io.flutter.embedding.engine.systemchannels;

import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.JSONMessageCodec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SettingsChannel {
   static final boolean $assertionsDisabled = false;
   private static final String ALWAYS_USE_24_HOUR_FORMAT = "alwaysUse24HourFormat";
   private static final String BRIEFLY_SHOW_PASSWORD = "brieflyShowPassword";
   public static final String CHANNEL_NAME = "flutter/settings";
   private static final String CONFIGURATION_ID = "configurationId";
   private static final SettingsChannel.ConfigurationQueue CONFIGURATION_QUEUE = new SettingsChannel.ConfigurationQueue();
   private static final String NATIVE_SPELL_CHECK_SERVICE_DEFINED = "nativeSpellCheckServiceDefined";
   private static final String PLATFORM_BRIGHTNESS = "platformBrightness";
   private static final String TAG = "SettingsChannel";
   private static final String TEXT_SCALE_FACTOR = "textScaleFactor";
   public final BasicMessageChannel<Object> channel;

   public SettingsChannel(DartExecutor var1) {
      this.channel = new BasicMessageChannel<>(var1, "flutter/settings", JSONMessageCodec.INSTANCE);
   }

   public static DisplayMetrics getPastDisplayMetrics(int var0) {
      SettingsChannel.ConfigurationQueue.SentConfiguration var1 = CONFIGURATION_QUEUE.getConfiguration(var0);
      DisplayMetrics var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.displayMetrics;
      }

      return var2;
   }

   public static boolean hasNonlinearTextScalingSupport() {
      boolean var0;
      if (VERSION.SDK_INT >= 34) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public SettingsChannel.MessageBuilder startMessage() {
      return new SettingsChannel.MessageBuilder(this.channel);
   }

   public static class ConfigurationQueue {
      private SettingsChannel.ConfigurationQueue.SentConfiguration currentConfiguration;
      private SettingsChannel.ConfigurationQueue.SentConfiguration previousEnqueuedConfiguration;
      private final ConcurrentLinkedQueue<SettingsChannel.ConfigurationQueue.SentConfiguration> sentQueue = new ConcurrentLinkedQueue<>();

      public BasicMessageChannel.Reply enqueueConfiguration(SettingsChannel.ConfigurationQueue.SentConfiguration var1) {
         this.sentQueue.add(var1);
         SettingsChannel.ConfigurationQueue.SentConfiguration var2 = this.previousEnqueuedConfiguration;
         this.previousEnqueuedConfiguration = var1;
         BasicMessageChannel.Reply var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = new BasicMessageChannel.Reply(this, var2) {
               final SettingsChannel.ConfigurationQueue this$0;
               final SettingsChannel.ConfigurationQueue.SentConfiguration val$configurationToRemove;

               {
                  this.this$0 = var1;
                  this.val$configurationToRemove = var2x;
               }

               @Override
               public void reply(Object var1) {
                  this.this$0.sentQueue.remove(this.val$configurationToRemove);
                  if (!this.this$0.sentQueue.isEmpty()) {
                     var1 = new StringBuilder("The queue becomes empty after removing config generation ");
                     var1.append(String.valueOf(this.val$configurationToRemove.generationNumber));
                     Log.e("SettingsChannel", var1.toString());
                  }
               }
            };
         }

         return var3;
      }

      public SettingsChannel.ConfigurationQueue.SentConfiguration getConfiguration(int var1) {
         if (this.currentConfiguration == null) {
            this.currentConfiguration = this.sentQueue.poll();
         }

         while (true) {
            SettingsChannel.ConfigurationQueue.SentConfiguration var2 = this.currentConfiguration;
            if (var2 == null || var2.generationNumber >= var1) {
               var2 = this.currentConfiguration;
               if (var2 == null) {
                  StringBuilder var5 = new StringBuilder("Cannot find config with generation: ");
                  var5.append(String.valueOf(var1));
                  var5.append(", after exhausting the queue.");
                  Log.e("SettingsChannel", var5.toString());
                  return null;
               } else if (var2.generationNumber != var1) {
                  StringBuilder var4 = new StringBuilder("Cannot find config with generation: ");
                  var4.append(String.valueOf(var1));
                  var4.append(", the oldest config is now: ");
                  var4.append(String.valueOf(this.currentConfiguration.generationNumber));
                  Log.e("SettingsChannel", var4.toString());
                  return null;
               } else {
                  return this.currentConfiguration;
               }
            }

            this.currentConfiguration = this.sentQueue.poll();
         }
      }

      public static class SentConfiguration {
         private static int nextConfigGeneration;
         private final DisplayMetrics displayMetrics;
         public final int generationNumber;

         public SentConfiguration(DisplayMetrics var1) {
            int var2 = nextConfigGeneration++;
            this.generationNumber = var2;
            this.displayMetrics = var1;
         }
      }
   }

   public static class MessageBuilder {
      private final BasicMessageChannel<Object> channel;
      private DisplayMetrics displayMetrics;
      private Map<String, Object> message = new HashMap<>();

      MessageBuilder(BasicMessageChannel<Object> var1) {
         this.channel = var1;
      }

      public void send() {
         StringBuilder var1 = new StringBuilder("Sending message: \ntextScaleFactor: ");
         var1.append(this.message.get("textScaleFactor"));
         var1.append("\nalwaysUse24HourFormat: ");
         var1.append(this.message.get("alwaysUse24HourFormat"));
         var1.append("\nplatformBrightness: ");
         var1.append(this.message.get("platformBrightness"));
         Log.v("SettingsChannel", var1.toString());
         DisplayMetrics var3 = this.displayMetrics;
         if (SettingsChannel.hasNonlinearTextScalingSupport() && var3 != null) {
            SettingsChannel.ConfigurationQueue.SentConfiguration var4 = new SettingsChannel.ConfigurationQueue.SentConfiguration(var3);
            BasicMessageChannel.Reply var2 = SettingsChannel.CONFIGURATION_QUEUE.enqueueConfiguration(var4);
            this.message.put("configurationId", var4.generationNumber);
            this.channel.send(this.message, var2);
         } else {
            this.channel.send(this.message);
         }
      }

      public SettingsChannel.MessageBuilder setBrieflyShowPassword(boolean var1) {
         this.message.put("brieflyShowPassword", var1);
         return this;
      }

      public SettingsChannel.MessageBuilder setDisplayMetrics(DisplayMetrics var1) {
         this.displayMetrics = var1;
         return this;
      }

      public SettingsChannel.MessageBuilder setNativeSpellCheckServiceDefined(boolean var1) {
         this.message.put("nativeSpellCheckServiceDefined", var1);
         return this;
      }

      public SettingsChannel.MessageBuilder setPlatformBrightness(SettingsChannel.PlatformBrightness var1) {
         this.message.put("platformBrightness", var1.name);
         return this;
      }

      public SettingsChannel.MessageBuilder setTextScaleFactor(float var1) {
         this.message.put("textScaleFactor", var1);
         return this;
      }

      public SettingsChannel.MessageBuilder setUse24HourFormat(boolean var1) {
         this.message.put("alwaysUse24HourFormat", var1);
         return this;
      }
   }

   public static enum PlatformBrightness {
      dark("dark"),
      light("light");

      private static final SettingsChannel.PlatformBrightness[] $VALUES = $values();
      public String name;

      private PlatformBrightness(String var3) {
         this.name = var3;
      }
   }
}
