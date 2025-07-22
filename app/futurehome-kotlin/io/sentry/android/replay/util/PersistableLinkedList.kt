package io.sentry.android.replay.util

import io.sentry.ReplayRecording
import io.sentry.SentryOptions
import io.sentry.android.replay.ReplayCache
import io.sentry.rrweb.RRWebEvent
import java.io.BufferedWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ScheduledExecutorService

internal class PersistableLinkedList(propertyName: String,
      options: SentryOptions,
      persistingExecutor: ScheduledExecutorService,
      cacheProvider: () -> ReplayCache?
   )
   : ConcurrentLinkedDeque<RRWebEvent> {
   private final val cacheProvider: () -> ReplayCache?
   private final val options: SentryOptions
   private final val persistingExecutor: ScheduledExecutorService
   private final val propertyName: String

   init {
      this.propertyName = var1;
      this.options = var2;
      this.persistingExecutor = var3;
      this.cacheProvider = var4;
   }

   private fun persistRecording() {
      val var1: ReplayCache = this.cacheProvider.invoke();
      if (var1 != null) {
         val var2: ReplayRecording = new ReplayRecording();
         var2.setPayload(new ArrayList<>(this as MutableCollection<RRWebEvent>));
         if (this.options.getMainThreadChecker().isMainThread()) {
            this.persistingExecutor.submit(new PersistableLinkedList$$ExternalSyntheticLambda0(this, var2, var1));
         } else {
            val var3: StringWriter = new StringWriter();
            this.options.getSerializer().serialize(var2, new BufferedWriter(var3));
            var1.persistSegmentValues(this.propertyName, var3.toString());
         }
      }
   }

   @JvmStatic
   fun `persistRecording$lambda$1`(var0: PersistableLinkedList, var1: ReplayRecording, var2: ReplayCache) {
      val var3: StringWriter = new StringWriter();
      var0.options.getSerializer().serialize(var1, new BufferedWriter(var3));
      var2.persistSegmentValues(var0.propertyName, var3.toString());
   }

   public open fun add(element: RRWebEvent): Boolean {
      val var2: Boolean = super.add(var1);
      this.persistRecording();
      return var2;
   }

   public override fun addAll(elements: Collection<RRWebEvent>): Boolean {
      val var2: Boolean = super.addAll(var1);
      this.persistRecording();
      return var2;
   }

   public open fun remove(): RRWebEvent {
      val var1: RRWebEvent = super.remove() as RRWebEvent;
      this.persistRecording();
      return var1;
   }
}
