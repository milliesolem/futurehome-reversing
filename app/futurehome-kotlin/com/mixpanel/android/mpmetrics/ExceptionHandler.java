package com.mixpanel.android.mpmetrics;

import android.os.Process;
import java.lang.Thread.UncaughtExceptionHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionHandler implements UncaughtExceptionHandler {
   private static final int SLEEP_TIMEOUT_MS = 400;
   private static ExceptionHandler sInstance;
   private final UncaughtExceptionHandler mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

   public ExceptionHandler() {
      Thread.setDefaultUncaughtExceptionHandler(this);
   }

   public static void init() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: getstatic com/mixpanel/android/mpmetrics/ExceptionHandler.sInstance Lcom/mixpanel/android/mpmetrics/ExceptionHandler;
      // 03: ifnonnull 27
      // 06: ldc com/mixpanel/android/mpmetrics/ExceptionHandler
      // 08: monitorenter
      // 09: getstatic com/mixpanel/android/mpmetrics/ExceptionHandler.sInstance Lcom/mixpanel/android/mpmetrics/ExceptionHandler;
      // 0c: ifnonnull 1b
      // 0f: new com/mixpanel/android/mpmetrics/ExceptionHandler
      // 12: astore 0
      // 13: aload 0
      // 14: invokespecial com/mixpanel/android/mpmetrics/ExceptionHandler.<init> ()V
      // 17: aload 0
      // 18: putstatic com/mixpanel/android/mpmetrics/ExceptionHandler.sInstance Lcom/mixpanel/android/mpmetrics/ExceptionHandler;
      // 1b: ldc com/mixpanel/android/mpmetrics/ExceptionHandler
      // 1d: monitorexit
      // 1e: goto 27
      // 21: astore 0
      // 22: ldc com/mixpanel/android/mpmetrics/ExceptionHandler
      // 24: monitorexit
      // 25: aload 0
      // 26: athrow
      // 27: return
   }

   private void killProcessAndExit() {
      try {
         Thread.sleep(400L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

      Process.killProcess(Process.myPid());
      System.exit(10);
   }

   @Override
   public void uncaughtException(Thread var1, Throwable var2) {
      MixpanelAPI.allInstances(new MixpanelAPI.InstanceProcessor(this, var2) {
         final ExceptionHandler this$0;
         final Throwable val$e;

         {
            this.this$0 = var1;
            this.val$e = var2x;
         }

         @Override
         public void process(MixpanelAPI var1) {
            if (var1.getTrackAutomaticEvents()) {
               try {
                  JSONObject var2x = new JSONObject();
                  var2x.put("$ae_crashed_reason", this.val$e.toString());
                  var1.track("$ae_crashed", var2x, true);
               } catch (JSONException var3) {
               }
            }
         }
      });
      UncaughtExceptionHandler var3 = this.mDefaultExceptionHandler;
      if (var3 != null) {
         var3.uncaughtException(var1, var2);
      } else {
         this.killProcessAndExit();
      }
   }
}
