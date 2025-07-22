package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public final class ObservableInternalHelper {
   private ObservableInternalHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> var0) {
      return new ObservableInternalHelper.FlatMapIntoIterable<>(var0);
   }

   public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(
      Function<? super T, ? extends ObservableSource<? extends U>> var0, BiFunction<? super T, ? super U, ? extends R> var1
   ) {
      return new ObservableInternalHelper.FlatMapWithCombinerOuter<>(var1, var0);
   }

   public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> var0) {
      return new ObservableInternalHelper.ItemDelayFunction<>(var0);
   }

   public static <T> Action observerOnComplete(Observer<T> var0) {
      return new ObservableInternalHelper.ObserverOnComplete(var0);
   }

   public static <T> Consumer<Throwable> observerOnError(Observer<T> var0) {
      return new ObservableInternalHelper.ObserverOnError(var0);
   }

   public static <T> Consumer<T> observerOnNext(Observer<T> var0) {
      return new ObservableInternalHelper.ObserverOnNext<>(var0);
   }

   public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> var0) {
      return new ObservableInternalHelper.ReplayCallable<>(var0);
   }

   public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> var0, int var1) {
      return new ObservableInternalHelper.BufferedReplayCallable<>(var0, var1);
   }

   public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> var0, int var1, long var2, TimeUnit var4, Scheduler var5) {
      return new ObservableInternalHelper.BufferedTimedReplayCallable<>(var0, var1, var2, var4, var5);
   }

   public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> var0, long var1, TimeUnit var3, Scheduler var4) {
      return new ObservableInternalHelper.TimedReplayCallable<>(var0, var1, var3, var4);
   }

   public static <T, R> Function<Observable<T>, ObservableSource<R>> replayFunction(
      Function<? super Observable<T>, ? extends ObservableSource<R>> var0, Scheduler var1
   ) {
      return new ObservableInternalHelper.ReplayFunction<>(var0, var1);
   }

   public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> var0) {
      return new ObservableInternalHelper.SimpleBiGenerator<>(var0);
   }

   public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> var0) {
      return new ObservableInternalHelper.SimpleGenerator<>(var0);
   }

   public static <T, R> Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> zipIterable(Function<? super Object[], ? extends R> var0) {
      return new ObservableInternalHelper.ZipIterableFunction<>(var0);
   }

   static final class BufferedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
      private final int bufferSize;
      private final Observable<T> parent;

      BufferedReplayCallable(Observable<T> var1, int var2) {
         this.parent = var1;
         this.bufferSize = var2;
      }

      public ConnectableObservable<T> call() {
         return this.parent.replay(this.bufferSize);
      }
   }

   static final class BufferedTimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
      private final int bufferSize;
      private final Observable<T> parent;
      private final Scheduler scheduler;
      private final long time;
      private final TimeUnit unit;

      BufferedTimedReplayCallable(Observable<T> var1, int var2, long var3, TimeUnit var5, Scheduler var6) {
         this.parent = var1;
         this.bufferSize = var2;
         this.time = var3;
         this.unit = var5;
         this.scheduler = var6;
      }

      public ConnectableObservable<T> call() {
         return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler);
      }
   }

   static final class FlatMapIntoIterable<T, U> implements Function<T, ObservableSource<U>> {
      private final Function<? super T, ? extends Iterable<? extends U>> mapper;

      FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
         this.mapper = var1;
      }

      public ObservableSource<U> apply(T var1) throws Exception {
         return new ObservableFromIterable(
            ObjectHelper.requireNonNull((Iterable<? extends T>)this.mapper.apply((T)var1), "The mapper returned a null Iterable")
         );
      }
   }

   static final class FlatMapWithCombinerInner<U, R, T> implements Function<U, R> {
      private final BiFunction<? super T, ? super U, ? extends R> combiner;
      private final T t;

      FlatMapWithCombinerInner(BiFunction<? super T, ? super U, ? extends R> var1, T var2) {
         this.combiner = var1;
         this.t = (T)var2;
      }

      @Override
      public R apply(U var1) throws Exception {
         return (R)this.combiner.apply(this.t, (U)var1);
      }
   }

   static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, ObservableSource<R>> {
      private final BiFunction<? super T, ? super U, ? extends R> combiner;
      private final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

      FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> var1, Function<? super T, ? extends ObservableSource<? extends U>> var2) {
         this.combiner = var1;
         this.mapper = var2;
      }

      public ObservableSource<R> apply(T var1) throws Exception {
         return new ObservableMap<>(
            ObjectHelper.requireNonNull((ObservableSource<U>)this.mapper.apply((T)var1), "The mapper returned a null ObservableSource"),
            new ObservableInternalHelper.FlatMapWithCombinerInner<>(this.combiner, (T)var1)
         );
      }
   }

   static final class ItemDelayFunction<T, U> implements Function<T, ObservableSource<T>> {
      final Function<? super T, ? extends ObservableSource<U>> itemDelay;

      ItemDelayFunction(Function<? super T, ? extends ObservableSource<U>> var1) {
         this.itemDelay = var1;
      }

      public ObservableSource<T> apply(T var1) throws Exception {
         return new ObservableTake<>(
               ObjectHelper.requireNonNull((ObservableSource<T>)this.itemDelay.apply((T)var1), "The itemDelay returned a null ObservableSource"), 1L
            )
            .map(Functions.justFunction((T)var1))
            .defaultIfEmpty((T)var1);
      }
   }

   static enum MapToInt implements Function<Object, Object> {
      INSTANCE;
      private static final ObservableInternalHelper.MapToInt[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         ObservableInternalHelper.MapToInt var0 = new ObservableInternalHelper.MapToInt();
         INSTANCE = var0;
         $VALUES = new ObservableInternalHelper.MapToInt[]{var0};
      }

      @Override
      public Object apply(Object var1) throws Exception {
         return 0;
      }
   }

   static final class ObserverOnComplete<T> implements Action {
      final Observer<T> observer;

      ObserverOnComplete(Observer<T> var1) {
         this.observer = var1;
      }

      @Override
      public void run() throws Exception {
         this.observer.onComplete();
      }
   }

   static final class ObserverOnError<T> implements Consumer<Throwable> {
      final Observer<T> observer;

      ObserverOnError(Observer<T> var1) {
         this.observer = var1;
      }

      public void accept(Throwable var1) throws Exception {
         this.observer.onError(var1);
      }
   }

   static final class ObserverOnNext<T> implements Consumer<T> {
      final Observer<T> observer;

      ObserverOnNext(Observer<T> var1) {
         this.observer = var1;
      }

      @Override
      public void accept(T var1) throws Exception {
         this.observer.onNext((T)var1);
      }
   }

   static final class ReplayCallable<T> implements Callable<ConnectableObservable<T>> {
      private final Observable<T> parent;

      ReplayCallable(Observable<T> var1) {
         this.parent = var1;
      }

      public ConnectableObservable<T> call() {
         return this.parent.replay();
      }
   }

   static final class ReplayFunction<T, R> implements Function<Observable<T>, ObservableSource<R>> {
      private final Scheduler scheduler;
      private final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

      ReplayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> var1, Scheduler var2) {
         this.selector = var1;
         this.scheduler = var2;
      }

      public ObservableSource<R> apply(Observable<T> var1) throws Exception {
         return Observable.wrap(ObjectHelper.requireNonNull((ObservableSource<T>)this.selector.apply(var1), "The selector returned a null ObservableSource"))
            .observeOn(this.scheduler);
      }
   }

   static final class SimpleBiGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
      final BiConsumer<S, Emitter<T>> consumer;

      SimpleBiGenerator(BiConsumer<S, Emitter<T>> var1) {
         this.consumer = var1;
      }

      public S apply(S var1, Emitter<T> var2) throws Exception {
         this.consumer.accept((S)var1, var2);
         return (S)var1;
      }
   }

   static final class SimpleGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
      final Consumer<Emitter<T>> consumer;

      SimpleGenerator(Consumer<Emitter<T>> var1) {
         this.consumer = var1;
      }

      public S apply(S var1, Emitter<T> var2) throws Exception {
         this.consumer.accept(var2);
         return (S)var1;
      }
   }

   static final class TimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
      private final Observable<T> parent;
      private final Scheduler scheduler;
      private final long time;
      private final TimeUnit unit;

      TimedReplayCallable(Observable<T> var1, long var2, TimeUnit var4, Scheduler var5) {
         this.parent = var1;
         this.time = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      public ConnectableObservable<T> call() {
         return this.parent.replay(this.time, this.unit, this.scheduler);
      }
   }

   static final class ZipIterableFunction<T, R> implements Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> {
      private final Function<? super Object[], ? extends R> zipper;

      ZipIterableFunction(Function<? super Object[], ? extends R> var1) {
         this.zipper = var1;
      }

      public ObservableSource<? extends R> apply(List<ObservableSource<? extends T>> var1) {
         return Observable.zipIterable(var1, this.zipper, false, Observable.bufferSize());
      }
   }
}
