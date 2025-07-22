package io.flutter.plugins.localauth;

import android.util.Log;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Messages {
   protected static ArrayList<Object> wrapError(Throwable var0) {
      ArrayList var1 = new ArrayList(3);
      if (var0 instanceof Messages.FlutterError) {
         Messages.FlutterError var3 = (Messages.FlutterError)var0;
         var1.add(var3.code);
         var1.add(var3.getMessage());
         var1.add(var3.details);
      } else {
         var1.add(var0.toString());
         var1.add(var0.getClass().getSimpleName());
         StringBuilder var2 = new StringBuilder("Cause: ");
         var2.append(var0.getCause());
         var2.append(", Stacktrace: ");
         var2.append(Log.getStackTraceString(var0));
         var1.add(var2.toString());
      }

      return var1;
   }

   public static enum AuthClassification {
      STRONG(1),
      WEAK(0);

      private static final Messages.AuthClassification[] $VALUES = $values();
      final int index;

      private AuthClassification(int var3) {
         this.index = var3;
      }
   }

   public static final class AuthOptions {
      private Boolean biometricOnly;
      private Boolean sensitiveTransaction;
      private Boolean sticky;
      private Boolean useErrorDialgs;

      AuthOptions() {
      }

      static Messages.AuthOptions fromList(ArrayList<Object> var0) {
         Messages.AuthOptions var1 = new Messages.AuthOptions();
         var1.setBiometricOnly((Boolean)var0.get(0));
         var1.setSensitiveTransaction((Boolean)var0.get(1));
         var1.setSticky((Boolean)var0.get(2));
         var1.setUseErrorDialgs((Boolean)var0.get(3));
         return var1;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            if (!this.biometricOnly.equals(var1.biometricOnly)
               || !this.sensitiveTransaction.equals(var1.sensitiveTransaction)
               || !this.sticky.equals(var1.sticky)
               || !this.useErrorDialgs.equals(var1.useErrorDialgs)) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }

      public Boolean getBiometricOnly() {
         return this.biometricOnly;
      }

      public Boolean getSensitiveTransaction() {
         return this.sensitiveTransaction;
      }

      public Boolean getSticky() {
         return this.sticky;
      }

      public Boolean getUseErrorDialgs() {
         return this.useErrorDialgs;
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.biometricOnly, this.sensitiveTransaction, this.sticky, this.useErrorDialgs);
      }

      public void setBiometricOnly(Boolean var1) {
         if (var1 != null) {
            this.biometricOnly = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"biometricOnly\" is null.");
         }
      }

      public void setSensitiveTransaction(Boolean var1) {
         if (var1 != null) {
            this.sensitiveTransaction = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"sensitiveTransaction\" is null.");
         }
      }

      public void setSticky(Boolean var1) {
         if (var1 != null) {
            this.sticky = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"sticky\" is null.");
         }
      }

      public void setUseErrorDialgs(Boolean var1) {
         if (var1 != null) {
            this.useErrorDialgs = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"useErrorDialgs\" is null.");
         }
      }

      ArrayList<Object> toList() {
         ArrayList var1 = new ArrayList(4);
         var1.add(this.biometricOnly);
         var1.add(this.sensitiveTransaction);
         var1.add(this.sticky);
         var1.add(this.useErrorDialgs);
         return var1;
      }

      public static final class Builder {
         private Boolean biometricOnly;
         private Boolean sensitiveTransaction;
         private Boolean sticky;
         private Boolean useErrorDialgs;

         public Messages.AuthOptions build() {
            Messages.AuthOptions var1 = new Messages.AuthOptions();
            var1.setBiometricOnly(this.biometricOnly);
            var1.setSensitiveTransaction(this.sensitiveTransaction);
            var1.setSticky(this.sticky);
            var1.setUseErrorDialgs(this.useErrorDialgs);
            return var1;
         }

         public Messages.AuthOptions.Builder setBiometricOnly(Boolean var1) {
            this.biometricOnly = var1;
            return this;
         }

         public Messages.AuthOptions.Builder setSensitiveTransaction(Boolean var1) {
            this.sensitiveTransaction = var1;
            return this;
         }

         public Messages.AuthOptions.Builder setSticky(Boolean var1) {
            this.sticky = var1;
            return this;
         }

         public Messages.AuthOptions.Builder setUseErrorDialgs(Boolean var1) {
            this.useErrorDialgs = var1;
            return this;
         }
      }
   }

   public static enum AuthResult {
      ERROR_ALREADY_IN_PROGRESS(2),
      ERROR_LOCKED_OUT_PERMANENTLY(8),
      ERROR_LOCKED_OUT_TEMPORARILY(7),
      ERROR_NOT_AVAILABLE(5),
      ERROR_NOT_ENROLLED(6),
      ERROR_NOT_FRAGMENT_ACTIVITY(4),
      ERROR_NO_ACTIVITY(3),
      FAILURE(1),
      SUCCESS(0);

      private static final Messages.AuthResult[] $VALUES = $values();
      final int index;

      private AuthResult(int var3) {
         this.index = var3;
      }
   }

   public static final class AuthStrings {
      private String biometricHint;
      private String biometricNotRecognized;
      private String biometricRequiredTitle;
      private String cancelButton;
      private String deviceCredentialsRequiredTitle;
      private String deviceCredentialsSetupDescription;
      private String goToSettingsButton;
      private String goToSettingsDescription;
      private String reason;
      private String signInTitle;

      AuthStrings() {
      }

      static Messages.AuthStrings fromList(ArrayList<Object> var0) {
         Messages.AuthStrings var1 = new Messages.AuthStrings();
         var1.setReason((String)var0.get(0));
         var1.setBiometricHint((String)var0.get(1));
         var1.setBiometricNotRecognized((String)var0.get(2));
         var1.setBiometricRequiredTitle((String)var0.get(3));
         var1.setCancelButton((String)var0.get(4));
         var1.setDeviceCredentialsRequiredTitle((String)var0.get(5));
         var1.setDeviceCredentialsSetupDescription((String)var0.get(6));
         var1.setGoToSettingsButton((String)var0.get(7));
         var1.setGoToSettingsDescription((String)var0.get(8));
         var1.setSignInTitle((String)var0.get(9));
         return var1;
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (this == var1) {
            return true;
         } else if (var1 != null && this.getClass() == var1.getClass()) {
            var1 = var1;
            if (!this.reason.equals(var1.reason)
               || !this.biometricHint.equals(var1.biometricHint)
               || !this.biometricNotRecognized.equals(var1.biometricNotRecognized)
               || !this.biometricRequiredTitle.equals(var1.biometricRequiredTitle)
               || !this.cancelButton.equals(var1.cancelButton)
               || !this.deviceCredentialsRequiredTitle.equals(var1.deviceCredentialsRequiredTitle)
               || !this.deviceCredentialsSetupDescription.equals(var1.deviceCredentialsSetupDescription)
               || !this.goToSettingsButton.equals(var1.goToSettingsButton)
               || !this.goToSettingsDescription.equals(var1.goToSettingsDescription)
               || !this.signInTitle.equals(var1.signInTitle)) {
               var2 = false;
            }

            return var2;
         } else {
            return false;
         }
      }

      public String getBiometricHint() {
         return this.biometricHint;
      }

      public String getBiometricNotRecognized() {
         return this.biometricNotRecognized;
      }

      public String getBiometricRequiredTitle() {
         return this.biometricRequiredTitle;
      }

      public String getCancelButton() {
         return this.cancelButton;
      }

      public String getDeviceCredentialsRequiredTitle() {
         return this.deviceCredentialsRequiredTitle;
      }

      public String getDeviceCredentialsSetupDescription() {
         return this.deviceCredentialsSetupDescription;
      }

      public String getGoToSettingsButton() {
         return this.goToSettingsButton;
      }

      public String getGoToSettingsDescription() {
         return this.goToSettingsDescription;
      }

      public String getReason() {
         return this.reason;
      }

      public String getSignInTitle() {
         return this.signInTitle;
      }

      @Override
      public int hashCode() {
         return Objects.hash(
            this.reason,
            this.biometricHint,
            this.biometricNotRecognized,
            this.biometricRequiredTitle,
            this.cancelButton,
            this.deviceCredentialsRequiredTitle,
            this.deviceCredentialsSetupDescription,
            this.goToSettingsButton,
            this.goToSettingsDescription,
            this.signInTitle
         );
      }

      public void setBiometricHint(String var1) {
         if (var1 != null) {
            this.biometricHint = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"biometricHint\" is null.");
         }
      }

      public void setBiometricNotRecognized(String var1) {
         if (var1 != null) {
            this.biometricNotRecognized = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"biometricNotRecognized\" is null.");
         }
      }

      public void setBiometricRequiredTitle(String var1) {
         if (var1 != null) {
            this.biometricRequiredTitle = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"biometricRequiredTitle\" is null.");
         }
      }

      public void setCancelButton(String var1) {
         if (var1 != null) {
            this.cancelButton = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"cancelButton\" is null.");
         }
      }

      public void setDeviceCredentialsRequiredTitle(String var1) {
         if (var1 != null) {
            this.deviceCredentialsRequiredTitle = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"deviceCredentialsRequiredTitle\" is null.");
         }
      }

      public void setDeviceCredentialsSetupDescription(String var1) {
         if (var1 != null) {
            this.deviceCredentialsSetupDescription = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"deviceCredentialsSetupDescription\" is null.");
         }
      }

      public void setGoToSettingsButton(String var1) {
         if (var1 != null) {
            this.goToSettingsButton = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"goToSettingsButton\" is null.");
         }
      }

      public void setGoToSettingsDescription(String var1) {
         if (var1 != null) {
            this.goToSettingsDescription = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"goToSettingsDescription\" is null.");
         }
      }

      public void setReason(String var1) {
         if (var1 != null) {
            this.reason = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"reason\" is null.");
         }
      }

      public void setSignInTitle(String var1) {
         if (var1 != null) {
            this.signInTitle = var1;
         } else {
            throw new IllegalStateException("Nonnull field \"signInTitle\" is null.");
         }
      }

      ArrayList<Object> toList() {
         ArrayList var1 = new ArrayList(10);
         var1.add(this.reason);
         var1.add(this.biometricHint);
         var1.add(this.biometricNotRecognized);
         var1.add(this.biometricRequiredTitle);
         var1.add(this.cancelButton);
         var1.add(this.deviceCredentialsRequiredTitle);
         var1.add(this.deviceCredentialsSetupDescription);
         var1.add(this.goToSettingsButton);
         var1.add(this.goToSettingsDescription);
         var1.add(this.signInTitle);
         return var1;
      }

      public static final class Builder {
         private String biometricHint;
         private String biometricNotRecognized;
         private String biometricRequiredTitle;
         private String cancelButton;
         private String deviceCredentialsRequiredTitle;
         private String deviceCredentialsSetupDescription;
         private String goToSettingsButton;
         private String goToSettingsDescription;
         private String reason;
         private String signInTitle;

         public Messages.AuthStrings build() {
            Messages.AuthStrings var1 = new Messages.AuthStrings();
            var1.setReason(this.reason);
            var1.setBiometricHint(this.biometricHint);
            var1.setBiometricNotRecognized(this.biometricNotRecognized);
            var1.setBiometricRequiredTitle(this.biometricRequiredTitle);
            var1.setCancelButton(this.cancelButton);
            var1.setDeviceCredentialsRequiredTitle(this.deviceCredentialsRequiredTitle);
            var1.setDeviceCredentialsSetupDescription(this.deviceCredentialsSetupDescription);
            var1.setGoToSettingsButton(this.goToSettingsButton);
            var1.setGoToSettingsDescription(this.goToSettingsDescription);
            var1.setSignInTitle(this.signInTitle);
            return var1;
         }

         public Messages.AuthStrings.Builder setBiometricHint(String var1) {
            this.biometricHint = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setBiometricNotRecognized(String var1) {
            this.biometricNotRecognized = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setBiometricRequiredTitle(String var1) {
            this.biometricRequiredTitle = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setCancelButton(String var1) {
            this.cancelButton = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setDeviceCredentialsRequiredTitle(String var1) {
            this.deviceCredentialsRequiredTitle = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setDeviceCredentialsSetupDescription(String var1) {
            this.deviceCredentialsSetupDescription = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setGoToSettingsButton(String var1) {
            this.goToSettingsButton = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setGoToSettingsDescription(String var1) {
            this.goToSettingsDescription = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setReason(String var1) {
            this.reason = var1;
            return this;
         }

         public Messages.AuthStrings.Builder setSignInTitle(String var1) {
            this.signInTitle = var1;
            return this;
         }
      }
   }

   @Retention(RetentionPolicy.CLASS)
   @Target({ElementType.METHOD})
   @interface CanIgnoreReturnValue {
   }

   public static class FlutterError extends RuntimeException {
      public final String code;
      public final Object details;

      public FlutterError(String var1, String var2, Object var3) {
         super(var2);
         this.code = var1;
         this.details = var3;
      }
   }

   public interface LocalAuthApi {
      void authenticate(Messages.AuthOptions var1, Messages.AuthStrings var2, Messages.Result<Messages.AuthResult> var3);

      Boolean deviceCanSupportBiometrics();

      List<Messages.AuthClassification> getEnrolledBiometrics();

      Boolean isDeviceSupported();

      Boolean stopAuthentication();
   }

   public interface NullableResult<T> {
      void error(Throwable var1);

      void success(T var1);
   }

   private static class PigeonCodec extends StandardMessageCodec {
      public static final Messages.PigeonCodec INSTANCE = new Messages.PigeonCodec();

      @Override
      protected Object readValueOfType(byte var1, ByteBuffer var2) {
         Object var3 = null;
         Object var4 = null;
         switch (var1) {
            case -127:
               Object var7 = this.readValue(var2);
               if (var7 == null) {
                  var7 = var3;
               } else {
                  var7 = Messages.AuthResult.values()[((Long)var7).intValue()];
               }

               return var7;
            case -126:
               Object var5 = this.readValue(var2);
               if (var5 == null) {
                  var5 = var4;
               } else {
                  var5 = Messages.AuthClassification.values()[((Long)var5).intValue()];
               }

               return var5;
            case -125:
               return Messages.AuthStrings.fromList((ArrayList<Object>)this.readValue(var2));
            case -124:
               return Messages.AuthOptions.fromList((ArrayList<Object>)this.readValue(var2));
            default:
               return super.readValueOfType(var1, var2);
         }
      }

      @Override
      protected void writeValue(ByteArrayOutputStream var1, Object var2) {
         boolean var3 = var2 instanceof Messages.AuthResult;
         Object var4 = null;
         Object var5 = null;
         if (var3) {
            var1.write(129);
            if (var2 == null) {
               var2 = (Integer)var5;
            } else {
               var2 = ((Messages.AuthResult)var2).index;
            }

            this.writeValue(var1, var2);
         } else if (var2 instanceof Messages.AuthClassification) {
            var1.write(130);
            if (var2 == null) {
               var2 = (Integer)var4;
            } else {
               var2 = ((Messages.AuthClassification)var2).index;
            }

            this.writeValue(var1, var2);
         } else if (var2 instanceof Messages.AuthStrings) {
            var1.write(131);
            this.writeValue(var1, ((Messages.AuthStrings)var2).toList());
         } else if (var2 instanceof Messages.AuthOptions) {
            var1.write(132);
            this.writeValue(var1, ((Messages.AuthOptions)var2).toList());
         } else {
            super.writeValue(var1, var2);
         }
      }
   }

   public interface Result<T> {
      void error(Throwable var1);

      void success(T var1);
   }

   public interface VoidResult {
      void error(Throwable var1);

      void success();
   }
}
