package com.polidea.rxandroidble2;

public class LogOptions {
   private final Integer logLevel;
   private final LogOptions.Logger logger;
   private final Integer macAddressLogSetting;
   private final Boolean shouldLogAttributeValues;
   private final Boolean shouldLogScannedPeripherals;
   private final Integer uuidLogSetting;

   LogOptions(Integer var1, Integer var2, Integer var3, Boolean var4, Boolean var5, LogOptions.Logger var6) {
      this.logLevel = var1;
      this.macAddressLogSetting = var2;
      this.uuidLogSetting = var3;
      this.shouldLogAttributeValues = var4;
      this.shouldLogScannedPeripherals = var5;
      this.logger = var6;
   }

   public Integer getLogLevel() {
      return this.logLevel;
   }

   public LogOptions.Logger getLogger() {
      return this.logger;
   }

   public Integer getMacAddressLogSetting() {
      return this.macAddressLogSetting;
   }

   public Boolean getShouldLogAttributeValues() {
      return this.shouldLogAttributeValues;
   }

   public Boolean getShouldLogScannedPeripherals() {
      return this.shouldLogScannedPeripherals;
   }

   public Integer getUuidLogSetting() {
      return this.uuidLogSetting;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("LogOptions{logLevel=");
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

   public static class Builder {
      private Integer logLevel;
      private LogOptions.Logger logger;
      private Integer macAddressLogSetting;
      private Boolean shouldLogAttributeValues;
      private Boolean shouldLogScannedPeripherals;
      private Integer uuidsLogSetting;

      public LogOptions build() {
         return new LogOptions(
            this.logLevel, this.macAddressLogSetting, this.uuidsLogSetting, this.shouldLogAttributeValues, this.shouldLogScannedPeripherals, this.logger
         );
      }

      public LogOptions.Builder setLogLevel(Integer var1) {
         this.logLevel = var1;
         return this;
      }

      public LogOptions.Builder setLogger(LogOptions.Logger var1) {
         this.logger = var1;
         return this;
      }

      public LogOptions.Builder setMacAddressLogSetting(Integer var1) {
         this.macAddressLogSetting = var1;
         return this;
      }

      public LogOptions.Builder setShouldLogAttributeValues(Boolean var1) {
         this.shouldLogAttributeValues = var1;
         return this;
      }

      public LogOptions.Builder setShouldLogScannedPeripherals(Boolean var1) {
         this.shouldLogScannedPeripherals = var1;
         return this;
      }

      public LogOptions.Builder setUuidsLogSetting(Integer var1) {
         this.uuidsLogSetting = var1;
         return this;
      }
   }

   public interface Logger {
      void log(int var1, String var2, String var3);
   }
}
