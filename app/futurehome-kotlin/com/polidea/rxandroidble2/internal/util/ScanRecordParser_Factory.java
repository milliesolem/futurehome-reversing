package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;

public final class ScanRecordParser_Factory implements Factory<ScanRecordParser> {
   public static ScanRecordParser_Factory create() {
      return ScanRecordParser_Factory.InstanceHolder.INSTANCE;
   }

   public static ScanRecordParser newInstance() {
      return new ScanRecordParser();
   }

   public ScanRecordParser get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final ScanRecordParser_Factory INSTANCE = new ScanRecordParser_Factory();
   }
}
