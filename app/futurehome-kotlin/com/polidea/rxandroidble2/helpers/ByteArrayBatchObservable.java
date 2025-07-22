package com.polidea.rxandroidble2.helpers;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import java.nio.ByteBuffer;
import org.reactivestreams.Subscriber;

public class ByteArrayBatchObservable extends Flowable<byte[]> {
   final ByteBuffer byteBuffer;
   final int maxBatchSize;

   public ByteArrayBatchObservable(byte[] var1, int var2) {
      if (var2 > 0) {
         this.byteBuffer = ByteBuffer.wrap(var1);
         this.maxBatchSize = var2;
      } else {
         StringBuilder var3 = new StringBuilder("maxBatchSize must be > 0 but found: ");
         var3.append(var2);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @Override
   protected void subscribeActual(Subscriber<byte[]> var1) {
      Flowable.generate(new Consumer<Emitter<byte[]>>(this) {
         final ByteArrayBatchObservable this$0;

         {
            this.this$0 = var1;
         }

         public void accept(Emitter<byte[]> var1) {
            int var2 = Math.min(this.this$0.byteBuffer.remaining(), this.this$0.maxBatchSize);
            if (var2 == 0) {
               var1.onComplete();
            } else {
               byte[] var3 = new byte[var2];
               this.this$0.byteBuffer.get(var3);
               var1.onNext(var3);
            }
         }
      }).subscribe(var1);
   }
}
