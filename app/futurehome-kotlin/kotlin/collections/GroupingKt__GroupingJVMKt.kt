package kotlin.collections

import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.Ref
import kotlin.jvm.internal.TypeIntrinsics

internal class GroupingKt__GroupingJVMKt {
   open fun GroupingKt__GroupingJVMKt() {
   }

   @JvmStatic
   public fun <T, K> Grouping<T, K>.eachCount(): Map<K, Int> {
      val var3: java.util.Map = new LinkedHashMap();
      val var5: java.util.Iterator = var0.sourceIterator();

      while (var5.hasNext()) {
         val var4: Any = var0.keyOf(var5.next());
         var var2: Any = var3.get(var4);
         val var1: Boolean;
         if (var2 == null && !var3.containsKey(var4)) {
            var1 = true;
         } else {
            var1 = false;
         }

         if (var1) {
            var2 = new Ref.IntRef();
         }

         val var7: Ref.IntRef = var2 as Ref.IntRef;
         (var2 as Ref.IntRef).element++;
         var3.put(var4, var7);
      }

      for (Entry var6 : var3.entrySet()) {
         TypeIntrinsics.asMutableMapEntry(var6).setValue((var6.getValue() as Ref.IntRef).element);
      }

      return TypeIntrinsics.asMutableMap(var3);
   }

   @JvmStatic
   internal inline fun <K, V, R> MutableMap<K, V>.mapValuesInPlace(f: (kotlin.collections.Map.Entry<K, V>) -> R): MutableMap<K, R> {
      for (Entry var3 : var0.entrySet()) {
         TypeIntrinsics.asMutableMapEntry(var3).setValue(var1.invoke(var3));
      }

      return TypeIntrinsics.asMutableMap(var0);
   }
}
