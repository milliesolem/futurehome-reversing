package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SchedulerPoolFactory;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public final class Schedulers {
   static final Scheduler COMPUTATION = RxJavaPlugins.initComputationScheduler(new Schedulers.ComputationTask());
   static final Scheduler IO = RxJavaPlugins.initIoScheduler(new Schedulers.IOTask());
   static final Scheduler NEW_THREAD = RxJavaPlugins.initNewThreadScheduler(new Schedulers.NewThreadTask());
   static final Scheduler SINGLE = RxJavaPlugins.initSingleScheduler(new Schedulers.SingleTask());
   static final Scheduler TRAMPOLINE = TrampolineScheduler.instance();

   private Schedulers() {
      throw new IllegalStateException("No instances!");
   }

   public static Scheduler computation() {
      return RxJavaPlugins.onComputationScheduler(COMPUTATION);
   }

   public static Scheduler from(Executor var0) {
      return new ExecutorScheduler(var0, false);
   }

   public static Scheduler from(Executor var0, boolean var1) {
      return new ExecutorScheduler(var0, var1);
   }

   public static Scheduler io() {
      return RxJavaPlugins.onIoScheduler(IO);
   }

   public static Scheduler newThread() {
      return RxJavaPlugins.onNewThreadScheduler(NEW_THREAD);
   }

   public static void shutdown() {
      computation().shutdown();
      io().shutdown();
      newThread().shutdown();
      single().shutdown();
      trampoline().shutdown();
      SchedulerPoolFactory.shutdown();
   }

   public static Scheduler single() {
      return RxJavaPlugins.onSingleScheduler(SINGLE);
   }

   public static void start() {
      computation().start();
      io().start();
      newThread().start();
      single().start();
      trampoline().start();
      SchedulerPoolFactory.start();
   }

   public static Scheduler trampoline() {
      return TRAMPOLINE;
   }

   static final class ComputationHolder {
      static final Scheduler DEFAULT = new ComputationScheduler();
   }

   static final class ComputationTask implements Callable<Scheduler> {
      public Scheduler call() throws Exception {
         return Schedulers.ComputationHolder.DEFAULT;
      }
   }

   static final class IOTask implements Callable<Scheduler> {
      public Scheduler call() throws Exception {
         return Schedulers.IoHolder.DEFAULT;
      }
   }

   static final class IoHolder {
      static final Scheduler DEFAULT = new IoScheduler();
   }

   static final class NewThreadHolder {
      static final Scheduler DEFAULT = new NewThreadScheduler();
   }

   static final class NewThreadTask implements Callable<Scheduler> {
      public Scheduler call() throws Exception {
         return Schedulers.NewThreadHolder.DEFAULT;
      }
   }

   static final class SingleHolder {
      static final Scheduler DEFAULT = new SingleScheduler();
   }

   static final class SingleTask implements Callable<Scheduler> {
      public Scheduler call() throws Exception {
         return Schedulers.SingleHolder.DEFAULT;
      }
   }
}
