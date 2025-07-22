package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class SafeObserver<T> implements Observer<T>, Disposable {
   boolean done;
   final Observer<? super T> downstream;
   Disposable upstream;

   public SafeObserver(Observer<? super T> var1) {
      this.downstream = var1;
   }

   @Override
   public void dispose() {
      this.upstream.dispose();
   }

   @Override
   public boolean isDisposed() {
      return this.upstream.isDisposed();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onComplete() {
      if (!this.done) {
         this.done = true;
         if (this.upstream == null) {
            this.onCompleteNoSubscription();
         } else {
            try {
               this.downstream.onComplete();
            } catch (Throwable var3) {
               Exceptions.throwIfFatal(var3);
               RxJavaPlugins.onError(var3);
               return;
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   void onCompleteNoSubscription() {
      NullPointerException var1 = new NullPointerException("Subscription not set!");

      try {
         this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
      } catch (Throwable var7) {
         Exceptions.throwIfFatal(var7);
         RxJavaPlugins.onError(new CompositeException(var1, var7));
         return;
      }

      try {
         this.downstream.onError(var1);
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         RxJavaPlugins.onError(new CompositeException(var1, var8));
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onError(Throwable var1) {
      if (this.done) {
         RxJavaPlugins.onError(var1);
      } else {
         this.done = true;
         if (this.upstream == null) {
            NullPointerException var17 = new NullPointerException("Subscription not set!");

            try {
               this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
            } catch (Throwable var14) {
               Exceptions.throwIfFatal(var14);
               RxJavaPlugins.onError(new CompositeException(var1, var17, var14));
               return;
            }

            try {
               Observer var4 = this.downstream;
               CompositeException var3 = new CompositeException(var1, var17);
               var4.onError(var3);
            } catch (Throwable var15) {
               Exceptions.throwIfFatal(var15);
               RxJavaPlugins.onError(new CompositeException(var1, var17, var15));
               return;
            }
         } else {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            try {
               this.downstream.onError((Throwable)var2);
            } catch (Throwable var16) {
               Exceptions.throwIfFatal(var16);
               RxJavaPlugins.onError(new CompositeException((Throwable)var2, var16));
               return;
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onNext(T var1) {
      if (!this.done) {
         if (this.upstream == null) {
            this.onNextNoSubscription();
         } else if (var1 == null) {
            NullPointerException var2 = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");

            try {
               this.upstream.dispose();
            } catch (Throwable var12) {
               Exceptions.throwIfFatal(var12);
               this.onError(new CompositeException(var2, var12));
               return;
            }

            this.onError(var2);
         } else {
            try {
               this.downstream.onNext((T)var1);
            } catch (Throwable var14) {
               Exceptions.throwIfFatal(var14);

               try {
                  this.upstream.dispose();
               } catch (Throwable var13) {
                  Exceptions.throwIfFatal(var13);
                  this.onError(new CompositeException(var14, var13));
                  return;
               }

               this.onError(var14);
               return;
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   void onNextNoSubscription() {
      this.done = true;
      NullPointerException var1 = new NullPointerException("Subscription not set!");

      try {
         this.downstream.onSubscribe(EmptyDisposable.INSTANCE);
      } catch (Throwable var7) {
         Exceptions.throwIfFatal(var7);
         RxJavaPlugins.onError(new CompositeException(var1, var7));
         return;
      }

      try {
         this.downstream.onError(var1);
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         RxJavaPlugins.onError(new CompositeException(var1, var8));
         return;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void onSubscribe(Disposable var1) {
      if (DisposableHelper.validate(this.upstream, var1)) {
         this.upstream = var1;

         try {
            this.downstream.onSubscribe(this);
         } catch (Throwable var8) {
            Exceptions.throwIfFatal(var8);
            this.done = true;

            try {
               var1.dispose();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               RxJavaPlugins.onError(new CompositeException(var8, var7));
               return;
            }

            RxJavaPlugins.onError(var8);
            return;
         }
      }
   }
}
