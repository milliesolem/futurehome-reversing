package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

public final class ObservableRange extends Observable<Integer> {
   private final long end;
   private final int start;

   public ObservableRange(int var1, int var2) {
      this.start = var1;
      this.end = (long)var1 + var2;
   }

   @Override
   protected void subscribeActual(Observer<? super Integer> var1) {
      ObservableRange.RangeDisposable var2 = new ObservableRange.RangeDisposable(var1, this.start, this.end);
      var1.onSubscribe(var2);
      var2.run();
   }

   static final class RangeDisposable extends BasicIntQueueDisposable<Integer> {
      private static final long serialVersionUID = 396518478098735504L;
      final Observer<? super Integer> downstream;
      final long end;
      boolean fused;
      long index;

      RangeDisposable(Observer<? super Integer> var1, long var2, long var4) {
         this.downstream = var1;
         this.index = var2;
         this.end = var4;
      }

      @Override
      public void clear() {
         this.index = this.end;
         this.lazySet(1);
      }

      @Override
      public void dispose() {
         this.set(1);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() != 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public boolean isEmpty() {
         boolean var1;
         if (this.index == this.end) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Integer poll() throws Exception {
         long var1 = this.index;
         if (var1 != this.end) {
            this.index = 1L + var1;
            return (int)var1;
         } else {
            this.lazySet(1);
            return null;
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 1) != 0) {
            this.fused = true;
            return 1;
         } else {
            return 0;
         }
      }

      void run() {
         if (!this.fused) {
            Observer var5 = this.downstream;
            long var3 = this.end;

            for (long var1 = this.index; var1 != var3 && this.get() == 0; var1++) {
               var5.onNext((int)var1);
            }

            if (this.get() == 0) {
               this.lazySet(1);
               var5.onComplete();
            }
         }
      }
   }
}
