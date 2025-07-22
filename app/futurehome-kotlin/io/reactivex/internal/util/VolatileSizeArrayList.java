package io.reactivex.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.concurrent.atomic.AtomicInteger;

public final class VolatileSizeArrayList<T> extends AtomicInteger implements List<T>, RandomAccess {
   private static final long serialVersionUID = 3972397474470203923L;
   final ArrayList<T> list;

   public VolatileSizeArrayList() {
      this.list = new ArrayList<>();
   }

   public VolatileSizeArrayList(int var1) {
      this.list = new ArrayList<>(var1);
   }

   @Override
   public void add(int var1, T var2) {
      this.list.add(var1, (T)var2);
      this.lazySet(this.list.size());
   }

   @Override
   public boolean add(T var1) {
      boolean var2 = this.list.add((T)var1);
      this.lazySet(this.list.size());
      return var2;
   }

   @Override
   public boolean addAll(int var1, Collection<? extends T> var2) {
      boolean var3 = this.list.addAll(var1, var2);
      this.lazySet(this.list.size());
      return var3;
   }

   @Override
   public boolean addAll(Collection<? extends T> var1) {
      boolean var2 = this.list.addAll(var1);
      this.lazySet(this.list.size());
      return var2;
   }

   @Override
   public void clear() {
      this.list.clear();
      this.lazySet(0);
   }

   @Override
   public boolean contains(Object var1) {
      return this.list.contains(var1);
   }

   @Override
   public boolean containsAll(Collection<?> var1) {
      return this.list.containsAll(var1);
   }

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof VolatileSizeArrayList ? this.list.equals(((VolatileSizeArrayList)var1).list) : this.list.equals(var1);
   }

   @Override
   public T get(int var1) {
      return this.list.get(var1);
   }

   @Override
   public int hashCode() {
      return this.list.hashCode();
   }

   @Override
   public int indexOf(Object var1) {
      return this.list.indexOf(var1);
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.get() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public Iterator<T> iterator() {
      return this.list.iterator();
   }

   @Override
   public int lastIndexOf(Object var1) {
      return this.list.lastIndexOf(var1);
   }

   @Override
   public ListIterator<T> listIterator() {
      return this.list.listIterator();
   }

   @Override
   public ListIterator<T> listIterator(int var1) {
      return this.list.listIterator(var1);
   }

   @Override
   public T remove(int var1) {
      Object var2 = this.list.remove(var1);
      this.lazySet(this.list.size());
      return (T)var2;
   }

   @Override
   public boolean remove(Object var1) {
      boolean var2 = this.list.remove(var1);
      this.lazySet(this.list.size());
      return var2;
   }

   @Override
   public boolean removeAll(Collection<?> var1) {
      boolean var2 = this.list.removeAll(var1);
      this.lazySet(this.list.size());
      return var2;
   }

   @Override
   public boolean retainAll(Collection<?> var1) {
      boolean var2 = this.list.retainAll(var1);
      this.lazySet(this.list.size());
      return var2;
   }

   @Override
   public T set(int var1, T var2) {
      return this.list.set(var1, (T)var2);
   }

   @Override
   public int size() {
      return this.get();
   }

   @Override
   public List<T> subList(int var1, int var2) {
      return this.list.subList(var1, var2);
   }

   @Override
   public Object[] toArray() {
      return this.list.toArray();
   }

   @Override
   public <E> E[] toArray(E[] var1) {
      return (E[])this.list.toArray(var1);
   }

   @Override
   public String toString() {
      return this.list.toString();
   }
}
