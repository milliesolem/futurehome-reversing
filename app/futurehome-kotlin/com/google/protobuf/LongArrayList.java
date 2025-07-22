package com.google.protobuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class LongArrayList extends AbstractProtobufList<Long> implements Internal.LongList, RandomAccess, PrimitiveNonBoxingCollection {
   private static final LongArrayList EMPTY_LIST = new LongArrayList(new long[0], 0, false);
   private long[] array;
   private int size;

   LongArrayList() {
      this(new long[10], 0, true);
   }

   private LongArrayList(long[] var1, int var2, boolean var3) {
      super(var3);
      this.array = var1;
      this.size = var2;
   }

   private void addLong(int var1, long var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var4 = this.size;
         if (var1 <= var4) {
            long[] var6 = this.array;
            if (var4 < var6.length) {
               System.arraycopy(var6, var1, var6, var1 + 1, var4 - var1);
            } else {
               long[] var5 = new long[var4 * 3 / 2 + 1];
               System.arraycopy(var6, 0, var5, 0, var1);
               System.arraycopy(this.array, var1, var5, var1 + 1, this.size - var1);
               this.array = var5;
            }

            this.array[var1] = var2;
            this.size++;
            this.modCount++;
            return;
         }
      }

      throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(var1));
   }

   public static LongArrayList emptyList() {
      return EMPTY_LIST;
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

   public void add(int var1, Long var2) {
      this.addLong(var1, var2);
   }

   public boolean add(Long var1) {
      this.addLong(var1);
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends Long> var1) {
      this.ensureIsMutable();
      Internal.checkNotNull(var1);
      if (!(var1 instanceof LongArrayList)) {
         return super.addAll(var1);
      } else {
         LongArrayList var4 = (LongArrayList)var1;
         int var2 = var4.size;
         if (var2 == 0) {
            return false;
         } else {
            int var3 = this.size;
            if (Integer.MAX_VALUE - var3 >= var2) {
               var2 = var3 + var2;
               long[] var5 = this.array;
               if (var2 > var5.length) {
                  this.array = Arrays.copyOf(var5, var2);
               }

               System.arraycopy(var4.array, 0, this.array, this.size, var4.size);
               this.size = var2;
               this.modCount++;
               return true;
            } else {
               throw new OutOfMemoryError();
            }
         }
      }
   }

   @Override
   public void addLong(long var1) {
      this.ensureIsMutable();
      int var3 = this.size;
      long[] var4 = this.array;
      if (var3 == var4.length) {
         long[] var5 = new long[var3 * 3 / 2 + 1];
         System.arraycopy(var4, 0, var5, 0, var3);
         this.array = var5;
      }

      var4 = this.array;
      var3 = this.size++;
      var4[var3] = var1;
   }

   @Override
   public boolean contains(Object var1) {
      boolean var2;
      if (this.indexOf(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof LongArrayList)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         if (this.size != var1.size) {
            return false;
         } else {
            long[] var4 = var1.array;

            for (int var2 = 0; var2 < this.size; var2++) {
               if (this.array[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Long get(int var1) {
      return this.getLong(var1);
   }

   @Override
   public long getLong(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   @Override
   public int hashCode() {
      int var2 = 1;

      for (int var1 = 0; var1 < this.size; var1++) {
         var2 = var2 * 31 + Internal.hashLong(this.array[var1]);
      }

      return var2;
   }

   @Override
   public int indexOf(Object var1) {
      if (!(var1 instanceof Long)) {
         return -1;
      } else {
         long var4 = (Long)var1;
         int var3 = this.size();

         for (int var2 = 0; var2 < var3; var2++) {
            if (this.array[var2] == var4) {
               return var2;
            }
         }

         return -1;
      }
   }

   @Override
   public Internal.LongList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new LongArrayList(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Long remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      long[] var5 = this.array;
      long var3 = var5[var1];
      int var2 = this.size;
      if (var1 < var2 - 1) {
         System.arraycopy(var5, var1 + 1, var5, var1, var2 - var1 - 1);
      }

      this.size--;
      this.modCount++;
      return var3;
   }

   @Override
   protected void removeRange(int var1, int var2) {
      this.ensureIsMutable();
      if (var2 >= var1) {
         long[] var3 = this.array;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         this.modCount++;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   public Long set(int var1, Long var2) {
      return this.setLong(var1, var2);
   }

   @Override
   public long setLong(int var1, long var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      long[] var6 = this.array;
      long var4 = var6[var1];
      var6[var1] = var2;
      return var4;
   }

   @Override
   public int size() {
      return this.size;
   }
}
