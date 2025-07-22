package io.reactivex;

public enum BackpressureStrategy {
   BUFFER,
   DROP,
   ERROR,
   LATEST,
   MISSING;
   private static final BackpressureStrategy[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      BackpressureStrategy var1 = new BackpressureStrategy();
      MISSING = var1;
      BackpressureStrategy var0 = new BackpressureStrategy();
      ERROR = var0;
      BackpressureStrategy var4 = new BackpressureStrategy();
      BUFFER = var4;
      BackpressureStrategy var2 = new BackpressureStrategy();
      DROP = var2;
      BackpressureStrategy var3 = new BackpressureStrategy();
      LATEST = var3;
      $VALUES = new BackpressureStrategy[]{var1, var0, var4, var2, var3};
   }
}
