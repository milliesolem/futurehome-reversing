package com.polidea.rxandroidble2.exceptions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

public class BleScanException extends BleException {
   public static final int BLUETOOTH_CANNOT_START = 0;
   public static final int BLUETOOTH_DISABLED = 1;
   public static final int BLUETOOTH_NOT_AVAILABLE = 2;
   public static final int LOCATION_PERMISSION_MISSING = 3;
   public static final int LOCATION_SERVICES_DISABLED = 4;
   public static final int SCAN_FAILED_ALREADY_STARTED = 5;
   public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 6;
   public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 8;
   public static final int SCAN_FAILED_INTERNAL_ERROR = 7;
   public static final int SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES = 9;
   public static final int UNDOCUMENTED_SCAN_THROTTLE = 2147483646;
   public static final int UNKNOWN_ERROR_CODE = Integer.MAX_VALUE;
   private final int reason;
   private final Date retryDateSuggestion;

   public BleScanException(int var1) {
      super(createMessage(var1, null));
      this.reason = var1;
      this.retryDateSuggestion = null;
   }

   public BleScanException(int var1, Throwable var2) {
      super(createMessage(var1, null), var2);
      this.reason = var1;
      this.retryDateSuggestion = null;
   }

   public BleScanException(int var1, Date var2) {
      super(createMessage(var1, var2));
      this.reason = var1;
      this.retryDateSuggestion = var2;
   }

   private static String createMessage(int var0, Date var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(reasonDescription(var0));
      var2.append(" (code ");
      var2.append(var0);
      var2.append(")");
      var2.append(retryDateSuggestionIfExists(var1));
      return var2.toString();
   }

   private static String reasonDescription(int var0) {
      if (var0 != 2147483646) {
         switch (var0) {
            case 0:
               return "Bluetooth cannot start";
            case 1:
               return "Bluetooth disabled";
            case 2:
               return "Bluetooth not available";
            case 3:
               return "Location Permission missing";
            case 4:
               return "Location Services disabled";
            case 5:
               return "Scan failed because it has already started";
            case 6:
               return "Scan failed because application registration failed";
            case 7:
               return "Scan failed because of an internal error";
            case 8:
               return "Scan failed because feature unsupported";
            case 9:
               return "Scan failed because out of hardware resources";
            default:
               return "Unknown error";
         }
      } else {
         return "Undocumented scan throttle";
      }
   }

   private static String retryDateSuggestionIfExists(Date var0) {
      if (var0 == null) {
         return "";
      } else {
         StringBuilder var1 = new StringBuilder(", suggested retry date is ");
         var1.append(var0);
         return var1.toString();
      }
   }

   public int getReason() {
      return this.reason;
   }

   public Date getRetryDateSuggestion() {
      return this.retryDateSuggestion;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Reason {
   }
}
