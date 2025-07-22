package kotlin.concurrent

import java.util.Date
import java.util.Timer
import java.util.TimerTask
import kotlin.jvm.functions.Function1

public inline fun fixedRateTimer(name: String? = null, daemon: Boolean = false, initialDelay: Long = 0L, period: Long, crossinline action: (TimerTask) -> Unit): Timer {
   val var7: Timer = timer(var0, var1);
   var7.scheduleAtFixedRate(new TimerTask(var6) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var4);
   return var7;
}

public inline fun fixedRateTimer(name: String? = null, daemon: Boolean = false, startAt: Date, period: Long, crossinline action: (TimerTask) -> Unit): Timer {
   val var6: Timer = timer(var0, var1);
   var6.scheduleAtFixedRate(new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var3);
   return var6;
}

@JvmSynthetic
fun `fixedRateTimer$default`(var0: java.lang.String, var1: Boolean, var2: Long, var4: Long, var6: Function1, var7: Int, var8: Any): Timer {
   if ((var7 and 1) != 0) {
      var0 = null;
   }

   if ((var7 and 2) != 0) {
      var1 = false;
   }

   if ((var7 and 4) != 0) {
      var2 = 0L;
   }

   val var9: Timer = timer(var0, var1);
   var9.scheduleAtFixedRate(new TimerTask(var6) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var4);
   return var9;
}

@JvmSynthetic
fun `fixedRateTimer$default`(var0: java.lang.String, var1: Boolean, var2: Date, var3: Long, var5: Function1, var6: Int, var7: Any): Timer {
   if ((var6 and 1) != 0) {
      var0 = null;
   }

   if ((var6 and 2) != 0) {
      var1 = false;
   }

   val var8: Timer = timer(var0, var1);
   var8.scheduleAtFixedRate(new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var3);
   return var8;
}

public inline fun Timer.schedule(delay: Long, period: Long, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var6: TimerTask = new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.schedule(var6, var1, var3);
   return var6;
}

public inline fun Timer.schedule(delay: Long, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var4: TimerTask = new TimerTask(var3) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.schedule(var4, var1);
   return var4;
}

public inline fun Timer.schedule(time: Date, period: Long, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var5: TimerTask = new TimerTask(var4) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.schedule(var5, var1, var2);
   return var5;
}

public inline fun Timer.schedule(time: Date, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var3: TimerTask = new TimerTask(var2) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.schedule(var3, var1);
   return var3;
}

public inline fun Timer.scheduleAtFixedRate(delay: Long, period: Long, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var6: TimerTask = new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.scheduleAtFixedRate(var6, var1, var3);
   return var6;
}

public inline fun Timer.scheduleAtFixedRate(time: Date, period: Long, crossinline action: (TimerTask) -> Unit): TimerTask {
   val var5: TimerTask = new TimerTask(var4) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
   var0.scheduleAtFixedRate(var5, var1, var2);
   return var5;
}

internal fun timer(name: String?, daemon: Boolean): Timer {
   val var2: Timer;
   if (var0 == null) {
      var2 = new Timer(var1);
   } else {
      var2 = new Timer(var0, var1);
   }

   return var2;
}

public inline fun timer(name: String? = null, daemon: Boolean = false, initialDelay: Long = 0L, period: Long, crossinline action: (TimerTask) -> Unit): Timer {
   val var7: Timer = timer(var0, var1);
   var7.schedule(new TimerTask(var6) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var4);
   return var7;
}

public inline fun timer(name: String? = null, daemon: Boolean = false, startAt: Date, period: Long, crossinline action: (TimerTask) -> Unit): Timer {
   val var6: Timer = timer(var0, var1);
   var6.schedule(new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var3);
   return var6;
}

@JvmSynthetic
fun `timer$default`(var0: java.lang.String, var1: Boolean, var2: Long, var4: Long, var6: Function1, var7: Int, var8: Any): Timer {
   if ((var7 and 1) != 0) {
      var0 = null;
   }

   if ((var7 and 2) != 0) {
      var1 = false;
   }

   if ((var7 and 4) != 0) {
      var2 = 0L;
   }

   val var9: Timer = timer(var0, var1);
   var9.schedule(new TimerTask(var6) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var4);
   return var9;
}

@JvmSynthetic
fun `timer$default`(var0: java.lang.String, var1: Boolean, var2: Date, var3: Long, var5: Function1, var6: Int, var7: Any): Timer {
   if ((var6 and 1) != 0) {
      var0 = null;
   }

   if ((var6 and 2) != 0) {
      var1 = false;
   }

   val var8: Timer = timer(var0, var1);
   var8.schedule(new TimerTask(var5) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   }, var2, var3);
   return var8;
}

public inline fun timerTask(crossinline action: (TimerTask) -> Unit): TimerTask {
   return new TimerTask(var0) {
      final Function1<TimerTask, Unit> $action;

      {
         this.$action = var1;
      }

      @Override
      public void run() {
         this.$action.invoke(this);
      }
   };
}
