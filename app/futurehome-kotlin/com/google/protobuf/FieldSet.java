package com.google.protobuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class FieldSet<T extends FieldSet.FieldDescriptorLite<T>> {
   private static final int DEFAULT_FIELD_MAP_ARRAY_SIZE = 16;
   private static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
   private final SmallSortedMap<T, Object> fields;
   private boolean hasLazyField;
   private boolean isImmutable;

   private FieldSet() {
      this.fields = SmallSortedMap.newFieldMap(16);
   }

   private FieldSet(SmallSortedMap<T, Object> var1) {
      this.fields = var1;
      this.makeImmutable();
   }

   private FieldSet(boolean var1) {
      this(SmallSortedMap.newFieldMap(0));
      this.makeImmutable();
   }

   private static <T extends FieldSet.FieldDescriptorLite<T>> SmallSortedMap<T, Object> cloneAllFieldsMap(SmallSortedMap<T, Object> var0, boolean var1) {
      SmallSortedMap var3 = SmallSortedMap.newFieldMap(16);

      for (int var2 = 0; var2 < var0.getNumArrayEntries(); var2++) {
         cloneFieldEntry(var3, var0.getArrayEntryAt(var2), var1);
      }

      Iterator var4 = var0.getOverflowEntries().iterator();

      while (var4.hasNext()) {
         cloneFieldEntry(var3, (Entry<T, Object>)var4.next(), var1);
      }

      return var3;
   }

   private static <T extends FieldSet.FieldDescriptorLite<T>> void cloneFieldEntry(Map<T, Object> var0, Entry<T, Object> var1, boolean var2) {
      FieldSet.FieldDescriptorLite var3 = (FieldSet.FieldDescriptorLite)var1.getKey();
      Object var4 = var1.getValue();
      if (var4 instanceof LazyField) {
         var0.put(var3, ((LazyField)var4).getValue());
      } else if (var2 && var4 instanceof List) {
         var0.put(var3, new ArrayList((List)var4));
      } else {
         var0.put(var3, var4);
      }
   }

   private static Object cloneIfMutable(Object var0) {
      if (var0 instanceof byte[]) {
         var0 = var0;
         byte[] var1 = new byte[var0.length];
         System.arraycopy(var0, 0, var1, 0, var0.length);
         return var1;
      } else {
         return var0;
      }
   }

   static int computeElementSize(WireFormat.FieldType var0, int var1, Object var2) {
      int var3 = CodedOutputStream.computeTagSize(var1);
      var1 = var3;
      if (var0 == WireFormat.FieldType.GROUP) {
         var1 = var3 * 2;
      }

      return var1 + computeElementSizeNoTag(var0, var2);
   }

   static int computeElementSizeNoTag(WireFormat.FieldType var0, Object var1) {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var0.ordinal()]) {
         case 1:
            return CodedOutputStream.computeDoubleSizeNoTag((Double)var1);
         case 2:
            return CodedOutputStream.computeFloatSizeNoTag((Float)var1);
         case 3:
            return CodedOutputStream.computeInt64SizeNoTag((Long)var1);
         case 4:
            return CodedOutputStream.computeUInt64SizeNoTag((Long)var1);
         case 5:
            return CodedOutputStream.computeInt32SizeNoTag((Integer)var1);
         case 6:
            return CodedOutputStream.computeFixed64SizeNoTag((Long)var1);
         case 7:
            return CodedOutputStream.computeFixed32SizeNoTag((Integer)var1);
         case 8:
            return CodedOutputStream.computeBoolSizeNoTag((Boolean)var1);
         case 9:
            return CodedOutputStream.computeGroupSizeNoTag((MessageLite)var1);
         case 10:
            if (var1 instanceof LazyField) {
               return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField)var1);
            }

            return CodedOutputStream.computeMessageSizeNoTag((MessageLite)var1);
         case 11:
            if (var1 instanceof ByteString) {
               return CodedOutputStream.computeBytesSizeNoTag((ByteString)var1);
            }

            return CodedOutputStream.computeStringSizeNoTag((String)var1);
         case 12:
            if (var1 instanceof ByteString) {
               return CodedOutputStream.computeBytesSizeNoTag((ByteString)var1);
            }

            return CodedOutputStream.computeByteArraySizeNoTag((byte[])var1);
         case 13:
            return CodedOutputStream.computeUInt32SizeNoTag((Integer)var1);
         case 14:
            return CodedOutputStream.computeSFixed32SizeNoTag((Integer)var1);
         case 15:
            return CodedOutputStream.computeSFixed64SizeNoTag((Long)var1);
         case 16:
            return CodedOutputStream.computeSInt32SizeNoTag((Integer)var1);
         case 17:
            return CodedOutputStream.computeSInt64SizeNoTag((Long)var1);
         case 18:
            if (var1 instanceof Internal.EnumLite) {
               return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite)var1).getNumber());
            }

            return CodedOutputStream.computeEnumSizeNoTag((Integer)var1);
         default:
            throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
      }
   }

   public static int computeFieldSize(FieldSet.FieldDescriptorLite<?> var0, Object var1) {
      WireFormat.FieldType var6 = var0.getLiteType();
      int var4 = var0.getNumber();
      if (!var0.isRepeated()) {
         return computeElementSize(var6, var4, var1);
      } else {
         var1 = var1;
         boolean var5 = var0.isPacked();
         int var2 = 0;
         byte var3 = 0;
         if (var5) {
            if (var1.isEmpty()) {
               return 0;
            } else {
               Iterator var8 = var1.iterator();
               var2 = var3;

               while (var8.hasNext()) {
                  var2 += computeElementSizeNoTag(var6, var8.next());
               }

               return CodedOutputStream.computeTagSize(var4) + var2 + CodedOutputStream.computeUInt32SizeNoTag(var2);
            }
         } else {
            Iterator var7 = var1.iterator();

            while (var7.hasNext()) {
               var2 += computeElementSize(var6, var4, var7.next());
            }

            return var2;
         }
      }
   }

   public static <T extends FieldSet.FieldDescriptorLite<T>> FieldSet<T> emptySet() {
      return DEFAULT_INSTANCE;
   }

   private int getMessageSetSerializedSize(Entry<T, Object> var1) {
      FieldSet.FieldDescriptorLite var3 = (FieldSet.FieldDescriptorLite)var1.getKey();
      Object var2 = var1.getValue();
      if (var3.getLiteJavaType() != WireFormat.JavaType.MESSAGE || var3.isRepeated() || var3.isPacked()) {
         return computeFieldSize(var3, var2);
      } else {
         return var2 instanceof LazyField
            ? CodedOutputStream.computeLazyFieldMessageSetExtensionSize(((FieldSet.FieldDescriptorLite)var1.getKey()).getNumber(), (LazyField)var2)
            : CodedOutputStream.computeMessageSetExtensionSize(((FieldSet.FieldDescriptorLite)var1.getKey()).getNumber(), (MessageLite)var2);
      }
   }

   static int getWireFormatForFieldType(WireFormat.FieldType var0, boolean var1) {
      return var1 ? 2 : var0.getWireType();
   }

   private static <T extends FieldSet.FieldDescriptorLite<T>> boolean isInitialized(Entry<T, Object> var0) {
      FieldSet.FieldDescriptorLite var1 = (FieldSet.FieldDescriptorLite)var0.getKey();
      if (var1.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
         if (!var1.isRepeated()) {
            return isMessageFieldValueInitialized(var0.getValue());
         }

         Iterator var2 = ((List)var0.getValue()).iterator();

         while (var2.hasNext()) {
            if (!isMessageFieldValueInitialized(var2.next())) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isMessageFieldValueInitialized(Object var0) {
      if (var0 instanceof MessageLiteOrBuilder) {
         return ((MessageLiteOrBuilder)var0).isInitialized();
      } else if (var0 instanceof LazyField) {
         return true;
      } else {
         throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
      }
   }

   private static boolean isValidType(WireFormat.FieldType var0, Object var1) {
      Internal.checkNotNull(var1);
      int var2 = <unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$JavaType[var0.getJavaType().ordinal()];
      boolean var5 = true;
      boolean var6 = true;
      boolean var4 = true;
      switch (var2) {
         case 1:
            return var1 instanceof Integer;
         case 2:
            return var1 instanceof Long;
         case 3:
            return var1 instanceof Float;
         case 4:
            return var1 instanceof Double;
         case 5:
            return var1 instanceof Boolean;
         case 6:
            return var1 instanceof String;
         case 7:
            boolean var8 = var6;
            if (!(var1 instanceof ByteString)) {
               if (var1 instanceof byte[]) {
                  var8 = var6;
               } else {
                  var8 = false;
               }
            }

            return var8;
         case 8:
            boolean var7 = var5;
            if (!(var1 instanceof Integer)) {
               if (var1 instanceof Internal.EnumLite) {
                  var7 = var5;
               } else {
                  var7 = false;
               }
            }

            return var7;
         case 9:
            boolean var3 = var4;
            if (!(var1 instanceof MessageLite)) {
               if (var1 instanceof LazyField) {
                  var3 = var4;
               } else {
                  var3 = false;
               }
            }

            return var3;
         default:
            return false;
      }
   }

   private void mergeFromField(Entry<T, Object> var1) {
      FieldSet.FieldDescriptorLite var4 = (FieldSet.FieldDescriptorLite)var1.getKey();
      Object var2 = var1.getValue();
      Object var5 = var2;
      if (var2 instanceof LazyField) {
         var5 = ((LazyField)var2).getValue();
      }

      if (var4.isRepeated()) {
         Object var3 = this.getField((T)var4);
         var2 = var3;
         if (var3 == null) {
            var2 = new ArrayList();
         }

         for (var5 : (List)var5) {
            ((List)var2).add(cloneIfMutable(var5));
         }

         this.fields.put((T)var4, var2);
      } else if (var4.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
         var2 = this.getField((T)var4);
         if (var2 == null) {
            this.fields.put((T)var4, cloneIfMutable(var5));
         } else {
            var5 = var4.internalMergeFrom(((MessageLite)var2).toBuilder(), (MessageLite)var5).build();
            this.fields.put((T)var4, var5);
         }
      } else {
         this.fields.put((T)var4, cloneIfMutable(var5));
      }
   }

   public static <T extends FieldSet.FieldDescriptorLite<T>> FieldSet.Builder<T> newBuilder() {
      return new FieldSet.Builder<>();
   }

   public static <T extends FieldSet.FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
      return new FieldSet<>();
   }

   public static Object readPrimitiveField(CodedInputStream var0, WireFormat.FieldType var1, boolean var2) throws IOException {
      return var2
         ? WireFormat.readPrimitiveField(var0, var1, WireFormat.Utf8Validation.STRICT)
         : WireFormat.readPrimitiveField(var0, var1, WireFormat.Utf8Validation.LOOSE);
   }

   private void verifyType(T var1, Object var2) {
      if (!isValidType(var1.getLiteType(), var2)) {
         throw new IllegalArgumentException(
            String.format(
               "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n",
               var1.getNumber(),
               var1.getLiteType().getJavaType(),
               var2.getClass().getName()
            )
         );
      }
   }

   static void writeElement(CodedOutputStream var0, WireFormat.FieldType var1, int var2, Object var3) throws IOException {
      if (var1 == WireFormat.FieldType.GROUP) {
         var0.writeGroup(var2, (MessageLite)var3);
      } else {
         var0.writeTag(var2, getWireFormatForFieldType(var1, false));
         writeElementNoTag(var0, var1, var3);
      }
   }

   static void writeElementNoTag(CodedOutputStream var0, WireFormat.FieldType var1, Object var2) throws IOException {
      switch (<unrepresentable>.$SwitchMap$com$google$protobuf$WireFormat$FieldType[var1.ordinal()]) {
         case 1:
            var0.writeDoubleNoTag((Double)var2);
            break;
         case 2:
            var0.writeFloatNoTag((Float)var2);
            break;
         case 3:
            var0.writeInt64NoTag((Long)var2);
            break;
         case 4:
            var0.writeUInt64NoTag((Long)var2);
            break;
         case 5:
            var0.writeInt32NoTag((Integer)var2);
            break;
         case 6:
            var0.writeFixed64NoTag((Long)var2);
            break;
         case 7:
            var0.writeFixed32NoTag((Integer)var2);
            break;
         case 8:
            var0.writeBoolNoTag((Boolean)var2);
            break;
         case 9:
            var0.writeGroupNoTag((MessageLite)var2);
            break;
         case 10:
            var0.writeMessageNoTag((MessageLite)var2);
            break;
         case 11:
            if (var2 instanceof ByteString) {
               var0.writeBytesNoTag((ByteString)var2);
            } else {
               var0.writeStringNoTag((String)var2);
            }
            break;
         case 12:
            if (var2 instanceof ByteString) {
               var0.writeBytesNoTag((ByteString)var2);
            } else {
               var0.writeByteArrayNoTag((byte[])var2);
            }
            break;
         case 13:
            var0.writeUInt32NoTag((Integer)var2);
            break;
         case 14:
            var0.writeSFixed32NoTag((Integer)var2);
            break;
         case 15:
            var0.writeSFixed64NoTag((Long)var2);
            break;
         case 16:
            var0.writeSInt32NoTag((Integer)var2);
            break;
         case 17:
            var0.writeSInt64NoTag((Long)var2);
            break;
         case 18:
            if (var2 instanceof Internal.EnumLite) {
               var0.writeEnumNoTag(((Internal.EnumLite)var2).getNumber());
            } else {
               var0.writeEnumNoTag((Integer)var2);
            }
      }
   }

   public static void writeField(FieldSet.FieldDescriptorLite<?> var0, Object var1, CodedOutputStream var2) throws IOException {
      WireFormat.FieldType var4 = var0.getLiteType();
      int var3 = var0.getNumber();
      if (var0.isRepeated()) {
         var1 = var1;
         if (var0.isPacked()) {
            if (var1.isEmpty()) {
               return;
            }

            var2.writeTag(var3, 2);
            Iterator var5 = var1.iterator();
            var3 = 0;

            while (var5.hasNext()) {
               var3 += computeElementSizeNoTag(var4, var5.next());
            }

            var2.writeUInt32NoTag(var3);
            Iterator var6 = var1.iterator();

            while (var6.hasNext()) {
               writeElementNoTag(var2, var4, var6.next());
            }
         } else {
            Iterator var7 = var1.iterator();

            while (var7.hasNext()) {
               writeElement(var2, var4, var3, var7.next());
            }
         }
      } else if (var1 instanceof LazyField) {
         writeElement(var2, var4, var3, ((LazyField)var1).getValue());
      } else {
         writeElement(var2, var4, var3, var1);
      }
   }

   private void writeMessageSetTo(Entry<T, Object> var1, CodedOutputStream var2) throws IOException {
      FieldSet.FieldDescriptorLite var3 = (FieldSet.FieldDescriptorLite)var1.getKey();
      if (var3.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !var3.isRepeated() && !var3.isPacked()) {
         var3 = (FieldSet.FieldDescriptorLite)var1.getValue();
         if (var3 instanceof LazyField) {
            ByteString var5 = ((LazyField)var3).toByteString();
            var2.writeRawMessageSetExtension(((FieldSet.FieldDescriptorLite)var1.getKey()).getNumber(), var5);
         } else {
            var2.writeMessageSetExtension(((FieldSet.FieldDescriptorLite)var1.getKey()).getNumber(), (MessageLite)var3);
         }
      } else {
         writeField(var3, var1.getValue(), var2);
      }
   }

   public void addRepeatedField(T var1, Object var2) {
      if (var1.isRepeated()) {
         this.verifyType((T)var1, var2);
         Object var3 = this.getField((T)var1);
         Object var4;
         if (var3 == null) {
            var3 = new ArrayList();
            this.fields.put((T)var1, var3);
            var4 = var3;
         } else {
            var4 = (List)var3;
         }

         var4.add(var2);
      } else {
         throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
      }
   }

   public void clear() {
      this.fields.clear();
      this.hasLazyField = false;
   }

   public void clearField(T var1) {
      this.fields.remove(var1);
      if (this.fields.isEmpty()) {
         this.hasLazyField = false;
      }
   }

   public FieldSet<T> clone() {
      FieldSet var2 = newFieldSet();

      for (int var1 = 0; var1 < this.fields.getNumArrayEntries(); var1++) {
         Entry var3 = this.fields.getArrayEntryAt(var1);
         var2.setField((T)var3.getKey(), var3.getValue());
      }

      for (Entry var4 : this.fields.getOverflowEntries()) {
         var2.setField((T)var4.getKey(), var4.getValue());
      }

      var2.hasLazyField = this.hasLazyField;
      return var2;
   }

   Iterator<Entry<T, Object>> descendingIterator() {
      return (Iterator<Entry<T, Object>>)(this.hasLazyField
         ? new LazyField.LazyIterator<>(this.fields.descendingEntrySet().iterator())
         : this.fields.descendingEntrySet().iterator());
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof FieldSet)) {
         return false;
      } else {
         var1 = var1;
         return this.fields.equals(var1.fields);
      }
   }

   public Map<T, Object> getAllFields() {
      if (this.hasLazyField) {
         SmallSortedMap var2 = cloneAllFieldsMap(this.fields, false);
         if (this.fields.isImmutable()) {
            var2.makeImmutable();
         }

         return var2;
      } else {
         Object var1;
         if (this.fields.isImmutable()) {
            var1 = this.fields;
         } else {
            var1 = Collections.unmodifiableMap(this.fields);
         }

         return (Map<T, Object>)var1;
      }
   }

   public Object getField(T var1) {
      Object var2 = this.fields.get(var1);
      Object var3 = var2;
      if (var2 instanceof LazyField) {
         var3 = ((LazyField)var2).getValue();
      }

      return var3;
   }

   public int getMessageSetSerializedSize() {
      int var2 = 0;

      int var1;
      for (var1 = 0; var2 < this.fields.getNumArrayEntries(); var2++) {
         var1 += this.getMessageSetSerializedSize(this.fields.getArrayEntryAt(var2));
      }

      Iterator var3 = this.fields.getOverflowEntries().iterator();

      while (var3.hasNext()) {
         var1 += this.getMessageSetSerializedSize((Entry<T, Object>)var3.next());
      }

      return var1;
   }

   public Object getRepeatedField(T var1, int var2) {
      if (var1.isRepeated()) {
         Object var3 = this.getField((T)var1);
         if (var3 != null) {
            return ((List)var3).get(var2);
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
      }
   }

   public int getRepeatedFieldCount(T var1) {
      if (var1.isRepeated()) {
         Object var2 = this.getField((T)var1);
         return var2 == null ? 0 : ((List)var2).size();
      } else {
         throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
      }
   }

   public int getSerializedSize() {
      int var2 = 0;

      int var1;
      for (var1 = 0; var2 < this.fields.getNumArrayEntries(); var2++) {
         Entry var3 = this.fields.getArrayEntryAt(var2);
         var1 += computeFieldSize((FieldSet.FieldDescriptorLite<?>)var3.getKey(), var3.getValue());
      }

      for (Entry var4 : this.fields.getOverflowEntries()) {
         var1 += computeFieldSize((FieldSet.FieldDescriptorLite<?>)var4.getKey(), var4.getValue());
      }

      return var1;
   }

   public boolean hasField(T var1) {
      if (!var1.isRepeated()) {
         boolean var2;
         if (this.fields.get(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      } else {
         throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
      }
   }

   @Override
   public int hashCode() {
      return this.fields.hashCode();
   }

   boolean isEmpty() {
      return this.fields.isEmpty();
   }

   public boolean isImmutable() {
      return this.isImmutable;
   }

   public boolean isInitialized() {
      for (int var1 = 0; var1 < this.fields.getNumArrayEntries(); var1++) {
         if (!isInitialized(this.fields.getArrayEntryAt(var1))) {
            return false;
         }
      }

      Iterator var2 = this.fields.getOverflowEntries().iterator();

      while (var2.hasNext()) {
         if (!isInitialized((Entry<T, Object>)var2.next())) {
            return false;
         }
      }

      return true;
   }

   public Iterator<Entry<T, Object>> iterator() {
      return (Iterator<Entry<T, Object>>)(this.hasLazyField
         ? new LazyField.LazyIterator<>(this.fields.entrySet().iterator())
         : this.fields.entrySet().iterator());
   }

   public void makeImmutable() {
      if (!this.isImmutable) {
         for (int var1 = 0; var1 < this.fields.getNumArrayEntries(); var1++) {
            Entry var2 = this.fields.getArrayEntryAt(var1);
            if (var2.getValue() instanceof GeneratedMessageLite) {
               ((GeneratedMessageLite)var2.getValue()).makeImmutable();
            }
         }

         this.fields.makeImmutable();
         this.isImmutable = true;
      }
   }

   public void mergeFrom(FieldSet<T> var1) {
      for (int var2 = 0; var2 < var1.fields.getNumArrayEntries(); var2++) {
         this.mergeFromField(var1.fields.getArrayEntryAt(var2));
      }

      Iterator var3 = var1.fields.getOverflowEntries().iterator();

      while (var3.hasNext()) {
         this.mergeFromField((Entry<T, Object>)var3.next());
      }
   }

   public void setField(T var1, Object var2) {
      if (var1.isRepeated()) {
         if (!(var2 instanceof List)) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
         }

         ArrayList var3 = new ArrayList();
         var3.addAll((List)var2);
         var2 = var3.iterator();

         while (var2.hasNext()) {
            this.verifyType((T)var1, var2.next());
         }

         var2 = var3;
      } else {
         this.verifyType((T)var1, var2);
      }

      if (var2 instanceof LazyField) {
         this.hasLazyField = true;
      }

      this.fields.put((T)var1, var2);
   }

   public void setRepeatedField(T var1, int var2, Object var3) {
      if (var1.isRepeated()) {
         Object var4 = this.getField((T)var1);
         if (var4 != null) {
            this.verifyType((T)var1, var3);
            ((List)var4).set(var2, var3);
         } else {
            throw new IndexOutOfBoundsException();
         }
      } else {
         throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
      }
   }

   public void writeMessageSetTo(CodedOutputStream var1) throws IOException {
      for (int var2 = 0; var2 < this.fields.getNumArrayEntries(); var2++) {
         this.writeMessageSetTo(this.fields.getArrayEntryAt(var2), var1);
      }

      Iterator var3 = this.fields.getOverflowEntries().iterator();

      while (var3.hasNext()) {
         this.writeMessageSetTo((Entry<T, Object>)var3.next(), var1);
      }
   }

   public void writeTo(CodedOutputStream var1) throws IOException {
      for (int var2 = 0; var2 < this.fields.getNumArrayEntries(); var2++) {
         Entry var3 = this.fields.getArrayEntryAt(var2);
         writeField((FieldSet.FieldDescriptorLite<?>)var3.getKey(), var3.getValue(), var1);
      }

      for (Entry var5 : this.fields.getOverflowEntries()) {
         writeField((FieldSet.FieldDescriptorLite<?>)var5.getKey(), var5.getValue(), var1);
      }
   }

   static final class Builder<T extends FieldSet.FieldDescriptorLite<T>> {
      private SmallSortedMap<T, Object> fields;
      private boolean hasLazyField;
      private boolean hasNestedBuilders;
      private boolean isMutable;

      private Builder() {
         this(SmallSortedMap.newFieldMap(16));
      }

      private Builder(SmallSortedMap<T, Object> var1) {
         this.fields = var1;
         this.isMutable = true;
      }

      private FieldSet<T> buildImpl(boolean var1) {
         if (this.fields.isEmpty()) {
            return FieldSet.emptySet();
         } else {
            this.isMutable = false;
            SmallSortedMap var3 = this.fields;
            SmallSortedMap var2 = var3;
            if (this.hasNestedBuilders) {
               var2 = FieldSet.cloneAllFieldsMap(var3, false);
               replaceBuilders(var2, var1);
            }

            FieldSet var4 = new FieldSet(var2);
            var4.hasLazyField = this.hasLazyField;
            return var4;
         }
      }

      private void ensureIsMutable() {
         if (!this.isMutable) {
            this.fields = FieldSet.cloneAllFieldsMap(this.fields, true);
            this.isMutable = true;
         }
      }

      public static <T extends FieldSet.FieldDescriptorLite<T>> FieldSet.Builder<T> fromFieldSet(FieldSet<T> var0) {
         FieldSet.Builder var1 = new FieldSet.Builder<>(FieldSet.cloneAllFieldsMap(var0.fields, true));
         var1.hasLazyField = var0.hasLazyField;
         return var1;
      }

      private void mergeFromField(Entry<T, Object> var1) {
         FieldSet.FieldDescriptorLite var4 = (FieldSet.FieldDescriptorLite)var1.getKey();
         Object var2 = var1.getValue();
         Object var5 = var2;
         if (var2 instanceof LazyField) {
            var5 = ((LazyField)var2).getValue();
         }

         if (var4.isRepeated()) {
            List var3 = (List)this.getFieldAllowBuilders((T)var4);
            var2 = var3;
            if (var3 == null) {
               var2 = new ArrayList();
               this.fields.put((T)var4, var2);
            }

            var5 = ((List)var5).iterator();

            while (var5.hasNext()) {
               var2.add(FieldSet.cloneIfMutable(var5.next()));
            }
         } else if (var4.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            var2 = this.getFieldAllowBuilders((T)var4);
            if (var2 == null) {
               this.fields.put((T)var4, FieldSet.cloneIfMutable(var5));
            } else if (var2 instanceof MessageLite.Builder) {
               var4.internalMergeFrom((MessageLite.Builder)var2, (MessageLite)var5);
            } else {
               var5 = var4.internalMergeFrom(((MessageLite)var2).toBuilder(), (MessageLite)var5).build();
               this.fields.put((T)var4, var5);
            }
         } else {
            this.fields.put((T)var4, FieldSet.cloneIfMutable(var5));
         }
      }

      private static Object replaceBuilder(Object var0, boolean var1) {
         if (!(var0 instanceof MessageLite.Builder)) {
            return var0;
         } else {
            var0 = var0;
            return var1 ? var0.buildPartial() : var0.build();
         }
      }

      private static <T extends FieldSet.FieldDescriptorLite<T>> Object replaceBuilders(T var0, Object var1, boolean var2) {
         if (var1 == null) {
            return var1;
         } else if (var0.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (var0.isRepeated()) {
               if (var1 instanceof List) {
                  Object var8 = (List)var1;
                  int var3 = 0;

                  while (var3 < var8.size()) {
                     Object var6 = var8.get(var3);
                     Object var5 = replaceBuilder(var6, var2);
                     Object var4 = var8;
                     if (var5 != var6) {
                        var4 = var8;
                        if (var8 == var1) {
                           var4 = new ArrayList((Collection)var8);
                        }

                        var4.set(var3, var5);
                     }

                     var3++;
                     var8 = var4;
                  }

                  return var8;
               } else {
                  StringBuilder var7 = new StringBuilder("Repeated field should contains a List but actually contains type: ");
                  var7.append(var1.getClass());
                  throw new IllegalStateException(var7.toString());
               }
            } else {
               return replaceBuilder(var1, var2);
            }
         } else {
            return var1;
         }
      }

      private static <T extends FieldSet.FieldDescriptorLite<T>> void replaceBuilders(SmallSortedMap<T, Object> var0, boolean var1) {
         for (int var2 = 0; var2 < var0.getNumArrayEntries(); var2++) {
            replaceBuilders(var0.getArrayEntryAt(var2), var1);
         }

         Iterator var3 = var0.getOverflowEntries().iterator();

         while (var3.hasNext()) {
            replaceBuilders((Entry<T, Object>)var3.next(), var1);
         }
      }

      private static <T extends FieldSet.FieldDescriptorLite<T>> void replaceBuilders(Entry<T, Object> var0, boolean var1) {
         var0.setValue(replaceBuilders((T)var0.getKey(), var0.getValue(), var1));
      }

      private void verifyType(T var1, Object var2) {
         if (!FieldSet.isValidType(var1.getLiteType(), var2)) {
            if (var1.getLiteType().getJavaType() != WireFormat.JavaType.MESSAGE || !(var2 instanceof MessageLite.Builder)) {
               throw new IllegalArgumentException(
                  String.format(
                     "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n",
                     var1.getNumber(),
                     var1.getLiteType().getJavaType(),
                     var2.getClass().getName()
                  )
               );
            }
         }
      }

      public void addRepeatedField(T var1, Object var2) {
         this.ensureIsMutable();
         if (!var1.isRepeated()) {
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
         } else {
            boolean var3;
            if (!this.hasNestedBuilders && !(var2 instanceof MessageLite.Builder)) {
               var3 = false;
            } else {
               var3 = true;
            }

            this.hasNestedBuilders = var3;
            this.verifyType((T)var1, var2);
            Object var4 = this.getFieldAllowBuilders((T)var1);
            Object var5;
            if (var4 == null) {
               var4 = new ArrayList();
               this.fields.put((T)var1, var4);
               var5 = var4;
            } else {
               var5 = (List)var4;
            }

            var5.add(var2);
         }
      }

      public FieldSet<T> build() {
         return this.buildImpl(false);
      }

      public FieldSet<T> buildPartial() {
         return this.buildImpl(true);
      }

      public void clearField(T var1) {
         this.ensureIsMutable();
         this.fields.remove(var1);
         if (this.fields.isEmpty()) {
            this.hasLazyField = false;
         }
      }

      public Map<T, Object> getAllFields() {
         if (this.hasLazyField) {
            SmallSortedMap var2 = FieldSet.cloneAllFieldsMap(this.fields, false);
            if (this.fields.isImmutable()) {
               var2.makeImmutable();
            } else {
               replaceBuilders(var2, true);
            }

            return var2;
         } else {
            Object var1;
            if (this.fields.isImmutable()) {
               var1 = this.fields;
            } else {
               var1 = Collections.unmodifiableMap(this.fields);
            }

            return (Map<T, Object>)var1;
         }
      }

      public Object getField(T var1) {
         return replaceBuilders((T)var1, this.getFieldAllowBuilders((T)var1), true);
      }

      Object getFieldAllowBuilders(T var1) {
         Object var2 = this.fields.get(var1);
         Object var3 = var2;
         if (var2 instanceof LazyField) {
            var3 = ((LazyField)var2).getValue();
         }

         return var3;
      }

      public Object getRepeatedField(T var1, int var2) {
         if (this.hasNestedBuilders) {
            this.ensureIsMutable();
         }

         return replaceBuilder(this.getRepeatedFieldAllowBuilders((T)var1, var2), true);
      }

      Object getRepeatedFieldAllowBuilders(T var1, int var2) {
         if (var1.isRepeated()) {
            Object var3 = this.getFieldAllowBuilders((T)var1);
            if (var3 != null) {
               return ((List)var3).get(var2);
            } else {
               throw new IndexOutOfBoundsException();
            }
         } else {
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
         }
      }

      public int getRepeatedFieldCount(T var1) {
         if (var1.isRepeated()) {
            Object var2 = this.getFieldAllowBuilders((T)var1);
            return var2 == null ? 0 : ((List)var2).size();
         } else {
            throw new IllegalArgumentException("getRepeatedFieldCount() can only be called on repeated fields.");
         }
      }

      public boolean hasField(T var1) {
         if (!var1.isRepeated()) {
            boolean var2;
            if (this.fields.get(var1) != null) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         } else {
            throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
         }
      }

      public boolean isInitialized() {
         for (int var1 = 0; var1 < this.fields.getNumArrayEntries(); var1++) {
            if (!FieldSet.isInitialized(this.fields.getArrayEntryAt(var1))) {
               return false;
            }
         }

         Iterator var2 = this.fields.getOverflowEntries().iterator();

         while (var2.hasNext()) {
            if (!FieldSet.isInitialized((Entry<T, Object>)var2.next())) {
               return false;
            }
         }

         return true;
      }

      public void mergeFrom(FieldSet<T> var1) {
         this.ensureIsMutable();

         for (int var2 = 0; var2 < var1.fields.getNumArrayEntries(); var2++) {
            this.mergeFromField(var1.fields.getArrayEntryAt(var2));
         }

         Iterator var3 = var1.fields.getOverflowEntries().iterator();

         while (var3.hasNext()) {
            this.mergeFromField((Entry<T, Object>)var3.next());
         }
      }

      public void setField(T var1, Object var2) {
         this.ensureIsMutable();
         boolean var3 = var1.isRepeated();
         boolean var4 = false;
         if (var3) {
            if (!(var2 instanceof List)) {
               throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }

            var2 = new ArrayList((List)var2);

            for (Object var6 : var2) {
               this.verifyType((T)var1, var6);
               if (!this.hasNestedBuilders && !(var6 instanceof MessageLite.Builder)) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               this.hasNestedBuilders = var3;
            }
         } else {
            this.verifyType((T)var1, var2);
         }

         if (var2 instanceof LazyField) {
            this.hasLazyField = true;
         }

         label29: {
            if (!this.hasNestedBuilders) {
               var3 = var4;
               if (!(var2 instanceof MessageLite.Builder)) {
                  break label29;
               }
            }

            var3 = true;
         }

         this.hasNestedBuilders = var3;
         this.fields.put((T)var1, var2);
      }

      public void setRepeatedField(T var1, int var2, Object var3) {
         this.ensureIsMutable();
         if (!var1.isRepeated()) {
            throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
         } else {
            boolean var4;
            if (!this.hasNestedBuilders && !(var3 instanceof MessageLite.Builder)) {
               var4 = false;
            } else {
               var4 = true;
            }

            this.hasNestedBuilders = var4;
            Object var5 = this.getFieldAllowBuilders((T)var1);
            if (var5 != null) {
               this.verifyType((T)var1, var3);
               ((List)var5).set(var2, var3);
            } else {
               throw new IndexOutOfBoundsException();
            }
         }
      }
   }

   public interface FieldDescriptorLite<T extends FieldSet.FieldDescriptorLite<T>> extends Comparable<T> {
      Internal.EnumLiteMap<?> getEnumType();

      WireFormat.JavaType getLiteJavaType();

      WireFormat.FieldType getLiteType();

      int getNumber();

      MessageLite.Builder internalMergeFrom(MessageLite.Builder var1, MessageLite var2);

      boolean isPacked();

      boolean isRepeated();
   }
}
