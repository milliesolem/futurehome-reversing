package com.google.protobuf;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

@CheckReturnValue
final class SchemaUtil {
   private static final int DEFAULT_LOOK_UP_START_NUMBER = 40;
   private static final Class<?> GENERATED_MESSAGE_CLASS = getGeneratedMessageClass();
   private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_FULL_SCHEMA = getUnknownFieldSetSchema();
   private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();

   private SchemaUtil() {
   }

   static int computeSizeBoolList(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3)
            : var3 * CodedOutputStream.computeBoolSize(var0, true);
      }
   }

   static int computeSizeBoolListNoTag(List<?> var0) {
      return var0.size();
   }

   static int computeSizeByteStringList(int var0, List<ByteString> var1) {
      int var3 = var1.size();
      int var2 = 0;
      if (var3 == 0) {
         return 0;
      } else {
         var3 *= CodedOutputStream.computeTagSize(var0);
         var0 = var2;

         for (var2 = var3; var0 < var1.size(); var0++) {
            var2 += CodedOutputStream.computeBytesSizeNoTag((ByteString)var1.get(var0));
         }

         return var2;
      }
   }

   static int computeSizeEnumList(int var0, List<Integer> var1, boolean var2) {
      int var4 = var1.size();
      if (var4 == 0) {
         return 0;
      } else {
         int var3 = computeSizeEnumListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3)
            : var3 + var4 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeEnumListNoTag(List<Integer> var0) {
      int var4 = var0.size();
      int var3 = 0;
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof IntArrayList) {
            IntArrayList var5 = (IntArrayList)var0;
            int var1 = 0;
            var3 = var2;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeEnumSizeNoTag(var5.getInt(var3));
               var3++;
            }
         } else {
            int var6 = 0;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeEnumSizeNoTag((Integer)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeFixed32List(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3 * 4)
            : var3 * CodedOutputStream.computeFixed32Size(var0, 0);
      }
   }

   static int computeSizeFixed32ListNoTag(List<?> var0) {
      return var0.size() * 4;
   }

   static int computeSizeFixed64List(int var0, List<?> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3 * 8)
            : var3 * CodedOutputStream.computeFixed64Size(var0, 0L);
      }
   }

   static int computeSizeFixed64ListNoTag(List<?> var0) {
      return var0.size() * 8;
   }

   static int computeSizeGroupList(int var0, List<MessageLite> var1) {
      int var4 = var1.size();
      int var3 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         int var2;
         for (var2 = 0; var3 < var4; var3++) {
            var2 += CodedOutputStream.computeGroupSize(var0, (MessageLite)var1.get(var3));
         }

         return var2;
      }
   }

   static int computeSizeGroupList(int var0, List<MessageLite> var1, Schema var2) {
      int var5 = var1.size();
      int var3 = 0;
      if (var5 == 0) {
         return 0;
      } else {
         int var4;
         for (var4 = 0; var3 < var5; var3++) {
            var4 += CodedOutputStream.computeGroupSize(var0, (MessageLite)var1.get(var3), var2);
         }

         return var4;
      }
   }

   static int computeSizeInt32List(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         int var4 = computeSizeInt32ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var4)
            : var4 + var3 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeInt32ListNoTag(List<Integer> var0) {
      int var4 = var0.size();
      int var3 = 0;
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof IntArrayList) {
            IntArrayList var5 = (IntArrayList)var0;
            int var1 = 0;
            var3 = var2;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeInt32SizeNoTag(var5.getInt(var3));
               var3++;
            }
         } else {
            int var6 = 0;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeInt32SizeNoTag((Integer)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeInt64List(int var0, List<Long> var1, boolean var2) {
      if (var1.size() == 0) {
         return 0;
      } else {
         int var3 = computeSizeInt64ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3)
            : var3 + var1.size() * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeInt64ListNoTag(List<Long> var0) {
      int var4 = var0.size();
      int var2 = 0;
      int var3 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof LongArrayList) {
            LongArrayList var5 = (LongArrayList)var0;
            int var1 = 0;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeInt64SizeNoTag(var5.getLong(var3));
               var3++;
            }
         } else {
            int var6 = 0;
            var3 = var2;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeInt64SizeNoTag((Long)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeMessage(int var0, Object var1, Schema var2) {
      return var1 instanceof LazyFieldLite
         ? CodedOutputStream.computeLazyFieldSize(var0, (LazyFieldLite)var1)
         : CodedOutputStream.computeMessageSize(var0, (MessageLite)var1, var2);
   }

   static int computeSizeMessageList(int var0, List<?> var1) {
      int var4 = var1.size();
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         for (var0 = CodedOutputStream.computeTagSize(var0) * var4; var2 < var4; var2++) {
            Object var5 = var1.get(var2);
            int var3;
            if (var5 instanceof LazyFieldLite) {
               var3 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite)var5);
            } else {
               var3 = CodedOutputStream.computeMessageSizeNoTag((MessageLite)var5);
            }

            var0 += var3;
         }

         return var0;
      }
   }

   static int computeSizeMessageList(int var0, List<?> var1, Schema var2) {
      int var5 = var1.size();
      int var4 = 0;
      if (var5 == 0) {
         return 0;
      } else {
         int var3 = CodedOutputStream.computeTagSize(var0) * var5;

         for (int var7 = var4; var7 < var5; var7++) {
            Object var6 = var1.get(var7);
            if (var6 instanceof LazyFieldLite) {
               var4 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite)var6);
            } else {
               var4 = CodedOutputStream.computeMessageSizeNoTag((MessageLite)var6, var2);
            }

            var3 += var4;
         }

         return var3;
      }
   }

   static int computeSizeSInt32List(int var0, List<Integer> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         int var4 = computeSizeSInt32ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var4)
            : var4 + var3 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeSInt32ListNoTag(List<Integer> var0) {
      int var4 = var0.size();
      int var3 = 0;
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof IntArrayList) {
            IntArrayList var5 = (IntArrayList)var0;
            int var1 = 0;
            var3 = var2;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeSInt32SizeNoTag(var5.getInt(var3));
               var3++;
            }
         } else {
            int var6 = 0;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeSInt32SizeNoTag((Integer)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeSInt64List(int var0, List<Long> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         int var4 = computeSizeSInt64ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var4)
            : var4 + var3 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeSInt64ListNoTag(List<Long> var0) {
      int var4 = var0.size();
      int var2 = 0;
      int var3 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof LongArrayList) {
            LongArrayList var5 = (LongArrayList)var0;
            int var1 = 0;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeSInt64SizeNoTag(var5.getLong(var3));
               var3++;
            }
         } else {
            int var6 = 0;
            var3 = var2;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeSInt64SizeNoTag((Long)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeStringList(int var0, List<?> var1) {
      int var5 = var1.size();
      int var2 = 0;
      byte var4 = 0;
      if (var5 == 0) {
         return 0;
      } else {
         int var3 = CodedOutputStream.computeTagSize(var0) * var5;
         var0 = var3;
         if (var1 instanceof LazyStringList) {
            LazyStringList var9 = (LazyStringList)var1;
            var0 = var3;
            var2 = var4;

            while (true) {
               var3 = var0;
               if (var2 >= var5) {
                  break;
               }

               Object var14 = var9.getRaw(var2);
               if (var14 instanceof ByteString) {
                  var3 = CodedOutputStream.computeBytesSizeNoTag((ByteString)var14);
               } else {
                  var3 = CodedOutputStream.computeStringSizeNoTag((String)var14);
               }

               var0 += var3;
               var2++;
            }
         } else {
            while (true) {
               var3 = var0;
               if (var2 >= var5) {
                  break;
               }

               Object var6 = var1.get(var2);
               if (var6 instanceof ByteString) {
                  var3 = CodedOutputStream.computeBytesSizeNoTag((ByteString)var6);
               } else {
                  var3 = CodedOutputStream.computeStringSizeNoTag((String)var6);
               }

               var0 += var3;
               var2++;
            }
         }

         return var3;
      }
   }

   static int computeSizeUInt32List(int var0, List<Integer> var1, boolean var2) {
      int var4 = var1.size();
      if (var4 == 0) {
         return 0;
      } else {
         int var3 = computeSizeUInt32ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var3)
            : var3 + var4 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeUInt32ListNoTag(List<Integer> var0) {
      int var4 = var0.size();
      int var3 = 0;
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof IntArrayList) {
            IntArrayList var5 = (IntArrayList)var0;
            int var1 = 0;
            var3 = var2;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeUInt32SizeNoTag(var5.getInt(var3));
               var3++;
            }
         } else {
            int var6 = 0;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeUInt32SizeNoTag((Integer)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static int computeSizeUInt64List(int var0, List<Long> var1, boolean var2) {
      int var3 = var1.size();
      if (var3 == 0) {
         return 0;
      } else {
         int var4 = computeSizeUInt64ListNoTag(var1);
         return var2
            ? CodedOutputStream.computeTagSize(var0) + CodedOutputStream.computeLengthDelimitedFieldSize(var4)
            : var4 + var3 * CodedOutputStream.computeTagSize(var0);
      }
   }

   static int computeSizeUInt64ListNoTag(List<Long> var0) {
      int var4 = var0.size();
      int var3 = 0;
      int var2 = 0;
      if (var4 == 0) {
         return 0;
      } else {
         if (var0 instanceof LongArrayList) {
            LongArrayList var5 = (LongArrayList)var0;
            int var1 = 0;
            var3 = var2;

            while (true) {
               var2 = var1;
               if (var3 >= var4) {
                  break;
               }

               var1 += CodedOutputStream.computeUInt64SizeNoTag(var5.getLong(var3));
               var3++;
            }
         } else {
            int var6 = 0;

            while (true) {
               var2 = var6;
               if (var3 >= var4) {
                  break;
               }

               var6 += CodedOutputStream.computeUInt64SizeNoTag((Long)var0.get(var3));
               var3++;
            }
         }

         return var2;
      }
   }

   static <UT, UB> UB filterUnknownEnumList(Object var0, int var1, List<Integer> var2, Internal.EnumLiteMap<?> var3, UB var4, UnknownFieldSchema<UT, UB> var5) {
      if (var3 == null) {
         return (UB)var4;
      } else {
         Object var13;
         if (var2 instanceof RandomAccess) {
            int var8 = var2.size();
            int var6 = 0;

            int var7;
            for (var7 = 0; var6 < var8; var6++) {
               var13 = (Integer)var2.get(var6);
               int var9 = var13;
               if (var3.findValueByNumber(var9) != null) {
                  if (var6 != var7) {
                     var2.set(var7, var13);
                  }

                  var7++;
               } else {
                  var4 = storeUnknownEnum(var0, var1, var9, var4, var5);
               }
            }

            var13 = (Integer)var4;
            if (var7 != var8) {
               var2.subList(var7, var8).clear();
               var13 = (Integer)var4;
            }
         } else {
            Iterator var11 = var2.iterator();

            while (true) {
               var13 = (Integer)var4;
               if (!var11.hasNext()) {
                  break;
               }

               int var12 = (Integer)var11.next();
               if (var3.findValueByNumber(var12) == null) {
                  var4 = storeUnknownEnum(var0, var1, var12, var4, var5);
                  var11.remove();
               }
            }
         }

         return (UB)var13;
      }
   }

   static <UT, UB> UB filterUnknownEnumList(Object var0, int var1, List<Integer> var2, Internal.EnumVerifier var3, UB var4, UnknownFieldSchema<UT, UB> var5) {
      if (var3 == null) {
         return (UB)var4;
      } else {
         Object var13;
         if (var2 instanceof RandomAccess) {
            int var8 = var2.size();
            int var6 = 0;

            int var7;
            for (var7 = 0; var6 < var8; var6++) {
               var13 = (Integer)var2.get(var6);
               int var9 = var13;
               if (var3.isInRange(var9)) {
                  if (var6 != var7) {
                     var2.set(var7, var13);
                  }

                  var7++;
               } else {
                  var4 = storeUnknownEnum(var0, var1, var9, var4, var5);
               }
            }

            var13 = (Integer)var4;
            if (var7 != var8) {
               var2.subList(var7, var8).clear();
               var13 = (Integer)var4;
            }
         } else {
            Iterator var11 = var2.iterator();

            while (true) {
               var13 = (Integer)var4;
               if (!var11.hasNext()) {
                  break;
               }

               int var12 = (Integer)var11.next();
               if (!var3.isInRange(var12)) {
                  var4 = storeUnknownEnum(var0, var1, var12, var4, var5);
                  var11.remove();
               }
            }
         }

         return (UB)var13;
      }
   }

   private static Class<?> getGeneratedMessageClass() {
      try {
         return Class.forName("com.google.protobuf.GeneratedMessageV3");
      } finally {
         ;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static Object getMapDefaultEntry(Class<?> var0, String var1) {
      try {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0.getName());
         var2.append("$");
         var2.append(toCamelCase(var1, true));
         var2.append("DefaultEntryHolder");
         java.lang.reflect.Field[] var6 = Class.forName(var2.toString()).getDeclaredFields();
         if (var6.length == 1) {
            return UnsafeUtil.getStaticObject(var6[0]);
         } else {
            StringBuilder var3 = new StringBuilder("Unable to look up map field default entry holder class for ");
            var3.append(var1);
            var3.append(" in ");
            var3.append(var0.getName());
            IllegalStateException var7 = new IllegalStateException(var3.toString());
            throw var7;
         }
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   private static UnknownFieldSchema<?, ?> getUnknownFieldSetSchema() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 2 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1051)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:501)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic com/google/protobuf/SchemaUtil.getUnknownFieldSetSchemaClass ()Ljava/lang/Class;
      // 03: astore 0
      // 04: aload 0
      // 05: ifnonnull 0a
      // 08: aconst_null
      // 09: areturn
      // 0a: aload 0
      // 0b: aconst_null
      // 0c: invokevirtual java/lang/Class.getConstructor ([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
      // 0f: aconst_null
      // 10: invokevirtual java/lang/reflect/Constructor.newInstance ([Ljava/lang/Object;)Ljava/lang/Object;
      // 13: checkcast com/google/protobuf/UnknownFieldSchema
      // 16: astore 0
      // 17: aload 0
      // 18: areturn
      // 19: astore 0
      // 1a: aconst_null
      // 1b: areturn
   }

   private static Class<?> getUnknownFieldSetSchemaClass() {
      try {
         return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
      } finally {
         ;
      }
   }

   static <T, FT extends FieldSet.FieldDescriptorLite<FT>> void mergeExtensions(ExtensionSchema<FT> var0, T var1, T var2) {
      var2 = var0.getExtensions(var2);
      if (!var2.isEmpty()) {
         var0.getMutableExtensions(var1).mergeFrom(var2);
      }
   }

   static <T> void mergeMap(MapFieldSchema var0, T var1, T var2, long var3) {
      UnsafeUtil.putObject(var1, var3, var0.mergeFrom(UnsafeUtil.getObject(var1, var3), UnsafeUtil.getObject(var2, var3)));
   }

   static <T, UT, UB> void mergeUnknownFields(UnknownFieldSchema<UT, UB> var0, T var1, T var2) {
      var0.setToMessage(var1, var0.merge(var0.getFromMessage(var1), var0.getFromMessage(var2)));
   }

   public static void requireGeneratedMessage(Class<?> var0) {
      if (!GeneratedMessageLite.class.isAssignableFrom(var0)) {
         Class var1 = GENERATED_MESSAGE_CLASS;
         if (var1 != null && !var1.isAssignableFrom(var0)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessageV3 or GeneratedMessageLite");
         }
      }
   }

   static boolean safeEquals(Object var0, Object var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var0.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static boolean shouldUseTableSwitch(int var0, int var1, int var2) {
      boolean var9 = true;
      if (var1 < 40) {
         return true;
      } else {
         long var7 = var1;
         long var5 = var0;
         long var3 = var2;
         if (var7 - var5 + 10L > 2L * var3 + 3L + (var3 + 3L) * 3L) {
            var9 = false;
         }

         return var9;
      }
   }

   public static boolean shouldUseTableSwitch(FieldInfo[] var0) {
      return var0.length == 0 ? false : shouldUseTableSwitch(var0[0].getFieldNumber(), var0[var0.length - 1].getFieldNumber(), var0.length);
   }

   static <UT, UB> UB storeUnknownEnum(Object var0, int var1, int var2, UB var3, UnknownFieldSchema<UT, UB> var4) {
      Object var5 = var3;
      if (var3 == null) {
         var5 = var4.getBuilderFromMessage(var0);
      }

      var4.addVarint(var5, var1, var2);
      return (UB)var5;
   }

   static String toCamelCase(String var0, boolean var1) {
      StringBuilder var4 = new StringBuilder();

      for (int var3 = 0; var3 < var0.length(); var3++) {
         char var2 = var0.charAt(var3);
         if ('a' <= var2 && var2 <= 'z') {
            if (var1) {
               var4.append((char)(var2 - ' '));
            } else {
               var4.append(var2);
            }
         } else {
            if ('A' > var2 || var2 > 'Z') {
               if ('0' <= var2 && var2 <= '9') {
                  var4.append(var2);
               }

               var1 = true;
               continue;
            }

            if (var3 == 0 && !var1) {
               var4.append((char)(var2 + ' '));
            } else {
               var4.append(var2);
            }
         }

         var1 = false;
      }

      return var4.toString();
   }

   public static UnknownFieldSchema<?, ?> unknownFieldSetFullSchema() {
      return UNKNOWN_FIELD_SET_FULL_SCHEMA;
   }

   public static UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema() {
      return UNKNOWN_FIELD_SET_LITE_SCHEMA;
   }

   public static void writeBool(int var0, boolean var1, Writer var2) throws IOException {
      if (var1) {
         var2.writeBool(var0, true);
      }
   }

   public static void writeBoolList(int var0, List<Boolean> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeBoolList(var0, var1, var3);
      }
   }

   public static void writeBytes(int var0, ByteString var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeBytes(var0, var1);
      }
   }

   public static void writeBytesList(int var0, List<ByteString> var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeBytesList(var0, var1);
      }
   }

   public static void writeDouble(int var0, double var1, Writer var3) throws IOException {
      if (Double.doubleToRawLongBits(var1) != 0L) {
         var3.writeDouble(var0, var1);
      }
   }

   public static void writeDoubleList(int var0, List<Double> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeDoubleList(var0, var1, var3);
      }
   }

   public static void writeEnum(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeEnum(var0, var1);
      }
   }

   public static void writeEnumList(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeEnumList(var0, var1, var3);
      }
   }

   public static void writeFixed32(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeFixed32(var0, var1);
      }
   }

   public static void writeFixed32List(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeFixed32List(var0, var1, var3);
      }
   }

   public static void writeFixed64(int var0, long var1, Writer var3) throws IOException {
      if (var1 != 0L) {
         var3.writeFixed64(var0, var1);
      }
   }

   public static void writeFixed64List(int var0, List<Long> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeFixed64List(var0, var1, var3);
      }
   }

   public static void writeFloat(int var0, float var1, Writer var2) throws IOException {
      if (Float.floatToRawIntBits(var1) != 0) {
         var2.writeFloat(var0, var1);
      }
   }

   public static void writeFloatList(int var0, List<Float> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeFloatList(var0, var1, var3);
      }
   }

   public static void writeGroupList(int var0, List<?> var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeGroupList(var0, var1);
      }
   }

   public static void writeGroupList(int var0, List<?> var1, Writer var2, Schema var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeGroupList(var0, var1, var3);
      }
   }

   public static void writeInt32(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeInt32(var0, var1);
      }
   }

   public static void writeInt32List(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeInt32List(var0, var1, var3);
      }
   }

   public static void writeInt64(int var0, long var1, Writer var3) throws IOException {
      if (var1 != 0L) {
         var3.writeInt64(var0, var1);
      }
   }

   public static void writeInt64List(int var0, List<Long> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeInt64List(var0, var1, var3);
      }
   }

   public static void writeLazyFieldList(int var0, List<?> var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         while (var3.hasNext()) {
            ((LazyFieldLite)var3.next()).writeTo(var2, var0);
         }
      }
   }

   public static void writeMessage(int var0, Object var1, Writer var2) throws IOException {
      if (var1 != null) {
         var2.writeMessage(var0, var1);
      }
   }

   public static void writeMessageList(int var0, List<?> var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeMessageList(var0, var1);
      }
   }

   public static void writeMessageList(int var0, List<?> var1, Writer var2, Schema var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeMessageList(var0, var1, var3);
      }
   }

   public static void writeSFixed32(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeSFixed32(var0, var1);
      }
   }

   public static void writeSFixed32List(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeSFixed32List(var0, var1, var3);
      }
   }

   public static void writeSFixed64(int var0, long var1, Writer var3) throws IOException {
      if (var1 != 0L) {
         var3.writeSFixed64(var0, var1);
      }
   }

   public static void writeSFixed64List(int var0, List<Long> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeSFixed64List(var0, var1, var3);
      }
   }

   public static void writeSInt32(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeSInt32(var0, var1);
      }
   }

   public static void writeSInt32List(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeSInt32List(var0, var1, var3);
      }
   }

   public static void writeSInt64(int var0, long var1, Writer var3) throws IOException {
      if (var1 != 0L) {
         var3.writeSInt64(var0, var1);
      }
   }

   public static void writeSInt64List(int var0, List<Long> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeSInt64List(var0, var1, var3);
      }
   }

   public static void writeString(int var0, Object var1, Writer var2) throws IOException {
      if (var1 instanceof String) {
         writeStringInternal(var0, (String)var1, var2);
      } else {
         writeBytes(var0, (ByteString)var1, var2);
      }
   }

   private static void writeStringInternal(int var0, String var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeString(var0, var1);
      }
   }

   public static void writeStringList(int var0, List<String> var1, Writer var2) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeStringList(var0, var1);
      }
   }

   public static void writeUInt32(int var0, int var1, Writer var2) throws IOException {
      if (var1 != 0) {
         var2.writeUInt32(var0, var1);
      }
   }

   public static void writeUInt32List(int var0, List<Integer> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeUInt32List(var0, var1, var3);
      }
   }

   public static void writeUInt64(int var0, long var1, Writer var3) throws IOException {
      if (var1 != 0L) {
         var3.writeUInt64(var0, var1);
      }
   }

   public static void writeUInt64List(int var0, List<Long> var1, Writer var2, boolean var3) throws IOException {
      if (var1 != null && !var1.isEmpty()) {
         var2.writeUInt64List(var0, var1, var3);
      }
   }
}
