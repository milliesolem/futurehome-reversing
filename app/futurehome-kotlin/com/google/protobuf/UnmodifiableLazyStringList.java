package com.google.protobuf;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

@Deprecated
public class UnmodifiableLazyStringList extends AbstractList<String> implements LazyStringList, RandomAccess {
   private final LazyStringList list;

   public UnmodifiableLazyStringList(LazyStringList var1) {
      this.list = var1;
   }

   @Override
   public void add(ByteString var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void add(byte[] var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean addAllByteArray(Collection<byte[]> var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean addAllByteString(Collection<? extends ByteString> var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public List<byte[]> asByteArrayList() {
      return Collections.unmodifiableList(this.list.asByteArrayList());
   }

   @Override
   public List<ByteString> asByteStringList() {
      return Collections.unmodifiableList(this.list.asByteStringList());
   }

   public String get(int var1) {
      return this.list.get(var1);
   }

   @Override
   public byte[] getByteArray(int var1) {
      return this.list.getByteArray(var1);
   }

   @Override
   public ByteString getByteString(int var1) {
      return this.list.getByteString(var1);
   }

   @Override
   public Object getRaw(int var1) {
      return this.list.getRaw(var1);
   }

   @Override
   public List<?> getUnderlyingElements() {
      return this.list.getUnderlyingElements();
   }

   @Override
   public LazyStringList getUnmodifiableView() {
      return this;
   }

   @Override
   public Iterator<String> iterator() {
      return new Iterator<String>(this) {
         Iterator<String> iter;
         final UnmodifiableLazyStringList this$0;

         {
            this.this$0 = var1;
            this.iter = var1.list.iterator();
         }

         @Override
         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public String next() {
            return this.iter.next();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   @Override
   public ListIterator<String> listIterator(int var1) {
      return new ListIterator<String>(this, var1) {
         ListIterator<String> iter;
         final UnmodifiableLazyStringList this$0;
         final int val$index;

         {
            this.this$0 = var1;
            this.val$index = var2;
            this.iter = var1.list.listIterator(var2);
         }

         public void add(String var1) {
            throw new UnsupportedOperationException();
         }

         @Override
         public boolean hasNext() {
            return this.iter.hasNext();
         }

         @Override
         public boolean hasPrevious() {
            return this.iter.hasPrevious();
         }

         public String next() {
            return this.iter.next();
         }

         @Override
         public int nextIndex() {
            return this.iter.nextIndex();
         }

         public String previous() {
            return this.iter.previous();
         }

         @Override
         public int previousIndex() {
            return this.iter.previousIndex();
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }

         public void set(String var1) {
            throw new UnsupportedOperationException();
         }
      };
   }

   @Override
   public void mergeFrom(LazyStringList var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void set(int var1, ByteString var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void set(int var1, byte[] var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int size() {
      return this.list.size();
   }
}
