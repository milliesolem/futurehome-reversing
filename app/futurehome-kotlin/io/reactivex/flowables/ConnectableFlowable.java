package io.reactivex.flowables;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableAutoConnect;
import io.reactivex.internal.operators.flowable.FlowablePublishAlt;
import io.reactivex.internal.operators.flowable.FlowablePublishClassic;
import io.reactivex.internal.operators.flowable.FlowableRefCount;
import io.reactivex.internal.util.ConnectConsumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public abstract class ConnectableFlowable<T> extends Flowable<T> {
   private ConnectableFlowable<T> onRefCount() {
      if (this instanceof FlowablePublishClassic) {
         FlowablePublishClassic var1 = (FlowablePublishClassic)this;
         return RxJavaPlugins.onAssembly(new FlowablePublishAlt<>(var1.publishSource(), var1.publishBufferSize()));
      } else {
         return this;
      }
   }

   public Flowable<T> autoConnect() {
      return this.autoConnect(1);
   }

   public Flowable<T> autoConnect(int var1) {
      return this.autoConnect(var1, Functions.emptyConsumer());
   }

   public Flowable<T> autoConnect(int var1, Consumer<? super Disposable> var2) {
      if (var1 <= 0) {
         this.connect(var2);
         return RxJavaPlugins.onAssembly(this);
      } else {
         return RxJavaPlugins.onAssembly(new FlowableAutoConnect<>(this, var1, var2));
      }
   }

   public final Disposable connect() {
      ConnectConsumer var1 = new ConnectConsumer();
      this.connect(var1);
      return var1.disposable;
   }

   public abstract void connect(Consumer<? super Disposable> var1);

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public Flowable<T> refCount() {
      return RxJavaPlugins.onAssembly(new FlowableRefCount<>(this.onRefCount()));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> refCount(int var1) {
      return this.refCount(var1, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> refCount(int var1, long var2, TimeUnit var4) {
      return this.refCount(var1, var2, var4, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> refCount(int var1, long var2, TimeUnit var4, Scheduler var5) {
      ObjectHelper.verifyPositive(var1, "subscriberCount");
      ObjectHelper.requireNonNull(var4, "unit is null");
      ObjectHelper.requireNonNull(var5, "scheduler is null");
      return RxJavaPlugins.onAssembly(new FlowableRefCount<>(this.onRefCount(), var1, var2, var4, var5));
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Flowable<T> refCount(long var1, TimeUnit var3) {
      return this.refCount(1, var1, var3, Schedulers.computation());
   }

   @BackpressureSupport(BackpressureKind.PASS_THROUGH)
   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Flowable<T> refCount(long var1, TimeUnit var3, Scheduler var4) {
      return this.refCount(1, var1, var3, var4);
   }
}
