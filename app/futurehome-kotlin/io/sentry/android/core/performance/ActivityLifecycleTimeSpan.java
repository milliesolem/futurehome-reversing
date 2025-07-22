package io.sentry.android.core.performance;

public class ActivityLifecycleTimeSpan implements Comparable<ActivityLifecycleTimeSpan> {
   private final TimeSpan onCreate = new TimeSpan();
   private final TimeSpan onStart = new TimeSpan();

   public int compareTo(ActivityLifecycleTimeSpan var1) {
      int var2 = Long.compare(this.onCreate.getStartUptimeMs(), var1.onCreate.getStartUptimeMs());
      return var2 == 0 ? Long.compare(this.onStart.getStartUptimeMs(), var1.onStart.getStartUptimeMs()) : var2;
   }

   public final TimeSpan getOnCreate() {
      return this.onCreate;
   }

   public final TimeSpan getOnStart() {
      return this.onStart;
   }
}
