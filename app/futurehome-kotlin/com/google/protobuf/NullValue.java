package com.google.protobuf;

public enum NullValue implements Internal.EnumLite {
   NULL_VALUE,
   UNRECOGNIZED;

   private static final NullValue[] $VALUES;
   public static final int NULL_VALUE_VALUE = 0;
   private static final Internal.EnumLiteMap<NullValue> internalValueMap = new Internal.EnumLiteMap<NullValue>() {
      public NullValue findValueByNumber(int var1) {
         return NullValue.forNumber(var1);
      }
   };
   private final int value;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      NullValue var0 = new NullValue(0);
      NULL_VALUE = var0;
      NullValue var1 = new NullValue(-1);
      UNRECOGNIZED = var1;
      $VALUES = new NullValue[]{var0, var1};
   }

   private NullValue(int var3) {
      this.value = var3;
   }

   public static NullValue forNumber(int var0) {
      return var0 != 0 ? null : NULL_VALUE;
   }

   public static Internal.EnumLiteMap<NullValue> internalGetValueMap() {
      return internalValueMap;
   }

   public static Internal.EnumVerifier internalGetVerifier() {
      return NullValue.NullValueVerifier.INSTANCE;
   }

   @Deprecated
   public static NullValue valueOf(int var0) {
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

   private static final class NullValueVerifier implements Internal.EnumVerifier {
      static final Internal.EnumVerifier INSTANCE = new NullValue.NullValueVerifier();

      @Override
      public boolean isInRange(int var1) {
         boolean var2;
         if (NullValue.forNumber(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }
}
