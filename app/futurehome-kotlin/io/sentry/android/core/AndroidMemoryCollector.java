package io.sentry.android.core;

import android.os.Debug;
import io.sentry.IPerformanceSnapshotCollector;
import io.sentry.MemoryCollectionData;
import io.sentry.PerformanceCollectionData;

public class AndroidMemoryCollector implements IPerformanceSnapshotCollector {
   @Override
   public void collect(PerformanceCollectionData var1) {
      var1.addMemoryData(
         new MemoryCollectionData(
            System.currentTimeMillis(),
            Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(),
            Debug.getNativeHeapSize() - Debug.getNativeHeapFreeSize()
         )
      );
   }

   @Override
   public void setup() {
   }
}
