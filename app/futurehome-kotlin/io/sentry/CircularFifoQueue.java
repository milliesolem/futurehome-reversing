package io.sentry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

final class CircularFifoQueue<E> extends AbstractCollection<E> implements Queue<E>, Serializable {
   private static final long serialVersionUID = -8423413834657610406L;
   private transient E[] elements;
   private transient int end;
   private transient boolean full;
   private final int maxElements;
   private transient int start = 0;

   public CircularFifoQueue() {
      this(32);
   }

   CircularFifoQueue(int var1) {
      this.end = 0;
      this.full = false;
      if (var1 > 0) {
         Object[] var2 = new Object[var1];
         this.elements = (E[])var2;
         this.maxElements = var2.length;
      } else {
         throw new IllegalArgumentException("The size must be greater than 0");
      }
   }

   public CircularFifoQueue(Collection<? extends E> var1) {
      this(var1.size());
      this.addAll(var1);
   }

   private int decrement(int var1) {
      int var2 = var1 - 1;
      var1 = var2;
      if (var2 < 0) {
         var1 = this.maxElements - 1;
      }

      return var1;
   }

   private int increment(int var1) {
      int var2 = var1 + 1;
      var1 = var2;
      if (var2 >= this.maxElements) {
         var1 = 0;
      }

      return var1;
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.elements = (E[])(new Object[this.maxElements]);
      int var3 = var1.readInt();

      for (int var2 = 0; var2 < var3; var2++) {
         this.elements[var2] = (E)var1.readObject();
      }

      this.start = 0;
      boolean var4;
      if (var3 == this.maxElements) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.full = var4;
      if (var4) {
         this.end = 0;
      } else {
         this.end = var3;
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeInt(this.size());
      Iterator var2 = this.iterator();

      while (var2.hasNext()) {
         var1.writeObject(var2.next());
      }
   }

   @Override
   public boolean add(E var1) {
      if (var1 != null) {
         if (this.isAtFullCapacity()) {
            this.remove();
         }

         Object[] var4 = this.elements;
         int var2 = this.end;
         int var3 = var2 + 1;
         this.end = var3;
         var4[var2] = var1;
         if (var3 >= this.maxElements) {
            this.end = 0;
         }

         if (this.end == this.start) {
            this.full = true;
         }

         return true;
      } else {
         throw new NullPointerException("Attempted to add null object to queue");
      }
   }

   @Override
   public void clear() {
      this.full = false;
      this.start = 0;
      this.end = 0;
      Arrays.fill(this.elements, null);
   }

   @Override
   public E element() {
      if (!this.isEmpty()) {
         return this.peek();
      } else {
         throw new NoSuchElementException("queue is empty");
      }
   }

   public E get(int var1) {
      int var2 = this.size();
      if (var1 >= 0 && var1 < var2) {
         int var3 = this.start;
         var2 = this.maxElements;
         return this.elements[(var3 + var1) % var2];
      } else {
         throw new NoSuchElementException(String.format("The specified index (%1$d) is outside the available range [0, %2$d)", var1, var2));
      }
   }

   public boolean isAtFullCapacity() {
      boolean var1;
      if (this.size() == this.maxElements) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isFull() {
      return false;
   }

   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>(this) {
         private int index;
         private boolean isFirst;
         private int lastReturnedIndex;
         final CircularFifoQueue this$0;

         {
            this.this$0 = var1;
            this.index = var1.start;
            this.lastReturnedIndex = -1;
            this.isFirst = var1.full;
         }

         @Override
         public boolean hasNext() {
            boolean var1;
            if (!this.isFirst && this.index == this.this$0.end) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         @Override
         public E next() {
            if (this.hasNext()) {
               this.isFirst = false;
               int var1 = this.index;
               this.lastReturnedIndex = var1;
               this.index = this.this$0.increment(var1);
               return this.this$0.elements[this.lastReturnedIndex];
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            int var1 = this.lastReturnedIndex;
            if (var1 != -1) {
               if (var1 == this.this$0.start) {
                  this.this$0.remove();
                  this.lastReturnedIndex = -1;
               } else {
                  label24: {
                     int var2 = this.lastReturnedIndex + 1;
                     var1 = var2;
                     if (this.this$0.start < this.lastReturnedIndex) {
                        var1 = var2;
                        if (var2 < this.this$0.end) {
                           System.arraycopy(this.this$0.elements, var2, this.this$0.elements, this.lastReturnedIndex, this.this$0.end - var2);
                           break label24;
                        }
                     }

                     while (var1 != this.this$0.end) {
                        if (var1 >= this.this$0.maxElements) {
                           this.this$0.elements[var1 - 1] = this.this$0.elements[0];
                           var1 = 0;
                        } else {
                           this.this$0.elements[this.this$0.decrement(var1)] = this.this$0.elements[var1];
                           var1 = this.this$0.increment(var1);
                        }
                     }
                  }

                  this.lastReturnedIndex = -1;
                  CircularFifoQueue var3 = this.this$0;
                  var3.end = var3.decrement(var3.end);
                  this.this$0.elements[this.this$0.end] = null;
                  this.this$0.full = false;
                  this.index = this.this$0.decrement(this.index);
               }
            } else {
               throw new IllegalStateException();
            }
         }
      };
   }

   public int maxSize() {
      return this.maxElements;
   }

   @Override
   public boolean offer(E var1) {
      return this.add((E)var1);
   }

   @Override
   public E peek() {
      return this.isEmpty() ? null : this.elements[this.start];
   }

   @Override
   public E poll() {
      return this.isEmpty() ? null : this.remove();
   }

   @Override
   public E remove() {
      if (!this.isEmpty()) {
         Object[] var3 = this.elements;
         int var2 = this.start;
         Object var4 = var3[var2];
         if (var4 != null) {
            int var1 = var2 + 1;
            this.start = var1;
            var3[var2] = null;
            if (var1 >= this.maxElements) {
               this.start = 0;
            }

            this.full = false;
         }

         return (E)var4;
      } else {
         throw new NoSuchElementException("queue is empty");
      }
   }

   @Override
   public int size() {
      int var2 = this.end;
      int var1 = this.start;
      if (var2 < var1) {
         var1 = this.maxElements - var1 + var2;
      } else if (var2 == var1) {
         if (this.full) {
            var1 = this.maxElements;
         } else {
            var1 = 0;
         }
      } else {
         var1 = var2 - var1;
      }

      return var1;
   }
}
