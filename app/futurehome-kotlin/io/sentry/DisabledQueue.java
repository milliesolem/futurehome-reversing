package io.sentry;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

final class DisabledQueue<E> extends AbstractCollection<E> implements Queue<E>, Serializable {
   private static final long serialVersionUID = -8423413834657610417L;

   public DisabledQueue() {
   }

   @Override
   public boolean add(E var1) {
      return false;
   }

   @Override
   public void clear() {
   }

   @Override
   public E element() {
      return null;
   }

   @Override
   public boolean isEmpty() {
      return false;
   }

   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>(this) {
         final DisabledQueue this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public boolean hasNext() {
            return false;
         }

         @Override
         public E next() {
            throw new NoSuchElementException();
         }

         @Override
         public void remove() {
            throw new IllegalStateException();
         }
      };
   }

   @Override
   public boolean offer(E var1) {
      return false;
   }

   @Override
   public E peek() {
      return null;
   }

   @Override
   public E poll() {
      return null;
   }

   @Override
   public E remove() {
      throw new NoSuchElementException("queue is disabled");
   }

   @Override
   public int size() {
      return 0;
   }
}
