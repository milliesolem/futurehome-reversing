package io.sentry.android.core.internal.threaddump;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class Lines {
   private final ArrayList<? extends Line> mList;
   private final int mMax;
   private final int mMin;
   public int pos;

   public Lines(ArrayList<? extends Line> var1) {
      this.mList = var1;
      this.mMin = 0;
      this.mMax = var1.size();
   }

   public static Lines readLines(BufferedReader var0) throws IOException {
      ArrayList var2 = new ArrayList();
      int var1 = 0;

      while (true) {
         String var3 = var0.readLine();
         if (var3 == null) {
            return new Lines(var2);
         }

         var2.add(new Line(++var1, var3));
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Lines readLines(File var0) throws IOException {
      BufferedReader var1 = new BufferedReader(new FileReader(var0));

      try {
         var8 = readLines(var1);
      } catch (Throwable var7) {
         try {
            var1.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
            throw var7;
         }

         throw var7;
      }

      var1.close();
      return var8;
   }

   public boolean hasNext() {
      boolean var1;
      if (this.pos < this.mMax) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Line next() {
      int var1 = this.pos;
      if (var1 >= this.mMin && var1 < this.mMax) {
         ArrayList var2 = this.mList;
         this.pos = var1 + 1;
         return (Line)var2.get(var1);
      } else {
         return null;
      }
   }

   public void rewind() {
      this.pos--;
   }
}
