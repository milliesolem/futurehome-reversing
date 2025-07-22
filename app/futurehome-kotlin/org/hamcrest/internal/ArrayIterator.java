package org.hamcrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayIterator implements Iterator<Object> {
   private final Object array;
   private int currentIndex = 0;

   public ArrayIterator(Object var1) {
      if (var1.getClass().isArray()) {
         this.array = var1;
      } else {
         throw new IllegalArgumentException("not an array");
      }
   }

   @Override
   public boolean hasNext() {
      boolean var1;
      if (this.currentIndex < Array.getLength(this.array)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public Object next() {
      Object var2 = this.array;
      int var1 = this.currentIndex++;
      return Array.get(var2, var1);
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException("cannot remove items from an array");
   }
}
