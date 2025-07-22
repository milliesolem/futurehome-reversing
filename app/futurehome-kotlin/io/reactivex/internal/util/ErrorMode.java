package io.reactivex.internal.util;

public enum ErrorMode {
   BOUNDARY,
   END,
   IMMEDIATE;
   private static final ErrorMode[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      ErrorMode var1 = new ErrorMode();
      IMMEDIATE = var1;
      ErrorMode var0 = new ErrorMode();
      BOUNDARY = var0;
      ErrorMode var2 = new ErrorMode();
      END = var2;
      $VALUES = new ErrorMode[]{var1, var0, var2};
   }
}
