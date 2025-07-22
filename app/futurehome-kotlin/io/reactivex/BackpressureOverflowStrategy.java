package io.reactivex;

public enum BackpressureOverflowStrategy {
   DROP_LATEST,
   DROP_OLDEST,
   ERROR;
   private static final BackpressureOverflowStrategy[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      BackpressureOverflowStrategy var0 = new BackpressureOverflowStrategy();
      ERROR = var0;
      BackpressureOverflowStrategy var2 = new BackpressureOverflowStrategy();
      DROP_OLDEST = var2;
      BackpressureOverflowStrategy var1 = new BackpressureOverflowStrategy();
      DROP_LATEST = var1;
      $VALUES = new BackpressureOverflowStrategy[]{var0, var2, var1};
   }
}
