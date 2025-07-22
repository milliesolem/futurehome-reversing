package com.polidea.rxandroidble2.scan;

public enum ScanCallbackType {
   CALLBACK_TYPE_ALL_MATCHES,
   CALLBACK_TYPE_BATCH,
   CALLBACK_TYPE_FIRST_MATCH,
   CALLBACK_TYPE_MATCH_LOST,
   CALLBACK_TYPE_UNKNOWN,
   CALLBACK_TYPE_UNSPECIFIED;
   private static final ScanCallbackType[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      ScanCallbackType var5 = new ScanCallbackType();
      CALLBACK_TYPE_ALL_MATCHES = var5;
      ScanCallbackType var4 = new ScanCallbackType();
      CALLBACK_TYPE_FIRST_MATCH = var4;
      ScanCallbackType var3 = new ScanCallbackType();
      CALLBACK_TYPE_MATCH_LOST = var3;
      ScanCallbackType var2 = new ScanCallbackType();
      CALLBACK_TYPE_BATCH = var2;
      ScanCallbackType var1 = new ScanCallbackType();
      CALLBACK_TYPE_UNSPECIFIED = var1;
      ScanCallbackType var0 = new ScanCallbackType();
      CALLBACK_TYPE_UNKNOWN = var0;
      $VALUES = new ScanCallbackType[]{var5, var4, var3, var2, var1, var0};
   }
}
