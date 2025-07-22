package com.google.protobuf;

import java.io.IOException;
import java.util.Arrays;

public final class UnknownFieldSetLite {
   private static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
   private static final int MIN_CAPACITY = 8;
   private int count;
   private boolean isMutable;
   private int memoizedSerializedSize = -1;
   private Object[] objects;
   private int[] tags;

   private UnknownFieldSetLite() {
      this(0, new int[8], new Object[8], true);
   }

   private UnknownFieldSetLite(int var1, int[] var2, Object[] var3, boolean var4) {
      this.count = var1;
      this.tags = var2;
      this.objects = var3;
      this.isMutable = var4;
   }

   private void ensureCapacity(int var1) {
      int[] var3 = this.tags;
      if (var1 > var3.length) {
         int var2 = this.count;
         var2 += var2 / 2;
         if (var2 >= var1) {
            var1 = var2;
         }

         var2 = var1;
         if (var1 < 8) {
            var2 = 8;
         }

         this.tags = Arrays.copyOf(var3, var2);
         this.objects = Arrays.copyOf(this.objects, var2);
      }
   }

   public static UnknownFieldSetLite getDefaultInstance() {
      return DEFAULT_INSTANCE;
   }

   private static int hashCode(int[] var0, int var1) {
      int var3 = 17;

      for (int var2 = 0; var2 < var1; var2++) {
         var3 = var3 * 31 + var0[var2];
      }

      return var3;
   }

   private static int hashCode(Object[] var0, int var1) {
      int var3 = 17;

      for (int var2 = 0; var2 < var1; var2++) {
         var3 = var3 * 31 + var0[var2].hashCode();
      }

      return var3;
   }

   private UnknownFieldSetLite mergeFrom(CodedInputStream var1) throws IOException {
      int var2;
      do {
         var2 = var1.readTag();
      } while (var2 != 0 && this.mergeFieldFrom(var2, var1));

      return this;
   }

   static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite var0, UnknownFieldSetLite var1) {
      int var2 = var0.count + var1.count;
      int[] var4 = Arrays.copyOf(var0.tags, var2);
      System.arraycopy(var1.tags, 0, var4, var0.count, var1.count);
      Object[] var3 = Arrays.copyOf(var0.objects, var2);
      System.arraycopy(var1.objects, 0, var3, var0.count, var1.count);
      return new UnknownFieldSetLite(var2, var4, var3, true);
   }

   static UnknownFieldSetLite newInstance() {
      return new UnknownFieldSetLite();
   }

   private static boolean objectsEquals(Object[] var0, Object[] var1, int var2) {
      for (int var3 = 0; var3 < var2; var3++) {
         if (!var0[var3].equals(var1[var3])) {
            return false;
         }
      }

      return true;
   }

   private static boolean tagsEquals(int[] var0, int[] var1, int var2) {
      for (int var3 = 0; var3 < var2; var3++) {
         if (var0[var3] != var1[var3]) {
            return false;
         }
      }

      return true;
   }

   private static void writeField(int var0, Object var1, Writer var2) throws IOException {
      int var3 = WireFormat.getTagFieldNumber(var0);
      var0 = WireFormat.getTagWireType(var0);
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               if (var0 != 3) {
                  if (var0 != 5) {
                     throw new RuntimeException(InvalidProtocolBufferException.invalidWireType());
                  }

                  var2.writeFixed32(var3, (Integer)var1);
               } else if (var2.fieldOrder() == Writer.FieldOrder.ASCENDING) {
                  var2.writeStartGroup(var3);
                  ((UnknownFieldSetLite)var1).writeTo(var2);
                  var2.writeEndGroup(var3);
               } else {
                  var2.writeEndGroup(var3);
                  ((UnknownFieldSetLite)var1).writeTo(var2);
                  var2.writeStartGroup(var3);
               }
            } else {
               var2.writeBytes(var3, (ByteString)var1);
            }
         } else {
            var2.writeFixed64(var3, (Long)var1);
         }
      } else {
         var2.writeInt64(var3, (Long)var1);
      }
   }

   void checkMutable() {
      if (!this.isMutable) {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof UnknownFieldSetLite)) {
         return false;
      } else {
         var1 = var1;
         int var2 = this.count;
         return var2 == var1.count && tagsEquals(this.tags, var1.tags, var2) && objectsEquals(this.objects, var1.objects, this.count);
      }
   }

   public int getSerializedSize() {
      int var1 = this.memoizedSerializedSize;
      if (var1 != -1) {
         return var1;
      } else {
         int var2 = 0;

         int var3;
         for (var3 = 0; var2 < this.count; var2++) {
            int var4 = this.tags[var2];
            var1 = WireFormat.getTagFieldNumber(var4);
            var4 = WireFormat.getTagWireType(var4);
            if (var4 != 0) {
               if (var4 != 1) {
                  if (var4 != 2) {
                     if (var4 != 3) {
                        if (var4 != 5) {
                           throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                        }

                        var1 = CodedOutputStream.computeFixed32Size(var1, (Integer)this.objects[var2]);
                     } else {
                        var1 = CodedOutputStream.computeTagSize(var1) * 2 + ((UnknownFieldSetLite)this.objects[var2]).getSerializedSize();
                     }
                  } else {
                     var1 = CodedOutputStream.computeBytesSize(var1, (ByteString)this.objects[var2]);
                  }
               } else {
                  var1 = CodedOutputStream.computeFixed64Size(var1, (Long)this.objects[var2]);
               }
            } else {
               var1 = CodedOutputStream.computeUInt64Size(var1, (Long)this.objects[var2]);
            }

            var3 += var1;
         }

         this.memoizedSerializedSize = var3;
         return var3;
      }
   }

   public int getSerializedSizeAsMessageSet() {
      int var1 = this.memoizedSerializedSize;
      if (var1 != -1) {
         return var1;
      } else {
         var1 = 0;

         int var2;
         for (var2 = 0; var1 < this.count; var1++) {
            var2 += CodedOutputStream.computeRawMessageSetExtensionSize(WireFormat.getTagFieldNumber(this.tags[var1]), (ByteString)this.objects[var1]);
         }

         this.memoizedSerializedSize = var2;
         return var2;
      }
   }

   @Override
   public int hashCode() {
      int var1 = this.count;
      return ((527 + var1) * 31 + hashCode(this.tags, var1)) * 31 + hashCode(this.objects, this.count);
   }

   public void makeImmutable() {
      if (this.isMutable) {
         this.isMutable = false;
      }
   }

   boolean mergeFieldFrom(int var1, CodedInputStream var2) throws IOException {
      this.checkMutable();
      int var4 = WireFormat.getTagFieldNumber(var1);
      int var3 = WireFormat.getTagWireType(var1);
      if (var3 != 0) {
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 != 3) {
                  if (var3 != 4) {
                     if (var3 == 5) {
                        this.storeField(var1, var2.readFixed32());
                        return true;
                     } else {
                        throw InvalidProtocolBufferException.invalidWireType();
                     }
                  } else {
                     return false;
                  }
               } else {
                  UnknownFieldSetLite var5 = new UnknownFieldSetLite();
                  var5.mergeFrom(var2);
                  var2.checkLastTagWas(WireFormat.makeTag(var4, 4));
                  this.storeField(var1, var5);
                  return true;
               }
            } else {
               this.storeField(var1, var2.readBytes());
               return true;
            }
         } else {
            this.storeField(var1, var2.readFixed64());
            return true;
         }
      } else {
         this.storeField(var1, var2.readInt64());
         return true;
      }
   }

   UnknownFieldSetLite mergeFrom(UnknownFieldSetLite var1) {
      if (var1.equals(getDefaultInstance())) {
         return this;
      } else {
         this.checkMutable();
         int var2 = this.count + var1.count;
         this.ensureCapacity(var2);
         System.arraycopy(var1.tags, 0, this.tags, this.count, var1.count);
         System.arraycopy(var1.objects, 0, this.objects, this.count, var1.count);
         this.count = var2;
         return this;
      }
   }

   UnknownFieldSetLite mergeLengthDelimitedField(int var1, ByteString var2) {
      this.checkMutable();
      if (var1 != 0) {
         this.storeField(WireFormat.makeTag(var1, 2), var2);
         return this;
      } else {
         throw new IllegalArgumentException("Zero is not a valid field number.");
      }
   }

   UnknownFieldSetLite mergeVarintField(int var1, int var2) {
      this.checkMutable();
      if (var1 != 0) {
         this.storeField(WireFormat.makeTag(var1, 0), (long)var2);
         return this;
      } else {
         throw new IllegalArgumentException("Zero is not a valid field number.");
      }
   }

   final void printWithIndent(StringBuilder var1, int var2) {
      for (int var3 = 0; var3 < this.count; var3++) {
         MessageLiteToString.printField(var1, var2, String.valueOf(WireFormat.getTagFieldNumber(this.tags[var3])), this.objects[var3]);
      }
   }

   void storeField(int var1, Object var2) {
      this.checkMutable();
      this.ensureCapacity(this.count + 1);
      int[] var4 = this.tags;
      int var3 = this.count;
      var4[var3] = var1;
      this.objects[var3] = var2;
      this.count = var3 + 1;
   }

   public void writeAsMessageSetTo(CodedOutputStream var1) throws IOException {
      for (int var2 = 0; var2 < this.count; var2++) {
         var1.writeRawMessageSetExtension(WireFormat.getTagFieldNumber(this.tags[var2]), (ByteString)this.objects[var2]);
      }
   }

   void writeAsMessageSetTo(Writer var1) throws IOException {
      if (var1.fieldOrder() == Writer.FieldOrder.DESCENDING) {
         for (int var2 = this.count - 1; var2 >= 0; var2--) {
            var1.writeMessageSetItem(WireFormat.getTagFieldNumber(this.tags[var2]), this.objects[var2]);
         }
      } else {
         for (int var3 = 0; var3 < this.count; var3++) {
            var1.writeMessageSetItem(WireFormat.getTagFieldNumber(this.tags[var3]), this.objects[var3]);
         }
      }
   }

   public void writeTo(CodedOutputStream var1) throws IOException {
      for (int var2 = 0; var2 < this.count; var2++) {
         int var4 = this.tags[var2];
         int var3 = WireFormat.getTagFieldNumber(var4);
         var4 = WireFormat.getTagWireType(var4);
         if (var4 != 0) {
            if (var4 != 1) {
               if (var4 != 2) {
                  if (var4 != 3) {
                     if (var4 != 5) {
                        throw InvalidProtocolBufferException.invalidWireType();
                     }

                     var1.writeFixed32(var3, (Integer)this.objects[var2]);
                  } else {
                     var1.writeTag(var3, 3);
                     ((UnknownFieldSetLite)this.objects[var2]).writeTo(var1);
                     var1.writeTag(var3, 4);
                  }
               } else {
                  var1.writeBytes(var3, (ByteString)this.objects[var2]);
               }
            } else {
               var1.writeFixed64(var3, (Long)this.objects[var2]);
            }
         } else {
            var1.writeUInt64(var3, (Long)this.objects[var2]);
         }
      }
   }

   public void writeTo(Writer var1) throws IOException {
      if (this.count != 0) {
         if (var1.fieldOrder() == Writer.FieldOrder.ASCENDING) {
            for (int var2 = 0; var2 < this.count; var2++) {
               writeField(this.tags[var2], this.objects[var2], var1);
            }
         } else {
            for (int var3 = this.count - 1; var3 >= 0; var3--) {
               writeField(this.tags[var3], this.objects[var3], var1);
            }
         }
      }
   }
}
