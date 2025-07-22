package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableInternalHelper {
   private FlowableInternalHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static <T, U> Function<T, Publisher<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> var0) {
      return new FlowableInternalHelper.FlatMapIntoIterable<>(var0);
   }

   public static <T, U, R> Function<T, Publisher<R>> flatMapWithCombiner(
      Function<? super T, ? extends Publisher<? extends U>> var0, BiFunction<? super T, ? super U, ? extends R> var1
   ) {
      return new FlowableInternalHelper.FlatMapWithCombinerOuter<>(var1, var0);
   }

   public static <T, U> Function<T, Publisher<T>> itemDelay(Function<? super T, ? extends Publisher<U>> var0) {
      return new FlowableInternalHelper.ItemDelayFunction<>(var0);
   }

   public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> var0) {
      return new FlowableInternalHelper.ReplayCallable<>(var0);
   }

   public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> var0, int var1) {
      return new FlowableInternalHelper.BufferedReplayCallable<>(var0, var1);
   }

   public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> var0, int var1, long var2, TimeUnit var4, Scheduler var5) {
      return new FlowableInternalHelper.BufferedTimedReplay<>(var0, var1, var2, var4, var5);
   }

   public static <T> Callable<ConnectableFlowable<T>> replayCallable(Flowable<T> var0, long var1, TimeUnit var3, Scheduler var4) {
      return new FlowableInternalHelper.TimedReplay<>(var0, var1, var3, var4);
   }

   public static <T, R> Function<Flowable<T>, Publisher<R>> replayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> var0, Scheduler var1) {
      return new FlowableInternalHelper.ReplayFunction<>(var0, var1);
   }

   public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> var0) {
      return new FlowableInternalHelper.SimpleBiGenerator<>(var0);
   }

   public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> var0) {
      return new FlowableInternalHelper.SimpleGenerator<>(var0);
   }

   public static <T> Action subscriberOnComplete(Subscriber<T> var0) {
      return new FlowableInternalHelper.SubscriberOnComplete(var0);
   }

   public static <T> Consumer<Throwable> subscriberOnError(Subscriber<T> var0) {
      return new FlowableInternalHelper.SubscriberOnError(var0);
   }

   public static <T> Consumer<T> subscriberOnNext(Subscriber<T> var0) {
      return new FlowableInternalHelper.SubscriberOnNext<>(var0);
   }

   public static <T, R> Function<List<Publisher<? extends T>>, Publisher<? extends R>> zipIterable(Function<? super Object[], ? extends R> var0) {
      return new FlowableInternalHelper.ZipIterableFunction<>(var0);
   }

   static final class BufferedReplayCallable<T> implements Callable<ConnectableFlowable<T>> {
      private final int bufferSize;
      private final Flowable<T> parent;

      BufferedReplayCallable(Flowable<T> var1, int var2) {
         this.parent = var1;
         this.bufferSize = var2;
      }

      public ConnectableFlowable<T> call() {
         return this.parent.replay(this.bufferSize);
      }
   }

   static final class BufferedTimedReplay<T> implements Callable<ConnectableFlowable<T>> {
      private final int bufferSize;
      private final Flowable<T> parent;
      private final Scheduler scheduler;
      private final long time;
      private final TimeUnit unit;

      BufferedTimedReplay(Flowable<T> var1, int var2, long var3, TimeUnit var5, Scheduler var6) {
         this.parent = var1;
         this.bufferSize = var2;
         this.time = var3;
         this.unit = var5;
         this.scheduler = var6;
      }

      public ConnectableFlowable<T> call() {
         return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler);
      }
   }

   static final class FlatMapIntoIterable<T, U> implements Function<T, Publisher<U>> {
      private final Function<? super T, ? extends Iterable<? extends U>> mapper;

      FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> var1) {
         this.mapper = var1;
      }

      public Publisher<U> apply(T var1) throws Exception {
         return new FlowableFromIterable(ObjectHelper.requireNonNull((Iterable<? extends T>)this.mapper.apply((T)var1), "The mapper returned a null Iterable"));
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

   static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, Publisher<R>> {
      private final BiFunction<? super T, ? super U, ? extends R> combiner;
      private final Function<? super T, ? extends Publisher<? extends U>> mapper;

      FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> var1, Function<? super T, ? extends Publisher<? extends U>> var2) {
         this.combiner = var1;
         this.mapper = var2;
      }

      public Publisher<R> apply(T var1) throws Exception {
         return new FlowableMapPublisher<>(
            ObjectHelper.requireNonNull((Publisher<U>)this.mapper.apply((T)var1), "The mapper returned a null Publisher"),
            new FlowableInternalHelper.FlatMapWithCombinerInner<>(this.combiner, (T)var1)
         );
      }
   }

   static final class ItemDelayFunction<T, U> implements Function<T, Publisher<T>> {
      final Function<? super T, ? extends Publisher<U>> itemDelay;

      ItemDelayFunction(Function<? super T, ? extends Publisher<U>> var1) {
         this.itemDelay = var1;
      }

      public Publisher<T> apply(T var1) throws Exception {
         return new FlowableTakePublisher<T>(
               ObjectHelper.requireNonNull((Publisher<T>)this.itemDelay.apply((T)var1), "The itemDelay returned a null Publisher"), 1L
            )
            .map(Functions.justFunction((T)var1))
            .defaultIfEmpty((T)var1);
      }
   }

   static final class ReplayCallable<T> implements Callable<ConnectableFlowable<T>> {
      private final Flowable<T> parent;

      ReplayCallable(Flowable<T> var1) {
         this.parent = var1;
      }

      public ConnectableFlowable<T> call() {
         return this.parent.replay();
      }
   }

   static final class ReplayFunction<T, R> implements Function<Flowable<T>, Publisher<R>> {
      private final Scheduler scheduler;
      private final Function<? super Flowable<T>, ? extends Publisher<R>> selector;

      ReplayFunction(Function<? super Flowable<T>, ? extends Publisher<R>> var1, Scheduler var2) {
         this.selector = var1;
         this.scheduler = var2;
      }

      public Publisher<R> apply(Flowable<T> var1) throws Exception {
         return Flowable.fromPublisher(ObjectHelper.requireNonNull((Publisher<? extends T>)this.selector.apply(var1), "The selector returned a null Publisher"))
            .observeOn(this.scheduler);
      }
   }

   public static enum RequestMax implements Consumer<Subscription> {
      INSTANCE;
      private static final FlowableInternalHelper.RequestMax[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         FlowableInternalHelper.RequestMax var0 = new FlowableInternalHelper.RequestMax();
         INSTANCE = var0;
         $VALUES = new FlowableInternalHelper.RequestMax[]{var0};
      }

      public void accept(Subscription var1) throws Exception {
         var1.request(Long.MAX_VALUE);
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

   static final class SubscriberOnComplete<T> implements Action {
      final Subscriber<T> subscriber;

      SubscriberOnComplete(Subscriber<T> var1) {
         this.subscriber = var1;
      }

      @Override
      public void run() throws Exception {
         this.subscriber.onComplete();
      }
   }

   static final class SubscriberOnError<T> implements Consumer<Throwable> {
      final Subscriber<T> subscriber;

      SubscriberOnError(Subscriber<T> var1) {
         this.subscriber = var1;
      }

      public void accept(Throwable var1) throws Exception {
         this.subscriber.onError(var1);
      }
   }

   static final class SubscriberOnNext<T> implements Consumer<T> {
      final Subscriber<T> subscriber;

      SubscriberOnNext(Subscriber<T> var1) {
         this.subscriber = var1;
      }

      @Override
      public void accept(T var1) throws Exception {
         this.subscriber.onNext(var1);
      }
   }

   static final class TimedReplay<T> implements Callable<ConnectableFlowable<T>> {
      private final Flowable<T> parent;
      private final Scheduler scheduler;
      private final long time;
      private final TimeUnit unit;

      TimedReplay(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5) {
         this.parent = var1;
         this.time = var2;
         this.unit = var4;
         this.scheduler = var5;
      }

      public ConnectableFlowable<T> call() {
         return this.parent.replay(this.time, this.unit, this.scheduler);
      }
   }

   static final class ZipIterableFunction<T, R> implements Function<List<Publisher<? extends T>>, Publisher<? extends R>> {
      private final Function<? super Object[], ? extends R> zipper;

      ZipIterableFunction(Function<? super Object[], ? extends R> var1) {
         this.zipper = var1;
      }

      public Publisher<? extends R> apply(List<Publisher<? extends T>> var1) {
         return Flowable.zipIterable(var1, this.zipper, false, Flowable.bufferSize());
      }
   }
}
