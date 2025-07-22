package io.reactivex.observers;

import io.reactivex.Notification;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.VolatileSizeArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BaseTestConsumer<T, U extends BaseTestConsumer<T, U>> implements Disposable {
   protected boolean checkSubscriptionOnce;
   protected long completions;
   protected final CountDownLatch done;
   protected final List<Throwable> errors;
   protected int establishedFusionMode;
   protected int initialFusionMode;
   protected Thread lastThread;
   protected CharSequence tag;
   protected boolean timeout;
   protected final List<T> values = new VolatileSizeArrayList<>();

   public BaseTestConsumer() {
      this.errors = new VolatileSizeArrayList<>();
      this.done = new CountDownLatch(1);
   }

   public static String valueAndClass(Object var0) {
      if (var0 != null) {
         StringBuilder var1 = new StringBuilder();
         var1.append(var0);
         var1.append(" (class: ");
         var1.append(var0.getClass().getSimpleName());
         var1.append(")");
         return var1.toString();
      } else {
         return "null";
      }
   }

   public final U assertComplete() {
      long var1 = this.completions;
      if (var1 != 0L) {
         if (var1 <= 1L) {
            return (U)this;
         } else {
            StringBuilder var3 = new StringBuilder("Multiple completions: ");
            var3.append(var1);
            throw this.fail(var3.toString());
         }
      } else {
         throw this.fail("Not completed");
      }
   }

   public final U assertEmpty() {
      return this.assertSubscribed().assertNoValues().assertNoErrors().assertNotComplete();
   }

   public final U assertError(Predicate<Throwable> var1) {
      int var2 = this.errors.size();
      if (var2 == 0) {
         throw this.fail("No errors");
      } else {
         for (Throwable var4 : this.errors) {
            boolean var3;
            try {
               var3 = var1.test(var4);
            } catch (Exception var6) {
               throw ExceptionHelper.wrapOrThrow(var6);
            }

            if (var3) {
               if (var2 == 1) {
                  return (U)this;
               }

               throw this.fail("Error present but other errors as well");
            }
         }

         throw this.fail("Error not present");
      }
   }

   public final U assertError(Class<? extends Throwable> var1) {
      return this.assertError(Functions.isInstanceOf(var1));
   }

   public final U assertError(Throwable var1) {
      return this.assertError(Functions.equalsWith(var1));
   }

   public final U assertErrorMessage(String var1) {
      int var2 = this.errors.size();
      if (var2 != 0) {
         if (var2 == 1) {
            String var4 = this.errors.get(0).getMessage();
            if (ObjectHelper.equals(var1, var4)) {
               return (U)this;
            } else {
               StringBuilder var3 = new StringBuilder("Error message differs; exptected: ");
               var3.append(var1);
               var3.append(" but was: ");
               var3.append(var4);
               throw this.fail(var3.toString());
            }
         } else {
            throw this.fail("Multiple errors");
         }
      } else {
         throw this.fail("No errors");
      }
   }

   public final U assertFailure(Predicate<Throwable> var1, T... var2) {
      return this.assertSubscribed().assertValues((T[])var2).assertError(var1).assertNotComplete();
   }

   public final U assertFailure(Class<? extends Throwable> var1, T... var2) {
      return this.assertSubscribed().assertValues((T[])var2).assertError(var1).assertNotComplete();
   }

   public final U assertFailureAndMessage(Class<? extends Throwable> var1, String var2, T... var3) {
      return this.assertSubscribed().assertValues((T[])var3).assertError(var1).assertErrorMessage(var2).assertNotComplete();
   }

   public final U assertNever(Predicate<? super T> var1) {
      int var3 = this.values.size();

      for (int var2 = 0; var2 < var3; var2++) {
         StringBuilder var4 = this.values.get(var2);

         try {
            if (var1.test(var4)) {
               var4 = new StringBuilder();
               var4.append("Value at position ");
               var4.append(var2);
               var4.append(" matches predicate ");
               var4.append(var1.toString());
               var4.append(", which was not expected.");
               throw this.fail(var4.toString());
            }
         } catch (Exception var5) {
            throw ExceptionHelper.wrapOrThrow(var5);
         }
      }

      return (U)this;
   }

   public final U assertNever(T var1) {
      int var3 = this.values.size();

      for (int var2 = 0; var2 < var3; var2++) {
         if (ObjectHelper.equals(this.values.get(var2), var1)) {
            StringBuilder var4 = new StringBuilder("Value at position ");
            var4.append(var2);
            var4.append(" is equal to ");
            var4.append(valueAndClass(var1));
            var4.append("; Expected them to be different");
            throw this.fail(var4.toString());
         }
      }

      return (U)this;
   }

   public final U assertNoErrors() {
      if (this.errors.size() == 0) {
         return (U)this;
      } else {
         StringBuilder var1 = new StringBuilder("Error(s) present: ");
         var1.append(this.errors);
         throw this.fail(var1.toString());
      }
   }

   public final U assertNoTimeout() {
      if (!this.timeout) {
         return (U)this;
      } else {
         throw this.fail("Timeout?!");
      }
   }

   public final U assertNoValues() {
      return this.assertValueCount(0);
   }

   public final U assertNotComplete() {
      long var2 = this.completions;
      long var5;
      int var1 = (var5 = var2 - 1L) == 0L ? 0 : (var5 < 0L ? -1 : 1);
      if (var1 != 0) {
         if (var1 <= 0) {
            return (U)this;
         } else {
            StringBuilder var4 = new StringBuilder("Multiple completions: ");
            var4.append(var2);
            throw this.fail(var4.toString());
         }
      } else {
         throw this.fail("Completed!");
      }
   }

   public abstract U assertNotSubscribed();

   public final U assertNotTerminated() {
      if (this.done.getCount() != 0L) {
         return (U)this;
      } else {
         throw this.fail("Subscriber terminated!");
      }
   }

   public final U assertResult(T... var1) {
      return this.assertSubscribed().assertValues((T[])var1).assertNoErrors().assertComplete();
   }

   public abstract U assertSubscribed();

   public final U assertTerminated() {
      if (this.done.getCount() == 0L) {
         long var2 = this.completions;
         if (var2 <= 1L) {
            int var1 = this.errors.size();
            if (var1 <= 1) {
               if (var2 != 0L && var1 != 0) {
                  StringBuilder var6 = new StringBuilder("Terminated with multiple completions and errors: ");
                  var6.append(var2);
                  throw this.fail(var6.toString());
               } else {
                  return (U)this;
               }
            } else {
               StringBuilder var5 = new StringBuilder("Terminated with multiple errors: ");
               var5.append(var1);
               throw this.fail(var5.toString());
            }
         } else {
            StringBuilder var4 = new StringBuilder("Terminated with multiple completions: ");
            var4.append(var2);
            throw this.fail(var4.toString());
         }
      } else {
         throw this.fail("Subscriber still running!");
      }
   }

   public final U assertTimeout() {
      if (this.timeout) {
         return (U)this;
      } else {
         throw this.fail("No timeout?!");
      }
   }

   public final U assertValue(Predicate<T> var1) {
      this.assertValueAt(0, var1);
      if (this.values.size() <= 1) {
         return (U)this;
      } else {
         throw this.fail("Value present but other values as well");
      }
   }

   public final U assertValue(T var1) {
      if (this.values.size() == 1) {
         Object var3 = this.values.get(0);
         if (ObjectHelper.equals(var1, var3)) {
            return (U)this;
         } else {
            StringBuilder var4 = new StringBuilder("expected: ");
            var4.append(valueAndClass(var1));
            var4.append(" but was: ");
            var4.append(valueAndClass(var3));
            throw this.fail(var4.toString());
         }
      } else {
         StringBuilder var2 = new StringBuilder("expected: ");
         var2.append(valueAndClass(var1));
         var2.append(" but was: ");
         var2.append(this.values);
         throw this.fail(var2.toString());
      }
   }

   public final U assertValueAt(int var1, Predicate<T> var2) {
      if (this.values.size() != 0) {
         if (var1 < this.values.size()) {
            boolean var3;
            try {
               var3 = var2.test(this.values.get(var1));
            } catch (Exception var4) {
               throw ExceptionHelper.wrapOrThrow(var4);
            }

            if (var3) {
               return (U)this;
            } else {
               throw this.fail("Value not present");
            }
         } else {
            StringBuilder var5 = new StringBuilder("Invalid index: ");
            var5.append(var1);
            throw this.fail(var5.toString());
         }
      } else {
         throw this.fail("No values");
      }
   }

   public final U assertValueAt(int var1, T var2) {
      int var3 = this.values.size();
      if (var3 != 0) {
         if (var1 < var3) {
            Object var5 = this.values.get(var1);
            if (ObjectHelper.equals(var2, var5)) {
               return (U)this;
            } else {
               StringBuilder var4 = new StringBuilder("expected: ");
               var4.append(valueAndClass(var2));
               var4.append(" but was: ");
               var4.append(valueAndClass(var5));
               throw this.fail(var4.toString());
            }
         } else {
            var2 = new StringBuilder("Invalid index: ");
            var2.append(var1);
            throw this.fail(var2.toString());
         }
      } else {
         throw this.fail("No values");
      }
   }

   public final U assertValueCount(int var1) {
      int var2 = this.values.size();
      if (var2 == var1) {
         return (U)this;
      } else {
         StringBuilder var3 = new StringBuilder("Value counts differ; expected: ");
         var3.append(var1);
         var3.append(" but was: ");
         var3.append(var2);
         throw this.fail(var3.toString());
      }
   }

   public final U assertValueSequence(Iterable<? extends T> var1) {
      Iterator var6 = this.values.iterator();
      Iterator var7 = var1.iterator();
      int var2 = 0;

      while (true) {
         boolean var3 = var7.hasNext();
         boolean var4 = var6.hasNext();
         if (!var4 || !var3) {
            if (!var4) {
               if (!var3) {
                  return (U)this;
               } else {
                  StringBuilder var10 = new StringBuilder("Fewer values received than expected (");
                  var10.append(var2);
                  var10.append(")");
                  throw this.fail(var10.toString());
               }
            } else {
               StringBuilder var9 = new StringBuilder("More values received than expected (");
               var9.append(var2);
               var9.append(")");
               throw this.fail(var9.toString());
            }
         }

         Object var5 = var7.next();
         Object var8 = var6.next();
         if (!ObjectHelper.equals(var5, var8)) {
            StringBuilder var11 = new StringBuilder("Values at position ");
            var11.append(var2);
            var11.append(" differ; expected: ");
            var11.append(valueAndClass(var5));
            var11.append(" but was: ");
            var11.append(valueAndClass(var8));
            throw this.fail(var11.toString());
         }

         var2++;
      }
   }

   public final U assertValueSequenceOnly(Iterable<? extends T> var1) {
      return this.assertSubscribed().assertValueSequence(var1).assertNoErrors().assertNotComplete();
   }

   public final U assertValueSet(Collection<? extends T> var1) {
      if (var1.isEmpty()) {
         this.assertNoValues();
         return (U)this;
      } else {
         for (Object var2 : this.values) {
            if (!var1.contains(var2)) {
               StringBuilder var4 = new StringBuilder("Value not in the expected collection: ");
               var4.append(valueAndClass(var2));
               throw this.fail(var4.toString());
            }
         }

         return (U)this;
      }
   }

   public final U assertValueSetOnly(Collection<? extends T> var1) {
      return this.assertSubscribed().assertValueSet(var1).assertNoErrors().assertNotComplete();
   }

   public final U assertValues(T... var1) {
      int var3 = this.values.size();
      if (var3 == var1.length) {
         for (int var2 = 0; var2 < var3; var2++) {
            Object var7 = this.values.get(var2);
            Object var5 = var1[var2];
            if (!ObjectHelper.equals(var5, var7)) {
               StringBuilder var6 = new StringBuilder("Values at position ");
               var6.append(var2);
               var6.append(" differ; expected: ");
               var6.append(valueAndClass(var5));
               var6.append(" but was: ");
               var6.append(valueAndClass(var7));
               throw this.fail(var6.toString());
            }
         }

         return (U)this;
      } else {
         StringBuilder var4 = new StringBuilder("Value count differs; expected: ");
         var4.append(var1.length);
         var4.append(" ");
         var4.append(Arrays.toString(var1));
         var4.append(" but was: ");
         var4.append(var3);
         var4.append(" ");
         var4.append(this.values);
         throw this.fail(var4.toString());
      }
   }

   public final U assertValuesOnly(T... var1) {
      return this.assertSubscribed().assertValues((T[])var1).assertNoErrors().assertNotComplete();
   }

   public final U await() throws InterruptedException {
      if (this.done.getCount() == 0L) {
         return (U)this;
      } else {
         this.done.await();
         return (U)this;
      }
   }

   public final boolean await(long var1, TimeUnit var3) throws InterruptedException {
      boolean var4;
      if (this.done.getCount() != 0L && !this.done.await(var1, var3)) {
         var4 = false;
      } else {
         var4 = true;
      }

      this.timeout = var4 ^ true;
      return var4;
   }

   public final U awaitCount(int var1) {
      return this.awaitCount(var1, BaseTestConsumer.TestWaitStrategy.SLEEP_10MS, 5000L);
   }

   public final U awaitCount(int var1, Runnable var2) {
      return this.awaitCount(var1, var2, 5000L);
   }

   public final U awaitCount(int var1, Runnable var2, long var3) {
      long var5 = System.currentTimeMillis();

      while (true) {
         if (var3 > 0L && System.currentTimeMillis() - var5 >= var3) {
            this.timeout = true;
            break;
         }

         if (this.done.getCount() == 0L || this.values.size() >= var1) {
            break;
         }

         var2.run();
      }

      return (U)this;
   }

   public final U awaitDone(long var1, TimeUnit var3) {
      try {
         if (!this.done.await(var1, var3)) {
            this.timeout = true;
            this.dispose();
         }

         return (U)this;
      } catch (InterruptedException var4) {
         this.dispose();
         throw ExceptionHelper.wrapOrThrow(var4);
      }
   }

   public final boolean awaitTerminalEvent() {
      try {
         this.await();
         return true;
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         return false;
      }
   }

   public final boolean awaitTerminalEvent(long var1, TimeUnit var3) {
      try {
         return this.await(var1, var3);
      } catch (InterruptedException var5) {
         Thread.currentThread().interrupt();
         return false;
      }
   }

   public final U clearTimeout() {
      this.timeout = false;
      return (U)this;
   }

   public final long completions() {
      return this.completions;
   }

   public final int errorCount() {
      return this.errors.size();
   }

   public final List<Throwable> errors() {
      return this.errors;
   }

   protected final AssertionError fail(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length() + 64);
      var2.append(var1);
      var2.append(" (latch = ");
      var2.append(this.done.getCount());
      var2.append(", values = ");
      var2.append(this.values.size());
      var2.append(", errors = ");
      var2.append(this.errors.size());
      var2.append(", completions = ");
      var2.append(this.completions);
      if (this.timeout) {
         var2.append(", timeout!");
      }

      if (this.isDisposed()) {
         var2.append(", disposed!");
      }

      CharSequence var3 = this.tag;
      if (var3 != null) {
         var2.append(", tag = ");
         var2.append(var3);
      }

      var2.append(')');
      AssertionError var4 = new AssertionError(var2.toString());
      if (!this.errors.isEmpty()) {
         if (this.errors.size() == 1) {
            var4.initCause(this.errors.get(0));
         } else {
            var4.initCause(new CompositeException(this.errors));
         }
      }

      return var4;
   }

   public final List<List<Object>> getEvents() {
      ArrayList var3 = new ArrayList();
      var3.add(this.values());
      var3.add(this.errors());
      ArrayList var4 = new ArrayList();

      for (long var1 = 0L; var1 < this.completions; var1++) {
         var4.add(Notification.createOnComplete());
      }

      var3.add(var4);
      return var3;
   }

   public final boolean isTerminated() {
      boolean var1;
      if (this.done.getCount() == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isTimeout() {
      return this.timeout;
   }

   public final Thread lastThread() {
      return this.lastThread;
   }

   public final int valueCount() {
      return this.values.size();
   }

   public final List<T> values() {
      return this.values;
   }

   public final U withTag(CharSequence var1) {
      this.tag = var1;
      return (U)this;
   }

   public static enum TestWaitStrategy implements Runnable {
      SLEEP_1000MS,
      SLEEP_100MS,
      SLEEP_10MS,
      SLEEP_1MS,
      SPIN,
      YIELD;
      private static final BaseTestConsumer.TestWaitStrategy[] $VALUES;

      // $VF: Failed to inline enum fields
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      static {
         BaseTestConsumer.TestWaitStrategy var1 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
            }
         };
         SPIN = var1;
         BaseTestConsumer.TestWaitStrategy var5 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
               Thread.yield();
            }
         };
         YIELD = var5;
         BaseTestConsumer.TestWaitStrategy var0 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
               sleep(1);
            }
         };
         SLEEP_1MS = var0;
         BaseTestConsumer.TestWaitStrategy var2 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
               sleep(10);
            }
         };
         SLEEP_10MS = var2;
         BaseTestConsumer.TestWaitStrategy var3 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
               sleep(100);
            }
         };
         SLEEP_100MS = var3;
         BaseTestConsumer.TestWaitStrategy var4 = new BaseTestConsumer.TestWaitStrategy() {
            @Override
            public void run() {
               sleep(1000);
            }
         };
         SLEEP_1000MS = var4;
         $VALUES = new BaseTestConsumer.TestWaitStrategy[]{var1, var5, var0, var2, var3, var4};
      }

      private TestWaitStrategy() {
      }

      static void sleep(int var0) {
         long var1 = var0;

         try {
            Thread.sleep(var1);
         } catch (InterruptedException var4) {
            throw new RuntimeException(var4);
         }
      }

      @Override
      public abstract void run();
   }
}
