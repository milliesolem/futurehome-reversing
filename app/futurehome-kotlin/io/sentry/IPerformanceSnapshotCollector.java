package io.sentry;

public interface IPerformanceSnapshotCollector extends IPerformanceCollector {
   void collect(PerformanceCollectionData var1);

   void setup();
}
