package io.sentry.util;

import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class CollectionUtils {
   private CollectionUtils() {
   }

   public static <T> boolean contains(T[] var0, T var1) {
      int var3 = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.equals(var0[var2])) {
            return true;
         }
      }

      return false;
   }

   public static <T> List<T> filterListEntries(List<T> var0, CollectionUtils.Predicate<T> var1) {
      ArrayList var2 = new ArrayList(var0.size());

      for (Object var3 : var0) {
         if (var1.test(var3)) {
            var2.add(var3);
         }
      }

      return var2;
   }

   public static <K, V> Map<K, V> filterMapEntries(Map<K, V> var0, CollectionUtils.Predicate<Entry<K, V>> var1) {
      HashMap var2 = new HashMap();

      for (Entry var3 : var0.entrySet()) {
         if (var1.test(var3)) {
            var2.put(var3.getKey(), var3.getValue());
         }
      }

      return var2;
   }

   public static <T, R> List<R> map(List<T> var0, CollectionUtils.Mapper<T, R> var1) {
      ArrayList var2 = new ArrayList(var0.size());
      Iterator var3 = var0.iterator();

      while (var3.hasNext()) {
         var2.add(var1.map(var3.next()));
      }

      return var2;
   }

   public static <T> List<T> newArrayList(List<T> var0) {
      return var0 != null ? new ArrayList<>(var0) : null;
   }

   public static <K, V> Map<K, V> newConcurrentHashMap(Map<K, V> var0) {
      if (var0 != null) {
         ConcurrentHashMap var1 = new ConcurrentHashMap();

         for (Entry var3 : var0.entrySet()) {
            if (var3.getKey() != null && var3.getValue() != null) {
               var1.put(var3.getKey(), var3.getValue());
            }
         }

         return var1;
      } else {
         return null;
      }
   }

   public static <K, V> Map<K, V> newHashMap(Map<K, V> var0) {
      return var0 != null ? new HashMap<>(var0) : null;
   }

   public static int size(Iterable<?> var0) {
      if (var0 instanceof Collection) {
         return ((Collection)var0).size();
      } else {
         Iterator var2 = var0.iterator();

         int var1;
         for (var1 = 0; var2.hasNext(); var1++) {
            var2.next();
         }

         return var1;
      }
   }

   public interface Mapper<T, R> {
      R map(T var1);
   }

   public interface Predicate<T> {
      boolean test(T var1);
   }
}
