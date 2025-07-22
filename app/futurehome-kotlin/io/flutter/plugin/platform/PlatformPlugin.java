package io.flutter.plugin.platform;

import android.app.Activity;
import android.app.ActivityManager.TaskDescription;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;
import android.view.View.OnSystemUiVisibilityChangeListener;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;
import androidx.core.view.WindowInsetsControllerCompat;
import io.flutter.embedding.engine.systemchannels.PlatformChannel;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.List;

public class PlatformPlugin {
   public static final int DEFAULT_SYSTEM_UI = 1280;
   private static final String TAG = "PlatformPlugin";
   private final Activity activity;
   private PlatformChannel.SystemChromeStyle currentTheme;
   private int mEnabledOverlays;
   final PlatformChannel.PlatformMessageHandler mPlatformMessageHandler;
   private final PlatformChannel platformChannel;
   private final PlatformPlugin.PlatformPluginDelegate platformPluginDelegate;

   public PlatformPlugin(Activity var1, PlatformChannel var2) {
      this(var1, var2, null);
   }

   public PlatformPlugin(Activity var1, PlatformChannel var2, PlatformPlugin.PlatformPluginDelegate var3) {
      PlatformChannel.PlatformMessageHandler var4 = new PlatformChannel.PlatformMessageHandler(this) {
         final PlatformPlugin this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public boolean clipboardHasStrings() {
            return this.this$0.clipboardHasStrings();
         }

         @Override
         public CharSequence getClipboardData(PlatformChannel.ClipboardContentFormat var1) {
            return this.this$0.getClipboardData(var1);
         }

         @Override
         public void playSystemSound(PlatformChannel.SoundType var1) {
            this.this$0.playSystemSound(var1);
         }

         @Override
         public void popSystemNavigator() {
            this.this$0.popSystemNavigator();
         }

         @Override
         public void restoreSystemUiOverlays() {
            this.this$0.restoreSystemChromeSystemUIOverlays();
         }

         @Override
         public void setApplicationSwitcherDescription(PlatformChannel.AppSwitcherDescription var1) {
            this.this$0.setSystemChromeApplicationSwitcherDescription(var1);
         }

         @Override
         public void setClipboardData(String var1) {
            this.this$0.setClipboardData(var1);
         }

         @Override
         public void setFrameworkHandlesBack(boolean var1) {
            this.this$0.setFrameworkHandlesBack(var1);
         }

         @Override
         public void setPreferredOrientations(int var1) {
            this.this$0.setSystemChromePreferredOrientations(var1);
         }

         @Override
         public void setSystemUiChangeListener() {
            this.this$0.setSystemChromeChangeListener();
         }

         @Override
         public void setSystemUiOverlayStyle(PlatformChannel.SystemChromeStyle var1) {
            this.this$0.setSystemChromeSystemUIOverlayStyle(var1);
         }

         @Override
         public void share(String var1) {
            this.this$0.share(var1);
         }

         @Override
         public void showSystemOverlays(List<PlatformChannel.SystemUiOverlay> var1) {
            this.this$0.setSystemChromeEnabledSystemUIOverlays(var1);
         }

         @Override
         public void showSystemUiMode(PlatformChannel.SystemUiMode var1) {
            this.this$0.setSystemChromeEnabledSystemUIMode(var1);
         }

         @Override
         public void vibrateHapticFeedback(PlatformChannel.HapticFeedbackType var1) {
            this.this$0.vibrateHapticFeedback(var1);
         }
      };
      this.mPlatformMessageHandler = var4;
      this.activity = var1;
      this.platformChannel = var2;
      var2.setPlatformMessageHandler(var4);
      this.platformPluginDelegate = var3;
      this.mEnabledOverlays = 1280;
   }

   private boolean clipboardHasStrings() {
      ClipboardManager var1 = (ClipboardManager)this.activity.getSystemService("clipboard");
      if (!var1.hasPrimaryClip()) {
         return false;
      } else {
         ClipDescription var2 = var1.getPrimaryClipDescription();
         return var2 == null ? false : var2.hasMimeType("text/*");
      }
   }

   private CharSequence getClipboardData(PlatformChannel.ClipboardContentFormat param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: getfield io/flutter/plugin/platform/PlatformPlugin.activity Landroid/app/Activity;
      // 004: ldc "clipboard"
      // 006: invokevirtual android/app/Activity.getSystemService (Ljava/lang/String;)Ljava/lang/Object;
      // 009: checkcast android/content/ClipboardManager
      // 00c: astore 3
      // 00d: aload 3
      // 00e: invokevirtual android/content/ClipboardManager.hasPrimaryClip ()Z
      // 011: istore 2
      // 012: aconst_null
      // 013: astore 4
      // 015: iload 2
      // 016: ifne 01b
      // 019: aconst_null
      // 01a: areturn
      // 01b: aload 3
      // 01c: invokevirtual android/content/ClipboardManager.getPrimaryClip ()Landroid/content/ClipData;
      // 01f: astore 3
      // 020: aload 3
      // 021: ifnonnull 026
      // 024: aconst_null
      // 025: areturn
      // 026: aload 1
      // 027: ifnull 036
      // 02a: aload 1
      // 02b: getstatic io/flutter/embedding/engine/systemchannels/PlatformChannel$ClipboardContentFormat.PLAIN_TEXT Lio/flutter/embedding/engine/systemchannels/PlatformChannel$ClipboardContentFormat;
      // 02e: if_acmpne 034
      // 031: goto 036
      // 034: aconst_null
      // 035: areturn
      // 036: aload 3
      // 037: bipush 0
      // 038: invokevirtual android/content/ClipData.getItemAt (I)Landroid/content/ClipData$Item;
      // 03b: astore 5
      // 03d: aload 5
      // 03f: invokevirtual android/content/ClipData$Item.getText ()Ljava/lang/CharSequence;
      // 042: astore 3
      // 043: aload 3
      // 044: astore 1
      // 045: aload 3
      // 046: ifnonnull 0df
      // 049: aload 3
      // 04a: astore 1
      // 04b: aload 5
      // 04d: invokevirtual android/content/ClipData$Item.getUri ()Landroid/net/Uri;
      // 050: astore 6
      // 052: aload 6
      // 054: ifnonnull 062
      // 057: aload 3
      // 058: astore 1
      // 059: ldc "PlatformPlugin"
      // 05b: ldc "Clipboard item contained no textual content nor a URI to retrieve it from."
      // 05d: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;)V
      // 060: aconst_null
      // 061: areturn
      // 062: aload 3
      // 063: astore 1
      // 064: aload 6
      // 066: invokevirtual android/net/Uri.getScheme ()Ljava/lang/String;
      // 069: astore 4
      // 06b: aload 3
      // 06c: astore 1
      // 06d: aload 4
      // 06f: ldc "content"
      // 071: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 074: ifne 0a9
      // 077: aload 3
      // 078: astore 1
      // 079: new java/lang/StringBuilder
      // 07c: astore 5
      // 07e: aload 3
      // 07f: astore 1
      // 080: aload 5
      // 082: ldc "Clipboard item contains a Uri with scheme '"
      // 084: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 087: aload 3
      // 088: astore 1
      // 089: aload 5
      // 08b: aload 4
      // 08d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 090: pop
      // 091: aload 3
      // 092: astore 1
      // 093: aload 5
      // 095: ldc "'that is unhandled."
      // 097: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 09a: pop
      // 09b: aload 3
      // 09c: astore 1
      // 09d: ldc "PlatformPlugin"
      // 09f: aload 5
      // 0a1: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0a4: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;)V
      // 0a7: aconst_null
      // 0a8: areturn
      // 0a9: aload 3
      // 0aa: astore 1
      // 0ab: aload 0
      // 0ac: getfield io/flutter/plugin/platform/PlatformPlugin.activity Landroid/app/Activity;
      // 0af: invokevirtual android/app/Activity.getContentResolver ()Landroid/content/ContentResolver;
      // 0b2: aload 6
      // 0b4: ldc "text/*"
      // 0b6: aconst_null
      // 0b7: invokevirtual android/content/ContentResolver.openTypedAssetFileDescriptor (Landroid/net/Uri;Ljava/lang/String;Landroid/os/Bundle;)Landroid/content/res/AssetFileDescriptor;
      // 0ba: astore 4
      // 0bc: aload 3
      // 0bd: astore 1
      // 0be: aload 5
      // 0c0: aload 0
      // 0c1: getfield io/flutter/plugin/platform/PlatformPlugin.activity Landroid/app/Activity;
      // 0c4: invokevirtual android/content/ClipData$Item.coerceToText (Landroid/content/Context;)Ljava/lang/CharSequence;
      // 0c7: astore 3
      // 0c8: aload 3
      // 0c9: astore 1
      // 0ca: aload 4
      // 0cc: ifnull 0df
      // 0cf: aload 3
      // 0d0: astore 1
      // 0d1: aload 4
      // 0d3: invokevirtual android/content/res/AssetFileDescriptor.close ()V
      // 0d6: aload 3
      // 0d7: astore 1
      // 0d8: goto 0df
      // 0db: astore 3
      // 0dc: goto 0e5
      // 0df: aload 1
      // 0e0: areturn
      // 0e1: astore 3
      // 0e2: aload 4
      // 0e4: astore 1
      // 0e5: ldc "PlatformPlugin"
      // 0e7: ldc "Failed to close AssetFileDescriptor while trying to read text from URI."
      // 0e9: aload 3
      // 0ea: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 0ed: aload 1
      // 0ee: areturn
      // 0ef: astore 1
      // 0f0: ldc "PlatformPlugin"
      // 0f2: ldc_w "Clipboard text was unable to be received from content URI."
      // 0f5: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;)V
      // 0f8: aconst_null
      // 0f9: areturn
      // 0fa: astore 1
      // 0fb: ldc "PlatformPlugin"
      // 0fd: ldc_w "Attempted to get clipboard data that requires additional permission(s).\nSee the exception details for which permission(s) are required, and consider adding them to your Android Manifest as described in:\nhttps://developer.android.com/guide/topics/permissions/overview"
      // 100: aload 1
      // 101: invokestatic io/flutter/Log.w (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      // 104: aconst_null
      // 105: areturn
   }

   private void playSystemSound(PlatformChannel.SoundType var1) {
      if (var1 == PlatformChannel.SoundType.CLICK) {
         this.activity.getWindow().getDecorView().playSoundEffect(0);
      }
   }

   private void popSystemNavigator() {
      PlatformPlugin.PlatformPluginDelegate var1 = this.platformPluginDelegate;
      if (var1 == null || !var1.popSystemNavigator()) {
         Activity var2 = this.activity;
         if (var2 instanceof OnBackPressedDispatcherOwner) {
            ((OnBackPressedDispatcherOwner)var2).getOnBackPressedDispatcher().onBackPressed();
         } else {
            var2.finish();
         }
      }
   }

   private void restoreSystemChromeSystemUIOverlays() {
      this.updateSystemUiOverlays();
   }

   private void setClipboardData(String var1) {
      ((ClipboardManager)this.activity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text label?", var1));
   }

   private void setFrameworkHandlesBack(boolean var1) {
      PlatformPlugin.PlatformPluginDelegate var2 = this.platformPluginDelegate;
      if (var2 != null) {
         var2.setFrameworkHandlesBack(var1);
      }
   }

   private void setSystemChromeApplicationSwitcherDescription(PlatformChannel.AppSwitcherDescription var1) {
      if (VERSION.SDK_INT < 28) {
         this.activity.setTaskDescription(new TaskDescription(var1.label, null, var1.color));
      } else {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$3();
         TaskDescription var2 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1.label, 0, var1.color);
         this.activity.setTaskDescription(var2);
      }
   }

   private void setSystemChromeChangeListener() {
      View var1 = this.activity.getWindow().getDecorView();
      var1.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener(this, var1) {
         final PlatformPlugin this$0;
         final View val$decorView;

         {
            this.this$0 = var1;
            this.val$decorView = var2;
         }

         public void onSystemUiVisibilityChange(int var1) {
            this.val$decorView.post(new PlatformPlugin$2$$ExternalSyntheticLambda0(this, var1));
         }
      });
   }

   private void setSystemChromeEnabledSystemUIMode(PlatformChannel.SystemUiMode var1) {
      short var2;
      if (var1 == PlatformChannel.SystemUiMode.LEAN_BACK) {
         var2 = 1798;
      } else if (var1 == PlatformChannel.SystemUiMode.IMMERSIVE) {
         var2 = 3846;
      } else if (var1 == PlatformChannel.SystemUiMode.IMMERSIVE_STICKY) {
         var2 = 5894;
      } else {
         if (var1 != PlatformChannel.SystemUiMode.EDGE_TO_EDGE || VERSION.SDK_INT < 29) {
            return;
         }

         var2 = 1792;
      }

      this.mEnabledOverlays = var2;
      this.updateSystemUiOverlays();
   }

   private void setSystemChromeEnabledSystemUIOverlays(List<PlatformChannel.SystemUiOverlay> var1) {
      short var2;
      if (var1.size() == 0) {
         var2 = 5894;
      } else {
         var2 = 1798;
      }

      for (int var3 = 0; var3 < var1.size(); var3++) {
         PlatformChannel.SystemUiOverlay var5 = (PlatformChannel.SystemUiOverlay)var1.get(var3);
         int var4 = <unrepresentable>.$SwitchMap$io$flutter$embedding$engine$systemchannels$PlatformChannel$SystemUiOverlay[var5.ordinal()];
         if (var4 != 1) {
            if (var4 == 2) {
               var2 &= -515;
            }
         } else {
            var2 &= -5;
         }
      }

      this.mEnabledOverlays = var2;
      this.updateSystemUiOverlays();
   }

   private void setSystemChromePreferredOrientations(int var1) {
      this.activity.setRequestedOrientation(var1);
   }

   private void setSystemChromeSystemUIOverlayStyle(PlatformChannel.SystemChromeStyle var1) {
      Window var3 = this.activity.getWindow();
      WindowInsetsControllerCompat var4 = new WindowInsetsControllerCompat(var3, var3.getDecorView());
      if (VERSION.SDK_INT < 30) {
         var3.addFlags(Integer.MIN_VALUE);
         var3.clearFlags(201326592);
      }

      if (VERSION.SDK_INT >= 23) {
         if (var1.statusBarIconBrightness != null) {
            int var2 = <unrepresentable>.$SwitchMap$io$flutter$embedding$engine$systemchannels$PlatformChannel$Brightness[var1.statusBarIconBrightness
               .ordinal()];
            if (var2 != 1) {
               if (var2 == 2) {
                  var4.setAppearanceLightStatusBars(false);
               }
            } else {
               var4.setAppearanceLightStatusBars(true);
            }
         }

         if (var1.statusBarColor != null) {
            var3.setStatusBarColor(var1.statusBarColor);
         }
      }

      if (var1.systemStatusBarContrastEnforced != null && VERSION.SDK_INT >= 29) {
         ExternalSyntheticApiModelOutline0.m(var3, var1.systemStatusBarContrastEnforced);
      }

      if (VERSION.SDK_INT >= 26) {
         if (var1.systemNavigationBarIconBrightness != null) {
            int var5 = <unrepresentable>.$SwitchMap$io$flutter$embedding$engine$systemchannels$PlatformChannel$Brightness[var1.systemNavigationBarIconBrightness
               .ordinal()];
            if (var5 != 1) {
               if (var5 == 2) {
                  var4.setAppearanceLightNavigationBars(false);
               }
            } else {
               var4.setAppearanceLightNavigationBars(true);
            }
         }

         if (var1.systemNavigationBarColor != null) {
            var3.setNavigationBarColor(var1.systemNavigationBarColor);
         }
      }

      if (var1.systemNavigationBarDividerColor != null && VERSION.SDK_INT >= 28) {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var3, var1.systemNavigationBarDividerColor);
      }

      if (var1.systemNavigationBarContrastEnforced != null && VERSION.SDK_INT >= 29) {
         ExternalSyntheticApiModelOutline0.m$1(var3, var1.systemNavigationBarContrastEnforced);
      }

      this.currentTheme = var1;
   }

   private void share(String var1) {
      Intent var2 = new Intent();
      var2.setAction("android.intent.action.SEND");
      var2.setType("text/plain");
      var2.putExtra("android.intent.extra.TEXT", var1);
      this.activity.startActivity(Intent.createChooser(var2, null));
   }

   public void destroy() {
      this.platformChannel.setPlatformMessageHandler(null);
   }

   public void updateSystemUiOverlays() {
      this.activity.getWindow().getDecorView().setSystemUiVisibility(this.mEnabledOverlays);
      PlatformChannel.SystemChromeStyle var1 = this.currentTheme;
      if (var1 != null) {
         this.setSystemChromeSystemUIOverlayStyle(var1);
      }
   }

   void vibrateHapticFeedback(PlatformChannel.HapticFeedbackType var1) {
      View var3 = this.activity.getWindow().getDecorView();
      int var2 = <unrepresentable>.$SwitchMap$io$flutter$embedding$engine$systemchannels$PlatformChannel$HapticFeedbackType[var1.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3) {
               if (var2 != 4) {
                  if (var2 == 5) {
                     var3.performHapticFeedback(4);
                  }
               } else if (VERSION.SDK_INT >= 23) {
                  var3.performHapticFeedback(6);
               }
            } else {
               var3.performHapticFeedback(3);
            }
         } else {
            var3.performHapticFeedback(1);
         }
      } else {
         var3.performHapticFeedback(0);
      }
   }

   public interface PlatformPluginDelegate {
      boolean popSystemNavigator();

      void setFrameworkHandlesBack(boolean var1);
   }
}
