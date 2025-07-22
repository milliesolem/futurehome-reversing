package io.sentry;

public final class PerformanceCollectionData {
   private CpuCollectionData cpuData;
   private MemoryCollectionData memoryData = null;

   public PerformanceCollectionData() {
      this.cpuData = null;
   }

   public void addCpuData(CpuCollectionData var1) {
      if (var1 != null) {
         this.cpuData = var1;
      }
   }

   public void addMemoryData(MemoryCollectionData var1) {
      if (var1 != null) {
         this.memoryData = var1;
      }
   }

   public CpuCollectionData getCpuData() {
      return this.cpuData;
   }

   public MemoryCollectionData getMemoryData() {
      return this.memoryData;
   }
}
