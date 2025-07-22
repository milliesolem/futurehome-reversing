package io.reactivex.internal.functions;

import io.reactivex.Notification;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
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
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

public final class Functions {
   static final Predicate<Object> ALWAYS_FALSE = new Functions.FalsePredicate();
   static final Predicate<Object> ALWAYS_TRUE = new Functions.TruePredicate();
   public static final Action EMPTY_ACTION = new Functions.EmptyAction();
   static final Consumer<Object> EMPTY_CONSUMER = new Functions.EmptyConsumer();
   public static final LongConsumer EMPTY_LONG_CONSUMER = new Functions.EmptyLongConsumer();
   public static final Runnable EMPTY_RUNNABLE = new Functions.EmptyRunnable();
   public static final Consumer<Throwable> ERROR_CONSUMER = new Functions.ErrorConsumer();
   static final Function<Object, Object> IDENTITY = new Functions.Identity();
   static final Comparator<Object> NATURAL_COMPARATOR = new Functions.NaturalObjectComparator();
   static final Callable<Object> NULL_SUPPLIER = new Functions.NullCallable();
   public static final Consumer<Throwable> ON_ERROR_MISSING = new Functions.OnErrorMissingConsumer();
   public static final Consumer<Subscription> REQUEST_MAX = new Functions.MaxRequestSubscription();

   private Functions() {
      throw new IllegalStateException("No instances!");
   }

   public static <T> Consumer<T> actionConsumer(Action var0) {
      return new Functions.ActionConsumer<>(var0);
   }

   public static <T> Predicate<T> alwaysFalse() {
      return (Predicate<T>)ALWAYS_FALSE;
   }

   public static <T> Predicate<T> alwaysTrue() {
      return (Predicate<T>)ALWAYS_TRUE;
   }

   public static <T> Consumer<T> boundedConsumer(int var0) {
      return new Functions.BoundedConsumer(var0);
   }

   public static <T, U> Function<T, U> castFunction(Class<U> var0) {
      return new Functions.CastToClass<>(var0);
   }

   public static <T> Callable<List<T>> createArrayList(int var0) {
      return new Functions.ArrayListCapacityCallable<>(var0);
   }

   public static <T> Callable<Set<T>> createHashSet() {
      return Functions.HashSetCallable.INSTANCE;
   }

   public static <T> Consumer<T> emptyConsumer() {
      return (Consumer<T>)EMPTY_CONSUMER;
   }

   public static <T> Predicate<T> equalsWith(T var0) {
      return new Functions.EqualsPredicate<>((T)var0);
   }

   public static Action futureAction(Future<?> var0) {
      return new Functions.FutureAction(var0);
   }

   public static <T> Function<T, T> identity() {
      return (Function<T, T>)IDENTITY;
   }

   public static <T, U> Predicate<T> isInstanceOf(Class<U> var0) {
      return new Functions.ClassFilter<>(var0);
   }

   public static <T> Callable<T> justCallable(T var0) {
      return new Functions.JustValue<>((T)var0);
   }

   public static <T, U> Function<T, U> justFunction(U var0) {
      return new Functions.JustValue<>((U)var0);
   }

   public static <T> Function<List<T>, List<T>> listSorter(Comparator<? super T> var0) {
      return new Functions.ListSorter<>(var0);
   }

   public static <T> Comparator<T> naturalComparator() {
      return Functions.NaturalComparator.INSTANCE;
   }

   public static <T> Comparator<T> naturalOrder() {
      return (Comparator<T>)NATURAL_COMPARATOR;
   }

   public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> var0) {
      return new Functions.NotificationOnComplete(var0);
   }

   public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> var0) {
      return new Functions.NotificationOnError(var0);
   }

   public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> var0) {
      return new Functions.NotificationOnNext<>(var0);
   }

   public static <T> Callable<T> nullSupplier() {
      return (Callable<T>)NULL_SUPPLIER;
   }

   public static <T> Predicate<T> predicateReverseFor(BooleanSupplier var0) {
      return new Functions.BooleanSupplierPredicateReverse<>(var0);
   }

   public static <T> Function<T, Timed<T>> timestampWith(TimeUnit var0, Scheduler var1) {
      return new Functions.TimestampFunction<>(var0, var1);
   }

   public static <T1, T2, R> Function<Object[], R> toFunction(BiFunction<? super T1, ? super T2, ? extends R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array2Func<>(var0);
   }

   public static <T1, T2, T3, R> Function<Object[], R> toFunction(Function3<T1, T2, T3, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array3Func<>(var0);
   }

   public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(Function4<T1, T2, T3, T4, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array4Func<>(var0);
   }

   public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(Function5<T1, T2, T3, T4, T5, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array5Func<>(var0);
   }

   public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(Function6<T1, T2, T3, T4, T5, T6, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array6Func<>(var0);
   }

   public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(Function7<T1, T2, T3, T4, T5, T6, T7, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array7Func<>(var0);
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array8Func<>(var0);
   }

   public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> var0) {
      ObjectHelper.requireNonNull(var0, "f is null");
      return new Functions.Array9Func<>(var0);
   }

   public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(Function<? super T, ? extends K> var0) {
      return new Functions.ToMapKeySelector<>(var0);
   }

   public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(Function<? super T, ? extends K> var0, Function<? super T, ? extends V> var1) {
      return new Functions.ToMapKeyValueSelector<>(var1, var0);
   }

   public static <T, K, V> BiConsumer<Map<K, Collection<V>>, T> toMultimapKeyValueSelector(
      Function<? super T, ? extends K> var0, Function<? super T, ? extends V> var1, Function<? super K, ? extends Collection<? super V>> var2
   ) {
      return new Functions.ToMultimapKeyValueSelector<>(var2, var1, var0);
   }

   static final class ActionConsumer<T> implements Consumer<T> {
      final Action action;

      ActionConsumer(Action var1) {
         this.action = var1;
      }

      @Override
      public void accept(T var1) throws Exception {
         this.action.run();
      }
   }

   static final class Array2Func<T1, T2, R> implements Function<Object[], R> {
      final BiFunction<? super T1, ? super T2, ? extends R> f;

      Array2Func(BiFunction<? super T1, ? super T2, ? extends R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 2) {
            return (R)this.f.apply((T1)var1[0], (T2)var1[1]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 2 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array3Func<T1, T2, T3, R> implements Function<Object[], R> {
      final Function3<T1, T2, T3, R> f;

      Array3Func(Function3<T1, T2, T3, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 3) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 3 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array4Func<T1, T2, T3, T4, R> implements Function<Object[], R> {
      final Function4<T1, T2, T3, T4, R> f;

      Array4Func(Function4<T1, T2, T3, T4, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 4) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 4 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array5Func<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {
      private final Function5<T1, T2, T3, T4, T5, R> f;

      Array5Func(Function5<T1, T2, T3, T4, T5, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 5) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3], (T5)var1[4]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 5 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array6Func<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {
      final Function6<T1, T2, T3, T4, T5, T6, R> f;

      Array6Func(Function6<T1, T2, T3, T4, T5, T6, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 6) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3], (T5)var1[4], (T6)var1[5]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 6 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array7Func<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {
      final Function7<T1, T2, T3, T4, T5, T6, T7, R> f;

      Array7Func(Function7<T1, T2, T3, T4, T5, T6, T7, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 7) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3], (T5)var1[4], (T6)var1[5], (T7)var1[6]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 7 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array8Func<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {
      final Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> f;

      Array8Func(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 8) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3], (T5)var1[4], (T6)var1[5], (T7)var1[6], (T8)var1[7]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 8 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class Array9Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {
      final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> f;

      Array9Func(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> var1) {
         this.f = var1;
      }

      public R apply(Object[] var1) throws Exception {
         if (var1.length == 9) {
            return this.f.apply((T1)var1[0], (T2)var1[1], (T3)var1[2], (T4)var1[3], (T5)var1[4], (T6)var1[5], (T7)var1[6], (T8)var1[7], (T9)var1[8]);
         } else {
            StringBuilder var2 = new StringBuilder("Array of size 9 expected but got ");
            var2.append(var1.length);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   static final class ArrayListCapacityCallable<T> implements Callable<List<T>> {
      final int capacity;

      ArrayListCapacityCallable(int var1) {
         this.capacity = var1;
      }

      public List<T> call() throws Exception {
         return new ArrayList<>(this.capacity);
      }
   }

   static final class BooleanSupplierPredicateReverse<T> implements Predicate<T> {
      final BooleanSupplier supplier;

      BooleanSupplierPredicateReverse(BooleanSupplier var1) {
         this.supplier = var1;
      }

      @Override
      public boolean test(T var1) throws Exception {
         return this.supplier.getAsBoolean() ^ true;
      }
   }

   public static class BoundedConsumer implements Consumer<Subscription> {
      final int bufferSize;

      BoundedConsumer(int var1) {
         this.bufferSize = var1;
      }

      public void accept(Subscription var1) throws Exception {
         var1.request(this.bufferSize);
      }
   }

   static final class CastToClass<T, U> implements Function<T, U> {
      final Class<U> clazz;

      CastToClass(Class<U> var1) {
         this.clazz = var1;
      }

      @Override
      public U apply(T var1) throws Exception {
         return this.clazz.cast(var1);
      }
   }

   static final class ClassFilter<T, U> implements Predicate<T> {
      final Class<U> clazz;

      ClassFilter(Class<U> var1) {
         this.clazz = var1;
      }

      @Override
      public boolean test(T var1) throws Exception {
         return this.clazz.isInstance(var1);
      }
   }

   static final class EmptyAction implements Action {
      @Override
      public void run() {
      }

      @Override
      public String toString() {
         return "EmptyAction";
      }
   }

   static final class EmptyConsumer implements Consumer<Object> {
      @Override
      public void accept(Object var1) {
      }

      @Override
      public String toString() {
         return "EmptyConsumer";
      }
   }

   static final class EmptyLongConsumer implements LongConsumer {
      @Override
      public void accept(long var1) {
      }
   }

   static final class EmptyRunnable implements Runnable {
      @Override
      public void run() {
      }

      @Override
      public String toString() {
         return "EmptyRunnable";
      }
   }

   static final class EqualsPredicate<T> implements Predicate<T> {
      final T value;

      EqualsPredicate(T var1) {
         this.value = (T)var1;
      }

      @Override
      public boolean test(T var1) throws Exception {
         return ObjectHelper.equals(var1, this.value);
      }
   }

   static final class ErrorConsumer implements Consumer<Throwable> {
      public void accept(Throwable var1) {
         RxJavaPlugins.onError(var1);
      }
   }

   static final class FalsePredicate implements Predicate<Object> {
      @Override
      public boolean test(Object var1) {
         return false;
      }
   }

   static final class FutureAction implements Action {
      final Future<?> future;

      FutureAction(Future<?> var1) {
         this.future = var1;
      }

      @Override
      public void run() throws Exception {
         this.future.get();
      }
   }

   static enum HashSetCallable implements Callable<Set<Object>> {
      INSTANCE;
      private static final Functions.HashSetCallable[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Functions.HashSetCallable var0 = new Functions.HashSetCallable();
         INSTANCE = var0;
         $VALUES = new Functions.HashSetCallable[]{var0};
      }

      public Set<Object> call() throws Exception {
         return new HashSet<>();
      }
   }

   static final class Identity implements Function<Object, Object> {
      @Override
      public Object apply(Object var1) {
         return var1;
      }

      @Override
      public String toString() {
         return "IdentityFunction";
      }
   }

   static final class JustValue<T, U> implements Callable<U>, Function<T, U> {
      final U value;

      JustValue(U var1) {
         this.value = (U)var1;
      }

      @Override
      public U apply(T var1) throws Exception {
         return this.value;
      }

      @Override
      public U call() throws Exception {
         return this.value;
      }
   }

   static final class ListSorter<T> implements Function<List<T>, List<T>> {
      final Comparator<? super T> comparator;

      ListSorter(Comparator<? super T> var1) {
         this.comparator = var1;
      }

      public List<T> apply(List<T> var1) {
         Collections.sort(var1, this.comparator);
         return var1;
      }
   }

   static final class MaxRequestSubscription implements Consumer<Subscription> {
      public void accept(Subscription var1) throws Exception {
         var1.request(Long.MAX_VALUE);
      }
   }

   static enum NaturalComparator implements Comparator<Object> {
      INSTANCE;
      private static final Functions.NaturalComparator[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         Functions.NaturalComparator var0 = new Functions.NaturalComparator();
         INSTANCE = var0;
         $VALUES = new Functions.NaturalComparator[]{var0};
      }

      @Override
      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }
   }

   static final class NaturalObjectComparator implements Comparator<Object> {
      @Override
      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }
   }

   static final class NotificationOnComplete<T> implements Action {
      final Consumer<? super Notification<T>> onNotification;

      NotificationOnComplete(Consumer<? super Notification<T>> var1) {
         this.onNotification = var1;
      }

      @Override
      public void run() throws Exception {
         this.onNotification.accept(Notification.createOnComplete());
      }
   }

   static final class NotificationOnError<T> implements Consumer<Throwable> {
      final Consumer<? super Notification<T>> onNotification;

      NotificationOnError(Consumer<? super Notification<T>> var1) {
         this.onNotification = var1;
      }

      public void accept(Throwable var1) throws Exception {
         this.onNotification.accept(Notification.createOnError(var1));
      }
   }

   static final class NotificationOnNext<T> implements Consumer<T> {
      final Consumer<? super Notification<T>> onNotification;

      NotificationOnNext(Consumer<? super Notification<T>> var1) {
         this.onNotification = var1;
      }

      @Override
      public void accept(T var1) throws Exception {
         this.onNotification.accept(Notification.createOnNext((T)var1));
      }
   }

   static final class NullCallable implements Callable<Object> {
      @Override
      public Object call() {
         return null;
      }
   }

   static final class OnErrorMissingConsumer implements Consumer<Throwable> {
      public void accept(Throwable var1) {
         RxJavaPlugins.onError(new OnErrorNotImplementedException(var1));
      }
   }

   static final class TimestampFunction<T> implements Function<T, Timed<T>> {
      final Scheduler scheduler;
      final TimeUnit unit;

      TimestampFunction(TimeUnit var1, Scheduler var2) {
         this.unit = var1;
         this.scheduler = var2;
      }

      public Timed<T> apply(T var1) throws Exception {
         return new Timed<>((T)var1, this.scheduler.now(this.unit), this.unit);
      }
   }

   static final class ToMapKeySelector<K, T> implements BiConsumer<Map<K, T>, T> {
      private final Function<? super T, ? extends K> keySelector;

      ToMapKeySelector(Function<? super T, ? extends K> var1) {
         this.keySelector = var1;
      }

      public void accept(Map<K, T> var1, T var2) throws Exception {
         var1.put((K)this.keySelector.apply((T)var2), var2);
      }
   }

   static final class ToMapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, V>, T> {
      private final Function<? super T, ? extends K> keySelector;
      private final Function<? super T, ? extends V> valueSelector;

      ToMapKeyValueSelector(Function<? super T, ? extends V> var1, Function<? super T, ? extends K> var2) {
         this.valueSelector = var1;
         this.keySelector = var2;
      }

      public void accept(Map<K, V> var1, T var2) throws Exception {
         var1.put((K)this.keySelector.apply((T)var2), (V)this.valueSelector.apply((T)var2));
      }
   }

   static final class ToMultimapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
      private final Function<? super K, ? extends Collection<? super V>> collectionFactory;
      private final Function<? super T, ? extends K> keySelector;
      private final Function<? super T, ? extends V> valueSelector;

      ToMultimapKeyValueSelector(
         Function<? super K, ? extends Collection<? super V>> var1, Function<? super T, ? extends V> var2, Function<? super T, ? extends K> var3
      ) {
         this.collectionFactory = var1;
         this.valueSelector = var2;
         this.keySelector = var3;
      }

      public void accept(Map<K, Collection<V>> var1, T var2) throws Exception {
         Object var5 = this.keySelector.apply((T)var2);
         Collection var4 = (Collection)var1.get(var5);
         Collection var3 = var4;
         if (var4 == null) {
            var3 = this.collectionFactory.apply((K)var5);
            var1.put(var5, var3);
         }

         var3.add(this.valueSelector.apply((T)var2));
      }
   }

   static final class TruePredicate implements Predicate<Object> {
      @Override
      public boolean test(Object var1) {
         return true;
      }
   }
}
