package com.google.protobuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class IntArrayList extends AbstractProtobufList<Integer> implements Internal.IntList, RandomAccess, PrimitiveNonBoxingCollection {
   private static final IntArrayList EMPTY_LIST = new IntArrayList(new int[0], 0, false);
   private int[] array;
   private int size;

   IntArrayList() {
      this(new int[10], 0, true);
   }

   private IntArrayList(int[] var1, int var2, boolean var3) {
      super(var3);
      this.array = var1;
      this.size = var2;
   }

   private void addInt(int var1, int var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            int[] var5 = this.array;
            if (var3 < var5.length) {
               System.arraycopy(var5, var1, var5, var1 + 1, var3 - var1);
            } else {
               int[] var4 = new int[var3 * 3 / 2 + 1];
               System.arraycopy(var5, 0, var4, 0, var1);
               System.arraycopy(this.array, var1, var4, var1 + 1, this.size - var1);
               this.array = var4;
            }

            this.array[var1] = var2;
            this.size++;
            this.modCount++;
            return;
         }
      }

      throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(var1));
   }

   public static IntArrayList emptyList() {
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

   public void add(int var1, Integer var2) {
      this.addInt(var1, var2);
   }

   public boolean add(Integer var1) {
      this.addInt(var1);
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends Integer> var1) {
      this.ensureIsMutable();
      Internal.checkNotNull(var1);
      if (!(var1 instanceof IntArrayList)) {
         return super.addAll(var1);
      } else {
         IntArrayList var4 = (IntArrayList)var1;
         int var2 = var4.size;
         if (var2 == 0) {
            return false;
         } else {
            int var3 = this.size;
            if (Integer.MAX_VALUE - var3 >= var2) {
               var2 = var3 + var2;
               int[] var5 = this.array;
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
   public void addInt(int var1) {
      this.ensureIsMutable();
      int var2 = this.size;
      int[] var3 = this.array;
      if (var2 == var3.length) {
         int[] var4 = new int[var2 * 3 / 2 + 1];
         System.arraycopy(var3, 0, var4, 0, var2);
         this.array = var4;
      }

      var3 = this.array;
      var2 = this.size++;
      var3[var2] = var1;
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
      } else if (!(var1 instanceof IntArrayList)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         if (this.size != var1.size) {
            return false;
         } else {
            int[] var4 = var1.array;

            for (int var2 = 0; var2 < this.size; var2++) {
               if (this.array[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Integer get(int var1) {
      return this.getInt(var1);
   }

   @Override
   public int getInt(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   @Override
   public int hashCode() {
      int var2 = 1;

      for (int var1 = 0; var1 < this.size; var1++) {
         var2 = var2 * 31 + this.array[var1];
      }

      return var2;
   }

   @Override
   public int indexOf(Object var1) {
      if (!(var1 instanceof Integer)) {
         return -1;
      } else {
         int var3 = (Integer)var1;
         int var4 = this.size();

         for (int var2 = 0; var2 < var4; var2++) {
            if (this.array[var2] == var3) {
               return var2;
            }
         }

         return -1;
      }
   }

   @Override
   public Internal.IntList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new IntArrayList(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Integer remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      int[] var4 = this.array;
      int var3 = var4[var1];
      int var2 = this.size;
      if (var1 < var2 - 1) {
         System.arraycopy(var4, var1 + 1, var4, var1, var2 - var1 - 1);
      }

      this.size--;
      this.modCount++;
      return var3;
   }

   @Override
   protected void removeRange(int var1, int var2) {
      this.ensureIsMutable();
      if (var2 >= var1) {
         int[] var3 = this.array;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         this.modCount++;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   public Integer set(int var1, Integer var2) {
      return this.setInt(var1, var2);
   }

   @Override
   public int setInt(int var1, int var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      int[] var4 = this.array;
      int var3 = var4[var1];
      var4[var1] = var2;
      return var3;
   }

   @Override
   public int size() {
      return this.size;
   }
}
