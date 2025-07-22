package io.reactivex.annotations;

public enum BackpressureKind {
   ERROR,
   FULL,
   NONE,
   PASS_THROUGH,
   SPECIAL,
   UNBOUNDED_IN;
   private static final BackpressureKind[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      BackpressureKind var0 = new BackpressureKind();
      PASS_THROUGH = var0;
      BackpressureKind var1 = new BackpressureKind();
      FULL = var1;
      BackpressureKind var5 = new BackpressureKind();
      SPECIAL = var5;
      BackpressureKind var4 = new BackpressureKind();
      UNBOUNDED_IN = var4;
      BackpressureKind var3 = new BackpressureKind();
      ERROR = var3;
      BackpressureKind var2 = new BackpressureKind();
      NONE = var2;
      $VALUES = new BackpressureKind[]{var0, var1, var5, var4, var3, var2};
   }
}
