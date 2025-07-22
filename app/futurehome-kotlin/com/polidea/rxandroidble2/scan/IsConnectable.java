package com.polidea.rxandroidble2.scan;

public enum IsConnectable {
   CONNECTABLE,
   LEGACY_UNKNOWN,
   NOT_CONNECTABLE;
   private static final IsConnectable[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      IsConnectable var0 = new IsConnectable();
      LEGACY_UNKNOWN = var0;
      IsConnectable var1 = new IsConnectable();
      CONNECTABLE = var1;
      IsConnectable var2 = new IsConnectable();
      NOT_CONNECTABLE = var2;
      $VALUES = new IsConnectable[]{var0, var1, var2};
   }
}
