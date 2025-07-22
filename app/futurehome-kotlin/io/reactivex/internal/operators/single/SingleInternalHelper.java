package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;

public final class SingleInternalHelper {
   private SingleInternalHelper() {
      throw new IllegalStateException("No instances!");
   }

   public static <T> Callable<NoSuchElementException> emptyThrower() {
      return SingleInternalHelper.NoSuchElementCallable.INSTANCE;
   }

   public static <T> Iterable<? extends Flowable<T>> iterableToFlowable(Iterable<? extends SingleSource<? extends T>> var0) {
      return new SingleInternalHelper.ToFlowableIterable<>(var0);
   }

   public static <T> Function<SingleSource<? extends T>, Publisher<? extends T>> toFlowable() {
      return SingleInternalHelper.ToFlowable.INSTANCE;
   }

   public static <T> Function<SingleSource<? extends T>, Observable<? extends T>> toObservable() {
      return SingleInternalHelper.ToObservable.INSTANCE;
   }

   static enum NoSuchElementCallable implements Callable<NoSuchElementException> {
      INSTANCE;
      private static final SingleInternalHelper.NoSuchElementCallable[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         SingleInternalHelper.NoSuchElementCallable var0 = new SingleInternalHelper.NoSuchElementCallable();
         INSTANCE = var0;
         $VALUES = new SingleInternalHelper.NoSuchElementCallable[]{var0};
      }

      public NoSuchElementException call() throws Exception {
         return new NoSuchElementException();
      }
   }

   static enum ToFlowable implements Function<SingleSource, Publisher> {
      INSTANCE;
      private static final SingleInternalHelper.ToFlowable[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         SingleInternalHelper.ToFlowable var0 = new SingleInternalHelper.ToFlowable();
         INSTANCE = var0;
         $VALUES = new SingleInternalHelper.ToFlowable[]{var0};
      }

      public Publisher apply(SingleSource var1) {
         return new SingleToFlowable(var1);
      }
   }

   static final class ToFlowableIterable<T> implements Iterable<Flowable<T>> {
      private final Iterable<? extends SingleSource<? extends T>> sources;

      ToFlowableIterable(Iterable<? extends SingleSource<? extends T>> var1) {
         this.sources = var1;
      }

      @Override
      public Iterator<Flowable<T>> iterator() {
         return new SingleInternalHelper.ToFlowableIterator<>(this.sources.iterator());
      }
   }

   static final class ToFlowableIterator<T> implements Iterator<Flowable<T>> {
      private final Iterator<? extends SingleSource<? extends T>> sit;

      ToFlowableIterator(Iterator<? extends SingleSource<? extends T>> var1) {
         this.sit = var1;
      }

      @Override
      public boolean hasNext() {
         return this.sit.hasNext();
      }

      public Flowable<T> next() {
         return new SingleToFlowable<>((SingleSource<? extends T>)this.sit.next());
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   static enum ToObservable implements Function<SingleSource, Observable> {
      INSTANCE;
      private static final SingleInternalHelper.ToObservable[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         SingleInternalHelper.ToObservable var0 = new SingleInternalHelper.ToObservable();
         INSTANCE = var0;
         $VALUES = new SingleInternalHelper.ToObservable[]{var0};
      }

      public Observable apply(SingleSource var1) {
         return new SingleToObservable(var1);
      }
   }
}
