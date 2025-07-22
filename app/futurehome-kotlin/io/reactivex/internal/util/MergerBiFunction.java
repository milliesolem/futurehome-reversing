package io.reactivex.internal.util;

import io.reactivex.functions.BiFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class MergerBiFunction<T> implements BiFunction<List<T>, List<T>, List<T>> {
   final Comparator<? super T> comparator;

   public MergerBiFunction(Comparator<? super T> var1) {
      this.comparator = var1;
   }

   // $VF: Irreducible bytecode was duplicated to produce valid code
   public List<T> apply(List<T> var1, List<T> var2) throws Exception {
      int var3 = var1.size() + var2.size();
      if (var3 == 0) {
         return new ArrayList<>();
      } else {
         ArrayList var5 = new ArrayList(var3);
         Iterator var6 = var1.iterator();
         Iterator var7 = var2.iterator();
         Object var8;
         if (var6.hasNext()) {
            var8 = var6.next();
         } else {
            var8 = null;
         }

         Object var9;
         if (var7.hasNext()) {
            var9 = var7.next();
         } else {
            var9 = null;
            var8 = var8;
         }

         while (var8 != null && var9 != null) {
            if (this.comparator.compare((T)var8, (T)var9) < 0) {
               var5.add(var8);
               if (var6.hasNext()) {
                  var8 = var6.next();
               } else {
                  var8 = null;
               }
            } else {
               var5.add(var9);
               if (var7.hasNext()) {
                  var9 = var7.next();
               } else {
                  var9 = null;
                  var8 = var8;
               }
            }
         }

         if (var8 != null) {
            var5.add(var8);

            while (var6.hasNext()) {
               var5.add(var6.next());
            }
         } else {
            var5.add(var9);

            while (var7.hasNext()) {
               var5.add(var7.next());
            }
         }

         return var5;
      }
   }
}
