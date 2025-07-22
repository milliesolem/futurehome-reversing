package io.reactivex.internal.util;

public final class OpenHashSet<T> {
   private static final int INT_PHI = -1640531527;
   T[] keys;
   final float loadFactor;
   int mask;
   int maxSize;
   int size;

   public OpenHashSet() {
      this(16, 0.75F);
   }

   public OpenHashSet(int var1) {
      this(var1, 0.75F);
   }

   public OpenHashSet(int var1, float var2) {
      this.loadFactor = var2;
      var1 = Pow2.roundToPowerOfTwo(var1);
      this.mask = var1 - 1;
      this.maxSize = (int)(var2 * var1);
      this.keys = (T[])(new Object[var1]);
   }

   static int mix(int var0) {
      var0 *= -1640531527;
      return var0 ^ var0 >>> 16;
   }

   public boolean add(T var1) {
      Object[] var5 = this.keys;
      int var4 = this.mask;
      int var3 = mix(var1.hashCode()) & var4;
      Object var6 = var5[var3];
      int var2 = var3;
      if (var6 != null) {
         if (var6.equals(var1)) {
            return false;
         }

         while (true) {
            var2 = var3 + 1 & var4;
            var6 = var5[var2];
            if (var6 == null) {
               break;
            }

            var3 = var2;
            if (var6.equals(var1)) {
               return false;
            }
         }
      }

      var5[var2] = var1;
      var2 = this.size + 1;
      this.size = var2;
      if (var2 >= this.maxSize) {
         this.rehash();
      }

      return true;
   }

   public Object[] keys() {
      return this.keys;
   }

   void rehash() {
      Object[] var8 = this.keys;
      int var1 = var8.length;
      int var5 = var1 << 1;
      int var6 = var5 - 1;
      Object[] var9 = new Object[var5];

      for (int var2 = this.size; var2 != 0; var2--) {
         Object var7;
         do {
            var7 = var8[--var1];
         } while (var7 == null);

         int var4 = mix(var7.hashCode()) & var6;
         int var3 = var4;
         if (var9[var4] != null) {
            do {
               var3 = var4 + 1 & var6;
               var4 = var3;
            } while (var9[var3] != null);
         }

         var9[var3] = var8[var1];
      }

      this.mask = var6;
      this.maxSize = (int)(var5 * this.loadFactor);
      this.keys = (T[])var9;
   }

   public boolean remove(T var1) {
      Object[] var5 = this.keys;
      int var4 = this.mask;
      int var3 = mix(var1.hashCode()) & var4;
      Object var6 = var5[var3];
      if (var6 == null) {
         return false;
      } else {
         int var2 = var3;
         if (var6.equals(var1)) {
            return this.removeEntry(var3, (T[])var5, var4);
         } else {
            do {
               var3 = var2 + 1 & var4;
               var6 = var5[var3];
               if (var6 == null) {
                  return false;
               }

               var2 = var3;
            } while (!var6.equals(var1));

            return this.removeEntry(var3, (T[])var5, var4);
         }
      }
   }

   boolean removeEntry(int var1, T[] var2, int var3) {
      this.size--;

      while (true) {
         int var7 = var1 + 1;

         while (true) {
            var7 &= var3;
            Object var6 = var2[var7];
            if (var6 == null) {
               var2[var1] = null;
               return true;
            }

            int var5 = mix(var6.hashCode()) & var3;
            if (var1 <= var7 ? var1 >= var5 || var5 > var7 : var1 >= var5 && var5 > var7) {
               var2[var1] = var6;
               var1 = var7;
               break;
            }

            var7++;
         }
      }
   }

   public int size() {
      return this.size;
   }
}
