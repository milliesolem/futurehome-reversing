package io.reactivex.internal.util;

import java.util.ArrayList;

public class LinkedArrayList {
   final int capacityHint;
   Object[] head;
   int indexInTail;
   volatile int size;
   Object[] tail;

   public LinkedArrayList(int var1) {
      this.capacityHint = var1;
   }

   public void add(Object var1) {
      if (this.size == 0) {
         Object[] var4 = new Object[this.capacityHint + 1];
         this.head = var4;
         this.tail = var4;
         var4[0] = var1;
         this.indexInTail = 1;
         this.size = 1;
      } else {
         int var3 = this.indexInTail;
         int var2 = this.capacityHint;
         if (var3 == var2) {
            Object[] var5 = new Object[var2 + 1];
            var5[0] = var1;
            this.tail[var2] = var5;
            this.tail = var5;
            this.indexInTail = 1;
            this.size++;
         } else {
            this.tail[var3] = var1;
            this.indexInTail = var3 + 1;
            this.size++;
         }
      }
   }

   public Object[] head() {
      return this.head;
   }

   public int size() {
      return this.size;
   }

   @Override
   public String toString() {
      int var6 = this.capacityHint;
      int var5 = this.size;
      ArrayList var8 = new ArrayList(var5 + 1);
      Object[] var7 = this.head();
      int var1 = 0;

      label17:
      while (true) {
         int var2 = 0;

         while (var1 < var5) {
            var8.add(var7[var2]);
            int var3 = var1 + 1;
            int var4 = var2 + 1;
            var1 = var3;
            var2 = var4;
            if (var4 == var6) {
               var7 = (Object[])var7[var6];
               var1 = var3;
               continue label17;
            }
         }

         return var8.toString();
      }
   }
}
