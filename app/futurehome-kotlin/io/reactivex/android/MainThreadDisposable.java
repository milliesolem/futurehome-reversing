package io.reactivex.android;

import android.os.Looper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class MainThreadDisposable implements Disposable {
   private final AtomicBoolean unsubscribed = new AtomicBoolean();

   public static void verifyMainThread() {
      if (Looper.myLooper() != Looper.getMainLooper()) {
         StringBuilder var0 = new StringBuilder("Expected to be called on the main thread but was ");
         var0.append(Thread.currentThread().getName());
         throw new IllegalStateException(var0.toString());
      }
   }

   @Override
   public final void dispose() {
      if (this.unsubscribed.compareAndSet(false, true)) {
         if (Looper.myLooper() == Looper.getMainLooper()) {
            this.onDispose();
         } else {
            AndroidSchedulers.mainThread().scheduleDirect(new Runnable(this) {
               final MainThreadDisposable this$0;

               {
                  this.this$0 = var1;
               }

               @Override
               public void run() {
                  this.this$0.onDispose();
               }
            });
         }
      }
   }

   @Override
   public final boolean isDisposed() {
      return this.unsubscribed.get();
   }

   protected abstract void onDispose();
}
