package com.jakewharton.rxrelay2;

import io.reactivex.functions.Predicate;

class AppendOnlyLinkedArrayList<T> {
   private final int capacity;
   private final Object[] head;
   private int offset;
   private Object[] tail;

   AppendOnlyLinkedArrayList(int var1) {
      this.capacity = var1;
      Object[] var2 = new Object[var1 + 1];
      this.head = var2;
      this.tail = var2;
   }

   void accept(Relay<? super T> var1) {
      Object[] var4 = this.head;

      for (int var3 = this.capacity; var4 != null; var4 = (Object[])var4[var3]) {
         for (int var2 = 0; var2 < var3; var2++) {
            Object var5 = var4[var2];
            if (var5 == null) {
               break;
            }

            var1.accept(var5);
         }
      }
   }

   void add(T var1) {
      int var4 = this.capacity;
      int var3 = this.offset;
      int var2 = var3;
      if (var3 == var4) {
         Object[] var5 = new Object[var4 + 1];
         this.tail[var4] = var5;
         this.tail = var5;
         var2 = 0;
      }

      this.tail[var2] = var1;
      this.offset = var2 + 1;
   }

   void forEachWhile(AppendOnlyLinkedArrayList.NonThrowingPredicate<? super T> var1) {
      Object[] var4 = this.head;

      for (int var3 = this.capacity; var4 != null; var4 = (Object[])var4[var3]) {
         for (int var2 = 0; var2 < var3; var2++) {
            Object var5 = var4[var2];
            if (var5 == null || var1.test(var5)) {
               break;
            }
         }
      }
   }

   public interface NonThrowingPredicate<T> extends Predicate<T> {
      @Override
      boolean test(T var1);
   }
}
