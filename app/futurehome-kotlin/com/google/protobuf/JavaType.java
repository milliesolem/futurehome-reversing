package com.google.protobuf;

public enum JavaType {
   BOOLEAN,
   BYTE_STRING,
   DOUBLE,
   ENUM,
   FLOAT,
   INT,
   LONG,
   MESSAGE,
   STRING,
   VOID;

   private static final JavaType[] $VALUES;
   private final Class<?> boxedType;
   private final Object defaultDefault;
   private final Class<?> type;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      JavaType var4 = new JavaType(Void.class, Void.class, null);
      VOID = var4;
      JavaType var0 = new JavaType(int.class, Integer.class, 0);
      INT = var0;
      JavaType var6 = new JavaType(long.class, Long.class, 0L);
      LONG = var6;
      JavaType var5 = new JavaType(float.class, Float.class, 0.0F);
      FLOAT = var5;
      JavaType var2 = new JavaType(double.class, Double.class, 0.0);
      DOUBLE = var2;
      JavaType var8 = new JavaType(boolean.class, Boolean.class, false);
      BOOLEAN = var8;
      JavaType var9 = new JavaType(String.class, String.class, "");
      STRING = var9;
      JavaType var3 = new JavaType(ByteString.class, ByteString.class, ByteString.EMPTY);
      BYTE_STRING = var3;
      JavaType var1 = new JavaType(int.class, Integer.class, null);
      ENUM = var1;
      JavaType var7 = new JavaType(Object.class, Object.class, null);
      MESSAGE = var7;
      $VALUES = new JavaType[]{var4, var0, var6, var5, var2, var8, var9, var3, var1, var7};
   }

   private JavaType(Class<?> var3, Class<?> var4, Object var5) {
      this.type = var3;
      this.boxedType = var4;
      this.defaultDefault = var5;
   }

   public Class<?> getBoxedType() {
      return this.boxedType;
   }

   public Object getDefaultDefault() {
      return this.defaultDefault;
   }

   public Class<?> getType() {
      return this.type;
   }

   public boolean isValidType(Class<?> var1) {
      return this.type.isAssignableFrom(var1);
   }
}
