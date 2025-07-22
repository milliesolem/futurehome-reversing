package io.reactivex.plugins;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.parallel.ParallelFlowable;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import org.reactivestreams.Subscriber;

public final class RxJavaPlugins {
   static volatile Consumer<? super Throwable> errorHandler;
   static volatile boolean failNonBlockingScheduler;
   static volatile boolean lockdown;
   static volatile BooleanSupplier onBeforeBlocking;
   static volatile Function<? super Completable, ? extends Completable> onCompletableAssembly;
   static volatile BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> onCompletableSubscribe;
   static volatile Function<? super Scheduler, ? extends Scheduler> onComputationHandler;
   static volatile Function<? super ConnectableFlowable, ? extends ConnectableFlowable> onConnectableFlowableAssembly;
   static volatile Function<? super ConnectableObservable, ? extends ConnectableObservable> onConnectableObservableAssembly;
   static volatile Function<? super Flowable, ? extends Flowable> onFlowableAssembly;
   static volatile BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> onFlowableSubscribe;
   static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitComputationHandler;
   static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitIoHandler;
   static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitNewThreadHandler;
   static volatile Function<? super Callable<Scheduler>, ? extends Scheduler> onInitSingleHandler;
   static volatile Function<? super Scheduler, ? extends Scheduler> onIoHandler;
   static volatile Function<? super Maybe, ? extends Maybe> onMaybeAssembly;
   static volatile BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> onMaybeSubscribe;
   static volatile Function<? super Scheduler, ? extends Scheduler> onNewThreadHandler;
   static volatile Function<? super Observable, ? extends Observable> onObservableAssembly;
   static volatile BiFunction<? super Observable, ? super Observer, ? extends Observer> onObservableSubscribe;
   static volatile Function<? super ParallelFlowable, ? extends ParallelFlowable> onParallelAssembly;
   static volatile Function<? super Runnable, ? extends Runnable> onScheduleHandler;
   static volatile Function<? super Single, ? extends Single> onSingleAssembly;
   static volatile Function<? super Scheduler, ? extends Scheduler> onSingleHandler;
   static volatile BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> onSingleSubscribe;

   private RxJavaPlugins() {
      throw new IllegalStateException("No instances!");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T, U, R> R apply(BiFunction<T, U, R> var0, T var1, U var2) {
      try {
         return (R)var0.apply(var1, var2);
      } catch (Throwable var4) {
         throw ExceptionHelper.wrapOrThrow(var4);
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static <T, R> R apply(Function<T, R> var0, T var1) {
      try {
         return (R)var0.apply(var1);
      } catch (Throwable var3) {
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   static Scheduler applyRequireNonNull(Function<? super Callable<Scheduler>, ? extends Scheduler> var0, Callable<Scheduler> var1) {
      return ObjectHelper.requireNonNull(apply(var0, var1), "Scheduler Callable result can't be null");
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static Scheduler callRequireNonNull(Callable<Scheduler> var0) {
      try {
         return ObjectHelper.requireNonNull((Scheduler)var0.call(), "Scheduler Callable result can't be null");
      } catch (Throwable var2) {
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   public static Scheduler createComputationScheduler(ThreadFactory var0) {
      return new ComputationScheduler(ObjectHelper.requireNonNull(var0, "threadFactory is null"));
   }

   public static Scheduler createIoScheduler(ThreadFactory var0) {
      return new IoScheduler(ObjectHelper.requireNonNull(var0, "threadFactory is null"));
   }

   public static Scheduler createNewThreadScheduler(ThreadFactory var0) {
      return new NewThreadScheduler(ObjectHelper.requireNonNull(var0, "threadFactory is null"));
   }

   public static Scheduler createSingleScheduler(ThreadFactory var0) {
      return new SingleScheduler(ObjectHelper.requireNonNull(var0, "threadFactory is null"));
   }

   public static Function<? super Scheduler, ? extends Scheduler> getComputationSchedulerHandler() {
      return onComputationHandler;
   }

   public static Consumer<? super Throwable> getErrorHandler() {
      return errorHandler;
   }

   public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitComputationSchedulerHandler() {
      return onInitComputationHandler;
   }

   public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitIoSchedulerHandler() {
      return onInitIoHandler;
   }

   public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitNewThreadSchedulerHandler() {
      return onInitNewThreadHandler;
   }

   public static Function<? super Callable<Scheduler>, ? extends Scheduler> getInitSingleSchedulerHandler() {
      return onInitSingleHandler;
   }

   public static Function<? super Scheduler, ? extends Scheduler> getIoSchedulerHandler() {
      return onIoHandler;
   }

   public static Function<? super Scheduler, ? extends Scheduler> getNewThreadSchedulerHandler() {
      return onNewThreadHandler;
   }

   public static BooleanSupplier getOnBeforeBlocking() {
      return onBeforeBlocking;
   }

   public static Function<? super Completable, ? extends Completable> getOnCompletableAssembly() {
      return onCompletableAssembly;
   }

   public static BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> getOnCompletableSubscribe() {
      return onCompletableSubscribe;
   }

   public static Function<? super ConnectableFlowable, ? extends ConnectableFlowable> getOnConnectableFlowableAssembly() {
      return onConnectableFlowableAssembly;
   }

   public static Function<? super ConnectableObservable, ? extends ConnectableObservable> getOnConnectableObservableAssembly() {
      return onConnectableObservableAssembly;
   }

   public static Function<? super Flowable, ? extends Flowable> getOnFlowableAssembly() {
      return onFlowableAssembly;
   }

   public static BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> getOnFlowableSubscribe() {
      return onFlowableSubscribe;
   }

   public static Function<? super Maybe, ? extends Maybe> getOnMaybeAssembly() {
      return onMaybeAssembly;
   }

   public static BiFunction<? super Maybe, ? super MaybeObserver, ? extends MaybeObserver> getOnMaybeSubscribe() {
      return onMaybeSubscribe;
   }

   public static Function<? super Observable, ? extends Observable> getOnObservableAssembly() {
      return onObservableAssembly;
   }

   public static BiFunction<? super Observable, ? super Observer, ? extends Observer> getOnObservableSubscribe() {
      return onObservableSubscribe;
   }

   public static Function<? super ParallelFlowable, ? extends ParallelFlowable> getOnParallelAssembly() {
      return onParallelAssembly;
   }

   public static Function<? super Single, ? extends Single> getOnSingleAssembly() {
      return onSingleAssembly;
   }

   public static BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> getOnSingleSubscribe() {
      return onSingleSubscribe;
   }

   public static Function<? super Runnable, ? extends Runnable> getScheduleHandler() {
      return onScheduleHandler;
   }

   public static Function<? super Scheduler, ? extends Scheduler> getSingleSchedulerHandler() {
      return onSingleHandler;
   }

   public static Scheduler initComputationScheduler(Callable<Scheduler> var0) {
      ObjectHelper.requireNonNull(var0, "Scheduler Callable can't be null");
      Function var1 = onInitComputationHandler;
      return var1 == null ? callRequireNonNull(var0) : applyRequireNonNull(var1, var0);
   }

   public static Scheduler initIoScheduler(Callable<Scheduler> var0) {
      ObjectHelper.requireNonNull(var0, "Scheduler Callable can't be null");
      Function var1 = onInitIoHandler;
      return var1 == null ? callRequireNonNull(var0) : applyRequireNonNull(var1, var0);
   }

   public static Scheduler initNewThreadScheduler(Callable<Scheduler> var0) {
      ObjectHelper.requireNonNull(var0, "Scheduler Callable can't be null");
      Function var1 = onInitNewThreadHandler;
      return var1 == null ? callRequireNonNull(var0) : applyRequireNonNull(var1, var0);
   }

   public static Scheduler initSingleScheduler(Callable<Scheduler> var0) {
      ObjectHelper.requireNonNull(var0, "Scheduler Callable can't be null");
      Function var1 = onInitSingleHandler;
      return var1 == null ? callRequireNonNull(var0) : applyRequireNonNull(var1, var0);
   }

   static boolean isBug(Throwable var0) {
      if (var0 instanceof OnErrorNotImplementedException) {
         return true;
      } else if (var0 instanceof MissingBackpressureException) {
         return true;
      } else if (var0 instanceof IllegalStateException) {
         return true;
      } else if (var0 instanceof NullPointerException) {
         return true;
      } else {
         return var0 instanceof IllegalArgumentException ? true : var0 instanceof CompositeException;
      }
   }

   public static boolean isFailOnNonBlockingScheduler() {
      return failNonBlockingScheduler;
   }

   public static boolean isLockdown() {
      return lockdown;
   }

   public static void lockdown() {
      lockdown = true;
   }

   public static Completable onAssembly(Completable var0) {
      Function var2 = onCompletableAssembly;
      Completable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> Flowable<T> onAssembly(Flowable<T> var0) {
      Function var2 = onFlowableAssembly;
      Flowable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> Maybe<T> onAssembly(Maybe<T> var0) {
      Function var2 = onMaybeAssembly;
      Maybe var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> Observable<T> onAssembly(Observable<T> var0) {
      Function var2 = onObservableAssembly;
      Observable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> Single<T> onAssembly(Single<T> var0) {
      Function var2 = onSingleAssembly;
      Single var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> ConnectableFlowable<T> onAssembly(ConnectableFlowable<T> var0) {
      Function var2 = onConnectableFlowableAssembly;
      ConnectableFlowable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> ConnectableObservable<T> onAssembly(ConnectableObservable<T> var0) {
      Function var2 = onConnectableObservableAssembly;
      ConnectableObservable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   public static <T> ParallelFlowable<T> onAssembly(ParallelFlowable<T> var0) {
      Function var2 = onParallelAssembly;
      ParallelFlowable var1 = var0;
      if (var2 != null) {
         var1 = apply(var2, var0);
      }

      return var1;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static boolean onBeforeBlocking() {
      BooleanSupplier var1 = onBeforeBlocking;
      if (var1 != null) {
         try {
            return var1.getAsBoolean();
         } catch (Throwable var3) {
            throw ExceptionHelper.wrapOrThrow(var3);
         }
      } else {
         return false;
      }
   }

   public static Scheduler onComputationScheduler(Scheduler var0) {
      Function var1 = onComputationHandler;
      return var1 == null ? var0 : apply(var1, var0);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void onError(Throwable var0) {
      Consumer var2 = errorHandler;
      Object var1;
      if (var0 == null) {
         var1 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      } else {
         var1 = var0;
         if (!isBug(var0)) {
            var1 = new UndeliverableException(var0);
         }
      }

      if (var2 != null) {
         label31:
         try {
            var2.accept(var1);
            return;
         } catch (Throwable var4) {
            var4.printStackTrace();
            uncaught(var4);
            break label31;
         }
      }

      var1.printStackTrace();
      uncaught((Throwable)var1);
   }

   public static Scheduler onIoScheduler(Scheduler var0) {
      Function var1 = onIoHandler;
      return var1 == null ? var0 : apply(var1, var0);
   }

   public static Scheduler onNewThreadScheduler(Scheduler var0) {
      Function var1 = onNewThreadHandler;
      return var1 == null ? var0 : apply(var1, var0);
   }

   public static Runnable onSchedule(Runnable var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      Function var1 = onScheduleHandler;
      return var1 == null ? var0 : apply(var1, var0);
   }

   public static Scheduler onSingleScheduler(Scheduler var0) {
      Function var1 = onSingleHandler;
      return var1 == null ? var0 : apply(var1, var0);
   }

   public static CompletableObserver onSubscribe(Completable var0, CompletableObserver var1) {
      BiFunction var2 = onCompletableSubscribe;
      return var2 != null ? apply(var2, var0, var1) : var1;
   }

   public static <T> MaybeObserver<? super T> onSubscribe(Maybe<T> var0, MaybeObserver<? super T> var1) {
      BiFunction var2 = onMaybeSubscribe;
      return var2 != null ? apply(var2, var0, var1) : var1;
   }

   public static <T> Observer<? super T> onSubscribe(Observable<T> var0, Observer<? super T> var1) {
      BiFunction var2 = onObservableSubscribe;
      return var2 != null ? apply(var2, var0, var1) : var1;
   }

   public static <T> SingleObserver<? super T> onSubscribe(Single<T> var0, SingleObserver<? super T> var1) {
      BiFunction var2 = onSingleSubscribe;
      return var2 != null ? apply(var2, var0, var1) : var1;
   }

   public static <T> Subscriber<? super T> onSubscribe(Flowable<T> var0, Subscriber<? super T> var1) {
      BiFunction var2 = onFlowableSubscribe;
      return var2 != null ? apply(var2, var0, var1) : var1;
   }

   public static void reset() {
      setErrorHandler(null);
      setScheduleHandler(null);
      setComputationSchedulerHandler(null);
      setInitComputationSchedulerHandler(null);
      setIoSchedulerHandler(null);
      setInitIoSchedulerHandler(null);
      setSingleSchedulerHandler(null);
      setInitSingleSchedulerHandler(null);
      setNewThreadSchedulerHandler(null);
      setInitNewThreadSchedulerHandler(null);
      setOnFlowableAssembly(null);
      setOnFlowableSubscribe(null);
      setOnObservableAssembly(null);
      setOnObservableSubscribe(null);
      setOnSingleAssembly(null);
      setOnSingleSubscribe(null);
      setOnCompletableAssembly(null);
      setOnCompletableSubscribe(null);
      setOnConnectableFlowableAssembly(null);
      setOnConnectableObservableAssembly(null);
      setOnMaybeAssembly(null);
      setOnMaybeSubscribe(null);
      setOnParallelAssembly(null);
      setFailOnNonBlockingScheduler(false);
      setOnBeforeBlocking(null);
   }

   public static void setComputationSchedulerHandler(Function<? super Scheduler, ? extends Scheduler> var0) {
      if (!lockdown) {
         onComputationHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setErrorHandler(Consumer<? super Throwable> var0) {
      if (!lockdown) {
         errorHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setFailOnNonBlockingScheduler(boolean var0) {
      if (!lockdown) {
         failNonBlockingScheduler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setInitComputationSchedulerHandler(Function<? super Callable<Scheduler>, ? extends Scheduler> var0) {
      if (!lockdown) {
         onInitComputationHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setInitIoSchedulerHandler(Function<? super Callable<Scheduler>, ? extends Scheduler> var0) {
      if (!lockdown) {
         onInitIoHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setInitNewThreadSchedulerHandler(Function<? super Callable<Scheduler>, ? extends Scheduler> var0) {
      if (!lockdown) {
         onInitNewThreadHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setInitSingleSchedulerHandler(Function<? super Callable<Scheduler>, ? extends Scheduler> var0) {
      if (!lockdown) {
         onInitSingleHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setIoSchedulerHandler(Function<? super Scheduler, ? extends Scheduler> var0) {
      if (!lockdown) {
         onIoHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setNewThreadSchedulerHandler(Function<? super Scheduler, ? extends Scheduler> var0) {
      if (!lockdown) {
         onNewThreadHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnBeforeBlocking(BooleanSupplier var0) {
      if (!lockdown) {
         onBeforeBlocking = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnCompletableAssembly(Function<? super Completable, ? extends Completable> var0) {
      if (!lockdown) {
         onCompletableAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnCompletableSubscribe(BiFunction<? super Completable, ? super CompletableObserver, ? extends CompletableObserver> var0) {
      if (!lockdown) {
         onCompletableSubscribe = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnConnectableFlowableAssembly(Function<? super ConnectableFlowable, ? extends ConnectableFlowable> var0) {
      if (!lockdown) {
         onConnectableFlowableAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnConnectableObservableAssembly(Function<? super ConnectableObservable, ? extends ConnectableObservable> var0) {
      if (!lockdown) {
         onConnectableObservableAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnFlowableAssembly(Function<? super Flowable, ? extends Flowable> var0) {
      if (!lockdown) {
         onFlowableAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnFlowableSubscribe(BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> var0) {
      if (!lockdown) {
         onFlowableSubscribe = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnMaybeAssembly(Function<? super Maybe, ? extends Maybe> var0) {
      if (!lockdown) {
         onMaybeAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnMaybeSubscribe(BiFunction<? super Maybe, MaybeObserver, ? extends MaybeObserver> var0) {
      if (!lockdown) {
         onMaybeSubscribe = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnObservableAssembly(Function<? super Observable, ? extends Observable> var0) {
      if (!lockdown) {
         onObservableAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnObservableSubscribe(BiFunction<? super Observable, ? super Observer, ? extends Observer> var0) {
      if (!lockdown) {
         onObservableSubscribe = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnParallelAssembly(Function<? super ParallelFlowable, ? extends ParallelFlowable> var0) {
      if (!lockdown) {
         onParallelAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnSingleAssembly(Function<? super Single, ? extends Single> var0) {
      if (!lockdown) {
         onSingleAssembly = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setOnSingleSubscribe(BiFunction<? super Single, ? super SingleObserver, ? extends SingleObserver> var0) {
      if (!lockdown) {
         onSingleSubscribe = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setScheduleHandler(Function<? super Runnable, ? extends Runnable> var0) {
      if (!lockdown) {
         onScheduleHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   public static void setSingleSchedulerHandler(Function<? super Scheduler, ? extends Scheduler> var0) {
      if (!lockdown) {
         onSingleHandler = var0;
      } else {
         throw new IllegalStateException("Plugins can't be changed anymore");
      }
   }

   static void uncaught(Throwable var0) {
      Thread var1 = Thread.currentThread();
      var1.getUncaughtExceptionHandler().uncaughtException(var1, var0);
   }

   static void unlock() {
      lockdown = false;
   }
}
