package io.sentry.util;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public final class Random implements Serializable {
   static final String BadBound = "bound must be positive";
   private static final double DOUBLE_UNIT = 1.110223E-16F;
   private static final long addend = 11L;
   private static final long mask = 281474976710655L;
   private static final long multiplier = 25214903917L;
   private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
   private static final long serialVersionUID = 3905348978240129619L;
   private final AtomicLong seed;

   public Random() {
      this(seedUniquifier() ^ System.nanoTime());
   }

   public Random(long var1) {
      if (this.getClass() == Random.class) {
         this.seed = new AtomicLong(initialScramble(var1));
      } else {
         this.seed = new AtomicLong();
         this.setSeed(var1);
      }
   }

   private static long initialScramble(long var0) {
      return (var0 ^ 25214903917L) & 281474976710655L;
   }

   private int next(int var1) {
      AtomicLong var6 = this.seed;

      long var2;
      long var4;
      do {
         var2 = var6.get();
         var4 = 25214903917L * var2 + 11L & 281474976710655L;
      } while (!var6.compareAndSet(var2, var4));

      return (int)(var4 >>> 48 - var1);
   }

   private static long seedUniquifier() {
      long var0;
      long var2;
      AtomicLong var4;
      do {
         var4 = seedUniquifier;
         var0 = var4.get();
         var2 = 1181783497276652981L * var0;
      } while (!var4.compareAndSet(var0, var2));

      return var2;
   }

   final double internalNextDouble(double var1, double var3) {
      double var7 = this.nextDouble();
      double var5 = var7;
      if (var1 < var3) {
         var1 = var7 * (var3 - var1) + var1;
         var5 = var1;
         if (var1 >= var3) {
            var5 = Double.longBitsToDouble(Double.doubleToLongBits(var3) - 1L);
         }
      }

      return var5;
   }

   final int internalNextInt(int var1, int var2) {
      if (var1 >= var2) {
         return this.nextInt();
      } else {
         int var3 = var2 - var1;
         if (var3 > 0) {
            return this.nextInt(var3) + var1;
         } else {
            do {
               var3 = this.nextInt();
            } while (var3 < var1 || var3 >= var2);

            return var3;
         }
      }
   }

   final long internalNextLong(long var1, long var3) {
      long var9 = this.nextLong();
      long var5 = var9;
      if (var1 < var3) {
         long var11 = var3 - var1;
         var5 = var11 - 1L;
         if ((var11 & var5) == 0L) {
            var5 = (var9 & var5) + var1;
         } else {
            long var7 = var9;
            if (var11 > 0L) {
               while (true) {
                  var7 = var9 >>> 1;
                  var3 = var7 % var11;
                  if (var7 + var5 - var3 >= 0L) {
                     var5 = var3 + var1;
                     break;
                  }

                  var9 = this.nextLong();
               }
            } else {
               while (true) {
                  if (var7 >= var1) {
                     var5 = var7;
                     if (var7 < var3) {
                        break;
                     }
                  }

                  var7 = this.nextLong();
               }
            }
         }
      }

      return var5;
   }

   public boolean nextBoolean() {
      boolean var1 = true;
      if (this.next(1) == 0) {
         var1 = false;
      }

      return var1;
   }

   public void nextBytes(byte[] var1) {
      int var6 = var1.length;
      int var3 = 0;

      while (var3 < var6) {
         int var5 = this.nextInt();
         int var2 = Math.min(var6 - var3, 4);
         int var4 = var3;

         while (true) {
            var3 = var4;
            if (var2 <= 0) {
               break;
            }

            var1[var4] = (byte)var5;
            var5 >>= 8;
            var4++;
            var2--;
         }
      }
   }

   public double nextDouble() {
      return (((long)this.next(26) << 27) + this.next(27)) * 1.110223E-16F;
   }

   public float nextFloat() {
      return this.next(24) / 1.6777216E7F;
   }

   public int nextInt() {
      return this.next(32);
   }

   public int nextInt(int var1) {
      if (var1 <= 0) {
         throw new IllegalArgumentException("bound must be positive");
      } else {
         int var3 = this.next(31);
         int var4 = var1 - 1;
         int var2 = var3;
         if ((var1 & var4) == 0) {
            var1 = (int)((long)var1 * var3 >> 31);
         } else {
            while (true) {
               var3 = var2 % var1;
               if (var2 - var3 + var4 >= 0) {
                  var1 = var3;
                  break;
               }

               var2 = this.next(31);
            }
         }

         return var1;
      }
   }

   public long nextLong() {
      return ((long)this.next(32) << 32) + this.next(32);
   }

   public void setSeed(long param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/util/Random.seed Ljava/util/concurrent/atomic/AtomicLong;
      // 06: lload 1
      // 07: invokestatic io/sentry/util/Random.initialScramble (J)J
      // 0a: invokevirtual java/util/concurrent/atomic/AtomicLong.set (J)V
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: return
      // 10: astore 3
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 3
      // 14: athrow
   }
}
