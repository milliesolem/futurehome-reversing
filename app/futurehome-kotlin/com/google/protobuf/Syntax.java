package com.google.protobuf;

public enum Syntax implements Internal.EnumLite {
   SYNTAX_EDITIONS,
   SYNTAX_PROTO2,
   SYNTAX_PROTO3,
   UNRECOGNIZED;

   private static final Syntax[] $VALUES;
   public static final int SYNTAX_EDITIONS_VALUE = 2;
   public static final int SYNTAX_PROTO2_VALUE = 0;
   public static final int SYNTAX_PROTO3_VALUE = 1;
   private static final Internal.EnumLiteMap<Syntax> internalValueMap = new Internal.EnumLiteMap<Syntax>() {
      public Syntax findValueByNumber(int var1) {
         return Syntax.forNumber(var1);
      }
   };
   private final int value;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      Syntax var0 = new Syntax(0);
      SYNTAX_PROTO2 = var0;
      Syntax var2 = new Syntax(1);
      SYNTAX_PROTO3 = var2;
      Syntax var3 = new Syntax(2);
      SYNTAX_EDITIONS = var3;
      Syntax var1 = new Syntax(-1);
      UNRECOGNIZED = var1;
      $VALUES = new Syntax[]{var0, var2, var3, var1};
   }

   private Syntax(int var3) {
      this.value = var3;
   }

   public static Syntax forNumber(int var0) {
      if (var0 != 0) {
         if (var0 != 1) {
            return var0 != 2 ? null : SYNTAX_EDITIONS;
         } else {
            return SYNTAX_PROTO3;
         }
      } else {
         return SYNTAX_PROTO2;
      }
   }

   public static Internal.EnumLiteMap<Syntax> internalGetValueMap() {
      return internalValueMap;
   }

   public static Internal.EnumVerifier internalGetVerifier() {
      return Syntax.SyntaxVerifier.INSTANCE;
   }

   @Deprecated
   public static Syntax valueOf(int var0) {
      return forNumber(var0);
   }

   @Override
   public final int getNumber() {
      if (this != UNRECOGNIZED) {
         return this.value;
      } else {
         throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
      }
   }

   private static final class SyntaxVerifier implements Internal.EnumVerifier {
      static final Internal.EnumVerifier INSTANCE = new Syntax.SyntaxVerifier();

      @Override
      public boolean isInRange(int var1) {
         boolean var2;
         if (Syntax.forNumber(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
