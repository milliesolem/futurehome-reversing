package com.google.protobuf;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class SmallSortedMap<K extends Comparable<K>, V> extends AbstractMap<K, V> {
   private List<SmallSortedMap<K, V>.Entry> entryList;
   private boolean isImmutable;
   private volatile SmallSortedMap<K, V>.DescendingEntrySet lazyDescendingEntrySet;
   private volatile SmallSortedMap<K, V>.EntrySet lazyEntrySet;
   private final int maxArraySize;
   private Map<K, V> overflowEntries;
   private Map<K, V> overflowEntriesDescending;

   private SmallSortedMap(int var1) {
      this.maxArraySize = var1;
      this.entryList = Collections.emptyList();
      this.overflowEntries = Collections.emptyMap();
      this.overflowEntriesDescending = Collections.emptyMap();
   }

   private int binarySearchInArray(K var1) {
      int var3 = this.entryList.size();
      int var2 = var3 - 1;
      if (var2 >= 0) {
         int var4 = var1.compareTo(this.entryList.get(var2).getKey());
         if (var4 > 0) {
            var2 = var3 + 1;
            return -var2;
         }

         if (var4 == 0) {
            return var2;
         }
      }

      var3 = 0;

      while (var3 <= var2) {
         int var5 = (var3 + var2) / 2;
         int var8 = var1.compareTo(this.entryList.get(var5).getKey());
         if (var8 < 0) {
            var2 = var5 - 1;
         } else {
            if (var8 <= 0) {
               return var5;
            }

            var3 = var5 + 1;
         }
      }

      var2 = var3 + 1;
      return -var2;
   }

   private void checkMutable() {
      if (this.isImmutable) {
         throw new UnsupportedOperationException();
      }
   }

   private void ensureEntryArrayMutable() {
      this.checkMutable();
      if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
         this.entryList = new ArrayList<>(this.maxArraySize);
      }
   }

   private SortedMap<K, V> getOverflowEntriesMutable() {
      this.checkMutable();
      if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
         TreeMap var2 = new TreeMap();
         this.overflowEntries = var2;
         TreeMap var1 = var2;
         this.overflowEntriesDescending = var2.descendingMap();
      }

      return (SortedMap<K, V>)this.overflowEntries;
   }

   static <FieldDescriptorType extends FieldSet.FieldDescriptorLite<FieldDescriptorType>> SmallSortedMap<FieldDescriptorType, Object> newFieldMap(int var0) {
      return new SmallSortedMap<FieldDescriptorType, Object>(var0) {
         @Override
         public void makeImmutable() {
            if (!this.isImmutable()) {
               for (int var1 = 0; var1 < this.getNumArrayEntries(); var1++) {
                  java.util.Map.Entry var2 = this.getArrayEntryAt(var1);
                  if (((FieldSet.FieldDescriptorLite)var2.getKey()).isRepeated()) {
                     var2.setValue(Collections.unmodifiableList((List)var2.getValue()));
                  }
               }

               for (java.util.Map.Entry var4 : this.getOverflowEntries()) {
                  if (((FieldSet.FieldDescriptorLite)var4.getKey()).isRepeated()) {
                     var4.setValue(Collections.unmodifiableList((List)var4.getValue()));
                  }
               }
            }

            super.makeImmutable();
         }
      };
   }

   static <K extends Comparable<K>, V> SmallSortedMap<K, V> newInstanceForTest(int var0) {
      return new SmallSortedMap<>(var0);
   }

   private V removeArrayEntryAt(int var1) {
      this.checkMutable();
      Object var2 = this.entryList.remove(var1).getValue();
      if (!this.overflowEntries.isEmpty()) {
         Iterator var3 = this.getOverflowEntriesMutable().entrySet().iterator();
         this.entryList.add(new SmallSortedMap.Entry(this, (java.util.Map.Entry)var3.next()));
         var3.remove();
      }

      return (V)var2;
   }

   @Override
   public void clear() {
      this.checkMutable();
      if (!this.entryList.isEmpty()) {
         this.entryList.clear();
      }

      if (!this.overflowEntries.isEmpty()) {
         this.overflowEntries.clear();
      }
   }

   @Override
   public boolean containsKey(Object var1) {
      var1 = var1;
      boolean var2;
      if (this.binarySearchInArray((K)var1) < 0 && !this.overflowEntries.containsKey(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   Set<java.util.Map.Entry<K, V>> descendingEntrySet() {
      if (this.lazyDescendingEntrySet == null) {
         this.lazyDescendingEntrySet = new SmallSortedMap.DescendingEntrySet(this);
      }

      return this.lazyDescendingEntrySet;
   }

   @Override
   public Set<java.util.Map.Entry<K, V>> entrySet() {
      if (this.lazyEntrySet == null) {
         this.lazyEntrySet = new SmallSortedMap.EntrySet(this);
      }

      return this.lazyEntrySet;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof SmallSortedMap)) {
         return super.equals(var1);
      } else {
         var1 = var1;
         int var4 = this.size();
         if (var4 != var1.size()) {
            return false;
         } else {
            int var3 = this.getNumArrayEntries();
            if (var3 != var1.getNumArrayEntries()) {
               return this.entrySet().equals(var1.entrySet());
            } else {
               for (int var2 = 0; var2 < var3; var2++) {
                  if (!this.getArrayEntryAt(var2).equals(var1.getArrayEntryAt(var2))) {
                     return false;
                  }
               }

               return var3 != var4 ? this.overflowEntries.equals(var1.overflowEntries) : true;
            }
         }
      }
   }

   @Override
   public V get(Object var1) {
      var1 = var1;
      int var2 = this.binarySearchInArray((K)var1);
      return var2 >= 0 ? this.entryList.get(var2).getValue() : this.overflowEntries.get(var1);
   }

   public java.util.Map.Entry<K, V> getArrayEntryAt(int var1) {
      return (java.util.Map.Entry<K, V>)this.entryList.get(var1);
   }

   public int getNumArrayEntries() {
      return this.entryList.size();
   }

   public int getNumOverflowEntries() {
      return this.overflowEntries.size();
   }

   public Iterable<java.util.Map.Entry<K, V>> getOverflowEntries() {
      Object var1;
      if (this.overflowEntries.isEmpty()) {
         var1 = SmallSortedMap.EmptySet.iterable();
      } else {
         var1 = this.overflowEntries.entrySet();
      }

      return (Iterable<java.util.Map.Entry<K, V>>)var1;
   }

   Iterable<java.util.Map.Entry<K, V>> getOverflowEntriesDescending() {
      Object var1;
      if (this.overflowEntriesDescending.isEmpty()) {
         var1 = SmallSortedMap.EmptySet.iterable();
      } else {
         var1 = this.overflowEntriesDescending.entrySet();
      }

      return (Iterable<java.util.Map.Entry<K, V>>)var1;
   }

   @Override
   public int hashCode() {
      int var3 = this.getNumArrayEntries();
      int var2 = 0;

      int var1;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += this.entryList.get(var2).hashCode();
      }

      var2 = var1;
      if (this.getNumOverflowEntries() > 0) {
         var2 = var1 + this.overflowEntries.hashCode();
      }

      return var2;
   }

   public boolean isImmutable() {
      return this.isImmutable;
   }

   public void makeImmutable() {
      if (!this.isImmutable) {
         Map var1;
         if (this.overflowEntries.isEmpty()) {
            var1 = Collections.emptyMap();
         } else {
            var1 = Collections.unmodifiableMap(this.overflowEntries);
         }

         this.overflowEntries = var1;
         if (this.overflowEntriesDescending.isEmpty()) {
            var1 = Collections.emptyMap();
         } else {
            var1 = Collections.unmodifiableMap(this.overflowEntriesDescending);
         }

         this.overflowEntriesDescending = var1;
         this.isImmutable = true;
      }
   }

   public V put(K var1, V var2) {
      this.checkMutable();
      int var3 = this.binarySearchInArray((K)var1);
      if (var3 >= 0) {
         return this.entryList.get(var3).setValue((V)var2);
      } else {
         this.ensureEntryArrayMutable();
         var3 = -(var3 + 1);
         if (var3 >= this.maxArraySize) {
            return this.getOverflowEntriesMutable().put((K)var1, (V)var2);
         } else {
            int var5 = this.entryList.size();
            int var4 = this.maxArraySize;
            if (var5 == var4) {
               SmallSortedMap.Entry var6 = this.entryList.remove(var4 - 1);
               this.getOverflowEntriesMutable().put((K)var6.getKey(), (V)var6.getValue());
            }

            this.entryList.add(var3, new SmallSortedMap.Entry((K)this, var1, var2));
            return null;
         }
      }
   }

   @Override
   public V remove(Object var1) {
      this.checkMutable();
      var1 = var1;
      int var2 = this.binarySearchInArray((K)var1);
      if (var2 >= 0) {
         return this.removeArrayEntryAt(var2);
      } else {
         return this.overflowEntries.isEmpty() ? null : this.overflowEntries.remove(var1);
      }
   }

   @Override
   public int size() {
      return this.entryList.size() + this.overflowEntries.size();
   }

   private class DescendingEntryIterator implements Iterator<java.util.Map.Entry<K, V>> {
      private Iterator<java.util.Map.Entry<K, V>> lazyOverflowIterator;
      private int pos;
      final SmallSortedMap this$0;

      private DescendingEntryIterator(SmallSortedMap var1) {
         this.this$0 = var1;
         this.pos = var1.entryList.size();
      }

      private Iterator<java.util.Map.Entry<K, V>> getOverflowIterator() {
         if (this.lazyOverflowIterator == null) {
            this.lazyOverflowIterator = this.this$0.overflowEntriesDescending.entrySet().iterator();
         }

         return this.lazyOverflowIterator;
      }

      @Override
      public boolean hasNext() {
         int var1 = this.pos;
         boolean var2;
         if ((var1 <= 0 || var1 > this.this$0.entryList.size()) && !this.getOverflowIterator().hasNext()) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public java.util.Map.Entry<K, V> next() {
         if (this.getOverflowIterator().hasNext()) {
            return (java.util.Map.Entry<K, V>)this.getOverflowIterator().next();
         } else {
            List var2 = this.this$0.entryList;
            int var1 = this.pos - 1;
            this.pos = var1;
            return (java.util.Map.Entry<K, V>)var2.get(var1);
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private class DescendingEntrySet extends SmallSortedMap<K, V>.EntrySet {
      final SmallSortedMap this$0;

      private DescendingEntrySet(SmallSortedMap var1) {
         super(var1);
         this.this$0 = var1;
      }

      @Override
      public Iterator<java.util.Map.Entry<K, V>> iterator() {
         return this.this$0.new DescendingEntryIterator(this.this$0);
      }
   }

   private static class EmptySet {
      private static final Iterable<Object> ITERABLE = new Iterable<Object>() {
         @Override
         public Iterator<Object> iterator() {
            return SmallSortedMap.EmptySet.ITERATOR;
         }
      };
      private static final Iterator<Object> ITERATOR = new Iterator<Object>() {
         @Override
         public boolean hasNext() {
            return false;
         }

         @Override
         public Object next() {
            throw new NoSuchElementException();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      };

      static <T> Iterable<T> iterable() {
         return (Iterable<T>)ITERABLE;
      }
   }

   private class Entry implements java.util.Map.Entry<K, V>, Comparable<SmallSortedMap<K, V>.Entry> {
      private final K key;
      final SmallSortedMap this$0;
      private V value;

      Entry(K var1, V var2, Object var3) {
         this.this$0 = var1;
         this.key = (K)var2;
         this.value = (V)var3;
      }

      Entry(java.util.Map.Entry<K, V> var1, java.util.Map.Entry var2) {
         this((K)var1, (Comparable)var2.getKey(), var2.getValue());
      }

      private boolean equals(Object var1, Object var2) {
         boolean var3;
         if (var1 == null) {
            if (var2 == null) {
               var3 = true;
            } else {
               var3 = false;
            }
         } else {
            var3 = var1.equals(var2);
         }

         return var3;
      }

      public int compareTo(SmallSortedMap<K, V>.Entry var1) {
         return this.getKey().compareTo(var1.getKey());
      }

      @Override
      public boolean equals(Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof java.util.Map.Entry)) {
            return false;
         } else {
            var1 = var1;
            if (!this.equals(this.key, var1.getKey()) || !this.equals(this.value, var1.getValue())) {
               var2 = false;
            }

            return var2;
         }
      }

      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public int hashCode() {
         Comparable var3 = this.key;
         int var2 = 0;
         int var1;
         if (var3 == null) {
            var1 = 0;
         } else {
            var1 = var3.hashCode();
         }

         var3 = this.value;
         if (var3 != null) {
            var2 = var3.hashCode();
         }

         return var1 ^ var2;
      }

      @Override
      public V setValue(V var1) {
         this.this$0.checkMutable();
         Object var2 = this.value;
         this.value = (V)var1;
         return (V)var2;
      }

      @Override
      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.key);
         var1.append("=");
         var1.append(this.value);
         return var1.toString();
      }
   }

   private class EntryIterator implements Iterator<java.util.Map.Entry<K, V>> {
      private Iterator<java.util.Map.Entry<K, V>> lazyOverflowIterator;
      private boolean nextCalledBeforeRemove;
      private int pos;
      final SmallSortedMap this$0;

      private EntryIterator(SmallSortedMap var1) {
         this.this$0 = var1;
         this.pos = -1;
      }

      private Iterator<java.util.Map.Entry<K, V>> getOverflowIterator() {
         if (this.lazyOverflowIterator == null) {
            this.lazyOverflowIterator = this.this$0.overflowEntries.entrySet().iterator();
         }

         return this.lazyOverflowIterator;
      }

      @Override
      public boolean hasNext() {
         int var1 = this.pos;
         boolean var3 = true;
         boolean var2 = var3;
         if (var1 + 1 >= this.this$0.entryList.size()) {
            if (!this.this$0.overflowEntries.isEmpty() && this.getOverflowIterator().hasNext()) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         return var2;
      }

      public java.util.Map.Entry<K, V> next() {
         this.nextCalledBeforeRemove = true;
         int var1 = this.pos + 1;
         this.pos = var1;
         return var1 < this.this$0.entryList.size()
            ? (java.util.Map.Entry)this.this$0.entryList.get(this.pos)
            : (java.util.Map.Entry)this.getOverflowIterator().next();
      }

      @Override
      public void remove() {
         if (this.nextCalledBeforeRemove) {
            this.nextCalledBeforeRemove = false;
            this.this$0.checkMutable();
            if (this.pos < this.this$0.entryList.size()) {
               SmallSortedMap var2 = this.this$0;
               int var1 = this.pos--;
               var2.removeArrayEntryAt(var1);
            } else {
               this.getOverflowIterator().remove();
            }
         } else {
            throw new IllegalStateException("remove() was called before next()");
         }
      }
   }

   private class EntrySet extends AbstractSet<java.util.Map.Entry<K, V>> {
      final SmallSortedMap this$0;

      private EntrySet(SmallSortedMap var1) {
         this.this$0 = var1;
      }

      public boolean add(java.util.Map.Entry<K, V> var1) {
         if (!this.contains(var1)) {
            this.this$0.put((K)var1.getKey(), var1.getValue());
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void clear() {
         this.this$0.clear();
      }

      @Override
      public boolean contains(Object var1) {
         java.util.Map.Entry var3 = (java.util.Map.Entry)var1;
         var1 = this.this$0.get(var3.getKey());
         Object var5 = var3.getValue();
         boolean var2;
         if (var1 == var5 || var1 != null && var1.equals(var5)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Override
      public Iterator<java.util.Map.Entry<K, V>> iterator() {
         return this.this$0.new EntryIterator(this.this$0);
      }

      @Override
      public boolean remove(Object var1) {
         var1 = var1;
         if (this.contains(var1)) {
            this.this$0.remove(var1.getKey());
            return true;
         } else {
            return false;
         }
      }

      @Override
      public int size() {
         return this.this$0.size();
      }
   }
}
