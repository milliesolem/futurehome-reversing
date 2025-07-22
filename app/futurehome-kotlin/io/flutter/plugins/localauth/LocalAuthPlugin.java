package io.flutter.plugins.localauth;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.biometric.BiometricManager;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.plugins.lifecycle.FlutterLifecycleAdapter;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocalAuthPlugin implements FlutterPlugin, ActivityAware, Messages.LocalAuthApi {
   private static final int LOCK_REQUEST_CODE = 221;
   private Activity activity;
   private AuthenticationHelper authHelper;
   final AtomicBoolean authInProgress = new AtomicBoolean(false);
   private BiometricManager biometricManager;
   private KeyguardManager keyguardManager;
   private Lifecycle lifecycle;
   Messages.Result<Messages.AuthResult> lockRequestResult;
   private final PluginRegistry.ActivityResultListener resultListener = new PluginRegistry.ActivityResultListener(this) {
      final LocalAuthPlugin this$0;

      {
         this.this$0 = var1;
      }

      @Override
      public boolean onActivityResult(int var1, int var2, Intent var3) {
         if (var1 == 221) {
            if (var2 == -1 && this.this$0.lockRequestResult != null) {
               LocalAuthPlugin var5 = this.this$0;
               var5.onAuthenticationCompleted(var5.lockRequestResult, Messages.AuthResult.SUCCESS);
            } else {
               LocalAuthPlugin var4 = this.this$0;
               var4.onAuthenticationCompleted(var4.lockRequestResult, Messages.AuthResult.FAILURE);
            }

            this.this$0.lockRequestResult = null;
         }

         return false;
      }
   };

   private boolean canAuthenticateWithBiometrics() {
      BiometricManager var2 = this.biometricManager;
      boolean var1 = false;
      if (var2 == null) {
         return false;
      } else {
         if (var2.canAuthenticate(255) == 0) {
            var1 = true;
         }

         return var1;
      }
   }

   private boolean hasBiometricHardware() {
      BiometricManager var2 = this.biometricManager;
      boolean var1 = false;
      if (var2 == null) {
         return false;
      } else {
         if (var2.canAuthenticate(255) != 12) {
            var1 = true;
         }

         return var1;
      }
   }

   private void setServicesFromActivity(Activity var1) {
      if (var1 != null) {
         this.activity = var1;
         Context var2 = var1.getBaseContext();
         this.biometricManager = BiometricManager.from(var1);
         this.keyguardManager = (KeyguardManager)var2.getSystemService("keyguard");
      }
   }

   @Override
   public void authenticate(Messages.AuthOptions var1, Messages.AuthStrings var2, Messages.Result<Messages.AuthResult> var3) {
      if (this.authInProgress.get()) {
         var3.success(Messages.AuthResult.ERROR_ALREADY_IN_PROGRESS);
      } else {
         Activity var5 = this.activity;
         if (var5 == null || var5.isFinishing()) {
            var3.success(Messages.AuthResult.ERROR_NO_ACTIVITY);
         } else if (!(this.activity instanceof FragmentActivity)) {
            var3.success(Messages.AuthResult.ERROR_NOT_FRAGMENT_ACTIVITY);
         } else if (!this.isDeviceSupported()) {
            var3.success(Messages.AuthResult.ERROR_NOT_AVAILABLE);
         } else {
            AtomicBoolean var7 = this.authInProgress;
            boolean var4 = true;
            var7.set(true);
            AuthenticationHelper.AuthCompletionHandler var6 = this.createAuthCompletionHandler(var3);
            if (var1.getBiometricOnly() || !this.canAuthenticateWithDeviceCredential()) {
               var4 = false;
            }

            this.sendAuthenticationRequest(var1, var2, var4, var6);
         }
      }
   }

   public boolean canAuthenticateWithDeviceCredential() {
      if (VERSION.SDK_INT < 30) {
         return this.isDeviceSecure();
      } else {
         BiometricManager var2 = this.biometricManager;
         boolean var1 = false;
         if (var2 == null) {
            return false;
         } else {
            if (var2.canAuthenticate(32768) == 0) {
               var1 = true;
            }

            return var1;
         }
      }
   }

   public AuthenticationHelper.AuthCompletionHandler createAuthCompletionHandler(Messages.Result<Messages.AuthResult> var1) {
      return new LocalAuthPlugin$$ExternalSyntheticLambda1(this, var1);
   }

   @Override
   public Boolean deviceCanSupportBiometrics() {
      return this.hasBiometricHardware();
   }

   final Activity getActivity() {
      return this.activity;
   }

   @Override
   public List<Messages.AuthClassification> getEnrolledBiometrics() {
      ArrayList var1 = new ArrayList();
      if (this.biometricManager.canAuthenticate(255) == 0) {
         var1.add(Messages.AuthClassification.WEAK);
      }

      if (this.biometricManager.canAuthenticate(15) == 0) {
         var1.add(Messages.AuthClassification.STRONG);
      }

      return var1;
   }

   public boolean isDeviceSecure() {
      KeyguardManager var3 = this.keyguardManager;
      boolean var2 = false;
      if (var3 == null) {
         return false;
      } else {
         boolean var1 = var2;
         if (VERSION.SDK_INT >= 23) {
            var1 = var2;
            if (Share$$ExternalSyntheticApiModelOutline0.m(this.keyguardManager)) {
               var1 = true;
            }
         }

         return var1;
      }
   }

   @Override
   public Boolean isDeviceSupported() {
      boolean var1;
      if (!this.isDeviceSecure() && !this.canAuthenticateWithBiometrics()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      var1.addActivityResultListener(this.resultListener);
      this.setServicesFromActivity(var1.getActivity());
      this.lifecycle = FlutterLifecycleAdapter.getActivityLifecycle(var1);
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Messages$LocalAuthApi$_CC.setUp(var1.getBinaryMessenger(), this);
   }

   void onAuthenticationCompleted(Messages.Result<Messages.AuthResult> var1, Messages.AuthResult var2) {
      if (this.authInProgress.compareAndSet(true, false)) {
         var1.success(var2);
      }
   }

   @Override
   public void onDetachedFromActivity() {
      this.lifecycle = null;
      this.activity = null;
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.lifecycle = null;
      this.activity = null;
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Messages$LocalAuthApi$_CC.setUp(var1.getBinaryMessenger(), null);
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      var1.addActivityResultListener(this.resultListener);
      this.setServicesFromActivity(var1.getActivity());
      this.lifecycle = FlutterLifecycleAdapter.getActivityLifecycle(var1);
   }

   public void sendAuthenticationRequest(Messages.AuthOptions var1, Messages.AuthStrings var2, boolean var3, AuthenticationHelper.AuthCompletionHandler var4) {
      AuthenticationHelper var5 = new AuthenticationHelper(this.lifecycle, (FragmentActivity)this.activity, var1, var2, var4, var3);
      this.authHelper = var5;
      var5.authenticate();
   }

   void setBiometricManager(BiometricManager var1) {
      this.biometricManager = var1;
   }

   void setKeyguardManager(KeyguardManager var1) {
      this.keyguardManager = var1;
   }

   @Override
   public Boolean stopAuthentication() {
      try {
         if (this.authHelper != null && this.authInProgress.get()) {
            this.authHelper.stopAuthentication();
            this.authHelper = null;
         }

         this.authInProgress.set(false);
      } catch (Exception var2) {
         return false;
      }

      return true;
   }
}
