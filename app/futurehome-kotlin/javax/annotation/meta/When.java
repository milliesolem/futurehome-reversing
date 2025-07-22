package javax.annotation.meta;

public enum When {
   ALWAYS,
   MAYBE,
   NEVER,
   UNKNOWN;
   private static final When[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      When var0 = new When();
      ALWAYS = var0;
      When var2 = new When();
      UNKNOWN = var2;
      When var1 = new When();
      MAYBE = var1;
      When var3 = new When();
      NEVER = var3;
      $VALUES = new When[]{var0, var2, var1, var3};
   }
}
