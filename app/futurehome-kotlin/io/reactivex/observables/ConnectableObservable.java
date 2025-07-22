package io.reactivex.observables;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableAutoConnect;
import io.reactivex.internal.operators.observable.ObservablePublishAlt;
import io.reactivex.internal.operators.observable.ObservablePublishClassic;
import io.reactivex.internal.operators.observable.ObservableRefCount;
import io.reactivex.internal.util.ConnectConsumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public abstract class ConnectableObservable<T> extends Observable<T> {
   private ConnectableObservable<T> onRefCount() {
      return this instanceof ObservablePublishClassic
         ? RxJavaPlugins.onAssembly(new ObservablePublishAlt<>(((ObservablePublishClassic)this).publishSource()))
         : this;
   }

   public Observable<T> autoConnect() {
      return this.autoConnect(1);
   }

   public Observable<T> autoConnect(int var1) {
      return this.autoConnect(var1, Functions.emptyConsumer());
   }

   public Observable<T> autoConnect(int var1, Consumer<? super Disposable> var2) {
      if (var1 <= 0) {
         this.connect(var2);
         return RxJavaPlugins.onAssembly(this);
      } else {
         return RxJavaPlugins.onAssembly(new ObservableAutoConnect<>(this, var1, var2));
      }
   }

   public final Disposable connect() {
      ConnectConsumer var1 = new ConnectConsumer();
      this.connect(var1);
      return var1.disposable;
   }

   public abstract void connect(Consumer<? super Disposable> var1);

   @CheckReturnValue
   @SchedulerSupport("none")
   public Observable<T> refCount() {
      return RxJavaPlugins.onAssembly(new ObservableRefCount<>(this.onRefCount()));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> refCount(int var1) {
      return this.refCount(var1, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> refCount(int var1, long var2, TimeUnit var4) {
      return this.refCount(var1, var2, var4, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> refCount(int var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.verifyPositive(var1, "subscriberCount");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableRefCount<>(this.onRefCount(), var1, var2, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Observable<T> refCount(long var1, TimeUnit var3) {
      return this.refCount(1, var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Observable<T> refCount(long var1, TimeUnit var3, Scheduler var4) {
      return this.refCount(1, var1, var3, var4);
   }
}
