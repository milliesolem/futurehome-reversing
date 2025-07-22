package com.google.protobuf;

import java.util.Arrays;
import java.util.RandomAccess;

final class ProtobufArrayList<E> extends AbstractProtobufList<E> implements RandomAccess {
   private static final ProtobufArrayList<Object> EMPTY_LIST = new ProtobufArrayList<>(new Object[0], 0, false);
   private E[] array;
   private int size;

   ProtobufArrayList() {
      this((E[])(new Object[10]), 0, true);
   }

   private ProtobufArrayList(E[] var1, int var2, boolean var3) {
      super(var3);
      this.array = (E[])var1;
      this.size = var2;
   }

   private static <E> E[] createArray(int var0) {
      return (E[])(new Object[var0]);
   }

   public static <E> ProtobufArrayList<E> emptyList() {
      return (ProtobufArrayList<E>)EMPTY_LIST;
   }

   private void ensureIndexInRange(int var1) {
      if (var1 < 0 || var1 >= this.size) {
         throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(var1));
      }
   }

   private String makeOutOfBoundsExceptionMessage(int var1) {
      StringBuilder var2 = new StringBuilder("Index:");
      var2.append(var1);
      var2.append(", Size:");
      var2.append(this.size);
      return var2.toString();
   }

   @Override
   public void add(int var1, E var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            Object[] var4 = this.array;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               var4 = createArray(var3 * 3 / 2 + 1);
               System.arraycopy(this.array, 0, var4, 0, var1);
               System.arraycopy(this.array, var1, var4, var1 + 1, this.size - var1);
               this.array = (E[])var4;
            }

            this.array[var1] = (E)var2;
            this.size++;
            this.modCount++;
            return;
         }
      }

      throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(var1));
   }

   @Override
   public boolean add(E var1) {
      this.ensureIsMutable();
      int var2 = this.size;
      Object[] var3 = this.array;
      if (var2 == var3.length) {
         this.array = (E[])Arrays.copyOf(var3, var2 * 3 / 2 + 1);
      }

      var3 = this.array;
      var2 = this.size++;
      var3[var2] = var1;
      this.modCount++;
      return true;
   }

   @Override
   public E get(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   public ProtobufArrayList<E> mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new ProtobufArrayList<>(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public E remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      Object[] var3 = this.array;
      Object var4 = var3[var1];
      int var2 = this.size;
      if (var1 < var2 - 1) {
         System.arraycopy(var3, var1 + 1, var3, var1, var2 - var1 - 1);
      }

      this.size--;
      this.modCount++;
      return (E)var4;
   }

   @Override
   public E set(int var1, E var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      Object[] var3 = this.array;
      Object var4 = var3[var1];
      var3[var1] = var2;
      this.modCount++;
      return (E)var4;
   }

   @Override
   public int size() {
      return this.size;
   }
}
