package com.google.protobuf;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class FloatArrayList extends AbstractProtobufList<Float> implements Internal.FloatList, RandomAccess, PrimitiveNonBoxingCollection {
   private static final FloatArrayList EMPTY_LIST = new FloatArrayList(new float[0], 0, false);
   private float[] array;
   private int size;

   FloatArrayList() {
      this(new float[10], 0, true);
   }

   private FloatArrayList(float[] var1, int var2, boolean var3) {
      super(var3);
      this.array = var1;
      this.size = var2;
   }

   private void addFloat(int var1, float var2) {
      this.ensureIsMutable();
      if (var1 >= 0) {
         int var3 = this.size;
         if (var1 <= var3) {
            float[] var4 = this.array;
            if (var3 < var4.length) {
               System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            } else {
               float[] var5 = new float[var3 * 3 / 2 + 1];
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

   public static FloatArrayList emptyList() {
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

   public void add(int var1, Float var2) {
      this.addFloat(var1, var2);
   }

   public boolean add(Float var1) {
      this.addFloat(var1);
      return true;
   }

   @Override
   public boolean addAll(Collection<? extends Float> var1) {
      this.ensureIsMutable();
      Internal.checkNotNull(var1);
      if (!(var1 instanceof FloatArrayList)) {
         return super.addAll(var1);
      } else {
         FloatArrayList var5 = (FloatArrayList)var1;
         int var2 = var5.size;
         if (var2 == 0) {
            return false;
         } else {
            int var3 = this.size;
            if (Integer.MAX_VALUE - var3 >= var2) {
               var2 = var3 + var2;
               float[] var4 = this.array;
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
   public void addFloat(float var1) {
      this.ensureIsMutable();
      int var2 = this.size;
      float[] var3 = this.array;
      if (var2 == var3.length) {
         float[] var4 = new float[var2 * 3 / 2 + 1];
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
      } else if (!(var1 instanceof FloatArrayList)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         if (this.size != var1.size) {
            return false;
         } else {
            float[] var4 = var1.array;

            for (int var2 = 0; var2 < this.size; var2++) {
               if (Float.floatToIntBits(this.array[var2]) != Float.floatToIntBits(var4[var2])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public Float get(int var1) {
      return this.getFloat(var1);
   }

   @Override
   public float getFloat(int var1) {
      this.ensureIndexInRange(var1);
      return this.array[var1];
   }

   @Override
   public int hashCode() {
      int var2 = 1;

      for (int var1 = 0; var1 < this.size; var1++) {
         var2 = var2 * 31 + Float.floatToIntBits(this.array[var1]);
      }

      return var2;
   }

   @Override
   public int indexOf(Object var1) {
      if (!(var1 instanceof Float)) {
         return -1;
      } else {
         float var2 = (Float)var1;
         int var4 = this.size();

         for (int var3 = 0; var3 < var4; var3++) {
            if (this.array[var3] == var2) {
               return var3;
            }
         }

         return -1;
      }
   }

   @Override
   public Internal.FloatList mutableCopyWithCapacity(int var1) {
      if (var1 >= this.size) {
         return new FloatArrayList(Arrays.copyOf(this.array, var1), this.size, true);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public Float remove(int var1) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      float[] var4 = this.array;
      float var2 = var4[var1];
      int var3 = this.size;
      if (var1 < var3 - 1) {
         System.arraycopy(var4, var1 + 1, var4, var1, var3 - var1 - 1);
      }

      this.size--;
      this.modCount++;
      return var2;
   }

   @Override
   protected void removeRange(int var1, int var2) {
      this.ensureIsMutable();
      if (var2 >= var1) {
         float[] var3 = this.array;
         System.arraycopy(var3, var2, var3, var1, this.size - var2);
         this.size -= var2 - var1;
         this.modCount++;
      } else {
         throw new IndexOutOfBoundsException("toIndex < fromIndex");
      }
   }

   public Float set(int var1, Float var2) {
      return this.setFloat(var1, var2);
   }

   @Override
   public float setFloat(int var1, float var2) {
      this.ensureIsMutable();
      this.ensureIndexInRange(var1);
      float[] var4 = this.array;
      float var3 = var4[var1];
      var4[var1] = var2;
      return var3;
   }

   @Override
   public int size() {
      return this.size;
   }
}
