package com.google.protobuf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class MapFieldLite<K, V> extends LinkedHashMap<K, V> {
   private static final MapFieldLite<?, ?> EMPTY_MAP_FIELD;
   private boolean isMutable = true;

   static {
      MapFieldLite var0 = new MapFieldLite();
      EMPTY_MAP_FIELD = var0;
      var0.makeImmutable();
   }

   private MapFieldLite() {
   }

   private MapFieldLite(Map<K, V> var1) {
      super(var1);
   }

   static <K, V> int calculateHashCodeForMap(Map<K, V> var0) {
      Iterator var4 = var0.entrySet().iterator();
      int var1 = 0;

      while (var4.hasNext()) {
         Entry var3 = (Entry)var4.next();
         int var2 = calculateHashCodeForObject(var3.getKey());
         var1 += calculateHashCodeForObject(var3.getValue()) ^ var2;
      }

      return var1;
   }

   private static int calculateHashCodeForObject(Object var0) {
      if (var0 instanceof byte[]) {
         return Internal.hashCode((byte[])var0);
      } else if (!(var0 instanceof Internal.EnumLite)) {
         return var0.hashCode();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private static void checkForNullKeysAndValues(Map<?, ?> var0) {
      for (Object var1 : var0.keySet()) {
         Internal.checkNotNull(var1);
         Internal.checkNotNull(var0.get(var1));
      }
   }

   private static Object copy(Object var0) {
      Object var1 = var0;
      if (var0 instanceof byte[]) {
         var0 = var0;
         var1 = Arrays.copyOf(var0, var0.length);
      }

      return var1;
   }

   static <K, V> Map<K, V> copy(Map<K, V> var0) {
      LinkedHashMap var1 = new LinkedHashMap(var0.size() * 4 / 3 + 1);

      for (Entry var3 : var0.entrySet()) {
         var1.put(var3.getKey(), copy(var3.getValue()));
      }

      return var1;
   }

   public static <K, V> MapFieldLite<K, V> emptyMapField() {
      return (MapFieldLite<K, V>)EMPTY_MAP_FIELD;
   }

   private void ensureMutable() {
      if (!this.isMutable()) {
         throw new UnsupportedOperationException();
      }
   }

   private static boolean equals(Object var0, Object var1) {
      return var0 instanceof byte[] && var1 instanceof byte[] ? Arrays.equals((byte[])var0, (byte[])var1) : var0.equals(var1);
   }

   static <K, V> boolean equals(Map<K, V> var0, Map<K, V> var1) {
      if (var0 == var1) {
         return true;
      } else if (var0.size() != var1.size()) {
         return false;
      } else {
         for (Entry var3 : var0.entrySet()) {
            if (!var1.containsKey(var3.getKey())) {
               return false;
            }

            if (!equals(var3.getValue(), var1.get(var3.getKey()))) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   public void clear() {
      this.ensureMutable();
      super.clear();
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      Set var1;
      if (this.isEmpty()) {
         var1 = Collections.emptySet();
      } else {
         var1 = super.entrySet();
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Map && equals(this, (Map<K, V>)var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public int hashCode() {
      return calculateHashCodeForMap(this);
   }

   public boolean isMutable() {
      return this.isMutable;
   }

   public void makeImmutable() {
      this.isMutable = false;
   }

   public void mergeFrom(MapFieldLite<K, V> var1) {
      this.ensureMutable();
      if (!var1.isEmpty()) {
         this.putAll(var1);
      }
   }

   public MapFieldLite<K, V> mutableCopy() {
      MapFieldLite var1;
      if (this.isEmpty()) {
         var1 = new MapFieldLite();
      } else {
         var1 = new MapFieldLite<>(this);
      }

      return var1;
   }

   @Override
   public V put(K var1, V var2) {
      this.ensureMutable();
      Internal.checkNotNull(var1);
      Internal.checkNotNull(var2);
      return super.put((K)var1, (V)var2);
   }

   public V put(Entry<K, V> var1) {
      return this.put((K)var1.getKey(), (V)var1.getValue());
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> var1) {
      this.ensureMutable();
      checkForNullKeysAndValues(var1);
      super.putAll(var1);
   }

   @Override
   public V remove(Object var1) {
      this.ensureMutable();
      return super.remove(var1);
   }
}
