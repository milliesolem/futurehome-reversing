package com.google.protobuf;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@CheckReturnValue
final class CodedOutputStreamWriter implements Writer {
   private final CodedOutputStream output;

   private CodedOutputStreamWriter(CodedOutputStream var1) {
      var1 = Internal.checkNotNull(var1, "output");
      this.output = var1;
      var1.wrapper = this;
   }

   public static CodedOutputStreamWriter forCodedOutput(CodedOutputStream var0) {
      return var0.wrapper != null ? var0.wrapper : new CodedOutputStreamWriter(var0);
   }

   private <V> void writeDeterministicBooleanMapEntry(int var1, boolean var2, V var3, MapEntryLite.Metadata<Boolean, V> var4) throws IOException {
      this.output.writeTag(var1, 2);
      this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(var4, var2, var3));
      MapEntryLite.writeTo(this.output, var4, var2, var3);
   }

   private <V> void writeDeterministicIntegerMap(int var1, MapEntryLite.Metadata<Integer, V> var2, Map<Integer, V> var3) throws IOException {
      int var6 = var3.size();
      int[] var7 = new int[var6];
      Iterator var8 = var3.keySet().iterator();
      int var5 = 0;

      for (int var4 = 0; var8.hasNext(); var4++) {
         var7[var4] = (Integer)var8.next();
      }

      Arrays.sort(var7);

      for (int var9 = var5; var9 < var6; var9++) {
         var5 = var7[var9];
         var8 = (Iterator)var3.get(var5);
         this.output.writeTag(var1, 2);
         this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(var2, var5, var8));
         MapEntryLite.writeTo(this.output, var2, var5, var8);
      }
   }

   private <V> void writeDeterministicLongMap(int var1, MapEntryLite.Metadata<Long, V> var2, Map<Long, V> var3) throws IOException {
      int var6 = var3.size();
      long[] var9 = new long[var6];
      Iterator var10 = var3.keySet().iterator();
      byte var5 = 0;

      for (int var4 = 0; var10.hasNext(); var4++) {
         var9[var4] = (Long)var10.next();
      }

      Arrays.sort(var9);

      for (int var11 = var5; var11 < var6; var11++) {
         long var7 = var9[var11];
         var10 = (Iterator)var3.get(var7);
         this.output.writeTag(var1, 2);
         this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(var2, var7, var10));
         MapEntryLite.writeTo(this.output, var2, var7, var10);
      }
   }

   private <K, V> void writeDeterministicMap(int var1, MapEntryLite.Metadata<K, V> var2, Map<K, V> var3) throws IOException {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var2.keyType.ordinal()]) {
         case 1:
            Object var4 = var3.get(Boolean.FALSE);
            if (var4 != null) {
               this.writeDeterministicBooleanMapEntry(var1, false, var4, var2);
            }

            Object var6 = var3.get(Boolean.TRUE);
            if (var6 != null) {
               this.writeDeterministicBooleanMapEntry(var1, true, var6, var2);
            }
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
            this.writeDeterministicIntegerMap(var1, var2, var3);
            break;
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
            this.writeDeterministicLongMap(var1, var2, var3);
            break;
         case 12:
            this.writeDeterministicStringMap(var1, var2, var3);
            break;
         default:
            StringBuilder var5 = new StringBuilder("does not support key type: ");
            var5.append(var2.keyType);
            throw new IllegalArgumentException(var5.toString());
      }
   }

   private <V> void writeDeterministicStringMap(int var1, MapEntryLite.Metadata<String, V> var2, Map<String, V> var3) throws IOException {
      int var6 = var3.size();
      String[] var7 = new String[var6];
      Iterator var8 = var3.keySet().iterator();
      byte var5 = 0;

      for (int var4 = 0; var8.hasNext(); var4++) {
         var7[var4] = (String)var8.next();
      }

      Arrays.sort((Object[])var7);

      for (int var10 = var5; var10 < var6; var10++) {
         String var11 = var7[var10];
         Object var9 = var3.get(var11);
         this.output.writeTag(var1, 2);
         this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(var2, var11, var9));
         MapEntryLite.writeTo(this.output, var2, var11, var9);
      }
   }

   private void writeLazyString(int var1, Object var2) throws IOException {
      if (var2 instanceof String) {
         this.output.writeString(var1, (String)var2);
      } else {
         this.output.writeBytes(var1, (ByteString)var2);
      }
   }

   @Override
   public Writer.FieldOrder fieldOrder() {
      return Writer.FieldOrder.ASCENDING;
   }

   public int getTotalBytesWritten() {
      return this.output.getTotalBytesWritten();
   }

   @Override
   public void writeBool(int var1, boolean var2) throws IOException {
      this.output.writeBool(var1, var2);
   }

   @Override
   public void writeBoolList(int var1, List<Boolean> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeBoolSizeNoTag((Boolean)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeBoolNoTag((Boolean)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeBool(var1, (Boolean)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeBytes(int var1, ByteString var2) throws IOException {
      this.output.writeBytes(var1, var2);
   }

   @Override
   public void writeBytesList(int var1, List<ByteString> var2) throws IOException {
      for (int var3 = 0; var3 < var2.size(); var3++) {
         this.output.writeBytes(var1, (ByteString)var2.get(var3));
      }
   }

   @Override
   public void writeDouble(int var1, double var2) throws IOException {
      this.output.writeDouble(var1, var2);
   }

   @Override
   public void writeDoubleList(int var1, List<Double> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeDoubleSizeNoTag((Double)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeDoubleNoTag((Double)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeDouble(var1, (Double)var2.get(var4));
            var4++;
         }
      }
   }

   @Deprecated
   @Override
   public void writeEndGroup(int var1) throws IOException {
      this.output.writeTag(var1, 4);
   }

   @Override
   public void writeEnum(int var1, int var2) throws IOException {
      this.output.writeEnum(var1, var2);
   }

   @Override
   public void writeEnumList(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeEnumSizeNoTag((Integer)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeEnumNoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeEnum(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeFixed32(int var1, int var2) throws IOException {
      this.output.writeFixed32(var1, var2);
   }

   @Override
   public void writeFixed32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeFixed32SizeNoTag((Integer)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeFixed32NoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeFixed32(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeFixed64(int var1, long var2) throws IOException {
      this.output.writeFixed64(var1, var2);
   }

   @Override
   public void writeFixed64List(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeFixed64SizeNoTag((Long)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeFixed64NoTag((Long)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeFixed64(var1, (Long)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeFloat(int var1, float var2) throws IOException {
      this.output.writeFloat(var1, var2);
   }

   @Override
   public void writeFloatList(int var1, List<Float> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeFloatSizeNoTag((Float)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeFloatNoTag((Float)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeFloat(var1, (Float)var2.get(var4));
            var4++;
         }
      }
   }

   @Deprecated
   @Override
   public void writeGroup(int var1, Object var2) throws IOException {
      this.output.writeGroup(var1, (MessageLite)var2);
   }

   @Override
   public void writeGroup(int var1, Object var2, Schema var3) throws IOException {
      this.output.writeGroup(var1, (MessageLite)var2, var3);
   }

   @Deprecated
   @Override
   public void writeGroupList(int var1, List<?> var2) throws IOException {
      for (int var3 = 0; var3 < var2.size(); var3++) {
         this.writeGroup(var1, var2.get(var3));
      }
   }

   @Override
   public void writeGroupList(int var1, List<?> var2, Schema var3) throws IOException {
      for (int var4 = 0; var4 < var2.size(); var4++) {
         this.writeGroup(var1, var2.get(var4), var3);
      }
   }

   @Override
   public void writeInt32(int var1, int var2) throws IOException {
      this.output.writeInt32(var1, var2);
   }

   @Override
   public void writeInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeInt32SizeNoTag((Integer)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeInt32NoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeInt32(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeInt64(int var1, long var2) throws IOException {
      this.output.writeInt64(var1, var2);
   }

   @Override
   public void writeInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeInt64SizeNoTag((Long)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeInt64NoTag((Long)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeInt64(var1, (Long)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public <K, V> void writeMap(int var1, MapEntryLite.Metadata<K, V> var2, Map<K, V> var3) throws IOException {
      if (this.output.isSerializationDeterministic()) {
         this.writeDeterministicMap(var1, var2, var3);
      } else {
         for (Entry var4 : var3.entrySet()) {
            this.output.writeTag(var1, 2);
            this.output.writeUInt32NoTag(MapEntryLite.computeSerializedSize(var2, var4.getKey(), var4.getValue()));
            MapEntryLite.writeTo(this.output, var2, var4.getKey(), var4.getValue());
         }
      }
   }

   @Override
   public void writeMessage(int var1, Object var2) throws IOException {
      this.output.writeMessage(var1, (MessageLite)var2);
   }

   @Override
   public void writeMessage(int var1, Object var2, Schema var3) throws IOException {
      this.output.writeMessage(var1, (MessageLite)var2, var3);
   }

   @Override
   public void writeMessageList(int var1, List<?> var2) throws IOException {
      for (int var3 = 0; var3 < var2.size(); var3++) {
         this.writeMessage(var1, var2.get(var3));
      }
   }

   @Override
   public void writeMessageList(int var1, List<?> var2, Schema var3) throws IOException {
      for (int var4 = 0; var4 < var2.size(); var4++) {
         this.writeMessage(var1, var2.get(var4), var3);
      }
   }

   @Override
   public final void writeMessageSetItem(int var1, Object var2) throws IOException {
      if (var2 instanceof ByteString) {
         this.output.writeRawMessageSetExtension(var1, (ByteString)var2);
      } else {
         this.output.writeMessageSetExtension(var1, (MessageLite)var2);
      }
   }

   @Override
   public void writeSFixed32(int var1, int var2) throws IOException {
      this.output.writeSFixed32(var1, var2);
   }

   @Override
   public void writeSFixed32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeSFixed32SizeNoTag((Integer)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeSFixed32NoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeSFixed32(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeSFixed64(int var1, long var2) throws IOException {
      this.output.writeSFixed64(var1, var2);
   }

   @Override
   public void writeSFixed64List(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeSFixed64SizeNoTag((Long)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeSFixed64NoTag((Long)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeSFixed64(var1, (Long)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeSInt32(int var1, int var2) throws IOException {
      this.output.writeSInt32(var1, var2);
   }

   @Override
   public void writeSInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeSInt32SizeNoTag((Integer)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeSInt32NoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeSInt32(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeSInt64(int var1, long var2) throws IOException {
      this.output.writeSInt64(var1, var2);
   }

   @Override
   public void writeSInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var4 = 0;

         for (var1 = 0; var4 < var2.size(); var4++) {
            var1 += CodedOutputStream.computeSInt64SizeNoTag((Long)var2.get(var4));
         }

         this.output.writeUInt32NoTag(var1);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeSInt64NoTag((Long)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeSInt64(var1, (Long)var2.get(var4));
            var4++;
         }
      }
   }

   @Deprecated
   @Override
   public void writeStartGroup(int var1) throws IOException {
      this.output.writeTag(var1, 3);
   }

   @Override
   public void writeString(int var1, String var2) throws IOException {
      this.output.writeString(var1, var2);
   }

   @Override
   public void writeStringList(int var1, List<String> var2) throws IOException {
      boolean var5 = var2 instanceof LazyStringList;
      int var3 = 0;
      byte var4 = 0;
      if (var5) {
         LazyStringList var6 = (LazyStringList)var2;

         for (int var7 = var4; var7 < var2.size(); var7++) {
            this.writeLazyString(var1, var6.getRaw(var7));
         }
      } else {
         while (var3 < var2.size()) {
            this.output.writeString(var1, (String)var2.get(var3));
            var3++;
         }
      }
   }

   @Override
   public void writeUInt32(int var1, int var2) throws IOException {
      this.output.writeUInt32(var1, var2);
   }

   @Override
   public void writeUInt32List(int var1, List<Integer> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeUInt32SizeNoTag((Integer)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeUInt32NoTag((Integer)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeUInt32(var1, (Integer)var2.get(var4));
            var4++;
         }
      }
   }

   @Override
   public void writeUInt64(int var1, long var2) throws IOException {
      this.output.writeUInt64(var1, var2);
   }

   @Override
   public void writeUInt64List(int var1, List<Long> var2, boolean var3) throws IOException {
      int var4 = 0;
      byte var5 = 0;
      if (var3) {
         this.output.writeTag(var1, 2);
         var1 = 0;

         for (var4 = 0; var1 < var2.size(); var1++) {
            var4 += CodedOutputStream.computeUInt64SizeNoTag((Long)var2.get(var1));
         }

         this.output.writeUInt32NoTag(var4);

         for (int var7 = var5; var7 < var2.size(); var7++) {
            this.output.writeUInt64NoTag((Long)var2.get(var7));
         }
      } else {
         while (var4 < var2.size()) {
            this.output.writeUInt64(var1, (Long)var2.get(var4));
            var4++;
         }
      }
   }
}
