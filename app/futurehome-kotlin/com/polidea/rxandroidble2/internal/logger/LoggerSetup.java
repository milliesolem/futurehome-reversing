package com.polidea.rxandroidble2.internal.logger;

import com.polidea.rxandroidble2.LogOptions;

public class LoggerSetup {
   public final int logLevel;
   public final LogOptions.Logger logger;
   public final int macAddressLogSetting;
   public final boolean shouldLogAttributeValues;
   public final boolean shouldLogScannedPeripherals;
   public final int uuidLogSetting;

   public LoggerSetup(int var1, int var2, int var3, boolean var4, boolean var5, LogOptions.Logger var6) {
      this.logLevel = var1;
      this.macAddressLogSetting = var2;
      this.uuidLogSetting = var3;
      this.shouldLogAttributeValues = var4;
      this.shouldLogScannedPeripherals = var5;
      this.logger = var6;
   }

   public LoggerSetup merge(LogOptions var1) {
      int var2;
      if (var1.getLogLevel() != null) {
         var2 = var1.getLogLevel();
      } else {
         var2 = this.logLevel;
      }

      int var3;
      if (var1.getMacAddressLogSetting() != null) {
         var3 = var1.getMacAddressLogSetting();
      } else {
         var3 = this.macAddressLogSetting;
      }

      int var4;
      if (var1.getUuidLogSetting() != null) {
         var4 = var1.getUuidLogSetting();
      } else {
         var4 = this.uuidLogSetting;
      }

      boolean var5;
      if (var1.getShouldLogAttributeValues() != null) {
         var5 = var1.getShouldLogAttributeValues();
      } else {
         var5 = this.shouldLogAttributeValues;
      }

      boolean var6;
      if (var1.getShouldLogScannedPeripherals() != null) {
         var6 = var1.getShouldLogScannedPeripherals();
      } else {
         var6 = this.shouldLogScannedPeripherals;
      }

      LogOptions.Logger var7;
      if (var1.getLogger() != null) {
         var7 = var1.getLogger();
      } else {
         var7 = this.logger;
      }

      return new LoggerSetup(var2, var3, var4, var5, var6, var7);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("LoggerSetup{logLevel=");
      var1.append(this.logLevel);
      var1.append(", macAddressLogSetting=");
      var1.append(this.macAddressLogSetting);
      var1.append(", uuidLogSetting=");
      var1.append(this.uuidLogSetting);
      var1.append(", shouldLogAttributeValues=");
      var1.append(this.shouldLogAttributeValues);
      var1.append(", shouldLogScannedPeripherals=");
      var1.append(this.shouldLogScannedPeripherals);
      var1.append(", logger=");
      var1.append(this.logger);
      var1.append('}');
      return var1.toString();
   }
}
