package com.signify.hue.flutterreactiveble.ble

import io.reactivex.subjects.BehaviorSubject

internal class ConnectionQueue {
   private final val queueSubject: BehaviorSubject<List<String>>

   public fun addToQueue(deviceId: String) {
      val var4: java.util.List = this.queueSubject.getValue();
      var var2: java.lang.String = null;
      val var3: Any = null;
      if (var4 != null) {
         val var8: java.util.Iterator = var4.iterator();

         do {
            var2 = (java.lang.String)var3;
            if (!var8.hasNext()) {
               break;
            }

            var2 = (java.lang.String)var8.next();
         } while (!(var2 as java.lang.String == var1));

         var2 = var2;
      }

      if (var2 == null) {
         val var6: java.util.List = this.queueSubject.getValue();
         if (var6 != null) {
            val var7: java.util.List = CollectionsKt.toMutableList(var6);
            var7.add(var1);
            this.queueSubject.onNext(var7);
         }
      }
   }

   internal fun getCurrentQueue(): List<String>? {
      return this.queueSubject.getValue();
   }

   public fun observeQueue(): BehaviorSubject<List<String>> {
      return this.queueSubject;
   }

   public fun removeFromQueue(deviceId: String) {
      var var2: java.util.List = this.queueSubject.getValue();
      if (var2 != null) {
         var2 = CollectionsKt.toMutableList(var2);
         var2.remove(var1);
         this.queueSubject.onNext(var2);
      }
   }
}
