package com.google.protobuf;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

@CheckReturnValue
final class MessageSchema<T> implements Schema<T> {
   private static final int CHECK_INITIALIZED_BIT = 1024;
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   private static final int ENFORCE_UTF8_MASK = 536870912;
   private static final int FIELD_TYPE_MASK = 267386880;
   private static final int HAS_HAS_BIT = 4096;
   private static final int INTS_PER_FIELD = 3;
   private static final int LEGACY_ENUM_IS_CLOSED_BIT = 2048;
   private static final int LEGACY_ENUM_IS_CLOSED_MASK = Integer.MIN_VALUE;
   private static final int NO_PRESENCE_SENTINEL = 1048575;
   private static final int OFFSET_BITS = 20;
   private static final int OFFSET_MASK = 1048575;
   static final int ONEOF_TYPE_OFFSET = 51;
   private static final int REQUIRED_BIT = 256;
   private static final int REQUIRED_MASK = 268435456;
   private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();
   private static final int UTF8_CHECK_BIT = 512;
   private final int[] buffer;
   private final int checkInitializedCount;
   private final MessageLite defaultInstance;
   private final ExtensionSchema<?> extensionSchema;
   private final boolean hasExtensions;
   private final int[] intArray;
   private final ListFieldSchema listFieldSchema;
   private final boolean lite;
   private final MapFieldSchema mapFieldSchema;
   private final int maxFieldNumber;
   private final int minFieldNumber;
   private final NewInstanceSchema newInstanceSchema;
   private final Object[] objects;
   private final int repeatedFieldOffsetStart;
   private final ProtoSyntax syntax;
   private final UnknownFieldSchema<?, ?> unknownFieldSchema;
   private final boolean useCachedSizeField;

   private MessageSchema(
      int[] var1,
      Object[] var2,
      int var3,
      int var4,
      MessageLite var5,
      ProtoSyntax var6,
      boolean var7,
      int[] var8,
      int var9,
      int var10,
      NewInstanceSchema var11,
      ListFieldSchema var12,
      UnknownFieldSchema<?, ?> var13,
      ExtensionSchema<?> var14,
      MapFieldSchema var15
   ) {
      this.buffer = var1;
      this.objects = var2;
      this.minFieldNumber = var3;
      this.maxFieldNumber = var4;
      this.lite = var5 instanceof GeneratedMessageLite;
      this.syntax = var6;
      boolean var16;
      if (var14 != null && var14.hasExtensions(var5)) {
         var16 = true;
      } else {
         var16 = false;
      }

      this.hasExtensions = var16;
      this.useCachedSizeField = var7;
      this.intArray = var8;
      this.checkInitializedCount = var9;
      this.repeatedFieldOffsetStart = var10;
      this.newInstanceSchema = var11;
      this.listFieldSchema = var12;
      this.unknownFieldSchema = var13;
      this.extensionSchema = var14;
      this.defaultInstance = var5;
      this.mapFieldSchema = var15;
   }

   private boolean arePresentForEquals(T var1, T var2, int var3) {
      boolean var4;
      if (this.isFieldPresent((T)var1, var3) == this.isFieldPresent((T)var2, var3)) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private static <T> boolean booleanAt(T var0, long var1) {
      return UnsafeUtil.getBoolean(var0, var1);
   }

   private static void checkMutable(Object var0) {
      if (!isMutable(var0)) {
         StringBuilder var1 = new StringBuilder("Mutating immutable message: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   private <K, V> int decodeMapEntry(byte[] var1, int var2, int var3, MapEntryLite.Metadata<K, V> var4, Map<K, V> var5, ArrayDecoders.Registers var6) throws IOException {
      var2 = ArrayDecoders.decodeVarint32(var1, var2, var6);
      int var7 = var6.int1;
      if (var7 >= 0 && var7 <= var3 - var2) {
         int var9 = var2 + var7;
         Object var11 = var4.defaultKey;
         Object var12 = var4.defaultValue;

         while (var2 < var9) {
            int var8 = var2 + 1;
            var7 = var1[var2];
            if (var7 < 0) {
               var2 = ArrayDecoders.decodeVarint32(var7, var1, var8, var6);
               var7 = var6.int1;
            } else {
               var2 = var8;
            }

            var8 = var7 >>> 3;
            int var10 = var7 & 7;
            if (var8 != 1) {
               if (var8 == 2 && var10 == var4.valueType.getWireType()) {
                  var2 = this.decodeMapEntryValue(var1, var2, var3, var4.valueType, var4.defaultValue.getClass(), var6);
                  var12 = var6.object1;
                  continue;
               }
            } else if (var10 == var4.keyType.getWireType()) {
               var2 = this.decodeMapEntryValue(var1, var2, var3, var4.keyType, null, var6);
               var11 = var6.object1;
               continue;
            }

            var2 = ArrayDecoders.skipField(var7, var1, var2, var3, var6);
         }

         if (var2 == var9) {
            var5.put(var11, var12);
            return var9;
         } else {
            throw InvalidProtocolBufferException.parseFailure();
         }
      } else {
         throw InvalidProtocolBufferException.truncatedMessage();
      }
   }

   private int decodeMapEntryValue(byte[] var1, int var2, int var3, WireFormat.FieldType var4, Class<?> var5, ArrayDecoders.Registers var6) throws IOException {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var4.ordinal()]) {
         case 1:
            var2 = ArrayDecoders.decodeVarint64(var1, var2, var6);
            boolean var7;
            if (var6.long1 != 0L) {
               var7 = true;
            } else {
               var7 = false;
            }

            var6.object1 = var7;
            return var2;
         case 2:
            return ArrayDecoders.decodeBytes(var1, var2, var6);
         case 3:
            var6.object1 = ArrayDecoders.decodeDouble(var1, var2);
            break;
         case 4:
         case 5:
            var6.object1 = ArrayDecoders.decodeFixed32(var1, var2);
            return var2 + 4;
         case 6:
         case 7:
            var6.object1 = ArrayDecoders.decodeFixed64(var1, var2);
            break;
         case 8:
            var6.object1 = ArrayDecoders.decodeFloat(var1, var2);
            return var2 + 4;
         case 9:
         case 10:
         case 11:
            var2 = ArrayDecoders.decodeVarint32(var1, var2, var6);
            var6.object1 = var6.int1;
            return var2;
         case 12:
         case 13:
            var2 = ArrayDecoders.decodeVarint64(var1, var2, var6);
            var6.object1 = var6.long1;
            return var2;
         case 14:
            return ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(var5), var1, var2, var3, var6);
         case 15:
            var2 = ArrayDecoders.decodeVarint32(var1, var2, var6);
            var6.object1 = CodedInputStream.decodeZigZag32(var6.int1);
            return var2;
         case 16:
            var2 = ArrayDecoders.decodeVarint64(var1, var2, var6);
            var6.object1 = CodedInputStream.decodeZigZag64(var6.long1);
            return var2;
         case 17:
            return ArrayDecoders.decodeStringRequireUtf8(var1, var2, var6);
         default:
            throw new RuntimeException("unsupported field type.");
      }

      return var2 + 8;
   }

   private static <T> double doubleAt(T var0, long var1) {
      return UnsafeUtil.getDouble(var0, var1);
   }

   private boolean equals(T var1, T var2, int var3) {
      int var4 = this.typeAndOffsetAt(var3);
      long var25 = offset(var4);
      var4 = type(var4);
      boolean var13 = false;
      boolean var12 = false;
      boolean var18 = false;
      boolean var7 = false;
      boolean var23 = false;
      boolean var6 = false;
      boolean var21 = false;
      boolean var14 = false;
      boolean var22 = false;
      boolean var9 = false;
      boolean var20 = false;
      boolean var24 = false;
      boolean var16 = false;
      boolean var8 = false;
      boolean var19 = false;
      boolean var11 = false;
      boolean var17 = false;
      boolean var10 = false;
      boolean var15 = false;
      switch (var4) {
         case 0:
            boolean var45 = var10;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var45 = var10;
               if (Double.doubleToLongBits(UnsafeUtil.getDouble(var1, var25)) == Double.doubleToLongBits(UnsafeUtil.getDouble(var2, var25))) {
                  var45 = true;
               }
            }

            return var45;
         case 1:
            boolean var44 = var17;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var44 = var17;
               if (Float.floatToIntBits(UnsafeUtil.getFloat(var1, var25)) == Float.floatToIntBits(UnsafeUtil.getFloat(var2, var25))) {
                  var44 = true;
               }
            }

            return var44;
         case 2:
            boolean var43 = var11;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var43 = var11;
               if (UnsafeUtil.getLong(var1, var25) == UnsafeUtil.getLong(var2, var25)) {
                  var43 = true;
               }
            }

            return var43;
         case 3:
            boolean var42 = var19;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var42 = var19;
               if (UnsafeUtil.getLong(var1, var25) == UnsafeUtil.getLong(var2, var25)) {
                  var42 = true;
               }
            }

            return var42;
         case 4:
            boolean var41 = var8;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var41 = var8;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var41 = true;
               }
            }

            return var41;
         case 5:
            boolean var40 = var16;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var40 = var16;
               if (UnsafeUtil.getLong(var1, var25) == UnsafeUtil.getLong(var2, var25)) {
                  var40 = true;
               }
            }

            return var40;
         case 6:
            boolean var39 = var24;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var39 = var24;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var39 = true;
               }
            }

            return var39;
         case 7:
            boolean var38 = var20;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var38 = var20;
               if (UnsafeUtil.getBoolean(var1, var25) == UnsafeUtil.getBoolean(var2, var25)) {
                  var38 = true;
               }
            }

            return var38;
         case 8:
            boolean var37 = var9;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var37 = var9;
               if (SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25))) {
                  var37 = true;
               }
            }

            return var37;
         case 9:
            boolean var36 = var22;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var36 = var22;
               if (SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25))) {
                  var36 = true;
               }
            }

            return var36;
         case 10:
            boolean var35 = var14;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var35 = var14;
               if (SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25))) {
                  var35 = true;
               }
            }

            return var35;
         case 11:
            boolean var34 = var21;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var34 = var21;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var34 = true;
               }
            }

            return var34;
         case 12:
            boolean var33 = var6;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var33 = var6;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var33 = true;
               }
            }

            return var33;
         case 13:
            boolean var32 = var23;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var32 = var23;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var32 = true;
               }
            }

            return var32;
         case 14:
            boolean var31 = var7;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var31 = var7;
               if (UnsafeUtil.getLong(var1, var25) == UnsafeUtil.getLong(var2, var25)) {
                  var31 = true;
               }
            }

            return var31;
         case 15:
            boolean var30 = var18;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var30 = var18;
               if (UnsafeUtil.getInt(var1, var25) == UnsafeUtil.getInt(var2, var25)) {
                  var30 = true;
               }
            }

            return var30;
         case 16:
            boolean var29 = var12;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var29 = var12;
               if (UnsafeUtil.getLong(var1, var25) == UnsafeUtil.getLong(var2, var25)) {
                  var29 = true;
               }
            }

            return var29;
         case 17:
            boolean var28 = var13;
            if (this.arePresentForEquals((T)var1, (T)var2, var3)) {
               var28 = var13;
               if (SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25))) {
                  var28 = true;
               }
            }

            return var28;
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
            return SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25));
         case 50:
            return SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25));
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
            boolean var5 = var15;
            if (this.isOneofCaseEqual((T)var1, (T)var2, var3)) {
               var5 = var15;
               if (SchemaUtil.safeEquals(UnsafeUtil.getObject(var1, var25), UnsafeUtil.getObject(var2, var25))) {
                  var5 = true;
               }
            }

            return var5;
         default:
            return true;
      }
   }

   private <UT, UB> UB filterMapUnknownEnumValues(Object var1, int var2, UB var3, UnknownFieldSchema<UT, UB> var4, Object var5) {
      int var6 = this.numberAt(var2);
      Object var7 = UnsafeUtil.getObject(var1, offset(this.typeAndOffsetAt(var2)));
      if (var7 == null) {
         return (UB)var3;
      } else {
         var1 = this.getEnumFieldVerifier(var2);
         return (UB)(var1 == null ? var3 : this.filterUnknownEnumMap(var2, var6, this.mapFieldSchema.forMutableMapData(var7), var1, (UB)var3, var4, var5));
      }
   }

   private <K, V, UT, UB> UB filterUnknownEnumMap(
      int var1, int var2, Map<K, V> var3, Internal.EnumVerifier var4, UB var5, UnknownFieldSchema<UT, UB> var6, Object var7
   ) {
      MapEntryLite.Metadata var8 = this.mapFieldSchema.forMapMetadata(this.getMapFieldDefaultEntry(var1));
      Iterator var9 = var3.entrySet().iterator();

      while (var9.hasNext()) {
         Entry var10 = (Entry)var9.next();
         if (!var4.isInRange((Integer)var10.getValue())) {
            Object var13 = var5;
            if (var5 == null) {
               var13 = var6.getBuilderFromMessage(var7);
            }

            ByteString.CodedBuilder var11 = ByteString.newCodedBuilder(MapEntryLite.computeSerializedSize(var8, var10.getKey(), var10.getValue()));
            var5 = var11.getCodedOutput();

            try {
               MapEntryLite.writeTo(var5, var8, var10.getKey(), var10.getValue());
            } catch (IOException var12) {
               throw new RuntimeException(var12);
            }

            var6.addLengthDelimited(var13, var2, var11.build());
            var9.remove();
            var5 = (CodedOutputStream)var13;
         }
      }

      return (UB)var5;
   }

   private static <T> float floatAt(T var0, long var1) {
      return UnsafeUtil.getFloat(var0, var1);
   }

   private Internal.EnumVerifier getEnumFieldVerifier(int var1) {
      return (Internal.EnumVerifier)this.objects[var1 / 3 * 2 + 1];
   }

   private Object getMapFieldDefaultEntry(int var1) {
      return this.objects[var1 / 3 * 2];
   }

   private Schema getMessageFieldSchema(int var1) {
      var1 = var1 / 3 * 2;
      Schema var2 = (Schema)this.objects[var1];
      if (var2 != null) {
         return var2;
      } else {
         var2 = Protobuf.getInstance().schemaFor((Class<T>)this.objects[var1 + 1]);
         this.objects[var1] = var2;
         return var2;
      }
   }

   static UnknownFieldSetLite getMutableUnknownFields(Object var0) {
      GeneratedMessageLite var2 = (GeneratedMessageLite)var0;
      UnknownFieldSetLite var1 = var2.unknownFields;
      var0 = var1;
      if (var1 == UnknownFieldSetLite.getDefaultInstance()) {
         var0 = UnknownFieldSetLite.newInstance();
         var2.unknownFields = var0;
      }

      return var0;
   }

   private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> var1, T var2) {
      return var1.getSerializedSize(var1.getFromMessage(var2));
   }

   private static <T> int intAt(T var0, long var1) {
      return UnsafeUtil.getInt(var0, var1);
   }

   private static boolean isEnforceUtf8(int var0) {
      boolean var1;
      if ((var0 & 536870912) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isFieldPresent(T var1, int var2) {
      int var3 = this.presenceMaskAndOffsetAt(var2);
      long var20 = 1048575 & var3;
      boolean var8 = false;
      boolean var19 = false;
      boolean var10 = false;
      boolean var18 = false;
      boolean var17 = false;
      boolean var9 = false;
      boolean var14 = false;
      boolean var13 = false;
      boolean var11 = false;
      boolean var12 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var7 = false;
      boolean var15 = false;
      boolean var6 = false;
      boolean var16 = false;
      if (var20 == 1048575L) {
         var2 = this.typeAndOffsetAt(var2);
         var20 = offset(var2);
         switch (type(var2)) {
            case 0:
               var4 = var15;
               if (Double.doubleToRawLongBits(UnsafeUtil.getDouble(var1, var20)) != 0L) {
                  var4 = true;
               }

               return var4;
            case 1:
               var4 = var7;
               if (Float.floatToRawIntBits(UnsafeUtil.getFloat(var1, var20)) != 0) {
                  var4 = true;
               }

               return var4;
            case 2:
               var4 = var5;
               if (UnsafeUtil.getLong(var1, var20) != 0L) {
                  var4 = true;
               }

               return var4;
            case 3:
               if (UnsafeUtil.getLong(var1, var20) != 0L) {
                  var4 = true;
               }

               return var4;
            case 4:
               var4 = var12;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 5:
               var4 = var11;
               if (UnsafeUtil.getLong(var1, var20) != 0L) {
                  var4 = true;
               }

               return var4;
            case 6:
               var4 = var13;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 7:
               return UnsafeUtil.getBoolean(var1, var20);
            case 8:
               var1 = UnsafeUtil.getObject(var1, var20);
               if (var1 instanceof String) {
                  return ((String)var1).isEmpty() ^ true;
               } else {
                  if (var1 instanceof ByteString) {
                     return ByteString.EMPTY.equals(var1) ^ true;
                  }

                  throw new IllegalArgumentException();
               }
            case 9:
               var4 = var14;
               if (UnsafeUtil.getObject(var1, var20) != null) {
                  var4 = true;
               }

               return var4;
            case 10:
               return ByteString.EMPTY.equals(UnsafeUtil.getObject(var1, var20)) ^ true;
            case 11:
               var4 = var9;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 12:
               var4 = var17;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 13:
               var4 = var18;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 14:
               var4 = var10;
               if (UnsafeUtil.getLong(var1, var20) != 0L) {
                  var4 = true;
               }

               return var4;
            case 15:
               var4 = var19;
               if (UnsafeUtil.getInt(var1, var20) != 0) {
                  var4 = true;
               }

               return var4;
            case 16:
               var4 = var8;
               if (UnsafeUtil.getLong(var1, var20) != 0L) {
                  var4 = true;
               }

               return var4;
            case 17:
               var4 = var16;
               if (UnsafeUtil.getObject(var1, var20) != null) {
                  var4 = true;
               }

               return var4;
            default:
               throw new IllegalArgumentException();
         }
      } else {
         var4 = var6;
         if ((UnsafeUtil.getInt(var1, var20) & 1 << (var3 >>> 20)) != 0) {
            var4 = true;
         }

         return var4;
      }
   }

   private boolean isFieldPresent(T var1, int var2, int var3, int var4, int var5) {
      if (var3 == 1048575) {
         return this.isFieldPresent((T)var1, var2);
      } else {
         boolean var6;
         if ((var4 & var5) != 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         return var6;
      }
   }

   private static boolean isInitialized(Object var0, int var1, Schema var2) {
      return var2.isInitialized(UnsafeUtil.getObject(var0, offset(var1)));
   }

   private static boolean isLegacyEnumIsClosed(int var0) {
      boolean var1;
      if ((var0 & -2147483648) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private <N> boolean isListInitialized(Object var1, int var2, int var3) {
      List var4 = (List)UnsafeUtil.getObject(var1, offset(var2));
      if (var4.isEmpty()) {
         return true;
      } else {
         var1 = this.getMessageFieldSchema(var3);

         for (int var6 = 0; var6 < var4.size(); var6++) {
            if (!var1.isInitialized(var4.get(var6))) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isMapInitialized(T var1, int var2, int var3) {
      Map var4 = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(var1, offset(var2)));
      if (var4.isEmpty()) {
         return true;
      } else {
         var1 = this.getMapFieldDefaultEntry(var3);
         if (this.mapFieldSchema.forMapMetadata(var1).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
         } else {
            Iterator var5 = var4.values().iterator();
            var1 = null;

            while (var5.hasNext()) {
               Object var6 = var5.next();
               Schema var9 = var1;
               if (var1 == null) {
                  var9 = Protobuf.getInstance().schemaFor(var6.getClass());
               }

               var1 = var9;
               if (!var9.isInitialized(var6)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private static boolean isMutable(Object var0) {
      if (var0 == null) {
         return false;
      } else {
         return var0 instanceof GeneratedMessageLite ? ((GeneratedMessageLite)var0).isMutable() : true;
      }
   }

   private boolean isOneofCaseEqual(T var1, T var2, int var3) {
      long var4 = this.presenceMaskAndOffsetAt(var3) & 1048575;
      boolean var6;
      if (UnsafeUtil.getInt(var1, var4) == UnsafeUtil.getInt(var2, var4)) {
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   private boolean isOneofPresent(T var1, int var2, int var3) {
      boolean var4;
      if (UnsafeUtil.getInt(var1, this.presenceMaskAndOffsetAt(var3) & 1048575) == var2) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   private static boolean isRequired(int var0) {
      boolean var1;
      if ((var0 & 268435456) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private static <T> long longAt(T var0, long var1) {
      return UnsafeUtil.getLong(var0, var1);
   }

   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(
      UnknownFieldSchema<UT, UB> param1, ExtensionSchema<ET> param2, T param3, Reader param4, ExtensionRegistryLite param5
   ) throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.NullPointerException: Cannot invoke "org.jetbrains.java.decompiler.code.cfg.ExceptionRangeCFG.isCircular()" because "range" is null
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.graphToStatement(DomHelper.java:84)
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:203)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 1
      // 001: astore 19
      // 003: aload 3
      // 004: astore 14
      // 006: aload 5
      // 008: astore 20
      // 00a: aconst_null
      // 00b: astore 13
      // 00d: aconst_null
      // 00e: astore 17
      // 010: aload 4
      // 012: invokeinterface com/google/protobuf/Reader.getFieldNumber ()I 1
      // 017: istore 7
      // 019: aload 0
      // 01a: iload 7
      // 01c: invokespecial com/google/protobuf/MessageSchema.positionForFieldNumber (I)I
      // 01f: istore 8
      // 021: iload 8
      // 023: ifge 15d
      // 026: iload 7
      // 028: ldc_w 2147483647
      // 02b: if_icmpne 064
      // 02e: aload 0
      // 02f: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // 032: istore 6
      // 034: iload 6
      // 036: aload 0
      // 037: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // 03a: if_icmpge 055
      // 03d: aload 0
      // 03e: aload 3
      // 03f: aload 0
      // 040: getfield com/google/protobuf/MessageSchema.intArray [I
      // 043: iload 6
      // 045: iaload
      // 046: aload 13
      // 048: aload 1
      // 049: aload 3
      // 04a: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // 04d: astore 13
      // 04f: iinc 6 1
      // 052: goto 034
      // 055: aload 13
      // 057: ifnull 063
      // 05a: aload 19
      // 05c: aload 14
      // 05e: aload 13
      // 060: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // 063: return
      // 064: aload 0
      // 065: getfield com/google/protobuf/MessageSchema.hasExtensions Z
      // 068: ifne 071
      // 06b: aconst_null
      // 06c: astore 18
      // 06e: goto 07f
      // 071: aload 2
      // 072: aload 20
      // 074: aload 0
      // 075: getfield com/google/protobuf/MessageSchema.defaultInstance Lcom/google/protobuf/MessageLite;
      // 078: iload 7
      // 07a: invokevirtual com/google/protobuf/ExtensionSchema.findExtensionByNumber (Lcom/google/protobuf/ExtensionRegistryLite;Lcom/google/protobuf/MessageLite;I)Ljava/lang/Object;
      // 07d: astore 18
      // 07f: aload 18
      // 081: ifnull 0b8
      // 084: aload 17
      // 086: astore 15
      // 088: aload 17
      // 08a: ifnonnull 09b
      // 08d: aload 2
      // 08e: aload 3
      // 08f: invokevirtual com/google/protobuf/ExtensionSchema.getMutableExtensions (Ljava/lang/Object;)Lcom/google/protobuf/FieldSet;
      // 092: astore 15
      // 094: goto 09b
      // 097: astore 2
      // 098: goto c06
      // 09b: aload 13
      // 09d: astore 16
      // 09f: aload 2
      // 0a0: aload 3
      // 0a1: aload 4
      // 0a3: aload 18
      // 0a5: aload 5
      // 0a7: aload 15
      // 0a9: aload 13
      // 0ab: aload 1
      // 0ac: invokevirtual com/google/protobuf/ExtensionSchema.parseExtension (Ljava/lang/Object;Lcom/google/protobuf/Reader;Ljava/lang/Object;Lcom/google/protobuf/ExtensionRegistryLite;Lcom/google/protobuf/FieldSet;Ljava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;)Ljava/lang/Object;
      // 0af: astore 13
      // 0b1: aload 15
      // 0b3: astore 17
      // 0b5: goto 010
      // 0b8: aload 14
      // 0ba: astore 21
      // 0bc: aload 13
      // 0be: astore 16
      // 0c0: aload 19
      // 0c2: aload 4
      // 0c4: invokevirtual com/google/protobuf/UnknownFieldSchema.shouldDiscardUnknownFields (Lcom/google/protobuf/Reader;)Z
      // 0c7: ifeq 0df
      // 0ca: aload 13
      // 0cc: astore 18
      // 0ce: aload 13
      // 0d0: astore 16
      // 0d2: aload 4
      // 0d4: invokeinterface com/google/protobuf/Reader.skipField ()Z 1
      // 0d9: ifeq 114
      // 0dc: goto 0b5
      // 0df: aload 13
      // 0e1: astore 15
      // 0e3: aload 13
      // 0e5: ifnonnull 0f5
      // 0e8: aload 13
      // 0ea: astore 16
      // 0ec: aload 19
      // 0ee: aload 21
      // 0f0: invokevirtual com/google/protobuf/UnknownFieldSchema.getBuilderFromMessage (Ljava/lang/Object;)Ljava/lang/Object;
      // 0f3: astore 15
      // 0f5: aload 15
      // 0f7: astore 16
      // 0f9: aload 19
      // 0fb: aload 15
      // 0fd: aload 4
      // 0ff: invokevirtual com/google/protobuf/UnknownFieldSchema.mergeOneFieldFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;)Z
      // 102: istore 10
      // 104: aload 15
      // 106: astore 18
      // 108: iload 10
      // 10a: ifeq 114
      // 10d: aload 15
      // 10f: astore 13
      // 111: goto 0dc
      // 114: aload 0
      // 115: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // 118: istore 6
      // 11a: aload 18
      // 11c: astore 2
      // 11d: iload 6
      // 11f: aload 0
      // 120: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // 123: if_icmpge 13c
      // 126: aload 0
      // 127: aload 3
      // 128: aload 0
      // 129: getfield com/google/protobuf/MessageSchema.intArray [I
      // 12c: iload 6
      // 12e: iaload
      // 12f: aload 2
      // 130: aload 1
      // 131: aload 3
      // 132: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // 135: astore 2
      // 136: iinc 6 1
      // 139: goto 11d
      // 13c: aload 2
      // 13d: ifnull 148
      // 140: aload 19
      // 142: aload 21
      // 144: aload 2
      // 145: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // 148: return
      // 149: astore 2
      // 14a: aload 14
      // 14c: astore 4
      // 14e: aload 16
      // 150: astore 13
      // 152: goto bfe
      // 155: astore 2
      // 156: aload 14
      // 158: astore 4
      // 15a: goto bfe
      // 15d: aload 14
      // 15f: astore 18
      // 161: aload 0
      // 162: iload 8
      // 164: invokespecial com/google/protobuf/MessageSchema.typeAndOffsetAt (I)I
      // 167: istore 6
      // 169: iload 6
      // 16b: invokestatic com/google/protobuf/MessageSchema.type (I)I
      // 16e: istore 9
      // 170: iload 9
      // 172: tableswitch 290 0 68 2374 2346 2318 2290 2262 2234 2206 2178 2157 2114 2086 2058 1976 1948 1920 1892 1864 1821 1797 1773 1749 1725 1701 1677 1653 1629 1616 1596 1572 1548 1497 1473 1449 1425 1401 1377 1353 1329 1305 1281 1257 1233 1209 1185 1134 1110 1086 1062 1038 998 974 937 904 871 838 805 772 739 706 683 636 606 573 486 453 420 387 354 307
      // 294: aload 13
      // 296: ifnonnull adf
      // 299: aload 19
      // 29b: aload 18
      // 29d: invokevirtual com/google/protobuf/UnknownFieldSchema.getBuilderFromMessage (Ljava/lang/Object;)Ljava/lang/Object;
      // 2a0: astore 15
      // 2a2: goto ae3
      // 2a5: aload 0
      // 2a6: aload 18
      // 2a8: iload 7
      // 2aa: iload 8
      // 2ac: invokespecial com/google/protobuf/MessageSchema.mutableOneofMessageFieldForMerge (Ljava/lang/Object;II)Ljava/lang/Object;
      // 2af: checkcast com/google/protobuf/MessageLite
      // 2b2: astore 15
      // 2b4: aload 4
      // 2b6: aload 15
      // 2b8: aload 0
      // 2b9: iload 8
      // 2bb: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 2be: aload 20
      // 2c0: invokeinterface com/google/protobuf/Reader.mergeGroupField (Ljava/lang/Object;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
      // 2c5: aload 0
      // 2c6: aload 18
      // 2c8: iload 7
      // 2ca: iload 8
      // 2cc: aload 15
      // 2ce: invokespecial com/google/protobuf/MessageSchema.storeOneofMessageField (Ljava/lang/Object;IILjava/lang/Object;)V
      // 2d1: goto 431
      // 2d4: aload 18
      // 2d6: iload 6
      // 2d8: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 2db: aload 4
      // 2dd: invokeinterface com/google/protobuf/Reader.readSInt64 ()J 1
      // 2e2: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 2e5: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 2e8: aload 0
      // 2e9: aload 18
      // 2eb: iload 7
      // 2ed: iload 8
      // 2ef: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 2f2: goto 431
      // 2f5: aload 18
      // 2f7: iload 6
      // 2f9: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 2fc: aload 4
      // 2fe: invokeinterface com/google/protobuf/Reader.readSInt32 ()I 1
      // 303: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 306: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 309: aload 0
      // 30a: aload 18
      // 30c: iload 7
      // 30e: iload 8
      // 310: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 313: goto 431
      // 316: aload 18
      // 318: iload 6
      // 31a: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 31d: aload 4
      // 31f: invokeinterface com/google/protobuf/Reader.readSFixed64 ()J 1
      // 324: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 327: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 32a: aload 0
      // 32b: aload 18
      // 32d: iload 7
      // 32f: iload 8
      // 331: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 334: goto 431
      // 337: aload 18
      // 339: iload 6
      // 33b: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 33e: aload 4
      // 340: invokeinterface com/google/protobuf/Reader.readSFixed32 ()I 1
      // 345: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 348: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 34b: aload 0
      // 34c: aload 18
      // 34e: iload 7
      // 350: iload 8
      // 352: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 355: goto 431
      // 358: aload 4
      // 35a: invokeinterface com/google/protobuf/Reader.readEnum ()I 1
      // 35f: istore 9
      // 361: aload 0
      // 362: iload 8
      // 364: invokespecial com/google/protobuf/MessageSchema.getEnumFieldVerifier (I)Lcom/google/protobuf/Internal$EnumVerifier;
      // 367: astore 15
      // 369: aload 15
      // 36b: ifnull 393
      // 36e: aload 15
      // 370: iload 9
      // 372: invokeinterface com/google/protobuf/Internal$EnumVerifier.isInRange (I)Z 2
      // 377: ifeq 37d
      // 37a: goto 393
      // 37d: aload 18
      // 37f: iload 7
      // 381: iload 9
      // 383: aload 13
      // 385: aload 19
      // 387: invokestatic com/google/protobuf/SchemaUtil.storeUnknownEnum (Ljava/lang/Object;IILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;)Ljava/lang/Object;
      // 38a: astore 15
      // 38c: aload 15
      // 38e: astore 13
      // 390: goto bf2
      // 393: aload 18
      // 395: iload 6
      // 397: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 39a: iload 9
      // 39c: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 39f: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 3a2: aload 0
      // 3a3: aload 18
      // 3a5: iload 7
      // 3a7: iload 8
      // 3a9: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 3ac: goto 431
      // 3af: aload 18
      // 3b1: iload 6
      // 3b3: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 3b6: aload 4
      // 3b8: invokeinterface com/google/protobuf/Reader.readUInt32 ()I 1
      // 3bd: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 3c0: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 3c3: aload 0
      // 3c4: aload 18
      // 3c6: iload 7
      // 3c8: iload 8
      // 3ca: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 3cd: goto 431
      // 3d0: aload 18
      // 3d2: iload 6
      // 3d4: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 3d7: aload 4
      // 3d9: invokeinterface com/google/protobuf/Reader.readBytes ()Lcom/google/protobuf/ByteString; 1
      // 3de: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 3e1: aload 0
      // 3e2: aload 18
      // 3e4: iload 7
      // 3e6: iload 8
      // 3e8: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 3eb: goto 431
      // 3ee: aload 0
      // 3ef: aload 18
      // 3f1: iload 7
      // 3f3: iload 8
      // 3f5: invokespecial com/google/protobuf/MessageSchema.mutableOneofMessageFieldForMerge (Ljava/lang/Object;II)Ljava/lang/Object;
      // 3f8: checkcast com/google/protobuf/MessageLite
      // 3fb: astore 15
      // 3fd: aload 4
      // 3ff: aload 15
      // 401: aload 0
      // 402: iload 8
      // 404: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 407: aload 20
      // 409: invokeinterface com/google/protobuf/Reader.mergeMessageField (Ljava/lang/Object;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
      // 40e: aload 0
      // 40f: aload 18
      // 411: iload 7
      // 413: iload 8
      // 415: aload 15
      // 417: invokespecial com/google/protobuf/MessageSchema.storeOneofMessageField (Ljava/lang/Object;IILjava/lang/Object;)V
      // 41a: goto 431
      // 41d: aload 0
      // 41e: aload 18
      // 420: iload 6
      // 422: aload 4
      // 424: invokespecial com/google/protobuf/MessageSchema.readString (Ljava/lang/Object;ILcom/google/protobuf/Reader;)V
      // 427: aload 0
      // 428: aload 18
      // 42a: iload 7
      // 42c: iload 8
      // 42e: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 431: goto 555
      // 434: aload 18
      // 436: iload 6
      // 438: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 43b: aload 4
      // 43d: invokeinterface com/google/protobuf/Reader.readBool ()Z 1
      // 442: invokestatic java/lang/Boolean.valueOf (Z)Ljava/lang/Boolean;
      // 445: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 448: aload 0
      // 449: aload 18
      // 44b: iload 7
      // 44d: iload 8
      // 44f: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 452: goto 431
      // 455: aload 18
      // 457: iload 6
      // 459: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 45c: aload 4
      // 45e: invokeinterface com/google/protobuf/Reader.readFixed32 ()I 1
      // 463: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 466: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 469: aload 0
      // 46a: aload 18
      // 46c: iload 7
      // 46e: iload 8
      // 470: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 473: goto 431
      // 476: aload 18
      // 478: iload 6
      // 47a: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 47d: aload 4
      // 47f: invokeinterface com/google/protobuf/Reader.readFixed64 ()J 1
      // 484: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 487: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 48a: aload 0
      // 48b: aload 18
      // 48d: iload 7
      // 48f: iload 8
      // 491: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 494: goto 431
      // 497: aload 18
      // 499: iload 6
      // 49b: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 49e: aload 4
      // 4a0: invokeinterface com/google/protobuf/Reader.readInt32 ()I 1
      // 4a5: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 4a8: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 4ab: aload 0
      // 4ac: aload 18
      // 4ae: iload 7
      // 4b0: iload 8
      // 4b2: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 4b5: goto 431
      // 4b8: aload 18
      // 4ba: iload 6
      // 4bc: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 4bf: aload 4
      // 4c1: invokeinterface com/google/protobuf/Reader.readUInt64 ()J 1
      // 4c6: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 4c9: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 4cc: aload 0
      // 4cd: aload 18
      // 4cf: iload 7
      // 4d1: iload 8
      // 4d3: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 4d6: goto 431
      // 4d9: aload 18
      // 4db: iload 6
      // 4dd: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 4e0: aload 4
      // 4e2: invokeinterface com/google/protobuf/Reader.readInt64 ()J 1
      // 4e7: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 4ea: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 4ed: aload 0
      // 4ee: aload 18
      // 4f0: iload 7
      // 4f2: iload 8
      // 4f4: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 4f7: goto 431
      // 4fa: aload 18
      // 4fc: iload 6
      // 4fe: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 501: aload 4
      // 503: invokeinterface com/google/protobuf/Reader.readFloat ()F 1
      // 508: invokestatic java/lang/Float.valueOf (F)Ljava/lang/Float;
      // 50b: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 50e: aload 0
      // 50f: aload 18
      // 511: iload 7
      // 513: iload 8
      // 515: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 518: goto 431
      // 51b: aload 18
      // 51d: iload 6
      // 51f: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 522: aload 4
      // 524: invokeinterface com/google/protobuf/Reader.readDouble ()D 1
      // 529: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 52c: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 52f: aload 0
      // 530: aload 18
      // 532: iload 7
      // 534: iload 8
      // 536: invokespecial com/google/protobuf/MessageSchema.setOneofPresent (Ljava/lang/Object;II)V
      // 539: goto 431
      // 53c: astore 2
      // 53d: goto bfe
      // 540: aload 0
      // 541: iload 8
      // 543: invokespecial com/google/protobuf/MessageSchema.getMapFieldDefaultEntry (I)Ljava/lang/Object;
      // 546: astore 15
      // 548: aload 0
      // 549: aload 3
      // 54a: iload 8
      // 54c: aload 15
      // 54e: aload 5
      // 550: aload 4
      // 552: invokespecial com/google/protobuf/MessageSchema.mergeMap (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/ExtensionRegistryLite;Lcom/google/protobuf/Reader;)V
      // 555: goto ad1
      // 558: iload 6
      // 55a: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 55d: lstore 11
      // 55f: aload 0
      // 560: iload 8
      // 562: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 565: astore 15
      // 567: aload 0
      // 568: aload 3
      // 569: lload 11
      // 56b: aload 4
      // 56d: aload 15
      // 56f: aload 5
      // 571: invokespecial com/google/protobuf/MessageSchema.readGroupList (Ljava/lang/Object;JLcom/google/protobuf/Reader;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V
      // 574: goto ad1
      // 577: astore 2
      // 578: goto ad5
      // 57b: astore 15
      // 57d: goto ad8
      // 580: aload 4
      // 582: aload 0
      // 583: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 586: aload 18
      // 588: iload 6
      // 58a: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 58d: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 590: invokeinterface com/google/protobuf/Reader.readSInt64List (Ljava/util/List;)V 2
      // 595: goto ad1
      // 598: aload 4
      // 59a: aload 0
      // 59b: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 59e: aload 18
      // 5a0: iload 6
      // 5a2: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 5a5: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 5a8: invokeinterface com/google/protobuf/Reader.readSInt32List (Ljava/util/List;)V 2
      // 5ad: goto ad1
      // 5b0: aload 4
      // 5b2: aload 0
      // 5b3: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 5b6: aload 18
      // 5b8: iload 6
      // 5ba: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 5bd: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 5c0: invokeinterface com/google/protobuf/Reader.readSFixed64List (Ljava/util/List;)V 2
      // 5c5: goto ad1
      // 5c8: aload 4
      // 5ca: aload 0
      // 5cb: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 5ce: aload 18
      // 5d0: iload 6
      // 5d2: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 5d5: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 5d8: invokeinterface com/google/protobuf/Reader.readSFixed32List (Ljava/util/List;)V 2
      // 5dd: goto ad1
      // 5e0: aload 0
      // 5e1: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 5e4: aload 18
      // 5e6: iload 6
      // 5e8: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 5eb: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 5ee: astore 15
      // 5f0: aload 4
      // 5f2: aload 15
      // 5f4: invokeinterface com/google/protobuf/Reader.readEnumList (Ljava/util/List;)V 2
      // 5f9: aload 3
      // 5fa: iload 7
      // 5fc: aload 15
      // 5fe: aload 0
      // 5ff: iload 8
      // 601: invokespecial com/google/protobuf/MessageSchema.getEnumFieldVerifier (I)Lcom/google/protobuf/Internal$EnumVerifier;
      // 604: aload 13
      // 606: aload 1
      // 607: invokestatic com/google/protobuf/SchemaUtil.filterUnknownEnumList (Ljava/lang/Object;ILjava/util/List;Lcom/google/protobuf/Internal$EnumVerifier;Ljava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;)Ljava/lang/Object;
      // 60a: astore 15
      // 60c: aload 15
      // 60e: astore 13
      // 610: goto bf2
      // 613: aload 4
      // 615: aload 0
      // 616: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 619: aload 18
      // 61b: iload 6
      // 61d: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 620: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 623: invokeinterface com/google/protobuf/Reader.readUInt32List (Ljava/util/List;)V 2
      // 628: goto ad1
      // 62b: aload 4
      // 62d: aload 0
      // 62e: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 631: aload 18
      // 633: iload 6
      // 635: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 638: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 63b: invokeinterface com/google/protobuf/Reader.readBoolList (Ljava/util/List;)V 2
      // 640: goto ad1
      // 643: aload 4
      // 645: aload 0
      // 646: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 649: aload 18
      // 64b: iload 6
      // 64d: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 650: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 653: invokeinterface com/google/protobuf/Reader.readFixed32List (Ljava/util/List;)V 2
      // 658: goto ad1
      // 65b: aload 4
      // 65d: aload 0
      // 65e: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 661: aload 18
      // 663: iload 6
      // 665: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 668: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 66b: invokeinterface com/google/protobuf/Reader.readFixed64List (Ljava/util/List;)V 2
      // 670: goto ad1
      // 673: aload 4
      // 675: aload 0
      // 676: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 679: aload 18
      // 67b: iload 6
      // 67d: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 680: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 683: invokeinterface com/google/protobuf/Reader.readInt32List (Ljava/util/List;)V 2
      // 688: goto ad1
      // 68b: aload 4
      // 68d: aload 0
      // 68e: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 691: aload 18
      // 693: iload 6
      // 695: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 698: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 69b: invokeinterface com/google/protobuf/Reader.readUInt64List (Ljava/util/List;)V 2
      // 6a0: goto ad1
      // 6a3: aload 4
      // 6a5: aload 0
      // 6a6: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 6a9: aload 18
      // 6ab: iload 6
      // 6ad: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 6b0: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 6b3: invokeinterface com/google/protobuf/Reader.readInt64List (Ljava/util/List;)V 2
      // 6b8: goto ad1
      // 6bb: aload 4
      // 6bd: aload 0
      // 6be: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 6c1: aload 18
      // 6c3: iload 6
      // 6c5: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 6c8: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 6cb: invokeinterface com/google/protobuf/Reader.readFloatList (Ljava/util/List;)V 2
      // 6d0: goto ad1
      // 6d3: aload 4
      // 6d5: aload 0
      // 6d6: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 6d9: aload 18
      // 6db: iload 6
      // 6dd: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 6e0: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 6e3: invokeinterface com/google/protobuf/Reader.readDoubleList (Ljava/util/List;)V 2
      // 6e8: goto ad1
      // 6eb: aload 4
      // 6ed: aload 0
      // 6ee: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 6f1: aload 18
      // 6f3: iload 6
      // 6f5: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 6f8: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 6fb: invokeinterface com/google/protobuf/Reader.readSInt64List (Ljava/util/List;)V 2
      // 700: goto ad1
      // 703: aload 4
      // 705: aload 0
      // 706: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 709: aload 18
      // 70b: iload 6
      // 70d: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 710: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 713: invokeinterface com/google/protobuf/Reader.readSInt32List (Ljava/util/List;)V 2
      // 718: goto ad1
      // 71b: aload 4
      // 71d: aload 0
      // 71e: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 721: aload 18
      // 723: iload 6
      // 725: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 728: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 72b: invokeinterface com/google/protobuf/Reader.readSFixed64List (Ljava/util/List;)V 2
      // 730: goto ad1
      // 733: aload 4
      // 735: aload 0
      // 736: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 739: aload 18
      // 73b: iload 6
      // 73d: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 740: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 743: invokeinterface com/google/protobuf/Reader.readSFixed32List (Ljava/util/List;)V 2
      // 748: goto ad1
      // 74b: aload 0
      // 74c: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 74f: aload 18
      // 751: iload 6
      // 753: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 756: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 759: astore 15
      // 75b: aload 4
      // 75d: aload 15
      // 75f: invokeinterface com/google/protobuf/Reader.readEnumList (Ljava/util/List;)V 2
      // 764: aload 3
      // 765: iload 7
      // 767: aload 15
      // 769: aload 0
      // 76a: iload 8
      // 76c: invokespecial com/google/protobuf/MessageSchema.getEnumFieldVerifier (I)Lcom/google/protobuf/Internal$EnumVerifier;
      // 76f: aload 13
      // 771: aload 1
      // 772: invokestatic com/google/protobuf/SchemaUtil.filterUnknownEnumList (Ljava/lang/Object;ILjava/util/List;Lcom/google/protobuf/Internal$EnumVerifier;Ljava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;)Ljava/lang/Object;
      // 775: astore 15
      // 777: aload 15
      // 779: astore 13
      // 77b: goto bf2
      // 77e: aload 4
      // 780: aload 0
      // 781: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 784: aload 18
      // 786: iload 6
      // 788: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 78b: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 78e: invokeinterface com/google/protobuf/Reader.readUInt32List (Ljava/util/List;)V 2
      // 793: goto ad1
      // 796: aload 4
      // 798: aload 0
      // 799: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 79c: aload 18
      // 79e: iload 6
      // 7a0: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 7a3: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 7a6: invokeinterface com/google/protobuf/Reader.readBytesList (Ljava/util/List;)V 2
      // 7ab: goto ad1
      // 7ae: aload 0
      // 7af: aload 3
      // 7b0: iload 6
      // 7b2: aload 4
      // 7b4: aload 0
      // 7b5: iload 8
      // 7b7: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 7ba: aload 5
      // 7bc: invokespecial com/google/protobuf/MessageSchema.readMessageList (Ljava/lang/Object;ILcom/google/protobuf/Reader;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V
      // 7bf: goto ad1
      // 7c2: aload 0
      // 7c3: aload 18
      // 7c5: iload 6
      // 7c7: aload 4
      // 7c9: invokespecial com/google/protobuf/MessageSchema.readStringList (Ljava/lang/Object;ILcom/google/protobuf/Reader;)V
      // 7cc: goto ad1
      // 7cf: aload 4
      // 7d1: aload 0
      // 7d2: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 7d5: aload 18
      // 7d7: iload 6
      // 7d9: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 7dc: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 7df: invokeinterface com/google/protobuf/Reader.readBoolList (Ljava/util/List;)V 2
      // 7e4: goto ad1
      // 7e7: aload 4
      // 7e9: aload 0
      // 7ea: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 7ed: aload 18
      // 7ef: iload 6
      // 7f1: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 7f4: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 7f7: invokeinterface com/google/protobuf/Reader.readFixed32List (Ljava/util/List;)V 2
      // 7fc: goto ad1
      // 7ff: aload 4
      // 801: aload 0
      // 802: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 805: aload 18
      // 807: iload 6
      // 809: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 80c: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 80f: invokeinterface com/google/protobuf/Reader.readFixed64List (Ljava/util/List;)V 2
      // 814: goto ad1
      // 817: aload 4
      // 819: aload 0
      // 81a: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 81d: aload 18
      // 81f: iload 6
      // 821: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 824: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 827: invokeinterface com/google/protobuf/Reader.readInt32List (Ljava/util/List;)V 2
      // 82c: goto ad1
      // 82f: aload 4
      // 831: aload 0
      // 832: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 835: aload 18
      // 837: iload 6
      // 839: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 83c: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 83f: invokeinterface com/google/protobuf/Reader.readUInt64List (Ljava/util/List;)V 2
      // 844: goto ad1
      // 847: aload 4
      // 849: aload 0
      // 84a: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 84d: aload 18
      // 84f: iload 6
      // 851: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 854: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 857: invokeinterface com/google/protobuf/Reader.readInt64List (Ljava/util/List;)V 2
      // 85c: goto ad1
      // 85f: aload 4
      // 861: aload 0
      // 862: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 865: aload 18
      // 867: iload 6
      // 869: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 86c: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 86f: invokeinterface com/google/protobuf/Reader.readFloatList (Ljava/util/List;)V 2
      // 874: goto ad1
      // 877: aload 4
      // 879: aload 0
      // 87a: getfield com/google/protobuf/MessageSchema.listFieldSchema Lcom/google/protobuf/ListFieldSchema;
      // 87d: aload 18
      // 87f: iload 6
      // 881: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 884: invokevirtual com/google/protobuf/ListFieldSchema.mutableListAt (Ljava/lang/Object;J)Ljava/util/List;
      // 887: invokeinterface com/google/protobuf/Reader.readDoubleList (Ljava/util/List;)V 2
      // 88c: goto ad1
      // 88f: aload 0
      // 890: aload 18
      // 892: iload 8
      // 894: invokespecial com/google/protobuf/MessageSchema.mutableMessageFieldForMerge (Ljava/lang/Object;I)Ljava/lang/Object;
      // 897: checkcast com/google/protobuf/MessageLite
      // 89a: astore 15
      // 89c: aload 4
      // 89e: aload 15
      // 8a0: aload 0
      // 8a1: iload 8
      // 8a3: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 8a6: aload 20
      // 8a8: invokeinterface com/google/protobuf/Reader.mergeGroupField (Ljava/lang/Object;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
      // 8ad: aload 0
      // 8ae: aload 18
      // 8b0: iload 8
      // 8b2: aload 15
      // 8b4: invokespecial com/google/protobuf/MessageSchema.storeMessageField (Ljava/lang/Object;ILjava/lang/Object;)V
      // 8b7: goto ad1
      // 8ba: aload 18
      // 8bc: iload 6
      // 8be: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 8c1: aload 4
      // 8c3: invokeinterface com/google/protobuf/Reader.readSInt64 ()J 1
      // 8c8: invokestatic com/google/protobuf/UnsafeUtil.putLong (Ljava/lang/Object;JJ)V
      // 8cb: aload 0
      // 8cc: aload 18
      // 8ce: iload 8
      // 8d0: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 8d3: goto ad1
      // 8d6: aload 18
      // 8d8: iload 6
      // 8da: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 8dd: aload 4
      // 8df: invokeinterface com/google/protobuf/Reader.readSInt32 ()I 1
      // 8e4: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // 8e7: aload 0
      // 8e8: aload 18
      // 8ea: iload 8
      // 8ec: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 8ef: goto ad1
      // 8f2: aload 18
      // 8f4: iload 6
      // 8f6: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 8f9: aload 4
      // 8fb: invokeinterface com/google/protobuf/Reader.readSFixed64 ()J 1
      // 900: invokestatic com/google/protobuf/UnsafeUtil.putLong (Ljava/lang/Object;JJ)V
      // 903: aload 0
      // 904: aload 18
      // 906: iload 8
      // 908: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 90b: goto ad1
      // 90e: aload 18
      // 910: iload 6
      // 912: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 915: aload 4
      // 917: invokeinterface com/google/protobuf/Reader.readSFixed32 ()I 1
      // 91c: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // 91f: aload 0
      // 920: aload 18
      // 922: iload 8
      // 924: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 927: goto ad1
      // 92a: aload 4
      // 92c: invokeinterface com/google/protobuf/Reader.readEnum ()I 1
      // 931: istore 9
      // 933: aload 0
      // 934: iload 8
      // 936: invokespecial com/google/protobuf/MessageSchema.getEnumFieldVerifier (I)Lcom/google/protobuf/Internal$EnumVerifier;
      // 939: astore 15
      // 93b: aload 15
      // 93d: ifnull 965
      // 940: aload 15
      // 942: iload 9
      // 944: invokeinterface com/google/protobuf/Internal$EnumVerifier.isInRange (I)Z 2
      // 949: ifeq 94f
      // 94c: goto 965
      // 94f: aload 18
      // 951: iload 7
      // 953: iload 9
      // 955: aload 13
      // 957: aload 19
      // 959: invokestatic com/google/protobuf/SchemaUtil.storeUnknownEnum (Ljava/lang/Object;IILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;)Ljava/lang/Object;
      // 95c: astore 15
      // 95e: aload 15
      // 960: astore 13
      // 962: goto bf2
      // 965: aload 18
      // 967: iload 6
      // 969: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 96c: iload 9
      // 96e: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // 971: aload 0
      // 972: aload 18
      // 974: iload 8
      // 976: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 979: goto ad1
      // 97c: aload 18
      // 97e: iload 6
      // 980: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 983: aload 4
      // 985: invokeinterface com/google/protobuf/Reader.readUInt32 ()I 1
      // 98a: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // 98d: aload 0
      // 98e: aload 18
      // 990: iload 8
      // 992: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 995: goto ad1
      // 998: aload 18
      // 99a: iload 6
      // 99c: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 99f: aload 4
      // 9a1: invokeinterface com/google/protobuf/Reader.readBytes ()Lcom/google/protobuf/ByteString; 1
      // 9a6: invokestatic com/google/protobuf/UnsafeUtil.putObject (Ljava/lang/Object;JLjava/lang/Object;)V
      // 9a9: aload 0
      // 9aa: aload 18
      // 9ac: iload 8
      // 9ae: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 9b1: goto ad1
      // 9b4: aload 0
      // 9b5: aload 18
      // 9b7: iload 8
      // 9b9: invokespecial com/google/protobuf/MessageSchema.mutableMessageFieldForMerge (Ljava/lang/Object;I)Ljava/lang/Object;
      // 9bc: checkcast com/google/protobuf/MessageLite
      // 9bf: astore 15
      // 9c1: aload 4
      // 9c3: aload 15
      // 9c5: aload 0
      // 9c6: iload 8
      // 9c8: invokespecial com/google/protobuf/MessageSchema.getMessageFieldSchema (I)Lcom/google/protobuf/Schema;
      // 9cb: aload 20
      // 9cd: invokeinterface com/google/protobuf/Reader.mergeMessageField (Ljava/lang/Object;Lcom/google/protobuf/Schema;Lcom/google/protobuf/ExtensionRegistryLite;)V 4
      // 9d2: aload 0
      // 9d3: aload 18
      // 9d5: iload 8
      // 9d7: aload 15
      // 9d9: invokespecial com/google/protobuf/MessageSchema.storeMessageField (Ljava/lang/Object;ILjava/lang/Object;)V
      // 9dc: goto ad1
      // 9df: aload 0
      // 9e0: aload 18
      // 9e2: iload 6
      // 9e4: aload 4
      // 9e6: invokespecial com/google/protobuf/MessageSchema.readString (Ljava/lang/Object;ILcom/google/protobuf/Reader;)V
      // 9e9: aload 0
      // 9ea: aload 18
      // 9ec: iload 8
      // 9ee: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // 9f1: goto ad1
      // 9f4: aload 18
      // 9f6: iload 6
      // 9f8: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // 9fb: aload 4
      // 9fd: invokeinterface com/google/protobuf/Reader.readBool ()Z 1
      // a02: invokestatic com/google/protobuf/UnsafeUtil.putBoolean (Ljava/lang/Object;JZ)V
      // a05: aload 0
      // a06: aload 18
      // a08: iload 8
      // a0a: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a0d: goto ad1
      // a10: aload 18
      // a12: iload 6
      // a14: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // a17: aload 4
      // a19: invokeinterface com/google/protobuf/Reader.readFixed32 ()I 1
      // a1e: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // a21: aload 0
      // a22: aload 18
      // a24: iload 8
      // a26: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a29: goto ad1
      // a2c: aload 18
      // a2e: iload 6
      // a30: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // a33: aload 4
      // a35: invokeinterface com/google/protobuf/Reader.readFixed64 ()J 1
      // a3a: invokestatic com/google/protobuf/UnsafeUtil.putLong (Ljava/lang/Object;JJ)V
      // a3d: aload 0
      // a3e: aload 18
      // a40: iload 8
      // a42: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a45: goto ad1
      // a48: aload 18
      // a4a: iload 6
      // a4c: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // a4f: aload 4
      // a51: invokeinterface com/google/protobuf/Reader.readInt32 ()I 1
      // a56: invokestatic com/google/protobuf/UnsafeUtil.putInt (Ljava/lang/Object;JI)V
      // a59: aload 0
      // a5a: aload 18
      // a5c: iload 8
      // a5e: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a61: goto ad1
      // a64: aload 18
      // a66: iload 6
      // a68: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // a6b: aload 4
      // a6d: invokeinterface com/google/protobuf/Reader.readUInt64 ()J 1
      // a72: invokestatic com/google/protobuf/UnsafeUtil.putLong (Ljava/lang/Object;JJ)V
      // a75: aload 0
      // a76: aload 18
      // a78: iload 8
      // a7a: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a7d: goto ad1
      // a80: aload 18
      // a82: iload 6
      // a84: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // a87: aload 4
      // a89: invokeinterface com/google/protobuf/Reader.readInt64 ()J 1
      // a8e: invokestatic com/google/protobuf/UnsafeUtil.putLong (Ljava/lang/Object;JJ)V
      // a91: aload 0
      // a92: aload 18
      // a94: iload 8
      // a96: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // a99: goto ad1
      // a9c: aload 18
      // a9e: iload 6
      // aa0: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // aa3: aload 4
      // aa5: invokeinterface com/google/protobuf/Reader.readFloat ()F 1
      // aaa: invokestatic com/google/protobuf/UnsafeUtil.putFloat (Ljava/lang/Object;JF)V
      // aad: aload 0
      // aae: aload 18
      // ab0: iload 8
      // ab2: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // ab5: goto ad1
      // ab8: aload 18
      // aba: iload 6
      // abc: invokestatic com/google/protobuf/MessageSchema.offset (I)J
      // abf: aload 4
      // ac1: invokeinterface com/google/protobuf/Reader.readDouble ()D 1
      // ac6: invokestatic com/google/protobuf/UnsafeUtil.putDouble (Ljava/lang/Object;JD)V
      // ac9: aload 0
      // aca: aload 18
      // acc: iload 8
      // ace: invokespecial com/google/protobuf/MessageSchema.setFieldPresent (Ljava/lang/Object;I)V
      // ad1: goto bf2
      // ad4: astore 2
      // ad5: goto c0a
      // ad8: aload 13
      // ada: astore 15
      // adc: goto b36
      // adf: aload 13
      // ae1: astore 15
      // ae3: aload 15
      // ae5: astore 13
      // ae7: aload 19
      // ae9: aload 15
      // aeb: aload 4
      // aed: invokevirtual com/google/protobuf/UnknownFieldSchema.mergeOneFieldFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;)Z
      // af0: istore 10
      // af2: aload 15
      // af4: astore 13
      // af6: iload 10
      // af8: ifne bf2
      // afb: aload 0
      // afc: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // aff: istore 6
      // b01: aload 15
      // b03: astore 2
      // b04: iload 6
      // b06: aload 0
      // b07: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // b0a: if_icmpge b23
      // b0d: aload 0
      // b0e: aload 3
      // b0f: aload 0
      // b10: getfield com/google/protobuf/MessageSchema.intArray [I
      // b13: iload 6
      // b15: iaload
      // b16: aload 2
      // b17: aload 1
      // b18: aload 3
      // b19: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // b1c: astore 2
      // b1d: iinc 6 1
      // b20: goto b04
      // b23: aload 2
      // b24: ifnull b2f
      // b27: aload 19
      // b29: aload 18
      // b2b: aload 2
      // b2c: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // b2f: return
      // b30: astore 15
      // b32: aload 13
      // b34: astore 15
      // b36: aload 15
      // b38: astore 13
      // b3a: aload 19
      // b3c: aload 4
      // b3e: invokevirtual com/google/protobuf/UnknownFieldSchema.shouldDiscardUnknownFields (Lcom/google/protobuf/Reader;)Z
      // b41: ifeq b8f
      // b44: aload 15
      // b46: astore 13
      // b48: aload 4
      // b4a: invokeinterface com/google/protobuf/Reader.skipField ()Z 1
      // b4f: istore 10
      // b51: aload 15
      // b53: astore 13
      // b55: iload 10
      // b57: ifne bf2
      // b5a: aload 0
      // b5b: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // b5e: istore 6
      // b60: aload 15
      // b62: astore 2
      // b63: iload 6
      // b65: aload 0
      // b66: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // b69: if_icmpge b82
      // b6c: aload 0
      // b6d: aload 3
      // b6e: aload 0
      // b6f: getfield com/google/protobuf/MessageSchema.intArray [I
      // b72: iload 6
      // b74: iaload
      // b75: aload 2
      // b76: aload 1
      // b77: aload 3
      // b78: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // b7b: astore 2
      // b7c: iinc 6 1
      // b7f: goto b63
      // b82: aload 2
      // b83: ifnull b8e
      // b86: aload 19
      // b88: aload 18
      // b8a: aload 2
      // b8b: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // b8e: return
      // b8f: aload 15
      // b91: astore 16
      // b93: aload 15
      // b95: ifnonnull ba5
      // b98: aload 15
      // b9a: astore 13
      // b9c: aload 19
      // b9e: aload 18
      // ba0: invokevirtual com/google/protobuf/UnknownFieldSchema.getBuilderFromMessage (Ljava/lang/Object;)Ljava/lang/Object;
      // ba3: astore 16
      // ba5: aload 16
      // ba7: astore 13
      // ba9: aload 19
      // bab: aload 16
      // bad: aload 4
      // baf: invokevirtual com/google/protobuf/UnknownFieldSchema.mergeOneFieldFrom (Ljava/lang/Object;Lcom/google/protobuf/Reader;)Z
      // bb2: istore 10
      // bb4: aload 16
      // bb6: astore 13
      // bb8: iload 10
      // bba: ifne bf2
      // bbd: aload 0
      // bbe: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // bc1: istore 6
      // bc3: aload 16
      // bc5: astore 2
      // bc6: iload 6
      // bc8: aload 0
      // bc9: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // bcc: if_icmpge be5
      // bcf: aload 0
      // bd0: aload 3
      // bd1: aload 0
      // bd2: getfield com/google/protobuf/MessageSchema.intArray [I
      // bd5: iload 6
      // bd7: iaload
      // bd8: aload 2
      // bd9: aload 1
      // bda: aload 3
      // bdb: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // bde: astore 2
      // bdf: iinc 6 1
      // be2: goto bc6
      // be5: aload 2
      // be6: ifnull bf1
      // be9: aload 19
      // beb: aload 18
      // bed: aload 2
      // bee: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // bf1: return
      // bf2: aload 18
      // bf4: astore 14
      // bf6: goto 010
      // bf9: astore 2
      // bfa: goto c0a
      // bfd: astore 2
      // bfe: aload 14
      // c00: astore 4
      // c02: goto c0a
      // c05: astore 2
      // c06: aload 14
      // c08: astore 4
      // c0a: aload 0
      // c0b: getfield com/google/protobuf/MessageSchema.checkInitializedCount I
      // c0e: istore 6
      // c10: aload 13
      // c12: astore 4
      // c14: iload 6
      // c16: aload 0
      // c17: getfield com/google/protobuf/MessageSchema.repeatedFieldOffsetStart I
      // c1a: if_icmpge c35
      // c1d: aload 0
      // c1e: aload 3
      // c1f: aload 0
      // c20: getfield com/google/protobuf/MessageSchema.intArray [I
      // c23: iload 6
      // c25: iaload
      // c26: aload 4
      // c28: aload 1
      // c29: aload 3
      // c2a: invokespecial com/google/protobuf/MessageSchema.filterMapUnknownEnumValues (Ljava/lang/Object;ILjava/lang/Object;Lcom/google/protobuf/UnknownFieldSchema;Ljava/lang/Object;)Ljava/lang/Object;
      // c2d: astore 4
      // c2f: iinc 6 1
      // c32: goto c14
      // c35: aload 4
      // c37: ifnull c43
      // c3a: aload 19
      // c3c: aload 14
      // c3e: aload 4
      // c40: invokevirtual com/google/protobuf/UnknownFieldSchema.setBuilderToMessage (Ljava/lang/Object;Ljava/lang/Object;)V
      // c43: aload 2
      // c44: athrow
      // c45: astore 15
      // c47: goto ad8
      // c4a: astore 15
      // c4c: goto b32
      // c4f: astore 13
      // c51: goto b36
   }

   private final <K, V> void mergeMap(Object var1, int var2, Object var3, ExtensionRegistryLite var4, Reader var5) throws IOException {
      long var6 = offset(this.typeAndOffsetAt(var2));
      Object var9 = UnsafeUtil.getObject(var1, var6);
      Object var8;
      if (var9 == null) {
         var8 = this.mapFieldSchema.newMapField(var3);
         UnsafeUtil.putObject(var1, var6, var8);
      } else {
         var8 = var9;
         if (this.mapFieldSchema.isImmutable(var9)) {
            var8 = this.mapFieldSchema.newMapField(var3);
            this.mapFieldSchema.mergeFrom(var8, var9);
            UnsafeUtil.putObject(var1, var6, var8);
         }
      }

      var5.readMap(this.mapFieldSchema.forMutableMapData(var8), this.mapFieldSchema.forMapMetadata(var3), var4);
   }

   private void mergeMessage(T var1, T var2, int var3) {
      if (this.isFieldPresent(var2, var3)) {
         long var4 = offset(this.typeAndOffsetAt(var3));
         Unsafe var9 = UNSAFE;
         Object var7 = var9.getObject(var2, var4);
         if (var7 != null) {
            Schema var8 = this.getMessageFieldSchema(var3);
            if (!this.isFieldPresent((T)var1, var3)) {
               if (!isMutable(var7)) {
                  var9.putObject(var1, var4, var7);
               } else {
                  var2 = var8.newInstance();
                  var8.mergeFrom(var2, var7);
                  var9.putObject(var1, var4, var2);
               }

               this.setFieldPresent((T)var1, var3);
            } else {
               Object var6 = var9.getObject(var1, var4);
               var2 = var6;
               if (!isMutable(var6)) {
                  var2 = var8.newInstance();
                  var8.mergeFrom(var2, var6);
                  var9.putObject(var1, var4, var2);
               }

               var8.mergeFrom(var2, var7);
            }
         } else {
            var1 = new StringBuilder("Source subfield ");
            var1.append(this.numberAt(var3));
            var1.append(" is present but null: ");
            var1.append(var2);
            throw new IllegalStateException(var1.toString());
         }
      }
   }

   private void mergeOneofMessage(T var1, T var2, int var3) {
      int var4 = this.numberAt(var3);
      if (this.isOneofPresent(var2, var4, var3)) {
         long var5 = offset(this.typeAndOffsetAt(var3));
         Unsafe var9 = UNSAFE;
         Object var8 = var9.getObject(var2, var5);
         if (var8 != null) {
            Schema var10 = this.getMessageFieldSchema(var3);
            if (!this.isOneofPresent((T)var1, var4, var3)) {
               if (!isMutable(var8)) {
                  var9.putObject(var1, var5, var8);
               } else {
                  var2 = var10.newInstance();
                  var10.mergeFrom(var2, var8);
                  var9.putObject(var1, var5, var2);
               }

               this.setOneofPresent((T)var1, var4, var3);
            } else {
               Object var7 = var9.getObject(var1, var5);
               var2 = var7;
               if (!isMutable(var7)) {
                  var2 = var10.newInstance();
                  var10.mergeFrom(var2, var7);
                  var9.putObject(var1, var5, var2);
               }

               var10.mergeFrom(var2, var8);
            }
         } else {
            var1 = new StringBuilder("Source subfield ");
            var1.append(this.numberAt(var3));
            var1.append(" is present but null: ");
            var1.append(var2);
            throw new IllegalStateException(var1.toString());
         }
      }
   }

   private void mergeSingleField(T var1, T var2, int var3) {
      int var4 = this.typeAndOffsetAt(var3);
      long var6 = offset(var4);
      int var5 = this.numberAt(var3);
      switch (type(var4)) {
         case 0:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putDouble(var1, var6, UnsafeUtil.getDouble(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 1:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putFloat(var1, var6, UnsafeUtil.getFloat(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 2:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putLong(var1, var6, UnsafeUtil.getLong(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 3:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putLong(var1, var6, UnsafeUtil.getLong(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 4:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 5:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putLong(var1, var6, UnsafeUtil.getLong(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 6:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 7:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putBoolean(var1, var6, UnsafeUtil.getBoolean(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 8:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putObject(var1, var6, UnsafeUtil.getObject(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 9:
            this.mergeMessage((T)var1, (T)var2, var3);
            break;
         case 10:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putObject(var1, var6, UnsafeUtil.getObject(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 11:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 12:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 13:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 14:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putLong(var1, var6, UnsafeUtil.getLong(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 15:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putInt(var1, var6, UnsafeUtil.getInt(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 16:
            if (this.isFieldPresent((T)var2, var3)) {
               UnsafeUtil.putLong(var1, var6, UnsafeUtil.getLong(var2, var6));
               this.setFieldPresent((T)var1, var3);
            }
            break;
         case 17:
            this.mergeMessage((T)var1, (T)var2, var3);
            break;
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
            this.listFieldSchema.mergeListsAt(var1, var2, var6);
            break;
         case 50:
            SchemaUtil.mergeMap(this.mapFieldSchema, var1, var2, var6);
            break;
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
            if (this.isOneofPresent((T)var2, var5, var3)) {
               UnsafeUtil.putObject(var1, var6, UnsafeUtil.getObject(var2, var6));
               this.setOneofPresent((T)var1, var5, var3);
            }
            break;
         case 60:
            this.mergeOneofMessage((T)var1, (T)var2, var3);
            break;
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
            if (this.isOneofPresent((T)var2, var5, var3)) {
               UnsafeUtil.putObject(var1, var6, UnsafeUtil.getObject(var2, var6));
               this.setOneofPresent((T)var1, var5, var3);
            }
            break;
         case 68:
            this.mergeOneofMessage((T)var1, (T)var2, var3);
      }
   }

   private Object mutableMessageFieldForMerge(T var1, int var2) {
      Schema var5 = this.getMessageFieldSchema(var2);
      long var3 = offset(this.typeAndOffsetAt(var2));
      if (!this.isFieldPresent((T)var1, var2)) {
         return var5.newInstance();
      } else {
         var1 = UNSAFE.getObject(var1, var3);
         if (isMutable(var1)) {
            return var1;
         } else {
            Object var6 = var5.newInstance();
            if (var1 != null) {
               var5.mergeFrom(var6, var1);
            }

            return var6;
         }
      }
   }

   private Object mutableOneofMessageFieldForMerge(T var1, int var2, int var3) {
      Schema var4 = this.getMessageFieldSchema(var3);
      if (!this.isOneofPresent((T)var1, var2, var3)) {
         return var4.newInstance();
      } else {
         Object var5 = UNSAFE.getObject(var1, offset(this.typeAndOffsetAt(var3)));
         if (isMutable(var5)) {
            return var5;
         } else {
            var1 = var4.newInstance();
            if (var5 != null) {
               var4.mergeFrom(var1, var5);
            }

            return var1;
         }
      }
   }

   static <T> MessageSchema<T> newSchema(
      Class<T> var0,
      MessageInfo var1,
      NewInstanceSchema var2,
      ListFieldSchema var3,
      UnknownFieldSchema<?, ?> var4,
      ExtensionSchema<?> var5,
      MapFieldSchema var6
   ) {
      return var1 instanceof RawMessageInfo
         ? newSchemaForRawMessageInfo((RawMessageInfo)var1, var2, var3, var4, var5, var6)
         : newSchemaForMessageInfo((StructuralMessageInfo)var1, var2, var3, var4, var5, var6);
   }

   static <T> MessageSchema<T> newSchemaForMessageInfo(
      StructuralMessageInfo var0, NewInstanceSchema var1, ListFieldSchema var2, UnknownFieldSchema<?, ?> var3, ExtensionSchema<?> var4, MapFieldSchema var5
   ) {
      FieldInfo[] var21 = var0.getFields();
      int var8;
      int var9;
      if (var21.length == 0) {
         var8 = 0;
         var9 = 0;
      } else {
         var8 = var21[0].getFieldNumber();
         var9 = var21[var21.length - 1].getFieldNumber();
      }

      int var6 = var21.length;
      int[] var20 = new int[var6 * 3];
      Object[] var19 = new Object[var6 * 2];
      int var13 = var21.length;
      int var10 = 0;
      int var7 = 0;
      var6 = 0;

      while (var10 < var13) {
         FieldInfo var15 = var21[var10];
         int var11;
         int var12;
         if (var15.getType() == FieldType.MAP) {
            var11 = var7 + 1;
            var12 = var6;
         } else {
            var11 = var7;
            var12 = var6;
            if (var15.getType().id() >= 18) {
               var11 = var7;
               var12 = var6;
               if (var15.getType().id() <= 49) {
                  var12 = var6 + 1;
                  var11 = var7;
               }
            }
         }

         var10++;
         var7 = var11;
         var6 = var12;
      }

      int[] var30 = null;
      int[] var16;
      if (var7 > 0) {
         var16 = new int[var7];
      } else {
         var16 = null;
      }

      if (var6 > 0) {
         var30 = new int[var6];
      }

      int[] var18 = var0.getCheckInitialized();
      int[] var17 = var18;
      if (var18 == null) {
         var17 = EMPTY_INT_ARRAY;
      }

      int var28 = 0;
      byte var26 = 0;
      var7 = 0;
      var6 = 0;
      int var27 = 0;

      while (var28 < var21.length) {
         FieldInfo var33 = var21[var28];
         int var14 = var33.getFieldNumber();
         storeFieldData(var33, var20, var26, var19);
         var13 = var7;
         if (var7 < var17.length) {
            var13 = var7;
            if (var17[var7] == var14) {
               var17[var7] = var26;
               var13 = var7 + 1;
            }
         }

         label64: {
            if (var33.getType() == FieldType.MAP) {
               var16[var6] = var26;
               var7 = var6 + 1;
            } else {
               var7 = var6;
               if (var33.getType().id() >= 18) {
                  var7 = var6;
                  if (var33.getType().id() <= 49) {
                     var30[var27] = (int)UnsafeUtil.objectFieldOffset(var33.getField());
                     var27++;
                     break label64;
                  }
               }
            }

            var6 = var7;
         }

         var28++;
         var26 += 3;
         var7 = var13;
      }

      var18 = var16;
      if (var16 == null) {
         var18 = EMPTY_INT_ARRAY;
      }

      var16 = var30;
      if (var30 == null) {
         var16 = EMPTY_INT_ARRAY;
      }

      var30 = new int[var17.length + var18.length + var16.length];
      System.arraycopy(var17, 0, var30, 0, var17.length);
      System.arraycopy(var18, 0, var30, var17.length, var18.length);
      System.arraycopy(var16, 0, var30, var17.length + var18.length, var16.length);
      return new MessageSchema<>(
         var20,
         var19,
         var8,
         var9,
         var0.getDefaultInstance(),
         var0.getSyntax(),
         true,
         var30,
         var17.length,
         var17.length + var18.length,
         var1,
         var2,
         var3,
         var4,
         var5
      );
   }

   static <T> MessageSchema<T> newSchemaForRawMessageInfo(
      RawMessageInfo var0, NewInstanceSchema var1, ListFieldSchema var2, UnknownFieldSchema<?, ?> var3, ExtensionSchema<?> var4, MapFieldSchema var5
   ) {
      String var26 = var0.getStringInfo();
      int var18 = var26.length();
      int var7;
      if (var26.charAt(0) >= '\ud800') {
         int var6 = 1;

         while (true) {
            int var8 = var6 + 1;
            var7 = var8;
            if (var26.charAt(var6) < '\ud800') {
               break;
            }

            var6 = var8;
         }
      } else {
         var7 = 1;
      }

      int var33 = var7 + 1;
      int var9 = var26.charAt(var7);
      int var52 = var33;
      var7 = var9;
      if (var9 >= 55296) {
         var52 = var9 & 8191;
         byte var40 = 13;
         var9 = var33;

         while (true) {
            var33 = var9 + 1;
            char var74 = var26.charAt(var9);
            if (var74 < '\ud800') {
               var7 = var52 | var74 << var40;
               var52 = var33;
               break;
            }

            var52 |= (var74 & 8191) << var40;
            var40 += 13;
            var9 = var33;
         }
      }

      int var10;
      int var11;
      int var12;
      int var13;
      int var15;
      int[] var25;
      if (var7 == 0) {
         var25 = EMPTY_INT_ARRAY;
         var11 = 0;
         var10 = 0;
         var15 = 0;
         var7 = 0;
         var13 = 0;
         var12 = 0;
         var9 = 0;
      } else {
         var7 = var52 + 1;
         int var76 = var26.charAt(var52);
         var33 = var76;
         var52 = var7;
         if (var76 >= 55296) {
            var52 = var76 & 8191;
            byte var36 = 13;
            var76 = var7;

            while (true) {
               var7 = var76 + 1;
               char var78 = var26.charAt(var76);
               if (var78 < '\ud800') {
                  var33 = var52 | var78 << var36;
                  var52 = var7;
                  break;
               }

               var52 |= (var78 & 8191) << var36;
               var36 += 13;
               var76 = var7;
            }
         }

         var7 = var52 + 1;
         int var79 = var26.charAt(var52);
         var13 = var79;
         var52 = var7;
         if (var79 >= 55296) {
            var79 &= 8191;
            byte var57 = 13;
            var10 = var7;

            while (true) {
               var7 = var10 + 1;
               char var97 = var26.charAt(var10);
               if (var97 < '\ud800') {
                  var13 = var79 | var97 << var57;
                  var52 = var7;
                  break;
               }

               var79 |= (var97 & 8191) << var57;
               var57 += 13;
               var10 = var7;
            }
         }

         var79 = var52 + 1;
         int var98 = var26.charAt(var52);
         var7 = var98;
         var52 = var79;
         if (var98 >= 55296) {
            var7 = var98 & 8191;
            byte var59 = 13;
            var98 = var79;
            var79 = var7;

            while (true) {
               var7 = var98 + 1;
               char var100 = var26.charAt(var98);
               if (var100 < '\ud800') {
                  var79 |= var100 << var59;
                  var52 = var7;
                  var7 = var79;
                  break;
               }

               var79 |= (var100 & 8191) << var59;
               var59 += 13;
               var98 = var7;
            }
         }

         var79 = var52 + 1;
         int var106 = var26.charAt(var52);
         var52 = var106;
         var98 = var79;
         if (var106 >= 55296) {
            var52 = var106 & 8191;
            byte var102 = 13;
            var106 = var79;
            var79 = var52;

            while (true) {
               var52 = var106 + 1;
               char var108 = var26.charAt(var106);
               if (var108 < '\ud800') {
                  var79 |= var108 << var102;
                  var98 = var52;
                  var52 = var79;
                  break;
               }

               var79 |= (var108 & 8191) << var102;
               var102 += 13;
               var106 = var52;
            }
         }

         var106 = var98 + 1;
         int var119 = var26.charAt(var98);
         var10 = var119;
         var79 = var106;
         if (var119 >= 55296) {
            var98 = var119 & 8191;
            byte var88 = 13;
            var119 = var106;
            var106 = var98;

            while (true) {
               var98 = var119 + 1;
               char var121 = var26.charAt(var119);
               if (var121 < '\ud800') {
                  var106 |= var121 << var88;
                  var79 = var98;
                  var10 = var106;
                  break;
               }

               var106 |= (var121 & 8191) << var88;
               var88 += 13;
               var119 = var98;
            }
         }

         var119 = var79 + 1;
         int var89 = var26.charAt(var79);
         var106 = var89;
         int var14 = var119;
         if (var89 >= 55296) {
            var106 = var89 & 8191;
            byte var90 = 13;
            var14 = var119;
            var119 = var106;

            while (true) {
               var106 = var14 + 1;
               char var146 = var26.charAt(var14);
               if (var146 < '\ud800') {
                  var89 = var119 | var146 << var90;
                  var14 = var106;
                  var106 = var89;
                  break;
               }

               var119 |= (var146 & 8191) << var90;
               var90 += 13;
               var14 = var106;
            }
         }

         var89 = var14 + 1;
         int var166 = var26.charAt(var14);
         var14 = var166;
         var119 = var89;
         if (var166 >= 55296) {
            var14 = var166 & 8191;
            byte var125 = 13;
            var166 = var89;

            while (true) {
               var89 = var166 + 1;
               char var168 = var26.charAt(var166);
               if (var168 < '\ud800') {
                  var14 |= var168 << var125;
                  var119 = var89;
                  break;
               }

               var14 |= (var168 & 8191) << var125;
               var125 += 13;
               var166 = var89;
            }
         }

         var166 = var119 + 1;
         int var16 = var26.charAt(var119);
         var119 = var16;
         var89 = var166;
         if (var16 >= 55296) {
            var89 = var16 & 8191;
            byte var127 = 13;
            var16 = var166;
            var166 = var89;

            while (true) {
               var89 = var16 + 1;
               char var173 = var26.charAt(var16);
               if (var173 < '\ud800') {
                  var119 = var166 | var173 << var127;
                  break;
               }

               var166 |= (var173 & 8191) << var127;
               var127 += 13;
               var16 = var89;
            }
         }

         var25 = new int[var119 + var106 + var14];
         var13 = var33 * 2 + var13;
         var9 = var119;
         var12 = var13;
         var13 = var52;
         var15 = var106;
         var11 = var33;
         var52 = var89;
      }

      Unsafe var30 = UNSAFE;
      Object[] var28 = var0.getObjects();
      Class var31 = var0.getDefaultInstance().getClass();
      int[] var29 = new int[var10 * 3];
      Object[] var32 = new Object[var10 * 2];
      int var22 = var9 + var15;
      var33 = var9;
      var10 = var22;
      int var17 = 0;
      int var150 = 0;
      var7 = var17;
      var17 = var11;

      while (true) {
         byte var23 = (byte)var150;
         if (var52 >= var18) {
            return new MessageSchema<>(
               var29, var32, var7, var13, var0.getDefaultInstance(), var0.getSyntax(), false, var25, var9, var22, var1, var2, var3, var4, var5
            );
         }

         var11 = var52 + 1;
         int var19 = var26.charAt(var52);
         if (var19 < 55296) {
            var52 = var11;
         } else {
            var13 = var19 & 8191;
            var150 = var11;
            byte var116 = 13;

            while (true) {
               var52 = var150 + 1;
               char var152 = var26.charAt(var150);
               if (var152 < '\ud800') {
                  var19 = var13 | var152 << var116;
                  break;
               }

               var13 |= (var152 & 8191) << var116;
               var116 += 13;
               var150 = var52;
            }
         }

         var150 = var52 + 1;
         int var20 = var26.charAt(var52);
         if (var20 < 55296) {
            var13 = var150;
         } else {
            var13 = var20 & 8191;
            byte var64 = 13;

            while (true) {
               var11 = var150 + 1;
               char var154 = var26.charAt(var150);
               if (var154 < '\ud800') {
                  var20 = var13 | var154 << var64;
                  var13 = var11;
                  break;
               }

               var13 |= (var154 & 8191) << var64;
               var64 += 13;
               var150 = var11;
            }
         }

         int var24 = var20 & 0xFF;
         var11 = var7;
         if ((var20 & 1024) != 0) {
            var25[var7] = var150;
            var11 = var7 + 1;
         }

         if (var24 >= 51) {
            var52 = var13 + 1;
            int var161 = var26.charAt(var13);
            var7 = var52;
            var13 = var161;
            if (var161 >= 55296) {
               var161 &= 8191;
               byte var144 = 13;

               while (true) {
                  var7 = var52 + 1;
                  char var71 = var26.charAt(var52);
                  if (var71 < '\ud800') {
                     var13 = var161 | var71 << var144;
                     break;
                  }

                  var161 |= (var71 & 8191) << var144;
                  var144 += 13;
                  var52 = var7;
               }
            }

            var161 = var24 - 51;
            if (var161 != 9 && var161 != 17) {
               var52 = var12;
               label187:
               if (var161 == 12) {
                  if (!var0.getSyntax().equals(ProtoSyntax.PROTO2)) {
                     var52 = var12;
                     if ((var20 & 2048) == 0) {
                        break label187;
                     }
                  }

                  var161 = var150 / 3;
                  var52 = var12 + 1;
                  var32[var161 * 2 + 1] = var28[var12];
               }
            } else {
               var161 = var150 / 3;
               var52 = var12 + 1;
               var32[var161 * 2 + 1] = var28[var12];
            }

            var12 = var13 * 2;
            Object var182 = var28[var12];
            if (var182 instanceof java.lang.reflect.Field) {
               var182 = (java.lang.reflect.Field)var182;
            } else {
               var182 = reflectField(var31, (String)var182);
               var28[var12] = var182;
            }

            var150 = (int)var30.objectFieldOffset((java.lang.reflect.Field)var182);
            var182 = var28[++var12];
            if (var182 instanceof java.lang.reflect.Field) {
               var182 = (java.lang.reflect.Field)var182;
            } else {
               var182 = reflectField(var31, (String)var182);
               var28[var12] = var182;
            }

            var12 = (int)var30.objectFieldOffset((java.lang.reflect.Field)var182);
            var13 = 0;
            var52 = var7;
            var7 = var52;
         } else {
            var150 = var12 + 1;
            java.lang.reflect.Field var27 = reflectField(var31, (String)var28[var12]);
            if (var24 == 9 || var24 == 17) {
               var32[var150 / 3 * 2 + 1] = var27.getType();
               var7 = var150;
            } else {
               label230:
               if (var24 != 27 && var24 != 49) {
                  if (var24 != 12 && var24 != 30 && var24 != 44) {
                     var7 = var150;
                     var52 = var33;
                     if (var24 == 50) {
                        var7 = var33 + 1;
                        var25[var33] = var150;
                        var52 = var150 / 3 * 2;
                        var33 = var12 + 2;
                        var32[var52] = var28[var150];
                        if ((var20 & 2048) != 0) {
                           var12 += 3;
                           var32[var52 + 1] = var28[var33];
                           var52 = var7;
                           var7 = var12;
                        } else {
                           var52 = var7;
                           var7 = var33;
                        }
                     }
                  } else {
                     label373: {
                        if (var0.getSyntax() != ProtoSyntax.PROTO2) {
                           var7 = var150;
                           var52 = var33;
                           if ((var20 & 2048) == 0) {
                              break label373;
                           }
                        }

                        var52 = var150 / 3;
                        var7 = var12 + 2;
                        var32[var52 * 2 + 1] = var28[var150];
                        break label230;
                     }
                  }

                  var33 = var52;
               } else {
                  var52 = var150 / 3;
                  var7 = var12 + 2;
                  var32[var52 * 2 + 1] = var28[var150];
               }
            }

            int var21 = (int)var30.objectFieldOffset(var27);
            if ((var20 & 4096) != 0 && var24 <= 17) {
               var12 = var13 + 1;
               int var156 = var26.charAt(var13);
               var13 = var156;
               var52 = var12;
               if (var156 >= 55296) {
                  var13 = var156 & 8191;
                  byte var69 = 13;
                  var156 = var12;

                  while (true) {
                     var12 = var156 + 1;
                     char var158 = var26.charAt(var156);
                     if (var158 < '\ud800') {
                        var13 |= var158 << var69;
                        var52 = var12;
                        break;
                     }

                     var13 |= (var158 & 8191) << var69;
                     var69 += 13;
                     var156 = var12;
                  }
               }

               var12 = var17 * 2 + var13 / 32;
               Object var180 = var28[var12];
               if (var180 instanceof java.lang.reflect.Field) {
                  var180 = (java.lang.reflect.Field)var180;
               } else {
                  var180 = reflectField(var31, (String)var180);
                  var28[var12] = var180;
               }

               var12 = (int)var30.objectFieldOffset((java.lang.reflect.Field)var180);
               var13 %= 32;
            } else {
               var12 = 1048575;
               var52 = var13;
               var13 = 0;
            }

            var150 = var10;
            if (var24 >= 18) {
               var150 = var10;
               if (var24 <= 49) {
                  var25[var10] = var21;
                  var150 = var10 + 1;
               }
            }

            var10 = var150;
            var150 = var21;
         }

         var29[var150] = var19;
         if ((var20 & 512) != 0) {
            var19 = 536870912;
         } else {
            var19 = 0;
         }

         int var179;
         if ((var20 & 256) != 0) {
            var179 = 268435456;
         } else {
            var179 = 0;
         }

         if ((var20 & 2048) != 0) {
            var20 = Integer.MIN_VALUE;
         } else {
            var20 = 0;
         }

         var29[var150 + 1] = var150 | var24 << 20 | var179 | var19 | var20;
         var150 += 3;
         var29[var23 + 2] = var12 | var13 << 20;
         var12 = var7;
         var7 = var11;
      }
   }

   private int numberAt(int var1) {
      return this.buffer[var1];
   }

   private static long offset(int var0) {
      return var0 & 1048575;
   }

   private static <T> boolean oneofBooleanAt(T var0, long var1) {
      return (Boolean)UnsafeUtil.getObject(var0, var1);
   }

   private static <T> double oneofDoubleAt(T var0, long var1) {
      return (Double)UnsafeUtil.getObject(var0, var1);
   }

   private static <T> float oneofFloatAt(T var0, long var1) {
      return (Float)UnsafeUtil.getObject(var0, var1);
   }

   private static <T> int oneofIntAt(T var0, long var1) {
      return (Integer)UnsafeUtil.getObject(var0, var1);
   }

   private static <T> long oneofLongAt(T var0, long var1) {
      return (Long)UnsafeUtil.getObject(var0, var1);
   }

   private <K, V> int parseMapField(T var1, byte[] var2, int var3, int var4, int var5, long var6, ArrayDecoders.Registers var8) throws IOException {
      Unsafe var12 = UNSAFE;
      Object var11 = this.getMapFieldDefaultEntry(var5);
      Object var10 = var12.getObject(var1, var6);
      Object var9 = var10;
      if (this.mapFieldSchema.isImmutable(var10)) {
         var9 = this.mapFieldSchema.newMapField(var11);
         this.mapFieldSchema.mergeFrom(var9, var10);
         var12.putObject(var1, var6, var9);
      }

      return this.decodeMapEntry(var2, var3, var4, this.mapFieldSchema.forMapMetadata(var11), this.mapFieldSchema.forMutableMapData(var9), var8);
   }

   private int parseOneofField(
      T var1, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10, int var12, ArrayDecoders.Registers var13
   ) throws IOException {
      Unsafe var17 = UNSAFE;
      long var14 = this.buffer[var12 + 2] & 1048575;
      switch (var9) {
         case 51:
            if (var7 == 1) {
               var17.putObject(var1, var10, ArrayDecoders.decodeDouble(var2, var3));
               var3 += 8;
               var17.putInt(var1, var14, var6);
            }
            break;
         case 52:
            if (var7 == 5) {
               var17.putObject(var1, var10, ArrayDecoders.decodeFloat(var2, var3));
               var3 += 4;
               var17.putInt(var1, var14, var6);
            }
            break;
         case 53:
         case 54:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint64(var2, var3, var13);
               var17.putObject(var1, var10, var13.long1);
               var17.putInt(var1, var14, var6);
            }
            break;
         case 55:
         case 62:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint32(var2, var3, var13);
               var17.putObject(var1, var10, var13.int1);
               var17.putInt(var1, var14, var6);
            }
            break;
         case 56:
         case 65:
            if (var7 == 1) {
               var17.putObject(var1, var10, ArrayDecoders.decodeFixed64(var2, var3));
               var3 += 8;
               var17.putInt(var1, var14, var6);
            }
            break;
         case 57:
         case 64:
            if (var7 == 5) {
               var17.putObject(var1, var10, ArrayDecoders.decodeFixed32(var2, var3));
               var3 += 4;
               var17.putInt(var1, var14, var6);
            }
            break;
         case 58:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint64(var2, var3, var13);
               boolean var16;
               if (var13.long1 != 0L) {
                  var16 = true;
               } else {
                  var16 = false;
               }

               var17.putObject(var1, var10, var16);
               var17.putInt(var1, var14, var6);
            }
            break;
         case 59:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodeVarint32(var2, var3, var13);
               var4 = var13.int1;
               if (var4 == 0) {
                  var17.putObject(var1, var10, "");
               } else {
                  if ((var8 & 536870912) != 0 && !Utf8.isValidUtf8(var2, var3, var3 + var4)) {
                     throw InvalidProtocolBufferException.invalidUtf8();
                  }

                  var17.putObject(var1, var10, new String(var2, var3, var4, Internal.UTF_8));
                  var3 += var4;
               }

               var17.putInt(var1, var14, var6);
            }
            break;
         case 60:
            if (var7 == 2) {
               Object var22 = this.mutableOneofMessageFieldForMerge((T)var1, var6, var12);
               var3 = ArrayDecoders.mergeMessageField(var22, this.getMessageFieldSchema(var12), var2, var3, var4, var13);
               this.storeOneofMessageField((T)var1, var6, var12, var22);
            }
            break;
         case 61:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodeBytes(var2, var3, var13);
               var17.putObject(var1, var10, var13.object1);
               var17.putInt(var1, var14, var6);
            }
            break;
         case 63:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint32(var2, var3, var13);
               var4 = var13.int1;
               Internal.EnumVerifier var18 = this.getEnumFieldVerifier(var12);
               if (var18 != null && !var18.isInRange(var4)) {
                  getMutableUnknownFields(var1).storeField(var5, (long)var4);
               } else {
                  var17.putObject(var1, var10, var4);
                  var17.putInt(var1, var14, var6);
               }
            }
            break;
         case 66:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint32(var2, var3, var13);
               var17.putObject(var1, var10, CodedInputStream.decodeZigZag32(var13.int1));
               var17.putInt(var1, var14, var6);
            }
            break;
         case 67:
            if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint64(var2, var3, var13);
               var17.putObject(var1, var10, CodedInputStream.decodeZigZag64(var13.long1));
               var17.putInt(var1, var14, var6);
            }
            break;
         case 68:
            if (var7 == 3) {
               Object var21 = this.mutableOneofMessageFieldForMerge((T)var1, var6, var12);
               var3 = ArrayDecoders.mergeGroupField(var21, this.getMessageFieldSchema(var12), var2, var3, var4, var5 & -8 | 4, var13);
               this.storeOneofMessageField((T)var1, var6, var12, var21);
            }
      }

      return var3;
   }

   private int parseRepeatedField(
      T var1, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9, int var11, long var12, ArrayDecoders.Registers var14
   ) throws IOException {
      Unsafe var18 = UNSAFE;
      Internal.ProtobufList var17 = (Internal.ProtobufList)var18.getObject(var1, var12);
      Internal.ProtobufList var16 = var17;
      if (!var17.isModifiable()) {
         int var15 = var17.size();
         if (var15 == 0) {
            var15 = 10;
         } else {
            var15 *= 2;
         }

         var16 = var17.mutableCopyWithCapacity(var15);
         var18.putObject(var1, var12, var16);
      }

      switch (var11) {
         case 18:
         case 35:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedDoubleList(var2, var3, var16, var14);
            } else if (var7 == 1) {
               var3 = ArrayDecoders.decodeDoubleList(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 19:
         case 36:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedFloatList(var2, var3, var16, var14);
            } else if (var7 == 5) {
               var3 = ArrayDecoders.decodeFloatList(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 20:
         case 21:
         case 37:
         case 38:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedVarint64List(var2, var3, var16, var14);
            } else if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint64List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 22:
         case 29:
         case 39:
         case 43:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedVarint32List(var2, var3, var16, var14);
            } else if (var7 == 0) {
               var3 = ArrayDecoders.decodeVarint32List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 23:
         case 32:
         case 40:
         case 46:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedFixed64List(var2, var3, var16, var14);
            } else if (var7 == 1) {
               var3 = ArrayDecoders.decodeFixed64List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 24:
         case 31:
         case 41:
         case 45:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedFixed32List(var2, var3, var16, var14);
            } else if (var7 == 5) {
               var3 = ArrayDecoders.decodeFixed32List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 25:
         case 42:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedBoolList(var2, var3, var16, var14);
            } else if (var7 == 0) {
               var3 = ArrayDecoders.decodeBoolList(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 26:
            if (var7 == 2) {
               if ((var9 & 536870912L) == 0L) {
                  var3 = ArrayDecoders.decodeStringList(var5, var2, var3, var4, var16, var14);
               } else {
                  var3 = ArrayDecoders.decodeStringListRequireUtf8(var5, var2, var3, var4, var16, var14);
               }
            }
            break;
         case 27:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodeMessageList(this.getMessageFieldSchema(var8), var5, var2, var3, var4, var16, var14);
            }
            break;
         case 28:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodeBytesList(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 30:
         case 44:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedVarint32List(var2, var3, var16, var14);
            } else {
               if (var7 != 0) {
                  break;
               }

               var3 = ArrayDecoders.decodeVarint32List(var5, var2, var3, var4, var16, var14);
            }

            SchemaUtil.filterUnknownEnumList(var1, var6, var16, this.getEnumFieldVerifier(var8), null, this.unknownFieldSchema);
            break;
         case 33:
         case 47:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedSInt32List(var2, var3, var16, var14);
            } else if (var7 == 0) {
               var3 = ArrayDecoders.decodeSInt32List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 34:
         case 48:
            if (var7 == 2) {
               var3 = ArrayDecoders.decodePackedSInt64List(var2, var3, var16, var14);
            } else if (var7 == 0) {
               var3 = ArrayDecoders.decodeSInt64List(var5, var2, var3, var4, var16, var14);
            }
            break;
         case 49:
            if (var7 == 3) {
               var3 = ArrayDecoders.decodeGroupList(this.getMessageFieldSchema(var8), var5, var2, var3, var4, var16, var14);
            }
      }

      return var3;
   }

   private int positionForFieldNumber(int var1) {
      return var1 >= this.minFieldNumber && var1 <= this.maxFieldNumber ? this.slowPositionForFieldNumber(var1, 0) : -1;
   }

   private int positionForFieldNumber(int var1, int var2) {
      return var1 >= this.minFieldNumber && var1 <= this.maxFieldNumber ? this.slowPositionForFieldNumber(var1, var2) : -1;
   }

   private int presenceMaskAndOffsetAt(int var1) {
      return this.buffer[var1 + 2];
   }

   private <E> void readGroupList(Object var1, long var2, Reader var4, Schema<E> var5, ExtensionRegistryLite var6) throws IOException {
      var4.readGroupList(this.listFieldSchema.mutableListAt(var1, var2), var5, var6);
   }

   private <E> void readMessageList(Object var1, int var2, Reader var3, Schema<E> var4, ExtensionRegistryLite var5) throws IOException {
      long var6 = offset(var2);
      var3.readMessageList(this.listFieldSchema.mutableListAt(var1, var6), var4, var5);
   }

   private void readString(Object var1, int var2, Reader var3) throws IOException {
      if (isEnforceUtf8(var2)) {
         UnsafeUtil.putObject(var1, offset(var2), var3.readStringRequireUtf8());
      } else if (this.lite) {
         UnsafeUtil.putObject(var1, offset(var2), var3.readString());
      } else {
         UnsafeUtil.putObject(var1, offset(var2), var3.readBytes());
      }
   }

   private void readStringList(Object var1, int var2, Reader var3) throws IOException {
      if (isEnforceUtf8(var2)) {
         var3.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(var1, offset(var2)));
      } else {
         var3.readStringList(this.listFieldSchema.mutableListAt(var1, offset(var2)));
      }
   }

   private static java.lang.reflect.Field reflectField(Class<?> var0, String var1) {
      try {
         return var0.getDeclaredField(var1);
      } catch (NoSuchFieldException var6) {
         for (java.lang.reflect.Field var5 : var0.getDeclaredFields()) {
            if (var1.equals(var5.getName())) {
               return var5;
            }
         }

         StringBuilder var7 = new StringBuilder("Field ");
         var7.append(var1);
         var7.append(" for ");
         var7.append(var0.getName());
         var7.append(" not found. Known fields are ");
         Object var4;
         var7.append(Arrays.toString((Object[])var4));
         throw new RuntimeException(var7.toString());
      }
   }

   private void setFieldPresent(T var1, int var2) {
      var2 = this.presenceMaskAndOffsetAt(var2);
      long var3 = 1048575 & var2;
      if (var3 != 1048575L) {
         UnsafeUtil.putInt(var1, var3, 1 << (var2 >>> 20) | UnsafeUtil.getInt(var1, var3));
      }
   }

   private void setOneofPresent(T var1, int var2, int var3) {
      UnsafeUtil.putInt(var1, this.presenceMaskAndOffsetAt(var3) & 1048575, var2);
   }

   private int slowPositionForFieldNumber(int var1, int var2) {
      int var3 = this.buffer.length / 3 - 1;

      while (var2 <= var3) {
         int var4 = var3 + var2 >>> 1;
         int var6 = var4 * 3;
         int var5 = this.numberAt(var6);
         if (var1 == var5) {
            return var6;
         }

         if (var1 < var5) {
            var3 = var4 - 1;
         } else {
            var2 = var4 + 1;
         }
      }

      return -1;
   }

   private static void storeFieldData(FieldInfo var0, int[] var1, int var2, Object[] var3) {
      int var4;
      int var5;
      int var6;
      int var7;
      int var9;
      label53: {
         label52: {
            OneofInfo var12 = var0.getOneof();
            var9 = 0;
            long var10;
            if (var12 != null) {
               var5 = var0.getType().id() + 51;
               var4 = (int)UnsafeUtil.objectFieldOffset(var12.getValueField());
               var10 = UnsafeUtil.objectFieldOffset(var12.getCaseField());
            } else {
               FieldType var15 = var0.getType();
               var4 = (int)UnsafeUtil.objectFieldOffset(var0.getField());
               var5 = var15.id();
               if (!var15.isList() && !var15.isMap()) {
                  java.lang.reflect.Field var16 = var0.getPresenceField();
                  if (var16 == null) {
                     var6 = 1048575;
                  } else {
                     var6 = (int)UnsafeUtil.objectFieldOffset(var16);
                  }

                  var7 = Integer.numberOfTrailingZeros(var0.getPresenceMask());
                  break label53;
               }

               if (var0.getCachedSizeField() == null) {
                  var6 = 0;
                  break label52;
               }

               var10 = UnsafeUtil.objectFieldOffset(var0.getCachedSizeField());
            }

            var6 = (int)var10;
         }

         var7 = 0;
      }

      var1[var2] = var0.getFieldNumber();
      int var8;
      if (var0.isEnforceUtf8()) {
         var8 = 536870912;
      } else {
         var8 = 0;
      }

      if (var0.isRequired()) {
         var9 = 268435456;
      }

      var1[var2 + 1] = var9 | var8 | var5 << 20 | var4;
      var1[var2 + 2] = var6 | var7 << 20;
      Class var13 = var0.getMessageFieldClass();
      if (var0.getMapDefaultEntry() != null) {
         var2 = var2 / 3 * 2;
         var3[var2] = var0.getMapDefaultEntry();
         if (var13 != null) {
            var3[var2 + 1] = var13;
         } else if (var0.getEnumVerifier() != null) {
            var3[var2 + 1] = var0.getEnumVerifier();
         }
      } else if (var13 != null) {
         var3[var2 / 3 * 2 + 1] = var13;
      } else if (var0.getEnumVerifier() != null) {
         var3[var2 / 3 * 2 + 1] = var0.getEnumVerifier();
      }
   }

   private void storeMessageField(T var1, int var2, Object var3) {
      UNSAFE.putObject(var1, offset(this.typeAndOffsetAt(var2)), var3);
      this.setFieldPresent((T)var1, var2);
   }

   private void storeOneofMessageField(T var1, int var2, int var3, Object var4) {
      UNSAFE.putObject(var1, offset(this.typeAndOffsetAt(var3)), var4);
      this.setOneofPresent((T)var1, var2, var3);
   }

   private static int type(int var0) {
      return (var0 & 267386880) >>> 20;
   }

   private int typeAndOffsetAt(int var1) {
      return this.buffer[var1 + 1];
   }

   private void writeFieldsInAscendingOrder(T var1, Writer var2) throws IOException {
      Iterator var15;
      Entry var19;
      label200: {
         if (this.hasExtensions) {
            FieldSet var14 = this.extensionSchema.getExtensions(var1);
            if (!var14.isEmpty()) {
               var15 = var14.iterator();
               var19 = (Entry)var15.next();
               break label200;
            }
         }

         var19 = null;
         var15 = null;
      }

      int var5 = this.buffer.length;
      Unsafe var17 = UNSAFE;
      int var4 = 1048575;
      int var3 = 0;

      for (byte var6 = 0; var6 < var5; var6 += 3) {
         int var10 = this.typeAndOffsetAt(var6);
         int var9 = this.numberAt(var6);
         int var8 = type(var10);
         int var18;
         if (var8 <= 17) {
            int var11 = this.buffer[var6 + 2];
            var18 = var11 & 1048575;
            if (var18 != var4) {
               if (var18 == 1048575) {
                  var3 = 0;
               } else {
                  var3 = var17.getInt(var1, var18);
               }

               var4 = var18;
            }

            var18 = 1 << (var11 >>> 20);
         } else {
            var18 = 0;
         }

         while (var19 != null && this.extensionSchema.extensionNumber(var19) <= var9) {
            this.extensionSchema.serializeExtension(var2, var19);
            if (var15.hasNext()) {
               var19 = (Entry)var15.next();
            } else {
               var19 = null;
            }
         }

         long var12 = offset(var10);
         switch (var8) {
            case 0:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeDouble(var9, doubleAt(var1, var12));
               }
               break;
            case 1:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeFloat(var9, floatAt(var1, var12));
               }
               break;
            case 2:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeInt64(var9, var17.getLong(var1, var12));
               }
               break;
            case 3:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeUInt64(var9, var17.getLong(var1, var12));
               }
               break;
            case 4:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeInt32(var9, var17.getInt(var1, var12));
               }
               break;
            case 5:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeFixed64(var9, var17.getLong(var1, var12));
               }
               break;
            case 6:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeFixed32(var9, var17.getInt(var1, var12));
               }
               break;
            case 7:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeBool(var9, booleanAt(var1, var12));
               }
               break;
            case 8:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  this.writeString(var9, var17.getObject(var1, var12), var2);
               }
               break;
            case 9:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeMessage(var9, var17.getObject(var1, var12), this.getMessageFieldSchema(var6));
               }
               break;
            case 10:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeBytes(var9, (ByteString)var17.getObject(var1, var12));
               }
               break;
            case 11:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeUInt32(var9, var17.getInt(var1, var12));
               }
               break;
            case 12:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeEnum(var9, var17.getInt(var1, var12));
               }
               break;
            case 13:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeSFixed32(var9, var17.getInt(var1, var12));
               }
               break;
            case 14:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeSFixed64(var9, var17.getLong(var1, var12));
               }
               break;
            case 15:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeSInt32(var9, var17.getInt(var1, var12));
               }
               break;
            case 16:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeSInt64(var9, var17.getLong(var1, var12));
               }
               break;
            case 17:
               if (this.isFieldPresent((T)var1, var6, var4, var3, var18)) {
                  var2.writeGroup(var9, var17.getObject(var1, var12), this.getMessageFieldSchema(var6));
               }
               break;
            case 18:
               SchemaUtil.writeDoubleList(this.numberAt(var6), (List<Double>)var17.getObject(var1, var12), var2, false);
               break;
            case 19:
               SchemaUtil.writeFloatList(this.numberAt(var6), (List<Float>)var17.getObject(var1, var12), var2, false);
               break;
            case 20:
               SchemaUtil.writeInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, false);
               break;
            case 21:
               SchemaUtil.writeUInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, false);
               break;
            case 22:
               SchemaUtil.writeInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 23:
               SchemaUtil.writeFixed64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, false);
               break;
            case 24:
               SchemaUtil.writeFixed32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 25:
               SchemaUtil.writeBoolList(this.numberAt(var6), (List<Boolean>)var17.getObject(var1, var12), var2, false);
               break;
            case 26:
               SchemaUtil.writeStringList(this.numberAt(var6), (List<String>)var17.getObject(var1, var12), var2);
               break;
            case 27:
               SchemaUtil.writeMessageList(this.numberAt(var6), (List<?>)var17.getObject(var1, var12), var2, this.getMessageFieldSchema(var6));
               break;
            case 28:
               SchemaUtil.writeBytesList(this.numberAt(var6), (List<ByteString>)var17.getObject(var1, var12), var2);
               break;
            case 29:
               SchemaUtil.writeUInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 30:
               SchemaUtil.writeEnumList(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 31:
               SchemaUtil.writeSFixed32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 32:
               SchemaUtil.writeSFixed64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, false);
               break;
            case 33:
               SchemaUtil.writeSInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, false);
               break;
            case 34:
               SchemaUtil.writeSInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, false);
               break;
            case 35:
               SchemaUtil.writeDoubleList(this.numberAt(var6), (List<Double>)var17.getObject(var1, var12), var2, true);
               break;
            case 36:
               SchemaUtil.writeFloatList(this.numberAt(var6), (List<Float>)var17.getObject(var1, var12), var2, true);
               break;
            case 37:
               SchemaUtil.writeInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, true);
               break;
            case 38:
               SchemaUtil.writeUInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, true);
               break;
            case 39:
               SchemaUtil.writeInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 40:
               SchemaUtil.writeFixed64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, true);
               break;
            case 41:
               SchemaUtil.writeFixed32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 42:
               SchemaUtil.writeBoolList(this.numberAt(var6), (List<Boolean>)var17.getObject(var1, var12), var2, true);
               break;
            case 43:
               SchemaUtil.writeUInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 44:
               SchemaUtil.writeEnumList(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 45:
               SchemaUtil.writeSFixed32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 46:
               SchemaUtil.writeSFixed64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, true);
               break;
            case 47:
               SchemaUtil.writeSInt32List(this.numberAt(var6), (List<Integer>)var17.getObject(var1, var12), var2, true);
               break;
            case 48:
               SchemaUtil.writeSInt64List(this.numberAt(var6), (List<Long>)var17.getObject(var1, var12), var2, true);
               break;
            case 49:
               SchemaUtil.writeGroupList(this.numberAt(var6), (List<?>)var17.getObject(var1, var12), var2, this.getMessageFieldSchema(var6));
               break;
            case 50:
               this.writeMapHelper(var2, var9, var17.getObject(var1, var12), var6);
               break;
            case 51:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeDouble(var9, oneofDoubleAt(var1, var12));
               }
               break;
            case 52:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeFloat(var9, oneofFloatAt(var1, var12));
               }
               break;
            case 53:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeInt64(var9, oneofLongAt(var1, var12));
               }
               break;
            case 54:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeUInt64(var9, oneofLongAt(var1, var12));
               }
               break;
            case 55:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeInt32(var9, oneofIntAt(var1, var12));
               }
               break;
            case 56:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeFixed64(var9, oneofLongAt(var1, var12));
               }
               break;
            case 57:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeFixed32(var9, oneofIntAt(var1, var12));
               }
               break;
            case 58:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeBool(var9, oneofBooleanAt(var1, var12));
               }
               break;
            case 59:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  this.writeString(var9, var17.getObject(var1, var12), var2);
               }
               break;
            case 60:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeMessage(var9, var17.getObject(var1, var12), this.getMessageFieldSchema(var6));
               }
               break;
            case 61:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeBytes(var9, (ByteString)var17.getObject(var1, var12));
               }
               break;
            case 62:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeUInt32(var9, oneofIntAt(var1, var12));
               }
               break;
            case 63:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeEnum(var9, oneofIntAt(var1, var12));
               }
               break;
            case 64:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeSFixed32(var9, oneofIntAt(var1, var12));
               }
               break;
            case 65:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeSFixed64(var9, oneofLongAt(var1, var12));
               }
               break;
            case 66:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeSInt32(var9, oneofIntAt(var1, var12));
               }
               break;
            case 67:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeSInt64(var9, oneofLongAt(var1, var12));
               }
               break;
            case 68:
               if (this.isOneofPresent((T)var1, var9, var6)) {
                  var2.writeGroup(var9, var17.getObject(var1, var12), this.getMessageFieldSchema(var6));
               }
         }
      }

      while (var19 != null) {
         this.extensionSchema.serializeExtension(var2, var19);
         if (var15.hasNext()) {
            var19 = (Entry)var15.next();
         } else {
            var19 = null;
         }
      }

      this.writeUnknownInMessageTo(this.unknownFieldSchema, (T)var1, var2);
   }

   private void writeFieldsInDescendingOrder(T var1, Writer var2) throws IOException {
      Entry var7;
      Iterator var8;
      label188: {
         this.writeUnknownInMessageTo(this.unknownFieldSchema, (T)var1, var2);
         if (this.hasExtensions) {
            FieldSet var6 = this.extensionSchema.getExtensions(var1);
            if (!var6.isEmpty()) {
               var8 = var6.descendingIterator();
               var7 = (Entry)var8.next();
               break label188;
            }
         }

         var8 = null;
         var7 = null;
      }

      int var3 = this.buffer.length - 3;

      while (true) {
         Entry var9 = var7;
         if (var3 < 0) {
            while (var9 != null) {
               this.extensionSchema.serializeExtension(var2, var9);
               if (var8.hasNext()) {
                  var9 = (Entry)var8.next();
               } else {
                  var9 = null;
               }
            }

            return;
         }

         int var5 = this.typeAndOffsetAt(var3);
         int var4 = this.numberAt(var3);

         while (var7 != null && this.extensionSchema.extensionNumber(var7) > var4) {
            this.extensionSchema.serializeExtension(var2, var7);
            if (var8.hasNext()) {
               var7 = (Entry)var8.next();
            } else {
               var7 = null;
            }
         }

         switch (type(var5)) {
            case 0:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeDouble(var4, doubleAt(var1, offset(var5)));
               }
               break;
            case 1:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeFloat(var4, floatAt(var1, offset(var5)));
               }
               break;
            case 2:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeInt64(var4, longAt(var1, offset(var5)));
               }
               break;
            case 3:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeUInt64(var4, longAt(var1, offset(var5)));
               }
               break;
            case 4:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeInt32(var4, intAt(var1, offset(var5)));
               }
               break;
            case 5:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeFixed64(var4, longAt(var1, offset(var5)));
               }
               break;
            case 6:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeFixed32(var4, intAt(var1, offset(var5)));
               }
               break;
            case 7:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeBool(var4, booleanAt(var1, offset(var5)));
               }
               break;
            case 8:
               if (this.isFieldPresent((T)var1, var3)) {
                  this.writeString(var4, UnsafeUtil.getObject(var1, offset(var5)), var2);
               }
               break;
            case 9:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeMessage(var4, UnsafeUtil.getObject(var1, offset(var5)), this.getMessageFieldSchema(var3));
               }
               break;
            case 10:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeBytes(var4, (ByteString)UnsafeUtil.getObject(var1, offset(var5)));
               }
               break;
            case 11:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeUInt32(var4, intAt(var1, offset(var5)));
               }
               break;
            case 12:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeEnum(var4, intAt(var1, offset(var5)));
               }
               break;
            case 13:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeSFixed32(var4, intAt(var1, offset(var5)));
               }
               break;
            case 14:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeSFixed64(var4, longAt(var1, offset(var5)));
               }
               break;
            case 15:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeSInt32(var4, intAt(var1, offset(var5)));
               }
               break;
            case 16:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeSInt64(var4, longAt(var1, offset(var5)));
               }
               break;
            case 17:
               if (this.isFieldPresent((T)var1, var3)) {
                  var2.writeGroup(var4, UnsafeUtil.getObject(var1, offset(var5)), this.getMessageFieldSchema(var3));
               }
               break;
            case 18:
               SchemaUtil.writeDoubleList(this.numberAt(var3), (List<Double>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 19:
               SchemaUtil.writeFloatList(this.numberAt(var3), (List<Float>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 20:
               SchemaUtil.writeInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 21:
               SchemaUtil.writeUInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 22:
               SchemaUtil.writeInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 23:
               SchemaUtil.writeFixed64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 24:
               SchemaUtil.writeFixed32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 25:
               SchemaUtil.writeBoolList(this.numberAt(var3), (List<Boolean>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 26:
               SchemaUtil.writeStringList(this.numberAt(var3), (List<String>)UnsafeUtil.getObject(var1, offset(var5)), var2);
               break;
            case 27:
               SchemaUtil.writeMessageList(this.numberAt(var3), (List<?>)UnsafeUtil.getObject(var1, offset(var5)), var2, this.getMessageFieldSchema(var3));
               break;
            case 28:
               SchemaUtil.writeBytesList(this.numberAt(var3), (List<ByteString>)UnsafeUtil.getObject(var1, offset(var5)), var2);
               break;
            case 29:
               SchemaUtil.writeUInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 30:
               SchemaUtil.writeEnumList(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 31:
               SchemaUtil.writeSFixed32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 32:
               SchemaUtil.writeSFixed64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 33:
               SchemaUtil.writeSInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 34:
               SchemaUtil.writeSInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, false);
               break;
            case 35:
               SchemaUtil.writeDoubleList(this.numberAt(var3), (List<Double>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 36:
               SchemaUtil.writeFloatList(this.numberAt(var3), (List<Float>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 37:
               SchemaUtil.writeInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 38:
               SchemaUtil.writeUInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 39:
               SchemaUtil.writeInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 40:
               SchemaUtil.writeFixed64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 41:
               SchemaUtil.writeFixed32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 42:
               SchemaUtil.writeBoolList(this.numberAt(var3), (List<Boolean>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 43:
               SchemaUtil.writeUInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 44:
               SchemaUtil.writeEnumList(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 45:
               SchemaUtil.writeSFixed32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 46:
               SchemaUtil.writeSFixed64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 47:
               SchemaUtil.writeSInt32List(this.numberAt(var3), (List<Integer>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 48:
               SchemaUtil.writeSInt64List(this.numberAt(var3), (List<Long>)UnsafeUtil.getObject(var1, offset(var5)), var2, true);
               break;
            case 49:
               SchemaUtil.writeGroupList(this.numberAt(var3), (List<?>)UnsafeUtil.getObject(var1, offset(var5)), var2, this.getMessageFieldSchema(var3));
               break;
            case 50:
               this.writeMapHelper(var2, var4, UnsafeUtil.getObject(var1, offset(var5)), var3);
               break;
            case 51:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeDouble(var4, oneofDoubleAt(var1, offset(var5)));
               }
               break;
            case 52:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeFloat(var4, oneofFloatAt(var1, offset(var5)));
               }
               break;
            case 53:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeInt64(var4, oneofLongAt(var1, offset(var5)));
               }
               break;
            case 54:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeUInt64(var4, oneofLongAt(var1, offset(var5)));
               }
               break;
            case 55:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeInt32(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 56:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeFixed64(var4, oneofLongAt(var1, offset(var5)));
               }
               break;
            case 57:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeFixed32(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 58:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeBool(var4, oneofBooleanAt(var1, offset(var5)));
               }
               break;
            case 59:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  this.writeString(var4, UnsafeUtil.getObject(var1, offset(var5)), var2);
               }
               break;
            case 60:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeMessage(var4, UnsafeUtil.getObject(var1, offset(var5)), this.getMessageFieldSchema(var3));
               }
               break;
            case 61:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeBytes(var4, (ByteString)UnsafeUtil.getObject(var1, offset(var5)));
               }
               break;
            case 62:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeUInt32(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 63:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeEnum(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 64:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeSFixed32(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 65:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeSFixed64(var4, oneofLongAt(var1, offset(var5)));
               }
               break;
            case 66:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeSInt32(var4, oneofIntAt(var1, offset(var5)));
               }
               break;
            case 67:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeSInt64(var4, oneofLongAt(var1, offset(var5)));
               }
               break;
            case 68:
               if (this.isOneofPresent((T)var1, var4, var3)) {
                  var2.writeGroup(var4, UnsafeUtil.getObject(var1, offset(var5)), this.getMessageFieldSchema(var3));
               }
         }

         var3 -= 3;
      }
   }

   private <K, V> void writeMapHelper(Writer var1, int var2, Object var3, int var4) throws IOException {
      if (var3 != null) {
         var1.writeMap(var2, this.mapFieldSchema.forMapMetadata(this.getMapFieldDefaultEntry(var4)), this.mapFieldSchema.forMapData(var3));
      }
   }

   private void writeString(int var1, Object var2, Writer var3) throws IOException {
      if (var2 instanceof String) {
         var3.writeString(var1, (String)var2);
      } else {
         var3.writeBytes(var1, (ByteString)var2);
      }
   }

   private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> var1, T var2, Writer var3) throws IOException {
      var1.writeTo(var1.getFromMessage(var2), var3);
   }

   @Override
   public boolean equals(T var1, T var2) {
      int var4 = this.buffer.length;

      for (byte var3 = 0; var3 < var4; var3 += 3) {
         if (!this.equals((T)var1, (T)var2, var3)) {
            return false;
         }
      }

      if (!this.unknownFieldSchema.getFromMessage(var1).equals(this.unknownFieldSchema.getFromMessage(var2))) {
         return false;
      } else {
         return this.hasExtensions ? this.extensionSchema.getExtensions(var1).equals(this.extensionSchema.getExtensions(var2)) : true;
      }
   }

   int getSchemaSize() {
      return this.buffer.length * 3;
   }

   @Override
   public int getSerializedSize(T var1) {
      Unsafe var15 = UNSAFE;
      int var2 = 1048575;
      int var3 = 0;
      byte var8 = 0;
      int var5 = 0;

      while (var8 < this.buffer.length) {
         int var12 = this.typeAndOffsetAt(var8);
         int var11 = type(var12);
         int var10 = this.numberAt(var8);
         int var6 = this.buffer[var8 + 2];
         int var9 = var6 & 1048575;
         int var7;
         if (var11 <= 17) {
            int var4 = var2;
            if (var9 != var2) {
               if (var9 == 1048575) {
                  var2 = 0;
               } else {
                  var2 = var15.getInt(var1, var9);
               }

               var4 = var9;
               var3 = var2;
            }

            var2 = 1 << (var6 >>> 20);
            var7 = var4;
            var6 = var3;
            var3 = var2;
         } else {
            var6 = var3;
            var3 = 0;
            var7 = var2;
         }

         long var13 = offset(var12);
         if (var11 < FieldType.DOUBLE_LIST_PACKED.id() || var11 > FieldType.SINT64_LIST_PACKED.id()) {
            var9 = 0;
         }

         int var28;
         label329: {
            label358: {
               label326: {
                  label325: {
                     switch (var11) {
                        case 0:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeDoubleSize(var10, 0.0);
                           break label326;
                        case 1:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeFloatSize(var10, 0.0F);
                           break label326;
                        case 2:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeInt64Size(var10, var15.getLong(var1, var13));
                           break label326;
                        case 3:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeUInt64Size(var10, var15.getLong(var1, var13));
                           break label326;
                        case 4:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeInt32Size(var10, var15.getInt(var1, var13));
                           break label326;
                        case 5:
                           var28 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label329;
                           }

                           var2 = CodedOutputStream.computeFixed64Size(var10, 0L);
                           break label326;
                        case 6:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeFixed32Size(var10, 0);
                           break label326;
                        case 7:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeBoolSize(var10, true);
                           break label325;
                        case 8:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           Object var30 = var15.getObject(var1, var13);
                           if (var30 instanceof ByteString) {
                              var2 = CodedOutputStream.computeBytesSize(var10, (ByteString)var30);
                           } else {
                              var2 = CodedOutputStream.computeStringSize(var10, (String)var30);
                           }
                           break label325;
                        case 9:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = SchemaUtil.computeSizeMessage(var10, var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        case 10:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeBytesSize(var10, (ByteString)var15.getObject(var1, var13));
                           break label325;
                        case 11:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeUInt32Size(var10, var15.getInt(var1, var13));
                           break label325;
                        case 12:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeEnumSize(var10, var15.getInt(var1, var13));
                           break label325;
                        case 13:
                           var2 = var5;
                           if (this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              var2 = var5 + CodedOutputStream.computeSFixed32Size(var10, 0);
                           }
                           break label358;
                        case 14:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSFixed64Size(var10, 0L);
                           break label325;
                        case 15:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSInt32Size(var10, var15.getInt(var1, var13));
                           break label325;
                        case 16:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSInt64Size(var10, var15.getLong(var1, var13));
                           break label325;
                        case 17:
                           var2 = var5;
                           if (!this.isFieldPresent((T)var1, var8, var7, var6, var3)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeGroupSize(var10, (MessageLite)var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        case 18:
                           var2 = SchemaUtil.computeSizeFixed64List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 19:
                           var2 = SchemaUtil.computeSizeFixed32List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 20:
                           var2 = SchemaUtil.computeSizeInt64List(var10, (List<Long>)var15.getObject(var1, var13), false);
                           break label325;
                        case 21:
                           var2 = SchemaUtil.computeSizeUInt64List(var10, (List<Long>)var15.getObject(var1, var13), false);
                           break label325;
                        case 22:
                           var2 = SchemaUtil.computeSizeInt32List(var10, (List<Integer>)var15.getObject(var1, var13), false);
                           break label325;
                        case 23:
                           var2 = SchemaUtil.computeSizeFixed64List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 24:
                           var2 = SchemaUtil.computeSizeFixed32List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 25:
                           var2 = SchemaUtil.computeSizeBoolList(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 26:
                           var2 = SchemaUtil.computeSizeStringList(var10, (List<?>)var15.getObject(var1, var13));
                           break label325;
                        case 27:
                           var2 = SchemaUtil.computeSizeMessageList(var10, (List<?>)var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        case 28:
                           var2 = SchemaUtil.computeSizeByteStringList(var10, (List<ByteString>)var15.getObject(var1, var13));
                           break label325;
                        case 29:
                           var2 = SchemaUtil.computeSizeUInt32List(var10, (List<Integer>)var15.getObject(var1, var13), false);
                           break label325;
                        case 30:
                           var2 = SchemaUtil.computeSizeEnumList(var10, (List<Integer>)var15.getObject(var1, var13), false);
                           break label325;
                        case 31:
                           var2 = SchemaUtil.computeSizeFixed32List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 32:
                           var2 = SchemaUtil.computeSizeFixed64List(var10, (List<?>)var15.getObject(var1, var13), false);
                           break label325;
                        case 33:
                           var2 = SchemaUtil.computeSizeSInt32List(var10, (List<Integer>)var15.getObject(var1, var13), false);
                           break label325;
                        case 34:
                           var2 = SchemaUtil.computeSizeSInt64List(var10, (List<Long>)var15.getObject(var1, var13), false);
                           break label325;
                        case 35:
                           var3 = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 36:
                           var3 = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 37:
                           var3 = SchemaUtil.computeSizeInt64ListNoTag((List<Long>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 38:
                           var3 = SchemaUtil.computeSizeUInt64ListNoTag((List<Long>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 39:
                           var3 = SchemaUtil.computeSizeInt32ListNoTag((List<Integer>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 40:
                           var3 = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 41:
                           var3 = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 42:
                           var3 = SchemaUtil.computeSizeBoolListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 43:
                           var3 = SchemaUtil.computeSizeUInt32ListNoTag((List<Integer>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 44:
                           var3 = SchemaUtil.computeSizeEnumListNoTag((List<Integer>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 45:
                           var3 = SchemaUtil.computeSizeFixed32ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 46:
                           var3 = SchemaUtil.computeSizeFixed64ListNoTag((List<?>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 47:
                           var3 = SchemaUtil.computeSizeSInt32ListNoTag((List<Integer>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 48:
                           var3 = SchemaUtil.computeSizeSInt64ListNoTag((List<Long>)var15.getObject(var1, var13));
                           var2 = var5;
                           if (var3 <= 0) {
                              break label358;
                           }

                           if (this.useCachedSizeField) {
                              var15.putInt(var1, var9, var3);
                           }

                           var2 = CodedOutputStream.computeTagSize(var10);
                           var28 = CodedOutputStream.computeUInt32SizeNoTag(var3);
                           break;
                        case 49:
                           var2 = SchemaUtil.computeSizeGroupList(var10, (List<MessageLite>)var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        case 50:
                           var2 = this.mapFieldSchema.getSerializedSize(var10, var15.getObject(var1, var13), this.getMapFieldDefaultEntry(var8));
                           break label325;
                        case 51:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeDoubleSize(var10, 0.0);
                           break label325;
                        case 52:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeFloatSize(var10, 0.0F);
                           break label325;
                        case 53:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeInt64Size(var10, oneofLongAt(var1, var13));
                           break label325;
                        case 54:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeUInt64Size(var10, oneofLongAt(var1, var13));
                           break label325;
                        case 55:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeInt32Size(var10, oneofIntAt(var1, var13));
                           break label325;
                        case 56:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeFixed64Size(var10, 0L);
                           break label325;
                        case 57:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeFixed32Size(var10, 0);
                           break label325;
                        case 58:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeBoolSize(var10, true);
                           break label325;
                        case 59:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           Object var16 = var15.getObject(var1, var13);
                           if (var16 instanceof ByteString) {
                              var2 = CodedOutputStream.computeBytesSize(var10, (ByteString)var16);
                           } else {
                              var2 = CodedOutputStream.computeStringSize(var10, (String)var16);
                           }
                           break label325;
                        case 60:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = SchemaUtil.computeSizeMessage(var10, var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        case 61:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeBytesSize(var10, (ByteString)var15.getObject(var1, var13));
                           break label325;
                        case 62:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeUInt32Size(var10, oneofIntAt(var1, var13));
                           break label325;
                        case 63:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeEnumSize(var10, oneofIntAt(var1, var13));
                           break label325;
                        case 64:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSFixed32Size(var10, 0);
                           break label325;
                        case 65:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSFixed64Size(var10, 0L);
                           break label325;
                        case 66:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSInt32Size(var10, oneofIntAt(var1, var13));
                           break label325;
                        case 67:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeSInt64Size(var10, oneofLongAt(var1, var13));
                           break label325;
                        case 68:
                           var2 = var5;
                           if (!this.isOneofPresent((T)var1, var10, var8)) {
                              break label358;
                           }

                           var2 = CodedOutputStream.computeGroupSize(var10, (MessageLite)var15.getObject(var1, var13), this.getMessageFieldSchema(var8));
                           break label325;
                        default:
                           var2 = var5;
                           break label358;
                     }

                     var2 = var2 + var28 + var3;
                  }

                  var2 = var5 + var2;
                  break label358;
               }

               var28 = var5 + var2;
               break label329;
            }

            var28 = var2;
         }

         var8 += 3;
         var2 = var7;
         var3 = var6;
         var5 = var28;
      }

      var3 = var5 + this.getUnknownFieldsSerializedSize(this.unknownFieldSchema, (T)var1);
      var2 = var3;
      if (this.hasExtensions) {
         var2 = var3 + this.extensionSchema.getExtensions(var1).getSerializedSize();
      }

      return var2;
   }

   @Override
   public int hashCode(T var1) {
      int var5 = this.buffer.length;
      byte var4 = 0;
      int var3 = 0;

      while (var4 < var5) {
         int var12;
         label120: {
            label119: {
               var12 = this.typeAndOffsetAt(var4);
               int var6 = this.numberAt(var4);
               long var8 = offset(var12);
               int var7 = type(var12);
               var12 = 37;
               switch (var7) {
                  case 0:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(var1, var8)));
                     break;
                  case 1:
                     var12 = var3 * 53;
                     var3 = Float.floatToIntBits(UnsafeUtil.getFloat(var1, var8));
                     break;
                  case 2:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(UnsafeUtil.getLong(var1, var8));
                     break;
                  case 3:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(UnsafeUtil.getLong(var1, var8));
                     break;
                  case 4:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 5:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(UnsafeUtil.getLong(var1, var8));
                     break;
                  case 6:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 7:
                     var12 = var3 * 53;
                     var3 = Internal.hashBoolean(UnsafeUtil.getBoolean(var1, var8));
                     break;
                  case 8:
                     var12 = var3 * 53;
                     var3 = ((String)UnsafeUtil.getObject(var1, var8)).hashCode();
                     break;
                  case 9:
                     Object var19 = UnsafeUtil.getObject(var1, var8);
                     if (var19 != null) {
                        var12 = var19.hashCode();
                     }
                     break label119;
                  case 10:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getObject(var1, var8).hashCode();
                     break;
                  case 11:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 12:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 13:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 14:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(UnsafeUtil.getLong(var1, var8));
                     break;
                  case 15:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getInt(var1, var8);
                     break;
                  case 16:
                     var12 = var3 * 53;
                     var3 = Internal.hashLong(UnsafeUtil.getLong(var1, var8));
                     break;
                  case 17:
                     Object var18 = UnsafeUtil.getObject(var1, var8);
                     if (var18 != null) {
                        var12 = var18.hashCode();
                     }
                     break label119;
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 48:
                  case 49:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getObject(var1, var8).hashCode();
                     break;
                  case 50:
                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getObject(var1, var8).hashCode();
                     break;
                  case 51:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(var1, var8)));
                     break;
                  case 52:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Float.floatToIntBits(oneofFloatAt(var1, var8));
                     break;
                  case 53:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(oneofLongAt(var1, var8));
                     break;
                  case 54:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(oneofLongAt(var1, var8));
                     break;
                  case 55:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 56:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(oneofLongAt(var1, var8));
                     break;
                  case 57:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 58:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashBoolean(oneofBooleanAt(var1, var8));
                     break;
                  case 59:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = ((String)UnsafeUtil.getObject(var1, var8)).hashCode();
                     break;
                  case 60:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     Object var17 = UnsafeUtil.getObject(var1, var8);
                     var12 = var3 * 53;
                     var3 = var17.hashCode();
                     break;
                  case 61:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = UnsafeUtil.getObject(var1, var8).hashCode();
                     break;
                  case 62:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 63:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 64:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 65:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(oneofLongAt(var1, var8));
                     break;
                  case 66:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = oneofIntAt(var1, var8);
                     break;
                  case 67:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     var12 = var3 * 53;
                     var3 = Internal.hashLong(oneofLongAt(var1, var8));
                     break;
                  case 68:
                     var12 = var3;
                     if (!this.isOneofPresent((T)var1, var6, var4)) {
                        break label120;
                     }

                     Object var10 = UnsafeUtil.getObject(var1, var8);
                     var12 = var3 * 53;
                     var3 = var10.hashCode();
                     break;
                  default:
                     var12 = var3;
                     break label120;
               }

               var12 += var3;
               break label120;
            }

            var12 = var3 * 53 + var12;
         }

         var4 += 3;
         var3 = var12;
      }

      var3 = var3 * 53 + this.unknownFieldSchema.getFromMessage(var1).hashCode();
      int var14 = var3;
      if (this.hasExtensions) {
         var14 = var3 * 53 + this.extensionSchema.getExtensions(var1).hashCode();
      }

      return var14;
   }

   @Override
   public final boolean isInitialized(T var1) {
      int var3 = 1048575;
      int var2 = 0;

      for (int var4 = 0; var4 < this.checkInitializedCount; var4++) {
         int var7 = this.intArray[var4];
         int var6 = this.numberAt(var7);
         int var8 = this.typeAndOffsetAt(var7);
         int var9 = this.buffer[var7 + 2];
         int var5 = var9 & 1048575;
         var9 = 1 << (var9 >>> 20);
         if (var5 != var3) {
            if (var5 != 1048575) {
               var2 = UNSAFE.getInt(var1, var5);
            }

            var3 = var5;
         }

         if (isRequired(var8) && !this.isFieldPresent((T)var1, var7, var3, var2, var9)) {
            return false;
         }

         var5 = type(var8);
         if (var5 != 9 && var5 != 17) {
            if (var5 != 27) {
               if (var5 == 60 || var5 == 68) {
                  if (this.isOneofPresent((T)var1, var6, var7) && !isInitialized(var1, var8, this.getMessageFieldSchema(var7))) {
                     return false;
                  }
                  continue;
               }

               if (var5 != 49) {
                  if (var5 == 50 && !this.isMapInitialized((T)var1, var8, var7)) {
                     return false;
                  }
                  continue;
               }
            }

            if (!this.isListInitialized(var1, var8, var7)) {
               return false;
            }
         } else if (this.isFieldPresent((T)var1, var7, var3, var2, var9) && !isInitialized(var1, var8, this.getMessageFieldSchema(var7))) {
            return false;
         }
      }

      return !this.hasExtensions || this.extensionSchema.getExtensions(var1).isInitialized();
   }

   @Override
   public void makeImmutable(T var1) {
      if (isMutable(var1)) {
         if (var1 instanceof GeneratedMessageLite) {
            GeneratedMessageLite var7 = (GeneratedMessageLite)var1;
            var7.clearMemoizedSerializedSize();
            var7.clearMemoizedHashCode();
            var7.markImmutable();
         }

         int var3 = this.buffer.length;

         for (byte var2 = 0; var2 < var3; var2 += 3) {
            int var4 = this.typeAndOffsetAt(var2);
            long var5 = offset(var4);
            var4 = type(var4);
            if (var4 != 9) {
               if (var4 == 60 || var4 == 68) {
                  if (this.isOneofPresent((T)var1, this.numberAt(var2), var2)) {
                     this.getMessageFieldSchema(var2).makeImmutable(UNSAFE.getObject(var1, var5));
                  }
                  continue;
               }

               switch (var4) {
                  case 17:
                     break;
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 48:
                  case 49:
                     this.listFieldSchema.makeImmutableListAt(var1, var5);
                     continue;
                  case 50:
                     Unsafe var10 = UNSAFE;
                     Object var8 = var10.getObject(var1, var5);
                     if (var8 != null) {
                        var10.putObject(var1, var5, this.mapFieldSchema.toImmutable(var8));
                     }
                  default:
                     continue;
               }
            }

            if (this.isFieldPresent((T)var1, var2)) {
               this.getMessageFieldSchema(var2).makeImmutable(UNSAFE.getObject(var1, var5));
            }
         }

         this.unknownFieldSchema.makeImmutable(var1);
         if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(var1);
         }
      }
   }

   @Override
   public void mergeFrom(T var1, Reader var2, ExtensionRegistryLite var3) throws IOException {
      var3.getClass();
      checkMutable(var1);
      this.mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, (T)var1, var2, var3);
   }

   @Override
   public void mergeFrom(T var1, T var2) {
      checkMutable(var1);

      for (byte var3 = 0; var3 < this.buffer.length; var3 += 3) {
         this.mergeSingleField((T)var1, (T)var2, var3);
      }

      SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, var1, var2);
      if (this.hasExtensions) {
         SchemaUtil.mergeExtensions(this.extensionSchema, var1, var2);
      }
   }

   @Override
   public void mergeFrom(T var1, byte[] var2, int var3, int var4, ArrayDecoders.Registers var5) throws IOException {
      this.parseMessage((T)var1, var2, var3, var4, 0, var5);
   }

   @Override
   public T newInstance() {
      return (T)this.newInstanceSchema.newInstance(this.defaultInstance);
   }

   int parseMessage(T var1, byte[] var2, int var3, int var4, int var5, ArrayDecoders.Registers var6) throws IOException {
      checkMutable(var1);
      Unsafe var21 = UNSAFE;
      int var7 = var3;
      int var11 = -1;
      int var12 = 0;
      int var9 = 0;
      var3 = 0;
      int var8 = 1048575;

      while (true) {
         if (var7 >= var4) {
            var7 = var9;
            var9 = var3;
            var3 = var7;
            break;
         }

         int var13 = var7 + 1;
         var7 = var2[var7];
         if (var7 < 0) {
            var13 = ArrayDecoders.decodeVarint32(var7, var2, var13, var6);
            var7 = var6.int1;
         }

         int var10 = var7 >>> 3;
         int var15 = var7 & 7;
         if (var10 > var11) {
            var9 = this.positionForFieldNumber(var10, var12 / 3);
         } else {
            var9 = this.positionForFieldNumber(var10);
         }

         int var14;
         label223: {
            label222: {
               label248: {
                  label245: {
                     label217: {
                        label246: {
                           if (var9 == -1) {
                              var11 = var13;
                              var3 = var8;
                              byte var61 = 0;
                              var8 = var3;
                              var9 = var61;
                           } else {
                              int var16 = this.buffer[var9 + 1];
                              int var17 = type(var16);
                              long var19 = offset(var16);
                              label204:
                              if (var17 <= 17) {
                                 var11 = this.buffer[var9 + 2];
                                 boolean var18 = true;
                                 var14 = 1 << (var11 >>> 20);
                                 var12 = var11 & 1048575;
                                 if (var12 != var8) {
                                    if (var8 != 1048575) {
                                       var21.putInt(var1, var8, var3);
                                    }

                                    if (var12 == 1048575) {
                                       var11 = 0;
                                       var8 = var12;
                                    } else {
                                       var11 = var21.getInt(var1, var12);
                                       var8 = var12;
                                    }
                                 } else {
                                    var11 = var3;
                                 }

                                 label201: {
                                    switch (var17) {
                                       case 0:
                                          if (var15 == 1) {
                                             UnsafeUtil.putDouble(var1, var19, ArrayDecoders.decodeDouble(var2, var13));
                                             break label201;
                                          }
                                          break;
                                       case 1:
                                          if (var15 == 5) {
                                             UnsafeUtil.putFloat(var1, var19, ArrayDecoders.decodeFloat(var2, var13));
                                             var3 = var13 + 4;
                                             break label217;
                                          }
                                          break;
                                       case 2:
                                       case 3:
                                          if (var15 == 0) {
                                             var3 = ArrayDecoders.decodeVarint64(var2, var13, var6);
                                             var21.putLong(var1, var19, var6.long1);
                                             var12 = var9;
                                             var9 = var7;
                                             var13 = var11 | var14;
                                             var7 = var3;
                                             var11 = var10;
                                             var3 = var13;
                                             continue;
                                          }
                                          break;
                                       case 4:
                                       case 11:
                                          if (var15 == 0) {
                                             var3 = ArrayDecoders.decodeVarint32(var2, var13, var6);
                                             var21.putInt(var1, var19, var6.int1);
                                             break label217;
                                          }
                                          break;
                                       case 5:
                                       case 14:
                                          if (var15 == 1) {
                                             var21.putLong(var1, var19, ArrayDecoders.decodeFixed64(var2, var13));
                                             break label201;
                                          }
                                          break;
                                       case 6:
                                       case 13:
                                          if (var15 == 5) {
                                             var21.putInt(var1, var19, ArrayDecoders.decodeFixed32(var2, var13));
                                             var3 = var13 + 4;
                                             var13 = var11 | var14;
                                             break label222;
                                          }
                                          break;
                                       case 7:
                                          if (var15 == 0) {
                                             var12 = ArrayDecoders.decodeVarint64(var2, var13, var6);
                                             if (var6.long1 == 0L) {
                                                var18 = false;
                                             }

                                             UnsafeUtil.putBoolean(var1, var19, var18);
                                             var13 = var11 | var14;
                                             var7 = var12;
                                             var11 = var10;
                                             var12 = var9;
                                             var9 = var7;
                                             var3 = var13;
                                             continue;
                                          }
                                          break;
                                       case 8:
                                          if (var15 == 2) {
                                             if (isEnforceUtf8(var16)) {
                                                var3 = ArrayDecoders.decodeStringRequireUtf8(var2, var13, var6);
                                             } else {
                                                var3 = ArrayDecoders.decodeString(var2, var13, var6);
                                             }

                                             var21.putObject(var1, var19, var6.object1);
                                             break label223;
                                          }
                                          break;
                                       case 9:
                                          if (var15 == 2) {
                                             Object var73 = this.mutableMessageFieldForMerge((T)var1, var9);
                                             var3 = ArrayDecoders.mergeMessageField(var73, this.getMessageFieldSchema(var9), var2, var13, var4, var6);
                                             this.storeMessageField((T)var1, var9, var73);
                                             break label223;
                                          }
                                          break;
                                       case 10:
                                          if (var15 == 2) {
                                             var3 = ArrayDecoders.decodeBytes(var2, var13, var6);
                                             var21.putObject(var1, var19, var6.object1);
                                             break label223;
                                          }
                                          break;
                                       case 12:
                                          if (var15 == 0) {
                                             var3 = ArrayDecoders.decodeVarint32(var2, var13, var6);
                                             var12 = var6.int1;
                                             Internal.EnumVerifier var72 = this.getEnumFieldVerifier(var9);
                                             if (!isLegacyEnumIsClosed(var16) || var72 == null || var72.isInRange(var12)) {
                                                var21.putInt(var1, var19, var12);
                                                break label223;
                                             }

                                             getMutableUnknownFields(var1).storeField(var7, (long)var12);
                                             var13 = var11;
                                             break label222;
                                          }
                                          break;
                                       case 15:
                                          if (var15 == 0) {
                                             var3 = ArrayDecoders.decodeVarint32(var2, var13, var6);
                                             var21.putInt(var1, var19, CodedInputStream.decodeZigZag32(var6.int1));
                                             break label223;
                                          }
                                          break;
                                       case 16:
                                          if (var15 == 0) {
                                             var12 = ArrayDecoders.decodeVarint64(var2, var13, var6);
                                             var21.putLong(var1, var19, CodedInputStream.decodeZigZag64(var6.long1));
                                             var13 = var11 | var14;
                                             var7 = var12;
                                             var11 = var10;
                                             var12 = var9;
                                             var9 = var7;
                                             var3 = var13;
                                             continue;
                                          }
                                          break;
                                       case 17:
                                          if (var15 == 3) {
                                             Object var71 = this.mutableMessageFieldForMerge((T)var1, var9);
                                             var3 = ArrayDecoders.mergeGroupField(
                                                var71, this.getMessageFieldSchema(var9), var2, var13, var4, var10 << 3 | 4, var6
                                             );
                                             this.storeMessageField((T)var1, var9, var71);
                                             var11 |= var14;
                                             var13 = var8;
                                             var8 = var3;
                                             var3 = var11;
                                             break label248;
                                          }
                                    }

                                    var3 = var8;
                                    var8 = var11;
                                    var11 = var13;
                                    break label204;
                                 }

                                 var3 = var13 + 8;
                                 break label217;
                              } else {
                                 label213: {
                                    if (var17 == 27) {
                                       if (var15 == 2) {
                                          Internal.ProtobufList var23 = (Internal.ProtobufList)var21.getObject(var1, var19);
                                          Internal.ProtobufList var22 = var23;
                                          if (!var23.isModifiable()) {
                                             var11 = var23.size();
                                             if (var11 == 0) {
                                                var11 = 10;
                                             } else {
                                                var11 *= 2;
                                             }

                                             var22 = var23.mutableCopyWithCapacity(var11);
                                             var21.putObject(var1, var19, var22);
                                          }

                                          var11 = ArrayDecoders.decodeMessageList(this.getMessageFieldSchema(var9), var7, var2, var13, var4, var22, var6);
                                          var13 = var8;
                                          var8 = var11;
                                          break label248;
                                       }
                                    } else {
                                       if (var17 <= 49) {
                                          var12 = this.parseRepeatedField((T)var1, var2, var13, var4, var7, var10, var15, var9, var16, var17, var19, var6);
                                          var11 = var12;
                                          if (var12 != var13) {
                                             var11 = var12;
                                             break label246;
                                          }
                                          break label213;
                                       }

                                       if (var17 != 50) {
                                          var12 = this.parseOneofField((T)var1, var2, var13, var4, var7, var10, var15, var16, var17, var19, var9, var6);
                                          var11 = var12;
                                          if (var12 != var13) {
                                             var11 = var12;
                                             break label246;
                                          }
                                          break label213;
                                       }

                                       if (var15 == 2) {
                                          var12 = this.parseMapField((T)var1, var2, var13, var4, var9, var19, var6);
                                          var11 = var12;
                                          if (var12 != var13) {
                                             var11 = var12;
                                             break label246;
                                          }
                                          break label213;
                                       }
                                    }

                                    var11 = var13;
                                 }

                                 var8 = var3;
                                 var3 = var8;
                              }
                           }

                           if (var7 == var5 && var5 != 0) {
                              var9 = var8;
                              var8 = var3;
                              var3 = var11;
                              break;
                           }

                           if (this.hasExtensions && var6.extensionRegistry != ExtensionRegistryLite.getEmptyRegistry()) {
                              var11 = ArrayDecoders.decodeExtensionOrUnknownField(
                                 var7,
                                 var2,
                                 var11,
                                 var4,
                                 var1,
                                 this.defaultInstance,
                                 (UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite>)this.unknownFieldSchema,
                                 var6
                              );
                           } else {
                              var11 = ArrayDecoders.decodeUnknownField(var7, var2, var11, var4, getMutableUnknownFields(var1), var6);
                           }

                           var13 = var7;
                           var7 = var11;
                           break label245;
                        }

                        var3 = var8;
                        var8 = var3;
                        var13 = var7;
                        var7 = var11;
                        break label245;
                     }

                     var13 = var11 | var14;
                     break label222;
                  }

                  var11 = var10;
                  var12 = var9;
                  var9 = var13;
                  var3 = var8;
                  var8 = var3;
                  continue;
               }

               var7 = var8;
               var11 = var10;
               var12 = var9;
               var9 = var7;
               var8 = var13;
               continue;
            }

            var12 = var9;
            var9 = var7;
            var7 = var3;
            var11 = var10;
            var3 = var13;
            continue;
         }

         var14 = var11 | var14;
         var7 = var3;
         var11 = var10;
         var12 = var9;
         var9 = var7;
         var3 = var14;
      }

      MessageSchema var33 = this;
      if (var8 != 1048575) {
         var21.putInt(var1, var8, var9);
      }

      var8 = this.checkInitializedCount;

      for (var24 = null; var8 < var33.repeatedFieldOffsetStart; var8++) {
         var24 = this.filterMapUnknownEnumValues(var1, var33.intArray[var8], var24, (UnknownFieldSchema<?, UnknownFieldSetLite>)var33.unknownFieldSchema, var1);
      }

      if (var24 != null) {
         ((UnknownFieldSchema<?, UnknownFieldSetLite>)var33.unknownFieldSchema).setBuilderToMessage(var1, var24);
      }

      if (var5 == 0) {
         if (var3 != var4) {
            throw InvalidProtocolBufferException.parseFailure();
         }
      } else if (var3 > var4 || var7 != var5) {
         throw InvalidProtocolBufferException.parseFailure();
      }

      return var3;
   }

   @Override
   public void writeTo(T var1, Writer var2) throws IOException {
      if (var2.fieldOrder() == Writer.FieldOrder.DESCENDING) {
         this.writeFieldsInDescendingOrder((T)var1, var2);
      } else {
         this.writeFieldsInAscendingOrder((T)var1, var2);
      }
   }
}
