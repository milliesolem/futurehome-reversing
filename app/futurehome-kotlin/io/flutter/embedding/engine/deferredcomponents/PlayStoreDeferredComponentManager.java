package io.flutter.embedding.engine.deferredcomponents;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterJNI;
import io.flutter.embedding.engine.loader.ApplicationInfoLoader;
import io.flutter.embedding.engine.loader.FlutterApplicationInfo;
import io.flutter.embedding.engine.systemchannels.DeferredComponentChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class PlayStoreDeferredComponentManager implements DeferredComponentManager {
   public static final String MAPPING_KEY;
   private static final String TAG = "PlayStoreDeferredComponentManager";
   private DeferredComponentChannel channel;
   private Context context;
   private FlutterApplicationInfo flutterApplicationInfo;
   private FlutterJNI flutterJNI;
   private PlayStoreDeferredComponentManager.FeatureInstallStateUpdatedListener listener;
   protected SparseArray<String> loadingUnitIdToComponentNames;
   protected SparseArray<String> loadingUnitIdToSharedLibraryNames;
   private Map<String, Integer> nameToSessionId;
   private SparseIntArray sessionIdToLoadingUnitId;
   private SparseArray<String> sessionIdToName;
   private SparseArray<String> sessionIdToState;
   private SplitInstallManager splitInstallManager;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(DeferredComponentManager.class.getName());
      var0.append(".loadingUnitMapping");
      MAPPING_KEY = var0.toString();
   }

   public PlayStoreDeferredComponentManager(Context var1, FlutterJNI var2) {
      this.context = var1;
      this.flutterJNI = var2;
      this.flutterApplicationInfo = ApplicationInfoLoader.load(var1);
      this.splitInstallManager = SplitInstallManagerFactory.create(var1);
      PlayStoreDeferredComponentManager.FeatureInstallStateUpdatedListener var3 = new PlayStoreDeferredComponentManager.FeatureInstallStateUpdatedListener(this);
      this.listener = var3;
      this.splitInstallManager.registerListener(var3);
      this.sessionIdToName = new SparseArray();
      this.sessionIdToLoadingUnitId = new SparseIntArray();
      this.sessionIdToState = new SparseArray();
      this.nameToSessionId = new HashMap<>();
      this.loadingUnitIdToComponentNames = new SparseArray();
      this.loadingUnitIdToSharedLibraryNames = new SparseArray();
      this.initLoadingUnitMappingToComponentNames();
   }

   private ApplicationInfo getApplicationInfo() {
      try {
         return this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128);
      } catch (NameNotFoundException var2) {
         throw new RuntimeException(var2);
      }
   }

   private void initLoadingUnitMappingToComponentNames() {
      ApplicationInfo var4 = this.getApplicationInfo();
      if (var4 != null) {
         Bundle var5 = var4.metaData;
         if (var5 != null) {
            String var6 = MAPPING_KEY;
            String var8 = var5.getString(var6, null);
            if (var8 == null) {
               StringBuilder var10 = new StringBuilder("No loading unit to dynamic feature module name found. Ensure '");
               var10.append(var6);
               var10.append("' is defined in the base module's AndroidManifest.");
               Log.e("PlayStoreDeferredComponentManager", var10.toString());
               return;
            }

            if (var8.equals("")) {
               return;
            }

            String[] var9 = var8.split(",");
            int var2 = var9.length;

            for (int var1 = 0; var1 < var2; var1++) {
               String[] var7 = var9[var1].split(":", -1);
               int var3 = Integer.parseInt(var7[0]);
               this.loadingUnitIdToComponentNames.put(var3, var7[1]);
               if (var7.length > 2) {
                  this.loadingUnitIdToSharedLibraryNames.put(var3, var7[2]);
               }
            }
         }
      }
   }

   private boolean verifyJNI() {
      if (this.flutterJNI == null) {
         Log.e(
            "PlayStoreDeferredComponentManager",
            "No FlutterJNI provided. `setJNI` must be called on the DeferredComponentManager before attempting to load dart libraries or invoking with platform channels."
         );
         return false;
      } else {
         return true;
      }
   }

   @Override
   public void destroy() {
      this.splitInstallManager.unregisterListener(this.listener);
      this.channel = null;
      this.flutterJNI = null;
   }

   @Override
   public String getDeferredComponentInstallState(int var1, String var2) {
      if (var2 == null) {
         var2 = (String)this.loadingUnitIdToComponentNames.get(var1);
      }

      String var3 = "unknown";
      if (var2 == null) {
         Log.e("PlayStoreDeferredComponentManager", "Deferred component name was null and could not be resolved from loading unit id.");
         return "unknown";
      } else if (!this.nameToSessionId.containsKey(var2)) {
         if (this.splitInstallManager.getInstalledModules().contains(var2)) {
            var3 = "installedPendingLoad";
         }

         return var3;
      } else {
         var1 = this.nameToSessionId.get(var2);
         return (String)this.sessionIdToState.get(var1);
      }
   }

   @Override
   public void installDeferredComponent(int var1, String var2) {
      String var3;
      if (var2 != null) {
         var3 = var2;
      } else {
         var3 = (String)this.loadingUnitIdToComponentNames.get(var1);
      }

      if (var3 == null) {
         Log.e("PlayStoreDeferredComponentManager", "Deferred component name was null and could not be resolved from loading unit id.");
      } else if (var3.equals("") && var1 > 0) {
         this.loadDartLibrary(var1, var3);
      } else {
         SplitInstallRequest var4 = SplitInstallRequest.newBuilder().addModule(var3).build();
         this.splitInstallManager
            .startInstall(var4)
            .addOnSuccessListener(new PlayStoreDeferredComponentManager$$ExternalSyntheticLambda0(this, var3, var1))
            .addOnFailureListener(new PlayStoreDeferredComponentManager$$ExternalSyntheticLambda1(this, var1, var2));
      }
   }

   @Override
   public void loadAssets(int var1, String var2) {
      if (this.verifyJNI()) {
         try {
            Context var4 = this.context;
            Context var5 = var4.createPackageContext(var4.getPackageName(), 0);
            this.context = var5;
            AssetManager var6 = var5.getAssets();
            this.flutterJNI.updateJavaAssetManager(var6, this.flutterApplicationInfo.flutterAssetsDir);
         } catch (NameNotFoundException var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   @Override
   public void loadDartLibrary(int var1, String var2) {
      if (this.verifyJNI()) {
         if (var1 >= 0) {
            String var6 = (String)this.loadingUnitIdToSharedLibraryNames.get(var1);
            String var5 = var6;
            if (var6 == null) {
               StringBuilder var16 = new StringBuilder();
               var16.append(this.flutterApplicationInfo.aotSharedLibraryName);
               var16.append("-");
               var16.append(var1);
               var16.append(".part.so");
               var5 = var16.toString();
            }

            var6 = Build.SUPPORTED_ABIS[0];
            String var9 = var6.replace("-", "_");
            ArrayList var8 = new ArrayList();
            ArrayList var7 = new ArrayList();
            LinkedList var10 = new LinkedList();
            var10.add(this.context.getFilesDir());
            String[] var11 = this.context.getApplicationInfo().splitSourceDirs;
            int var4 = var11.length;

            for (int var3 = 0; var3 < var4; var3++) {
               var10.add(new File(var11[var3]));
            }

            while (!var10.isEmpty()) {
               File var12 = (File)var10.remove();
               if (var12 != null && var12.isDirectory() && var12.listFiles() != null) {
                  File[] var23 = var12.listFiles();
                  var4 = var23.length;

                  for (int var14 = 0; var14 < var4; var14++) {
                     var10.add(var23[var14]);
                  }
               } else {
                  String var22 = var12.getName();
                  if (var22.endsWith(".apk") && (var22.startsWith(var2) || var22.startsWith("split_config")) && var22.contains(var9)) {
                     var8.add(var12.getAbsolutePath());
                  } else if (var22.equals(var5)) {
                     var7.add(var12.getAbsolutePath());
                  }
               }
            }

            ArrayList var13 = new ArrayList();
            var13.add(var5);

            for (String var20 : var8) {
               StringBuilder var21 = new StringBuilder();
               var21.append(var20);
               var21.append("!lib/");
               var21.append(var6);
               var21.append("/");
               var21.append(var5);
               var13.add(var21.toString());
            }

            Iterator var17 = var7.iterator();

            while (var17.hasNext()) {
               var13.add((String)var17.next());
            }

            this.flutterJNI.loadDartDeferredLibrary(var1, var13.toArray(new String[var13.size()]));
         }
      }
   }

   @Override
   public void setDeferredComponentChannel(DeferredComponentChannel var1) {
      this.channel = var1;
   }

   @Override
   public void setJNI(FlutterJNI var1) {
      this.flutterJNI = var1;
   }

   @Override
   public boolean uninstallDeferredComponent(int var1, String var2) {
      if (var2 == null) {
         var2 = (String)this.loadingUnitIdToComponentNames.get(var1);
      }

      if (var2 == null) {
         Log.e("PlayStoreDeferredComponentManager", "Deferred component name was null and could not be resolved from loading unit id.");
         return false;
      } else {
         ArrayList var3 = new ArrayList();
         var3.add(var2);
         this.splitInstallManager.deferredUninstall(var3);
         if (this.nameToSessionId.get(var2) != null) {
            this.sessionIdToState.delete(this.nameToSessionId.get(var2));
         }

         return true;
      }
   }

   private class FeatureInstallStateUpdatedListener implements SplitInstallStateUpdatedListener {
      final PlayStoreDeferredComponentManager this$0;

      private FeatureInstallStateUpdatedListener(PlayStoreDeferredComponentManager var1) {
         this.this$0 = var1;
      }

      public void onStateUpdate(SplitInstallSessionState var1) {
         int var3 = var1.sessionId();
         if (this.this$0.sessionIdToName.get(var3) != null) {
            switch (var1.status()) {
               case 1:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install pending.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "pending");
                  break;
               case 2:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) downloading.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "downloading");
                  break;
               case 3:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) downloaded.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "downloaded");
                  break;
               case 4:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) installing.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "installing");
                  break;
               case 5:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install successfully.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  PlayStoreDeferredComponentManager var6 = this.this$0;
                  var6.loadAssets(var6.sessionIdToLoadingUnitId.get(var3), (String)this.this$0.sessionIdToName.get(var3));
                  if (this.this$0.sessionIdToLoadingUnitId.get(var3) > 0) {
                     PlayStoreDeferredComponentManager var7 = this.this$0;
                     var7.loadDartLibrary(var7.sessionIdToLoadingUnitId.get(var3), (String)this.this$0.sessionIdToName.get(var3));
                  }

                  if (this.this$0.channel != null) {
                     this.this$0.channel.completeInstallSuccess((String)this.this$0.sessionIdToName.get(var3));
                  }

                  this.this$0.sessionIdToName.delete(var3);
                  this.this$0.sessionIdToLoadingUnitId.delete(var3);
                  this.this$0.sessionIdToState.put(var3, "installed");
                  break;
               case 6:
                  Log.e(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install failed with: %s", this.this$0.sessionIdToName.get(var3), var3, var1.errorCode())
                  );
                  FlutterJNI var5 = this.this$0.flutterJNI;
                  int var2 = this.this$0.sessionIdToLoadingUnitId.get(var3);
                  StringBuilder var8 = new StringBuilder("Module install failed with ");
                  var8.append(var1.errorCode());
                  var5.deferredComponentInstallFailure(var2, var8.toString(), true);
                  if (this.this$0.channel != null) {
                     this.this$0.channel.completeInstallError((String)this.this$0.sessionIdToName.get(var3), "Android Deferred Component failed to install.");
                  }

                  this.this$0.sessionIdToName.delete(var3);
                  this.this$0.sessionIdToLoadingUnitId.delete(var3);
                  this.this$0.sessionIdToState.put(var3, "failed");
                  break;
               case 7:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install canceled.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  if (this.this$0.channel != null) {
                     this.this$0
                        .channel
                        .completeInstallError((String)this.this$0.sessionIdToName.get(var3), "Android Deferred Component installation canceled.");
                  }

                  this.this$0.sessionIdToName.delete(var3);
                  this.this$0.sessionIdToLoadingUnitId.delete(var3);
                  this.this$0.sessionIdToState.put(var3, "cancelled");
                  break;
               case 8:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install requires user confirmation.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "requiresUserConfirmation");
                  break;
               case 9:
                  Log.d(
                     "PlayStoreDeferredComponentManager",
                     String.format("Module \"%s\" (sessionId %d) install canceling.", this.this$0.sessionIdToName.get(var3), var3)
                  );
                  this.this$0.sessionIdToState.put(var3, "canceling");
                  break;
               default:
                  StringBuilder var4 = new StringBuilder("Unknown status: ");
                  var4.append(var1.status());
                  Log.d("PlayStoreDeferredComponentManager", var4.toString());
            }
         }
      }
   }
}
