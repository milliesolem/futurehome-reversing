package io.sentry;

public final class JavaMemoryCollector implements IPerformanceSnapshotCollector {
   private final Runtime runtime = Runtime.getRuntime();

   @Override
   public void collect(PerformanceCollectionData var1) {
      var1.addMemoryData(new MemoryCollectionData(System.currentTimeMillis(), this.runtime.totalMemory() - this.runtime.freeMemory()));
   }

   @Override
   public void setup() {
   }
}
