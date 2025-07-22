package com.mixpanel.android.mpmetrics;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Build.VERSION;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.ProxyServerInteractor;
import j..util.DesugarTimeZone;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MixpanelAPI {
   private static final String APP_LINKS_LOGTAG = "MixpanelAPI.AL";
   private static final String ENGAGE_DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
   private static final String LOGTAG = "MixpanelAPI.API";
   public static final String VERSION = "7.5.2";
   private static final Map<String, Map<Context, MixpanelAPI>> sInstanceMap = new HashMap<>();
   private static final SharedPreferencesLoader sPrefsLoader = new SharedPreferencesLoader();
   private static Future<SharedPreferences> sReferrerPrefs;
   private final MPConfig mConfig;
   private final Context mContext;
   private final Map<String, String> mDeviceInfo;
   private final Map<String, Long> mEventTimings;
   private final Map<String, MixpanelAPI.GroupImpl> mGroups;
   private final String mInstanceName;
   private final AnalyticsMessages mMessages;
   private MixpanelActivityLifecycleCallbacks mMixpanelActivityLifecycleCallbacks;
   private final MixpanelAPI.PeopleImpl mPeople;
   private final PersistentIdentity mPersistentIdentity;
   private final SessionMetadata mSessionMetadata;
   private final String mToken;
   private final Boolean mTrackAutomaticEvents;

   MixpanelAPI(Context var1, Future<SharedPreferences> var2, String var3, MPConfig var4, boolean var5, JSONObject var6, String var7, boolean var8) {
      this.mContext = var1;
      this.mToken = var3;
      this.mInstanceName = var7;
      this.mPeople = new MixpanelAPI.PeopleImpl(this);
      this.mGroups = new HashMap<>();
      this.mConfig = var4;
      this.mTrackAutomaticEvents = var8;
      HashMap var10 = new HashMap();
      var10.put("$android_lib_version", "7.5.2");
      var10.put("$android_os", "Android");
      String var15 = android.os.Build.VERSION.RELEASE;
      String var9 = "UNKNOWN";
      String var16;
      if (var15 == null) {
         var16 = "UNKNOWN";
      } else {
         var16 = android.os.Build.VERSION.RELEASE;
      }

      var10.put("$android_os_version", var16);
      String var17;
      if (Build.MANUFACTURER == null) {
         var17 = "UNKNOWN";
      } else {
         var17 = Build.MANUFACTURER;
      }

      var10.put("$android_manufacturer", var17);
      String var18;
      if (Build.BRAND == null) {
         var18 = "UNKNOWN";
      } else {
         var18 = Build.BRAND;
      }

      var10.put("$android_brand", var18);
      String var19;
      if (Build.MODEL == null) {
         var19 = var9;
      } else {
         var19 = Build.MODEL;
      }

      var10.put("$android_model", var19);

      try {
         PackageInfo var20 = var1.getPackageManager().getPackageInfo(var1.getPackageName(), 0);
         var10.put("$android_app_version", var20.versionName);
         var10.put("$android_app_version_code", Integer.toString(var20.versionCode));
      } catch (NameNotFoundException var12) {
         MPLog.e("MixpanelAPI.API", "Exception getting app version name", var12);
      }

      this.mDeviceInfo = Collections.unmodifiableMap(var10);
      this.mSessionMetadata = new SessionMetadata();
      this.mMessages = this.getAnalyticsMessages();
      PersistentIdentity var13 = this.getPersistentIdentity(var1, var2, var3, var7);
      this.mPersistentIdentity = var13;
      this.mEventTimings = var13.getTimeEvents();
      if (var5 && (this.hasOptedOutTracking() || !var13.hasOptOutFlag(var3))) {
         this.optOutTracking();
      }

      if (var6 != null) {
         this.registerSuperProperties(var6);
      }

      var5 = MPDbAdapter.getInstance(this.mContext, this.mConfig).getDatabaseFile().exists();
      this.registerMixpanelActivityLifecycleCallbacks();
      if (var13.isFirstLaunch(var5, this.mToken) && this.mTrackAutomaticEvents) {
         this.track("$ae_first_open", null, true);
         var13.setHasLaunched(this.mToken);
      }

      if (this.sendAppOpen() && this.mTrackAutomaticEvents) {
         this.track("$app_open", null);
      }

      if (var13.isNewVersion((String)var10.get("$android_app_version_code")) && this.mTrackAutomaticEvents) {
         try {
            JSONObject var14 = new JSONObject();
            var14.put("$ae_updated_version", var10.get("$android_app_version"));
            this.track("$ae_updated", var14, true);
         } catch (JSONException var11) {
         }
      }

      if (!this.mConfig.getDisableExceptionHandler()) {
         ExceptionHandler.init();
      }

      if (this.mConfig.getRemoveLegacyResidualFiles()) {
         this.mMessages.removeResidualImageFiles(new File(this.mContext.getApplicationInfo().dataDir));
      }
   }

   MixpanelAPI(Context var1, Future<SharedPreferences> var2, String var3, boolean var4, JSONObject var5, String var6, boolean var7) {
      this(var1, var2, var3, MPConfig.getInstance(var1, var6), var4, var5, var6, var7);
   }

   MixpanelAPI(Context var1, Future<SharedPreferences> var2, String var3, boolean var4, JSONObject var5, boolean var6) {
      this(var1, var2, var3, MPConfig.getInstance(var1, null), var4, var5, null, var6);
   }

   static void allInstances(MixpanelAPI.InstanceProcessor param0) {
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
      // 00: getstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sInstanceMap Ljava/util/Map;
      // 03: astore 1
      // 04: aload 1
      // 05: monitorenter
      // 06: aload 1
      // 07: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 0c: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 11: astore 3
      // 12: aload 3
      // 13: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 18: ifeq 4a
      // 1b: aload 3
      // 1c: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 21: checkcast java/util/Map
      // 24: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 29: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 2e: astore 2
      // 2f: aload 2
      // 30: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 35: ifeq 12
      // 38: aload 0
      // 39: aload 2
      // 3a: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 3f: checkcast com/mixpanel/android/mpmetrics/MixpanelAPI
      // 42: invokeinterface com/mixpanel/android/mpmetrics/MixpanelAPI$InstanceProcessor.process (Lcom/mixpanel/android/mpmetrics/MixpanelAPI;)V 2
      // 47: goto 2f
      // 4a: aload 1
      // 4b: monitorexit
      // 4c: return
      // 4d: astore 0
      // 4e: aload 1
      // 4f: monitorexit
      // 50: aload 0
      // 51: athrow
   }

   private static void checkIntentForInboundAppLink(Context var0) {
      if (var0 instanceof Activity) {
         try {
            Class var2 = Class.forName("bolts.AppLinks");
            Intent var9 = ((Activity)var0).getIntent();
            var2.getMethod("getTargetUrlFromInboundIntent", Context.class, Intent.class).invoke(null, var0, var9);
         } catch (InvocationTargetException var3) {
            MPLog.d("MixpanelAPI.AL", "Failed to invoke bolts.AppLinks.getTargetUrlFromInboundIntent() -- Unable to detect inbound App Links", var3);
         } catch (ClassNotFoundException var4) {
            StringBuilder var8 = new StringBuilder("Please install the Bolts library >= 1.1.2 to track App Links: ");
            var8.append(var4.getMessage());
            MPLog.d("MixpanelAPI.AL", var8.toString());
         } catch (NoSuchMethodException var5) {
            StringBuilder var1 = new StringBuilder("Please install the Bolts library >= 1.1.2 to track App Links: ");
            var1.append(var5.getMessage());
            MPLog.d("MixpanelAPI.AL", var1.toString());
         } catch (IllegalAccessException var6) {
            StringBuilder var7 = new StringBuilder("Unable to detect inbound App Links: ");
            var7.append(var6.getMessage());
            MPLog.d("MixpanelAPI.AL", var7.toString());
         }
      } else {
         MPLog.d("MixpanelAPI.AL", "Context is not an instance of Activity. To detect inbound App Links, pass an instance of an Activity to getInstance.");
      }
   }

   public static MixpanelAPI getInstance(Context var0, String var1, String var2, boolean var3) {
      return getInstance(var0, var1, false, null, var2, var3);
   }

   public static MixpanelAPI getInstance(Context var0, String var1, JSONObject var2, String var3, boolean var4) {
      return getInstance(var0, var1, false, var2, var3, var4);
   }

   public static MixpanelAPI getInstance(Context var0, String var1, JSONObject var2, boolean var3) {
      return getInstance(var0, var1, false, var2, null, var3);
   }

   public static MixpanelAPI getInstance(Context var0, String var1, boolean var2) {
      return getInstance(var0, var1, false, null, null, var2);
   }

   public static MixpanelAPI getInstance(Context var0, String var1, boolean var2, String var3, boolean var4) {
      return getInstance(var0, var1, var2, null, var3, var4);
   }

   public static MixpanelAPI getInstance(Context param0, String param1, boolean param2, JSONObject param3, String param4, boolean param5) {
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
      // 00: aload 1
      // 01: ifnull c4
      // 04: aload 0
      // 05: ifnonnull 0b
      // 08: goto c4
      // 0b: getstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sInstanceMap Ljava/util/Map;
      // 0e: astore 9
      // 10: aload 9
      // 12: monitorenter
      // 13: aload 0
      // 14: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 17: astore 10
      // 19: getstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sReferrerPrefs Ljava/util/concurrent/Future;
      // 1c: ifnonnull 2d
      // 1f: getstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sPrefsLoader Lcom/mixpanel/android/mpmetrics/SharedPreferencesLoader;
      // 22: aload 0
      // 23: ldc_w "com.mixpanel.android.mpmetrics.ReferralInfo"
      // 26: aconst_null
      // 27: invokevirtual com/mixpanel/android/mpmetrics/SharedPreferencesLoader.loadPreferences (Landroid/content/Context;Ljava/lang/String;Lcom/mixpanel/android/mpmetrics/SharedPreferencesLoader$OnPrefsLoadedListener;)Ljava/util/concurrent/Future;
      // 2a: putstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sReferrerPrefs Ljava/util/concurrent/Future;
      // 2d: aload 4
      // 2f: ifnull 39
      // 32: aload 4
      // 34: astore 7
      // 36: goto 3c
      // 39: aload 1
      // 3a: astore 7
      // 3c: aload 9
      // 3e: aload 7
      // 40: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 45: checkcast java/util/Map
      // 48: astore 8
      // 4a: aload 8
      // 4c: astore 6
      // 4e: aload 8
      // 50: ifnonnull 69
      // 53: new java/util/HashMap
      // 56: astore 6
      // 58: aload 6
      // 5a: invokespecial java/util/HashMap.<init> ()V
      // 5d: aload 9
      // 5f: aload 7
      // 61: aload 6
      // 63: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 68: pop
      // 69: aload 6
      // 6b: aload 10
      // 6d: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 72: checkcast com/mixpanel/android/mpmetrics/MixpanelAPI
      // 75: astore 8
      // 77: aload 8
      // 79: astore 7
      // 7b: aload 8
      // 7d: ifnonnull b4
      // 80: aload 8
      // 82: astore 7
      // 84: aload 10
      // 86: invokestatic com/mixpanel/android/mpmetrics/ConfigurationChecker.checkBasicConfiguration (Landroid/content/Context;)Z
      // 89: ifeq b4
      // 8c: new com/mixpanel/android/mpmetrics/MixpanelAPI
      // 8f: astore 7
      // 91: aload 7
      // 93: aload 10
      // 95: getstatic com/mixpanel/android/mpmetrics/MixpanelAPI.sReferrerPrefs Ljava/util/concurrent/Future;
      // 98: aload 1
      // 99: iload 2
      // 9a: aload 3
      // 9b: aload 4
      // 9d: iload 5
      // 9f: invokespecial com/mixpanel/android/mpmetrics/MixpanelAPI.<init> (Landroid/content/Context;Ljava/util/concurrent/Future;Ljava/lang/String;ZLorg/json/JSONObject;Ljava/lang/String;Z)V
      // a2: aload 0
      // a3: aload 7
      // a5: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI.registerAppLinksListeners (Landroid/content/Context;Lcom/mixpanel/android/mpmetrics/MixpanelAPI;)V
      // a8: aload 6
      // aa: aload 10
      // ac: aload 7
      // ae: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // b3: pop
      // b4: aload 0
      // b5: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI.checkIntentForInboundAppLink (Landroid/content/Context;)V
      // b8: aload 9
      // ba: monitorexit
      // bb: aload 7
      // bd: areturn
      // be: astore 0
      // bf: aload 9
      // c1: monitorexit
      // c2: aload 0
      // c3: athrow
      // c4: aconst_null
      // c5: areturn
   }

   public static MixpanelAPI getInstance(Context var0, String var1, boolean var2, boolean var3) {
      return getInstance(var0, var1, var2, null, null, var3);
   }

   private String makeMapKey(String var1, Object var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      var3.append('_');
      var3.append(var2);
      return var3.toString();
   }

   private void pushWaitingPeopleRecord(String var1) {
      this.mMessages.pushAnonymousPeopleMessage(new AnalyticsMessages.PushAnonymousPeopleDescription(var1, this.mToken));
   }

   private void recordGroupMessage(JSONObject var1) {
      if (!this.hasOptedOutTracking()) {
         if (var1.has("$group_key") && var1.has("$group_id")) {
            this.mMessages.groupMessage(new AnalyticsMessages.GroupDescription(var1, this.mToken));
         } else {
            MPLog.e("MixpanelAPI.API", "Attempt to update group without key and value--this should not happen.");
         }
      }
   }

   private void recordPeopleMessage(JSONObject var1) {
      if (!this.hasOptedOutTracking()) {
         this.mMessages.peopleMessage(new AnalyticsMessages.PeopleDescription(var1, this.mToken));
      }
   }

   private static void registerAppLinksListeners(Context var0, MixpanelAPI var1) {
      try {
         Class var2 = Class.forName("androidx.localbroadcastmanager.content.LocalBroadcastManager");
         Method var3 = var2.getMethod("getInstance", Context.class);
         Method var13 = var2.getMethod("registerReceiver", BroadcastReceiver.class, IntentFilter.class);
         Object var14 = var3.invoke(null, var0);
         BroadcastReceiver var9 = new BroadcastReceiver(var1) {
            final MixpanelAPI val$mixpanel;

            {
               this.val$mixpanel = var1;
            }

            public void onReceive(Context var1, Intent var2x) {
               JSONObject var9x = new JSONObject();
               Bundle var4x = var2x.getBundleExtra("event_args");
               if (var4x != null) {
                  for (String var3x : var4x.keySet()) {
                     try {
                        var9x.put(var3x, var4x.get(var3x));
                     } catch (JSONException var8) {
                        StringBuilder var6x = new StringBuilder("failed to add key \"");
                        var6x.append(var3x);
                        var6x.append("\" to properties for tracking bolts event");
                        MPLog.e("MixpanelAPI.AL", var6x.toString(), var8);
                     }
                  }
               }

               MixpanelAPI var11 = this.val$mixpanel;
               StringBuilder var10 = new StringBuilder("$");
               var10.append(var2x.getStringExtra("event_name"));
               var11.track(var10.toString(), var9x);
            }
         };
         IntentFilter var12 = new IntentFilter("com.parse.bolts.measurement_event");
         var13.invoke(var14, var9, var12);
      } catch (InvocationTargetException var4) {
         MPLog.d(
            "MixpanelAPI.AL", "Failed to invoke LocalBroadcastManager.registerReceiver() -- App Links tracking will not be enabled due to this exception", var4
         );
      } catch (ClassNotFoundException var5) {
         StringBuilder var11 = new StringBuilder(
            "To enable App Links tracking, add implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0': "
         );
         var11.append(var5.getMessage());
         MPLog.d("MixpanelAPI.AL", var11.toString());
      } catch (NoSuchMethodException var6) {
         StringBuilder var8 = new StringBuilder(
            "To enable App Links tracking, add implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0': "
         );
         var8.append(var6.getMessage());
         MPLog.d("MixpanelAPI.AL", var8.toString());
      } catch (IllegalAccessException var7) {
         StringBuilder var10 = new StringBuilder("App Links tracking will not be enabled due to this exception: ");
         var10.append(var7.getMessage());
         MPLog.d("MixpanelAPI.AL", var10.toString());
      }
   }

   public void addGroup(String var1, Object var2) {
      if (!this.hasOptedOutTracking()) {
         this.updateSuperProperties(new SuperPropertyUpdate(this, var1, var2) {
            final MixpanelAPI this$0;
            final Object val$groupID;
            final String val$groupKey;

            {
               this.this$0 = var1;
               this.val$groupKey = var2x;
               this.val$groupID = var3;
            }

            @Override
            public JSONObject update(JSONObject var1) {
               try {
                  var1.accumulate(this.val$groupKey, this.val$groupID);
               } catch (JSONException var3) {
                  MPLog.e("MixpanelAPI.API", "Failed to add groups superProperty", var3);
               }

               return var1;
            }
         });
         this.mPeople.union(var1, new JSONArray().put(var2));
      }
   }

   public void alias(String var1, String var2) {
      if (!this.hasOptedOutTracking()) {
         String var3 = var2;
         if (var2 == null) {
            var3 = this.getDistinctId();
         }

         if (var1.equals(var3)) {
            StringBuilder var6 = new StringBuilder("Attempted to alias identical distinct_ids ");
            var6.append(var1);
            var6.append(". Alias message will not be sent.");
            MPLog.w("MixpanelAPI.API", var6.toString());
         } else {
            try {
               JSONObject var5 = new JSONObject();
               var5.put("alias", var1);
               var5.put("distinct_id", var3);
               this.track("$create_alias", var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Failed to alias", var4);
            }

            this.flush();
         }
      }
   }

   public void clearSuperProperties() {
      this.mPersistentIdentity.clearSuperProperties();
   }

   public void clearTimedEvent(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 11: pop
      // 12: aload 0
      // 13: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 16: aload 1
      // 17: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.removeTimedEvent (Ljava/lang/String;)V
      // 1a: aload 2
      // 1b: monitorexit
      // 1c: return
      // 1d: astore 1
      // 1e: aload 2
      // 1f: monitorexit
      // 20: aload 1
      // 21: athrow
   }

   public void clearTimedEvents() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 0b: invokeinterface java/util/Map.clear ()V 1
      // 10: aload 0
      // 11: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 14: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.clearTimedEvents ()V
      // 17: aload 2
      // 18: monitorexit
      // 19: return
      // 1a: astore 1
      // 1b: aload 2
      // 1c: monitorexit
      // 1d: aload 1
      // 1e: athrow
   }

   public double eventElapsedTime(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic java/lang/System.currentTimeMillis ()J
      // 03: lstore 4
      // 05: aload 0
      // 06: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 09: astore 6
      // 0b: aload 6
      // 0d: monitorenter
      // 0e: aload 0
      // 0f: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 12: aload 1
      // 13: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 18: checkcast java/lang/Long
      // 1b: astore 1
      // 1c: aload 6
      // 1e: monitorexit
      // 1f: aload 1
      // 20: ifnonnull 28
      // 23: dconst_0
      // 24: dstore 2
      // 25: goto 35
      // 28: lload 4
      // 2a: aload 1
      // 2b: invokevirtual java/lang/Long.longValue ()J
      // 2e: lsub
      // 2f: ldc2_w 1000
      // 32: ldiv
      // 33: l2d
      // 34: dstore 2
      // 35: dload 2
      // 36: dreturn
      // 37: astore 1
      // 38: aload 6
      // 3a: monitorexit
      // 3b: aload 1
      // 3c: athrow
   }

   public void flush() {
      if (!this.hasOptedOutTracking()) {
         this.mMessages.postToServer(new AnalyticsMessages.MixpanelDescription(this.mToken));
      }
   }

   AnalyticsMessages getAnalyticsMessages() {
      return AnalyticsMessages.getInstance(this.mContext, this.mConfig);
   }

   public String getAnonymousId() {
      return this.mPersistentIdentity.getAnonymousId();
   }

   Context getContext() {
      return this.mContext;
   }

   public Map<String, String> getDeviceInfo() {
      return this.mDeviceInfo;
   }

   public String getDistinctId() {
      return this.mPersistentIdentity.getEventsDistinctId();
   }

   public int getFlushBatchSize() {
      return this.mConfig.getFlushBatchSize();
   }

   public MixpanelAPI.Group getGroup(String var1, Object var2) {
      String var5 = this.makeMapKey(var1, var2);
      MixpanelAPI.GroupImpl var4 = this.mGroups.get(var5);
      MixpanelAPI.GroupImpl var3 = var4;
      if (var4 == null) {
         var3 = new MixpanelAPI.GroupImpl(this, var1, var2);
         this.mGroups.put(var5, var3);
      }

      if (var3.mGroupKey.equals(var1) && var3.mGroupID.equals(var2)) {
         return var3;
      } else {
         StringBuilder var6 = new StringBuilder("groups map key collision ");
         var6.append(var5);
         MPLog.i("MixpanelAPI.API", var6.toString());
         var4 = new MixpanelAPI.GroupImpl(this, var1, var2);
         this.mGroups.put(var5, var4);
         return var4;
      }
   }

   public int getMaximumDatabaseLimit() {
      return this.mConfig.getMaximumDatabaseLimit();
   }

   public MixpanelAPI.People getPeople() {
      return this.mPeople;
   }

   PersistentIdentity getPersistentIdentity(Context var1, Future<SharedPreferences> var2, String var3) {
      return this.getPersistentIdentity(var1, var2, var3, null);
   }

   PersistentIdentity getPersistentIdentity(Context var1, Future<SharedPreferences> var2, String var3, String var4) {
      SharedPreferencesLoader.OnPrefsLoadedListener var5 = new SharedPreferencesLoader.OnPrefsLoadedListener(this) {
         final MixpanelAPI this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onPrefsLoaded(SharedPreferences var1) {
            String var2x = PersistentIdentity.getPeopleDistinctId(var1);
            if (var2x != null) {
               this.this$0.pushWaitingPeopleRecord(var2x);
            }
         }
      };
      if (var4 != null) {
         var3 = var4;
      }

      StringBuilder var7 = new StringBuilder("com.mixpanel.android.mpmetrics.MixpanelAPI_");
      var7.append(var3);
      String var6 = var7.toString();
      SharedPreferencesLoader var8 = sPrefsLoader;
      Future var10 = var8.loadPreferences(var1, var6, var5);
      StringBuilder var9 = new StringBuilder("com.mixpanel.android.mpmetrics.MixpanelAPI.TimeEvents_");
      var9.append(var3);
      return new PersistentIdentity(
         var2, var10, var8.loadPreferences(var1, var9.toString(), null), var8.loadPreferences(var1, "com.mixpanel.android.mpmetrics.Mixpanel", null)
      );
   }

   public JSONObject getSuperProperties() {
      JSONObject var1 = new JSONObject();
      this.mPersistentIdentity.addSuperPropertiesToObject(var1);
      return var1;
   }

   public Boolean getTrackAutomaticEvents() {
      return this.mTrackAutomaticEvents;
   }

   protected String getUserId() {
      return this.mPersistentIdentity.getEventsUserId();
   }

   public boolean hasOptedOutTracking() {
      return this.mPersistentIdentity.getOptOutTracking(this.mToken);
   }

   public void identify(String var1) {
      this.identify(var1, true);
   }

   public void identify(String param1, boolean param2) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.hasOptedOutTracking ()Z
      // 04: ifeq 08
      // 07: return
      // 08: aload 1
      // 09: ifnonnull 15
      // 0c: ldc "MixpanelAPI.API"
      // 0e: ldc_w "Can't identify with null distinct_id."
      // 11: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 14: return
      // 15: aload 0
      // 16: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 19: astore 3
      // 1a: aload 3
      // 1b: monitorenter
      // 1c: aload 0
      // 1d: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 20: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.getEventsDistinctId ()Ljava/lang/String;
      // 23: astore 5
      // 25: aload 1
      // 26: aload 5
      // 28: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 2b: ifne 86
      // 2e: aload 1
      // 2f: ldc_w "$device:"
      // 32: invokevirtual java/lang/String.startsWith (Ljava/lang/String;)Z
      // 35: ifeq 43
      // 38: ldc "MixpanelAPI.API"
      // 3a: ldc_w "Can't identify with '$device:' distinct_id."
      // 3d: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 40: aload 3
      // 41: monitorexit
      // 42: return
      // 43: aload 0
      // 44: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 47: aload 1
      // 48: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.setEventsDistinctId (Ljava/lang/String;)V
      // 4b: aload 0
      // 4c: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 4f: aload 5
      // 51: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.setAnonymousIdIfAbsent (Ljava/lang/String;)V
      // 54: aload 0
      // 55: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 58: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.markEventsUserIdPresent ()V
      // 5b: new org/json/JSONObject
      // 5e: astore 4
      // 60: aload 4
      // 62: invokespecial org/json/JSONObject.<init> ()V
      // 65: aload 4
      // 67: ldc_w "$anon_distinct_id"
      // 6a: aload 5
      // 6c: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 6f: pop
      // 70: aload 0
      // 71: ldc_w "$identify"
      // 74: aload 4
      // 76: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.track (Ljava/lang/String;Lorg/json/JSONObject;)V
      // 79: goto 86
      // 7c: astore 4
      // 7e: ldc "MixpanelAPI.API"
      // 80: ldc_w "Could not track $identify event"
      // 83: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;)V
      // 86: iload 2
      // 87: ifeq 92
      // 8a: aload 0
      // 8b: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPeople Lcom/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl;
      // 8e: aload 1
      // 8f: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl.access$100 (Lcom/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl;Ljava/lang/String;)V
      // 92: aload 3
      // 93: monitorexit
      // 94: return
      // 95: astore 1
      // 96: aload 3
      // 97: monitorexit
      // 98: aload 1
      // 99: athrow
   }

   public boolean isAppInForeground() {
      MixpanelActivityLifecycleCallbacks var1 = this.mMixpanelActivityLifecycleCallbacks;
      return var1 != null ? var1.isInForeground() : false;
   }

   void onBackground() {
      if (this.mConfig.getFlushOnBackground()) {
         this.flush();
      }
   }

   void onForeground() {
      this.mSessionMetadata.initSession();
   }

   public void optInTracking() {
      this.optInTracking(null, null);
   }

   public void optInTracking(String var1) {
      this.optInTracking(var1, null);
   }

   public void optInTracking(String var1, JSONObject var2) {
      this.mPersistentIdentity.setOptOutTracking(false, this.mToken);
      if (var1 != null) {
         this.identify(var1);
      }

      this.track("$opt_in", var2);
   }

   public void optOutTracking() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getAnalyticsMessages ()Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
      // 04: new com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription
      // 07: dup
      // 08: aload 0
      // 09: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mToken Ljava/lang/String;
      // 0c: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription.<init> (Ljava/lang/String;)V
      // 0f: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages.emptyTrackingQueues (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$MixpanelDescription;)V
      // 12: aload 0
      // 13: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getPeople ()Lcom/mixpanel/android/mpmetrics/MixpanelAPI$People;
      // 16: invokeinterface com/mixpanel/android/mpmetrics/MixpanelAPI$People.isIdentified ()Z 1
      // 1b: ifeq 30
      // 1e: aload 0
      // 1f: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getPeople ()Lcom/mixpanel/android/mpmetrics/MixpanelAPI$People;
      // 22: invokeinterface com/mixpanel/android/mpmetrics/MixpanelAPI$People.deleteUser ()V 1
      // 27: aload 0
      // 28: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getPeople ()Lcom/mixpanel/android/mpmetrics/MixpanelAPI$People;
      // 2b: invokeinterface com/mixpanel/android/mpmetrics/MixpanelAPI$People.clearCharges ()V 1
      // 30: aload 0
      // 31: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 34: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.clearPreferences ()V
      // 37: aload 0
      // 38: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 3b: astore 1
      // 3c: aload 1
      // 3d: monitorenter
      // 3e: aload 0
      // 3f: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 42: invokeinterface java/util/Map.clear ()V 1
      // 47: aload 0
      // 48: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 4b: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.clearTimedEvents ()V
      // 4e: aload 1
      // 4f: monitorexit
      // 50: aload 0
      // 51: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 54: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.clearReferrerProperties ()V
      // 57: aload 0
      // 58: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 5b: bipush 1
      // 5c: aload 0
      // 5d: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mToken Ljava/lang/String;
      // 60: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.setOptOutTracking (ZLjava/lang/String;)V
      // 63: return
      // 64: astore 2
      // 65: aload 1
      // 66: monitorexit
      // 67: aload 2
      // 68: athrow
   }

   void registerMixpanelActivityLifecycleCallbacks() {
      if (this.mContext.getApplicationContext() instanceof Application) {
         Application var1 = (Application)this.mContext.getApplicationContext();
         MixpanelActivityLifecycleCallbacks var2 = new MixpanelActivityLifecycleCallbacks(this, this.mConfig);
         this.mMixpanelActivityLifecycleCallbacks = var2;
         var1.registerActivityLifecycleCallbacks(var2);
      } else {
         MPLog.i("MixpanelAPI.API", "Context is not an Application, Mixpanel won't be able to automatically flush on an app background.");
      }
   }

   public void registerSuperProperties(JSONObject var1) {
      if (!this.hasOptedOutTracking()) {
         this.mPersistentIdentity.registerSuperProperties(var1);
      }
   }

   public void registerSuperPropertiesMap(Map<String, Object> var1) {
      if (!this.hasOptedOutTracking()) {
         if (var1 == null) {
            MPLog.e("MixpanelAPI.API", "registerSuperPropertiesMap does not accept null properties");
         } else {
            try {
               JSONObject var2 = new JSONObject(var1);
               this.registerSuperProperties(var2);
            } catch (NullPointerException var3) {
               MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties of registerSuperPropertiesMap");
            }
         }
      }
   }

   public void registerSuperPropertiesOnce(JSONObject var1) {
      if (!this.hasOptedOutTracking()) {
         this.mPersistentIdentity.registerSuperPropertiesOnce(var1);
      }
   }

   public void registerSuperPropertiesOnceMap(Map<String, Object> var1) {
      if (!this.hasOptedOutTracking()) {
         if (var1 == null) {
            MPLog.e("MixpanelAPI.API", "registerSuperPropertiesOnceMap does not accept null properties");
         } else {
            try {
               JSONObject var2 = new JSONObject(var1);
               this.registerSuperPropertiesOnce(var2);
            } catch (NullPointerException var3) {
               MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties of registerSuperPropertiesOnce!");
            }
         }
      }
   }

   public void removeGroup(String var1, Object var2) {
      if (!this.hasOptedOutTracking()) {
         this.updateSuperProperties(new SuperPropertyUpdate(this, var1, var2) {
            final MixpanelAPI this$0;
            final Object val$groupID;
            final String val$groupKey;

            {
               this.this$0 = var1;
               this.val$groupKey = var2x;
               this.val$groupID = var3;
            }

            // $VF: Duplicated exception handlers to handle obfuscated exceptions
            @Override
            public JSONObject update(JSONObject var1) {
               JSONArray var3;
               JSONArray var4;
               try {
                  var4 = var1.getJSONArray(this.val$groupKey);
                  var3 = new JSONArray();
                  if (var4.length() <= 1) {
                     var1.remove(this.val$groupKey);
                     this.this$0.mPeople.unset(this.val$groupKey);
                     return var1;
                  }
               } catch (JSONException var7) {
                  var1.remove(this.val$groupKey);
                  this.this$0.mPeople.unset(this.val$groupKey);
                  return var1;
               }

               int var2x = 0;

               while (true) {
                  try {
                     if (var2x >= var4.length()) {
                        break;
                     }

                     if (!var4.get(var2x).equals(this.val$groupID)) {
                        var3.put(var4.get(var2x));
                     }
                  } catch (JSONException var6) {
                     var1.remove(this.val$groupKey);
                     this.this$0.mPeople.unset(this.val$groupKey);
                     return var1;
                  }

                  var2x++;
               }

               try {
                  var1.put(this.val$groupKey, var3);
                  this.this$0.mPeople.remove(this.val$groupKey, this.val$groupID);
               } catch (JSONException var5) {
                  var1.remove(this.val$groupKey);
                  this.this$0.mPeople.unset(this.val$groupKey);
               }

               return var1;
            }
         });
      }
   }

   public void reset() {
      this.mPersistentIdentity.clearPreferences();
      this.getAnalyticsMessages().clearAnonymousUpdatesMessage(new AnalyticsMessages.MixpanelDescription(this.mToken));
      this.identify(this.getDistinctId(), false);
      this.flush();
   }

   boolean sendAppOpen() {
      return this.mConfig.getDisableAppOpenEvent() ^ true;
   }

   public void setEnableLogging(boolean var1) {
      this.mConfig.setEnableLogging(var1);
   }

   public void setFlushBatchSize(int var1) {
      this.mConfig.setFlushBatchSize(var1);
   }

   public void setGroup(String var1, Object var2) {
      if (!this.hasOptedOutTracking()) {
         ArrayList var3 = new ArrayList(1);
         var3.add(var2);
         this.setGroup(var1, (List<Object>)var3);
      }
   }

   public void setGroup(String var1, List<Object> var2) {
      if (!this.hasOptedOutTracking()) {
         JSONArray var3 = new JSONArray();

         for (Object var4 : var2) {
            if (var4 == null) {
               MPLog.w("MixpanelAPI.API", "groupID must be non-null");
            } else {
               var3.put(var4);
            }
         }

         try {
            JSONObject var7 = new JSONObject();
            this.registerSuperProperties(var7.put(var1, var3));
            this.mPeople.set(var1, var3);
         } catch (JSONException var5) {
            MPLog.w("MixpanelAPI.API", "groupKey must be non-null");
         }
      }
   }

   public void setMaximumDatabaseLimit(int var1) {
      this.mConfig.setMaximumDatabaseLimit(var1);
   }

   public void setServerURL(String var1) {
      this.mConfig.setServerURL(var1);
   }

   public void setServerURL(String var1, ProxyServerInteractor var2) {
      this.mConfig.setServerURL(var1, var2);
   }

   public void setUseIpAddressForGeolocation(boolean var1) {
      this.mConfig.setUseIpAddressForGeolocation(var1);
   }

   public void timeEvent(String param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.hasOptedOutTracking ()Z
      // 04: ifeq 08
      // 07: return
      // 08: invokestatic java/lang/System.currentTimeMillis ()J
      // 0b: lstore 2
      // 0c: aload 0
      // 0d: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 10: astore 4
      // 12: aload 4
      // 14: monitorenter
      // 15: aload 0
      // 16: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 19: aload 1
      // 1a: lload 2
      // 1b: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 1e: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 23: pop
      // 24: aload 0
      // 25: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 28: aload 1
      // 29: lload 2
      // 2a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 2d: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.addTimeEvent (Ljava/lang/String;Ljava/lang/Long;)V
      // 30: aload 4
      // 32: monitorexit
      // 33: return
      // 34: astore 1
      // 35: aload 4
      // 37: monitorexit
      // 38: aload 1
      // 39: athrow
   }

   public void track(String var1) {
      if (!this.hasOptedOutTracking()) {
         this.track(var1, null);
      }
   }

   public void track(String var1, JSONObject var2) {
      if (!this.hasOptedOutTracking()) {
         this.track(var1, var2, false);
      }
   }

   protected void track(String param1, JSONObject param2, boolean param3) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.hasOptedOutTracking ()Z
      // 004: ifne 195
      // 007: iload 3
      // 008: ifeq 018
      // 00b: aload 0
      // 00c: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mTrackAutomaticEvents Ljava/lang/Boolean;
      // 00f: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 012: ifne 018
      // 015: goto 195
      // 018: aload 0
      // 019: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 01c: astore 6
      // 01e: aload 6
      // 020: monitorenter
      // 021: aload 0
      // 022: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 025: aload 1
      // 026: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 02b: checkcast java/lang/Long
      // 02e: astore 7
      // 030: aload 0
      // 031: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mEventTimings Ljava/util/Map;
      // 034: aload 1
      // 035: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 03a: pop
      // 03b: aload 0
      // 03c: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 03f: aload 1
      // 040: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.removeTimedEvent (Ljava/lang/String;)V
      // 043: aload 6
      // 045: monitorexit
      // 046: new org/json/JSONObject
      // 049: astore 6
      // 04b: aload 6
      // 04d: invokespecial org/json/JSONObject.<init> ()V
      // 050: aload 0
      // 051: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 054: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.getReferrerProperties ()Ljava/util/Map;
      // 057: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 05c: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 061: astore 8
      // 063: aload 8
      // 065: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 06a: ifeq 096
      // 06d: aload 8
      // 06f: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 074: checkcast java/util/Map$Entry
      // 077: astore 9
      // 079: aload 6
      // 07b: aload 9
      // 07d: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 082: checkcast java/lang/String
      // 085: aload 9
      // 087: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 08c: checkcast java/lang/String
      // 08f: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 092: pop
      // 093: goto 063
      // 096: aload 0
      // 097: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 09a: aload 6
      // 09c: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.addSuperPropertiesToObject (Lorg/json/JSONObject;)V
      // 09f: invokestatic java/lang/System.currentTimeMillis ()J
      // 0a2: l2d
      // 0a3: ldc2_w 1000.0
      // 0a6: ddiv
      // 0a7: dstore 4
      // 0a9: aload 0
      // 0aa: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getDistinctId ()Ljava/lang/String;
      // 0ad: astore 8
      // 0af: aload 0
      // 0b0: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getAnonymousId ()Ljava/lang/String;
      // 0b3: astore 10
      // 0b5: aload 0
      // 0b6: invokevirtual com/mixpanel/android/mpmetrics/MixpanelAPI.getUserId ()Ljava/lang/String;
      // 0b9: astore 9
      // 0bb: aload 6
      // 0bd: ldc_w "time"
      // 0c0: invokestatic java/lang/System.currentTimeMillis ()J
      // 0c3: invokevirtual org/json/JSONObject.put (Ljava/lang/String;J)Lorg/json/JSONObject;
      // 0c6: pop
      // 0c7: aload 6
      // 0c9: ldc_w "distinct_id"
      // 0cc: aload 8
      // 0ce: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 0d1: pop
      // 0d2: aload 6
      // 0d4: ldc_w "$had_persisted_distinct_id"
      // 0d7: aload 0
      // 0d8: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mPersistentIdentity Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
      // 0db: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.getHadPersistedDistinctId ()Z
      // 0de: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Z)Lorg/json/JSONObject;
      // 0e1: pop
      // 0e2: aload 10
      // 0e4: ifnull 0f2
      // 0e7: aload 6
      // 0e9: ldc_w "$device_id"
      // 0ec: aload 10
      // 0ee: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 0f1: pop
      // 0f2: aload 9
      // 0f4: ifnull 102
      // 0f7: aload 6
      // 0f9: ldc_w "$user_id"
      // 0fc: aload 9
      // 0fe: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 101: pop
      // 102: aload 7
      // 104: ifnull 11d
      // 107: aload 6
      // 109: ldc_w "$duration"
      // 10c: dload 4
      // 10e: aload 7
      // 110: invokevirtual java/lang/Long.longValue ()J
      // 113: l2d
      // 114: ldc2_w 1000.0
      // 117: ddiv
      // 118: dsub
      // 119: invokevirtual org/json/JSONObject.put (Ljava/lang/String;D)Lorg/json/JSONObject;
      // 11c: pop
      // 11d: aload 2
      // 11e: ifnull 14e
      // 121: aload 2
      // 122: invokevirtual org/json/JSONObject.keys ()Ljava/util/Iterator;
      // 125: astore 8
      // 127: aload 8
      // 129: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 12e: ifeq 14e
      // 131: aload 8
      // 133: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 138: checkcast java/lang/String
      // 13b: astore 7
      // 13d: aload 6
      // 13f: aload 7
      // 141: aload 2
      // 142: aload 7
      // 144: invokevirtual org/json/JSONObject.opt (Ljava/lang/String;)Ljava/lang/Object;
      // 147: invokevirtual org/json/JSONObject.put (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      // 14a: pop
      // 14b: goto 127
      // 14e: new com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription
      // 151: astore 2
      // 152: aload 2
      // 153: aload 1
      // 154: aload 6
      // 156: aload 0
      // 157: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mToken Ljava/lang/String;
      // 15a: iload 3
      // 15b: aload 0
      // 15c: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mSessionMetadata Lcom/mixpanel/android/mpmetrics/SessionMetadata;
      // 15f: invokevirtual com/mixpanel/android/mpmetrics/SessionMetadata.getMetadataForEvent ()Lorg/json/JSONObject;
      // 162: invokespecial com/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription.<init> (Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;ZLorg/json/JSONObject;)V
      // 165: aload 0
      // 166: getfield com/mixpanel/android/mpmetrics/MixpanelAPI.mMessages Lcom/mixpanel/android/mpmetrics/AnalyticsMessages;
      // 169: aload 2
      // 16a: invokevirtual com/mixpanel/android/mpmetrics/AnalyticsMessages.eventsMessage (Lcom/mixpanel/android/mpmetrics/AnalyticsMessages$EventDescription;)V
      // 16d: goto 18e
      // 170: astore 6
      // 172: new java/lang/StringBuilder
      // 175: dup
      // 176: ldc_w "Exception tracking event "
      // 179: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 17c: astore 2
      // 17d: aload 2
      // 17e: aload 1
      // 17f: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 182: pop
      // 183: ldc "MixpanelAPI.API"
      // 185: aload 2
      // 186: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 189: aload 6
      // 18b: invokestatic com/mixpanel/android/util/MPLog.e (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 18e: return
      // 18f: astore 1
      // 190: aload 6
      // 192: monitorexit
      // 193: aload 1
      // 194: athrow
      // 195: return
   }

   public void trackMap(String var1, Map<String, Object> var2) {
      if (!this.hasOptedOutTracking()) {
         if (var2 == null) {
            this.track(var1, null);
         } else {
            try {
               JSONObject var3 = new JSONObject(var2);
               this.track(var1, var3);
            } catch (NullPointerException var4) {
               MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties of trackMap!");
            }
         }
      }
   }

   public void trackWithGroups(String var1, Map<String, Object> var2, Map<String, Object> var3) {
      if (!this.hasOptedOutTracking()) {
         if (var3 == null) {
            this.trackMap(var1, var2);
         } else if (var2 == null) {
            this.trackMap(var1, var3);
         } else {
            for (Entry var5 : var3.entrySet()) {
               if (var5.getValue() != null) {
                  var2.put(var5.getKey(), var5.getValue());
               }
            }

            this.trackMap(var1, var2);
         }
      }
   }

   public void unregisterSuperProperty(String var1) {
      if (!this.hasOptedOutTracking()) {
         this.mPersistentIdentity.unregisterSuperProperty(var1);
      }
   }

   public void updateSuperProperties(SuperPropertyUpdate var1) {
      if (!this.hasOptedOutTracking()) {
         this.mPersistentIdentity.updateSuperProperties(var1);
      }
   }

   public interface Group {
      void deleteGroup();

      void remove(String var1, Object var2);

      void set(String var1, Object var2);

      void set(JSONObject var1);

      void setMap(Map<String, Object> var1);

      void setOnce(String var1, Object var2);

      void setOnce(JSONObject var1);

      void setOnceMap(Map<String, Object> var1);

      void union(String var1, JSONArray var2);

      void unset(String var1);
   }

   private class GroupImpl implements MixpanelAPI.Group {
      private final Object mGroupID;
      private final String mGroupKey;
      final MixpanelAPI this$0;

      public GroupImpl(MixpanelAPI var1, String var2, Object var3) {
         this.this$0 = var1;
         this.mGroupKey = var2;
         this.mGroupID = var3;
      }

      private JSONObject stdGroupMessage(String var1, Object var2) throws JSONException {
         JSONObject var3 = new JSONObject();
         var3.put(var1, var2);
         var3.put("$token", this.this$0.mToken);
         var3.put("$time", System.currentTimeMillis());
         var3.put("$group_key", this.mGroupKey);
         var3.put("$group_id", this.mGroupID);
         var3.put("$mp_metadata", this.this$0.mSessionMetadata.getMetadataForPeople());
         return var3;
      }

      @Override
      public void deleteGroup() {
         try {
            JSONObject var1 = this.stdGroupMessage("$delete", JSONObject.NULL);
            this.this$0.recordGroupMessage(var1);
            this.this$0.mGroups.remove(this.this$0.makeMapKey(this.mGroupKey, this.mGroupID));
         } catch (JSONException var2) {
            MPLog.e("MixpanelAPI.API", "Exception deleting a group", var2);
         }
      }

      @Override
      public void remove(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               var3.put(var1, var2);
               JSONObject var5 = this.stdGroupMessage("$remove", var3);
               this.this$0.recordGroupMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception removing a property", var4);
            }
         }
      }

      @Override
      public void set(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               this.set(var3.put(var1, var2));
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "set", var4);
            }
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      @Override
      public void set(JSONObject var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            JSONObject var3;
            Iterator var4;
            try {
               var3 = new JSONObject();
               var4 = var1.keys();
            } catch (JSONException var6) {
               MPLog.e("MixpanelAPI.API", "Exception setting group properties", var6);
               return;
            }

            while (true) {
               try {
                  if (!var4.hasNext()) {
                     break;
                  }

                  String var2 = (String)var4.next();
                  var3.put(var2, var1.get(var2));
               } catch (JSONException var7) {
                  MPLog.e("MixpanelAPI.API", "Exception setting group properties", var7);
                  return;
               }
            }

            try {
               var1 = this.stdGroupMessage("$set", var3);
               this.this$0.recordGroupMessage(var1);
            } catch (JSONException var5) {
               MPLog.e("MixpanelAPI.API", "Exception setting group properties", var5);
            }
         }
      }

      @Override
      public void setMap(Map<String, Object> var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            if (var1 == null) {
               MPLog.e("MixpanelAPI.API", "setMap does not accept null properties");
            } else {
               this.set(new JSONObject(var1));
            }
         }
      }

      @Override
      public void setOnce(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               this.setOnce(var3.put(var1, var2));
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Property name cannot be null", var4);
            }
         }
      }

      @Override
      public void setOnce(JSONObject var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               var1 = this.stdGroupMessage("$set_once", var1);
               this.this$0.recordGroupMessage(var1);
            } catch (JSONException var2) {
               MPLog.e("MixpanelAPI.API", "Exception setting group properties");
            }
         }
      }

      @Override
      public void setOnceMap(Map<String, Object> var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            if (var1 == null) {
               MPLog.e("MixpanelAPI.API", "setOnceMap does not accept null properties");
            } else {
               try {
                  JSONObject var2 = new JSONObject(var1);
                  this.setOnce(var2);
               } catch (NullPointerException var3) {
                  MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties for setOnceMap!");
               }
            }
         }
      }

      @Override
      public void union(String var1, JSONArray var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               var3.put(var1, var2);
               JSONObject var5 = this.stdGroupMessage("$union", var3);
               this.this$0.recordGroupMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception unioning a property", var4);
            }
         }
      }

      @Override
      public void unset(String var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONArray var2 = new JSONArray();
               var2.put(var1);
               JSONObject var4 = this.stdGroupMessage("$unset", var2);
               this.this$0.recordGroupMessage(var4);
            } catch (JSONException var3) {
               MPLog.e("MixpanelAPI.API", "Exception unsetting a property", var3);
            }
         }
      }
   }

   interface InstanceProcessor {
      void process(MixpanelAPI var1);
   }

   public interface People {
      void append(String var1, Object var2);

      void clearCharges();

      void deleteUser();

      @Deprecated
      String getDistinctId();

      @Deprecated
      void identify(String var1);

      void increment(String var1, double var2);

      void increment(Map<String, ? extends Number> var1);

      boolean isIdentified();

      void merge(String var1, JSONObject var2);

      void remove(String var1, Object var2);

      void set(String var1, Object var2);

      void set(JSONObject var1);

      void setMap(Map<String, Object> var1);

      void setOnce(String var1, Object var2);

      void setOnce(JSONObject var1);

      void setOnceMap(Map<String, Object> var1);

      void trackCharge(double var1, JSONObject var3);

      void union(String var1, JSONArray var2);

      void unset(String var1);

      MixpanelAPI.People withIdentity(String var1);
   }

   private class PeopleImpl implements MixpanelAPI.People {
      final MixpanelAPI this$0;

      private PeopleImpl(MixpanelAPI var1) {
         this.this$0 = var1;
      }

      private void identify_people(String param1) {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.NullPointerException: Cannot read field "id" because the return value of "org.jetbrains.java.decompiler.modules.decompiler.flow.FlattenStatementsHelper.getDirectNode(org.jetbrains.java.decompiler.modules.decompiler.stats.Statement)" is null
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:179)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.collectCatchVars(ExprProcessor.java:184)
         //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:112)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.getFinallyInformation(FinallyProcessor.java:135)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:84)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: getfield com/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl.this$0 Lcom/mixpanel/android/mpmetrics/MixpanelAPI;
         // 04: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI.access$600 (Lcom/mixpanel/android/mpmetrics/MixpanelAPI;)Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
         // 07: astore 2
         // 08: aload 2
         // 09: monitorenter
         // 0a: aload 0
         // 0b: getfield com/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl.this$0 Lcom/mixpanel/android/mpmetrics/MixpanelAPI;
         // 0e: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI.access$600 (Lcom/mixpanel/android/mpmetrics/MixpanelAPI;)Lcom/mixpanel/android/mpmetrics/PersistentIdentity;
         // 11: aload 1
         // 12: invokevirtual com/mixpanel/android/mpmetrics/PersistentIdentity.setPeopleDistinctId (Ljava/lang/String;)V
         // 15: aload 2
         // 16: monitorexit
         // 17: aload 0
         // 18: getfield com/mixpanel/android/mpmetrics/MixpanelAPI$PeopleImpl.this$0 Lcom/mixpanel/android/mpmetrics/MixpanelAPI;
         // 1b: aload 1
         // 1c: invokestatic com/mixpanel/android/mpmetrics/MixpanelAPI.access$500 (Lcom/mixpanel/android/mpmetrics/MixpanelAPI;Ljava/lang/String;)V
         // 1f: return
         // 20: astore 1
         // 21: aload 2
         // 22: monitorexit
         // 23: aload 1
         // 24: athrow
      }

      private JSONObject stdPeopleMessage(String var1, Object var2) throws JSONException {
         JSONObject var5 = new JSONObject();
         String var4 = this.getDistinctId();
         String var3 = this.this$0.getAnonymousId();
         var5.put(var1, var2);
         var5.put("$token", this.this$0.mToken);
         var5.put("$time", System.currentTimeMillis());
         var5.put("$had_persisted_distinct_id", this.this$0.mPersistentIdentity.getHadPersistedDistinctId());
         if (var3 != null) {
            var5.put("$device_id", var3);
         }

         if (var4 != null) {
            var5.put("$distinct_id", var4);
            var5.put("$user_id", var4);
         }

         var5.put("$mp_metadata", this.this$0.mSessionMetadata.getMetadataForPeople());
         return var5;
      }

      @Override
      public void append(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               var3.put(var1, var2);
               JSONObject var5 = this.stdPeopleMessage("$append", var3);
               this.this$0.recordPeopleMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception appending a property", var4);
            }
         }
      }

      @Override
      public void clearCharges() {
         this.unset("$transactions");
      }

      @Override
      public void deleteUser() {
         try {
            JSONObject var1 = this.stdPeopleMessage("$delete", JSONObject.NULL);
            this.this$0.recordPeopleMessage(var1);
         } catch (JSONException var2) {
            MPLog.e("MixpanelAPI.API", "Exception deleting a user");
         }
      }

      @Override
      public String getDistinctId() {
         return this.this$0.mPersistentIdentity.getPeopleDistinctId();
      }

      @Override
      public void identify(String var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            MPLog.w(
               "MixpanelAPI.API",
               "People.identify() is deprecated and calling it is no longer necessary, please use MixpanelAPI.identify() and set 'usePeople' to true instead"
            );
            if (var1 == null) {
               MPLog.e("MixpanelAPI.API", "Can't identify with null distinct_id.");
            } else if (!var1.equals(this.this$0.mPersistentIdentity.getEventsDistinctId())) {
               MPLog.w("MixpanelAPI.API", "Identifying with a distinct_id different from the one being set by MixpanelAPI.identify() is not supported.");
            } else {
               this.identify_people(var1);
            }
         }
      }

      @Override
      public void increment(String var1, double var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            HashMap var4 = new HashMap();
            var4.put(var1, var2);
            this.increment(var4);
         }
      }

      @Override
      public void increment(Map<String, ? extends Number> var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            JSONObject var3 = new JSONObject(var1);

            try {
               JSONObject var4 = this.stdPeopleMessage("$add", var3);
               this.this$0.recordPeopleMessage(var4);
            } catch (JSONException var2) {
               MPLog.e("MixpanelAPI.API", "Exception incrementing properties", var2);
            }
         }
      }

      @Override
      public boolean isIdentified() {
         boolean var1;
         if (this.getDistinctId() != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void merge(String var1, JSONObject var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            JSONObject var3 = new JSONObject();

            try {
               var3.put(var1, var2);
               JSONObject var5 = this.stdPeopleMessage("$merge", var3);
               this.this$0.recordPeopleMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception merging a property", var4);
            }
         }
      }

      @Override
      public void remove(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               var3.put(var1, var2);
               JSONObject var5 = this.stdPeopleMessage("$remove", var3);
               this.this$0.recordPeopleMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception appending a property", var4);
            }
         }
      }

      @Override
      public void set(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               this.set(var3.put(var1, var2));
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "set", var4);
            }
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      @Override
      public void set(JSONObject var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            JSONObject var3;
            Iterator var4;
            try {
               var3 = new JSONObject(this.this$0.mDeviceInfo);
               var4 = var1.keys();
            } catch (JSONException var6) {
               MPLog.e("MixpanelAPI.API", "Exception setting people properties", var6);
               return;
            }

            while (true) {
               try {
                  if (!var4.hasNext()) {
                     break;
                  }

                  String var2 = (String)var4.next();
                  var3.put(var2, var1.get(var2));
               } catch (JSONException var7) {
                  MPLog.e("MixpanelAPI.API", "Exception setting people properties", var7);
                  return;
               }
            }

            try {
               var1 = this.stdPeopleMessage("$set", var3);
               this.this$0.recordPeopleMessage(var1);
            } catch (JSONException var5) {
               MPLog.e("MixpanelAPI.API", "Exception setting people properties", var5);
            }
         }
      }

      @Override
      public void setMap(Map<String, Object> var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            if (var1 == null) {
               MPLog.e("MixpanelAPI.API", "setMap does not accept null properties");
            } else {
               try {
                  JSONObject var2 = new JSONObject(var1);
                  this.set(var2);
               } catch (NullPointerException var3) {
                  MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties of setMap!");
               }
            }
         }
      }

      @Override
      public void setOnce(String var1, Object var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               this.setOnce(var3.put(var1, var2));
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "set", var4);
            }
         }
      }

      @Override
      public void setOnce(JSONObject var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               var1 = this.stdPeopleMessage("$set_once", var1);
               this.this$0.recordPeopleMessage(var1);
            } catch (JSONException var2) {
               MPLog.e("MixpanelAPI.API", "Exception setting people properties");
            }
         }
      }

      @Override
      public void setOnceMap(Map<String, Object> var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            if (var1 == null) {
               MPLog.e("MixpanelAPI.API", "setOnceMap does not accept null properties");
            } else {
               try {
                  JSONObject var2 = new JSONObject(var1);
                  this.setOnce(var2);
               } catch (NullPointerException var3) {
                  MPLog.w("MixpanelAPI.API", "Can't have null keys in the properties setOnceMap!");
               }
            }
         }
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      @Override
      public void trackCharge(double var1, JSONObject var3) {
         if (!this.this$0.hasOptedOutTracking()) {
            Date var6 = new Date();
            SimpleDateFormat var5 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            var5.setTimeZone(DesugarTimeZone.getTimeZone("UTC"));

            JSONObject var4;
            try {
               var4 = new JSONObject();
               var4.put("$amount", var1);
               var4.put("$time", var5.format(var6));
            } catch (JSONException var10) {
               MPLog.e("MixpanelAPI.API", "Exception creating new charge", var10);
               return;
            }

            if (var3 != null) {
               try {
                  var12 = var3.keys();
               } catch (JSONException var8) {
                  MPLog.e("MixpanelAPI.API", "Exception creating new charge", var8);
                  return;
               }

               while (true) {
                  try {
                     if (!var12.hasNext()) {
                        break;
                     }

                     String var11 = (String)var12.next();
                     var4.put(var11, var3.get(var11));
                  } catch (JSONException var9) {
                     MPLog.e("MixpanelAPI.API", "Exception creating new charge", var9);
                     return;
                  }
               }
            }

            try {
               this.append("$transactions", var4);
            } catch (JSONException var7) {
               MPLog.e("MixpanelAPI.API", "Exception creating new charge", var7);
            }
         }
      }

      @Override
      public void union(String var1, JSONArray var2) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONObject var3 = new JSONObject();
               var3.put(var1, var2);
               JSONObject var5 = this.stdPeopleMessage("$union", var3);
               this.this$0.recordPeopleMessage(var5);
            } catch (JSONException var4) {
               MPLog.e("MixpanelAPI.API", "Exception unioning a property");
            }
         }
      }

      @Override
      public void unset(String var1) {
         if (!this.this$0.hasOptedOutTracking()) {
            try {
               JSONArray var2 = new JSONArray();
               var2.put(var1);
               JSONObject var4 = this.stdPeopleMessage("$unset", var2);
               this.this$0.recordPeopleMessage(var4);
            } catch (JSONException var3) {
               MPLog.e("MixpanelAPI.API", "Exception unsetting a property", var3);
            }
         }
      }

      @Override
      public MixpanelAPI.People withIdentity(String var1) {
         return var1 == null ? null : new MixpanelAPI.PeopleImpl(this, var1) {
            final MixpanelAPI.PeopleImpl this$1;
            final String val$distinctId;

            {
               this.this$1 = var1;
               this.val$distinctId = var2;
            }

            @Override
            public String getDistinctId() {
               return this.val$distinctId;
            }

            @Override
            public void identify(String var1) {
               throw new RuntimeException("This MixpanelPeople object has a fixed, constant distinctId");
            }
         };
      }
   }
}
