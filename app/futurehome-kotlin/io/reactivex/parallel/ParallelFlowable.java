package io.reactivex.parallel;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.parallel.ParallelCollect;
import io.reactivex.internal.operators.parallel.ParallelConcatMap;
import io.reactivex.internal.operators.parallel.ParallelDoOnNextTry;
import io.reactivex.internal.operators.parallel.ParallelFilter;
import io.reactivex.internal.operators.parallel.ParallelFilterTry;
import io.reactivex.internal.operators.parallel.ParallelFlatMap;
import io.reactivex.internal.operators.parallel.ParallelFromArray;
import io.reactivex.internal.operators.parallel.ParallelFromPublisher;
import io.reactivex.internal.operators.parallel.ParallelJoin;
import io.reactivex.internal.operators.parallel.ParallelMap;
import io.reactivex.internal.operators.parallel.ParallelMapTry;
import io.reactivex.internal.operators.parallel.ParallelPeek;
import io.reactivex.internal.operators.parallel.ParallelReduce;
import io.reactivex.internal.operators.parallel.ParallelReduceFull;
import io.reactivex.internal.operators.parallel.ParallelRunOn;
import io.reactivex.internal.operators.parallel.ParallelSortedJoin;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.ListAddBiConsumer;
import io.reactivex.internal.util.MergerBiFunction;
import io.reactivex.internal.util.SorterFunction;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class ParallelFlowable<T> {
   @CheckReturnValue
   public static <T> ParallelFlowable<T> from(Publisher<? extends T> var0) {
      return from(var0, Runtime.getRuntime().availableProcessors(), Flowable.bufferSize());
   }

   @CheckReturnValue
   public static <T> ParallelFlowable<T> from(Publisher<? extends T> var0, int var1) {
      return from(var0, var1, Flowable.bufferSize());
   }

   @CheckReturnValue
   public static <T> ParallelFlowable<T> from(Publisher<? extends T> var0, int var1, int var2) {
      ObjectHelper.requireNonNull(var0, "source");
      ObjectHelper.verifyPositive(var1, "parallelism");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelFromPublisher<>(var0, var1, var2));
   }

   @CheckReturnValue
   public static <T> ParallelFlowable<T> fromArray(Publisher<T>... var0) {
      if (var0.length != 0) {
         return RxJavaPlugins.onAssembly(new ParallelFromArray<>(var0));
      } else {
         throw new IllegalArgumentException("Zero publishers not supported");
      }
   }

   @CheckReturnValue
   public final <R> R as(ParallelFlowableConverter<T, R> var1) {
      return (R)ObjectHelper.requireNonNull(var1, "converter is null").apply(this);
   }

   @CheckReturnValue
   public final <C> ParallelFlowable<C> collect(Callable<? extends C> var1, BiConsumer<? super C, ? super T> var2) {
      ObjectHelper.requireNonNull(var1, "collectionSupplier is null");
      ObjectHelper.requireNonNull(var2, "collector is null");
      return RxJavaPlugins.onAssembly(new ParallelCollect<>(this, var1, var2));
   }

   @CheckReturnValue
   public final <U> ParallelFlowable<U> compose(ParallelTransformer<T, U> var1) {
      return RxJavaPlugins.onAssembly(ObjectHelper.requireNonNull(var1, "composer is null").apply(this));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.concatMap(var1, 2);
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelConcatMap<>(this, var1, var2, ErrorMode.IMMEDIATE));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, int var2, boolean var3) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var2, "prefetch");
      ErrorMode var4;
      if (var3) {
         var4 = ErrorMode.END;
      } else {
         var4 = ErrorMode.BOUNDARY;
      }

      return RxJavaPlugins.onAssembly(new ParallelConcatMap<>(this, var1, var2, var4));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.concatMapDelayError(var1, 2, var2);
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doAfterNext(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onAfterNext is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            var1,
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doAfterTerminated(Action var1) {
      ObjectHelper.requireNonNull(var1, "onAfterTerminate is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            var1,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnCancel(Action var1) {
      ObjectHelper.requireNonNull(var1, "onCancel is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            var1
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnComplete(Action var1) {
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            var1,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnError(Consumer<Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onError is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            var1,
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnNext(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            var1,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnNext(Consumer<? super T> var1, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry<>(this, var1, var2));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnNext(Consumer<? super T> var1, ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "onNext is null");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelDoOnNextTry<>(this, var1, var2));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnRequest(LongConsumer var1) {
      ObjectHelper.requireNonNull(var1, "onRequest is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.emptyConsumer(),
            var1,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> doOnSubscribe(Consumer<? super Subscription> var1) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      return RxJavaPlugins.onAssembly(
         new ParallelPeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            var1,
            Functions.EMPTY_LONG_CONSUMER,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   public final ParallelFlowable<T> filter(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate");
      return RxJavaPlugins.onAssembly(new ParallelFilter<>(this, var1));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> filter(Predicate<? super T> var1, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "predicate");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelFilterTry<>(this, var1, var2));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> filter(Predicate<? super T> var1, ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "predicate");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelFilterTry<>(this, var1, var2));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1) {
      return this.flatMap(var1, false, Integer.MAX_VALUE, Flowable.bufferSize());
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2) {
      return this.flatMap(var1, var2, Integer.MAX_VALUE, Flowable.bufferSize());
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3) {
      return this.flatMap(var1, var2, var3, Flowable.bufferSize());
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> var1, boolean var2, int var3, int var4) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.verifyPositive(var3, "maxConcurrency");
      ObjectHelper.verifyPositive(var4, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelFlatMap<>(this, var1, var2, var3, var4));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper");
      return RxJavaPlugins.onAssembly(new ParallelMap<>(this, var1));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> var1, BiFunction<? super Long, ? super Throwable, ParallelFailureHandling> var2) {
      ObjectHelper.requireNonNull(var1, "mapper");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelMapTry<>(this, var1, var2));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> map(Function<? super T, ? extends R> var1, ParallelFailureHandling var2) {
      ObjectHelper.requireNonNull(var1, "mapper");
      ObjectHelper.requireNonNull(var2, "errorHandler is null");
      return RxJavaPlugins.onAssembly(new ParallelMapTry<>(this, var1, var2));
   }

   public abstract int parallelism();

   @CheckReturnValue
   public final Flowable<T> reduce(BiFunction<T, T, T> var1) {
      ObjectHelper.requireNonNull(var1, "reducer");
      return RxJavaPlugins.onAssembly(new ParallelReduceFull<>(this, var1));
   }

   @CheckReturnValue
   public final <R> ParallelFlowable<R> reduce(Callable<R> var1, BiFunction<R, ? super T, R> var2) {
      ObjectHelper.requireNonNull(var1, "initialSupplier");
      ObjectHelper.requireNonNull(var2, "reducer");
      return RxJavaPlugins.onAssembly(new ParallelReduce<>(this, var1, var2));
   }

   @CheckReturnValue
   public final ParallelFlowable<T> runOn(Scheduler var1) {
      return this.runOn(var1, Flowable.bufferSize());
   }

   @CheckReturnValue
   public final ParallelFlowable<T> runOn(Scheduler var1, int var2) {
      ObjectHelper.requireNonNull(var1, "scheduler");
      ObjectHelper.verifyPositive(var2, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelRunOn<>(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sequential() {
      return this.sequential(Flowable.bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sequential(int var1) {
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelJoin<>(this, var1, false));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sequentialDelayError() {
      return this.sequentialDelayError(Flowable.bufferSize());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> sequentialDelayError(int var1) {
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new ParallelJoin<>(this, var1, true));
   }

   @CheckReturnValue
   public final Flowable<T> sorted(Comparator<? super T> var1) {
      return this.sorted(var1, 16);
   }

   @CheckReturnValue
   public final Flowable<T> sorted(Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      ObjectHelper.verifyPositive(var2, "capacityHint");
      return RxJavaPlugins.onAssembly(
         new ParallelSortedJoin<>(
            this.reduce(Functions.createArrayList(var2 / this.parallelism() + 1), ListAddBiConsumer.instance()).map(new SorterFunction<>(var1)), var1
         )
      );
   }

   public abstract void subscribe(Subscriber<? super T>[] var1);

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @CheckReturnValue
   public final <U> U to(Function<? super ParallelFlowable<T>, U> var1) {
      try {
         return (U)ObjectHelper.requireNonNull(var1, "converter is null").apply(this);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   @CheckReturnValue
   public final Flowable<List<T>> toSortedList(Comparator<? super T> var1) {
      return this.toSortedList(var1, 16);
   }

   @CheckReturnValue
   public final Flowable<List<T>> toSortedList(Comparator<? super T> var1, int var2) {
      ObjectHelper.requireNonNull(var1, "comparator is null");
      ObjectHelper.verifyPositive(var2, "capacityHint");
      return RxJavaPlugins.onAssembly(
         this.reduce(Functions.createArrayList(var2 / this.parallelism() + 1), ListAddBiConsumer.instance())
            .map(new SorterFunction<>(var1))
            .reduce(new MergerBiFunction<>(var1))
      );
   }

   protected final boolean validate(Subscriber<?>[] var1) {
      int var2 = this.parallelism();
      if (var1.length == var2) {
         return true;
      } else {
         StringBuilder var4 = new StringBuilder("parallelism = ");
         var4.append(var2);
         var4.append(", subscribers = ");
         var4.append(var1.length);
         IllegalArgumentException var6 = new IllegalArgumentException(var4.toString());
         int var3 = var1.length;

         for (int var5 = 0; var5 < var3; var5++) {
            EmptySubscription.error(var6, var1[var5]);
         }

         return false;
      }
   }
}
