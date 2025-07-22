package io.flutter.embedding.android;

import android.util.LongSparseArray;
import android.view.MotionEvent;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

public final class MotionEventTracker {
   private static MotionEventTracker INSTANCE;
   private static final String TAG = "MotionEventTracker";
   private final LongSparseArray<MotionEvent> eventById = new LongSparseArray();
   private final PriorityQueue<Long> unusedEvents = new PriorityQueue<>();

   private MotionEventTracker() {
   }

   public static MotionEventTracker getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new MotionEventTracker();
      }

      return INSTANCE;
   }

   public MotionEvent pop(MotionEventTracker.MotionEventId var1) {
      while (!this.unusedEvents.isEmpty() && this.unusedEvents.peek() < var1.id) {
         this.eventById.remove(this.unusedEvents.poll());
      }

      if (!this.unusedEvents.isEmpty() && this.unusedEvents.peek() == var1.id) {
         this.unusedEvents.poll();
      }

      MotionEvent var2 = (MotionEvent)this.eventById.get(var1.id);
      this.eventById.remove(var1.id);
      return var2;
   }

   public MotionEventTracker.MotionEventId track(MotionEvent var1) {
      MotionEventTracker.MotionEventId var2 = MotionEventTracker.MotionEventId.createUnique();
      var1 = MotionEvent.obtain(var1);
      this.eventById.put(var2.id, var1);
      this.unusedEvents.add(var2.id);
      return var2;
   }

   public static class MotionEventId {
      private static final AtomicLong ID_COUNTER = new AtomicLong(0L);
      private final long id;

      private MotionEventId(long var1) {
         this.id = var1;
      }

      public static MotionEventTracker.MotionEventId createUnique() {
         return from(ID_COUNTER.incrementAndGet());
      }

      public static MotionEventTracker.MotionEventId from(long var0) {
         return new MotionEventTracker.MotionEventId(var0);
      }

      public long getId() {
         return this.id;
      }
   }
}
