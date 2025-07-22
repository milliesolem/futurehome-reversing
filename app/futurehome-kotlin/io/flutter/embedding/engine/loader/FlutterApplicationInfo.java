package io.flutter.embedding.engine.loader;

public final class FlutterApplicationInfo {
   private static final String DEFAULT_AOT_SHARED_LIBRARY_NAME = "libapp.so";
   private static final String DEFAULT_FLUTTER_ASSETS_DIR = "flutter_assets";
   private static final String DEFAULT_ISOLATE_SNAPSHOT_DATA = "isolate_snapshot_data";
   private static final String DEFAULT_VM_SNAPSHOT_DATA = "vm_snapshot_data";
   public final String aotSharedLibraryName;
   final boolean automaticallyRegisterPlugins;
   public final String domainNetworkPolicy;
   public final String flutterAssetsDir;
   public final String isolateSnapshotData;
   public final String nativeLibraryDir;
   public final String vmSnapshotData;

   public FlutterApplicationInfo(String var1, String var2, String var3, String var4, String var5, String var6, boolean var7) {
      String var8 = var1;
      if (var1 == null) {
         var8 = "libapp.so";
      }

      this.aotSharedLibraryName = var8;
      var1 = var2;
      if (var2 == null) {
         var1 = "vm_snapshot_data";
      }

      this.vmSnapshotData = var1;
      var1 = var3;
      if (var3 == null) {
         var1 = "isolate_snapshot_data";
      }

      this.isolateSnapshotData = var1;
      var1 = var4;
      if (var4 == null) {
         var1 = "flutter_assets";
      }

      this.flutterAssetsDir = var1;
      this.nativeLibraryDir = var6;
      var1 = var5;
      if (var5 == null) {
         var1 = "";
      }

      this.domainNetworkPolicy = var1;
      this.automaticallyRegisterPlugins = var7;
   }
}
