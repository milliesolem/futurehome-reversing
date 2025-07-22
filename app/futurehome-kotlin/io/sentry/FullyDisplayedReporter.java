package io.sentry;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FullyDisplayedReporter {
   private static final FullyDisplayedReporter instance = new FullyDisplayedReporter();
   private final List<FullyDisplayedReporter.FullyDisplayedReporterListener> listeners = new CopyOnWriteArrayList<>();

   private FullyDisplayedReporter() {
   }

   public static FullyDisplayedReporter getInstance() {
      return instance;
   }

   public void registerFullyDrawnListener(FullyDisplayedReporter.FullyDisplayedReporterListener var1) {
      this.listeners.add(var1);
   }

   public void reportFullyDrawn() {
      Iterator var1 = this.listeners.iterator();
      this.listeners.clear();

      while (var1.hasNext()) {
         ((FullyDisplayedReporter.FullyDisplayedReporterListener)var1.next()).onFullyDrawn();
      }
   }

   public interface FullyDisplayedReporterListener {
      void onFullyDrawn();
   }
}
