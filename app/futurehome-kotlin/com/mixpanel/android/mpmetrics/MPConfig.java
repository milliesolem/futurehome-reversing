package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.OfflineMode;
import com.mixpanel.android.util.ProxyServerInteractor;
import java.security.GeneralSecurityException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class MPConfig {
   public static boolean DEBUG;
   private static final String LOGTAG = "MixpanelAPI.Conf";
   static final String REFERRER_PREFS_NAME = "com.mixpanel.android.mpmetrics.ReferralInfo";
   public static final String VERSION = "7.5.2";
   private final int mBulkUploadLimit;
   private final long mDataExpiration;
   private final boolean mDisableAppOpenEvent;
   private final boolean mDisableExceptionHandler;
   private String mEventsEndpoint;
   private int mFlushBatchSize;
   private final int mFlushInterval;
   private final boolean mFlushOnBackground;
   private String mGroupsEndpoint;
   private String mInstanceName;
   private int mMaximumDatabaseLimit;
   private final int mMinSessionDuration;
   private final int mMinimumDatabaseLimit;
   private OfflineMode mOfflineMode;
   private String mPeopleEndpoint;
   private final boolean mRemoveLegacyResidualFiles;
   private final String mResourcePackageName;
   private SSLSocketFactory mSSLSocketFactory;
   private final int mSessionTimeoutDuration;
   private boolean mTrackAutomaticEvents = true;
   private boolean mUseIpAddressForGeolocation;
   private ProxyServerInteractor serverCallbacks;

   MPConfig(Bundle var1, Context var2, String var3) {
      SSLSocketFactory var11 = null;
      this.serverCallbacks = null;

      label73: {
         SSLSocketFactory var17;
         try {
            SSLContext var7 = SSLContext.getInstance("TLS");
            var7.init(null, null, null);
            var17 = var7.getSocketFactory();
         } catch (GeneralSecurityException var9) {
            MPLog.i("MixpanelAPI.Conf", "System has no SSL support. Built-in events editor will not be available", var9);
            break label73;
         }

         var11 = var17;
      }

      this.mSSLSocketFactory = var11;
      this.mInstanceName = var3;
      boolean var4 = var1.getBoolean("com.mixpanel.android.MPConfig.EnableDebugLogging", false);
      DEBUG = var4;
      if (var4) {
         MPLog.setLevel(2);
      }

      if (var1.containsKey("com.mixpanel.android.MPConfig.DebugFlushInterval")) {
         MPLog.w(
            "MixpanelAPI.Conf",
            "We do not support com.mixpanel.android.MPConfig.DebugFlushInterval anymore. There will only be one flush interval. Please, update your AndroidManifest.xml."
         );
      }

      long var5;
      label66: {
         this.mBulkUploadLimit = var1.getInt("com.mixpanel.android.MPConfig.BulkUploadLimit", 40);
         this.mFlushInterval = var1.getInt("com.mixpanel.android.MPConfig.FlushInterval", 60000);
         this.mFlushBatchSize = var1.getInt("com.mixpanel.android.MPConfig.FlushBatchSize", 50);
         this.mFlushOnBackground = var1.getBoolean("com.mixpanel.android.MPConfig.FlushOnBackground", true);
         this.mMinimumDatabaseLimit = var1.getInt("com.mixpanel.android.MPConfig.MinimumDatabaseLimit", 20971520);
         this.mMaximumDatabaseLimit = var1.getInt("com.mixpanel.android.MPConfig.MaximumDatabaseLimit", Integer.MAX_VALUE);
         this.mResourcePackageName = var1.getString("com.mixpanel.android.MPConfig.ResourcePackageName");
         this.mDisableAppOpenEvent = var1.getBoolean("com.mixpanel.android.MPConfig.DisableAppOpenEvent", true);
         this.mDisableExceptionHandler = var1.getBoolean("com.mixpanel.android.MPConfig.DisableExceptionHandler", false);
         this.mMinSessionDuration = var1.getInt("com.mixpanel.android.MPConfig.MinimumSessionDuration", 10000);
         this.mSessionTimeoutDuration = var1.getInt("com.mixpanel.android.MPConfig.SessionTimeoutDuration", Integer.MAX_VALUE);
         this.mUseIpAddressForGeolocation = var1.getBoolean("com.mixpanel.android.MPConfig.UseIpAddressForGeolocation", true);
         this.mRemoveLegacyResidualFiles = var1.getBoolean("com.mixpanel.android.MPConfig.RemoveLegacyResidualFiles", false);
         Object var18 = var1.get("com.mixpanel.android.MPConfig.DataExpiration");
         if (var18 != null) {
            try {
               if (var18 instanceof Integer) {
                  var5 = ((Integer)var18).intValue();
               } else {
                  if (!(var18 instanceof Float)) {
                     StringBuilder var15 = new StringBuilder();
                     var15.append(var18.toString());
                     var15.append(" is not a number.");
                     NumberFormatException var14 = new NumberFormatException(var15.toString());
                     throw var14;
                  }

                  var5 = (long)((Float)var18).floatValue();
               }
               break label66;
            } catch (Exception var8) {
               MPLog.e("MixpanelAPI.Conf", "Error parsing com.mixpanel.android.MPConfig.DataExpiration meta-data value", var8);
            }
         }

         var5 = 432000000L;
      }

      this.mDataExpiration = var5;
      var4 = var1.containsKey("com.mixpanel.android.MPConfig.UseIpAddressForGeolocation");
      String var12 = var1.getString("com.mixpanel.android.MPConfig.EventsEndpoint");
      if (var12 != null) {
         if (var4) {
            var12 = this.getEndPointWithIpTrackingParam(var12, this.getUseIpAddressForGeolocation());
         }

         this.setEventsEndpoint(var12);
      } else {
         this.setEventsEndpointWithBaseURL("https://api.mixpanel.com");
      }

      String var13 = var1.getString("com.mixpanel.android.MPConfig.PeopleEndpoint");
      if (var13 != null) {
         if (var4) {
            var13 = this.getEndPointWithIpTrackingParam(var13, this.getUseIpAddressForGeolocation());
         }

         this.setPeopleEndpoint(var13);
      } else {
         this.setPeopleEndpointWithBaseURL("https://api.mixpanel.com");
      }

      String var10 = var1.getString("com.mixpanel.android.MPConfig.GroupsEndpoint");
      if (var10 != null) {
         if (var4) {
            var10 = this.getEndPointWithIpTrackingParam(var10, this.getUseIpAddressForGeolocation());
         }

         this.setGroupsEndpoint(var10);
      } else {
         this.setGroupsEndpointWithBaseURL("https://api.mixpanel.com");
      }

      MPLog.v("MixpanelAPI.Conf", this.toString());
   }

   private String getEndPointWithIpTrackingParam(String var1, boolean var2) {
      boolean var3 = var1.contains("?ip=");
      String var4 = "1";
      if (var3) {
         StringBuilder var6 = new StringBuilder();
         var6.append(var1.substring(0, var1.indexOf("?ip=")));
         var6.append("?ip=");
         if (!var2) {
            var4 = "0";
         }

         var6.append(var4);
         return var6.toString();
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append(var1);
         var5.append("?ip=");
         if (!var2) {
            var4 = "0";
         }

         var5.append(var4);
         return var5.toString();
      }
   }

   public static MPConfig getInstance(Context var0, String var1) {
      return readConfig(var0.getApplicationContext(), var1);
   }

   private boolean getUseIpAddressForGeolocation() {
      return this.mUseIpAddressForGeolocation;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   static MPConfig readConfig(Context var0, String var1) {
      String var4 = var0.getPackageName();

      Bundle var3;
      try {
         var3 = var0.getPackageManager().getApplicationInfo(var4, 128).metaData;
      } catch (NameNotFoundException var7) {
         StringBuilder var8 = new StringBuilder("Can't configure Mixpanel with package name ");
         var8.append(var4);
         throw new RuntimeException(var8.toString(), var7);
      }

      Bundle var2 = var3;
      if (var3 == null) {
         try {
            var2 = new Bundle();
         } catch (NameNotFoundException var6) {
            StringBuilder var9 = new StringBuilder("Can't configure Mixpanel with package name ");
            var9.append(var4);
            throw new RuntimeException(var9.toString(), var6);
         }
      }

      try {
         return new MPConfig(var2, var0, var1);
      } catch (NameNotFoundException var5) {
         StringBuilder var10 = new StringBuilder("Can't configure Mixpanel with package name ");
         var10.append(var4);
         throw new RuntimeException(var10.toString(), var5);
      }
   }

   private void setEventsEndpoint(String var1) {
      this.mEventsEndpoint = var1;
   }

   private void setEventsEndpointWithBaseURL(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append("/track/");
      this.setEventsEndpoint(this.getEndPointWithIpTrackingParam(var2.toString(), this.getUseIpAddressForGeolocation()));
   }

   private void setGroupsEndpoint(String var1) {
      this.mGroupsEndpoint = var1;
   }

   private void setGroupsEndpointWithBaseURL(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append("/groups/");
      this.setGroupsEndpoint(this.getEndPointWithIpTrackingParam(var2.toString(), this.getUseIpAddressForGeolocation()));
   }

   private void setPeopleEndpoint(String var1) {
      this.mPeopleEndpoint = var1;
   }

   private void setPeopleEndpointWithBaseURL(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append("/engage/");
      this.setPeopleEndpoint(this.getEndPointWithIpTrackingParam(var2.toString(), this.getUseIpAddressForGeolocation()));
   }

   public int getBulkUploadLimit() {
      return this.mBulkUploadLimit;
   }

   public long getDataExpiration() {
      return this.mDataExpiration;
   }

   public boolean getDisableAppOpenEvent() {
      return this.mDisableAppOpenEvent;
   }

   public boolean getDisableExceptionHandler() {
      return this.mDisableExceptionHandler;
   }

   public String getEventsEndpoint() {
      return this.mEventsEndpoint;
   }

   public int getFlushBatchSize() {
      return this.mFlushBatchSize;
   }

   public int getFlushInterval() {
      return this.mFlushInterval;
   }

   public boolean getFlushOnBackground() {
      return this.mFlushOnBackground;
   }

   public String getGroupsEndpoint() {
      return this.mGroupsEndpoint;
   }

   public String getInstanceName() {
      return this.mInstanceName;
   }

   public int getMaximumDatabaseLimit() {
      return this.mMaximumDatabaseLimit;
   }

   public int getMinimumDatabaseLimit() {
      return this.mMinimumDatabaseLimit;
   }

   public int getMinimumSessionDuration() {
      return this.mMinSessionDuration;
   }

   public OfflineMode getOfflineMode() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield com/mixpanel/android/mpmetrics/MPConfig.mOfflineMode Lcom/mixpanel/android/util/OfflineMode;
      // 6: astore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: aload 1
      // a: areturn
      // b: astore 1
      // c: aload 0
      // d: monitorexit
      // e: aload 1
      // f: athrow
   }

   public String getPeopleEndpoint() {
      return this.mPeopleEndpoint;
   }

   public ProxyServerInteractor getProxyServerInteractor() {
      return this.serverCallbacks;
   }

   public boolean getRemoveLegacyResidualFiles() {
      return this.mRemoveLegacyResidualFiles;
   }

   public String getResourcePackageName() {
      return this.mResourcePackageName;
   }

   public SSLSocketFactory getSSLSocketFactory() {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: getfield com/mixpanel/android/mpmetrics/MPConfig.mSSLSocketFactory Ljavax/net/ssl/SSLSocketFactory;
      // 6: astore 1
      // 7: aload 0
      // 8: monitorexit
      // 9: aload 1
      // a: areturn
      // b: astore 1
      // c: aload 0
      // d: monitorexit
      // e: aload 1
      // f: athrow
   }

   public int getSessionTimeoutDuration() {
      return this.mSessionTimeoutDuration;
   }

   public boolean getTrackAutomaticEvents() {
      return this.mTrackAutomaticEvents;
   }

   public void setEnableLogging(boolean var1) {
      DEBUG = var1;
      int var2;
      if (var1) {
         var2 = 2;
      } else {
         var2 = Integer.MAX_VALUE;
      }

      MPLog.setLevel(var2);
   }

   public void setFlushBatchSize(int var1) {
      this.mFlushBatchSize = var1;
   }

   public void setMaximumDatabaseLimit(int var1) {
      this.mMaximumDatabaseLimit = var1;
   }

   public void setOfflineMode(OfflineMode param1) {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: aload 1
      // 4: putfield com/mixpanel/android/mpmetrics/MPConfig.mOfflineMode Lcom/mixpanel/android/util/OfflineMode;
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public void setProxyServerInteractor(ProxyServerInteractor var1) {
      this.serverCallbacks = var1;
   }

   public void setSSLSocketFactory(SSLSocketFactory param1) {
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
      // 0: aload 0
      // 1: monitorenter
      // 2: aload 0
      // 3: aload 1
      // 4: putfield com/mixpanel/android/mpmetrics/MPConfig.mSSLSocketFactory Ljavax/net/ssl/SSLSocketFactory;
      // 7: aload 0
      // 8: monitorexit
      // 9: return
      // a: astore 1
      // b: aload 0
      // c: monitorexit
      // d: aload 1
      // e: athrow
   }

   public void setServerURL(String var1) {
      this.setEventsEndpointWithBaseURL(var1);
      this.setPeopleEndpointWithBaseURL(var1);
      this.setGroupsEndpointWithBaseURL(var1);
   }

   public void setServerURL(String var1, ProxyServerInteractor var2) {
      this.setServerURL(var1);
      this.setProxyServerInteractor(var2);
   }

   public void setTrackAutomaticEvents(boolean var1) {
      this.mTrackAutomaticEvents = var1;
   }

   public void setUseIpAddressForGeolocation(boolean var1) {
      this.mUseIpAddressForGeolocation = var1;
      this.setEventsEndpoint(this.getEndPointWithIpTrackingParam(this.getEventsEndpoint(), var1));
      this.setPeopleEndpoint(this.getEndPointWithIpTrackingParam(this.getPeopleEndpoint(), var1));
      this.setGroupsEndpoint(this.getEndPointWithIpTrackingParam(this.getGroupsEndpoint(), var1));
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("Mixpanel (7.5.2) configured with:\n    TrackAutomaticEvents: ");
      var1.append(this.getTrackAutomaticEvents());
      var1.append("\n    BulkUploadLimit ");
      var1.append(this.getBulkUploadLimit());
      var1.append("\n    FlushInterval ");
      var1.append(this.getFlushInterval());
      var1.append("\n    FlushInterval ");
      var1.append(this.getFlushBatchSize());
      var1.append("\n    DataExpiration ");
      var1.append(this.getDataExpiration());
      var1.append("\n    MinimumDatabaseLimit ");
      var1.append(this.getMinimumDatabaseLimit());
      var1.append("\n    MaximumDatabaseLimit ");
      var1.append(this.getMaximumDatabaseLimit());
      var1.append("\n    DisableAppOpenEvent ");
      var1.append(this.getDisableAppOpenEvent());
      var1.append("\n    EnableDebugLogging ");
      var1.append(DEBUG);
      var1.append("\n    EventsEndpoint ");
      var1.append(this.getEventsEndpoint());
      var1.append("\n    PeopleEndpoint ");
      var1.append(this.getPeopleEndpoint());
      var1.append("\n    MinimumSessionDuration: ");
      var1.append(this.getMinimumSessionDuration());
      var1.append("\n    SessionTimeoutDuration: ");
      var1.append(this.getSessionTimeoutDuration());
      var1.append("\n    DisableExceptionHandler: ");
      var1.append(this.getDisableExceptionHandler());
      var1.append("\n    FlushOnBackground: ");
      var1.append(this.getFlushOnBackground());
      return var1.toString();
   }
}
