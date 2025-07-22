package com.google.protobuf;

import java.io.IOException;

public final class WireFormat {
   static final int FIXED32_SIZE = 4;
   static final int FIXED64_SIZE = 8;
   static final int MAX_VARINT32_SIZE = 5;
   static final int MAX_VARINT64_SIZE = 10;
   static final int MAX_VARINT_SIZE = 10;
   static final int MESSAGE_SET_ITEM = 1;
   static final int MESSAGE_SET_ITEM_END_TAG = makeTag(1, 4);
   static final int MESSAGE_SET_ITEM_TAG = makeTag(1, 3);
   static final int MESSAGE_SET_MESSAGE = 3;
   static final int MESSAGE_SET_MESSAGE_TAG = makeTag(3, 2);
   static final int MESSAGE_SET_TYPE_ID = 2;
   static final int MESSAGE_SET_TYPE_ID_TAG = makeTag(2, 0);
   static final int TAG_TYPE_BITS = 3;
   static final int TAG_TYPE_MASK = 7;
   public static final int WIRETYPE_END_GROUP = 4;
   public static final int WIRETYPE_FIXED32 = 5;
   public static final int WIRETYPE_FIXED64 = 1;
   public static final int WIRETYPE_LENGTH_DELIMITED = 2;
   public static final int WIRETYPE_START_GROUP = 3;
   public static final int WIRETYPE_VARINT = 0;

   private WireFormat() {
   }

   public static int getTagFieldNumber(int var0) {
      return var0 >>> 3;
   }

   public static int getTagWireType(int var0) {
      return var0 & 7;
   }

   static int makeTag(int var0, int var1) {
      return var0 << 3 | var1;
   }

   static Object readPrimitiveField(CodedInputStream var0, WireFormat.FieldType var1, WireFormat.Utf8Validation var2) throws IOException {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var1.ordinal()]) {
         case 1:
            return var0.readDouble();
         case 2:
            return var0.readFloat();
         case 3:
            return var0.readInt64();
         case 4:
            return var0.readUInt64();
         case 5:
            return var0.readInt32();
         case 6:
            return var0.readFixed64();
         case 7:
            return var0.readFixed32();
         case 8:
            return var0.readBool();
         case 9:
            return var0.readBytes();
         case 10:
            return var0.readUInt32();
         case 11:
            return var0.readSFixed32();
         case 12:
            return var0.readSFixed64();
         case 13:
            return var0.readSInt32();
         case 14:
            return var0.readSInt64();
         case 15:
            return var2.readString(var0);
         case 16:
            throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
         case 17:
            throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
         case 18:
            throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
         default:
            throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
      }
   }

   public static enum FieldType {
      BOOL,
      BYTES,
      DOUBLE,
      ENUM,
      FIXED32,
      FIXED64,
      FLOAT,
      GROUP,
      INT32,
      INT64,
      MESSAGE,
      SFIXED32,
      SFIXED64,
      SINT32,
      SINT64,
      STRING,
      UINT32,
      UINT64;

      private static final WireFormat.FieldType[] $VALUES;
      private final WireFormat.JavaType javaType;
      private final int wireType;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         WireFormat.FieldType var14 = new WireFormat.FieldType(WireFormat.JavaType.DOUBLE, 1);
         DOUBLE = var14;
         WireFormat.FieldType var8 = new WireFormat.FieldType(WireFormat.JavaType.FLOAT, 5);
         FLOAT = var8;
         WireFormat.FieldType var12 = new WireFormat.FieldType(WireFormat.JavaType.LONG, 0);
         INT64 = var12;
         WireFormat.FieldType var4 = new WireFormat.FieldType(WireFormat.JavaType.LONG, 0);
         UINT64 = var4;
         WireFormat.FieldType var13 = new WireFormat.FieldType(WireFormat.JavaType.INT, 0);
         INT32 = var13;
         WireFormat.FieldType var15 = new WireFormat.FieldType(WireFormat.JavaType.LONG, 1);
         FIXED64 = var15;
         WireFormat.FieldType var17 = new WireFormat.FieldType(WireFormat.JavaType.INT, 5);
         FIXED32 = var17;
         WireFormat.FieldType var2 = new WireFormat.FieldType(WireFormat.JavaType.BOOLEAN, 0);
         BOOL = var2;
         WireFormat.FieldType var11 = new WireFormat.FieldType(WireFormat.JavaType.STRING, 2) {
            @Override
            public boolean isPackable() {
               return false;
            }
         };
         STRING = var11;
         WireFormat.FieldType var10 = new WireFormat.FieldType(WireFormat.JavaType.MESSAGE, 3) {
            @Override
            public boolean isPackable() {
               return false;
            }
         };
         GROUP = var10;
         WireFormat.FieldType var9 = new WireFormat.FieldType(WireFormat.JavaType.MESSAGE, 2) {
            @Override
            public boolean isPackable() {
               return false;
            }
         };
         MESSAGE = var9;
         WireFormat.FieldType var1 = new WireFormat.FieldType(WireFormat.JavaType.BYTE_STRING, 2) {
            @Override
            public boolean isPackable() {
               return false;
            }
         };
         BYTES = var1;
         WireFormat.FieldType var3 = new WireFormat.FieldType(WireFormat.JavaType.INT, 0);
         UINT32 = var3;
         WireFormat.FieldType var6 = new WireFormat.FieldType(WireFormat.JavaType.ENUM, 0);
         ENUM = var6;
         WireFormat.FieldType var16 = new WireFormat.FieldType(WireFormat.JavaType.INT, 5);
         SFIXED32 = var16;
         WireFormat.FieldType var5 = new WireFormat.FieldType(WireFormat.JavaType.LONG, 1);
         SFIXED64 = var5;
         WireFormat.FieldType var0 = new WireFormat.FieldType(WireFormat.JavaType.INT, 0);
         SINT32 = var0;
         WireFormat.FieldType var7 = new WireFormat.FieldType(WireFormat.JavaType.LONG, 0);
         SINT64 = var7;
         $VALUES = new WireFormat.FieldType[]{
            var14, var8, var12, var4, var13, var15, var17, var2, var11, var10, var9, var1, var3, var6, var16, var5, var0, var7
         };
      }

      private FieldType(WireFormat.JavaType var3, int var4) {
         this.javaType = var3;
         this.wireType = var4;
      }

      public WireFormat.JavaType getJavaType() {
         return this.javaType;
      }

      public int getWireType() {
         return this.wireType;
      }

      public boolean isPackable() {
         return true;
      }
   }

   public static enum JavaType {
      BOOLEAN,
      BYTE_STRING,
      DOUBLE,
      ENUM,
      FLOAT,
      INT,
      LONG,
      MESSAGE,
      STRING;

      private static final WireFormat.JavaType[] $VALUES;
      private final Object defaultDefault;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         WireFormat.JavaType var0 = new WireFormat.JavaType(0);
         INT = var0;
         WireFormat.JavaType var4 = new WireFormat.JavaType(0L);
         LONG = var4;
         WireFormat.JavaType var8 = new WireFormat.JavaType(0.0F);
         FLOAT = var8;
         WireFormat.JavaType var1 = new WireFormat.JavaType(0.0);
         DOUBLE = var1;
         WireFormat.JavaType var6 = new WireFormat.JavaType(false);
         BOOLEAN = var6;
         WireFormat.JavaType var5 = new WireFormat.JavaType("");
         STRING = var5;
         WireFormat.JavaType var3 = new WireFormat.JavaType(ByteString.EMPTY);
         BYTE_STRING = var3;
         WireFormat.JavaType var7 = new WireFormat.JavaType(null);
         ENUM = var7;
         WireFormat.JavaType var2 = new WireFormat.JavaType(null);
         MESSAGE = var2;
         $VALUES = new WireFormat.JavaType[]{var0, var4, var8, var1, var6, var5, var3, var7, var2};
      }

      private JavaType(Object var3) {
         this.defaultDefault = var3;
      }

      Object getDefaultDefault() {
         return this.defaultDefault;
      }
   }

   static enum Utf8Validation {
      LAZY,
      LOOSE,
      STRICT;
      private static final WireFormat.Utf8Validation[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         WireFormat.Utf8Validation var0 = new WireFormat.Utf8Validation() {
            @Override
            Object readString(CodedInputStream var1) throws IOException {
               return var1.readString();
            }
         };
         LOOSE = var0;
         WireFormat.Utf8Validation var1 = new WireFormat.Utf8Validation() {
            @Override
            Object readString(CodedInputStream var1) throws IOException {
               return var1.readStringRequireUtf8();
            }
         };
         STRICT = var1;
         WireFormat.Utf8Validation var2 = new WireFormat.Utf8Validation() {
            @Override
            Object readString(CodedInputStream var1) throws IOException {
               return var1.readBytes();
            }
         };
         LAZY = var2;
         $VALUES = new WireFormat.Utf8Validation[]{var0, var1, var2};
      }

      private Utf8Validation() {
      }

      abstract Object readString(CodedInputStream var1) throws IOException;
   }
}
