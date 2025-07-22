package io.reactivex;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.operators.flowable.FlowableConcatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableFlatMapPublisher;
import io.reactivex.internal.operators.maybe.MaybeAmb;
import io.reactivex.internal.operators.maybe.MaybeCache;
import io.reactivex.internal.operators.maybe.MaybeCallbackObserver;
import io.reactivex.internal.operators.maybe.MaybeConcatArray;
import io.reactivex.internal.operators.maybe.MaybeConcatArrayDelayError;
import io.reactivex.internal.operators.maybe.MaybeConcatIterable;
import io.reactivex.internal.operators.maybe.MaybeContains;
import io.reactivex.internal.operators.maybe.MaybeCount;
import io.reactivex.internal.operators.maybe.MaybeCreate;
import io.reactivex.internal.operators.maybe.MaybeDefer;
import io.reactivex.internal.operators.maybe.MaybeDelay;
import io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher;
import io.reactivex.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher;
import io.reactivex.internal.operators.maybe.MaybeDetach;
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess;
import io.reactivex.internal.operators.maybe.MaybeDoFinally;
import io.reactivex.internal.operators.maybe.MaybeDoOnEvent;
import io.reactivex.internal.operators.maybe.MaybeDoOnTerminate;
import io.reactivex.internal.operators.maybe.MaybeEmpty;
import io.reactivex.internal.operators.maybe.MaybeEqualSingle;
import io.reactivex.internal.operators.maybe.MaybeError;
import io.reactivex.internal.operators.maybe.MaybeErrorCallable;
import io.reactivex.internal.operators.maybe.MaybeFilter;
import io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector;
import io.reactivex.internal.operators.maybe.MaybeFlatMapCompletable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapIterableFlowable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapIterableObservable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapNotification;
import io.reactivex.internal.operators.maybe.MaybeFlatMapSingle;
import io.reactivex.internal.operators.maybe.MaybeFlatMapSingleElement;
import io.reactivex.internal.operators.maybe.MaybeFlatten;
import io.reactivex.internal.operators.maybe.MaybeFromAction;
import io.reactivex.internal.operators.maybe.MaybeFromCallable;
import io.reactivex.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.internal.operators.maybe.MaybeFromFuture;
import io.reactivex.internal.operators.maybe.MaybeFromRunnable;
import io.reactivex.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.internal.operators.maybe.MaybeHide;
import io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.internal.operators.maybe.MaybeIsEmptySingle;
import io.reactivex.internal.operators.maybe.MaybeJust;
import io.reactivex.internal.operators.maybe.MaybeLift;
import io.reactivex.internal.operators.maybe.MaybeMap;
import io.reactivex.internal.operators.maybe.MaybeMaterialize;
import io.reactivex.internal.operators.maybe.MaybeMergeArray;
import io.reactivex.internal.operators.maybe.MaybeNever;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.internal.operators.maybe.MaybeOnErrorComplete;
import io.reactivex.internal.operators.maybe.MaybeOnErrorNext;
import io.reactivex.internal.operators.maybe.MaybeOnErrorReturn;
import io.reactivex.internal.operators.maybe.MaybePeek;
import io.reactivex.internal.operators.maybe.MaybeSubscribeOn;
import io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty;
import io.reactivex.internal.operators.maybe.MaybeSwitchIfEmptySingle;
import io.reactivex.internal.operators.maybe.MaybeTakeUntilMaybe;
import io.reactivex.internal.operators.maybe.MaybeTakeUntilPublisher;
import io.reactivex.internal.operators.maybe.MaybeTimeoutMaybe;
import io.reactivex.internal.operators.maybe.MaybeTimeoutPublisher;
import io.reactivex.internal.operators.maybe.MaybeTimer;
import io.reactivex.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.internal.operators.maybe.MaybeToObservable;
import io.reactivex.internal.operators.maybe.MaybeToPublisher;
import io.reactivex.internal.operators.maybe.MaybeToSingle;
import io.reactivex.internal.operators.maybe.MaybeUnsafeCreate;
import io.reactivex.internal.operators.maybe.MaybeUnsubscribeOn;
import io.reactivex.internal.operators.maybe.MaybeUsing;
import io.reactivex.internal.operators.maybe.MaybeZipArray;
import io.reactivex.internal.operators.maybe.MaybeZipIterable;
import io.reactivex.internal.operators.mixed.MaybeFlatMapObservable;
import io.reactivex.internal.operators.mixed.MaybeFlatMapPublisher;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Maybe<T> implements MaybeSource<T> {
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> amb(Iterable<? extends MaybeSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new MaybeAmb<>(null, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> ambArray(MaybeSource<? extends T>... var0) {
      if (var0.length == 0) {
         return empty();
      } else {
         return var0.length == 1 ? wrap(var0[0]) : RxJavaPlugins.onAssembly(new MaybeAmb<>(var0, null));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return concatArray(var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return concatArray(var0, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(
      MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2, MaybeSource<? extends T> var3
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return concatArray(var0, var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(Iterable<? extends MaybeSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new MaybeConcatIterable<>(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(Publisher<? extends MaybeSource<? extends T>> var0) {
      return concat(var0, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concat(Publisher<? extends MaybeSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new FlowableConcatMapPublisher<>(var0, MaybeToPublisher.instance(), var1, ErrorMode.IMMEDIATE));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatArray(MaybeSource<? extends T>... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if (var0.length == 0) {
         return Flowable.empty();
      } else {
         return var0.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable<>(var0[0])) : RxJavaPlugins.onAssembly(new MaybeConcatArray<>(var0));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatArrayDelayError(MaybeSource<? extends T>... var0) {
      if (var0.length == 0) {
         return Flowable.empty();
      } else {
         return var0.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable<>(var0[0])) : RxJavaPlugins.onAssembly(new MaybeConcatArrayDelayError<>(var0));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatArrayEager(MaybeSource<? extends T>... var0) {
      return Flowable.fromArray(var0).concatMapEager(MaybeToPublisher.instance());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatDelayError(Iterable<? extends MaybeSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return Flowable.<MaybeSource<? extends T>>fromIterable(var0).concatMapDelayError(MaybeToPublisher.instance());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatDelayError(Publisher<? extends MaybeSource<? extends T>> var0) {
      return Flowable.<MaybeSource<? extends T>>fromPublisher(var0).concatMapDelayError(MaybeToPublisher.instance());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatEager(Iterable<? extends MaybeSource<? extends T>> var0) {
      return Flowable.<MaybeSource<? extends T>>fromIterable(var0).concatMapEager(MaybeToPublisher.instance());
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> concatEager(Publisher<? extends MaybeSource<? extends T>> var0) {
      return Flowable.<MaybeSource<? extends T>>fromPublisher(var0).concatMapEager(MaybeToPublisher.instance());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> create(MaybeOnSubscribe<T> var0) {
      ObjectHelper.requireNonNull(var0, "onSubscribe is null");
      return RxJavaPlugins.onAssembly(new MaybeCreate<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> defer(Callable<? extends MaybeSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "maybeSupplier is null");
      return RxJavaPlugins.onAssembly(new MaybeDefer<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> empty() {
      return RxJavaPlugins.onAssembly(MaybeEmpty.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "exception is null");
      return RxJavaPlugins.onAssembly(new MaybeError<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new MaybeErrorCallable<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromAction(Action var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new MaybeFromAction<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromCallable(Callable<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "callable is null");
      return RxJavaPlugins.onAssembly(new MaybeFromCallable<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromCompletable(CompletableSource var0) {
      ObjectHelper.requireNonNull(var0, "completableSource is null");
      return RxJavaPlugins.onAssembly(new MaybeFromCompletable<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromFuture(Future<? extends T> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return RxJavaPlugins.onAssembly(new MaybeFromFuture<>(var0, 0L, null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromFuture(Future<? extends T> var0, long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var0, "future is null");
      ObjectHelper.requireNonNull(var3, "unit is null");
      return RxJavaPlugins.onAssembly(new MaybeFromFuture<>(var0, var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromRunnable(Runnable var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new MaybeFromRunnable<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> fromSingle(SingleSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "singleSource is null");
      return RxJavaPlugins.onAssembly(new MaybeFromSingle<>(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> just(T var0) {
      ObjectHelper.requireNonNull(var0, "item is null");
      return RxJavaPlugins.onAssembly(new MaybeJust<>((T)var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return mergeArray(var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return mergeArray(var0, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(
      MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2, MaybeSource<? extends T> var3
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return mergeArray(var0, var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(Iterable<? extends MaybeSource<? extends T>> var0) {
      return merge(Flowable.fromIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(Publisher<? extends MaybeSource<? extends T>> var0) {
      return merge(var0, Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> merge(Publisher<? extends MaybeSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "source is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher<>(var0, MaybeToPublisher.instance(), false, var1, 1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> merge(MaybeSource<? extends MaybeSource<? extends T>> var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatten<>(var0, Functions.identity()));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeArray(MaybeSource<? extends T>... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if (var0.length == 0) {
         return Flowable.empty();
      } else {
         return var0.length == 1 ? RxJavaPlugins.onAssembly(new MaybeToFlowable<>(var0[0])) : RxJavaPlugins.onAssembly(new MaybeMergeArray<>(var0));
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeArrayDelayError(MaybeSource<? extends T>... var0) {
      return var0.length == 0 ? Flowable.empty() : Flowable.fromArray(var0).flatMap(MaybeToPublisher.instance(), true, var0.length);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return mergeArrayDelayError(var0, var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return mergeArrayDelayError(var0, var1, var2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(
      MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, MaybeSource<? extends T> var2, MaybeSource<? extends T> var3
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return mergeArrayDelayError(var0, var1, var2, var3);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(Iterable<? extends MaybeSource<? extends T>> var0) {
      return Flowable.<MaybeSource<? extends T>>fromIterable(var0).flatMap(MaybeToPublisher.instance(), true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(Publisher<? extends MaybeSource<? extends T>> var0) {
      return mergeDelayError(var0, Integer.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Flowable<T> mergeDelayError(Publisher<? extends MaybeSource<? extends T>> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "source is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher<>(var0, MaybeToPublisher.instance(), true, var1, 1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> never() {
      return RxJavaPlugins.onAssembly(MaybeNever.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Single<Boolean> sequenceEqual(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1) {
      return sequenceEqual(var0, var1, ObjectHelper.equalsPredicate());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Single<Boolean> sequenceEqual(MaybeSource<? extends T> var0, MaybeSource<? extends T> var1, BiPredicate<? super T, ? super T> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "isEqual is null");
      return RxJavaPlugins.onAssembly(new MaybeEqualSingle(var0, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Maybe<Long> timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Maybe<Long> timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new MaybeTimer(Math.max(0L, var0), var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> unsafeCreate(MaybeSource<T> var0) {
      if (!(var0 instanceof Maybe)) {
         ObjectHelper.requireNonNull(var0, "onSubscribe is null");
         return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate<>(var0));
      } else {
         throw new IllegalArgumentException("unsafeCreate(Maybe) should be upgraded");
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T, D> Maybe<T> using(Callable<? extends D> var0, Function<? super D, ? extends MaybeSource<? extends T>> var1, Consumer<? super D> var2) {
      return using(var0, var1, var2, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T, D> Maybe<T> using(
      Callable<? extends D> var0, Function<? super D, ? extends MaybeSource<? extends T>> var1, Consumer<? super D> var2, boolean var3
   ) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "sourceSupplier is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new MaybeUsing<>(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Maybe<T> wrap(MaybeSource<T> var0) {
      if (var0 instanceof Maybe) {
         return RxJavaPlugins.onAssembly((Maybe<T>)var0);
      } else {
         ObjectHelper.requireNonNull(var0, "onSubscribe is null");
         return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate<>(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      MaybeSource<? extends T5> var4,
      MaybeSource<? extends T6> var5,
      MaybeSource<? extends T7> var6,
      MaybeSource<? extends T8> var7,
      MaybeSource<? extends T9> var8,
      Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> var9
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      ObjectHelper.requireNonNull(var8, "source9 is null");
      return zipArray(Functions.toFunction(var9), var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      MaybeSource<? extends T5> var4,
      MaybeSource<? extends T6> var5,
      MaybeSource<? extends T7> var6,
      MaybeSource<? extends T8> var7,
      Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> var8
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      ObjectHelper.requireNonNull(var7, "source8 is null");
      return zipArray(Functions.toFunction(var8), var0, var1, var2, var3, var4, var5, var6, var7);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, T5, T6, T7, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      MaybeSource<? extends T5> var4,
      MaybeSource<? extends T6> var5,
      MaybeSource<? extends T7> var6,
      Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> var7
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      ObjectHelper.requireNonNull(var6, "source7 is null");
      return zipArray(Functions.toFunction(var7), var0, var1, var2, var3, var4, var5, var6);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, T5, T6, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      MaybeSource<? extends T5> var4,
      MaybeSource<? extends T6> var5,
      Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> var6
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      ObjectHelper.requireNonNull(var5, "source6 is null");
      return zipArray(Functions.toFunction(var6), var0, var1, var2, var3, var4, var5);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, T5, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      MaybeSource<? extends T5> var4,
      Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> var5
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      ObjectHelper.requireNonNull(var4, "source5 is null");
      return zipArray(Functions.toFunction(var5), var0, var1, var2, var3, var4);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, T4, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      MaybeSource<? extends T4> var3,
      Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> var4
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      ObjectHelper.requireNonNull(var3, "source4 is null");
      return zipArray(Functions.toFunction(var4), var0, var1, var2, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, T3, R> Maybe<R> zip(
      MaybeSource<? extends T1> var0,
      MaybeSource<? extends T2> var1,
      MaybeSource<? extends T3> var2,
      Function3<? super T1, ? super T2, ? super T3, ? extends R> var3
   ) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      ObjectHelper.requireNonNull(var2, "source3 is null");
      return zipArray(Functions.toFunction(var3), var0, var1, var2);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T1, T2, R> Maybe<R> zip(MaybeSource<? extends T1> var0, MaybeSource<? extends T2> var1, BiFunction<? super T1, ? super T2, ? extends R> var2) {
      ObjectHelper.requireNonNull(var0, "source1 is null");
      ObjectHelper.requireNonNull(var1, "source2 is null");
      return zipArray(Functions.toFunction(var2), var0, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T, R> Maybe<R> zip(Iterable<? extends MaybeSource<? extends T>> var0, Function<? super Object[], ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "zipper is null");
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new MaybeZipIterable<>(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T, R> Maybe<R> zipArray(Function<? super Object[], ? extends R> var0, MaybeSource<? extends T>... var1) {
      ObjectHelper.requireNonNull(var1, "sources is null");
      if (var1.length == 0) {
         return empty();
      } else {
         ObjectHelper.requireNonNull(var0, "zipper is null");
         return RxJavaPlugins.onAssembly(new MaybeZipArray<>(var1, var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> ambWith(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> R as(MaybeConverter<T, ? extends R> var1) {
      return (R)ObjectHelper.requireNonNull(var1, "converter is null").apply(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingGet() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe(var1);
      return (T)var1.blockingGet();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final T blockingGet(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultValue is null");
      BlockingMultiObserver var2 = new BlockingMultiObserver();
      this.subscribe(var2);
      return (T)var2.blockingGet(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> cache() {
      return RxJavaPlugins.onAssembly(new MaybeCache<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<U> cast(Class<? extends U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.map(Functions.castFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> compose(MaybeTransformer<? super T, ? extends R> var1) {
      return wrap(ObjectHelper.requireNonNull(var1, "transformer is null").apply(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> concatMap(Function<? super T, ? extends MaybeSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatten<>(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> concatWith(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concat(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> contains(Object var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return RxJavaPlugins.onAssembly(new MaybeContains<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Long> count() {
      return RxJavaPlugins.onAssembly(new MaybeCount<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> defaultIfEmpty(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultItem is null");
      return this.switchIfEmpty(just((T)var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Maybe<T> delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> delay(long var1, TimeUnit var3, Scheduler var4) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new MaybeDelay<>(this, Math.max(0L, var1), var3, var4));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U, V> Maybe<T> delay(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "delayIndicator is null");
      return RxJavaPlugins.onAssembly(new MaybeDelayOtherPublisher<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Maybe<T> delaySubscription(long var1, TimeUnit var3) {
      return this.delaySubscription(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> delaySubscription(long var1, TimeUnit var3, Scheduler var4) {
      return this.delaySubscription(Flowable.timer(var1, var3, var4));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> delaySubscription(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "subscriptionIndicator is null");
      return RxJavaPlugins.onAssembly(new MaybeDelaySubscriptionOtherPublisher<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doAfterSuccess(Consumer<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "onAfterSuccess is null");
      return RxJavaPlugins.onAssembly(new MaybeDoAfterSuccess<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doAfterTerminate(Action var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            ObjectHelper.requireNonNull(var1, "onAfterTerminate is null"),
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new MaybeDoFinally<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnComplete(Action var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            ObjectHelper.requireNonNull(var1, "onComplete is null"),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnDispose(Action var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            ObjectHelper.requireNonNull(var1, "onDispose is null")
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnError(Consumer<? super Throwable> var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            ObjectHelper.requireNonNull(var1, "onError is null"),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnEvent(BiConsumer<? super T, ? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onEvent is null");
      return RxJavaPlugins.onAssembly(new MaybeDoOnEvent<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnSubscribe(Consumer<? super Disposable> var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            ObjectHelper.requireNonNull(var1, "onSubscribe is null"),
            Functions.emptyConsumer(),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnSuccess(Consumer<? super T> var1) {
      return RxJavaPlugins.onAssembly(
         new MaybePeek<>(
            this,
            Functions.emptyConsumer(),
            ObjectHelper.requireNonNull(var1, "onSuccess is null"),
            Functions.emptyConsumer(),
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION,
            Functions.EMPTY_ACTION
         )
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> doOnTerminate(Action var1) {
      ObjectHelper.requireNonNull(var1, "onTerminate is null");
      return RxJavaPlugins.onAssembly(new MaybeDoOnTerminate<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> filter(Predicate<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new MaybeFilter<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatten<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U, R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends U>> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      ObjectHelper.requireNonNull(var2, "resultSelector is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapBiSelector<>(this, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> flatMap(
      Function<? super T, ? extends MaybeSource<? extends R>> var1,
      Function<? super Throwable, ? extends MaybeSource<? extends R>> var2,
      Callable<? extends MaybeSource<? extends R>> var3
   ) {
      ObjectHelper.requireNonNull(var1, "onSuccessMapper is null");
      ObjectHelper.requireNonNull(var2, "onErrorMapper is null");
      ObjectHelper.requireNonNull(var3, "onCompleteSupplier is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapNotification<>(this, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapCompletable<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Observable<R> flatMapObservable(Function<? super T, ? extends ObservableSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapObservable<>(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Flowable<R> flatMapPublisher(Function<? super T, ? extends Publisher<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapPublisher<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Single<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapSingle<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> flatMapSingleElement(Function<? super T, ? extends SingleSource<? extends R>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapSingleElement<>(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Flowable<U> flattenAsFlowable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableFlowable<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Observable<U> flattenAsObservable(Function<? super T, ? extends Iterable<? extends U>> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableObservable<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> hide() {
      return RxJavaPlugins.onAssembly(new MaybeHide<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable ignoreElement() {
      return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Boolean> isEmpty() {
      return RxJavaPlugins.onAssembly(new MaybeIsEmptySingle<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> lift(MaybeOperator<? extends R, ? super T> var1) {
      ObjectHelper.requireNonNull(var1, "lift is null");
      return RxJavaPlugins.onAssembly(new MaybeLift<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> Maybe<R> map(Function<? super T, ? extends R> var1) {
      ObjectHelper.requireNonNull(var1, "mapper is null");
      return RxJavaPlugins.onAssembly(new MaybeMap<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<Notification<T>> materialize() {
      return RxJavaPlugins.onAssembly(new MaybeMaterialize<>(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> mergeWith(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return merge(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> observeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new MaybeObserveOn<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<U> ofType(Class<U> var1) {
      ObjectHelper.requireNonNull(var1, "clazz is null");
      return this.filter(Functions.isInstanceOf(var1)).cast(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorComplete() {
      return this.onErrorComplete(Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorComplete(Predicate<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new MaybeOnErrorComplete<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorResumeNext(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return this.onErrorResumeNext(Functions.justFunction(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorResumeNext(Function<? super Throwable, ? extends MaybeSource<? extends T>> var1) {
      ObjectHelper.requireNonNull(var1, "resumeFunction is null");
      return RxJavaPlugins.onAssembly(new MaybeOnErrorNext<>(this, var1, true));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorReturn(Function<? super Throwable, ? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "valueSupplier is null");
      return RxJavaPlugins.onAssembly(new MaybeOnErrorReturn<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onErrorReturnItem(T var1) {
      ObjectHelper.requireNonNull(var1, "item is null");
      return this.onErrorReturn(Functions.justFunction((T)var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onExceptionResumeNext(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new MaybeOnErrorNext<>(this, Functions.justFunction(var1), false));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new MaybeDetach<>(this));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat() {
      return this.repeat(Long.MAX_VALUE);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeat(long var1) {
      return this.toFlowable().repeat(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatUntil(BooleanSupplier var1) {
      return this.toFlowable().repeatUntil(var1);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> var1) {
      return this.toFlowable().repeatWhen(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retry() {
      return this.retry(Long.MAX_VALUE, Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retry(long var1) {
      return this.retry(var1, Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retry(long var1, Predicate<? super Throwable> var3) {
      return this.toFlowable().retry(var1, var3).singleElement();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      return this.toFlowable().retry(var1).singleElement();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retry(Predicate<? super Throwable> var1) {
      return this.retry(Long.MAX_VALUE, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retryUntil(BooleanSupplier var1) {
      ObjectHelper.requireNonNull(var1, "stop is null");
      return this.retry(Long.MAX_VALUE, Functions.predicateReverseFor(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> var1) {
      return this.toFlowable().retryWhen(var1).singleElement();
   }

   @SchedulerSupport("none")
   public final Disposable subscribe() {
      return this.subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1) {
      return this.subscribe(var1, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2) {
      return this.subscribe(var1, var2, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Consumer<? super T> var1, Consumer<? super Throwable> var2, Action var3) {
      ObjectHelper.requireNonNull(var1, "onSuccess is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      return this.subscribeWith(new MaybeCallbackObserver(var1, var2, var3));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @SchedulerSupport("none")
   @Override
   public final void subscribe(MaybeObserver<? super T> var1) {
      ObjectHelper.requireNonNull(var1, "observer is null");
      var1 = RxJavaPlugins.onSubscribe(this, var1);
      ObjectHelper.requireNonNull(
         var1,
         "The RxJavaPlugins.onSubscribe hook returned a null MaybeObserver. Please check the handler provided to RxJavaPlugins.setOnMaybeSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins"
      );

      try {
         try {
            this.subscribeActual(var1);
            return;
         } catch (NullPointerException var5) {
            var8 = var5;
         }
      } catch (Throwable var6) {
         Exceptions.throwIfFatal(var6);
         NullPointerException var2 = new NullPointerException("subscribeActual failed");
         var2.initCause(var6);
         throw var2;
      }

      throw var8;
   }

   protected abstract void subscribeActual(MaybeObserver<? super T> var1);

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> subscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new MaybeSubscribeOn<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends MaybeObserver<? super T>> E subscribeWith(E var1) {
      this.subscribe(var1);
      return (E)var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Maybe<T> switchIfEmpty(MaybeSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmpty<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> switchIfEmpty(SingleSource<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmptySingle<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> takeUntil(MaybeSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new MaybeTakeUntilMaybe<>(this, var1));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> takeUntil(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new MaybeTakeUntilPublisher<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test() {
      TestObserver var1 = new TestObserver();
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<T> test(boolean var1) {
      TestObserver var2 = new TestObserver();
      if (var1) {
         var2.cancel();
      }

      this.subscribe(var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Maybe<T> timeout(long var1, TimeUnit var3) {
      return this.timeout(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Maybe<T> timeout(long var1, TimeUnit var3, MaybeSource<? extends T> var4) {
      ObjectHelper.requireNonNull(var4, "fallback is null");
      return this.timeout(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout(timer(var1, var3, var4));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> timeout(long var1, TimeUnit var3, Scheduler var4, MaybeSource<? extends T> var5) {
      ObjectHelper.requireNonNull(var5, "fallback is null");
      return this.timeout(timer(var1, var3, var4), var5);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> timeout(MaybeSource<U> var1) {
      ObjectHelper.requireNonNull(var1, "timeoutIndicator is null");
      return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe<>(this, var1, null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> timeout(MaybeSource<U> var1, MaybeSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var1, "timeoutIndicator is null");
      ObjectHelper.requireNonNull(var2, "fallback is null");
      return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe<>(this, var1, var2));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> timeout(Publisher<U> var1) {
      ObjectHelper.requireNonNull(var1, "timeoutIndicator is null");
      return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher<>(this, var1, null));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> Maybe<T> timeout(Publisher<U> var1, MaybeSource<? extends T> var2) {
      ObjectHelper.requireNonNull(var1, "timeoutIndicator is null");
      ObjectHelper.requireNonNull(var2, "fallback is null");
      return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher<>(this, var1, var2));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> R to(Function<? super Maybe<T>, R> var1) {
      try {
         return (R)ObjectHelper.requireNonNull(var1, "convert is null").apply(this);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final Flowable<T> toFlowable() {
      return this instanceof FuseToFlowable ? ((FuseToFlowable)this).fuseToFlowable() : RxJavaPlugins.onAssembly(new MaybeToFlowable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Observable<T> toObservable() {
      return this instanceof FuseToObservable ? ((FuseToObservable)this).fuseToObservable() : RxJavaPlugins.onAssembly(new MaybeToObservable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> toSingle() {
      return RxJavaPlugins.onAssembly(new MaybeToSingle<>(this, null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Single<T> toSingle(T var1) {
      ObjectHelper.requireNonNull(var1, "defaultValue is null");
      return RxJavaPlugins.onAssembly(new MaybeToSingle<>(this, (T)var1));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Maybe<T> unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new MaybeUnsubscribeOn<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U, R> Maybe<R> zipWith(MaybeSource<? extends U> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return zip(this, var1, var2);
   }
}
