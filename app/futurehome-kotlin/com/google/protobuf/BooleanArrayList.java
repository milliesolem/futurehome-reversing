package com.google.protobuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class BooleanArrayList extends AbstractProtobufList<Boolean> implements Internal.BooleanList, RandomAccess, PrimitiveNonBoxingCollection {
   private static final BooleanArrayList EMPTY_LIST = new BooleanArrayList(new boolean[0], 0, false);
   private boolean[] array;
   private int size;

   BooleanArrayList() {
      this(new boolean[10], 0, true);
   }

   private BooleanArrayList(boolean[] var1, int var2, boolean var3) {
      super(var3);
      this.array = var1;
      this.size = var2;
   }

   private void addBoolean(int var1, boolean var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            boolean[] var4 = this.array;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               boolean[] var5 = new boolean[var3 * 3 / 2 + 1];
               System.arraycopy(var4, 0, var5, 0, var1);
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

   public static BooleanArrayList emptyList() {
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

   public void add(int var1, Boolean var2) {
      this.addBoolean(var1, var2);
   }

   public boolean add(Boolean var1) {
      this.addBoolean(var1);
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends Boolean> var1) {
      this.ensureIsMutable();
      Internal.checkNotNull(var1);
      if (!(var1 instanceof BooleanArrayList)) {
         return super.addAll(var1);
      } else {
         BooleanArrayList var4 = (BooleanArrayList)var1;
         int var3 = var4.size;
         if (var3 == 0) {
            return false;
         } else {
            int var2 = this.size;
            if (Integer.MAX_VALUE - var2 >= var3) {
               var2 += var3;
               boolean[] var5 = this.array;
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
   public void addBoolean(boolean var1) {
      this.ensureIsMutable();
      int var2 = this.size;
      boolean[] var4 = this.array;
      if (var2 == var4.length) {
         boolean[] var3 = new boolean[var2 * 3 / 2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         this.array = var3;
      }

      boolean[] var6 = this.array;
      var2 = this.size++;
      var6[var2] = var1;
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
      } else if (!(var1 instanceof BooleanArrayList)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         if (this.size != var1.size) {
            return false;
         } else {
            boolean[] var4 = var1.array;

            for (int var2 = 0; var2 < this.size; var2++) {
               if (this.array[var2] != var4[var2]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Boolean get(int var1) {
      return this.getBoolean(var1);
   }

   @Override
   public boolean getBoolean(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   @Override
   public int hashCode() {
      int var2 = 1;

      for (int var1 = 0; var1 < this.size; var1++) {
         var2 = var2 * 31 + Internal.hashBoolean(this.array[var1]);
      }

      return var2;
   }

   @Override
   public int indexOf(Object var1) {
      if (!(var1 instanceof Boolean)) {
         return -1;
      } else {
         boolean var4 = (Boolean)var1;
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
   public Internal.BooleanList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new BooleanArrayList(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Boolean remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      boolean[] var4 = this.array;
      boolean var3 = var4[var1];
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
         boolean[] var3 = this.array;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         this.modCount++;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   public Boolean set(int var1, Boolean var2) {
      return this.setBoolean(var1, var2);
   }

   @Override
   public boolean setBoolean(int var1, boolean var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      boolean[] var4 = this.array;
      boolean var3 = var4[var1];
      var4[var1] = var2;
      return var3;
   }

   @Override
   public int size() {
      return this.size;
   }
}
