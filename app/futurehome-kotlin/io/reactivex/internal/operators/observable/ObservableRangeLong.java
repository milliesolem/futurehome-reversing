package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

public final class ObservableRangeLong extends Observable<Long> {
   private final long count;
   private final long start;

   public ObservableRangeLong(long var1, long var3) {
      this.start = var1;
      this.count = var3;
   }

   @Override
   protected void subscribeActual(Observer<? super Long> var1) {
      long var2 = this.start;
      ObservableRangeLong.RangeDisposable var4 = new ObservableRangeLong.RangeDisposable(var1, var2, var2 + this.count);
      var1.onSubscribe(var4);
      var4.run();
   }

   static final class RangeDisposable extends BasicIntQueueDisposable<Long> {
      private static final long serialVersionUID = 396518478098735504L;
      final Observer<? super Long> downstream;
      final long end;
      boolean fused;
      long index;

      RangeDisposable(Observer<? super Long> var1, long var2, long var4) {
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

      public Long poll() throws Exception {
         long var1 = this.index;
         if (var1 != this.end) {
            this.index = 1L + var1;
            return var1;
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
               var5.onNext(var1);
            }

            if (this.get() == 0) {
               this.lazySet(1);
               var5.onComplete();
            }
         }
      }
   }
}
