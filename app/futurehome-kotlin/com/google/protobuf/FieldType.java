package com.google.protobuf;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

public enum FieldType {
   BOOL,
   BOOL_LIST,
   BOOL_LIST_PACKED,
   BYTES,
   BYTES_LIST,
   DOUBLE,
   DOUBLE_LIST,
   DOUBLE_LIST_PACKED,
   ENUM,
   ENUM_LIST,
   ENUM_LIST_PACKED,
   FIXED32,
   FIXED32_LIST,
   FIXED32_LIST_PACKED,
   FIXED64,
   FIXED64_LIST,
   FIXED64_LIST_PACKED,
   FLOAT,
   FLOAT_LIST,
   FLOAT_LIST_PACKED,
   GROUP,
   GROUP_LIST,
   INT32,
   INT32_LIST,
   INT32_LIST_PACKED,
   INT64,
   INT64_LIST,
   INT64_LIST_PACKED,
   MAP,
   MESSAGE,
   MESSAGE_LIST,
   SFIXED32,
   SFIXED32_LIST,
   SFIXED32_LIST_PACKED,
   SFIXED64,
   SFIXED64_LIST,
   SFIXED64_LIST_PACKED,
   SINT32,
   SINT32_LIST,
   SINT32_LIST_PACKED,
   SINT64,
   SINT64_LIST,
   SINT64_LIST_PACKED,
   STRING,
   STRING_LIST,
   UINT32,
   UINT32_LIST,
   UINT32_LIST_PACKED,
   UINT64,
   UINT64_LIST,
   UINT64_LIST_PACKED;

   private static final FieldType[] $VALUES;
   private static final java.lang.reflect.Type[] EMPTY_TYPES = new java.lang.reflect.Type[0];
   private static final FieldType[] VALUES;
   private final FieldType.Collection collection;
   private final Class<?> elementType;
   private final int id;
   private final JavaType javaType;
   private final boolean primitiveScalar;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      FieldType var9 = new FieldType(0, FieldType.Collection.SCALAR, JavaType.DOUBLE);
      DOUBLE = var9;
      FieldType var40 = new FieldType(1, FieldType.Collection.SCALAR, JavaType.FLOAT);
      FLOAT = var40;
      FieldType var28 = new FieldType(2, FieldType.Collection.SCALAR, JavaType.LONG);
      INT64 = var28;
      FieldType var14 = new FieldType(3, FieldType.Collection.SCALAR, JavaType.LONG);
      UINT64 = var14;
      FieldType var33 = new FieldType(4, FieldType.Collection.SCALAR, JavaType.INT);
      INT32 = var33;
      FieldType var13 = new FieldType(5, FieldType.Collection.SCALAR, JavaType.LONG);
      FIXED64 = var13;
      FieldType var50 = new FieldType(6, FieldType.Collection.SCALAR, JavaType.INT);
      FIXED32 = var50;
      FieldType var35 = new FieldType(7, FieldType.Collection.SCALAR, JavaType.BOOLEAN);
      BOOL = var35;
      FieldType var25 = new FieldType(8, FieldType.Collection.SCALAR, JavaType.STRING);
      STRING = var25;
      FieldType var22 = new FieldType(9, FieldType.Collection.SCALAR, JavaType.MESSAGE);
      MESSAGE = var22;
      FieldType var16 = new FieldType(10, FieldType.Collection.SCALAR, JavaType.BYTE_STRING);
      BYTES = var16;
      FieldType var4 = new FieldType(11, FieldType.Collection.SCALAR, JavaType.INT);
      UINT32 = var4;
      FieldType var36 = new FieldType(12, FieldType.Collection.SCALAR, JavaType.ENUM);
      ENUM = var36;
      FieldType var27 = new FieldType(13, FieldType.Collection.SCALAR, JavaType.INT);
      SFIXED32 = var27;
      FieldType var5 = new FieldType(14, FieldType.Collection.SCALAR, JavaType.LONG);
      SFIXED64 = var5;
      FieldType var19 = new FieldType(15, FieldType.Collection.SCALAR, JavaType.INT);
      SINT32 = var19;
      FieldType var34 = new FieldType(16, FieldType.Collection.SCALAR, JavaType.LONG);
      SINT64 = var34;
      FieldType var12 = new FieldType(17, FieldType.Collection.SCALAR, JavaType.MESSAGE);
      GROUP = var12;
      FieldType var32 = new FieldType(18, FieldType.Collection.VECTOR, JavaType.DOUBLE);
      DOUBLE_LIST = var32;
      FieldType var47 = new FieldType(19, FieldType.Collection.VECTOR, JavaType.FLOAT);
      FLOAT_LIST = var47;
      FieldType var2 = new FieldType(20, FieldType.Collection.VECTOR, JavaType.LONG);
      INT64_LIST = var2;
      FieldType var41 = new FieldType(21, FieldType.Collection.VECTOR, JavaType.LONG);
      UINT64_LIST = var41;
      FieldType var48 = new FieldType(22, FieldType.Collection.VECTOR, JavaType.INT);
      INT32_LIST = var48;
      FieldType var29 = new FieldType(23, FieldType.Collection.VECTOR, JavaType.LONG);
      FIXED64_LIST = var29;
      FieldType var11 = new FieldType(24, FieldType.Collection.VECTOR, JavaType.INT);
      FIXED32_LIST = var11;
      FieldType var42 = new FieldType(25, FieldType.Collection.VECTOR, JavaType.BOOLEAN);
      BOOL_LIST = var42;
      FieldType var8 = new FieldType(26, FieldType.Collection.VECTOR, JavaType.STRING);
      STRING_LIST = var8;
      FieldType var23 = new FieldType(27, FieldType.Collection.VECTOR, JavaType.MESSAGE);
      MESSAGE_LIST = var23;
      FieldType var3 = new FieldType(28, FieldType.Collection.VECTOR, JavaType.BYTE_STRING);
      BYTES_LIST = var3;
      FieldType var18 = new FieldType(29, FieldType.Collection.VECTOR, JavaType.INT);
      UINT32_LIST = var18;
      FieldType var45 = new FieldType(30, FieldType.Collection.VECTOR, JavaType.ENUM);
      ENUM_LIST = var45;
      FieldType var6 = new FieldType(31, FieldType.Collection.VECTOR, JavaType.INT);
      SFIXED32_LIST = var6;
      FieldType var30 = new FieldType(32, FieldType.Collection.VECTOR, JavaType.LONG);
      SFIXED64_LIST = var30;
      FieldType var20 = new FieldType(33, FieldType.Collection.VECTOR, JavaType.INT);
      SINT32_LIST = var20;
      FieldType var51 = new FieldType(34, FieldType.Collection.VECTOR, JavaType.LONG);
      SINT64_LIST = var51;
      FieldType var52 = new FieldType(35, FieldType.Collection.PACKED_VECTOR, JavaType.DOUBLE);
      DOUBLE_LIST_PACKED = var52;
      FieldType var39 = new FieldType(36, FieldType.Collection.PACKED_VECTOR, JavaType.FLOAT);
      FLOAT_LIST_PACKED = var39;
      FieldType var49 = new FieldType(37, FieldType.Collection.PACKED_VECTOR, JavaType.LONG);
      INT64_LIST_PACKED = var49;
      FieldType var37 = new FieldType(38, FieldType.Collection.PACKED_VECTOR, JavaType.LONG);
      UINT64_LIST_PACKED = var37;
      FieldType var26 = new FieldType(39, FieldType.Collection.PACKED_VECTOR, JavaType.INT);
      INT32_LIST_PACKED = var26;
      FieldType var15 = new FieldType(40, FieldType.Collection.PACKED_VECTOR, JavaType.LONG);
      FIXED64_LIST_PACKED = var15;
      FieldType var43 = new FieldType(41, FieldType.Collection.PACKED_VECTOR, JavaType.INT);
      FIXED32_LIST_PACKED = var43;
      FieldType var24 = new FieldType(42, FieldType.Collection.PACKED_VECTOR, JavaType.BOOLEAN);
      BOOL_LIST_PACKED = var24;
      FieldType var7 = new FieldType(43, FieldType.Collection.PACKED_VECTOR, JavaType.INT);
      UINT32_LIST_PACKED = var7;
      FieldType var31 = new FieldType(44, FieldType.Collection.PACKED_VECTOR, JavaType.ENUM);
      ENUM_LIST_PACKED = var31;
      FieldType var46 = new FieldType(45, FieldType.Collection.PACKED_VECTOR, JavaType.INT);
      SFIXED32_LIST_PACKED = var46;
      FieldType var17 = new FieldType(46, FieldType.Collection.PACKED_VECTOR, JavaType.LONG);
      SFIXED64_LIST_PACKED = var17;
      FieldType var44 = new FieldType(47, FieldType.Collection.PACKED_VECTOR, JavaType.INT);
      SINT32_LIST_PACKED = var44;
      FieldType var38 = new FieldType(48, FieldType.Collection.PACKED_VECTOR, JavaType.LONG);
      SINT64_LIST_PACKED = var38;
      FieldType var21 = new FieldType(49, FieldType.Collection.VECTOR, JavaType.MESSAGE);
      GROUP_LIST = var21;
      FieldType var10 = new FieldType(50, FieldType.Collection.MAP, JavaType.VOID);
      MAP = var10;
      int var0 = 0;
      $VALUES = new FieldType[]{
         var9,
         var40,
         var28,
         var14,
         var33,
         var13,
         var50,
         var35,
         var25,
         var22,
         var16,
         var4,
         var36,
         var27,
         var5,
         var19,
         var34,
         var12,
         var32,
         var47,
         var2,
         var41,
         var48,
         var29,
         var11,
         var42,
         var8,
         var23,
         var3,
         var18,
         var45,
         var6,
         var30,
         var20,
         var51,
         var52,
         var39,
         var49,
         var37,
         var26,
         var15,
         var43,
         var24,
         var7,
         var31,
         var46,
         var17,
         var44,
         var38,
         var21,
         var10
      };
      FieldType[] var54 = values();
      VALUES = new FieldType[var54.length];

      for (int var1 = var54.length; var0 < var1; var0++) {
         var2 = var54[var0];
         VALUES[var2.id] = var2;
      }
   }

   private FieldType(int var3, FieldType.Collection var4, JavaType var5) {
      this.id = var3;
      this.collection = var4;
      this.javaType = var5;
      var2 = <unrepresentable>.$SwitchMap$com$google$protobuf$FieldType$Collection[var4.ordinal()];
      boolean var6 = true;
      if (var2 != 1) {
         if (var2 != 2) {
            this.elementType = null;
         } else {
            this.elementType = var5.getBoxedType();
         }
      } else {
         this.elementType = var5.getBoxedType();
      }

      label23: {
         if (var4 == FieldType.Collection.SCALAR) {
            var2 = <unrepresentable>.$SwitchMap$com$google$protobuf$JavaType[var5.ordinal()];
            if (var2 != 1 && var2 != 2 && var2 != 3) {
               break label23;
            }
         }

         var6 = false;
      }

      this.primitiveScalar = var6;
   }

   public static FieldType forId(int var0) {
      if (var0 >= 0) {
         FieldType[] var1 = VALUES;
         if (var0 < var1.length) {
            return var1[var0];
         }
      }

      return null;
   }

   private static java.lang.reflect.Type getGenericSuperList(Class<?> var0) {
      for (java.lang.reflect.Type var4 : var0.getGenericInterfaces()) {
         if (var4 instanceof ParameterizedType && List.class.isAssignableFrom((Class<?>)((ParameterizedType)var4).getRawType())) {
            return var4;
         }
      }

      java.lang.reflect.Type var5 = var0.getGenericSuperclass();
      return var5 instanceof ParameterizedType && List.class.isAssignableFrom((Class<?>)((ParameterizedType)var5).getRawType()) ? var5 : null;
   }

   private static java.lang.reflect.Type getListParameter(Class<?> var0, java.lang.reflect.Type[] var1) {
      label56:
      while (true) {
         int var2 = 0;
         if (var0 == List.class) {
            if (var1.length == 1) {
               return var1[0];
            }

            throw new RuntimeException("Unable to identify parameter type for List<T>");
         }

         java.lang.reflect.Type var4 = getGenericSuperList(var0);
         if (!(var4 instanceof ParameterizedType)) {
            var1 = EMPTY_TYPES;
            Class[] var13 = var0.getInterfaces();

            for (int var10 = var13.length; var2 < var10; var2++) {
               Class var12 = var13[var2];
               if (List.class.isAssignableFrom(var12)) {
                  var0 = var12;
                  continue label56;
               }
            }

            var0 = var0.getSuperclass();
         } else {
            ParameterizedType var7 = (ParameterizedType)var4;
            java.lang.reflect.Type[] var11 = var7.getActualTypeArguments();

            label54:
            for (int var9 = 0; var9 < var11.length; var9++) {
               java.lang.reflect.Type var5 = var11[var9];
               if (var5 instanceof TypeVariable) {
                  TypeVariable[] var6 = var0.getTypeParameters();
                  if (var1.length != var6.length) {
                     throw new RuntimeException("Type array mismatch");
                  }

                  for (int var3 = 0; var3 < var6.length; var3++) {
                     if (var5 == var6[var3]) {
                        var11[var9] = var1[var3];
                        continue label54;
                     }
                  }

                  StringBuilder var8 = new StringBuilder("Unable to find replacement for ");
                  var8.append(var5);
                  throw new RuntimeException(var8.toString());
               }
            }

            var0 = (Class)var7.getRawType();
            var1 = var11;
         }
      }
   }

   private boolean isValidForList(java.lang.reflect.Field var1) {
      Class var3 = var1.getType();
      if (!this.javaType.getType().isAssignableFrom(var3)) {
         return false;
      } else {
         java.lang.reflect.Type[] var2 = EMPTY_TYPES;
         if (var1.getGenericType() instanceof ParameterizedType) {
            var2 = ((ParameterizedType)var1.getGenericType()).getActualTypeArguments();
         }

         java.lang.reflect.Type var4 = getListParameter(var3, var2);
         return !(var4 instanceof Class) ? true : this.elementType.isAssignableFrom((Class<?>)var4);
      }
   }

   public JavaType getJavaType() {
      return this.javaType;
   }

   public int id() {
      return this.id;
   }

   public boolean isList() {
      return this.collection.isList();
   }

   public boolean isMap() {
      boolean var1;
      if (this.collection == FieldType.Collection.MAP) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isPacked() {
      return FieldType.Collection.PACKED_VECTOR.equals(this.collection);
   }

   public boolean isPrimitiveScalar() {
      return this.primitiveScalar;
   }

   public boolean isScalar() {
      boolean var1;
      if (this.collection == FieldType.Collection.SCALAR) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isValidForField(java.lang.reflect.Field var1) {
      return FieldType.Collection.VECTOR.equals(this.collection) ? this.isValidForList(var1) : this.javaType.getType().isAssignableFrom(var1.getType());
   }

   static enum Collection {
      MAP,
      PACKED_VECTOR,
      SCALAR,
      VECTOR;

      private static final FieldType.Collection[] $VALUES;
      private final boolean isList;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         FieldType.Collection var2 = new FieldType.Collection(false);
         SCALAR = var2;
         FieldType.Collection var1 = new FieldType.Collection(true);
         VECTOR = var1;
         FieldType.Collection var0 = new FieldType.Collection(true);
         PACKED_VECTOR = var0;
         FieldType.Collection var3 = new FieldType.Collection(false);
         MAP = var3;
         $VALUES = new FieldType.Collection[]{var2, var1, var0, var3};
      }

      private Collection(boolean var3) {
         this.isList = var3;
      }

      public boolean isList() {
         return this.isList;
      }
   }
}
