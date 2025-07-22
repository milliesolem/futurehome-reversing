package com.google.protobuf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

@CheckReturnValue
abstract class BinaryWriter extends ByteOutput implements Writer {
   public static final int DEFAULT_CHUNK_SIZE = 4096;
   private static final int MAP_KEY_NUMBER = 1;
   private static final int MAP_VALUE_NUMBER = 2;
   private final BufferAllocator alloc;
   final ArrayDeque<AllocatedBuffer> buffers = new ArrayDeque<>(4);
   private final int chunkSize;
   int totalDoneBytes;

   private BinaryWriter(BufferAllocator var1, int var2) {
      if (var2 > 0) {
         this.alloc = Internal.checkNotNull(var1, "alloc");
         this.chunkSize = var2;
      } else {
         throw new IllegalArgumentException("chunkSize must be > 0");
      }
   }

   private static byte computeUInt64SizeNoTag(long var0) {
      if ((-128L & var0) == 0L) {
         return 1;
      } else if (var0 < 0L) {
         return 10;
      } else {
         byte var3;
         if ((-34359738368L & var0) != 0L) {
            var3 = 6;
            var0 >>>= 28;
         } else {
            var3 = 2;
         }

         byte var2 = var3;
         long var4 = var0;
         if ((-2097152L & var0) != 0L) {
            var2 = (byte)(var3 + 2);
            var4 = var0 >>> 14;
         }

         var3 = var2;
         if ((var4 & -16384L) != 0L) {
            var3 = (byte)(var2 + 1);
         }

         return var3;
      }
   }

   static boolean isUnsafeDirectSupported() {
      return BinaryWriter.UnsafeDirectWriter.isSupported();
   }

   static boolean isUnsafeHeapSupported() {
      return BinaryWriter.UnsafeHeapWriter.isSupported();
   }

   public static BinaryWriter newDirectInstance(BufferAllocator var0) {
      return newDirectInstance(var0, 4096);
   }

   public static BinaryWriter newDirectInstance(BufferAllocator var0, int var1) {
      BinaryWriter var2;
      if (isUnsafeDirectSupported()) {
         var2 = newUnsafeDirectInstance(var0, var1);
      } else {
         var2 = newSafeDirectInstance(var0, var1);
      }

      return var2;
   }

   public static BinaryWriter newHeapInstance(BufferAllocator var0) {
      return newHeapInstance(var0, 4096);
   }

   public static BinaryWriter newHeapInstance(BufferAllocator var0, int var1) {
      BinaryWriter var2;
      if (isUnsafeHeapSupported()) {
         var2 = newUnsafeHeapInstance(var0, var1);
      } else {
         var2 = newSafeHeapInstance(var0, var1);
      }

      return var2;
   }

   static BinaryWriter newSafeDirectInstance(BufferAllocator var0, int var1) {
      return new BinaryWriter.SafeDirectWriter(var0, var1);
   }

   static BinaryWriter newSafeHeapInstance(BufferAllocator var0, int var1) {
      return new BinaryWriter.SafeHeapWriter(var0, var1);
   }

   static BinaryWriter newUnsafeDirectInstance(BufferAllocator var0, int var1) {
      if (isUnsafeDirectSupported()) {
         return new BinaryWriter.UnsafeDirectWriter(var0, var1);
      } else {
         throw new UnsupportedOperationException("Unsafe operations not supported");
      }
   }

   static BinaryWriter newUnsafeHeapInstance(BufferAllocator var0, int var1) {
      if (isUnsafeHeapSupported()) {
         return new BinaryWriter.UnsafeHeapWriter(var0, var1);
      } else {
         throw new UnsupportedOperationException("Unsafe operations not supported");
      }
   }

   private void writeBoolList_Internal(int var1, BooleanArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeBool(var2.getBoolean(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeBool(var1, var2.getBoolean(var6));
         }
      }
   }

   private void writeBoolList_Internal(int var1, List<Boolean> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeBool((Boolean)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeBool(var1, (Boolean)var2.get(var6));
         }
      }
   }

   private void writeDoubleList_Internal(int var1, DoubleArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 8 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed64(Double.doubleToRawLongBits(var2.getDouble(var4)));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeDouble(var1, var2.getDouble(var6));
         }
      }
   }

   private void writeDoubleList_Internal(int var1, List<Double> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 8 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed64(Double.doubleToRawLongBits((Double)var2.get(var4)));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeDouble(var1, (Double)var2.get(var6));
         }
      }
   }

   private void writeFixed32List_Internal(int var1, IntArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 4 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed32(var2.getInt(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFixed32(var1, var2.getInt(var6));
         }
      }
   }

   private void writeFixed32List_Internal(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 4 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed32((Integer)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFixed32(var1, (Integer)var2.get(var6));
         }
      }
   }

   private void writeFixed64List_Internal(int var1, LongArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 8 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed64(var2.getLong(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFixed64(var1, var2.getLong(var6));
         }
      }
   }

   private void writeFixed64List_Internal(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 8 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed64((Long)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFixed64(var1, (Long)var2.get(var6));
         }
      }
   }

   private void writeFloatList_Internal(int var1, FloatArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 4 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed32(Float.floatToRawIntBits(var2.getFloat(var4)));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFloat(var1, var2.getFloat(var6));
         }
      }
   }

   private void writeFloatList_Internal(int var1, List<Float> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 4 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeFixed32(Float.floatToRawIntBits((Float)var2.get(var4)));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeFloat(var1, (Float)var2.get(var6));
         }
      }
   }

   private void writeInt32List_Internal(int var1, IntArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeInt32(var2.getInt(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeInt32(var1, var2.getInt(var6));
         }
      }
   }

   private void writeInt32List_Internal(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeInt32((Integer)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeInt32(var1, (Integer)var2.get(var6));
         }
      }
   }

   private void writeLazyString(int var1, Object var2) throws IOException {
      if (var2 instanceof String) {
         this.writeString(var1, (String)var2);
      } else {
         this.writeBytes(var1, (ByteString)var2);
      }
   }

   static final void writeMapEntryField(Writer var0, int var1, WireFormat.FieldType var2, Object var3) throws IOException {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var2.ordinal()]) {
         case 1:
            var0.writeBool(var1, (Boolean)var3);
            break;
         case 2:
            var0.writeFixed32(var1, (Integer)var3);
            break;
         case 3:
            var0.writeFixed64(var1, (Long)var3);
            break;
         case 4:
            var0.writeInt32(var1, (Integer)var3);
            break;
         case 5:
            var0.writeInt64(var1, (Long)var3);
            break;
         case 6:
            var0.writeSFixed32(var1, (Integer)var3);
            break;
         case 7:
            var0.writeSFixed64(var1, (Long)var3);
            break;
         case 8:
            var0.writeSInt32(var1, (Integer)var3);
            break;
         case 9:
            var0.writeSInt64(var1, (Long)var3);
            break;
         case 10:
            var0.writeString(var1, (String)var3);
            break;
         case 11:
            var0.writeUInt32(var1, (Integer)var3);
            break;
         case 12:
            var0.writeUInt64(var1, (Long)var3);
            break;
         case 13:
            var0.writeFloat(var1, (Float)var3);
            break;
         case 14:
            var0.writeDouble(var1, (Double)var3);
            break;
         case 15:
            var0.writeMessage(var1, var3);
            break;
         case 16:
            var0.writeBytes(var1, (ByteString)var3);
            break;
         case 17:
            if (var3 instanceof Internal.EnumLite) {
               var0.writeEnum(var1, ((Internal.EnumLite)var3).getNumber());
            } else {
               if (!(var3 instanceof Integer)) {
                  throw new IllegalArgumentException("Unexpected type for enum in map.");
               }

               var0.writeEnum(var1, (Integer)var3);
            }
            break;
         default:
            StringBuilder var4 = new StringBuilder("Unsupported map value type for: ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString());
      }
   }

   private void writeSInt32List_Internal(int var1, IntArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 5 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeSInt32(var2.getInt(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeSInt32(var1, var2.getInt(var6));
         }
      }
   }

   private void writeSInt32List_Internal(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 5 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeSInt32((Integer)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeSInt32(var1, (Integer)var2.get(var6));
         }
      }
   }

   private void writeSInt64List_Internal(int var1, LongArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeSInt64(var2.getLong(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeSInt64(var1, var2.getLong(var6));
         }
      }
   }

   private void writeSInt64List_Internal(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeSInt64((Long)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeSInt64(var1, (Long)var2.get(var6));
         }
      }
   }

   private void writeUInt32List_Internal(int var1, IntArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 5 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeVarint32(var2.getInt(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeUInt32(var1, var2.getInt(var6));
         }
      }
   }

   private void writeUInt32List_Internal(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 5 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeVarint32((Integer)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeUInt32(var1, (Integer)var2.get(var6));
         }
      }
   }

   private void writeUInt64List_Internal(int var1, LongArrayList var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeVarint64(var2.getLong(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeUInt64(var1, var2.getLong(var6));
         }
      }
   }

   private void writeUInt64List_Internal(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var3) {
         this.requireSpace(var2.size() * 10 + 10);
         int var5 = this.getTotalBytesWritten();

         for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
            this.writeVarint64((Long)var2.get(var4));
         }

         this.writeVarint32(this.getTotalBytesWritten() - var5);
         this.writeTag(var1, 2);
      } else {
         for (int var6 = var2.size() - 1; var6 >= 0; var6--) {
            this.writeUInt64(var1, (Long)var2.get(var6));
         }
      }
   }

   public final Queue<AllocatedBuffer> complete() {
      this.finishCurrentBuffer();
      return this.buffers;
   }

   @Override
   public final Writer.FieldOrder fieldOrder() {
      return Writer.FieldOrder.DESCENDING;
   }

   abstract void finishCurrentBuffer();

   public abstract int getTotalBytesWritten();

   final AllocatedBuffer newDirectBuffer() {
      return this.alloc.allocateDirectBuffer(this.chunkSize);
   }

   final AllocatedBuffer newDirectBuffer(int var1) {
      return this.alloc.allocateDirectBuffer(Math.max(var1, this.chunkSize));
   }

   final AllocatedBuffer newHeapBuffer() {
      return this.alloc.allocateHeapBuffer(this.chunkSize);
   }

   final AllocatedBuffer newHeapBuffer(int var1) {
      return this.alloc.allocateHeapBuffer(Math.max(var1, this.chunkSize));
   }

   abstract void requireSpace(int var1);

   abstract void writeBool(boolean var1);

   @Override
   public final void writeBoolList(int var1, List<Boolean> var2, boolean var3) throws IOException {
      if (var2 instanceof BooleanArrayList) {
         this.writeBoolList_Internal(var1, (BooleanArrayList)var2, var3);
      } else {
         this.writeBoolList_Internal(var1, var2, var3);
      }
   }

   @Override
   public final void writeBytesList(int var1, List<ByteString> var2) throws IOException {
      for (int var3 = var2.size() - 1; var3 >= 0; var3--) {
         this.writeBytes(var1, (ByteString)var2.get(var3));
      }
   }

   @Override
   public final void writeDouble(int var1, double var2) throws IOException {
      this.writeFixed64(var1, Double.doubleToRawLongBits(var2));
   }

   @Override
   public final void writeDoubleList(int var1, List<Double> var2, boolean var3) throws IOException {
      if (var2 instanceof DoubleArrayList) {
         this.writeDoubleList_Internal(var1, (DoubleArrayList)var2, var3);
      } else {
         this.writeDoubleList_Internal(var1, var2, var3);
      }
   }

   @Override
   public final void writeEnum(int var1, int var2) throws IOException {
      this.writeInt32(var1, var2);
   }

   @Override
   public final void writeEnumList(int var1, List<Integer> var2, boolean var3) throws IOException {
      this.writeInt32List(var1, var2, var3);
   }

   abstract void writeFixed32(int var1);

   @Override
   public final void writeFixed32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var2 instanceof IntArrayList) {
         this.writeFixed32List_Internal(var1, (IntArrayList)var2, var3);
      } else {
         this.writeFixed32List_Internal(var1, var2, var3);
      }
   }

   abstract void writeFixed64(long var1);

   @Override
   public final void writeFixed64List(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var2 instanceof LongArrayList) {
         this.writeFixed64List_Internal(var1, (LongArrayList)var2, var3);
      } else {
         this.writeFixed64List_Internal(var1, var2, var3);
      }
   }

   @Override
   public final void writeFloat(int var1, float var2) throws IOException {
      this.writeFixed32(var1, Float.floatToRawIntBits(var2));
   }

   @Override
   public final void writeFloatList(int var1, List<Float> var2, boolean var3) throws IOException {
      if (var2 instanceof FloatArrayList) {
         this.writeFloatList_Internal(var1, (FloatArrayList)var2, var3);
      } else {
         this.writeFloatList_Internal(var1, var2, var3);
      }
   }

   @Deprecated
   @Override
   public final void writeGroupList(int var1, List<?> var2) throws IOException {
      for (int var3 = var2.size() - 1; var3 >= 0; var3--) {
         this.writeGroup(var1, var2.get(var3));
      }
   }

   @Deprecated
   @Override
   public final void writeGroupList(int var1, List<?> var2, Schema var3) throws IOException {
      for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
         this.writeGroup(var1, var2.get(var4), var3);
      }
   }

   abstract void writeInt32(int var1);

   @Override
   public final void writeInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var2 instanceof IntArrayList) {
         this.writeInt32List_Internal(var1, (IntArrayList)var2, var3);
      } else {
         this.writeInt32List_Internal(var1, var2, var3);
      }
   }

   @Override
   public final void writeInt64(int var1, long var2) throws IOException {
      this.writeUInt64(var1, var2);
   }

   @Override
   public final void writeInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      this.writeUInt64List(var1, var2, var3);
   }

   @Override
   public <K, V> void writeMap(int var1, MapEntryLite.Metadata<K, V> var2, Map<K, V> var3) throws IOException {
      for (Entry var6 : var3.entrySet()) {
         int var4 = this.getTotalBytesWritten();
         writeMapEntryField(this, 2, var2.valueType, var6.getValue());
         writeMapEntryField(this, 1, var2.keyType, var6.getKey());
         this.writeVarint32(this.getTotalBytesWritten() - var4);
         this.writeTag(var1, 2);
      }
   }

   @Override
   public final void writeMessageList(int var1, List<?> var2) throws IOException {
      for (int var3 = var2.size() - 1; var3 >= 0; var3--) {
         this.writeMessage(var1, var2.get(var3));
      }
   }

   @Override
   public final void writeMessageList(int var1, List<?> var2, Schema var3) throws IOException {
      for (int var4 = var2.size() - 1; var4 >= 0; var4--) {
         this.writeMessage(var1, var2.get(var4), var3);
      }
   }

   @Override
   public final void writeMessageSetItem(int var1, Object var2) throws IOException {
      this.writeTag(1, 4);
      if (var2 instanceof ByteString) {
         this.writeBytes(3, (ByteString)var2);
      } else {
         this.writeMessage(3, var2);
      }

      this.writeUInt32(2, var1);
      this.writeTag(1, 3);
   }

   @Override
   public final void writeSFixed32(int var1, int var2) throws IOException {
      this.writeFixed32(var1, var2);
   }

   @Override
   public final void writeSFixed32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      this.writeFixed32List(var1, var2, var3);
   }

   @Override
   public final void writeSFixed64(int var1, long var2) throws IOException {
      this.writeFixed64(var1, var2);
   }

   @Override
   public final void writeSFixed64List(int var1, List<Long> var2, boolean var3) throws IOException {
      this.writeFixed64List(var1, var2, var3);
   }

   abstract void writeSInt32(int var1);

   @Override
   public final void writeSInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var2 instanceof IntArrayList) {
         this.writeSInt32List_Internal(var1, (IntArrayList)var2, var3);
      } else {
         this.writeSInt32List_Internal(var1, var2, var3);
      }
   }

   abstract void writeSInt64(long var1);

   @Override
   public final void writeSInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var2 instanceof LongArrayList) {
         this.writeSInt64List_Internal(var1, (LongArrayList)var2, var3);
      } else {
         this.writeSInt64List_Internal(var1, var2, var3);
      }
   }

   abstract void writeString(String var1);

   @Override
   public final void writeStringList(int var1, List<String> var2) throws IOException {
      if (var2 instanceof LazyStringList) {
         LazyStringList var4 = (LazyStringList)var2;

         for (int var3 = var2.size() - 1; var3 >= 0; var3--) {
            this.writeLazyString(var1, var4.getRaw(var3));
         }
      } else {
         for (int var5 = var2.size() - 1; var5 >= 0; var5--) {
            this.writeString(var1, (String)var2.get(var5));
         }
      }
   }

   abstract void writeTag(int var1, int var2);

   @Override
   public final void writeUInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      if (var2 instanceof IntArrayList) {
         this.writeUInt32List_Internal(var1, (IntArrayList)var2, var3);
      } else {
         this.writeUInt32List_Internal(var1, var2, var3);
      }
   }

   @Override
   public final void writeUInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      if (var2 instanceof LongArrayList) {
         this.writeUInt64List_Internal(var1, (LongArrayList)var2, var3);
      } else {
         this.writeUInt64List_Internal(var1, var2, var3);
      }
   }

   abstract void writeVarint32(int var1);

   abstract void writeVarint64(long var1);

   private static final class SafeDirectWriter extends BinaryWriter {
      private ByteBuffer buffer;
      private int limitMinusOne;
      private int pos;

      SafeDirectWriter(BufferAllocator var1, int var2) {
         super(var1, var2);
         this.nextBuffer();
      }

      private int bytesWrittenToCurrentBuffer() {
         return this.limitMinusOne - this.pos;
      }

      private void nextBuffer() {
         this.nextBuffer(this.newDirectBuffer());
      }

      private void nextBuffer(int var1) {
         this.nextBuffer(this.newDirectBuffer(var1));
      }

      private void nextBuffer(AllocatedBuffer var1) {
         if (var1.hasNioBuffer()) {
            ByteBuffer var3 = var1.nioBuffer();
            if (var3.isDirect()) {
               this.finishCurrentBuffer();
               this.buffers.addFirst(var1);
               this.buffer = var3;
               Java8Compatibility.limit(var3, var3.capacity());
               Java8Compatibility.position(this.buffer, 0);
               this.buffer.order(ByteOrder.LITTLE_ENDIAN);
               int var2 = this.buffer.limit() - 1;
               this.limitMinusOne = var2;
               this.pos = var2;
            } else {
               throw new RuntimeException("Allocator returned non-direct buffer");
            }
         } else {
            throw new RuntimeException("Allocated buffer does not have NIO buffer");
         }
      }

      private int spaceLeft() {
         return this.pos + 1;
      }

      private void writeVarint32FiveBytes(int var1) {
         ByteBuffer var3 = this.buffer;
         int var2 = this.pos--;
         var3.put(var2, (byte)(var1 >>> 28));
         var2 = this.pos;
         this.pos = var2 - 4;
         this.buffer.putInt(var2 - 3, var1 & 127 | 128 | (var1 >>> 21 & 127 | 128) << 24 | (var1 >>> 14 & 127 | 128) << 16 | (var1 >>> 7 & 127 | 128) << 8);
      }

      private void writeVarint32FourBytes(int var1) {
         int var2 = this.pos;
         this.pos = var2 - 4;
         this.buffer.putInt(var2 - 3, var1 & 127 | 128 | (266338304 & var1) << 3 | (2080768 & var1 | 2097152) << 2 | (var1 & 16256 | 16384) << 1);
      }

      private void writeVarint32OneByte(int var1) {
         ByteBuffer var3 = this.buffer;
         int var2 = this.pos--;
         var3.put(var2, (byte)var1);
      }

      private void writeVarint32ThreeBytes(int var1) {
         int var2 = this.pos - 3;
         this.pos = var2;
         this.buffer.putInt(var2, (var1 & 127 | 128) << 8 | (2080768 & var1) << 10 | (var1 & 16256 | 16384) << 9);
      }

      private void writeVarint32TwoBytes(int var1) {
         int var2 = this.pos;
         this.pos = var2 - 2;
         this.buffer.putShort(var2 - 1, (short)(var1 & 127 | 128 | (var1 & 16256) << 1));
      }

      private void writeVarint64EightBytes(long var1) {
         int var3 = this.pos;
         this.pos = var3 - 8;
         this.buffer
            .putLong(
               var3 - 7,
               var1 & 127L
                  | 128L
                  | (71494644084506624L & var1) << 7
                  | (558551906910208L & var1 | 562949953421312L) << 6
                  | (4363686772736L & var1 | 4398046511104L) << 5
                  | (34091302912L & var1 | 34359738368L) << 4
                  | (266338304L & var1 | 268435456L) << 3
                  | (2080768L & var1 | 2097152L) << 2
                  | (16256L & var1 | 16384L) << 1
            );
      }

      private void writeVarint64EightBytesWithSign(long var1) {
         int var3 = this.pos;
         this.pos = var3 - 8;
         this.buffer
            .putLong(
               var3 - 7,
               var1 & 127L
                  | 128L
                  | (71494644084506624L & var1 | 72057594037927936L) << 7
                  | (558551906910208L & var1 | 562949953421312L) << 6
                  | (4363686772736L & var1 | 4398046511104L) << 5
                  | (34091302912L & var1 | 34359738368L) << 4
                  | (266338304L & var1 | 268435456L) << 3
                  | (2080768L & var1 | 2097152L) << 2
                  | (16256L & var1 | 16384L) << 1
            );
      }

      private void writeVarint64FiveBytes(long var1) {
         int var3 = this.pos;
         this.pos = var3 - 5;
         this.buffer
            .putLong(
               var3 - 7,
               (var1 & 127L | 128L) << 24
                  | (34091302912L & var1) << 28
                  | (266338304L & var1 | 268435456L) << 27
                  | (2080768L & var1 | 2097152L) << 26
                  | (16256L & var1 | 16384L) << 25
            );
      }

      private void writeVarint64FourBytes(long var1) {
         this.writeVarint32FourBytes((int)var1);
      }

      private void writeVarint64NineBytes(long var1) {
         ByteBuffer var4 = this.buffer;
         int var3 = this.pos--;
         var4.put(var3, (byte)(var1 >>> 56));
         this.writeVarint64EightBytesWithSign(var1 & 72057594037927935L);
      }

      private void writeVarint64OneByte(long var1) {
         this.writeVarint32OneByte((int)var1);
      }

      private void writeVarint64SevenBytes(long var1) {
         int var3 = this.pos - 7;
         this.pos = var3;
         this.buffer
            .putLong(
               var3,
               (var1 & 127L | 128L) << 8
                  | (558551906910208L & var1) << 14
                  | (4363686772736L & var1 | 4398046511104L) << 13
                  | (34091302912L & var1 | 34359738368L) << 12
                  | (266338304L & var1 | 268435456L) << 11
                  | (2080768L & var1 | 2097152L) << 10
                  | (16256L & var1 | 16384L) << 9
            );
      }

      private void writeVarint64SixBytes(long var1) {
         int var3 = this.pos;
         this.pos = var3 - 6;
         this.buffer
            .putLong(
               var3 - 7,
               (var1 & 127L | 128L) << 16
                  | (4363686772736L & var1) << 21
                  | (34091302912L & var1 | 34359738368L) << 20
                  | (266338304L & var1 | 268435456L) << 19
                  | (2080768L & var1 | 2097152L) << 18
                  | (16256L & var1 | 16384L) << 17
            );
      }

      private void writeVarint64TenBytes(long var1) {
         ByteBuffer var4 = this.buffer;
         int var3 = this.pos--;
         var4.put(var3, (byte)(var1 >>> 63));
         var4 = this.buffer;
         var3 = this.pos--;
         var4.put(var3, (byte)(var1 >>> 56 & 127L | 128L));
         this.writeVarint64EightBytesWithSign(var1 & 72057594037927935L);
      }

      private void writeVarint64ThreeBytes(long var1) {
         this.writeVarint32ThreeBytes((int)var1);
      }

      private void writeVarint64TwoBytes(long var1) {
         this.writeVarint32TwoBytes((int)var1);
      }

      @Override
      void finishCurrentBuffer() {
         if (this.buffer != null) {
            this.totalDoneBytes = this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
            Java8Compatibility.position(this.buffer, this.pos + 1);
            this.buffer = null;
            this.pos = 0;
            this.limitMinusOne = 0;
         }
      }

      @Override
      public int getTotalBytesWritten() {
         return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
      }

      @Override
      void requireSpace(int var1) {
         if (this.spaceLeft() < var1) {
            this.nextBuffer(var1);
         }
      }

      @Override
      public void write(byte var1) {
         ByteBuffer var3 = this.buffer;
         int var2 = this.pos--;
         var3.put(var2, var1);
      }

      @Override
      public void write(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.nextBuffer(var2);
         }

         var2 = this.pos - var2;
         this.pos = var2;
         Java8Compatibility.position(this.buffer, var2 + 1);
         this.buffer.put(var1);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.nextBuffer(var3);
         }

         int var4 = this.pos - var3;
         this.pos = var4;
         Java8Compatibility.position(this.buffer, var4 + 1);
         this.buffer.put(var1, var2, var3);
      }

      @Override
      public void writeBool(int var1, boolean var2) {
         this.requireSpace(6);
         this.write((byte)var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeBool(boolean var1) {
         this.write((byte)var1);
      }

      @Override
      public void writeBytes(int var1, ByteString var2) {
         try {
            var2.writeToReverse(this);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         this.requireSpace(10);
         this.writeVarint32(var2.size());
         this.writeTag(var1, 2);
      }

      @Deprecated
      @Override
      public void writeEndGroup(int var1) {
         this.writeTag(var1, 4);
      }

      @Override
      void writeFixed32(int var1) {
         int var2 = this.pos;
         this.pos = var2 - 4;
         this.buffer.putInt(var2 - 3, var1);
      }

      @Override
      public void writeFixed32(int var1, int var2) {
         this.requireSpace(9);
         this.writeFixed32(var2);
         this.writeTag(var1, 5);
      }

      @Override
      public void writeFixed64(int var1, long var2) {
         this.requireSpace(13);
         this.writeFixed64(var2);
         this.writeTag(var1, 1);
      }

      @Override
      void writeFixed64(long var1) {
         int var3 = this.pos;
         this.pos = var3 - 8;
         this.buffer.putLong(var3 - 7, var1);
      }

      @Deprecated
      @Override
      public void writeGroup(int var1, Object var2) throws IOException {
         this.writeTag(var1, 4);
         Protobuf.getInstance().writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      public void writeGroup(int var1, Object var2, Schema var3) throws IOException {
         this.writeTag(var1, 4);
         var3.writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      void writeInt32(int var1) {
         if (var1 >= 0) {
            this.writeVarint32(var1);
         } else {
            this.writeVarint64(var1);
         }
      }

      @Override
      public void writeInt32(int var1, int var2) {
         this.requireSpace(15);
         this.writeInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeLazy(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.totalDoneBytes += var2;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1));
            this.nextBuffer();
         } else {
            var2 = this.pos - var2;
            this.pos = var2;
            Java8Compatibility.position(this.buffer, var2 + 1);
            this.buffer.put(var1);
         }
      }

      @Override
      public void writeLazy(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.totalDoneBytes += var3;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1, var2, var3));
            this.nextBuffer();
         } else {
            int var4 = this.pos - var3;
            this.pos = var4;
            Java8Compatibility.position(this.buffer, var4 + 1);
            this.buffer.put(var1, var2, var3);
         }
      }

      @Override
      public void writeMessage(int var1, Object var2) throws IOException {
         int var3 = this.getTotalBytesWritten();
         Protobuf.getInstance().writeTo(var2, this);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var3);
         this.writeTag(var1, 2);
      }

      @Override
      public void writeMessage(int var1, Object var2, Schema var3) throws IOException {
         int var4 = this.getTotalBytesWritten();
         var3.writeTo(var2, this);
         int var5 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var5 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      void writeSInt32(int var1) {
         this.writeVarint32(CodedOutputStream.encodeZigZag32(var1));
      }

      @Override
      public void writeSInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeSInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeSInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeSInt64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeSInt64(long var1) {
         this.writeVarint64(CodedOutputStream.encodeZigZag64(var1));
      }

      @Deprecated
      @Override
      public void writeStartGroup(int var1) {
         this.writeTag(var1, 3);
      }

      @Override
      public void writeString(int var1, String var2) {
         int var4 = this.getTotalBytesWritten();
         this.writeString(var2);
         int var3 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var3 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      void writeString(String var1) {
         this.requireSpace(var1.length());
         int var4 = var1.length() - 1;

         for (this.pos -= var4; var4 >= 0; var4--) {
            char var5 = var1.charAt(var4);
            if (var5 >= 128) {
               break;
            }

            this.buffer.put(this.pos + var4, (byte)var5);
         }

         if (var4 == -1) {
            this.pos--;
         } else {
            this.pos += var4;

            while (true) {
               if (var4 < 0) {
                  return;
               }

               label73: {
                  char var3 = var1.charAt(var4);
                  if (var3 < 128) {
                     int var8 = this.pos;
                     if (var8 >= 0) {
                        ByteBuffer var26 = this.buffer;
                        this.pos = var8 - 1;
                        var26.put(var8, (byte)var3);
                        break label73;
                     }
                  }

                  if (var3 < 2048) {
                     int var9 = this.pos;
                     if (var9 > 0) {
                        ByteBuffer var24 = this.buffer;
                        this.pos = var9 - 1;
                        var24.put(var9, (byte)(var3 & '?' | 128));
                        var24 = this.buffer;
                        var9 = this.pos--;
                        var24.put(var9, (byte)(var3 >>> 6 | 960));
                        break label73;
                     }
                  }

                  if (var3 < '\ud800' || '\udfff' < var3) {
                     int var10 = this.pos;
                     if (var10 > 1) {
                        ByteBuffer var21 = this.buffer;
                        this.pos = var10 - 1;
                        var21.put(var10, (byte)(var3 & '?' | 128));
                        var21 = this.buffer;
                        var10 = this.pos--;
                        var21.put(var10, (byte)(var3 >>> 6 & 63 | 128));
                        var21 = this.buffer;
                        var10 = this.pos--;
                        var21.put(var10, (byte)(var3 >>> '\f' | 480));
                        break label73;
                     }
                  }

                  if (this.pos > 2) {
                     if (var4 == 0) {
                        break;
                     }

                     char var2 = var1.charAt(var4 - 1);
                     if (!Character.isSurrogatePair(var2, var3)) {
                        break;
                     }

                     var4--;
                     int var11 = Character.toCodePoint(var2, var3);
                     ByteBuffer var7 = this.buffer;
                     int var6 = this.pos--;
                     var7.put(var6, (byte)(var11 & 63 | 128));
                     var7 = this.buffer;
                     var6 = this.pos--;
                     var7.put(var6, (byte)(var11 >>> 6 & 63 | 128));
                     var7 = this.buffer;
                     var6 = this.pos--;
                     var7.put(var6, (byte)(var11 >>> 12 & 63 | 128));
                     var7 = this.buffer;
                     var6 = this.pos--;
                     var7.put(var6, (byte)(var11 >>> 18 | 240));
                  } else {
                     this.requireSpace(var4);
                     var4++;
                  }
               }

               var4--;
            }

            throw new Utf8.UnpairedSurrogateException(var4 - 1, var4);
         }
      }

      @Override
      void writeTag(int var1, int var2) {
         this.writeVarint32(WireFormat.makeTag(var1, var2));
      }

      @Override
      public void writeUInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeVarint32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeUInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeVarint64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeVarint32(int var1) {
         if ((var1 & -128) == 0) {
            this.writeVarint32OneByte(var1);
         } else if ((var1 & -16384) == 0) {
            this.writeVarint32TwoBytes(var1);
         } else if ((-2097152 & var1) == 0) {
            this.writeVarint32ThreeBytes(var1);
         } else if ((-268435456 & var1) == 0) {
            this.writeVarint32FourBytes(var1);
         } else {
            this.writeVarint32FiveBytes(var1);
         }
      }

      @Override
      void writeVarint64(long var1) {
         switch (BinaryWriter.computeUInt64SizeNoTag(var1)) {
            case 1:
               this.writeVarint64OneByte(var1);
               break;
            case 2:
               this.writeVarint64TwoBytes(var1);
               break;
            case 3:
               this.writeVarint64ThreeBytes(var1);
               break;
            case 4:
               this.writeVarint64FourBytes(var1);
               break;
            case 5:
               this.writeVarint64FiveBytes(var1);
               break;
            case 6:
               this.writeVarint64SixBytes(var1);
               break;
            case 7:
               this.writeVarint64SevenBytes(var1);
               break;
            case 8:
               this.writeVarint64EightBytes(var1);
               break;
            case 9:
               this.writeVarint64NineBytes(var1);
               break;
            case 10:
               this.writeVarint64TenBytes(var1);
         }
      }
   }

   private static final class SafeHeapWriter extends BinaryWriter {
      private AllocatedBuffer allocatedBuffer;
      private byte[] buffer;
      private int limit;
      private int limitMinusOne;
      private int offset;
      private int offsetMinusOne;
      private int pos;

      SafeHeapWriter(BufferAllocator var1, int var2) {
         super(var1, var2);
         this.nextBuffer();
      }

      private void nextBuffer() {
         this.nextBuffer(this.newHeapBuffer());
      }

      private void nextBuffer(int var1) {
         this.nextBuffer(this.newHeapBuffer(var1));
      }

      private void nextBuffer(AllocatedBuffer var1) {
         if (var1.hasArray()) {
            this.finishCurrentBuffer();
            this.buffers.addFirst(var1);
            this.allocatedBuffer = var1;
            this.buffer = var1.array();
            int var2 = var1.arrayOffset();
            this.limit = var1.limit() + var2;
            var2 += var1.position();
            this.offset = var2;
            this.offsetMinusOne = var2 - 1;
            var2 = this.limit - 1;
            this.limitMinusOne = var2;
            this.pos = var2;
         } else {
            throw new RuntimeException("Allocator returned non-heap buffer");
         }
      }

      private void writeVarint32FiveBytes(int var1) {
         byte[] var5 = this.buffer;
         int var2 = this.pos;
         int var4 = var2 - 1;
         this.pos = var4;
         var5[var2] = (byte)(var1 >>> 28);
         int var3 = var2 - 2;
         this.pos = var3;
         var5[var4] = (byte)(var1 >>> 21 & 127 | 128);
         var4 = var2 - 3;
         this.pos = var4;
         var5[var3] = (byte)(var1 >>> 14 & 127 | 128);
         var3 = var2 - 4;
         this.pos = var3;
         var5[var4] = (byte)(var1 >>> 7 & 127 | 128);
         this.pos = var2 - 5;
         var5[var3] = (byte)(var1 & 127 | 128);
      }

      private void writeVarint32FourBytes(int var1) {
         byte[] var5 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var5[var3] = (byte)(var1 >>> 21);
         int var2 = var3 - 2;
         this.pos = var2;
         var5[var4] = (byte)(var1 >>> 14 & 127 | 128);
         var4 = var3 - 3;
         this.pos = var4;
         var5[var2] = (byte)(var1 >>> 7 & 127 | 128);
         this.pos = var3 - 4;
         var5[var4] = (byte)(var1 & 127 | 128);
      }

      private void writeVarint32OneByte(int var1) {
         byte[] var3 = this.buffer;
         int var2 = this.pos--;
         var3[var2] = (byte)var1;
      }

      private void writeVarint32ThreeBytes(int var1) {
         byte[] var5 = this.buffer;
         int var2 = this.pos;
         int var3 = var2 - 1;
         this.pos = var3;
         var5[var2] = (byte)(var1 >>> 14);
         int var4 = var2 - 2;
         this.pos = var4;
         var5[var3] = (byte)(var1 >>> 7 & 127 | 128);
         this.pos = var2 - 3;
         var5[var4] = (byte)(var1 & 127 | 128);
      }

      private void writeVarint32TwoBytes(int var1) {
         byte[] var4 = this.buffer;
         int var3 = this.pos;
         int var2 = var3 - 1;
         this.pos = var2;
         var4[var3] = (byte)(var1 >>> 7);
         this.pos = var3 - 2;
         var4[var2] = (byte)(var1 & 127 | 128);
      }

      private void writeVarint64EightBytes(long var1) {
         byte[] var7 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var7[var3] = (byte)(var1 >>> 49);
         int var5 = var3 - 2;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 42 & 127L | 128L);
         var4 = var3 - 3;
         this.pos = var4;
         var7[var5] = (byte)(var1 >>> 35 & 127L | 128L);
         var5 = var3 - 4;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 28 & 127L | 128L);
         int var6 = var3 - 5;
         this.pos = var6;
         var7[var5] = (byte)(var1 >>> 21 & 127L | 128L);
         var4 = var3 - 6;
         this.pos = var4;
         var7[var6] = (byte)(var1 >>> 14 & 127L | 128L);
         var5 = var3 - 7;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 8;
         var7[var5] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64FiveBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var5 = var3 - 1;
         this.pos = var5;
         var6[var3] = (byte)(var1 >>> 28);
         int var4 = var3 - 2;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 21 & 127L | 128L);
         var5 = var3 - 3;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 14 & 127L | 128L);
         var4 = var3 - 4;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 5;
         var6[var4] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64FourBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var5 = var3 - 1;
         this.pos = var5;
         var6[var3] = (byte)(var1 >>> 21);
         int var4 = var3 - 2;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 14 & 127L | 128L);
         var5 = var3 - 3;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 4;
         var6[var5] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64NineBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var6[var3] = (byte)(var1 >>> 56);
         int var5 = var3 - 2;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 49 & 127L | 128L);
         var4 = var3 - 3;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 42 & 127L | 128L);
         var5 = var3 - 4;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 35 & 127L | 128L);
         var4 = var3 - 5;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 28 & 127L | 128L);
         var5 = var3 - 6;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 21 & 127L | 128L);
         var4 = var3 - 7;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 14 & 127L | 128L);
         var5 = var3 - 8;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 9;
         var6[var5] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64OneByte(long var1) {
         byte[] var4 = this.buffer;
         int var3 = this.pos--;
         var4[var3] = (byte)var1;
      }

      private void writeVarint64SevenBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var5 = var3 - 1;
         this.pos = var5;
         var6[var3] = (byte)(var1 >>> 42);
         int var4 = var3 - 2;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 35 & 127L | 128L);
         var5 = var3 - 3;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 28 & 127L | 128L);
         var4 = var3 - 4;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 21 & 127L | 128L);
         var5 = var3 - 5;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 14 & 127L | 128L);
         var4 = var3 - 6;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 7;
         var6[var4] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64SixBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var6[var3] = (byte)(var1 >>> 35);
         int var5 = var3 - 2;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 28 & 127L | 128L);
         var4 = var3 - 3;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 21 & 127L | 128L);
         var5 = var3 - 4;
         this.pos = var5;
         var6[var4] = (byte)(var1 >>> 14 & 127L | 128L);
         var4 = var3 - 5;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 6;
         var6[var4] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64TenBytes(long var1) {
         byte[] var7 = this.buffer;
         int var3 = this.pos;
         int var5 = var3 - 1;
         this.pos = var5;
         var7[var3] = (byte)(var1 >>> 63);
         int var4 = var3 - 2;
         this.pos = var4;
         var7[var5] = (byte)(var1 >>> 56 & 127L | 128L);
         var5 = var3 - 3;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 49 & 127L | 128L);
         int var6 = var3 - 4;
         this.pos = var6;
         var7[var5] = (byte)(var1 >>> 42 & 127L | 128L);
         var4 = var3 - 5;
         this.pos = var4;
         var7[var6] = (byte)(var1 >>> 35 & 127L | 128L);
         var5 = var3 - 6;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 28 & 127L | 128L);
         var6 = var3 - 7;
         this.pos = var6;
         var7[var5] = (byte)(var1 >>> 21 & 127L | 128L);
         var4 = var3 - 8;
         this.pos = var4;
         var7[var6] = (byte)(var1 >>> 14 & 127L | 128L);
         var5 = var3 - 9;
         this.pos = var5;
         var7[var4] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 10;
         var7[var5] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64ThreeBytes(long var1) {
         byte[] var6 = this.buffer;
         int var3 = this.pos;
         int var5 = var3 - 1;
         this.pos = var5;
         var6[var3] = (byte)((int)var1 >>> 14);
         int var4 = var3 - 2;
         this.pos = var4;
         var6[var5] = (byte)(var1 >>> 7 & 127L | 128L);
         this.pos = var3 - 3;
         var6[var4] = (byte)(var1 & 127L | 128L);
      }

      private void writeVarint64TwoBytes(long var1) {
         byte[] var5 = this.buffer;
         int var4 = this.pos;
         int var3 = var4 - 1;
         this.pos = var3;
         var5[var4] = (byte)(var1 >>> 7);
         this.pos = var4 - 2;
         var5[var3] = (byte)((int)var1 & 127 | 128);
      }

      int bytesWrittenToCurrentBuffer() {
         return this.limitMinusOne - this.pos;
      }

      @Override
      void finishCurrentBuffer() {
         if (this.allocatedBuffer != null) {
            this.totalDoneBytes = this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
            AllocatedBuffer var1 = this.allocatedBuffer;
            var1.position(this.pos - var1.arrayOffset() + 1);
            this.allocatedBuffer = null;
            this.pos = 0;
            this.limitMinusOne = 0;
         }
      }

      @Override
      public int getTotalBytesWritten() {
         return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
      }

      @Override
      void requireSpace(int var1) {
         if (this.spaceLeft() < var1) {
            this.nextBuffer(var1);
         }
      }

      int spaceLeft() {
         return this.pos - this.offsetMinusOne;
      }

      @Override
      public void write(byte var1) {
         byte[] var3 = this.buffer;
         int var2 = this.pos--;
         var3[var2] = var1;
      }

      @Override
      public void write(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.nextBuffer(var2);
         }

         int var3 = this.pos - var2;
         this.pos = var3;
         var1.get(this.buffer, var3 + 1, var2);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.nextBuffer(var3);
         }

         int var4 = this.pos - var3;
         this.pos = var4;
         System.arraycopy(var1, var2, this.buffer, var4 + 1, var3);
      }

      @Override
      public void writeBool(int var1, boolean var2) throws IOException {
         this.requireSpace(6);
         this.write((byte)var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeBool(boolean var1) {
         this.write((byte)var1);
      }

      @Override
      public void writeBytes(int var1, ByteString var2) throws IOException {
         try {
            var2.writeToReverse(this);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         this.requireSpace(10);
         this.writeVarint32(var2.size());
         this.writeTag(var1, 2);
      }

      @Override
      public void writeEndGroup(int var1) {
         this.writeTag(var1, 4);
      }

      @Override
      void writeFixed32(int var1) {
         byte[] var5 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var5[var3] = (byte)(var1 >> 24 & 0xFF);
         int var2 = var3 - 2;
         this.pos = var2;
         var5[var4] = (byte)(var1 >> 16 & 0xFF);
         var4 = var3 - 3;
         this.pos = var4;
         var5[var2] = (byte)(var1 >> 8 & 0xFF);
         this.pos = var3 - 4;
         var5[var4] = (byte)(var1 & 0xFF);
      }

      @Override
      public void writeFixed32(int var1, int var2) throws IOException {
         this.requireSpace(9);
         this.writeFixed32(var2);
         this.writeTag(var1, 5);
      }

      @Override
      public void writeFixed64(int var1, long var2) throws IOException {
         this.requireSpace(13);
         this.writeFixed64(var2);
         this.writeTag(var1, 1);
      }

      @Override
      void writeFixed64(long var1) {
         byte[] var7 = this.buffer;
         int var3 = this.pos;
         int var4 = var3 - 1;
         this.pos = var4;
         var7[var3] = (byte)((int)(var1 >> 56) & 0xFF);
         int var5 = var3 - 2;
         this.pos = var5;
         var7[var4] = (byte)((int)(var1 >> 48) & 0xFF);
         var4 = var3 - 3;
         this.pos = var4;
         var7[var5] = (byte)((int)(var1 >> 40) & 0xFF);
         var5 = var3 - 4;
         this.pos = var5;
         var7[var4] = (byte)((int)(var1 >> 32) & 0xFF);
         int var6 = var3 - 5;
         this.pos = var6;
         var7[var5] = (byte)((int)(var1 >> 24) & 0xFF);
         var4 = var3 - 6;
         this.pos = var4;
         var7[var6] = (byte)((int)(var1 >> 16) & 0xFF);
         var5 = var3 - 7;
         this.pos = var5;
         var7[var4] = (byte)((int)(var1 >> 8) & 0xFF);
         this.pos = var3 - 8;
         var7[var5] = (byte)((int)var1 & 0xFF);
      }

      @Deprecated
      @Override
      public void writeGroup(int var1, Object var2) throws IOException {
         this.writeTag(var1, 4);
         Protobuf.getInstance().writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      public void writeGroup(int var1, Object var2, Schema var3) throws IOException {
         this.writeTag(var1, 4);
         var3.writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      void writeInt32(int var1) {
         if (var1 >= 0) {
            this.writeVarint32(var1);
         } else {
            this.writeVarint64(var1);
         }
      }

      @Override
      public void writeInt32(int var1, int var2) throws IOException {
         this.requireSpace(15);
         this.writeInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeLazy(ByteBuffer var1) {
         int var3 = var1.remaining();
         if (this.spaceLeft() < var3) {
            this.totalDoneBytes += var3;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1));
            this.nextBuffer();
         }

         int var2 = this.pos - var3;
         this.pos = var2;
         var1.get(this.buffer, var2 + 1, var3);
      }

      @Override
      public void writeLazy(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.totalDoneBytes += var3;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1, var2, var3));
            this.nextBuffer();
         } else {
            int var4 = this.pos - var3;
            this.pos = var4;
            System.arraycopy(var1, var2, this.buffer, var4 + 1, var3);
         }
      }

      @Override
      public void writeMessage(int var1, Object var2) throws IOException {
         int var4 = this.getTotalBytesWritten();
         Protobuf.getInstance().writeTo(var2, this);
         int var3 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var3 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      public void writeMessage(int var1, Object var2, Schema var3) throws IOException {
         int var5 = this.getTotalBytesWritten();
         var3.writeTo(var2, this);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var5);
         this.writeTag(var1, 2);
      }

      @Override
      void writeSInt32(int var1) {
         this.writeVarint32(CodedOutputStream.encodeZigZag32(var1));
      }

      @Override
      public void writeSInt32(int var1, int var2) throws IOException {
         this.requireSpace(10);
         this.writeSInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeSInt64(int var1, long var2) throws IOException {
         this.requireSpace(15);
         this.writeSInt64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeSInt64(long var1) {
         this.writeVarint64(CodedOutputStream.encodeZigZag64(var1));
      }

      @Override
      public void writeStartGroup(int var1) {
         this.writeTag(var1, 3);
      }

      @Override
      public void writeString(int var1, String var2) throws IOException {
         int var4 = this.getTotalBytesWritten();
         this.writeString(var2);
         int var3 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var3 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      void writeString(String var1) {
         this.requireSpace(var1.length());
         int var4 = var1.length() - 1;

         for (this.pos -= var4; var4 >= 0; var4--) {
            char var5 = var1.charAt(var4);
            if (var5 >= 128) {
               break;
            }

            this.buffer[this.pos + var4] = (byte)var5;
         }

         if (var4 == -1) {
            this.pos--;
         } else {
            this.pos += var4;

            while (true) {
               if (var4 < 0) {
                  return;
               }

               label73: {
                  char var2 = var1.charAt(var4);
                  if (var2 < 128) {
                     int var10 = this.pos;
                     if (var10 > this.offsetMinusOne) {
                        byte[] var20 = this.buffer;
                        this.pos = var10 - 1;
                        var20[var10] = (byte)var2;
                        break label73;
                     }
                  }

                  if (var2 < 2048) {
                     int var11 = this.pos;
                     if (var11 > this.offset) {
                        byte[] var19 = this.buffer;
                        int var15 = var11 - 1;
                        this.pos = var15;
                        var19[var11] = (byte)(var2 & '?' | 128);
                        this.pos = var11 - 2;
                        var19[var15] = (byte)(var2 >>> 6 | 960);
                        break label73;
                     }
                  }

                  if (var2 < '\ud800' || '\udfff' < var2) {
                     int var12 = this.pos;
                     if (var12 > this.offset + 1) {
                        byte[] var18 = this.buffer;
                        int var16 = var12 - 1;
                        this.pos = var16;
                        var18[var12] = (byte)(var2 & '?' | 128);
                        int var14 = var12 - 2;
                        this.pos = var14;
                        var18[var16] = (byte)(var2 >>> 6 & 63 | 128);
                        this.pos = var12 - 3;
                        var18[var14] = (byte)(var2 >>> '\f' | 480);
                        break label73;
                     }
                  }

                  if (this.pos > this.offset + 2) {
                     if (var4 == 0) {
                        break;
                     }

                     char var3 = var1.charAt(var4 - 1);
                     if (!Character.isSurrogatePair(var3, var2)) {
                        break;
                     }

                     var4--;
                     int var13 = Character.toCodePoint(var3, var2);
                     byte[] var9 = this.buffer;
                     int var6 = this.pos;
                     int var8 = var6 - 1;
                     this.pos = var8;
                     var9[var6] = (byte)(var13 & 63 | 128);
                     int var7 = var6 - 2;
                     this.pos = var7;
                     var9[var8] = (byte)(var13 >>> 6 & 63 | 128);
                     var8 = var6 - 3;
                     this.pos = var8;
                     var9[var7] = (byte)(var13 >>> 12 & 63 | 128);
                     this.pos = var6 - 4;
                     var9[var8] = (byte)(var13 >>> 18 | 240);
                  } else {
                     this.requireSpace(var4);
                     var4++;
                  }
               }

               var4--;
            }

            throw new Utf8.UnpairedSurrogateException(var4 - 1, var4);
         }
      }

      @Override
      void writeTag(int var1, int var2) {
         this.writeVarint32(WireFormat.makeTag(var1, var2));
      }

      @Override
      public void writeUInt32(int var1, int var2) throws IOException {
         this.requireSpace(10);
         this.writeVarint32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeUInt64(int var1, long var2) throws IOException {
         this.requireSpace(15);
         this.writeVarint64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeVarint32(int var1) {
         if ((var1 & -128) == 0) {
            this.writeVarint32OneByte(var1);
         } else if ((var1 & -16384) == 0) {
            this.writeVarint32TwoBytes(var1);
         } else if ((-2097152 & var1) == 0) {
            this.writeVarint32ThreeBytes(var1);
         } else if ((-268435456 & var1) == 0) {
            this.writeVarint32FourBytes(var1);
         } else {
            this.writeVarint32FiveBytes(var1);
         }
      }

      @Override
      void writeVarint64(long var1) {
         switch (BinaryWriter.computeUInt64SizeNoTag(var1)) {
            case 1:
               this.writeVarint64OneByte(var1);
               break;
            case 2:
               this.writeVarint64TwoBytes(var1);
               break;
            case 3:
               this.writeVarint64ThreeBytes(var1);
               break;
            case 4:
               this.writeVarint64FourBytes(var1);
               break;
            case 5:
               this.writeVarint64FiveBytes(var1);
               break;
            case 6:
               this.writeVarint64SixBytes(var1);
               break;
            case 7:
               this.writeVarint64SevenBytes(var1);
               break;
            case 8:
               this.writeVarint64EightBytes(var1);
               break;
            case 9:
               this.writeVarint64NineBytes(var1);
               break;
            case 10:
               this.writeVarint64TenBytes(var1);
         }
      }
   }

   private static final class UnsafeDirectWriter extends BinaryWriter {
      private ByteBuffer buffer;
      private long bufferOffset;
      private long limitMinusOne;
      private long pos;

      UnsafeDirectWriter(BufferAllocator var1, int var2) {
         super(var1, var2);
         this.nextBuffer();
      }

      private int bufferPos() {
         return (int)(this.pos - this.bufferOffset);
      }

      private int bytesWrittenToCurrentBuffer() {
         return (int)(this.limitMinusOne - this.pos);
      }

      private static boolean isSupported() {
         return UnsafeUtil.hasUnsafeByteBufferOperations();
      }

      private void nextBuffer() {
         this.nextBuffer(this.newDirectBuffer());
      }

      private void nextBuffer(int var1) {
         this.nextBuffer(this.newDirectBuffer(var1));
      }

      private void nextBuffer(AllocatedBuffer var1) {
         if (var1.hasNioBuffer()) {
            ByteBuffer var4 = var1.nioBuffer();
            if (var4.isDirect()) {
               this.finishCurrentBuffer();
               this.buffers.addFirst(var1);
               this.buffer = var4;
               Java8Compatibility.limit(var4, var4.capacity());
               Java8Compatibility.position(this.buffer, 0);
               long var2 = UnsafeUtil.addressOffset(this.buffer);
               this.bufferOffset = var2;
               var2 += this.buffer.limit() - 1;
               this.limitMinusOne = var2;
               this.pos = var2;
            } else {
               throw new RuntimeException("Allocator returned non-direct buffer");
            }
         } else {
            throw new RuntimeException("Allocated buffer does not have NIO buffer");
         }
      }

      private int spaceLeft() {
         return this.bufferPos() + 1;
      }

      private void writeVarint32FiveBytes(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 28));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 21 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 14 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 7 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32FourBytes(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 21));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 14 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 7 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32OneByte(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)var1);
      }

      private void writeVarint32ThreeBytes(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 14));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 7 & 127 | 128));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32TwoBytes(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >>> 7));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint64EightBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 49));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 42 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 35 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64FiveBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64FourBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64NineBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 56));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 49 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 42 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 35 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64OneByte(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)var1);
      }

      private void writeVarint64SevenBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 42));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 35 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64SixBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 35));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64TenBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 63));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 56 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 49 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 42 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 35 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 28 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 21 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 14 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64ThreeBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)var1 >>> 14));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7 & 127L | 128L));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64TwoBytes(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)(var1 >>> 7));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)var1 & 127 | 128));
      }

      @Override
      void finishCurrentBuffer() {
         if (this.buffer != null) {
            this.totalDoneBytes = this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
            Java8Compatibility.position(this.buffer, this.bufferPos() + 1);
            this.buffer = null;
            this.pos = 0L;
            this.limitMinusOne = 0L;
         }
      }

      @Override
      public int getTotalBytesWritten() {
         return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
      }

      @Override
      void requireSpace(int var1) {
         if (this.spaceLeft() < var1) {
            this.nextBuffer(var1);
         }
      }

      @Override
      public void write(byte var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, var1);
      }

      @Override
      public void write(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.nextBuffer(var2);
         }

         this.pos -= var2;
         Java8Compatibility.position(this.buffer, this.bufferPos() + 1);
         this.buffer.put(var1);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.nextBuffer(var3);
         }

         this.pos -= var3;
         Java8Compatibility.position(this.buffer, this.bufferPos() + 1);
         this.buffer.put(var1, var2, var3);
      }

      @Override
      public void writeBool(int var1, boolean var2) {
         this.requireSpace(6);
         this.write((byte)var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeBool(boolean var1) {
         this.write((byte)var1);
      }

      @Override
      public void writeBytes(int var1, ByteString var2) {
         try {
            var2.writeToReverse(this);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         this.requireSpace(10);
         this.writeVarint32(var2.size());
         this.writeTag(var1, 2);
      }

      @Deprecated
      @Override
      public void writeEndGroup(int var1) {
         this.writeTag(var1, 4);
      }

      @Override
      void writeFixed32(int var1) {
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >> 24 & 0xFF));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >> 16 & 0xFF));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 >> 8 & 0xFF));
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var2, (byte)(var1 & 0xFF));
      }

      @Override
      public void writeFixed32(int var1, int var2) {
         this.requireSpace(9);
         this.writeFixed32(var2);
         this.writeTag(var1, 5);
      }

      @Override
      public void writeFixed64(int var1, long var2) {
         this.requireSpace(13);
         this.writeFixed64(var2);
         this.writeTag(var1, 1);
      }

      @Override
      void writeFixed64(long var1) {
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 56) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 48) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 40) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 32) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 24) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 16) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)(var1 >> 8) & 0xFF));
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var3, (byte)((int)var1 & 0xFF));
      }

      @Override
      public void writeGroup(int var1, Object var2) throws IOException {
         this.writeTag(var1, 4);
         Protobuf.getInstance().writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      public void writeGroup(int var1, Object var2, Schema var3) throws IOException {
         this.writeTag(var1, 4);
         var3.writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      void writeInt32(int var1) {
         if (var1 >= 0) {
            this.writeVarint32(var1);
         } else {
            this.writeVarint64(var1);
         }
      }

      @Override
      public void writeInt32(int var1, int var2) {
         this.requireSpace(15);
         this.writeInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeLazy(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.totalDoneBytes += var2;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1));
            this.nextBuffer();
         } else {
            this.pos -= var2;
            Java8Compatibility.position(this.buffer, this.bufferPos() + 1);
            this.buffer.put(var1);
         }
      }

      @Override
      public void writeLazy(byte[] var1, int var2, int var3) {
         if (this.spaceLeft() < var3) {
            this.totalDoneBytes += var3;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1, var2, var3));
            this.nextBuffer();
         } else {
            this.pos -= var3;
            Java8Compatibility.position(this.buffer, this.bufferPos() + 1);
            this.buffer.put(var1, var2, var3);
         }
      }

      @Override
      public void writeMessage(int var1, Object var2) throws IOException {
         int var3 = this.getTotalBytesWritten();
         Protobuf.getInstance().writeTo(var2, this);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var3);
         this.writeTag(var1, 2);
      }

      @Override
      public void writeMessage(int var1, Object var2, Schema var3) throws IOException {
         int var4 = this.getTotalBytesWritten();
         var3.writeTo(var2, this);
         int var5 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var5 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      void writeSInt32(int var1) {
         this.writeVarint32(CodedOutputStream.encodeZigZag32(var1));
      }

      @Override
      public void writeSInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeSInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeSInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeSInt64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeSInt64(long var1) {
         this.writeVarint64(CodedOutputStream.encodeZigZag64(var1));
      }

      @Deprecated
      @Override
      public void writeStartGroup(int var1) {
         this.writeTag(var1, 3);
      }

      @Override
      public void writeString(int var1, String var2) {
         int var3 = this.getTotalBytesWritten();
         this.writeString(var2);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var3);
         this.writeTag(var1, 2);
      }

      @Override
      void writeString(String var1) {
         this.requireSpace(var1.length());

         int var4;
         for (var4 = var1.length() - 1; var4 >= 0; var4--) {
            char var5 = var1.charAt(var4);
            if (var5 >= 128) {
               break;
            }

            long var6 = (long)(this.pos--);
            UnsafeUtil.putByte(var6, (byte)var5);
         }

         int var9 = var4;
         if (var4 != -1) {
            while (true) {
               if (var9 < 0) {
                  return;
               }

               label71: {
                  char var3 = var1.charAt(var9);
                  if (var3 < 128) {
                     long var10 = this.pos;
                     if (var10 >= this.bufferOffset) {
                        this.pos = var10 - 1L;
                        UnsafeUtil.putByte(var10, (byte)var3);
                        break label71;
                     }
                  }

                  if (var3 < 2048) {
                     long var11 = this.pos;
                     if (var11 > this.bufferOffset) {
                        this.pos = var11 - 1L;
                        UnsafeUtil.putByte(var11, (byte)(var3 & '?' | 128));
                        var11 = (long)(this.pos--);
                        UnsafeUtil.putByte(var11, (byte)(var3 >>> 6 | 960));
                        break label71;
                     }
                  }

                  if (var3 < '\ud800' || '\udfff' < var3) {
                     long var12 = this.pos;
                     if (var12 > this.bufferOffset + 1L) {
                        this.pos = var12 - 1L;
                        UnsafeUtil.putByte(var12, (byte)(var3 & '?' | 128));
                        var12 = (long)(this.pos--);
                        UnsafeUtil.putByte(var12, (byte)(var3 >>> 6 & 63 | 128));
                        var12 = (long)(this.pos--);
                        UnsafeUtil.putByte(var12, (byte)(var3 >>> '\f' | 480));
                        break label71;
                     }
                  }

                  if (this.pos > this.bufferOffset + 2L) {
                     if (var9 == 0) {
                        break;
                     }

                     char var2 = var1.charAt(var9 - 1);
                     if (!Character.isSurrogatePair(var2, var3)) {
                        break;
                     }

                     var9--;
                     var4 = Character.toCodePoint(var2, var3);
                     long var13 = (long)(this.pos--);
                     UnsafeUtil.putByte(var13, (byte)(var4 & 63 | 128));
                     var13 = (long)(this.pos--);
                     UnsafeUtil.putByte(var13, (byte)(var4 >>> 6 & 63 | 128));
                     var13 = (long)(this.pos--);
                     UnsafeUtil.putByte(var13, (byte)(var4 >>> 12 & 63 | 128));
                     var13 = (long)(this.pos--);
                     UnsafeUtil.putByte(var13, (byte)(var4 >>> 18 | 240));
                  } else {
                     this.requireSpace(var9);
                     var9++;
                  }
               }

               var9--;
            }

            throw new Utf8.UnpairedSurrogateException(var9 - 1, var9);
         }
      }

      @Override
      void writeTag(int var1, int var2) {
         this.writeVarint32(WireFormat.makeTag(var1, var2));
      }

      @Override
      public void writeUInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeVarint32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeUInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeVarint64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeVarint32(int var1) {
         if ((var1 & -128) == 0) {
            this.writeVarint32OneByte(var1);
         } else if ((var1 & -16384) == 0) {
            this.writeVarint32TwoBytes(var1);
         } else if ((-2097152 & var1) == 0) {
            this.writeVarint32ThreeBytes(var1);
         } else if ((-268435456 & var1) == 0) {
            this.writeVarint32FourBytes(var1);
         } else {
            this.writeVarint32FiveBytes(var1);
         }
      }

      @Override
      void writeVarint64(long var1) {
         switch (BinaryWriter.computeUInt64SizeNoTag(var1)) {
            case 1:
               this.writeVarint64OneByte(var1);
               break;
            case 2:
               this.writeVarint64TwoBytes(var1);
               break;
            case 3:
               this.writeVarint64ThreeBytes(var1);
               break;
            case 4:
               this.writeVarint64FourBytes(var1);
               break;
            case 5:
               this.writeVarint64FiveBytes(var1);
               break;
            case 6:
               this.writeVarint64SixBytes(var1);
               break;
            case 7:
               this.writeVarint64SevenBytes(var1);
               break;
            case 8:
               this.writeVarint64EightBytes(var1);
               break;
            case 9:
               this.writeVarint64NineBytes(var1);
               break;
            case 10:
               this.writeVarint64TenBytes(var1);
         }
      }
   }

   private static final class UnsafeHeapWriter extends BinaryWriter {
      private AllocatedBuffer allocatedBuffer;
      private byte[] buffer;
      private long limit;
      private long limitMinusOne;
      private long offset;
      private long offsetMinusOne;
      private long pos;

      UnsafeHeapWriter(BufferAllocator var1, int var2) {
         super(var1, var2);
         this.nextBuffer();
      }

      private int arrayPos() {
         return (int)this.pos;
      }

      static boolean isSupported() {
         return UnsafeUtil.hasUnsafeArrayOperations();
      }

      private void nextBuffer() {
         this.nextBuffer(this.newHeapBuffer());
      }

      private void nextBuffer(int var1) {
         this.nextBuffer(this.newHeapBuffer(var1));
      }

      private void nextBuffer(AllocatedBuffer var1) {
         if (var1.hasArray()) {
            this.finishCurrentBuffer();
            this.buffers.addFirst(var1);
            this.allocatedBuffer = var1;
            this.buffer = var1.array();
            long var2 = var1.arrayOffset();
            this.limit = var1.limit() + var2;
            var2 += var1.position();
            this.offset = var2;
            this.offsetMinusOne = var2 - 1L;
            var2 = this.limit - 1L;
            this.limitMinusOne = var2;
            this.pos = var2;
         } else {
            throw new RuntimeException("Allocator returned non-heap buffer");
         }
      }

      private void writeVarint32FiveBytes(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 28));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 21 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 14 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 7 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32FourBytes(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 21));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 14 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 7 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32OneByte(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)var1);
      }

      private void writeVarint32ThreeBytes(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 14));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 7 & 127 | 128));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint32TwoBytes(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >>> 7));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 & 127 | 128));
      }

      private void writeVarint64EightBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 49));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 42 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 35 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64FiveBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64FourBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64NineBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 56));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 49 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 42 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 35 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64OneByte(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)var1);
      }

      private void writeVarint64SevenBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 42));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 35 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64SixBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 35));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64TenBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 63));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 56 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 49 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 42 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 35 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 28 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 21 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 14 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64ThreeBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)var1 >>> 14));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7 & 127L | 128L));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 & 127L | 128L));
      }

      private void writeVarint64TwoBytes(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)(var1 >>> 7));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)var1 & 127 | 128));
      }

      int bytesWrittenToCurrentBuffer() {
         return (int)(this.limitMinusOne - this.pos);
      }

      @Override
      void finishCurrentBuffer() {
         if (this.allocatedBuffer != null) {
            this.totalDoneBytes = this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
            this.allocatedBuffer.position(this.arrayPos() - this.allocatedBuffer.arrayOffset() + 1);
            this.allocatedBuffer = null;
            this.pos = 0L;
            this.limitMinusOne = 0L;
         }
      }

      @Override
      public int getTotalBytesWritten() {
         return this.totalDoneBytes + this.bytesWrittenToCurrentBuffer();
      }

      @Override
      void requireSpace(int var1) {
         if (this.spaceLeft() < var1) {
            this.nextBuffer(var1);
         }
      }

      int spaceLeft() {
         return (int)(this.pos - this.offsetMinusOne);
      }

      @Override
      public void write(byte var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, var1);
      }

      @Override
      public void write(ByteBuffer var1) {
         int var2 = var1.remaining();
         this.requireSpace(var2);
         this.pos -= var2;
         var1.get(this.buffer, this.arrayPos() + 1, var2);
      }

      @Override
      public void write(byte[] var1, int var2, int var3) {
         if (var2 >= 0 && var2 + var3 <= var1.length) {
            this.requireSpace(var3);
            this.pos -= var3;
            System.arraycopy(var1, var2, this.buffer, this.arrayPos() + 1, var3);
         } else {
            throw new ArrayIndexOutOfBoundsException(String.format("value.length=%d, offset=%d, length=%d", var1.length, var2, var3));
         }
      }

      @Override
      public void writeBool(int var1, boolean var2) {
         this.requireSpace(6);
         this.write((byte)var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeBool(boolean var1) {
         this.write((byte)var1);
      }

      @Override
      public void writeBytes(int var1, ByteString var2) {
         try {
            var2.writeToReverse(this);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         this.requireSpace(10);
         this.writeVarint32(var2.size());
         this.writeTag(var1, 2);
      }

      @Override
      public void writeEndGroup(int var1) {
         this.writeTag(var1, 4);
      }

      @Override
      void writeFixed32(int var1) {
         byte[] var4 = this.buffer;
         long var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >> 24 & 0xFF));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >> 16 & 0xFF));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 >> 8 & 0xFF));
         var4 = this.buffer;
         var2 = (long)(this.pos--);
         UnsafeUtil.putByte(var4, var2, (byte)(var1 & 0xFF));
      }

      @Override
      public void writeFixed32(int var1, int var2) {
         this.requireSpace(9);
         this.writeFixed32(var2);
         this.writeTag(var1, 5);
      }

      @Override
      public void writeFixed64(int var1, long var2) {
         this.requireSpace(13);
         this.writeFixed64(var2);
         this.writeTag(var1, 1);
      }

      @Override
      void writeFixed64(long var1) {
         byte[] var5 = this.buffer;
         long var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 56) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 48) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 40) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 32) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 24) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 16) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)(var1 >> 8) & 0xFF));
         var5 = this.buffer;
         var3 = (long)(this.pos--);
         UnsafeUtil.putByte(var5, var3, (byte)((int)var1 & 0xFF));
      }

      @Override
      public void writeGroup(int var1, Object var2) throws IOException {
         this.writeTag(var1, 4);
         Protobuf.getInstance().writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      public void writeGroup(int var1, Object var2, Schema var3) throws IOException {
         this.writeTag(var1, 4);
         var3.writeTo(var2, this);
         this.writeTag(var1, 3);
      }

      @Override
      void writeInt32(int var1) {
         if (var1 >= 0) {
            this.writeVarint32(var1);
         } else {
            this.writeVarint64(var1);
         }
      }

      @Override
      public void writeInt32(int var1, int var2) {
         this.requireSpace(15);
         this.writeInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeLazy(ByteBuffer var1) {
         int var2 = var1.remaining();
         if (this.spaceLeft() < var2) {
            this.totalDoneBytes += var2;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1));
            this.nextBuffer();
         }

         this.pos -= var2;
         var1.get(this.buffer, this.arrayPos() + 1, var2);
      }

      @Override
      public void writeLazy(byte[] var1, int var2, int var3) {
         if (var2 < 0 || var2 + var3 > var1.length) {
            throw new ArrayIndexOutOfBoundsException(String.format("value.length=%d, offset=%d, length=%d", var1.length, var2, var3));
         } else if (this.spaceLeft() < var3) {
            this.totalDoneBytes += var3;
            this.buffers.addFirst(AllocatedBuffer.wrap(var1, var2, var3));
            this.nextBuffer();
         } else {
            this.pos -= var3;
            System.arraycopy(var1, var2, this.buffer, this.arrayPos() + 1, var3);
         }
      }

      @Override
      public void writeMessage(int var1, Object var2) throws IOException {
         int var3 = this.getTotalBytesWritten();
         Protobuf.getInstance().writeTo(var2, this);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var3);
         this.writeTag(var1, 2);
      }

      @Override
      public void writeMessage(int var1, Object var2, Schema var3) throws IOException {
         int var4 = this.getTotalBytesWritten();
         var3.writeTo(var2, this);
         int var5 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var5 - var4);
         this.writeTag(var1, 2);
      }

      @Override
      void writeSInt32(int var1) {
         this.writeVarint32(CodedOutputStream.encodeZigZag32(var1));
      }

      @Override
      public void writeSInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeSInt32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeSInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeSInt64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeSInt64(long var1) {
         this.writeVarint64(CodedOutputStream.encodeZigZag64(var1));
      }

      @Override
      public void writeStartGroup(int var1) {
         this.writeTag(var1, 3);
      }

      @Override
      public void writeString(int var1, String var2) {
         int var3 = this.getTotalBytesWritten();
         this.writeString(var2);
         int var4 = this.getTotalBytesWritten();
         this.requireSpace(10);
         this.writeVarint32(var4 - var3);
         this.writeTag(var1, 2);
      }

      @Override
      void writeString(String var1) {
         this.requireSpace(var1.length());

         int var4;
         for (var4 = var1.length() - 1; var4 >= 0; var4--) {
            char var5 = var1.charAt(var4);
            if (var5 >= 128) {
               break;
            }

            byte[] var8 = this.buffer;
            long var6 = (long)(this.pos--);
            UnsafeUtil.putByte(var8, var6, (byte)var5);
         }

         int var10 = var4;
         if (var4 != -1) {
            while (true) {
               if (var10 < 0) {
                  return;
               }

               label71: {
                  char var3 = var1.charAt(var10);
                  if (var3 < 128) {
                     long var11 = this.pos;
                     if (var11 > this.offsetMinusOne) {
                        byte[] var30 = this.buffer;
                        this.pos = var11 - 1L;
                        UnsafeUtil.putByte(var30, var11, (byte)var3);
                        break label71;
                     }
                  }

                  if (var3 < 2048) {
                     long var12 = this.pos;
                     if (var12 > this.offset) {
                        byte[] var28 = this.buffer;
                        this.pos = var12 - 1L;
                        UnsafeUtil.putByte(var28, var12, (byte)(var3 & '?' | 128));
                        var28 = this.buffer;
                        var12 = (long)(this.pos--);
                        UnsafeUtil.putByte(var28, var12, (byte)(var3 >>> 6 | 960));
                        break label71;
                     }
                  }

                  if (var3 < '\ud800' || '\udfff' < var3) {
                     long var13 = this.pos;
                     if (var13 > this.offset + 1L) {
                        byte[] var25 = this.buffer;
                        this.pos = var13 - 1L;
                        UnsafeUtil.putByte(var25, var13, (byte)(var3 & '?' | 128));
                        var25 = this.buffer;
                        var13 = (long)(this.pos--);
                        UnsafeUtil.putByte(var25, var13, (byte)(var3 >>> 6 & 63 | 128));
                        var25 = this.buffer;
                        var13 = (long)(this.pos--);
                        UnsafeUtil.putByte(var25, var13, (byte)(var3 >>> '\f' | 480));
                        break label71;
                     }
                  }

                  if (this.pos > this.offset + 2L) {
                     if (var10 == 0) {
                        break;
                     }

                     char var2 = var1.charAt(var10 - 1);
                     if (!Character.isSurrogatePair(var2, var3)) {
                        break;
                     }

                     var10--;
                     var4 = Character.toCodePoint(var2, var3);
                     byte[] var21 = this.buffer;
                     long var14 = (long)(this.pos--);
                     UnsafeUtil.putByte(var21, var14, (byte)(var4 & 63 | 128));
                     var21 = this.buffer;
                     var14 = (long)(this.pos--);
                     UnsafeUtil.putByte(var21, var14, (byte)(var4 >>> 6 & 63 | 128));
                     var21 = this.buffer;
                     var14 = (long)(this.pos--);
                     UnsafeUtil.putByte(var21, var14, (byte)(var4 >>> 12 & 63 | 128));
                     var21 = this.buffer;
                     var14 = (long)(this.pos--);
                     UnsafeUtil.putByte(var21, var14, (byte)(var4 >>> 18 | 240));
                  } else {
                     this.requireSpace(var10);
                     var10++;
                  }
               }

               var10--;
            }

            throw new Utf8.UnpairedSurrogateException(var10 - 1, var10);
         }
      }

      @Override
      void writeTag(int var1, int var2) {
         this.writeVarint32(WireFormat.makeTag(var1, var2));
      }

      @Override
      public void writeUInt32(int var1, int var2) {
         this.requireSpace(10);
         this.writeVarint32(var2);
         this.writeTag(var1, 0);
      }

      @Override
      public void writeUInt64(int var1, long var2) {
         this.requireSpace(15);
         this.writeVarint64(var2);
         this.writeTag(var1, 0);
      }

      @Override
      void writeVarint32(int var1) {
         if ((var1 & -128) == 0) {
            this.writeVarint32OneByte(var1);
         } else if ((var1 & -16384) == 0) {
            this.writeVarint32TwoBytes(var1);
         } else if ((-2097152 & var1) == 0) {
            this.writeVarint32ThreeBytes(var1);
         } else if ((-268435456 & var1) == 0) {
            this.writeVarint32FourBytes(var1);
         } else {
            this.writeVarint32FiveBytes(var1);
         }
      }

      @Override
      void writeVarint64(long var1) {
         switch (BinaryWriter.computeUInt64SizeNoTag(var1)) {
            case 1:
               this.writeVarint64OneByte(var1);
               break;
            case 2:
               this.writeVarint64TwoBytes(var1);
               break;
            case 3:
               this.writeVarint64ThreeBytes(var1);
               break;
            case 4:
               this.writeVarint64FourBytes(var1);
               break;
            case 5:
               this.writeVarint64FiveBytes(var1);
               break;
            case 6:
               this.writeVarint64SixBytes(var1);
               break;
            case 7:
               this.writeVarint64SevenBytes(var1);
               break;
            case 8:
               this.writeVarint64EightBytes(var1);
               break;
            case 9:
               this.writeVarint64NineBytes(var1);
               break;
            case 10:
               this.writeVarint64TenBytes(var1);
         }
      }
   }
}
