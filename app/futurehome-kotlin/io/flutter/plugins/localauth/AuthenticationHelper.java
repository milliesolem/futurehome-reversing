package io.flutter.plugins.localauth;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.AuthenticationCallback;
import androidx.biometric.BiometricPrompt.AuthenticationResult;
import androidx.biometric.BiometricPrompt.PromptInfo;
import androidx.biometric.BiometricPrompt.PromptInfo.Builder;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import java.util.concurrent.Executor;

class AuthenticationHelper extends AuthenticationCallback implements ActivityLifecycleCallbacks, DefaultLifecycleObserver {
   private final FragmentActivity activity;
   private boolean activityPaused = false;
   private BiometricPrompt biometricPrompt;
   private final AuthenticationHelper.AuthCompletionHandler completionHandler;
   private final boolean isAuthSticky;
   private final Lifecycle lifecycle;
   private final PromptInfo promptInfo;
   private final Messages.AuthStrings strings;
   private final AuthenticationHelper.UiThreadExecutor uiThreadExecutor;
   private final boolean useErrorDialogs;

   AuthenticationHelper(
      Lifecycle var1,
      FragmentActivity var2,
      Messages.AuthOptions var3,
      Messages.AuthStrings var4,
      AuthenticationHelper.AuthCompletionHandler var5,
      boolean var6
   ) {
      this.lifecycle = var1;
      this.activity = var2;
      this.completionHandler = var5;
      this.strings = var4;
      this.isAuthSticky = var3.getSticky();
      this.useErrorDialogs = var3.getUseErrorDialgs();
      this.uiThreadExecutor = new AuthenticationHelper.UiThreadExecutor();
      Builder var8 = new Builder()
         .setDescription(var4.getReason())
         .setTitle(var4.getSignInTitle())
         .setSubtitle(var4.getBiometricHint())
         .setConfirmationRequired(var3.getSensitiveTransaction());
      char var7;
      if (var6) {
         var7 = '胿';
      } else {
         var8.setNegativeButtonText(var4.getCancelButton());
         var7 = 255;
      }

      var8.setAllowedAuthenticators(var7);
      this.promptInfo = var8.build();
   }

   private void showGoToSettingsDialog(String var1, String var2) {
      View var3 = LayoutInflater.from(this.activity).inflate(R.layout.go_to_setting, null, false);
      TextView var5 = (TextView)var3.findViewById(R.id.fingerprint_required);
      TextView var4 = (TextView)var3.findViewById(R.id.go_to_setting_description);
      var5.setText(var1);
      var4.setText(var2);
      ContextThemeWrapper var6 = new ContextThemeWrapper(this.activity, R.style.AlertDialogCustom);
      AuthenticationHelper$$ExternalSyntheticLambda0 var8 = new AuthenticationHelper$$ExternalSyntheticLambda0(this);
      AuthenticationHelper$$ExternalSyntheticLambda1 var7 = new AuthenticationHelper$$ExternalSyntheticLambda1(this);
      new android.app.AlertDialog.Builder(var6)
         .setView(var3)
         .setPositiveButton(this.strings.getGoToSettingsButton(), var8)
         .setNegativeButton(this.strings.getCancelButton(), var7)
         .setCancelable(false)
         .show();
   }

   private void stop() {
      Lifecycle var1 = this.lifecycle;
      if (var1 != null) {
         var1.removeObserver(this);
      } else {
         this.activity.getApplication().unregisterActivityLifecycleCallbacks(this);
      }
   }

   void authenticate() {
      Lifecycle var1 = this.lifecycle;
      if (var1 != null) {
         var1.addObserver(this);
      } else {
         this.activity.getApplication().registerActivityLifecycleCallbacks(this);
      }

      BiometricPrompt var2 = new BiometricPrompt(this.activity, this.uiThreadExecutor, this);
      this.biometricPrompt = var2;
      var2.authenticate(this.promptInfo);
   }

   public void onActivityCreated(Activity var1, Bundle var2) {
   }

   public void onActivityDestroyed(Activity var1) {
   }

   public void onActivityPaused(Activity var1) {
      if (this.isAuthSticky) {
         this.activityPaused = true;
      }
   }

   public void onActivityResumed(Activity var1) {
      if (this.isAuthSticky) {
         this.activityPaused = false;
         BiometricPrompt var2 = new BiometricPrompt(this.activity, this.uiThreadExecutor, this);
         this.uiThreadExecutor.handler.post(new AuthenticationHelper$$ExternalSyntheticLambda2(this, var2));
      }
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity var1) {
   }

   public void onActivityStopped(Activity var1) {
   }

   public void onAuthenticationError(int var1, CharSequence var2) {
      if (var1 == 1) {
         this.completionHandler.complete(Messages.AuthResult.ERROR_NOT_AVAILABLE);
      } else {
         label38:
         if (var1 == 7) {
            this.completionHandler.complete(Messages.AuthResult.ERROR_LOCKED_OUT_TEMPORARILY);
         } else if (var1 == 9) {
            this.completionHandler.complete(Messages.AuthResult.ERROR_LOCKED_OUT_PERMANENTLY);
         } else if (var1 == 14) {
            if (this.useErrorDialogs) {
               this.showGoToSettingsDialog(this.strings.getDeviceCredentialsRequiredTitle(), this.strings.getDeviceCredentialsSetupDescription());
               return;
            }

            this.completionHandler.complete(Messages.AuthResult.ERROR_NOT_AVAILABLE);
         } else {
            label61: {
               if (var1 != 4) {
                  if (var1 == 5) {
                     if (this.activityPaused && this.isAuthSticky) {
                        return;
                     }

                     this.completionHandler.complete(Messages.AuthResult.FAILURE);
                     break label61;
                  }

                  if (var1 != 11) {
                     if (var1 != 12) {
                        this.completionHandler.complete(Messages.AuthResult.FAILURE);
                        break label61;
                     }
                     break label38;
                  }
               }

               if (this.useErrorDialogs) {
                  this.showGoToSettingsDialog(this.strings.getBiometricRequiredTitle(), this.strings.getGoToSettingsDescription());
                  return;
               }

               this.completionHandler.complete(Messages.AuthResult.ERROR_NOT_ENROLLED);
            }
         }
      }

      this.stop();
   }

   public void onAuthenticationFailed() {
   }

   public void onAuthenticationSucceeded(AuthenticationResult var1) {
      this.completionHandler.complete(Messages.AuthResult.SUCCESS);
      this.stop();
   }

   public void onCreate(LifecycleOwner var1) {
   }

   public void onDestroy(LifecycleOwner var1) {
   }

   public void onPause(LifecycleOwner var1) {
      this.onActivityPaused(null);
   }

   public void onResume(LifecycleOwner var1) {
      this.onActivityResumed(null);
   }

   public void onStart(LifecycleOwner var1) {
   }

   public void onStop(LifecycleOwner var1) {
   }

   void stopAuthentication() {
      BiometricPrompt var1 = this.biometricPrompt;
      if (var1 != null) {
         var1.cancelAuthentication();
         this.biometricPrompt = null;
      }
   }

   interface AuthCompletionHandler {
      void complete(Messages.AuthResult var1);
   }

   static class UiThreadExecutor implements Executor {
      final Handler handler = new Handler(Looper.getMainLooper());

      @Override
      public void execute(Runnable var1) {
         this.handler.post(var1);
      }
   }
}
