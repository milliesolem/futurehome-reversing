package io.reactivex.disposables;

import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Future;
import org.reactivestreams.Subscription;

public final class Disposables {
   private Disposables() {
      throw new IllegalStateException("No instances!");
   }

   public static Disposable disposed() {
      return EmptyDisposable.INSTANCE;
   }

   public static Disposable empty() {
      return fromRunnable(Functions.EMPTY_RUNNABLE);
   }

   public static Disposable fromAction(Action var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return new ActionDisposable(var0);
   }

   public static Disposable fromFuture(Future<?> var0) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return fromFuture(var0, true);
   }

   public static Disposable fromFuture(Future<?> var0, boolean var1) {
      ObjectHelper.requireNonNull(var0, "future is null");
      return new FutureDisposable(var0, var1);
   }

   public static Disposable fromRunnable(Runnable var0) {
      ObjectHelper.requireNonNull(var0, "run is null");
      return new RunnableDisposable(var0);
   }

   public static Disposable fromSubscription(Subscription var0) {
      ObjectHelper.requireNonNull(var0, "subscription is null");
      return new SubscriptionDisposable(var0);
   }
}
