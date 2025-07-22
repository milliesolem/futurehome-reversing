package io.reactivex;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.observers.CallbackCompletableObserver;
import io.reactivex.internal.observers.EmptyCompletableObserver;
import io.reactivex.internal.operators.completable.CompletableAmb;
import io.reactivex.internal.operators.completable.CompletableAndThenCompletable;
import io.reactivex.internal.operators.completable.CompletableCache;
import io.reactivex.internal.operators.completable.CompletableConcat;
import io.reactivex.internal.operators.completable.CompletableConcatArray;
import io.reactivex.internal.operators.completable.CompletableConcatIterable;
import io.reactivex.internal.operators.completable.CompletableCreate;
import io.reactivex.internal.operators.completable.CompletableDefer;
import io.reactivex.internal.operators.completable.CompletableDelay;
import io.reactivex.internal.operators.completable.CompletableDetach;
import io.reactivex.internal.operators.completable.CompletableDisposeOn;
import io.reactivex.internal.operators.completable.CompletableDoFinally;
import io.reactivex.internal.operators.completable.CompletableDoOnEvent;
import io.reactivex.internal.operators.completable.CompletableEmpty;
import io.reactivex.internal.operators.completable.CompletableError;
import io.reactivex.internal.operators.completable.CompletableErrorSupplier;
import io.reactivex.internal.operators.completable.CompletableFromAction;
import io.reactivex.internal.operators.completable.CompletableFromCallable;
import io.reactivex.internal.operators.completable.CompletableFromObservable;
import io.reactivex.internal.operators.completable.CompletableFromPublisher;
import io.reactivex.internal.operators.completable.CompletableFromRunnable;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.completable.CompletableFromUnsafeSource;
import io.reactivex.internal.operators.completable.CompletableHide;
import io.reactivex.internal.operators.completable.CompletableLift;
import io.reactivex.internal.operators.completable.CompletableMaterialize;
import io.reactivex.internal.operators.completable.CompletableMerge;
import io.reactivex.internal.operators.completable.CompletableMergeArray;
import io.reactivex.internal.operators.completable.CompletableMergeDelayErrorArray;
import io.reactivex.internal.operators.completable.CompletableMergeDelayErrorIterable;
import io.reactivex.internal.operators.completable.CompletableMergeIterable;
import io.reactivex.internal.operators.completable.CompletableNever;
import io.reactivex.internal.operators.completable.CompletableObserveOn;
import io.reactivex.internal.operators.completable.CompletableOnErrorComplete;
import io.reactivex.internal.operators.completable.CompletablePeek;
import io.reactivex.internal.operators.completable.CompletableResumeNext;
import io.reactivex.internal.operators.completable.CompletableSubscribeOn;
import io.reactivex.internal.operators.completable.CompletableTakeUntilCompletable;
import io.reactivex.internal.operators.completable.CompletableTimeout;
import io.reactivex.internal.operators.completable.CompletableTimer;
import io.reactivex.internal.operators.completable.CompletableToFlowable;
import io.reactivex.internal.operators.completable.CompletableToObservable;
import io.reactivex.internal.operators.completable.CompletableToSingle;
import io.reactivex.internal.operators.completable.CompletableUsing;
import io.reactivex.internal.operators.maybe.MaybeDelayWithCompletable;
import io.reactivex.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.internal.operators.mixed.CompletableAndThenObservable;
import io.reactivex.internal.operators.mixed.CompletableAndThenPublisher;
import io.reactivex.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Completable implements CompletableSource {
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable amb(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableAmb(null, var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable ambArray(CompletableSource... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if (var0.length == 0) {
         return complete();
      } else {
         return var0.length == 1 ? wrap(var0[0]) : RxJavaPlugins.onAssembly(new CompletableAmb(var0, null));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable complete() {
      return RxJavaPlugins.onAssembly(CompletableEmpty.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableConcatIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Publisher<? extends CompletableSource> var0) {
      return concat(var0, 2);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concat(Publisher<? extends CompletableSource> var0, int var1) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "prefetch");
      return RxJavaPlugins.onAssembly(new CompletableConcat(var0, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable concatArray(CompletableSource... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if (var0.length == 0) {
         return complete();
      } else {
         return var0.length == 1 ? wrap(var0[0]) : RxJavaPlugins.onAssembly(new CompletableConcatArray(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable create(CompletableOnSubscribe var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return RxJavaPlugins.onAssembly(new CompletableCreate(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable defer(Callable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "completableSupplier");
      return RxJavaPlugins.onAssembly(new CompletableDefer(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   private Completable doOnLifecycle(Consumer<? super Disposable> var1, Consumer<? super Throwable> var2, Action var3, Action var4, Action var5, Action var6) {
      ObjectHelper.requireNonNull(var1, "onSubscribe is null");
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var3, "onComplete is null");
      ObjectHelper.requireNonNull(var4, "onTerminate is null");
      ObjectHelper.requireNonNull(var5, "onAfterTerminate is null");
      ObjectHelper.requireNonNull(var6, "onDispose is null");
      return RxJavaPlugins.onAssembly(new CompletablePeek(this, var1, var2, var3, var4, var5, var6));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable error(Throwable var0) {
      ObjectHelper.requireNonNull(var0, "error is null");
      return RxJavaPlugins.onAssembly(new CompletableError(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable error(Callable<? extends Throwable> var0) {
      ObjectHelper.requireNonNull(var0, "errorSupplier is null");
      return RxJavaPlugins.onAssembly(new CompletableErrorSupplier(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromAction(Action var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new CompletableFromAction(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromCallable(Callable<?> var0) {
      ObjectHelper.requireNonNull(var0, "callable is null");
      return RxJavaPlugins.onAssembly(new CompletableFromCallable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromFuture(Future<?> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return fromAction(Functions.futureAction(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Completable fromMaybe(MaybeSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "maybe is null");
      return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Completable fromObservable(ObservableSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "observable is null");
      return RxJavaPlugins.onAssembly(new CompletableFromObservable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Completable fromPublisher(Publisher<T> var0) {
      ObjectHelper.requireNonNull(var0, "publisher is null");
      return RxJavaPlugins.onAssembly(new CompletableFromPublisher(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable fromRunnable(Runnable var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return RxJavaPlugins.onAssembly(new CompletableFromRunnable(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <T> Completable fromSingle(SingleSource<T> var0) {
      ObjectHelper.requireNonNull(var0, "single is null");
      return RxJavaPlugins.onAssembly(new CompletableFromSingle(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Publisher<? extends CompletableSource> var0) {
      return merge0(var0, Integer.MAX_VALUE, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable merge(Publisher<? extends CompletableSource> var0, int var1) {
      return merge0(var0, var1, false);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   private static Completable merge0(Publisher<? extends CompletableSource> var0, int var1, boolean var2) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      ObjectHelper.verifyPositive(var1, "maxConcurrency");
      return RxJavaPlugins.onAssembly(new CompletableMerge(var0, var1, var2));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeArray(CompletableSource... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      if (var0.length == 0) {
         return complete();
      } else {
         return var0.length == 1 ? wrap(var0[0]) : RxJavaPlugins.onAssembly(new CompletableMergeArray(var0));
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeArrayDelayError(CompletableSource... var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorArray(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Iterable<? extends CompletableSource> var0) {
      ObjectHelper.requireNonNull(var0, "sources is null");
      return RxJavaPlugins.onAssembly(new CompletableMergeDelayErrorIterable(var0));
   }

   @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Publisher<? extends CompletableSource> var0) {
      return merge0(var0, Integer.MAX_VALUE, true);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable mergeDelayError(Publisher<? extends CompletableSource> var0, int var1) {
      return merge0(var0, var1, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable never() {
      return RxJavaPlugins.onAssembly(CompletableNever.INSTANCE);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   private Completable timeout0(long var1, TimeUnit var3, Scheduler var4, CompletableSource var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableTimeout(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public static Completable timer(long var0, TimeUnit var2) {
      return timer(var0, var2, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public static Completable timer(long var0, TimeUnit var2, Scheduler var3) {
      ObjectHelper.requireNonNull(var2, "unit is null");
      ObjectHelper.requireNonNull(var3, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableTimer(var0, var2, var3));
   }

   private static NullPointerException toNpe(Throwable var0) {
      NullPointerException var1 = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
      var1.initCause(var0);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable unsafeCreate(CompletableSource var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      if (!(var0 instanceof Completable)) {
         return RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(var0));
      } else {
         throw new IllegalArgumentException("Use of unsafeCreate(Completable)!");
      }
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <R> Completable using(Callable<R> var0, Function<? super R, ? extends CompletableSource> var1, Consumer<? super R> var2) {
      return using(var0, var1, var2, true);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static <R> Completable using(Callable<R> var0, Function<? super R, ? extends CompletableSource> var1, Consumer<? super R> var2, boolean var3) {
      ObjectHelper.requireNonNull(var0, "resourceSupplier is null");
      ObjectHelper.requireNonNull(var1, "completableFunction is null");
      ObjectHelper.requireNonNull(var2, "disposer is null");
      return RxJavaPlugins.onAssembly(new CompletableUsing(var0, var1, var2, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public static Completable wrap(CompletableSource var0) {
      ObjectHelper.requireNonNull(var0, "source is null");
      return var0 instanceof Completable ? RxJavaPlugins.onAssembly((Completable)var0) : RxJavaPlugins.onAssembly(new CompletableFromUnsafeSource(var0));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable ambWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return ambArray(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable andThen(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, var1));
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Flowable<T> andThen(Publisher<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new CompletableAndThenPublisher<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Maybe<T> andThen(MaybeSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new MaybeDelayWithCompletable<>(var1, this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Observable<T> andThen(ObservableSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new CompletableAndThenObservable<>(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Single<T> andThen(SingleSource<T> var1) {
      ObjectHelper.requireNonNull(var1, "next is null");
      return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable<>(var1, this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <R> R as(CompletableConverter<? extends R> var1) {
      return (R)ObjectHelper.requireNonNull(var1, "converter is null").apply(this);
   }

   @SchedulerSupport("none")
   public final void blockingAwait() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe(var1);
      var1.blockingGet();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final boolean blockingAwait(long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      BlockingMultiObserver var4 = new BlockingMultiObserver();
      this.subscribe(var4);
      return var4.blockingAwait(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Throwable blockingGet() {
      BlockingMultiObserver var1 = new BlockingMultiObserver();
      this.subscribe(var1);
      return var1.blockingGetError();
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Throwable blockingGet(long var1, TimeUnit var3) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      BlockingMultiObserver var4 = new BlockingMultiObserver();
      this.subscribe(var4);
      return var4.blockingGetError(var1, var3);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable cache() {
      return RxJavaPlugins.onAssembly(new CompletableCache(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable compose(CompletableTransformer var1) {
      return wrap(ObjectHelper.requireNonNull(var1, "transformer is null").apply(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable concatWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new CompletableAndThenCompletable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable delay(long var1, TimeUnit var3) {
      return this.delay(var1, var3, Schedulers.computation(), false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable delay(long var1, TimeUnit var3, Scheduler var4) {
      return this.delay(var1, var3, var4, false);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable delay(long var1, TimeUnit var3, Scheduler var4, boolean var5) {
      ObjectHelper.requireNonNull(var3, "unit is null");
      ObjectHelper.requireNonNull(var4, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableDelay(this, var1, var3, var4, var5));
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable delaySubscription(long var1, TimeUnit var3) {
      return this.delaySubscription(var1, var3, Schedulers.computation());
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable delaySubscription(long var1, TimeUnit var3, Scheduler var4) {
      return timer(var1, var3, var4).andThen(this);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doAfterTerminate(Action var1) {
      return this.doOnLifecycle(
         Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, var1, Functions.EMPTY_ACTION
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doFinally(Action var1) {
      ObjectHelper.requireNonNull(var1, "onFinally is null");
      return RxJavaPlugins.onAssembly(new CompletableDoFinally(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnComplete(Action var1) {
      return this.doOnLifecycle(
         Functions.emptyConsumer(), Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnDispose(Action var1) {
      return this.doOnLifecycle(
         Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, var1
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnError(Consumer<? super Throwable> var1) {
      return this.doOnLifecycle(Functions.emptyConsumer(), var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnEvent(Consumer<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "onEvent is null");
      return RxJavaPlugins.onAssembly(new CompletableDoOnEvent(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnSubscribe(Consumer<? super Disposable> var1) {
      return this.doOnLifecycle(var1, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable doOnTerminate(Action var1) {
      return this.doOnLifecycle(
         Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, var1, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION
      );
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable hide() {
      return RxJavaPlugins.onAssembly(new CompletableHide(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable lift(CompletableOperator var1) {
      ObjectHelper.requireNonNull(var1, "onLift is null");
      return RxJavaPlugins.onAssembly(new CompletableLift(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Single<Notification<T>> materialize() {
      return RxJavaPlugins.onAssembly(new CompletableMaterialize<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable mergeWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return mergeArray(this, var1);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable observeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableObserveOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorComplete() {
      return this.onErrorComplete(Functions.alwaysTrue());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorComplete(Predicate<? super Throwable> var1) {
      ObjectHelper.requireNonNull(var1, "predicate is null");
      return RxJavaPlugins.onAssembly(new CompletableOnErrorComplete(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onErrorResumeNext(Function<? super Throwable, ? extends CompletableSource> var1) {
      ObjectHelper.requireNonNull(var1, "errorMapper is null");
      return RxJavaPlugins.onAssembly(new CompletableResumeNext(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable onTerminateDetach() {
      return RxJavaPlugins.onAssembly(new CompletableDetach(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeat() {
      return fromPublisher(this.toFlowable().repeat());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeat(long var1) {
      return fromPublisher(this.toFlowable().repeat(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeatUntil(BooleanSupplier var1) {
      return fromPublisher(this.toFlowable().repeatUntil(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> var1) {
      return fromPublisher(this.toFlowable().repeatWhen(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry() {
      return fromPublisher(this.toFlowable().retry());
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(long var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(long var1, Predicate<? super Throwable> var3) {
      return fromPublisher(this.toFlowable().retry(var1, var3));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(BiPredicate<? super Integer, ? super Throwable> var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retry(Predicate<? super Throwable> var1) {
      return fromPublisher(this.toFlowable().retry(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> var1) {
      return fromPublisher(this.toFlowable().retryWhen(var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable startWith(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return concatArray(var1, this);
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Flowable<T> startWith(Publisher<T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return this.<T>toFlowable().startWith(var1);
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Observable<T> startWith(Observable<T> var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return var1.concatWith(this.toObservable());
   }

   @SchedulerSupport("none")
   public final Disposable subscribe() {
      EmptyCompletableObserver var1 = new EmptyCompletableObserver();
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Action var1) {
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      CallbackCompletableObserver var2 = new CallbackCompletableObserver(var1);
      this.subscribe(var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Disposable subscribe(Action var1, Consumer<? super Throwable> var2) {
      ObjectHelper.requireNonNull(var2, "onError is null");
      ObjectHelper.requireNonNull(var1, "onComplete is null");
      CallbackCompletableObserver var3 = new CallbackCompletableObserver(var2, var1);
      this.subscribe(var3);
      return var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @SchedulerSupport("none")
   @Override
   public final void subscribe(CompletableObserver var1) {
      ObjectHelper.requireNonNull(var1, "observer is null");

      try {
         try {
            var1 = RxJavaPlugins.onSubscribe(this, var1);
            ObjectHelper.requireNonNull(
               var1,
               "The RxJavaPlugins.onSubscribe hook returned a null CompletableObserver. Please check the handler provided to RxJavaPlugins.setOnCompletableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins"
            );
            this.subscribeActual(var1);
            return;
         } catch (NullPointerException var4) {
            var6 = var4;
         }
      } catch (Throwable var5) {
         Exceptions.throwIfFatal(var5);
         RxJavaPlugins.onError(var5);
         throw toNpe(var5);
      }

      throw var6;
   }

   protected abstract void subscribeActual(CompletableObserver var1);

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable subscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableSubscribeOn(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <E extends CompletableObserver> E subscribeWith(E var1) {
      this.subscribe(var1);
      return (E)var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final Completable takeUntil(CompletableSource var1) {
      ObjectHelper.requireNonNull(var1, "other is null");
      return RxJavaPlugins.onAssembly(new CompletableTakeUntilCompletable(this, var1));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<Void> test() {
      TestObserver var1 = new TestObserver();
      this.subscribe(var1);
      return var1;
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final TestObserver<Void> test(boolean var1) {
      TestObserver var2 = new TestObserver();
      if (var1) {
         var2.cancel();
      }

      this.subscribe(var2);
      return var2;
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable timeout(long var1, TimeUnit var3) {
      return this.timeout0(var1, var3, Schedulers.computation(), null);
   }

   @CheckReturnValue
   @SchedulerSupport("io.reactivex:computation")
   public final Completable timeout(long var1, TimeUnit var3, CompletableSource var4) {
      ObjectHelper.requireNonNull(var4, "other is null");
      return this.timeout0(var1, var3, Schedulers.computation(), var4);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable timeout(long var1, TimeUnit var3, Scheduler var4) {
      return this.timeout0(var1, var3, var4, null);
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable timeout(long var1, TimeUnit var3, Scheduler var4, CompletableSource var5) {
      ObjectHelper.requireNonNull(var5, "other is null");
      return this.timeout0(var1, var3, var4, var5);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <U> U to(Function<? super Completable, U> var1) {
      try {
         return (U)ObjectHelper.requireNonNull(var1, "converter is null").apply(this);
      } catch (Throwable var3) {
         Exceptions.throwIfFatal(var3);
         throw ExceptionHelper.wrapOrThrow(var3);
      }
   }

   @BackpressureSupport(BackpressureKind.FULL)
   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Flowable<T> toFlowable() {
      return this instanceof FuseToFlowable ? ((FuseToFlowable)this).fuseToFlowable() : RxJavaPlugins.onAssembly(new CompletableToFlowable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Maybe<T> toMaybe() {
      return this instanceof FuseToMaybe ? ((FuseToMaybe)this).fuseToMaybe() : RxJavaPlugins.onAssembly(new MaybeFromCompletable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Observable<T> toObservable() {
      return this instanceof FuseToObservable ? ((FuseToObservable)this).fuseToObservable() : RxJavaPlugins.onAssembly(new CompletableToObservable<>(this));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Single<T> toSingle(Callable<? extends T> var1) {
      ObjectHelper.requireNonNull(var1, "completionValueSupplier is null");
      return RxJavaPlugins.onAssembly(new CompletableToSingle<>(this, var1, null));
   }

   @CheckReturnValue
   @SchedulerSupport("none")
   public final <T> Single<T> toSingleDefault(T var1) {
      ObjectHelper.requireNonNull(var1, "completionValue is null");
      return RxJavaPlugins.onAssembly(new CompletableToSingle<>(this, null, (T)var1));
   }

   @CheckReturnValue
   @SchedulerSupport("custom")
   public final Completable unsubscribeOn(Scheduler var1) {
      ObjectHelper.requireNonNull(var1, "scheduler is null");
      return RxJavaPlugins.onAssembly(new CompletableDisposeOn(this, var1));
   }
}
