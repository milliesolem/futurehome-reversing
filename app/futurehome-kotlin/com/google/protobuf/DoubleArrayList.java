package com.google.protobuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class DoubleArrayList extends AbstractProtobufList<Double> implements Internal.DoubleList, RandomAccess, PrimitiveNonBoxingCollection {
   private static final DoubleArrayList EMPTY_LIST = new DoubleArrayList(new double[0], 0, false);
   private double[] array;
   private int size;

   DoubleArrayList() {
      this(new double[10], 0, true);
   }

   private DoubleArrayList(double[] var1, int var2, boolean var3) {
      super(var3);
      this.array = var1;
      this.size = var2;
   }

   private void addDouble(int var1, double var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var4 = this.size;
         if (var1 <= var4) {
            double[] var6 = this.array;
            if (var4 < var6.length) {
               System.arraycopy(var6, var1, var6, var1 + 1, var4 - var1);
            } else {
               double[] var5 = new double[var4 * 3 / 2 + 1];
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

   public static DoubleArrayList emptyList() {
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

   public void add(int var1, Double var2) {
      this.addDouble(var1, var2);
   }

   public boolean add(Double var1) {
      this.addDouble(var1);
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends Double> var1) {
      this.ensureIsMutable();
      Internal.checkNotNull(var1);
      if (!(var1 instanceof DoubleArrayList)) {
         return super.addAll(var1);
      } else {
         DoubleArrayList var5 = (DoubleArrayList)var1;
         int var3 = var5.size;
         if (var3 == 0) {
            return false;
         } else {
            int var2 = this.size;
            if (Integer.MAX_VALUE - var2 >= var3) {
               var2 += var3;
               double[] var4 = this.array;
               if (var2 > var4.length) {
                  this.array = Arrays.copyOf(var4, var2);
               }

               System.arraycopy(var5.array, 0, this.array, this.size, var5.size);
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
   public void addDouble(double var1) {
      this.ensureIsMutable();
      int var3 = this.size;
      double[] var4 = this.array;
      if (var3 == var4.length) {
         double[] var5 = new double[var3 * 3 / 2 + 1];
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
      } else if (!(var1 instanceof DoubleArrayList)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         if (this.size != var1.size) {
            return false;
         } else {
            double[] var4 = var1.array;

            for (int var2 = 0; var2 < this.size; var2++) {
               if (Double.doubleToLongBits(this.array[var2]) != Double.doubleToLongBits(var4[var2])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Double get(int var1) {
      return this.getDouble(var1);
   }

   @Override
   public double getDouble(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   @Override
   public int hashCode() {
      int var2 = 1;

      for (int var1 = 0; var1 < this.size; var1++) {
         var2 = var2 * 31 + Internal.hashLong(Double.doubleToLongBits(this.array[var1]));
      }

      return var2;
   }

   @Override
   public int indexOf(Object var1) {
      if (!(var1 instanceof Double)) {
         return -1;
      } else {
         double var2 = (Double)var1;
         int var5 = this.size();

         for (int var4 = 0; var4 < var5; var4++) {
            if (this.array[var4] == var2) {
               return var4;
            }
         }

         return -1;
      }
   }

   @Override
   public Internal.DoubleList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new DoubleArrayList(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Double remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      double[] var5 = this.array;
      double var2 = var5[var1];
      int var4 = this.size;
      if (var1 < var4 - 1) {
         System.arraycopy(var5, var1 + 1, var5, var1, var4 - var1 - 1);
      }

      this.size--;
      this.modCount++;
      return var2;
   }

   @Override
   protected void removeRange(int var1, int var2) {
      this.ensureIsMutable();
      if (var2 >= var1) {
         double[] var3 = this.array;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         this.modCount++;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   public Double set(int var1, Double var2) {
      return this.setDouble(var1, var2);
   }

   @Override
   public double setDouble(int var1, double var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      double[] var6 = this.array;
      double var4 = var6[var1];
      var6[var1] = var2;
      return var4;
   }

   @Override
   public int size() {
      return this.size;
   }
}
