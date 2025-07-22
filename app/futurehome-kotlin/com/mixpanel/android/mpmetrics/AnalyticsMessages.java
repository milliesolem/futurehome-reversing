package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.mixpanel.android.util.HttpService;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.RemoteService;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class AnalyticsMessages {
   private static final int CLEAR_ANONYMOUS_UPDATES = 7;
   private static final int EMPTY_QUEUES = 6;
   private static final int ENQUEUE_EVENTS = 1;
   private static final int ENQUEUE_GROUP = 3;
   private static final int ENQUEUE_PEOPLE = 0;
   private static final int FLUSH_QUEUE = 2;
   private static final int KILL_WORKER = 5;
   private static final String LOGTAG = "MixpanelAPI.Messages";
   private static final int PUSH_ANONYMOUS_PEOPLE_RECORDS = 4;
   private static final int REMOVE_RESIDUAL_IMAGE_FILES = 9;
   private static final int REWRITE_EVENT_PROPERTIES = 8;
   private static final Map<String, AnalyticsMessages> sInstances = new HashMap<>();
   protected final MPConfig mConfig;
   protected final Context mContext;
   private final String mInstanceName;
   private final AnalyticsMessages.Worker mWorker;

   AnalyticsMessages(Context var1, MPConfig var2) {
      this.mContext = var1;
      this.mConfig = var2;
      this.mInstanceName = var2.getInstanceName();
      this.mWorker = this.createWorker();
      this.getPoster().checkIsMixpanelBlocked();
   }

   public static AnalyticsMessages getInstance(Context param0, MPConfig param1) {
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
      // 00: getstatic com/mixpanel/android/mpmetrics/AnalyticsMessages.sInstances Ljava/util/Map;
      // 03: astore 2
      // 04: aload 2
      // 05: monitorenter
      // 06: aload 0
      // 07: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 0a: astore 4
      // 0c: aload 1
      // 0d: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getInstanceName ()Ljava/lang/String;
      // 10: astore 3
      // 11: aload 2
      // 12: aload 3
      // 13: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 18: ifne 32
      // 1b: new com/mixpanel/android/mpmetrics/AnalyticsMessages
      // 1e: astore 0
      // 1f: aload 0
      // 20: aload 4
      // 22: aload 1
      // 23: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages.<init> (Landroid/content/Context;Lcom/mixpanel/android/mpmetrics/MPConfig;)V
      // 26: aload 2
      // 27: aload 3
      // 28: aload 0
      // 29: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 2e: pop
      // 2f: goto 3d
      // 32: aload 2
      // 33: aload 3
      // 34: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 39: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages
      // 3c: astore 0
      // 3d: aload 2
      // 3e: monitorexit
      // 3f: aload 0
      // 40: areturn
      // 41: astore 0
      // 42: aload 2
      // 43: monitorexit
      // 44: aload 0
      // 45: athrow
   }

   private void logAboutMessageToMixpanel(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(" (Thread ");
      var2.append(Thread.currentThread().getId());
      var2.append(")");
      MPLog.v("MixpanelAPI.Messages", var2.toString());
   }

   private void logAboutMessageToMixpanel(String var1, Throwable var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      var3.append(" (Thread ");
      var3.append(Thread.currentThread().getId());
      var3.append(")");
      MPLog.v("MixpanelAPI.Messages", var3.toString(), var2);
   }

   public void clearAnonymousUpdatesMessage(AnalyticsMessages.MixpanelDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 7;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   protected AnalyticsMessages.Worker createWorker() {
      return new AnalyticsMessages.Worker(this);
   }

   public void emptyTrackingQueues(AnalyticsMessages.MixpanelDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 6;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   public void eventsMessage(AnalyticsMessages.EventDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 1;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   protected RemoteService getPoster() {
      return new HttpService();
   }

   public long getTrackEngageRetryAfter() {
      return ((AnalyticsMessages.Worker.AnalyticsMessageHandler)this.mWorker.mHandler).getTrackEngageRetryAfter();
   }

   public void groupMessage(AnalyticsMessages.GroupDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 3;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   public void hardKill() {
      Message var1 = Message.obtain();
      var1.what = 5;
      this.mWorker.runMessage(var1);
   }

   boolean isDead() {
      return this.mWorker.isDead();
   }

   protected MPDbAdapter makeDbAdapter(Context var1) {
      return MPDbAdapter.getInstance(var1, this.mConfig);
   }

   public void peopleMessage(AnalyticsMessages.PeopleDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 0;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   public void postToServer(AnalyticsMessages.MixpanelDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 2;
      var2.obj = var1.getToken();
      var2.arg1 = 0;
      this.mWorker.runMessage(var2);
   }

   public void pushAnonymousPeopleMessage(AnalyticsMessages.PushAnonymousPeopleDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 4;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   public void removeResidualImageFiles(File var1) {
      Message var2 = Message.obtain();
      var2.what = 9;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   public void updateEventProperties(AnalyticsMessages.UpdateEventsPropertiesDescription var1) {
      Message var2 = Message.obtain();
      var2.what = 8;
      var2.obj = var1;
      this.mWorker.runMessage(var2);
   }

   static class EventDescription extends AnalyticsMessages.MixpanelMessageDescription {
      private final String mEventName;
      private final boolean mIsAutomatic;
      private final JSONObject mSessionMetadata;

      public EventDescription(String var1, JSONObject var2, String var3) {
         this(var1, var2, var3, false, new JSONObject());
      }

      public EventDescription(String var1, JSONObject var2, String var3, boolean var4, JSONObject var5) {
         super(var3, var2);
         this.mEventName = var1;
         this.mIsAutomatic = var4;
         this.mSessionMetadata = var5;
      }

      public String getEventName() {
         return this.mEventName;
      }

      public JSONObject getProperties() {
         return this.getMessage();
      }

      public JSONObject getSessionMetadata() {
         return this.mSessionMetadata;
      }

      public boolean isAutomatic() {
         return this.mIsAutomatic;
      }
   }

   static class GroupDescription extends AnalyticsMessages.MixpanelMessageDescription {
      public GroupDescription(JSONObject var1, String var2) {
         super(var2, var1);
      }

      @Override
      public String toString() {
         return this.getMessage().toString();
      }
   }

   static class MixpanelDescription {
      private final String mToken;

      public MixpanelDescription(String var1) {
         this.mToken = var1;
      }

      public String getToken() {
         return this.mToken;
      }
   }

   static class MixpanelMessageDescription extends AnalyticsMessages.MixpanelDescription {
      private final JSONObject mMessage;

      public MixpanelMessageDescription(String var1, JSONObject var2) {
         super(var1);
         if (var2 != null && var2.length() > 0) {
            Iterator var7 = var2.keys();

            while (var7.hasNext()) {
               String var3 = (String)var7.next();

               try {
                  var2.get(var3).toString();
               } catch (AssertionError var5) {
                  var2.remove(var3);
                  MPLog.e(
                     "MixpanelAPI.Messages", "Removing people profile property from update (see https://github.com/mixpanel/mixpanel-android/issues/567)", var5
                  );
               } catch (JSONException var6) {
               }
            }
         }

         this.mMessage = var2;
      }

      public JSONObject getMessage() {
         return this.mMessage;
      }
   }

   static class PeopleDescription extends AnalyticsMessages.MixpanelMessageDescription {
      public PeopleDescription(JSONObject var1, String var2) {
         super(var2, var1);
      }

      public boolean isAnonymous() {
         return this.getMessage().has("$distinct_id") ^ true;
      }

      @Override
      public String toString() {
         return this.getMessage().toString();
      }
   }

   static class PushAnonymousPeopleDescription extends AnalyticsMessages.MixpanelDescription {
      private final String mDistinctId;

      public PushAnonymousPeopleDescription(String var1, String var2) {
         super(var2);
         this.mDistinctId = var1;
      }

      public String getDistinctId() {
         return this.mDistinctId;
      }

      @Override
      public String toString() {
         return this.mDistinctId;
      }
   }

   static class UpdateEventsPropertiesDescription extends AnalyticsMessages.MixpanelDescription {
      private final Map<String, String> mProps;

      public UpdateEventsPropertiesDescription(String var1, Map<String, String> var2) {
         super(var1);
         this.mProps = var2;
      }

      public Map<String, String> getProperties() {
         return this.mProps;
      }
   }

   class Worker {
      private long mAveFlushFrequency;
      private long mFlushCount;
      private Handler mHandler;
      private final Object mHandlerLock;
      private long mLastFlushTime;
      private SystemInformation mSystemInformation;
      final AnalyticsMessages this$0;

      public Worker(AnalyticsMessages var1) {
         this.this$0 = var1;
         this.mHandlerLock = new Object();
         this.mFlushCount = 0L;
         this.mAveFlushFrequency = 0L;
         this.mLastFlushTime = -1L;
         this.mHandler = this.restartWorkerThread();
      }

      private void updateFlushFrequency() {
         long var3 = System.currentTimeMillis();
         long var7 = this.mFlushCount;
         long var1 = 1L + var7;
         long var5 = this.mLastFlushTime;
         if (var5 > 0L) {
            var5 = (var3 - var5 + this.mAveFlushFrequency * var7) / var1;
            this.mAveFlushFrequency = var5;
            var5 /= 1000L;
            AnalyticsMessages var9 = this.this$0;
            StringBuilder var10 = new StringBuilder("Average send frequency approximately ");
            var10.append(var5);
            var10.append(" seconds.");
            var9.logAboutMessageToMixpanel(var10.toString());
         }

         this.mLastFlushTime = var3;
         this.mFlushCount = var1;
      }

      public boolean isDead() {
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
         // 00: aload 0
         // 01: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.mHandlerLock Ljava/lang/Object;
         // 04: astore 2
         // 05: aload 2
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.mHandler Landroid/os/Handler;
         // 0b: ifnonnull 13
         // 0e: bipush 1
         // 0f: istore 1
         // 10: goto 15
         // 13: bipush 0
         // 14: istore 1
         // 15: aload 2
         // 16: monitorexit
         // 17: iload 1
         // 18: ireturn
         // 19: astore 3
         // 1a: aload 2
         // 1b: monitorexit
         // 1c: aload 3
         // 1d: athrow
      }

      protected Handler restartWorkerThread() {
         HandlerThread var1 = new HandlerThread("com.mixpanel.android.AnalyticsWorker", 10);
         var1.start();
         return new AnalyticsMessages.Worker.AnalyticsMessageHandler(this, var1.getLooper());
      }

      public void runMessage(Message param1) {
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
         // 00: aload 0
         // 01: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.mHandlerLock Ljava/lang/Object;
         // 04: astore 2
         // 05: aload 2
         // 06: monitorenter
         // 07: aload 0
         // 08: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.mHandler Landroid/os/Handler;
         // 0b: astore 3
         // 0c: aload 3
         // 0d: ifnonnull 35
         // 10: aload 0
         // 11: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
         // 14: astore 4
         // 16: new java/lang/StringBuilder
         // 19: astore 3
         // 1a: aload 3
         // 1b: ldc "Dead mixpanel worker dropping a message: "
         // 1d: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
         // 20: aload 3
         // 21: aload 1
         // 22: getfield android/os/Message.what I
         // 25: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
         // 28: pop
         // 29: aload 4
         // 2b: aload 3
         // 2c: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
         // 2f: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
         // 32: goto 3b
         // 35: aload 3
         // 36: aload 1
         // 37: invokevirtual android/os/Handler.sendMessage (Landroid/os/Message;)Z
         // 3a: pop
         // 3b: aload 2
         // 3c: monitorexit
         // 3d: return
         // 3e: astore 1
         // 3f: aload 2
         // 40: monitorexit
         // 41: aload 1
         // 42: athrow
      }

      class AnalyticsMessageHandler extends Handler {
         private MPDbAdapter mDbAdapter;
         private int mFailedRetries;
         private final long mFlushInterval;
         private long mTrackEngageRetryAfter;
         final AnalyticsMessages.Worker this$1;

         public AnalyticsMessageHandler(AnalyticsMessages.Worker var1, Looper var2) {
            super(var2);
            this.this$1 = var1;
            this.mDbAdapter = null;
            var1.mSystemInformation = SystemInformation.getInstance(var1.this$0.mContext);
            this.mFlushInterval = var1.this$0.mConfig.getFlushInterval();
         }

         private JSONObject getDefaultEventProperties() throws JSONException {
            JSONObject var4 = new JSONObject();
            var4.put("mp_lib", "android");
            var4.put("$lib_version", "7.5.2");
            var4.put("$os", "Android");
            String var2 = VERSION.RELEASE;
            String var3 = "UNKNOWN";
            if (var2 == null) {
               var2 = "UNKNOWN";
            } else {
               var2 = VERSION.RELEASE;
            }

            var4.put("$os_version", var2);
            if (Build.MANUFACTURER == null) {
               var2 = "UNKNOWN";
            } else {
               var2 = Build.MANUFACTURER;
            }

            var4.put("$manufacturer", var2);
            if (Build.BRAND == null) {
               var2 = "UNKNOWN";
            } else {
               var2 = Build.BRAND;
            }

            var4.put("$brand", var2);
            if (Build.MODEL == null) {
               var2 = var3;
            } else {
               var2 = Build.MODEL;
            }

            var4.put("$model", var2);
            DisplayMetrics var10 = this.this$1.mSystemInformation.getDisplayMetrics();
            var4.put("$screen_dpi", var10.densityDpi);
            var4.put("$screen_height", var10.heightPixels);
            var4.put("$screen_width", var10.widthPixels);
            var2 = this.this$1.mSystemInformation.getAppVersionName();
            if (var2 != null) {
               var4.put("$app_version", var2);
               var4.put("$app_version_string", var2);
            }

            Integer var12 = this.this$1.mSystemInformation.getAppVersionCode();
            if (var12 != null) {
               var2 = String.valueOf(var12);
               var4.put("$app_release", var2);
               var4.put("$app_build_number", var2);
            }

            boolean var1 = this.this$1.mSystemInformation.hasNFC();
            Boolean var14 = var1;
            if (var14 != null) {
               var14.getClass();
               var4.put("$has_nfc", var1);
            }

            var1 = this.this$1.mSystemInformation.hasTelephony();
            Boolean var15 = var1;
            if (var15 != null) {
               var15.getClass();
               var4.put("$has_telephone", var1);
            }

            var2 = this.this$1.mSystemInformation.getCurrentNetworkOperator();
            if (var2 != null && !var2.trim().isEmpty()) {
               var4.put("$carrier", var2);
            }

            Boolean var17 = this.this$1.mSystemInformation.isWifiConnected();
            if (var17 != null) {
               var4.put("$wifi", var17);
            }

            Boolean var18 = this.this$1.mSystemInformation.isBluetoothEnabled();
            if (var18 != null) {
               var4.put("$bluetooth_enabled", var18);
            }

            var2 = this.this$1.mSystemInformation.getBluetoothVersion();
            if (var2 != null) {
               var4.put("$bluetooth_version", var2);
            }

            return var4;
         }

         private JSONObject prepareEventObject(AnalyticsMessages.EventDescription var1) throws JSONException {
            JSONObject var4 = new JSONObject();
            JSONObject var2 = var1.getProperties();
            JSONObject var3 = this.getDefaultEventProperties();
            var3.put("token", var1.getToken());
            if (var2 != null) {
               Iterator var5 = var2.keys();

               while (var5.hasNext()) {
                  String var6 = (String)var5.next();
                  var3.put(var6, var2.get(var6));
               }
            }

            var4.put("event", var1.getEventName());
            var4.put("properties", var3);
            var4.put("$mp_metadata", var1.getSessionMetadata());
            return var4;
         }

         private void sendAllData(MPDbAdapter var1, String var2) {
            if (!this.this$1.this$0.getPoster().isOnline(this.this$1.this$0.mContext, this.this$1.this$0.mConfig.getOfflineMode())) {
               this.this$1.this$0.logAboutMessageToMixpanel("Not flushing data to Mixpanel because the device is not connected to the internet.");
            } else {
               this.sendData(var1, var2, MPDbAdapter.Table.EVENTS, this.this$1.this$0.mConfig.getEventsEndpoint());
               this.sendData(var1, var2, MPDbAdapter.Table.PEOPLE, this.this$1.this$0.mConfig.getPeopleEndpoint());
               this.sendData(var1, var2, MPDbAdapter.Table.GROUPS, this.this$1.this$0.mConfig.getGroupsEndpoint());
            }
         }

         private void sendData(MPDbAdapter param1, String param2, MPDbAdapter.Table param3, String param4) {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
            //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
            //
            // Bytecode:
            // 000: aload 0
            // 001: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 004: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 007: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages.getPoster ()Lcom/mixpanel/android/util/RemoteService;
            // 00a: astore 13
            // 00c: aload 1
            // 00d: aload 3
            // 00e: aload 2
            // 00f: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.generateDataString (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)[Ljava/lang/String;
            // 012: astore 12
            // 014: bipush 0
            // 015: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
            // 018: astore 10
            // 01a: aload 12
            // 01c: astore 11
            // 01e: aload 12
            // 020: ifnull 030
            // 023: aload 12
            // 025: bipush 2
            // 026: aaload
            // 027: invokestatic java/lang/Integer.valueOf (Ljava/lang/String;)Ljava/lang/Integer;
            // 02a: astore 10
            // 02c: aload 12
            // 02e: astore 11
            // 030: bipush 0
            // 031: istore 7
            // 033: bipush 0
            // 034: istore 6
            // 036: bipush 0
            // 037: istore 5
            // 039: aload 11
            // 03b: ifnull 370
            // 03e: aload 10
            // 040: invokevirtual java/lang/Integer.intValue ()I
            // 043: ifle 370
            // 046: aload 11
            // 048: bipush 0
            // 049: aaload
            // 04a: astore 12
            // 04c: aload 11
            // 04e: bipush 1
            // 04f: aaload
            // 050: astore 11
            // 052: aload 11
            // 054: invokestatic com/mixpanel/android/util/Base64Coder.encodeString (Ljava/lang/String;)Ljava/lang/String;
            // 057: astore 15
            // 059: new java/util/HashMap
            // 05c: dup
            // 05d: invokespecial java/util/HashMap.<init> ()V
            // 060: astore 14
            // 062: aload 14
            // 064: ldc_w "data"
            // 067: aload 15
            // 069: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
            // 06e: pop
            // 06f: getstatic com/mixpanel/android/mpmetrics/MPConfig.DEBUG Z
            // 072: ifeq 083
            // 075: aload 14
            // 077: ldc_w "verbose"
            // 07a: ldc_w "1"
            // 07d: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
            // 082: pop
            // 083: aload 0
            // 084: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 087: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 08a: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mConfig Lcom/mixpanel/android/mpmetrics/MPConfig;
            // 08d: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getSSLSocketFactory ()Ljavax/net/ssl/SSLSocketFactory;
            // 090: astore 15
            // 092: aload 13
            // 094: aload 4
            // 096: aload 0
            // 097: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 09a: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 09d: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mConfig Lcom/mixpanel/android/mpmetrics/MPConfig;
            // 0a0: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getProxyServerInteractor ()Lcom/mixpanel/android/util/ProxyServerInteractor;
            // 0a3: aload 14
            // 0a5: aload 15
            // 0a7: invokeinterface com/mixpanel/android/util/RemoteService.performRequest (Ljava/lang/String;Lcom/mixpanel/android/util/ProxyServerInteractor;Ljava/util/Map;Ljavax/net/ssl/SSLSocketFactory;)[B 5
            // 0ac: astore 15
            // 0ae: aload 15
            // 0b0: ifnonnull 0ff
            // 0b3: aload 0
            // 0b4: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 0b7: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 0ba: astore 14
            // 0bc: new java/lang/StringBuilder
            // 0bf: astore 11
            // 0c1: aload 11
            // 0c3: invokespecial java/lang/StringBuilder.<init> ()V
            // 0c6: aload 11
            // 0c8: ldc_w "Response was null, unexpected failure posting to "
            // 0cb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 0ce: pop
            // 0cf: aload 11
            // 0d1: aload 4
            // 0d3: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 0d6: pop
            // 0d7: aload 11
            // 0d9: ldc_w "."
            // 0dc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 0df: pop
            // 0e0: aload 14
            // 0e2: aload 11
            // 0e4: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 0e7: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 0ea: iload 6
            // 0ec: istore 5
            // 0ee: goto 2be
            // 0f1: astore 11
            // 0f3: goto 262
            // 0f6: astore 11
            // 0f8: iload 7
            // 0fa: istore 5
            // 0fc: goto 294
            // 0ff: new java/lang/String
            // 102: astore 14
            // 104: aload 14
            // 106: aload 15
            // 108: ldc_w "UTF-8"
            // 10b: invokespecial java/lang/String.<init> ([BLjava/lang/String;)V
            // 10e: aload 0
            // 10f: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 112: ifle 120
            // 115: aload 0
            // 116: bipush 0
            // 117: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 11a: aload 0
            // 11b: bipush 2
            // 11c: aload 2
            // 11d: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.removeMessages (ILjava/lang/Object;)V
            // 120: aload 0
            // 121: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 124: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 127: astore 15
            // 129: new java/lang/StringBuilder
            // 12c: astore 16
            // 12e: aload 16
            // 130: invokespecial java/lang/StringBuilder.<init> ()V
            // 133: aload 16
            // 135: ldc_w "Successfully posted to "
            // 138: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 13b: pop
            // 13c: aload 16
            // 13e: aload 4
            // 140: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 143: pop
            // 144: aload 16
            // 146: ldc_w ": \n"
            // 149: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 14c: pop
            // 14d: aload 16
            // 14f: aload 11
            // 151: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 154: pop
            // 155: aload 15
            // 157: aload 16
            // 159: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 15c: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 15f: aload 0
            // 160: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 163: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 166: astore 15
            // 168: new java/lang/StringBuilder
            // 16b: astore 11
            // 16d: aload 11
            // 16f: invokespecial java/lang/StringBuilder.<init> ()V
            // 172: aload 11
            // 174: ldc_w "Response was "
            // 177: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 17a: pop
            // 17b: aload 11
            // 17d: aload 14
            // 17f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 182: pop
            // 183: aload 15
            // 185: aload 11
            // 187: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 18a: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 18d: bipush 1
            // 18e: istore 5
            // 190: goto 2be
            // 193: astore 14
            // 195: new java/lang/RuntimeException
            // 198: astore 11
            // 19a: aload 11
            // 19c: ldc_w "UTF not supported on this platform?"
            // 19f: aload 14
            // 1a1: invokespecial java/lang/RuntimeException.<init> (Ljava/lang/String;Ljava/lang/Throwable;)V
            // 1a4: aload 11
            // 1a6: athrow
            // 1a7: astore 11
            // 1a9: aload 0
            // 1aa: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 1ad: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 1b0: astore 14
            // 1b2: new java/lang/StringBuilder
            // 1b5: dup
            // 1b6: ldc_w "Cannot post message to "
            // 1b9: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 1bc: astore 15
            // 1be: aload 15
            // 1c0: aload 4
            // 1c2: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 1c5: pop
            // 1c6: aload 15
            // 1c8: ldc_w "."
            // 1cb: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 1ce: pop
            // 1cf: aload 14
            // 1d1: aload 15
            // 1d3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 1d6: aload 11
            // 1d8: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$500 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 1db: goto 257
            // 1de: astore 15
            // 1e0: aload 0
            // 1e1: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 1e4: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 1e7: astore 11
            // 1e9: new java/lang/StringBuilder
            // 1ec: dup
            // 1ed: ldc_w "Cannot post message to "
            // 1f0: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 1f3: astore 14
            // 1f5: aload 14
            // 1f7: aload 4
            // 1f9: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 1fc: pop
            // 1fd: aload 14
            // 1ff: ldc_w "."
            // 202: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 205: pop
            // 206: aload 11
            // 208: aload 14
            // 20a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 20d: aload 15
            // 20f: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$500 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 212: goto 257
            // 215: astore 14
            // 217: aload 0
            // 218: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 21b: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 21e: astore 15
            // 220: new java/lang/StringBuilder
            // 223: dup
            // 224: ldc_w "Cannot post message to "
            // 227: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 22a: astore 11
            // 22c: aload 11
            // 22e: aload 4
            // 230: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 233: pop
            // 234: aload 11
            // 236: ldc_w "."
            // 239: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 23c: pop
            // 23d: aload 15
            // 23f: aload 11
            // 241: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 244: aload 14
            // 246: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$500 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 249: aload 0
            // 24a: aload 14
            // 24c: invokevirtual com/mixpanel/android/util/RemoteService$ServiceUnavailableException.getRetryAfter ()I
            // 24f: sipush 1000
            // 252: imul
            // 253: i2l
            // 254: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 257: bipush 0
            // 258: istore 5
            // 25a: goto 2be
            // 25d: astore 11
            // 25f: bipush 1
            // 260: istore 5
            // 262: new java/lang/StringBuilder
            // 265: dup
            // 266: ldc_w "Cannot interpret "
            // 269: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 26c: astore 14
            // 26e: aload 14
            // 270: aload 4
            // 272: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 275: pop
            // 276: aload 14
            // 278: ldc_w " as a URL."
            // 27b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 27e: pop
            // 27f: ldc_w "MixpanelAPI.Messages"
            // 282: aload 14
            // 284: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 287: aload 11
            // 289: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 28c: goto 2be
            // 28f: astore 11
            // 291: bipush 1
            // 292: istore 5
            // 294: new java/lang/StringBuilder
            // 297: dup
            // 298: ldc_w "Out of memory when posting to "
            // 29b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 29e: astore 14
            // 2a0: aload 14
            // 2a2: aload 4
            // 2a4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 2a7: pop
            // 2a8: aload 14
            // 2aa: ldc_w "."
            // 2ad: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 2b0: pop
            // 2b1: ldc_w "MixpanelAPI.Messages"
            // 2b4: aload 14
            // 2b6: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 2b9: aload 11
            // 2bb: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 2be: iload 5
            // 2c0: ifeq 2f1
            // 2c3: aload 0
            // 2c4: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 2c7: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 2ca: ldc_w "Not retrying this batch of events, deleting them from DB."
            // 2cd: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 2d0: aload 1
            // 2d1: aload 12
            // 2d3: aload 3
            // 2d4: aload 2
            // 2d5: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupEvents (Ljava/lang/String;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 2d8: aload 1
            // 2d9: aload 3
            // 2da: aload 2
            // 2db: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.generateDataString (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)[Ljava/lang/String;
            // 2de: astore 11
            // 2e0: aload 11
            // 2e2: ifnull 2ee
            // 2e5: aload 11
            // 2e7: bipush 2
            // 2e8: aaload
            // 2e9: invokestatic java/lang/Integer.valueOf (Ljava/lang/String;)Ljava/lang/Integer;
            // 2ec: astore 10
            // 2ee: goto 030
            // 2f1: aload 0
            // 2f2: bipush 2
            // 2f3: aload 2
            // 2f4: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.removeMessages (ILjava/lang/Object;)V
            // 2f7: ldc2_w 2.0
            // 2fa: aload 0
            // 2fb: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 2fe: i2d
            // 2ff: invokestatic java/lang/Math.pow (DD)D
            // 302: d2l
            // 303: ldc2_w 60000
            // 306: lmul
            // 307: aload 0
            // 308: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 30b: invokestatic java/lang/Math.max (JJ)J
            // 30e: lstore 8
            // 310: aload 0
            // 311: lload 8
            // 313: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 316: aload 0
            // 317: lload 8
            // 319: ldc2_w 600000
            // 31c: invokestatic java/lang/Math.min (JJ)J
            // 31f: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 322: invokestatic android/os/Message.obtain ()Landroid/os/Message;
            // 325: astore 1
            // 326: aload 1
            // 327: bipush 2
            // 328: putfield android/os/Message.what I
            // 32b: aload 1
            // 32c: aload 2
            // 32d: putfield android/os/Message.obj Ljava/lang/Object;
            // 330: aload 0
            // 331: aload 1
            // 332: aload 0
            // 333: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 336: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.sendMessageDelayed (Landroid/os/Message;J)Z
            // 339: pop
            // 33a: aload 0
            // 33b: aload 0
            // 33c: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 33f: bipush 1
            // 340: iadd
            // 341: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 344: aload 0
            // 345: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 348: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 34b: astore 2
            // 34c: new java/lang/StringBuilder
            // 34f: dup
            // 350: ldc_w "Retrying this batch of events in "
            // 353: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 356: astore 1
            // 357: aload 1
            // 358: aload 0
            // 359: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mTrackEngageRetryAfter J
            // 35c: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
            // 35f: pop
            // 360: aload 1
            // 361: ldc_w " ms"
            // 364: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 367: pop
            // 368: aload 2
            // 369: aload 1
            // 36a: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 36d: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 370: return
         }

         protected long getTrackEngageRetryAfter() {
            return this.mTrackEngageRetryAfter;
         }

         public void handleMessage(Message param1) {
            // $VF: Couldn't be decompiled
            // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
            // java.lang.RuntimeException: parsing failure!
            //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
            //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
            //
            // Bytecode:
            // 000: aload 0
            // 001: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 004: ifnonnull 054
            // 007: aload 0
            // 008: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 00b: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 00e: aload 0
            // 00f: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 012: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 015: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mContext Landroid/content/Context;
            // 018: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages.makeDbAdapter (Landroid/content/Context;)Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 01b: astore 3
            // 01c: aload 0
            // 01d: aload 3
            // 01e: putfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 021: aload 3
            // 022: invokestatic java/lang/System.currentTimeMillis ()J
            // 025: aload 0
            // 026: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 029: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 02c: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mConfig Lcom/mixpanel/android/mpmetrics/MPConfig;
            // 02f: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getDataExpiration ()J
            // 032: lsub
            // 033: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.EVENTS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 036: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupEvents (JLcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)V
            // 039: aload 0
            // 03a: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 03d: invokestatic java/lang/System.currentTimeMillis ()J
            // 040: aload 0
            // 041: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 044: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 047: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mConfig Lcom/mixpanel/android/mpmetrics/MPConfig;
            // 04a: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getDataExpiration ()J
            // 04d: lsub
            // 04e: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 051: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupEvents (JLcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)V
            // 054: aload 1
            // 055: getfield android/os/Message.what I
            // 058: ifne 0d1
            // 05b: aload 1
            // 05c: getfield android/os/Message.obj Ljava/lang/Object;
            // 05f: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription
            // 062: astore 4
            // 064: aload 4
            // 066: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription.isAnonymous ()Z
            // 069: ifeq 073
            // 06c: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 06f: astore 1
            // 070: goto 077
            // 073: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 076: astore 1
            // 077: aload 0
            // 078: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 07b: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 07e: ldc_w "Queuing people record for sending later"
            // 081: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 084: aload 0
            // 085: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 088: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 08b: astore 5
            // 08d: new java/lang/StringBuilder
            // 090: astore 3
            // 091: aload 3
            // 092: ldc_w "    "
            // 095: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 098: aload 3
            // 099: aload 4
            // 09b: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription.toString ()Ljava/lang/String;
            // 09e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 0a1: pop
            // 0a2: aload 5
            // 0a4: aload 3
            // 0a5: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 0a8: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 0ab: aload 4
            // 0ad: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription.getToken ()Ljava/lang/String;
            // 0b0: astore 3
            // 0b1: aload 0
            // 0b2: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 0b5: aload 4
            // 0b7: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription.getMessage ()Lorg/json/JSONObject;
            // 0ba: aload 3
            // 0bb: aload 1
            // 0bc: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.addJSON (Lorg/json/JSONObject;Ljava/lang/String;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)I
            // 0bf: istore 2
            // 0c0: aload 3
            // 0c1: astore 1
            // 0c2: aload 4
            // 0c4: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PeopleDescription.isAnonymous ()Z
            // 0c7: ifeq 348
            // 0ca: bipush 0
            // 0cb: istore 2
            // 0cc: aload 3
            // 0cd: astore 1
            // 0ce: goto 348
            // 0d1: aload 1
            // 0d2: getfield android/os/Message.what I
            // 0d5: bipush 3
            // 0d6: if_icmpne 12e
            // 0d9: aload 1
            // 0da: getfield android/os/Message.obj Ljava/lang/Object;
            // 0dd: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$GroupDescription
            // 0e0: astore 3
            // 0e1: aload 0
            // 0e2: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 0e5: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 0e8: ldc_w "Queuing group record for sending later"
            // 0eb: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 0ee: aload 0
            // 0ef: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 0f2: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 0f5: astore 1
            // 0f6: new java/lang/StringBuilder
            // 0f9: astore 4
            // 0fb: aload 4
            // 0fd: ldc_w "    "
            // 100: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 103: aload 4
            // 105: aload 3
            // 106: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$GroupDescription.toString ()Ljava/lang/String;
            // 109: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 10c: pop
            // 10d: aload 1
            // 10e: aload 4
            // 110: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 113: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 116: aload 3
            // 117: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$GroupDescription.getToken ()Ljava/lang/String;
            // 11a: astore 1
            // 11b: aload 0
            // 11c: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 11f: aload 3
            // 120: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$GroupDescription.getMessage ()Lorg/json/JSONObject;
            // 123: aload 1
            // 124: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.GROUPS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 127: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.addJSON (Lorg/json/JSONObject;Ljava/lang/String;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)I
            // 12a: istore 2
            // 12b: goto 348
            // 12e: aload 1
            // 12f: getfield android/os/Message.what I
            // 132: bipush 1
            // 133: if_icmpne 1bd
            // 136: aload 1
            // 137: getfield android/os/Message.obj Ljava/lang/Object;
            // 13a: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription
            // 13d: astore 4
            // 13f: aload 0
            // 140: aload 4
            // 142: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.prepareEventObject (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription;)Lorg/json/JSONObject;
            // 145: astore 3
            // 146: aload 0
            // 147: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 14a: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 14d: ldc_w "Queuing event for sending later"
            // 150: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 153: aload 0
            // 154: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 157: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 15a: astore 5
            // 15c: new java/lang/StringBuilder
            // 15f: astore 1
            // 160: aload 1
            // 161: ldc_w "    "
            // 164: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 167: aload 1
            // 168: aload 3
            // 169: invokevirtual org/json/JSONObject.toString ()Ljava/lang/String;
            // 16c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 16f: pop
            // 170: aload 5
            // 172: aload 1
            // 173: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 176: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 179: aload 4
            // 17b: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription.getToken ()Ljava/lang/String;
            // 17e: astore 1
            // 17f: aload 0
            // 180: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 183: aload 3
            // 184: aload 1
            // 185: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.EVENTS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 188: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.addJSON (Lorg/json/JSONObject;Ljava/lang/String;Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;)I
            // 18b: istore 2
            // 18c: goto 348
            // 18f: astore 3
            // 190: goto 196
            // 193: astore 3
            // 194: aconst_null
            // 195: astore 1
            // 196: new java/lang/StringBuilder
            // 199: astore 5
            // 19b: aload 5
            // 19d: ldc_w "Exception tracking event "
            // 1a0: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 1a3: aload 5
            // 1a5: aload 4
            // 1a7: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription.getEventName ()Ljava/lang/String;
            // 1aa: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 1ad: pop
            // 1ae: ldc_w "MixpanelAPI.Messages"
            // 1b1: aload 5
            // 1b3: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 1b6: aload 3
            // 1b7: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 1ba: goto 203
            // 1bd: aload 1
            // 1be: getfield android/os/Message.what I
            // 1c1: bipush 4
            // 1c2: if_icmpne 1e4
            // 1c5: aload 1
            // 1c6: getfield android/os/Message.obj Ljava/lang/Object;
            // 1c9: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$PushAnonymousPeopleDescription
            // 1cc: astore 1
            // 1cd: aload 1
            // 1ce: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PushAnonymousPeopleDescription.getDistinctId ()Ljava/lang/String;
            // 1d1: astore 3
            // 1d2: aload 1
            // 1d3: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$PushAnonymousPeopleDescription.getToken ()Ljava/lang/String;
            // 1d6: astore 1
            // 1d7: aload 0
            // 1d8: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 1db: aload 1
            // 1dc: aload 3
            // 1dd: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.pushAnonymousUpdatesToPeopleDb (Ljava/lang/String;Ljava/lang/String;)I
            // 1e0: istore 2
            // 1e1: goto 348
            // 1e4: aload 1
            // 1e5: getfield android/os/Message.what I
            // 1e8: bipush 7
            // 1ea: if_icmpne 209
            // 1ed: aload 1
            // 1ee: getfield android/os/Message.obj Ljava/lang/Object;
            // 1f1: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription
            // 1f4: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription.getToken ()Ljava/lang/String;
            // 1f7: astore 1
            // 1f8: aload 0
            // 1f9: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 1fc: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 1ff: aload 1
            // 200: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupAllEvents (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 203: bipush -3
            // 205: istore 2
            // 206: goto 348
            // 209: aload 1
            // 20a: getfield android/os/Message.what I
            // 20d: bipush 8
            // 20f: if_icmpne 24d
            // 212: aload 1
            // 213: getfield android/os/Message.obj Ljava/lang/Object;
            // 216: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$UpdateEventsPropertiesDescription
            // 219: astore 1
            // 21a: aload 0
            // 21b: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 21e: aload 1
            // 21f: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$UpdateEventsPropertiesDescription.getProperties ()Ljava/util/Map;
            // 222: aload 1
            // 223: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$UpdateEventsPropertiesDescription.getToken ()Ljava/lang/String;
            // 226: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.rewriteEventDataWithProperties (Ljava/util/Map;Ljava/lang/String;)I
            // 229: istore 2
            // 22a: new java/lang/StringBuilder
            // 22d: astore 1
            // 22e: aload 1
            // 22f: invokespecial java/lang/StringBuilder.<init> ()V
            // 232: aload 1
            // 233: iload 2
            // 234: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
            // 237: pop
            // 238: aload 1
            // 239: ldc_w " stored events were updated with new properties."
            // 23c: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 23f: pop
            // 240: ldc_w "MixpanelAPI.Messages"
            // 243: aload 1
            // 244: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 247: invokestatic com/mixpanel/android/util/MPLog.d (Ljava/lang/String;Ljava/lang/String;)V
            // 24a: goto 343
            // 24d: aload 1
            // 24e: getfield android/os/Message.what I
            // 251: bipush 2
            // 252: if_icmpne 27d
            // 255: aload 0
            // 256: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 259: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 25c: ldc_w "Flushing queue due to scheduled or forced flush"
            // 25f: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 262: aload 0
            // 263: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 266: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$200 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)V
            // 269: aload 1
            // 26a: getfield android/os/Message.obj Ljava/lang/Object;
            // 26d: checkcast java/lang/String
            // 270: astore 1
            // 271: aload 0
            // 272: aload 0
            // 273: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 276: aload 1
            // 277: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.sendAllData (Lcom/mixpanel/android/mpmetrics/MPDbAdapter;Ljava/lang/String;)V
            // 27a: goto 203
            // 27d: aload 1
            // 27e: getfield android/os/Message.what I
            // 281: bipush 6
            // 283: if_icmpne 2c0
            // 286: aload 1
            // 287: getfield android/os/Message.obj Ljava/lang/Object;
            // 28a: checkcast com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription
            // 28d: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription.getToken ()Ljava/lang/String;
            // 290: astore 1
            // 291: aload 0
            // 292: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 295: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.EVENTS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 298: aload 1
            // 299: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupAllEvents (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 29c: aload 0
            // 29d: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 2a0: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 2a3: aload 1
            // 2a4: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupAllEvents (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 2a7: aload 0
            // 2a8: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 2ab: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.GROUPS Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 2ae: aload 1
            // 2af: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupAllEvents (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 2b2: aload 0
            // 2b3: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 2b6: getstatic com/mixpanel/android/mpmetrics/MPDbAdapter$Table.ANONYMOUS_PEOPLE Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;
            // 2b9: aload 1
            // 2ba: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.cleanupAllEvents (Lcom/mixpanel/android/mpmetrics/MPDbAdapter$Table;Ljava/lang/String;)V
            // 2bd: goto 203
            // 2c0: aload 1
            // 2c1: getfield android/os/Message.what I
            // 2c4: bipush 5
            // 2c5: if_icmpne 312
            // 2c8: new java/lang/StringBuilder
            // 2cb: astore 1
            // 2cc: aload 1
            // 2cd: ldc_w "Worker received a hard kill. Dumping all events and force-killing. Thread id "
            // 2d0: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 2d3: aload 1
            // 2d4: invokestatic java/lang/Thread.currentThread ()Ljava/lang/Thread;
            // 2d7: invokevirtual java/lang/Thread.getId ()J
            // 2da: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
            // 2dd: pop
            // 2de: ldc_w "MixpanelAPI.Messages"
            // 2e1: aload 1
            // 2e2: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 2e5: invokestatic com/mixpanel/android/util/MPLog.w (Ljava/lang/String;Ljava/lang/String;)V
            // 2e8: aload 0
            // 2e9: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 2ec: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$300 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)Ljava/lang/Object;
            // 2ef: astore 1
            // 2f0: aload 1
            // 2f1: monitorenter
            // 2f2: aload 0
            // 2f3: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 2f6: invokevirtual com/mixpanel/android/mpmetrics/MPDbAdapter.deleteDB ()V
            // 2f9: aload 0
            // 2fa: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 2fd: aconst_null
            // 2fe: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$402 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;Landroid/os/Handler;)Landroid/os/Handler;
            // 301: pop
            // 302: invokestatic android/os/Looper.myLooper ()Landroid/os/Looper;
            // 305: invokevirtual android/os/Looper.quit ()V
            // 308: aload 1
            // 309: monitorexit
            // 30a: goto 343
            // 30d: astore 3
            // 30e: aload 1
            // 30f: monitorexit
            // 310: aload 3
            // 311: athrow
            // 312: aload 1
            // 313: getfield android/os/Message.what I
            // 316: bipush 9
            // 318: if_icmpne 328
            // 31b: aload 1
            // 31c: getfield android/os/Message.obj Ljava/lang/Object;
            // 31f: checkcast java/io/File
            // 322: invokestatic com/mixpanel/android/util/LegacyVersionUtils.removeLegacyResidualImageFiles (Ljava/io/File;)V
            // 325: goto 343
            // 328: new java/lang/StringBuilder
            // 32b: astore 3
            // 32c: aload 3
            // 32d: ldc_w "Unexpected message received by Mixpanel worker: "
            // 330: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 333: aload 3
            // 334: aload 1
            // 335: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
            // 338: pop
            // 339: ldc_w "MixpanelAPI.Messages"
            // 33c: aload 3
            // 33d: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 340: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
            // 343: aconst_null
            // 344: astore 1
            // 345: goto 203
            // 348: iload 2
            // 349: aload 0
            // 34a: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 34d: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 350: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages.mConfig Lcom/mixpanel/android/mpmetrics/MPConfig;
            // 353: invokevirtual com/mixpanel/android/mpmetrics/MPConfig.getBulkUploadLimit ()I
            // 356: if_icmpge 35f
            // 359: iload 2
            // 35a: bipush -2
            // 35c: if_icmpne 3ae
            // 35f: aload 0
            // 360: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFailedRetries I
            // 363: ifgt 3ae
            // 366: aload 1
            // 367: ifnull 3ae
            // 36a: aload 0
            // 36b: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 36e: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 371: astore 4
            // 373: new java/lang/StringBuilder
            // 376: astore 3
            // 377: aload 3
            // 378: ldc_w "Flushing queue due to bulk upload limit ("
            // 37b: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 37e: aload 3
            // 37f: iload 2
            // 380: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
            // 383: pop
            // 384: aload 3
            // 385: ldc_w ") for project "
            // 388: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 38b: pop
            // 38c: aload 3
            // 38d: aload 1
            // 38e: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 391: pop
            // 392: aload 4
            // 394: aload 3
            // 395: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 398: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 39b: aload 0
            // 39c: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 39f: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$200 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)V
            // 3a2: aload 0
            // 3a3: aload 0
            // 3a4: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mDbAdapter Lcom/mixpanel/android/mpmetrics/MPDbAdapter;
            // 3a7: aload 1
            // 3a8: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.sendAllData (Lcom/mixpanel/android/mpmetrics/MPDbAdapter;Ljava/lang/String;)V
            // 3ab: goto 45a
            // 3ae: iload 2
            // 3af: ifle 45a
            // 3b2: aload 0
            // 3b3: bipush 2
            // 3b4: aload 1
            // 3b5: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.hasMessages (ILjava/lang/Object;)Z
            // 3b8: ifne 45a
            // 3bb: aload 0
            // 3bc: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 3bf: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.this$0 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
            // 3c2: astore 3
            // 3c3: new java/lang/StringBuilder
            // 3c6: astore 4
            // 3c8: aload 4
            // 3ca: ldc_w "Queue depth "
            // 3cd: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
            // 3d0: aload 4
            // 3d2: iload 2
            // 3d3: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
            // 3d6: pop
            // 3d7: aload 4
            // 3d9: ldc_w " - Adding flush in "
            // 3dc: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            // 3df: pop
            // 3e0: aload 4
            // 3e2: aload 0
            // 3e3: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFlushInterval J
            // 3e6: invokevirtual java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
            // 3e9: pop
            // 3ea: aload 3
            // 3eb: aload 4
            // 3ed: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
            // 3f0: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages.access$000 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;Ljava/lang/String;)V
            // 3f3: aload 0
            // 3f4: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFlushInterval J
            // 3f7: lconst_0
            // 3f8: lcmp
            // 3f9: iflt 45a
            // 3fc: invokestatic android/os/Message.obtain ()Landroid/os/Message;
            // 3ff: astore 3
            // 400: aload 3
            // 401: bipush 2
            // 402: putfield android/os/Message.what I
            // 405: aload 3
            // 406: aload 1
            // 407: putfield android/os/Message.obj Ljava/lang/Object;
            // 40a: aload 3
            // 40b: bipush 1
            // 40c: putfield android/os/Message.arg1 I
            // 40f: aload 0
            // 410: aload 3
            // 411: aload 0
            // 412: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.mFlushInterval J
            // 415: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.sendMessageDelayed (Landroid/os/Message;J)Z
            // 418: pop
            // 419: goto 45a
            // 41c: astore 3
            // 41d: ldc_w "MixpanelAPI.Messages"
            // 420: ldc_w "Worker threw an unhandled exception"
            // 423: aload 3
            // 424: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 427: aload 0
            // 428: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 42b: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$300 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;)Ljava/lang/Object;
            // 42e: astore 1
            // 42f: aload 1
            // 430: monitorenter
            // 431: aload 0
            // 432: getfield com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker$AnalyticsMessageHandler.this$1 Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;
            // 435: aconst_null
            // 436: invokestatic com/mixpanel/android/mpmetrics/AnalyticsMessages$Worker.access$402 (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$Worker;Landroid/os/Handler;)Landroid/os/Handler;
            // 439: pop
            // 43a: invokestatic android/os/Looper.myLooper ()Landroid/os/Looper;
            // 43d: invokevirtual android/os/Looper.quit ()V
            // 440: ldc_w "MixpanelAPI.Messages"
            // 443: ldc_w "Mixpanel will not process any more analytics messages"
            // 446: aload 3
            // 447: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 44a: goto 458
            // 44d: astore 3
            // 44e: ldc_w "MixpanelAPI.Messages"
            // 451: ldc_w "Could not halt looper"
            // 454: aload 3
            // 455: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            // 458: aload 1
            // 459: monitorexit
            // 45a: return
            // 45b: astore 3
            // 45c: aload 1
            // 45d: monitorexit
            // 45e: aload 3
            // 45f: athrow
         }
      }
   }
}
