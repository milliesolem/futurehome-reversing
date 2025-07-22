package com.mixpanel.android.mpmetrics;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.mixpanel.android.util.MPLog;
import java.lang.reflect.InvocationTargetException;

class SystemInformation {
   private static final String LOGTAG = "MixpanelAPI.SysInfo";
   private static SystemInformation sInstance;
   private static final Object sInstanceLock = new Object();
   private final String mAppName;
   private final Integer mAppVersionCode;
   private final String mAppVersionName;
   private final Context mContext;
   private final DisplayMetrics mDisplayMetrics;
   private final Boolean mHasNFC;
   private final Boolean mHasTelephony;

   private SystemInformation(Context var1) {
      this.mContext = var1;
      PackageManager var6 = var1.getPackageManager();
      Object var5 = null;

      String var3;
      Integer var22;
      label69: {
         label68: {
            try {
               var4 = var6.getPackageInfo(var1.getPackageName(), 0);
               var3 = var4.versionName;
            } catch (NameNotFoundException var14) {
               var3 = null;
               break label68;
            }

            try {
               var22 = var4.versionCode;
               break label69;
            } catch (NameNotFoundException var13) {
            }
         }

         MPLog.w("MixpanelAPI.SysInfo", "System information constructed with a context that apparently doesn't exist.");
         var22 = null;
      }

      ApplicationInfo var7 = var1.getApplicationInfo();
      int var2 = var7.labelRes;
      this.mAppVersionName = var3;
      this.mAppVersionCode = var22;
      String var15;
      if (var2 == 0) {
         if (var7.nonLocalizedLabel == null) {
            var15 = "Misc";
         } else {
            var15 = var7.nonLocalizedLabel.toString();
         }
      } else {
         var15 = var1.getString(var2);
      }

      this.mAppName = var15;
      Class var16 = var6.getClass();

      try {
         var20 = var16.getMethod("hasSystemFeature", String.class);
      } catch (NoSuchMethodException var8) {
         var20 = null;
      }

      label60:
      if (var20 != null) {
         label58: {
            label57: {
               label56: {
                  label55: {
                     try {
                        var17 = (Boolean)var20.invoke(var6, "android.hardware.nfc");
                     } catch (InvocationTargetException var11) {
                        var17 = null;
                        break label55;
                     } catch (IllegalAccessException var12) {
                        var17 = null;
                        break label56;
                     }

                     try {
                        var23 = (Boolean)var20.invoke(var6, "android.hardware.telephony");
                        break label58;
                     } catch (InvocationTargetException var9) {
                     } catch (IllegalAccessException var10) {
                        break label56;
                     }
                  }

                  MPLog.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
                  var21 = var17;
                  break label57;
               }

               MPLog.w("MixpanelAPI.SysInfo", "System version appeared to support PackageManager.hasSystemFeature, but we were unable to call it.");
               var21 = var17;
            }

            var18 = null;
            break label60;
         }

         var21 = var17;
         var18 = var23;
      } else {
         var18 = null;
         var21 = (Boolean)var5;
      }

      this.mHasNFC = var21;
      this.mHasTelephony = var18;
      DisplayMetrics var19 = new DisplayMetrics();
      this.mDisplayMetrics = var19;
      ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(var19);
   }

   static SystemInformation getInstance(Context param0) {
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
      // 00: getstatic com/mixpanel/android/mpmetrics/SystemInformation.sInstanceLock Ljava/lang/Object;
      // 03: astore 1
      // 04: aload 1
      // 05: monitorenter
      // 06: getstatic com/mixpanel/android/mpmetrics/SystemInformation.sInstance Lcom/mixpanel/android/mpmetrics/SystemInformation;
      // 09: ifnonnull 1e
      // 0c: aload 0
      // 0d: invokevirtual android/content/Context.getApplicationContext ()Landroid/content/Context;
      // 10: astore 0
      // 11: new com/mixpanel/android/mpmetrics/SystemInformation
      // 14: astore 2
      // 15: aload 2
      // 16: aload 0
      // 17: invokespecial com/mixpanel/android/mpmetrics/SystemInformation.<init> (Landroid/content/Context;)V
      // 1a: aload 2
      // 1b: putstatic com/mixpanel/android/mpmetrics/SystemInformation.sInstance Lcom/mixpanel/android/mpmetrics/SystemInformation;
      // 1e: aload 1
      // 1f: monitorexit
      // 20: getstatic com/mixpanel/android/mpmetrics/SystemInformation.sInstance Lcom/mixpanel/android/mpmetrics/SystemInformation;
      // 23: areturn
      // 24: astore 0
      // 25: aload 1
      // 26: monitorexit
      // 27: aload 0
      // 28: athrow
   }

   public String getAppName() {
      return this.mAppName;
   }

   public Integer getAppVersionCode() {
      return this.mAppVersionCode;
   }

   public String getAppVersionName() {
      return this.mAppVersionName;
   }

   public String getBluetoothVersion() {
      String var1;
      if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
         var1 = "ble";
      } else if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
         var1 = "classic";
      } else {
         var1 = "none";
      }

      return var1;
   }

   public String getCurrentNetworkOperator() {
      TelephonyManager var1 = (TelephonyManager)this.mContext.getSystemService("phone");
      String var2;
      if (var1 != null) {
         var2 = var1.getNetworkOperatorName();
      } else {
         var2 = null;
      }

      return var2;
   }

   public DisplayMetrics getDisplayMetrics() {
      return this.mDisplayMetrics;
   }

   public boolean hasNFC() {
      return this.mHasNFC;
   }

   public boolean hasTelephony() {
      return this.mHasTelephony;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public Boolean isBluetoothEnabled() {
      Object var2 = null;
      Boolean var1 = (Boolean)var2;

      BluetoothAdapter var3;
      try {
         if (this.mContext.getPackageManager().checkPermission("android.permission.BLUETOOTH", this.mContext.getPackageName()) != 0) {
            return var1;
         }

         var3 = BluetoothAdapter.getDefaultAdapter();
      } catch (Exception var5) {
         return (Boolean)var2;
      }

      var1 = (Boolean)var2;
      if (var3 != null) {
         try {
            var1 = var3.isEnabled();
         } catch (Exception var4) {
            var1 = (Boolean)var2;
         }
      }

      return var1;
   }

   public Boolean isWifiConnected() {
      Boolean var4;
      if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
         boolean var2;
         label17: {
            NetworkInfo var3 = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
            if (var3 != null) {
               int var1 = var3.getType();
               var2 = true;
               if (var1 == 1 && var3.isConnected()) {
                  break label17;
               }
            }

            var2 = false;
         }

         var4 = var2;
      } else {
         var4 = null;
      }

      return var4;
   }
}
