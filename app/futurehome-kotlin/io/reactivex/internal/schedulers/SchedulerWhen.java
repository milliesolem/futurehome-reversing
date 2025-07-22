package io.reactivex.internal.schedulers;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SchedulerWhen extends Scheduler implements Disposable {
   static final Disposable DISPOSED = Disposables.disposed();
   static final Disposable SUBSCRIBED = new SchedulerWhen.SubscribedDisposable();
   private final Scheduler actualScheduler;
   private Disposable disposable;
   private final FlowableProcessor<Flowable<Completable>> workerProcessor;

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> var1, Scheduler var2) {
      this.actualScheduler = var2;
      FlowableProcessor var5 = UnicastProcessor.create().toSerialized();
      this.workerProcessor = var5;

      try {
         this.disposable = ((Completable)var1.apply(var5)).subscribe();
      } catch (Throwable var4) {
         throw ExceptionHelper.wrapOrThrow(var4);
      }
   }

   @Override
   public Scheduler.Worker createWorker() {
      Scheduler.Worker var2 = this.actualScheduler.createWorker();
      FlowableProcessor var3 = UnicastProcessor.create().toSerialized();
      Flowable var1 = var3.map(new SchedulerWhen.CreateWorkerFunction(var2));
      SchedulerWhen.QueueWorker var4 = new SchedulerWhen.QueueWorker(var3, var2);
      this.workerProcessor.onNext(var1);
      return var4;
   }

   @Override
   public void dispose() {
      this.disposable.dispose();
   }

   @Override
   public boolean isDisposed() {
      return this.disposable.isDisposed();
   }

   static final class CreateWorkerFunction implements Function<SchedulerWhen.ScheduledAction, Completable> {
      final Scheduler.Worker actualWorker;

      CreateWorkerFunction(Scheduler.Worker var1) {
         this.actualWorker = var1;
      }

      public Completable apply(SchedulerWhen.ScheduledAction var1) {
         return new SchedulerWhen.CreateWorkerFunction.WorkerCompletable(this, var1);
      }

      final class WorkerCompletable extends Completable {
         final SchedulerWhen.ScheduledAction action;
         final SchedulerWhen.CreateWorkerFunction this$0;

         WorkerCompletable(SchedulerWhen.CreateWorkerFunction var1, SchedulerWhen.ScheduledAction var2) {
            this.this$0 = var1;
            this.action = var2;
         }

         @Override
         protected void subscribeActual(CompletableObserver var1) {
            var1.onSubscribe(this.action);
            this.action.call(this.this$0.actualWorker, var1);
         }
      }
   }

   static class DelayedAction extends SchedulerWhen.ScheduledAction {
      private final Runnable action;
      private final long delayTime;
      private final TimeUnit unit;

      DelayedAction(Runnable var1, long var2, TimeUnit var4) {
         this.action = var1;
         this.delayTime = var2;
         this.unit = var4;
      }

      @Override
      protected Disposable callActual(Scheduler.Worker var1, CompletableObserver var2) {
         return var1.schedule(new SchedulerWhen.OnCompletedAction(this.action, var2), this.delayTime, this.unit);
      }
   }

   static class ImmediateAction extends SchedulerWhen.ScheduledAction {
      private final Runnable action;

      ImmediateAction(Runnable var1) {
         this.action = var1;
      }

      @Override
      protected Disposable callActual(Scheduler.Worker var1, CompletableObserver var2) {
         return var1.schedule(new SchedulerWhen.OnCompletedAction(this.action, var2));
      }
   }

   static class OnCompletedAction implements Runnable {
      final Runnable action;
      final CompletableObserver actionCompletable;

      OnCompletedAction(Runnable var1, CompletableObserver var2) {
         this.action = var1;
         this.actionCompletable = var2;
      }

      @Override
      public void run() {
         try {
            this.action.run();
         } finally {
            this.actionCompletable.onComplete();
         }
      }
   }

   static final class QueueWorker extends Scheduler.Worker {
      private final FlowableProcessor<SchedulerWhen.ScheduledAction> actionProcessor;
      private final Scheduler.Worker actualWorker;
      private final AtomicBoolean unsubscribed;

      QueueWorker(FlowableProcessor<SchedulerWhen.ScheduledAction> var1, Scheduler.Worker var2) {
         this.actionProcessor = var1;
         this.actualWorker = var2;
         this.unsubscribed = new AtomicBoolean();
      }

      @Override
      public void dispose() {
         if (this.unsubscribed.compareAndSet(false, true)) {
            this.actionProcessor.onComplete();
            this.actualWorker.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.unsubscribed.get();
      }

      @Override
      public Disposable schedule(Runnable var1) {
         SchedulerWhen.ImmediateAction var2 = new SchedulerWhen.ImmediateAction(var1);
         this.actionProcessor.onNext(var2);
         return var2;
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         SchedulerWhen.DelayedAction var5 = new SchedulerWhen.DelayedAction(var1, var2, var4);
         this.actionProcessor.onNext(var5);
         return var5;
      }
   }

   abstract static class ScheduledAction extends AtomicReference<Disposable> implements Disposable {
      ScheduledAction() {
         super(SchedulerWhen.SUBSCRIBED);
      }

      void call(Scheduler.Worker var1, CompletableObserver var2) {
         Disposable var3 = this.get();
         if (var3 != SchedulerWhen.DISPOSED) {
            if (var3 == SchedulerWhen.SUBSCRIBED) {
               Disposable var4 = this.callActual(var1, var2);
               if (!this.compareAndSet(SchedulerWhen.SUBSCRIBED, var4)) {
                  var4.dispose();
               }
            }
         }
      }

      protected abstract Disposable callActual(Scheduler.Worker var1, CompletableObserver var2);

      @Override
      public void dispose() {
         Disposable var1 = SchedulerWhen.DISPOSED;

         Disposable var2;
         do {
            var2 = this.get();
            if (var2 == SchedulerWhen.DISPOSED) {
               return;
            }
         } while (!this.compareAndSet(var2, var1));

         if (var2 != SchedulerWhen.SUBSCRIBED) {
            var2.dispose();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get().isDisposed();
      }
   }

   static final class SubscribedDisposable implements Disposable {
      @Override
      public void dispose() {
      }

      @Override
      public boolean isDisposed() {
         return false;
      }
   }
}
