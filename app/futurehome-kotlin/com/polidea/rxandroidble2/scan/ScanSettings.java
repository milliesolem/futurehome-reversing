package com.polidea.rxandroidble2.scan;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.polidea.rxandroidble2.internal.scan.ExternalScanSettingsExtension;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScanSettings implements Parcelable, ExternalScanSettingsExtension<ScanSettings> {
   public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
   public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
   public static final int CALLBACK_TYPE_MATCH_LOST = 4;
   public static final Creator<ScanSettings> CREATOR = new Creator<ScanSettings>() {
      public ScanSettings createFromParcel(Parcel var1) {
         return new ScanSettings(var1);
      }

      public ScanSettings[] newArray(int var1) {
         return new ScanSettings[var1];
      }
   };
   public static final int MATCH_MODE_AGGRESSIVE = 1;
   public static final int MATCH_MODE_STICKY = 2;
   public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
   public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
   public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
   public static final int SCAN_MODE_BALANCED = 1;
   public static final int SCAN_MODE_LOW_LATENCY = 2;
   public static final int SCAN_MODE_LOW_POWER = 0;
   public static final int SCAN_MODE_OPPORTUNISTIC = -1;
   private int mCallbackType;
   private boolean mLegacy;
   private int mMatchMode;
   private int mNumOfMatchesPerFilter;
   private long mReportDelayMillis;
   private int mScanMode;
   private boolean mShouldCheckLocationProviderState;

   ScanSettings(int var1, int var2, long var3, int var5, int var6, boolean var7, boolean var8) {
      this.mScanMode = var1;
      this.mCallbackType = var2;
      this.mReportDelayMillis = var3;
      this.mNumOfMatchesPerFilter = var6;
      this.mMatchMode = var5;
      this.mLegacy = var7;
      this.mShouldCheckLocationProviderState = var8;
   }

   ScanSettings(Parcel var1) {
      this.mScanMode = var1.readInt();
      this.mCallbackType = var1.readInt();
      this.mReportDelayMillis = var1.readLong();
      this.mMatchMode = var1.readInt();
      this.mNumOfMatchesPerFilter = var1.readInt();
      int var2 = var1.readInt();
      boolean var4 = true;
      boolean var3;
      if (var2 != 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.mLegacy = var3;
      if (var1.readInt() != 0) {
         var3 = var4;
      } else {
         var3 = false;
      }

      this.mShouldCheckLocationProviderState = var3;
   }

   public ScanSettings copyWithCallbackType(int var1) {
      return new ScanSettings(
         this.mScanMode, var1, this.mReportDelayMillis, this.mMatchMode, this.mNumOfMatchesPerFilter, this.mLegacy, this.mShouldCheckLocationProviderState
      );
   }

   public int describeContents() {
      return 0;
   }

   public int getCallbackType() {
      return this.mCallbackType;
   }

   public boolean getLegacy() {
      return this.mLegacy;
   }

   public int getMatchMode() {
      return this.mMatchMode;
   }

   public int getNumOfMatches() {
      return this.mNumOfMatchesPerFilter;
   }

   public long getReportDelayMillis() {
      return this.mReportDelayMillis;
   }

   public int getScanMode() {
      return this.mScanMode;
   }

   @Override
   public boolean shouldCheckLocationProviderState() {
      return this.mShouldCheckLocationProviderState;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeInt(this.mScanMode);
      var1.writeInt(this.mCallbackType);
      var1.writeLong(this.mReportDelayMillis);
      var1.writeInt(this.mMatchMode);
      var1.writeInt(this.mNumOfMatchesPerFilter);
      var1.writeInt(this.mLegacy);
      var1.writeInt(this.mShouldCheckLocationProviderState);
   }

   public static final class Builder implements ExternalScanSettingsExtension.Builder {
      private int mCallbackType;
      private boolean mLegacy;
      private int mMatchMode;
      private int mNumOfMatchesPerFilter;
      private long mReportDelayMillis;
      private int mScanMode = 0;
      private boolean mShouldCheckLocationProviderState;

      public Builder() {
         this.mCallbackType = 1;
         this.mReportDelayMillis = 0L;
         this.mMatchMode = 1;
         this.mNumOfMatchesPerFilter = 3;
         this.mLegacy = true;
         this.mShouldCheckLocationProviderState = true;
      }

      private static boolean isValidCallbackType(int var0) {
         boolean var2 = true;
         boolean var1 = var2;
         if (var0 != 1) {
            var1 = var2;
            if (var0 != 2) {
               if (var0 == 4) {
                  var1 = var2;
               } else if (var0 == 6) {
                  var1 = var2;
               } else {
                  var1 = false;
               }
            }
         }

         return var1;
      }

      public ScanSettings build() {
         return new ScanSettings(
            this.mScanMode,
            this.mCallbackType,
            this.mReportDelayMillis,
            this.mMatchMode,
            this.mNumOfMatchesPerFilter,
            this.mLegacy,
            this.mShouldCheckLocationProviderState
         );
      }

      public ScanSettings.Builder setCallbackType(int var1) {
         if (isValidCallbackType(var1)) {
            this.mCallbackType = var1;
            return this;
         } else {
            StringBuilder var2 = new StringBuilder("invalid callback type - ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         }
      }

      public ScanSettings.Builder setLegacy(boolean var1) {
         this.mLegacy = var1;
         return this;
      }

      public ScanSettings.Builder setScanMode(int var1) {
         if (var1 >= -1 && var1 <= 2) {
            this.mScanMode = var1;
            return this;
         } else {
            StringBuilder var2 = new StringBuilder("invalid scan mode ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         }
      }

      public ScanSettings.Builder setShouldCheckLocationServicesState(boolean var1) {
         this.mShouldCheckLocationProviderState = var1;
         return this;
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface CallbackType {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface MatchMode {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface MatchNum {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface ScanMode {
   }
}
