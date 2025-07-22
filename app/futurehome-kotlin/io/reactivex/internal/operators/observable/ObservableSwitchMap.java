package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSwitchMap<T, R> extends AbstractObservableWithUpstream<T, R> {
   final int bufferSize;
   final boolean delayErrors;
   final Function<? super T, ? extends ObservableSource<? extends R>> mapper;

   public ObservableSwitchMap(ObservableSource<T> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2, int var3, boolean var4) {
      super(var1);
      this.mapper = var2;
      this.bufferSize = var3;
      this.delayErrors = var4;
   }

   @Override
   public void subscribeActual(Observer<? super R> var1) {
      if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, var1, this.mapper)) {
         this.source.subscribe(new ObservableSwitchMap.SwitchMapObserver<>(var1, this.mapper, this.bufferSize, this.delayErrors));
      }
   }

   static final class SwitchMapInnerObserver<T, R> extends AtomicReference<Disposable> implements Observer<R> {
      private static final long serialVersionUID = 3837284832786408377L;
      final int bufferSize;
      volatile boolean done;
      final long index;
      final ObservableSwitchMap.SwitchMapObserver<T, R> parent;
      volatile SimpleQueue<R> queue;

      SwitchMapInnerObserver(ObservableSwitchMap.SwitchMapObserver<T, R> var1, long var2, int var4) {
         this.parent = var1;
         this.index = var2;
         this.bufferSize = var4;
      }

      public void cancel() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         if (this.index == this.parent.unique) {
            this.done = true;
            this.parent.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.innerError(this, var1);
      }

      @Override
      public void onNext(R var1) {
         if (this.index == this.parent.unique) {
            if (var1 != null) {
               this.queue.offer((R)var1);
            }

            this.parent.drain();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this, var1)) {
            if (var1 instanceof QueueDisposable) {
               QueueDisposable var3 = (QueueDisposable)var1;
               int var2 = var3.requestFusion(7);
               if (var2 == 1) {
                  this.queue = var3;
                  this.done = true;
                  this.parent.drain();
                  return;
               }

               if (var2 == 2) {
                  this.queue = var3;
                  return;
               }
            }

            this.queue = new SpscLinkedArrayQueue(this.bufferSize);
         }
      }
   }

   static final class SwitchMapObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
      static final ObservableSwitchMap.SwitchMapInnerObserver<Object, Object> CANCELLED;
      private static final long serialVersionUID = -3491074160481096299L;
      final AtomicReference<ObservableSwitchMap.SwitchMapInnerObserver<T, R>> active = new AtomicReference<>();
      final int bufferSize;
      volatile boolean cancelled;
      final boolean delayErrors;
      volatile boolean done;
      final Observer<? super R> downstream;
      final AtomicThrowable errors;
      final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
      volatile long unique;
      Disposable upstream;

      static {
         ObservableSwitchMap.SwitchMapInnerObserver var0 = new ObservableSwitchMap.SwitchMapInnerObserver(null, -1L, 1);
         CANCELLED = var0;
         var0.cancel();
      }

      SwitchMapObserver(Observer<? super R> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2, int var3, boolean var4) {
         this.downstream = var1;
         this.mapper = var2;
         this.bufferSize = var3;
         this.delayErrors = var4;
         this.errors = new AtomicThrowable();
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
            this.disposeInner();
         }
      }

      void disposeInner() {
         ObservableSwitchMap.SwitchMapInnerObserver var2 = this.active.get();
         ObservableSwitchMap.SwitchMapInnerObserver var1 = CANCELLED;
         if (var2 != var1) {
            var2 = this.active.getAndSet(var1);
            if (var2 != var1 && var2 != null) {
               var2.cancel();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var7 = this.downstream;
            AtomicReference var9 = this.active;
            boolean var4 = this.delayErrors;
            int var2 = 1;

            while (!this.cancelled) {
               if (this.done) {
                  boolean var1;
                  if (var9.get() == null) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var4) {
                     if (var1) {
                        Throwable var6 = this.errors.get();
                        if (var6 != null) {
                           var7.onError(var6);
                        } else {
                           var7.onComplete();
                        }

                        return;
                     }
                  } else {
                     if (this.errors.get() != null) {
                        var7.onError(this.errors.terminate());
                        return;
                     }

                     if (var1) {
                        var7.onComplete();
                        return;
                     }
                  }
               }

               ObservableSwitchMap.SwitchMapInnerObserver var10 = (ObservableSwitchMap.SwitchMapInnerObserver)var9.get();
               if (var10 != null) {
                  SimpleQueue var8 = var10.queue;
                  if (var8 != null) {
                     if (var10.done) {
                        boolean var5 = var8.isEmpty();
                        if (var4) {
                           if (var5) {
                              ExternalSyntheticBackportWithForwarding0.m(var9, var10, null);
                              continue;
                           }
                        } else {
                           if (this.errors.get() != null) {
                              var7.onError(this.errors.terminate());
                              return;
                           }

                           if (var5) {
                              ExternalSyntheticBackportWithForwarding0.m(var9, var10, null);
                              continue;
                           }
                        }
                     }

                     boolean var13 = false;

                     label178: {
                        while (true) {
                           if (this.cancelled) {
                              return;
                           }

                           if (var10 != var9.get()) {
                              break;
                           }

                           if (!var4 && this.errors.get() != null) {
                              var7.onError(this.errors.terminate());
                              return;
                           }

                           boolean var15 = var10.done;

                           Object var16;
                           try {
                              var16 = var8.poll();
                           } catch (Throwable var12) {
                              label194: {
                                 Exceptions.throwIfFatal(var12);
                                 this.errors.addThrowable(var12);
                                 ExternalSyntheticBackportWithForwarding0.m(var9, var10, null);
                                 if (!var4) {
                                    this.disposeInner();
                                    this.upstream.dispose();
                                    this.done = true;
                                 } else {
                                    var10.cancel();
                                 }

                                 var16 = null;
                                 var13 = true;
                                 break label194;
                              }
                           }

                           boolean var3;
                           if (var16 == null) {
                              var3 = true;
                           } else {
                              var3 = false;
                           }

                           if (var15 && var3) {
                              ExternalSyntheticBackportWithForwarding0.m(var9, var10, null);
                              break;
                           }

                           if (var3) {
                              break label178;
                           }

                           var7.onNext(var16);
                        }

                        var13 = true;
                     }

                     if (var13) {
                        continue;
                     }
                  }
               }

               int var14 = this.addAndGet(-var2);
               var2 = var14;
               if (var14 == 0) {
                  return;
               }
            }
         }
      }

      void innerError(ObservableSwitchMap.SwitchMapInnerObserver<T, R> var1, Throwable var2) {
         if (var1.index == this.unique && this.errors.addThrowable(var2)) {
            if (!this.delayErrors) {
               this.upstream.dispose();
               this.done = true;
            }

            var1.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var2);
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.drain();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (!this.done && this.errors.addThrowable(var1)) {
            if (!this.delayErrors) {
               this.disposeInner();
            }

            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         long var2 = this.unique + 1L;
         this.unique = var2;
         ObservableSwitchMap.SwitchMapInnerObserver var4 = this.active.get();
         if (var4 != null) {
            var4.cancel();
         }

         ObservableSource var5;
         try {
            var5 = ObjectHelper.requireNonNull(this.mapper.apply((T)var1), "The ObservableSource returned is null");
         } catch (Throwable var7) {
            Exceptions.throwIfFatal(var7);
            this.upstream.dispose();
            this.onError(var7);
            return;
         }

         var4 = new ObservableSwitchMap.SwitchMapInnerObserver<>(this, var2, this.bufferSize);

         while (true) {
            var1 = this.active.get();
            if (var1 == CANCELLED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.active, var1, var4)) {
               var5.subscribe(var4);
               break;
            }
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
