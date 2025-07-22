package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;
import org.reactivestreams.Subscriber;

public class AppendOnlyLinkedArrayList<T> {
   final int capacity;
   final Object[] head;
   int offset;
   Object[] tail;

   public AppendOnlyLinkedArrayList(int var1) {
      this.capacity = var1;
      Object[] var2 = new Object[var1 + 1];
      this.head = var2;
      this.tail = var2;
   }

   public <U> boolean accept(Observer<? super U> var1) {
      Object[] var4 = this.head;
      int var3 = this.capacity;

      while (true) {
         int var2 = 0;
         if (var4 == null) {
            return false;
         }

         while (var2 < var3) {
            Object var5 = var4[var2];
            if (var5 == null) {
               break;
            }

            if (NotificationLite.acceptFull(var5, var1)) {
               return true;
            }

            var2++;
         }

         var4 = (Object[])var4[var3];
      }
   }

   public <U> boolean accept(Subscriber<? super U> var1) {
      Object[] var4 = this.head;
      int var3 = this.capacity;

      while (true) {
         int var2 = 0;
         if (var4 == null) {
            return false;
         }

         while (var2 < var3) {
            Object var5 = var4[var2];
            if (var5 == null) {
               break;
            }

            if (NotificationLite.acceptFull(var5, var1)) {
               return true;
            }

            var2++;
         }

         var4 = (Object[])var4[var3];
      }
   }

   public void add(T var1) {
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

   public void forEachWhile(AppendOnlyLinkedArrayList.NonThrowingPredicate<? super T> var1) {
      Object[] var4 = this.head;

      for (int var3 = this.capacity; var4 != null; var4 = (Object[])var4[var3]) {
         for (int var2 = 0; var2 < var3; var2++) {
            Object var5 = var4[var2];
            if (var5 == null) {
               break;
            }

            if (var1.test(var5)) {
               return;
            }
         }
      }
   }

   public <S> void forEachWhile(S var1, BiPredicate<? super S, ? super T> var2) throws Exception {
      Object[] var5 = this.head;
      int var4 = this.capacity;

      while (true) {
         for (int var3 = 0; var3 < var4; var3++) {
            Object var6 = var5[var3];
            if (var6 == null) {
               return;
            }

            if (var2.test(var1, var6)) {
               return;
            }
         }

         var5 = (Object[])var5[var4];
      }
   }

   public void setFirst(T var1) {
      this.head[0] = var1;
   }

   public interface NonThrowingPredicate<T> extends Predicate<T> {
      @Override
      boolean test(T var1);
   }
}
